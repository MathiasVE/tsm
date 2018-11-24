/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { TimesheetLabelMveDetailComponent } from 'app/entities/timesheet-label-mve/timesheet-label-mve-detail.component';
import { TimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';

describe('Component Tests', () => {
    describe('TimesheetLabelMve Management Detail Component', () => {
        let comp: TimesheetLabelMveDetailComponent;
        let fixture: ComponentFixture<TimesheetLabelMveDetailComponent>;
        const route = ({ data: of({ timesheetLabel: new TimesheetLabelMve(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TimesheetLabelMveDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TimesheetLabelMveDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimesheetLabelMveDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.timesheetLabel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
