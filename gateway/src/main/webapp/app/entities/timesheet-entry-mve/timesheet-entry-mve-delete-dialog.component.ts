import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';
import { TimesheetEntryMveService } from './timesheet-entry-mve.service';

@Component({
    selector: 'tsmjhi-timesheet-entry-mve-delete-dialog',
    templateUrl: './timesheet-entry-mve-delete-dialog.component.html'
})
export class TimesheetEntryMveDeleteDialogComponent {
    timesheetEntry: ITimesheetEntryMve;

    constructor(
        private timesheetEntryService: TimesheetEntryMveService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timesheetEntryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timesheetEntryListModification',
                content: 'Deleted an timesheetEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'tsmjhi-timesheet-entry-mve-delete-popup',
    template: ''
})
export class TimesheetEntryMveDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timesheetEntry }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TimesheetEntryMveDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.timesheetEntry = timesheetEntry;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
