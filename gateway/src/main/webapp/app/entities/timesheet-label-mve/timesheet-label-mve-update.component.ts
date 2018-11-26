import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';
import { TimesheetLabelMveService } from './timesheet-label-mve.service';
import { ITimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';
import { TimesheetEntryMveService } from 'app/entities/timesheet-entry-mve';

@Component({
    selector: 'tsmjhi-timesheet-label-mve-update',
    templateUrl: './timesheet-label-mve-update.component.html'
})
export class TimesheetLabelMveUpdateComponent implements OnInit {
    timesheetLabel: ITimesheetLabelMve;
    isSaving: boolean;

    timesheetentries: ITimesheetEntryMve[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private timesheetLabelService: TimesheetLabelMveService,
        private timesheetEntryService: TimesheetEntryMveService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timesheetLabel }) => {
            this.timesheetLabel = timesheetLabel;
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
        if (this.timesheetLabel.id !== undefined) {
            this.subscribeToSaveResponse(this.timesheetLabelService.update(this.timesheetLabel));
        } else {
            this.subscribeToSaveResponse(this.timesheetLabelService.create(this.timesheetLabel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITimesheetLabelMve>>) {
        result.subscribe((res: HttpResponse<ITimesheetLabelMve>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
