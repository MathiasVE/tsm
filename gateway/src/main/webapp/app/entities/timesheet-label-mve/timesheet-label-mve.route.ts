import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';
import { TimesheetLabelMveService } from './timesheet-label-mve.service';
import { TimesheetLabelMveComponent } from './timesheet-label-mve.component';
import { TimesheetLabelMveDetailComponent } from './timesheet-label-mve-detail.component';
import { TimesheetLabelMveUpdateComponent } from './timesheet-label-mve-update.component';
import { TimesheetLabelMveDeletePopupComponent } from './timesheet-label-mve-delete-dialog.component';
import { ITimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';

@Injectable({ providedIn: 'root' })
export class TimesheetLabelMveResolve implements Resolve<ITimesheetLabelMve> {
    constructor(private service: TimesheetLabelMveService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TimesheetLabelMve> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TimesheetLabelMve>) => response.ok),
                map((timesheetLabel: HttpResponse<TimesheetLabelMve>) => timesheetLabel.body)
            );
        }
        return of(new TimesheetLabelMve());
    }
}

export const timesheetLabelRoute: Routes = [
    {
        path: 'timesheet-label-mve',
        component: TimesheetLabelMveComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'gatewayApp.timesheetLabel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-label-mve/:id/view',
        component: TimesheetLabelMveDetailComponent,
        resolve: {
            timesheetLabel: TimesheetLabelMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetLabel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-label-mve/new',
        component: TimesheetLabelMveUpdateComponent,
        resolve: {
            timesheetLabel: TimesheetLabelMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetLabel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'timesheet-label-mve/:id/edit',
        component: TimesheetLabelMveUpdateComponent,
        resolve: {
            timesheetLabel: TimesheetLabelMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetLabel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timesheetLabelPopupRoute: Routes = [
    {
        path: 'timesheet-label-mve/:id/delete',
        component: TimesheetLabelMveDeletePopupComponent,
        resolve: {
            timesheetLabel: TimesheetLabelMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.timesheetLabel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
