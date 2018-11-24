/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { TimesheetEntryMveDetailComponent } from 'app/entities/timesheet-entry-mve/timesheet-entry-mve-detail.component';
import { TimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';

describe('Component Tests', () => {
    describe('TimesheetEntryMve Management Detail Component', () => {
        let comp: TimesheetEntryMveDetailComponent;
        let fixture: ComponentFixture<TimesheetEntryMveDetailComponent>;
        const route = ({ data: of({ timesheetEntry: new TimesheetEntryMve(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TimesheetEntryMveDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TimesheetEntryMveDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimesheetEntryMveDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.timesheetEntry).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
