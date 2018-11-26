import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    TimesheetEntryMveComponent,
    TimesheetEntryMveDetailComponent,
    TimesheetEntryMveUpdateComponent,
    TimesheetEntryMveDeletePopupComponent,
    TimesheetEntryMveDeleteDialogComponent,
    timesheetEntryRoute,
    timesheetEntryPopupRoute
} from './';

const ENTITY_STATES = [...timesheetEntryRoute, ...timesheetEntryPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimesheetEntryMveComponent,
        TimesheetEntryMveDetailComponent,
        TimesheetEntryMveUpdateComponent,
        TimesheetEntryMveDeleteDialogComponent,
        TimesheetEntryMveDeletePopupComponent
    ],
    entryComponents: [
        TimesheetEntryMveComponent,
        TimesheetEntryMveUpdateComponent,
        TimesheetEntryMveDeleteDialogComponent,
        TimesheetEntryMveDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayTimesheetEntryMveModule {}
