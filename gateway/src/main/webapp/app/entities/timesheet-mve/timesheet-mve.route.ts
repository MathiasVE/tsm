import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TimesheetMve } from 'app/shared/model/timesheet-mve.model';
import { TimesheetMveService } from './timesheet-mve.service';
import { TimesheetMveComponent } from './timesheet-mve.component';
import { TimesheetMveDetailComponent } from './timesheet-mve-detail.component';
import { TimesheetMveUpdateComponent } from './timesheet-mve-update.component';
import { TimesheetMveDeletePopupComponent } from './timesheet-mve-delete-dialog.component';
import { ITimesheetMve } from 'app/shared/model/timesheet-mve.model';

@Injectable({ providedIn: 'root' })
export class TimesheetMveResolve implements Resolve<ITimesheetMve> {
    constructor(private service: TimesheetMveService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TimesheetMve> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TimesheetMve>) => response.ok),
                map((timesheet: HttpResponse<TimesheetMve>) => timesheet.body)
            );
        }
        return of(new TimesheetMve());
    }
}

export const timesheetRoute: Routes = [
    {
        path: 'timesheet-mve',
        component: TimesheetMveComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'gatewayApp.timesheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-mve/:id/view',
        component: TimesheetMveDetailComponent,
        resolve: {
            timesheet: TimesheetMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-mve/new',
        component: TimesheetMveUpdateComponent,
        resolve: {
            timesheet: TimesheetMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-mve/:id/edit',
        component: TimesheetMveUpdateComponent,
        resolve: {
            timesheet: TimesheetMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timesheetPopupRoute: Routes = [
    {
        path: 'timesheet-mve/:id/delete',
        component: TimesheetMveDeletePopupComponent,
        resolve: {
            timesheet: TimesheetMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
