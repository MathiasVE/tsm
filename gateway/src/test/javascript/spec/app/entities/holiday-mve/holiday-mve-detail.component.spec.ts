/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { HolidayMveDetailComponent } from 'app/entities/holiday-mve/holiday-mve-detail.component';
import { HolidayMve } from 'app/shared/model/holiday-mve.model';

describe('Component Tests', () => {
    describe('HolidayMve Management Detail Component', () => {
        let comp: HolidayMveDetailComponent;
        let fixture: ComponentFixture<HolidayMveDetailComponent>;
        const route = ({ data: of({ holiday: new HolidayMve(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [HolidayMveDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HolidayMveDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HolidayMveDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.holiday).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
