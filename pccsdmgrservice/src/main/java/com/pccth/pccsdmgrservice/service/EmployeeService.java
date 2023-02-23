package com.pccth.pccsdmgrservice.service;

import com.pccth.jwt.JwtGenerator;
import com.pccth.pccsdmgrservice.exception.UserNotFoundException;
import com.pccth.pccsdmgrservice.model.Employee;
import com.pccth.pccsdmgrservice.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.samstevens.totp.secret.SecretGenerator;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final JwtGenerator jwtGenerator;
    @Autowired
	private SecretGenerator secretGenerator;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
        this.jwtGenerator = new JwtGenerator();
    }

    public Employee addEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        String passToken = jwtGenerator.generatePassWordToken(employee.getPassword());
        // System.out.println("//////////"+passToken);
        employee.setPassword(passToken);
        if (employee.isUsing2FA()) {
			employee.setUsing2FA(true);
			employee.setSecret(secretGenerator.generate());
		}
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepo.findEmployeeById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteEmployee(Long id){
        employeeRepo.deleteEmployeeById(id);
    }
    
    public Employee login(String username , String password) {
        return employeeRepo.loginEmployee(username);
    }
 
    
}
