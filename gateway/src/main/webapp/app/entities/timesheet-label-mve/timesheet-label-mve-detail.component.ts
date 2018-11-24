import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';

@Component({
    selector: 'tsmjhi-timesheet-label-mve-detail',
    templateUrl: './timesheet-label-mve-detail.component.html'
})
export class TimesheetLabelMveDetailComponent implements OnInit {
    timesheetLabel: ITimesheetLabelMve;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timesheetLabel }) => {
            this.timesheetLabel = timesheetLabel;
        });
    }

    previousState() {
        window.history.back();
    }
}
