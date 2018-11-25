import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayTimesheetMveModule } from './timesheet-mve/timesheet-mve.module';
import { GatewayTimesheetEntryMveModule } from './timesheet-entry-mve/timesheet-entry-mve.module';
import { GatewayTimesheetLabelMveModule } from './timesheet-label-mve/timesheet-label-mve.module';
import { GatewayHolidayMveModule } from './holiday-mve/holiday-mve.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GatewayTimesheetMveModule,
        GatewayTimesheetEntryMveModule,
        GatewayTimesheetLabelMveModule,
        GatewayHolidayMveModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}
