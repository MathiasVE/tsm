import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    TimesheetLabelMveComponent,
    TimesheetLabelMveDetailComponent,
    TimesheetLabelMveUpdateComponent,
    TimesheetLabelMveDeletePopupComponent,
    TimesheetLabelMveDeleteDialogComponent,
    timesheetLabelRoute,
    timesheetLabelPopupRoute
} from './';

const ENTITY_STATES = [...timesheetLabelRoute, ...timesheetLabelPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimesheetLabelMveComponent,
        TimesheetLabelMveDetailComponent,
        TimesheetLabelMveUpdateComponent,
        TimesheetLabelMveDeleteDialogComponent,
        TimesheetLabelMveDeletePopupComponent
    ],
    entryComponents: [
        TimesheetLabelMveComponent,
        TimesheetLabelMveUpdateComponent,
        TimesheetLabelMveDeleteDialogComponent,
        TimesheetLabelMveDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayTimesheetLabelMveModule {}
