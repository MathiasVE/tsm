import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimesheetMve } from 'app/shared/model/timesheet-mve.model';

@Component({
    selector: 'tsmjhi-timesheet-mve-detail',
    templateUrl: './timesheet-mve-detail.component.html'
})
export class TimesheetMveDetailComponent implements OnInit {
    timesheet: ITimesheetMve;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timesheet }) => {
            this.timesheet = timesheet;
        });
    }

    previousState() {
        window.history.back();
    }
}
