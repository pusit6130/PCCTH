import { Component, OnInit, LOCALE_ID } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { News } from '../news';
import { NewsService } from '../news.service';
import { Router } from '@angular/router';
import { HttpErrorResponse ,HttpEvent, HttpEventType} from '@angular/common/http';
import { TokenStorageService } from '../services/token-storage.service';
import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';
import { saveAs } from 'file-saver';
import { NgForm } from '@angular/forms';
import { FileService } from '../services/file.service';
import { formatDate } from '@angular/common';
import { DatePipe } from '@angular/common';

const EXCEL_TYPE =
  'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';

@Component({
  selector: 'app-uploadfile',
  templateUrl: './uploadfile.component.html',
  styleUrls: ['./uploadfile.component.css']
})
export class UploadfileComponent implements OnInit {
  news: News[];
  viewNews: News;
  // viewdetailNews: string[] = [];
  formNews: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  getUsername: any;
  files: any;
  allnews: News[];
  isSuccessful = false;
  isSignUpFailed = false;
  formData = new FormData();
  filenames: string[] = [];
  nameoffile: string[] = [];
  nDate: Date;
  txtDate: String;
  ntxtDate: any;
  today: number = Date.now();
  
  fileStatus = { status: '', requestType: '', percent: 0 };

  constructor(private employeeService: EmployeeService,private router: Router,
    private tokenStorageService: TokenStorageService,private fileService: FileService
    ,private newsService: NewsService,
    private datePipe: DatePipe) { }

  ngOnInit() {
    if (this.tokenStorageService.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorageService.getUser().roles;
      this.getUsername = this.tokenStorageService.getUser().username;
      this.nDate = new Date();
      this.txtDate = this.datePipe.transform(this.nDate,'MM/dd/yyyy');
      // alert(this.txtDate);
      // console.log("---------------------------ntxtDate--------------------------",this.ntxtDate)
      this.getNews();
    }else{
      this.router.navigate(['../../']);
    } 
  }

  onSubmitNews(addNewsForm): void {
    this.newsService.addNews(addNewsForm).subscribe(
      data => {
        console.log(data);
	      this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.ClickUploadFiles(data);
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
      window.location.reload();
  }

  // define a function to upload files
  onUploadFiles(files: File[]): void {
    for (const file of files) { 
      this.formData.append('files', file, file.name); 
      this.nameoffile.push(file.name);
    }
  }

  public getNews(): void {
    this.newsService.getNews().subscribe(
      (response: News[]) => {
        this.allnews = response;
        console.log(this.allnews);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getFileNews(idnews): void {
    this.fileService.getFileNews(idnews).subscribe(
      (response: string[]) => {
        this.filenames = response;
        console.log(this.allnews);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public searchNews(key: string): void {
    console.log(key);
    const results: News[] = [];
    for (const snews of this.allnews) {
      if (snews.news_name.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(snews);
      }
    }
    this.allnews = results;
    if (results.length === 0 || !key) {
      this.getNews();
    }
  }

  ClickUploadFiles(data: News): void {
    this.fileService.upload(this.formData,data).subscribe(
      event => {
        console.log(event);
        this.resportProgress(event);
        this.formData = new FormData();
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
    
  }

   // define a function to download files
   onDownloadFile(filename: string): void {
    this.fileService.download(filename).subscribe(
      event => {
        console.log(event);
        this.resportProgress(event);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  private resportProgress(httpEvent: HttpEvent<string[] | Blob>): void {
    switch(httpEvent.type) {
      case HttpEventType.UploadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Uploading... ');
        break;
      case HttpEventType.DownloadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Downloading... ');
        break;
      case HttpEventType.ResponseHeader:
        console.log('Header returned', httpEvent);
        break;
      case HttpEventType.Response:
        if (httpEvent.body instanceof Array) {
          this.fileStatus.status = 'done';
          for (const filename of httpEvent.body) {
            this.filenames.unshift(filename);
          }
        } else {
          saveAs(new File([httpEvent.body!], httpEvent.headers.get('File-Name')!, 
                  {type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}));
          // saveAs(new Blob([httpEvent.body!], 
          //   { type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}),
          //    httpEvent.headers.get('File-Name'));
        }
        this.fileStatus.status = 'done';
        break;
        default:
          console.log(httpEvent);
          break;
      
    }
  }

  private updateStatus(loaded: number, total: number, requestType: string): void {
    this.fileStatus.status = 'progress';
    this.fileStatus.requestType = requestType;
    this.fileStatus.percent = Math.round(100 * loaded / total);
  }

  public openModalAddNews(mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addNewsModal');
    }
    container.appendChild(button);
    button.click();
  }

  public openModalNewsDetail(news: News, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'detail') {
      // this.viewdetailNews = news;
      this.viewNews = news;
      this.getFileNews(news.idnews);
      button.setAttribute('data-target', '#detailNewsModal');
    }
    container.appendChild(button);
    button.click();
  }

  public ViewNewsDetail(news: News): void {
    this.newsService.viewNewsDetail(news).subscribe(
      (response: News) => {
        console.log(response);
        this.getNews();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  exportToExcel() {
    this.getNews();
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.news);
    const workbook: XLSX.WorkBook = {
      Sheets: { Sheet1: worksheet },
      SheetNames: ['Sheet1'],
    };

    const excelBuffer: any = XLSX.write(workbook, {
      bookType: 'xlsx',
      type: 'array',
    });

    const data: Blob = new Blob([excelBuffer], { type: EXCEL_TYPE });
    const date = new Date();
    const fileName = 'example.xlsx';

    FileSaver.saveAs(data, fileName);
  }

  toUploadPage(): void{
    this.router.navigate(['../../uploadfile']);
  }

  toHomePage(): void {
    this.router.navigate(['../../home']);
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  clean(): void {
    this.files = "";
  }
}
