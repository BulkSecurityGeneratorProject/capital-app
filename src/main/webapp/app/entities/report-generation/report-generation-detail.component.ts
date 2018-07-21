import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportGeneration } from 'app/shared/model/report-generation.model';

@Component({
    selector: 'jhi-report-generation-detail',
    templateUrl: './report-generation-detail.component.html'
})
export class ReportGenerationDetailComponent implements OnInit {
    reportGeneration: IReportGeneration;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reportGeneration }) => {
            this.reportGeneration = reportGeneration;
        });
    }

    previousState() {
        window.history.back();
    }
}
