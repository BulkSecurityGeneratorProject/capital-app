import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IReportGeneration } from 'app/shared/model/report-generation.model';
import { ReportGenerationService } from './report-generation.service';
import { IReport } from 'app/shared/model/report.model';
import { ReportService } from 'app/entities/report';

@Component({
    selector: 'jhi-report-generation-update',
    templateUrl: './report-generation-update.component.html'
})
export class ReportGenerationUpdateComponent implements OnInit {
    private _reportGeneration: IReportGeneration;
    isSaving: boolean;

    reports: IReport[];
    creationDate: string;
    startedOn: string;
    completionDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private reportGenerationService: ReportGenerationService,
        private reportService: ReportService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reportGeneration }) => {
            this.reportGeneration = reportGeneration;
        });
        this.reportService.query().subscribe(
            (res: HttpResponse<IReport[]>) => {
                this.reports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.reportGeneration.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        this.reportGeneration.startedOn = moment(this.startedOn, DATE_TIME_FORMAT);
        this.reportGeneration.completionDate = moment(this.completionDate, DATE_TIME_FORMAT);
        if (this.reportGeneration.id !== undefined) {
            this.subscribeToSaveResponse(this.reportGenerationService.update(this.reportGeneration));
        } else {
            this.subscribeToSaveResponse(this.reportGenerationService.create(this.reportGeneration));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReportGeneration>>) {
        result.subscribe((res: HttpResponse<IReportGeneration>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackReportById(index: number, item: IReport) {
        return item.id;
    }
    get reportGeneration() {
        return this._reportGeneration;
    }

    set reportGeneration(reportGeneration: IReportGeneration) {
        this._reportGeneration = reportGeneration;
        this.creationDate = moment(reportGeneration.creationDate).format(DATE_TIME_FORMAT);
        this.startedOn = moment(reportGeneration.startedOn).format(DATE_TIME_FORMAT);
        this.completionDate = moment(reportGeneration.completionDate).format(DATE_TIME_FORMAT);
    }
}
