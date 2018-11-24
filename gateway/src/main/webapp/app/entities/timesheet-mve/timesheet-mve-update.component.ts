import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITimesheetMve } from 'app/shared/model/timesheet-mve.model';
import { TimesheetMveService } from './timesheet-mve.service';
import { ITimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';
import { TimesheetEntryMveService } from 'app/entities/timesheet-entry-mve';

@Component({
    selector: 'tsmjhi-timesheet-mve-update',
    templateUrl: './timesheet-mve-update.component.html'
})
export class TimesheetMveUpdateComponent implements OnInit {
    timesheet: ITimesheetMve;
    isSaving: boolean;

    timesheetentries: ITimesheetEntryMve[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private timesheetService: TimesheetMveService,
        private timesheetEntryService: TimesheetEntryMveService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timesheet }) => {
            this.timesheet = timesheet;
        });
        this.timesheetEntryService.query().subscribe(
            (res: HttpResponse<ITimesheetEntryMve[]>) => {
                this.timesheetentries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.timesheet.id !== undefined) {
            this.subscribeToSaveResponse(this.timesheetService.update(this.timesheet));
        } else {
            this.subscribeToSaveResponse(this.timesheetService.create(this.timesheet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITimesheetMve>>) {
        result.subscribe((res: HttpResponse<ITimesheetMve>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTimesheetEntryById(index: number, item: ITimesheetEntryMve) {
        return item.id;
    }
}
