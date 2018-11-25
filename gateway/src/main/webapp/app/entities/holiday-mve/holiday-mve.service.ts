import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHolidayMve } from 'app/shared/model/holiday-mve.model';

type EntityResponseType = HttpResponse<IHolidayMve>;
type EntityArrayResponseType = HttpResponse<IHolidayMve[]>;

@Injectable({ providedIn: 'root' })
export class HolidayMveService {
    public resourceUrl = SERVER_API_URL + 'tsm/api/holidays';

    constructor(private http: HttpClient) {}

    create(holiday: IHolidayMve): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(holiday);
        return this.http
            .post<IHolidayMve>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(holiday: IHolidayMve): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(holiday);
        return this.http
            .put<IHolidayMve>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHolidayMve>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHolidayMve[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(holiday: IHolidayMve): IHolidayMve {
        const copy: IHolidayMve = Object.assign({}, holiday, {
            date: holiday.date != null && holiday.date.isValid() ? holiday.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((holiday: IHolidayMve) => {
                holiday.date = holiday.date != null ? moment(holiday.date) : null;
            });
        }
        return res;
    }
}
