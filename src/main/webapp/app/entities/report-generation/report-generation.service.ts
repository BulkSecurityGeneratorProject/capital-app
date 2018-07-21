import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReportGeneration } from 'app/shared/model/report-generation.model';

type EntityResponseType = HttpResponse<IReportGeneration>;
type EntityArrayResponseType = HttpResponse<IReportGeneration[]>;

@Injectable({ providedIn: 'root' })
export class ReportGenerationService {
    private resourceUrl = SERVER_API_URL + 'api/report-generations';

    constructor(private http: HttpClient) {}

    create(reportGeneration: IReportGeneration): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reportGeneration);
        return this.http
            .post<IReportGeneration>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(reportGeneration: IReportGeneration): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reportGeneration);
        return this.http
            .put<IReportGeneration>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReportGeneration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReportGeneration[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(reportGeneration: IReportGeneration): IReportGeneration {
        const copy: IReportGeneration = Object.assign({}, reportGeneration, {
            creationDate:
                reportGeneration.creationDate != null && reportGeneration.creationDate.isValid()
                    ? reportGeneration.creationDate.toJSON()
                    : null,
            startedOn:
                reportGeneration.startedOn != null && reportGeneration.startedOn.isValid() ? reportGeneration.startedOn.toJSON() : null,
            completionDate:
                reportGeneration.completionDate != null && reportGeneration.completionDate.isValid()
                    ? reportGeneration.completionDate.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
        res.body.startedOn = res.body.startedOn != null ? moment(res.body.startedOn) : null;
        res.body.completionDate = res.body.completionDate != null ? moment(res.body.completionDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((reportGeneration: IReportGeneration) => {
            reportGeneration.creationDate = reportGeneration.creationDate != null ? moment(reportGeneration.creationDate) : null;
            reportGeneration.startedOn = reportGeneration.startedOn != null ? moment(reportGeneration.startedOn) : null;
            reportGeneration.completionDate = reportGeneration.completionDate != null ? moment(reportGeneration.completionDate) : null;
        });
        return res;
    }
}
