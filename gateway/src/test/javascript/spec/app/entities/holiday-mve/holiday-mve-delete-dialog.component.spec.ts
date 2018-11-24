/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../test.module';
import { HolidayMveDeleteDialogComponent } from 'app/entities/holiday-mve/holiday-mve-delete-dialog.component';
import { HolidayMveService } from 'app/entities/holiday-mve/holiday-mve.service';

describe('Component Tests', () => {
    describe('HolidayMve Management Delete Component', () => {
        let comp: HolidayMveDeleteDialogComponent;
        let fixture: ComponentFixture<HolidayMveDeleteDialogComponent>;
        let service: HolidayMveService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [HolidayMveDeleteDialogComponent]
            })
                .overrideTemplate(HolidayMveDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HolidayMveDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HolidayMveService);
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
