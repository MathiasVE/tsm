import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHolidayMve } from 'app/shared/model/holiday-mve.model';

@Component({
    selector: 'tsmjhi-holiday-mve-detail',
    templateUrl: './holiday-mve-detail.component.html'
})
export class HolidayMveDetailComponent implements OnInit {
    holiday: IHolidayMve;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holiday }) => {
            this.holiday = holiday;
        });
    }

    previousState() {
        window.history.back();
    }
}
