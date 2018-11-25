import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';
import { TimesheetLabelMveService } from './timesheet-label-mve.service';

@Component({
    selector: 'tsmjhi-timesheet-label-mve-delete-dialog',
    templateUrl: './timesheet-label-mve-delete-dialog.component.html'
})
export class TimesheetLabelMveDeleteDialogComponent {
    timesheetLabel: ITimesheetLabelMve;

    constructor(
        private timesheetLabelService: TimesheetLabelMveService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timesheetLabelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timesheetLabelListModification',
                content: 'Deleted an timesheetLabel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'tsmjhi-timesheet-label-mve-delete-popup',
    template: ''
})
export class TimesheetLabelMveDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timesheetLabel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TimesheetLabelMveDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.timesheetLabel = timesheetLabel;
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
