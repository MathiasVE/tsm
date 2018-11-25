import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimesheetMve } from 'app/shared/model/timesheet-mve.model';
import { TimesheetMveService } from './timesheet-mve.service';

@Component({
    selector: 'tsmjhi-timesheet-mve-delete-dialog',
    templateUrl: './timesheet-mve-delete-dialog.component.html'
})
export class TimesheetMveDeleteDialogComponent {
    timesheet: ITimesheetMve;

    constructor(private timesheetService: TimesheetMveService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timesheetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timesheetListModification',
                content: 'Deleted an timesheet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'tsmjhi-timesheet-mve-delete-popup',
    template: ''
})
export class TimesheetMveDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timesheet }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TimesheetMveDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.timesheet = timesheet;
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
