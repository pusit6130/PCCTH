import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { TokenStorageService } from '../services/token-storage.service';
import { LoginForm } from '../services/loginform';
//import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  form: any = {
    username: null,
    password: null
  };

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  currentUser: any;
  loginForm: LoginForm;


  constructor(private router: Router,private tokenStorage:TokenStorageService,private authService:EmployeeService,private employeeService: EmployeeService){}

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  onSubmit(): void {
    this.loginForm = this.form;
    this.authService.login(this.loginForm).subscribe(
      data => {
        console.log("------------------------------------",data.authenticated)
        if(data.authenticated == false){
	        this.login(data);
        } else {
          this.tokenStorage.saveUser(data);
        	this.router.navigate(['/totp']);
        }
        
        console.log(
          "user:"+data.username + " "+
          "email:"+data.email + " "+
          "token:"+data.accessToken 
          );

      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  login(data): void {
    this.tokenStorage.saveToken(data.accessToken);
    this.tokenStorage.saveUser(data);
    this.isLoginFailed = false;
    this.isLoggedIn = true;
    this.roles = this.tokenStorage.getUser().roles;
    this.reloadPage();
    }

  reloadPage(): void {
    this.router.navigate(['../../home']);
  }

  toRegisterPage(): void {
    this.router.navigate(['../../register']);
  }
}
