import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { TsmjhiMetricsMonitoringComponent } from 'app/admin/metrics/metrics.component';
import { TsmjhiMetricsService } from 'app/admin/metrics/metrics.service';

describe('Component Tests', () => {
    describe('TsmjhiMetricsMonitoringComponent', () => {
        let comp: TsmjhiMetricsMonitoringComponent;
        let fixture: ComponentFixture<TsmjhiMetricsMonitoringComponent>;
        let service: TsmjhiMetricsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TsmjhiMetricsMonitoringComponent]
            })
                .overrideTemplate(TsmjhiMetricsMonitoringComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TsmjhiMetricsMonitoringComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TsmjhiMetricsService);
        });

        describe('refresh', () => {
            it('should call refresh on init', () => {
                // GIVEN
                const response = {
                    timers: {
                        service: 'test',
                        unrelatedKey: 'test'
                    },
                    gauges: {
                        'jcache.statistics': {
                            value: 2
                        },
                        unrelatedKey: 'test'
                    }
                };
                spyOn(service, 'getMetrics').and.returnValue(of(response));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.getMetrics).toHaveBeenCalled();
                expect(comp.servicesStats).toEqual({ service: 'test' });
                expect(comp.cachesStats).toEqual({ jcache: { name: 17, value: 2 } });
            });
        });

        describe('isNan', () => {
            it('should return if a variable is NaN', () => {
                expect(comp.filterNaN(1)).toBe(1);
                expect(comp.filterNaN('test')).toBe(0);
            });
        });
    });
});
