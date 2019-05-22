import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UploadFileComponent} from './upload/upload-file.component';
import {LocalMergeComponent} from './merge/local-merge.component';
import {GlobalMergeComponent} from './merge/global-merge.component';
import {DownloadFileComponent} from './download/download-file.component';
import {NotFoundComponent} from './not-found.component';


const routes: Routes = [
  { path: 'value_normalizer/file/header/:name', component: LocalMergeComponent },
  { path: 'value_normalizer/file/global/:name/:column', component: GlobalMergeComponent },
  { path: 'value_normalizer/file/download/:name', component: DownloadFileComponent },
  { path: 'value_normalizer/:token', component: UploadFileComponent },
  { path: 'value_normalizer', component: UploadFileComponent},
  { path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
