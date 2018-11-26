import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    HolidayMveComponent,
    HolidayMveDetailComponent,
    HolidayMveUpdateComponent,
    HolidayMveDeletePopupComponent,
    HolidayMveDeleteDialogComponent,
    holidayRoute,
    holidayPopupRoute
} from './';

const ENTITY_STATES = [...holidayRoute, ...holidayPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HolidayMveComponent,
        HolidayMveDetailComponent,
        HolidayMveUpdateComponent,
        HolidayMveDeleteDialogComponent,
        HolidayMveDeletePopupComponent
    ],
    entryComponents: [HolidayMveComponent, HolidayMveUpdateComponent, HolidayMveDeleteDialogComponent, HolidayMveDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayHolidayMveModule {}
