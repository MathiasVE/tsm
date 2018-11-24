/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { TimesheetMveDetailComponent } from 'app/entities/timesheet-mve/timesheet-mve-detail.component';
import { TimesheetMve } from 'app/shared/model/timesheet-mve.model';

describe('Component Tests', () => {
    describe('TimesheetMve Management Detail Component', () => {
        let comp: TimesheetMveDetailComponent;
        let fixture: ComponentFixture<TimesheetMveDetailComponent>;
        const route = ({ data: of({ timesheet: new TimesheetMve(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TimesheetMveDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TimesheetMveDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimesheetMveDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.timesheet).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
