import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { News } from '../news';

@Injectable({providedIn: 'root'})
export class FileService {
  private server = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // define function to upload files
  upload(formData: FormData,data: News): Observable<HttpEvent<string[]>> {
    console.log("///////////fornt-end/////////////",formData,data);
    return this.http.post<string[]>(`${this.server}/file/upload/${data.idnews}/`, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }

  // define function to download files
  download(filename: string): Observable<HttpEvent<Blob>> {
    return this.http.get(`${this.server}/file/download/${filename}/`, {
      reportProgress: true,
      observe: 'events',
      responseType: 'blob'
    });
  }

  getFileNews(id: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.server}/file/findNews/${id}/`);
  }
  
}
