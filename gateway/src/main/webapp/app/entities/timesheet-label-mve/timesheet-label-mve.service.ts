import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITimesheetLabelMve } from 'app/shared/model/timesheet-label-mve.model';

type EntityResponseType = HttpResponse<ITimesheetLabelMve>;
type EntityArrayResponseType = HttpResponse<ITimesheetLabelMve[]>;

@Injectable({ providedIn: 'root' })
export class TimesheetLabelMveService {
    public resourceUrl = SERVER_API_URL + 'api/timesheet-labels';

    constructor(private http: HttpClient) {}

    create(timesheetLabel: ITimesheetLabelMve): Observable<EntityResponseType> {
        return this.http.post<ITimesheetLabelMve>(this.resourceUrl, timesheetLabel, { observe: 'response' });
    }

    update(timesheetLabel: ITimesheetLabelMve): Observable<EntityResponseType> {
        return this.http.put<ITimesheetLabelMve>(this.resourceUrl, timesheetLabel, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITimesheetLabelMve>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITimesheetLabelMve[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
