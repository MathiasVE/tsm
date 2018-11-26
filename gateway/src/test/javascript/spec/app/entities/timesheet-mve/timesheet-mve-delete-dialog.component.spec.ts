/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../test.module';
import { TimesheetMveDeleteDialogComponent } from 'app/entities/timesheet-mve/timesheet-mve-delete-dialog.component';
import { TimesheetMveService } from 'app/entities/timesheet-mve/timesheet-mve.service';

describe('Component Tests', () => {
    describe('TimesheetMve Management Delete Component', () => {
        let comp: TimesheetMveDeleteDialogComponent;
        let fixture: ComponentFixture<TimesheetMveDeleteDialogComponent>;
        let service: TimesheetMveService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TimesheetMveDeleteDialogComponent]
            })
                .overrideTemplate(TimesheetMveDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimesheetMveDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimesheetMveService);
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
