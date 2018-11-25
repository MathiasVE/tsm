import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IHolidayMve } from 'app/shared/model/holiday-mve.model';
import { HolidayMveService } from './holiday-mve.service';

@Component({
    selector: 'tsmjhi-holiday-mve-update',
    templateUrl: './holiday-mve-update.component.html'
})
export class HolidayMveUpdateComponent implements OnInit {
    holiday: IHolidayMve;
    isSaving: boolean;
    dateDp: any;

    constructor(private holidayService: HolidayMveService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ holiday }) => {
            this.holiday = holiday;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.holiday.id !== undefined) {
            this.subscribeToSaveResponse(this.holidayService.update(this.holiday));
        } else {
            this.subscribeToSaveResponse(this.holidayService.create(this.holiday));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHolidayMve>>) {
        result.subscribe((res: HttpResponse<IHolidayMve>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
