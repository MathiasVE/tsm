import {Component, OnInit} from '@angular/core';
import {Message} from 'primeng/components/common/api';

import {TimesheetCsvMveService} from './timesheet-csv-mve.service';

@Component({
    selector: 'tsmjhi-timesheet-csv-mve',
    templateUrl: './timesheet-csv-mve.component.html',
    styles: []
})
export class TimesheetCsvMveComponent implements OnInit {
    messages: Message[] = [];

    constructor(private timesheetCsvMveService: TimesheetCsvMveService) {

    }

    ngOnInit() {
    }

    upload(event: any) {
        this.messages = [];
        this.messages.push({severity: 'info', summary: 'CSV uploaded', detail: 'CSV uploaded'});

        this.timesheetCsvMveService.upload(event.files);
    }
}
