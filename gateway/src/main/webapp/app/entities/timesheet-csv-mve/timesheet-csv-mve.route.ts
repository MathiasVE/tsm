import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../core/auth/user-route-access-service';
import { TimesheetCsvMveComponent } from './timesheet-csv-mve.component';

export const timesheetCsvMveRoute: Route = {
    path: 'timesheet-csv-mve',
    component: TimesheetCsvMveComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'gatewayApp.timesheetCsv.home.title'
    },
    canActivate: [UserRouteAccessService]
};
