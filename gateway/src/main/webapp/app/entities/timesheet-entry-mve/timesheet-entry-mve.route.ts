import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';
import { TimesheetEntryMveService } from './timesheet-entry-mve.service';
import { TimesheetEntryMveComponent } from './timesheet-entry-mve.component';
import { TimesheetEntryMveDetailComponent } from './timesheet-entry-mve-detail.component';
import { TimesheetEntryMveUpdateComponent } from './timesheet-entry-mve-update.component';
import { TimesheetEntryMveDeletePopupComponent } from './timesheet-entry-mve-delete-dialog.component';
import { ITimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';

@Injectable({ providedIn: 'root' })
export class TimesheetEntryMveResolve implements Resolve<ITimesheetEntryMve> {
    constructor(private service: TimesheetEntryMveService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TimesheetEntryMve> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TimesheetEntryMve>) => response.ok),
                map((timesheetEntry: HttpResponse<TimesheetEntryMve>) => timesheetEntry.body)
            );
        }
        return of(new TimesheetEntryMve());
    }
}

export const timesheetEntryRoute: Routes = [
    {
        path: 'timesheet-entry-mve',
        component: TimesheetEntryMveComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-entry-mve/:id/view',
        component: TimesheetEntryMveDetailComponent,
        resolve: {
            timesheetEntry: TimesheetEntryMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-entry-mve/new',
        component: TimesheetEntryMveUpdateComponent,
        resolve: {
            timesheetEntry: TimesheetEntryMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-entry-mve/:id/edit',
        component: TimesheetEntryMveUpdateComponent,
        resolve: {
            timesheetEntry: TimesheetEntryMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timesheetEntryPopupRoute: Routes = [
    {
        path: 'timesheet-entry-mve/:id/delete',
        component: TimesheetEntryMveDeletePopupComponent,
        resolve: {
            timesheetEntry: TimesheetEntryMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
