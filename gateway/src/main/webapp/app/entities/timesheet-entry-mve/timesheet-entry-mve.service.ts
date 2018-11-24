import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITimesheetEntryMve } from 'app/shared/model/timesheet-entry-mve.model';

type EntityResponseType = HttpResponse<ITimesheetEntryMve>;
type EntityArrayResponseType = HttpResponse<ITimesheetEntryMve[]>;

@Injectable({ providedIn: 'root' })
export class TimesheetEntryMveService {
    public resourceUrl = SERVER_API_URL + 'api/timesheet-entries';

    constructor(private http: HttpClient) {}

    create(timesheetEntry: ITimesheetEntryMve): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(timesheetEntry);
        return this.http
            .post<ITimesheetEntryMve>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(timesheetEntry: ITimesheetEntryMve): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(timesheetEntry);
        return this.http
            .put<ITimesheetEntryMve>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITimesheetEntryMve>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITimesheetEntryMve[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(timesheetEntry: ITimesheetEntryMve): ITimesheetEntryMve {
        const copy: ITimesheetEntryMve = Object.assign({}, timesheetEntry, {
            date: timesheetEntry.date != null && timesheetEntry.date.isValid() ? timesheetEntry.date.format(DATE_FORMAT) : null,
            from: timesheetEntry.from != null && timesheetEntry.from.isValid() ? timesheetEntry.from.toJSON() : null,
            until: timesheetEntry.until != null && timesheetEntry.until.isValid() ? timesheetEntry.until.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
            res.body.from = res.body.from != null ? moment(res.body.from) : null;
            res.body.until = res.body.until != null ? moment(res.body.until) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((timesheetEntry: ITimesheetEntryMve) => {
                timesheetEntry.date = timesheetEntry.date != null ? moment(timesheetEntry.date) : null;
                timesheetEntry.from = timesheetEntry.from != null ? moment(timesheetEntry.from) : null;
                timesheetEntry.until = timesheetEntry.until != null ? moment(timesheetEntry.until) : null;
            });
        }
        return res;
    }
}
