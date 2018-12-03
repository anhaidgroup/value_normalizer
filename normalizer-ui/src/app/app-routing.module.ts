import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UploadFileComponent} from './upload/upload-file.component';
import {LocalMergeComponent} from './merge/local-merge.component';
import {GlobalMergeComponent} from './merge/global-merge.component';

const routes: Routes = [
  { path: 'file/header/:name', component: LocalMergeComponent },
  { path: 'file/global/:name/:column', component: GlobalMergeComponent },
  { path: '**', component: UploadFileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
