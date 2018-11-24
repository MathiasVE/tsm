import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHolidayMve } from 'app/shared/model/holiday-mve.model';
import { HolidayMveService } from './holiday-mve.service';

@Component({
    selector: 'tsmjhi-holiday-mve-delete-dialog',
    templateUrl: './holiday-mve-delete-dialog.component.html'
})
export class HolidayMveDeleteDialogComponent {
    holiday: IHolidayMve;

    constructor(private holidayService: HolidayMveService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.holidayService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'holidayListModification',
                content: 'Deleted an holiday'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'tsmjhi-holiday-mve-delete-popup',
    template: ''
})
export class HolidayMveDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holiday }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HolidayMveDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.holiday = holiday;
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
