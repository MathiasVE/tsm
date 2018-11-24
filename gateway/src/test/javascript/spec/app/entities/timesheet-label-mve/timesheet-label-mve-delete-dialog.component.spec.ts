/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../test.module';
import { TimesheetLabelMveDeleteDialogComponent } from 'app/entities/timesheet-label-mve/timesheet-label-mve-delete-dialog.component';
import { TimesheetLabelMveService } from 'app/entities/timesheet-label-mve/timesheet-label-mve.service';

describe('Component Tests', () => {
    describe('TimesheetLabelMve Management Delete Component', () => {
        let comp: TimesheetLabelMveDeleteDialogComponent;
        let fixture: ComponentFixture<TimesheetLabelMveDeleteDialogComponent>;
        let service: TimesheetLabelMveService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TimesheetLabelMveDeleteDialogComponent]
            })
                .overrideTemplate(TimesheetLabelMveDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimesheetLabelMveDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimesheetLabelMveService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
