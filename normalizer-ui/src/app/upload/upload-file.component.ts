import { Component, OnInit } from '@angular/core';
import { UploadFileService } from '../upload/upload-file.service';
import {HttpResponse, HttpEventType, HttpErrorResponse} from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private uploadService: UploadFileService,
              private router: Router) { }

  ngOnInit() {
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe((response: any) => {
      if (response.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * response.loaded / response.total);
      } else if (response instanceof HttpResponse) {
        const file = JSON.parse(response.body).file;
        console.log("Redirecting");
        this.router.navigate(['/file/header/', file]);
      } else if (response.type instanceof HttpErrorResponse) {
        console.log('Some Error occurred uploading');
      }
    }, );

    this.selectedFiles = undefined;
  }

}
