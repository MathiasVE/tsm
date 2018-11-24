import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    TimesheetMveComponent,
    TimesheetMveDetailComponent,
    TimesheetMveUpdateComponent,
    TimesheetMveDeletePopupComponent,
    TimesheetMveDeleteDialogComponent,
    timesheetRoute,
    timesheetPopupRoute
} from './';

const ENTITY_STATES = [...timesheetRoute, ...timesheetPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimesheetMveComponent,
        TimesheetMveDetailComponent,
        TimesheetMveUpdateComponent,
        TimesheetMveDeleteDialogComponent,
        TimesheetMveDeletePopupComponent
    ],
    entryComponents: [
        TimesheetMveComponent,
        TimesheetMveUpdateComponent,
        TimesheetMveDeleteDialogComponent,
        TimesheetMveDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayTimesheetMveModule {}
