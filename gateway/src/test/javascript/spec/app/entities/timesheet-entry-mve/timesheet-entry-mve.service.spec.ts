/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TimesheetEntryMveService } from 'app/entities/timesheet-entry-mve/timesheet-entry-mve.service';
import { ITimesheetEntryMve, TimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';

describe('Service Tests', () => {
    describe('TimesheetEntryMve Service', () => {
        let injector: TestBed;
        let service: TimesheetEntryMveService;
        let httpMock: HttpTestingController;
        let elemDefault: ITimesheetEntryMve;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TimesheetEntryMveService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new TimesheetEntryMve(0, currentDate, currentDate, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        date: currentDate.format(DATE_FORMAT),
                        from: currentDate.format(DATE_TIME_FORMAT),
                        until: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a TimesheetEntryMve', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        date: currentDate.format(DATE_FORMAT),
                        from: currentDate.format(DATE_TIME_FORMAT),
                        until: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date: currentDate,
                        from: currentDate,
                        until: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new TimesheetEntryMve(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a TimesheetEntryMve', async () => {
                const returnedFromService = Object.assign(
                    {
                        date: currentDate.format(DATE_FORMAT),
                        from: currentDate.format(DATE_TIME_FORMAT),
                        until: currentDate.format(DATE_TIME_FORMAT),
                        remark: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        date: currentDate,
                        from: currentDate,
                        until: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of TimesheetEntryMve', async () => {
                const returnedFromService = Object.assign(
                    {
                        date: currentDate.format(DATE_FORMAT),
                        from: currentDate.format(DATE_TIME_FORMAT),
                        until: currentDate.format(DATE_TIME_FORMAT),
                        remark: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date: currentDate,
                        from: currentDate,
                        until: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a TimesheetEntryMve', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
