import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';

@Component({
    selector: 'tsmjhi-timesheet-entry-mve-detail',
    templateUrl: './timesheet-entry-mve-detail.component.html'
})
export class TimesheetEntryMveDetailComponent implements OnInit {
    timesheetEntry: ITimesheetEntryMve;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timesheetEntry }) => {
            this.timesheetEntry = timesheetEntry;
        });
    }

    previousState() {
        window.history.back();
    }
}
