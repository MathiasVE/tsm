import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';
import { TimesheetEntryMveService } from './timesheet-entry-mve.service';
import { ITimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';
import { TimesheetLabelMveService } from 'app/entities/timesheet-label-mve';

@Component({
    selector: 'tsmjhi-timesheet-entry-mve-update',
    templateUrl: './timesheet-entry-mve-update.component.html'
})
export class TimesheetEntryMveUpdateComponent implements OnInit {
    timesheetEntry: ITimesheetEntryMve;
    isSaving: boolean;

    timesheetlabels: ITimesheetLabelMve[];
    dateDp: any;
    from: string;
    until: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private timesheetEntryService: TimesheetEntryMveService,
        private timesheetLabelService: TimesheetLabelMveService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timesheetEntry }) => {
            this.timesheetEntry = timesheetEntry;
            this.from = this.timesheetEntry.from != null ? this.timesheetEntry.from.format(DATE_TIME_FORMAT) : null;
            this.until = this.timesheetEntry.until != null ? this.timesheetEntry.until.format(DATE_TIME_FORMAT) : null;
        });
        this.timesheetLabelService.query().subscribe(
            (res: HttpResponse<ITimesheetLabelMve[]>) => {
                this.timesheetlabels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.timesheetEntry.from = this.from != null ? moment(this.from, DATE_TIME_FORMAT) : null;
        this.timesheetEntry.until = this.until != null ? moment(this.until, DATE_TIME_FORMAT) : null;
        if (this.timesheetEntry.id !== undefined) {
            this.subscribeToSaveResponse(this.timesheetEntryService.update(this.timesheetEntry));
        } else {
            this.subscribeToSaveResponse(this.timesheetEntryService.create(this.timesheetEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITimesheetEntryMve>>) {
        result.subscribe((res: HttpResponse<ITimesheetEntryMve>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTimesheetLabelById(index: number, item: ITimesheetLabelMve) {
        return item.id;
    }
}
