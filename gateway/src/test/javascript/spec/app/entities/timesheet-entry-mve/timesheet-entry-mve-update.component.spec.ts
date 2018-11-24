/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { TimesheetEntryMveUpdateComponent } from 'app/entities/timesheet-entry-mve/timesheet-entry-mve-update.component';
import { TimesheetEntryMveService } from 'app/entities/timesheet-entry-mve/timesheet-entry-mve.service';
import { TimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';

describe('Component Tests', () => {
    describe('TimesheetEntryMve Management Update Component', () => {
        let comp: TimesheetEntryMveUpdateComponent;
        let fixture: ComponentFixture<TimesheetEntryMveUpdateComponent>;
        let service: TimesheetEntryMveService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TimesheetEntryMveUpdateComponent]
            })
                .overrideTemplate(TimesheetEntryMveUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimesheetEntryMveUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimesheetEntryMveService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TimesheetEntryMve(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timesheetEntry = entity;
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
                    const entity = new TimesheetEntryMve();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timesheetEntry = entity;
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
