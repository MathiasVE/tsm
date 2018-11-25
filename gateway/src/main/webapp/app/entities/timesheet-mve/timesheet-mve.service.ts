import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITimesheetMve } from 'app/shared/model/timesheet-mve.model';

type EntityResponseType = HttpResponse<ITimesheetMve>;
type EntityArrayResponseType = HttpResponse<ITimesheetMve[]>;

@Injectable({ providedIn: 'root' })
export class TimesheetMveService {
    public resourceUrl = SERVER_API_URL + 'api/timesheets';

    constructor(private http: HttpClient) {}

    create(timesheet: ITimesheetMve): Observable<EntityResponseType> {
        return this.http.post<ITimesheetMve>(this.resourceUrl, timesheet, { observe: 'response' });
    }

    update(timesheet: ITimesheetMve): Observable<EntityResponseType> {
        return this.http.put<ITimesheetMve>(this.resourceUrl, timesheet, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITimesheetMve>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITimesheetMve[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
