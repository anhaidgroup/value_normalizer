import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DownloadFileService} from './download-file.service';
import {HttpErrorResponse, HttpEventType, HttpResponse} from '@angular/common/http';

@Component({
  selector: 'download-file',
  templateUrl: './download-file.component.html',
  styleUrls: ['./download-file.component.css']
})
export class DownloadFileComponent implements OnInit {
  fileName: string;
  token: string;
  constructor(private activeRoute: ActivatedRoute, private downloadFileService: DownloadFileService,
              private router: Router) {
  }
  ngOnInit(): void {
    const routeParams = this.activeRoute.snapshot.params;
    this.fileName = routeParams.name;
  }

  download() {
    this.downloadFileService.downloadFile(this.fileName);
  }

  saveToCdrive() {}
}
