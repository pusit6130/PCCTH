import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from './employee';
import { LoginForm }from './services/loginform';
import { environment } from 'src/environments/environment';
import { returnToken } from './services/rueturntoken';
import { RegisterResponse } from './RegisterResponse';
import { TokenStorageService } from '../app/_services/token-storage.service';

@Injectable({providedIn: 'root'})
export class EmployeeService {
  private apiServerUrl = environment.apiBaseUrl;
  roles: string[] = [];
  
  constructor(private http: HttpClient, private tokenstorageservice: TokenStorageService){}

  public getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.apiServerUrl}/employee/all`);
  }

  public addEmployee(employee: Employee): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.apiServerUrl}/employee/add`, employee);
  }

  public updateEmployee(employee: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.apiServerUrl}/employee/update`, employee);
  }

  public deleteEmployee(employeeId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/employee/delete/${employeeId}`);
  }

  public login(loginForm: LoginForm): Observable<returnToken> {
    return this.http.post<returnToken>(`${this.apiServerUrl}/employee/login`,loginForm);
  }

  public verify(credentials): Observable<returnToken> {
    this.roles = this.tokenstorageservice.getUser()
    return this.http.post<returnToken>(`${this.apiServerUrl}/employee/verify/${credentials.code}`,this.roles);
  }
}
