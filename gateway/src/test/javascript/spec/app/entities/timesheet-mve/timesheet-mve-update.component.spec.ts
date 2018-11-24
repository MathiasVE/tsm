/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { TimesheetMveUpdateComponent } from 'app/entities/timesheet-mve/timesheet-mve-update.component';
import { TimesheetMveService } from 'app/entities/timesheet-mve/timesheet-mve.service';
import { TimesheetMve } from 'app/shared/model/timesheet-mve.model';

describe('Component Tests', () => {
    describe('TimesheetMve Management Update Component', () => {
        let comp: TimesheetMveUpdateComponent;
        let fixture: ComponentFixture<TimesheetMveUpdateComponent>;
        let service: TimesheetMveService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TimesheetMveUpdateComponent]
            })
                .overrideTemplate(TimesheetMveUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimesheetMveUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimesheetMveService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TimesheetMve(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timesheet = entity;
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
                    const entity = new TimesheetMve();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timesheet = entity;
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
