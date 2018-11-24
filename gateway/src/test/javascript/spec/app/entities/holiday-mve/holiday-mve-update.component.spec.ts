/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { HolidayMveUpdateComponent } from 'app/entities/holiday-mve/holiday-mve-update.component';
import { HolidayMveService } from 'app/entities/holiday-mve/holiday-mve.service';
import { HolidayMve } from 'app/shared/model/holiday-mve.model';

describe('Component Tests', () => {
    describe('HolidayMve Management Update Component', () => {
        let comp: HolidayMveUpdateComponent;
        let fixture: ComponentFixture<HolidayMveUpdateComponent>;
        let service: HolidayMveService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [HolidayMveUpdateComponent]
            })
                .overrideTemplate(HolidayMveUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HolidayMveUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HolidayMveService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HolidayMve(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.holiday = entity;
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
                    const entity = new HolidayMve();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.holiday = entity;
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
