import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { EmployeeService } from './employee.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { MatCardModule } from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { TotpComponent } from './totp/totp.component';
import { UploadfileComponent } from './uploadfile/uploadfile.component';
import { DatePipe } from '@angular/common';



@NgModule({
  declarations: [			
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    TotpComponent,
    UploadfileComponent
   ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule, 
    MatCardModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    AppRoutingModule
  ],
  providers: [EmployeeService,DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
