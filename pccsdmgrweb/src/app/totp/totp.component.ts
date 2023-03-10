import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';



@Component({
  selector: 'app-totp',
  templateUrl: './totp.component.html',
  styleUrls: ['./totp.component.css']
})
export class TotpComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  currentUser: any;

  constructor(private authService: EmployeeService, private tokenStorage: TokenStorageService,private router: Router) {}

  ngOnInit(): void {
  	if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.tokenStorage.getUser();
    }
  }

  onSubmit(): void {
    this.authService.verify(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        
        this.login(data);
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  login(user): void {
	this.tokenStorage.saveUser(user);
	this.isLoginFailed = false;
	this.isLoggedIn = true;
	this.currentUser = this.tokenStorage.getUser();
  // window.location.reload();
  // this.nextPage();
  alert("เข้าสู่ระบบสำเร็จ ยินดีต้อนรับ");
  this.router.navigate(['../../home']);
  }

  nextPage(): void{
    this.router.navigate(['../../home']);
  }

}
