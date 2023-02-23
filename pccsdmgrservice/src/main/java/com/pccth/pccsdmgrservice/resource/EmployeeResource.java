package com.pccth.pccsdmgrservice.resource;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static dev.samstevens.totp.util.Utils.*;
import com.pccth.jwt.JwtGenerator;
import com.pccth.pccsdmgrservice.model.*;
import com.pccth.pccsdmgrservice.service.*;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import io.jsonwebtoken.Claims;


@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private final EmployeeService employeeService;
    private final JwtGenerator jwtGenerator;
    
    @Autowired
	private QrGenerator qrGenerator;
     @Autowired
	private QrDataFactory qrDataFactory;
 	@Autowired
 	private CodeVerifier verifier;

    public EmployeeResource(EmployeeService employeeService) {
    	this.jwtGenerator = new JwtGenerator();
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees () {
        List<Employee> employees = employeeService.findAllEmployees();
        System.out.println("---------------------------------Get News :"+employees);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable("id") Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
    	 try {
    		 Employee newEmployee = employeeService.addEmployee(employee);
         if (employee.isUsing2FA()) {
 				QrData data = qrDataFactory.newBuilder().label(newEmployee.getEmail()).secret(newEmployee.getSecret()).issuer("JavaChinna").build();
 				String qrCodeImage = getDataUriForImage(qrGenerator.generate(data), qrGenerator.getImageMimeType());
 				System.out.println("--------------QR----------------"+qrCodeImage);
 				return ResponseEntity.ok().body(new RegisterResponse(true,qrCodeImage));
 			}
         }catch(Exception e) {}
         
         return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<returntoken>loginEmployee(@RequestBody loginform loginform) {
    	System.out.println("////////////////////"+ loginform.getUsername() +" "+ loginform.getPassword());
    	Employee loginEmployee = employeeService.login(loginform.getUsername(), loginform.getPassword());
        returntoken RT = null;
        
        if(loginEmployee != null) {
            Claims passwordjwt = JwtGenerator.decodeJWT(loginEmployee.getPassword()); 
               
                 var arr = passwordjwt.toString().split("=");
                 var arr2 = arr[1].toString().split("}");
                 
                 System.out.println(arr2[0]+loginform.getPassword());
                 
            if(arr2[0].equals(loginform.getPassword())) {
              String jwt = jwtGenerator.generateAccessToken(loginform.getUsername());
                RT = new returntoken(loginEmployee.getName(),loginEmployee.getEmail(),jwt,loginEmployee.isUsing2FA(),loginEmployee.getSecret());
                System.out.println("////////////////////"+loginEmployee.isUsing2FA());
                System.out.println("////////////////////"+RT.isAuthenticated()); 
            }else {
                String jwt = "error";
                RT = new returntoken(loginEmployee.getName(),null,jwt,loginEmployee.isUsing2FA(),null);
            }
          
           }else {
            String error_jwt = "error";
             RT = new returntoken(loginEmployee.getName(),null,error_jwt,loginEmployee.isUsing2FA(),null);
           }

           
           return new ResponseEntity<>(RT,HttpStatus.OK);
    }
    
    @PostMapping("/verify/{Code}")
    public ResponseEntity<?> verifyCode(@PathVariable("Code") String Code ,@RequestBody returntoken returntoken) {
        if (!verifier.isValidCode(returntoken.getSecret(), Code)) {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid Code!"), HttpStatus.BAD_REQUEST);
        }
        String jwt = jwtGenerator.generateAccessToken(returntoken.getEmail());
 
        returntoken AR = new returntoken(returntoken.getUsername(),returntoken.getEmail(),returntoken.getSecret(),true,jwt);
         return new ResponseEntity<>(AR,HttpStatus.OK);
    }
    
    
    

}
