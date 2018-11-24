/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { TimesheetLabelMveUpdateComponent } from 'app/entities/timesheet-label-mve/timesheet-label-mve-update.component';
import { TimesheetLabelMveService } from 'app/entities/timesheet-label-mve/timesheet-label-mve.service';
import { TimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';

describe('Component Tests', () => {
    describe('TimesheetLabelMve Management Update Component', () => {
        let comp: TimesheetLabelMveUpdateComponent;
        let fixture: ComponentFixture<TimesheetLabelMveUpdateComponent>;
        let service: TimesheetLabelMveService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TimesheetLabelMveUpdateComponent]
            })
                .overrideTemplate(TimesheetLabelMveUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimesheetLabelMveUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimesheetLabelMveService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TimesheetLabelMve(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timesheetLabel = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TimesheetLabelMve();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timesheetLabel = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
