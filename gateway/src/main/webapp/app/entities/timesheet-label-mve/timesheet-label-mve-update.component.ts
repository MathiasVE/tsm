import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';
import { TimesheetLabelMveService } from './timesheet-label-mve.service';

@Component({
    selector: 'tsmjhi-timesheet-label-mve-update',
    templateUrl: './timesheet-label-mve-update.component.html'
})
export class TimesheetLabelMveUpdateComponent implements OnInit {
    timesheetLabel: ITimesheetLabelMve;
    isSaving: boolean;

    constructor(private timesheetLabelService: TimesheetLabelMveService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timesheetLabel }) => {
            this.timesheetLabel = timesheetLabel;
        });
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
}
