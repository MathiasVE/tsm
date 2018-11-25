import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {FileUploadModule} from 'primeng/components/fileupload/fileupload';
import {APP_BASE_HREF, CommonModule} from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {GrowlModule} from 'primeng/primeng';

import { GatewaySharedModule } from '../../shared';

import {
    TimesheetCsvMveComponent,
    timesheetCsvMveRoute
} from './';

const CSV_STATES = [
    timesheetCsvMveRoute
];

@NgModule({
  imports: [
      GrowlModule,
      CommonModule,
      FileUploadModule,
      GatewaySharedModule,
      BrowserAnimationsModule,
      RouterModule.forChild(CSV_STATES)
  ],
  declarations: [
      TimesheetCsvMveComponent
  ],
  entryComponents: [
    TimesheetCsvMveComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayTimesheetCsvMveModule { }
