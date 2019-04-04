import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DownloadFileService {
  constructor(private http: HttpClient) { }

  downloadFile(fileName: string) {
    window.location.href = 'api/file/download/' + fileName;
  }

  uploadFileToCdrive(url: string, token: string): Observable<HttpEvent<{}>> {
    console.log('url' + url);

    const req = new HttpRequest('GET', 'api/cdrive/download?url=' + url + '&token=' + token, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(req);
  }
}
