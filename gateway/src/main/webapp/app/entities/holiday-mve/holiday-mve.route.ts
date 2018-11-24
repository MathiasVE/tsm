import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HolidayMve } from 'app/shared/model/holiday-mve.model';
import { HolidayMveService } from './holiday-mve.service';
import { HolidayMveComponent } from './holiday-mve.component';
import { HolidayMveDetailComponent } from './holiday-mve-detail.component';
import { HolidayMveUpdateComponent } from './holiday-mve-update.component';
import { HolidayMveDeletePopupComponent } from './holiday-mve-delete-dialog.component';
import { IHolidayMve } from 'app/shared/model/holiday-mve.model';

@Injectable({ providedIn: 'root' })
export class HolidayMveResolve implements Resolve<IHolidayMve> {
    constructor(private service: HolidayMveService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HolidayMve> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HolidayMve>) => response.ok),
                map((holiday: HttpResponse<HolidayMve>) => holiday.body)
            );
        }
        return of(new HolidayMve());
    }
}

export const holidayRoute: Routes = [
    {
        path: 'holiday-mve',
        component: HolidayMveComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'gatewayApp.holiday.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'holiday-mve/:id/view',
        component: HolidayMveDetailComponent,
        resolve: {
            holiday: HolidayMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.holiday.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'holiday-mve/new',
        component: HolidayMveUpdateComponent,
        resolve: {
            holiday: HolidayMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.holiday.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'holiday-mve/:id/edit',
        component: HolidayMveUpdateComponent,
        resolve: {
            holiday: HolidayMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.holiday.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const holidayPopupRoute: Routes = [
    {
        path: 'holiday-mve/:id/delete',
        component: HolidayMveDeletePopupComponent,
        resolve: {
            holiday: HolidayMveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.holiday.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
