import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { AuthService } from '../_services/auth.service';
import { Employee } from '../employee';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.scss']
})

export class RegisterComponent implements OnInit {
  form: any = {};
  public employees: Employee[];
  isSuccessful = false;
  isSignUpFailed = false;
  isUsing2FA = false;
  errorMessage = '';
  qrCodeImage = '';

  constructor(private employeeService: EmployeeService,private authService: AuthService) {}

  ngOnInit() {}

  onSubmit(): void {
    console.log(this.form);
    this.employeeService.addEmployee(this.form).subscribe(
      data => {
        console.log(data);
        if(data.using2FA){
        	this.isUsing2FA = true;
        	this.qrCodeImage = data.qrCodeImage;
        }
	      this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

}
