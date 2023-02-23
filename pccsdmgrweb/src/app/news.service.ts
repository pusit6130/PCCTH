import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { News } from '../app/news';
import { LoginForm }from './services/loginform';
import { environment } from 'src/environments/environment';
import { returnToken } from './services/rueturntoken';
import { RegisterResponse } from './RegisterResponse';
import { TokenStorageService } from '../app/_services/token-storage.service';

@Injectable({providedIn: 'root'})
export class NewsService {
  private apiServerUrl = environment.apiBaseUrl;
  roles: string[] = [];
  private server = 'http://localhost:8080';

  constructor(private http: HttpClient, private tokenstorageservice: TokenStorageService){}

// define function to upload files
  upload(formData: FormData): Observable<HttpEvent<string[]>> {
    return this.http.post<string[]>(`${this.server}/file/upload`, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }
 
  public addNews(addNewsForm): Observable<News> {
    console.log("------------------------------News service--------------------------------: ",addNewsForm);
    return this.http.post<News>(`${this.apiServerUrl}/news/addnews`, addNewsForm);
  }

  public getNews(): Observable<News[]> {
    return this.http.get<News[]>(`${this.apiServerUrl}/news/allnews`);
  }

  // public updateNews(news: News): Observable<News> {
  //   return this.http.put<News>(`${this.apiServerUrl}/news/update`, news);
  // }

  public viewNewsDetail(news: News): Observable<News> {
    return this.http.put<News>(`${this.apiServerUrl}/news/viewnewsdetail`, news);
  }




}