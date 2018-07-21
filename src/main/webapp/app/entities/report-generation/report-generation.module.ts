import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapitalappSharedModule } from 'app/shared';
import {
    ReportGenerationComponent,
    ReportGenerationDetailComponent,
    ReportGenerationUpdateComponent,
    ReportGenerationDeletePopupComponent,
    ReportGenerationDeleteDialogComponent,
    reportGenerationRoute,
    reportGenerationPopupRoute
} from './';

const ENTITY_STATES = [...reportGenerationRoute, ...reportGenerationPopupRoute];

@NgModule({
    imports: [CapitalappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReportGenerationComponent,
        ReportGenerationDetailComponent,
        ReportGenerationUpdateComponent,
        ReportGenerationDeleteDialogComponent,
        ReportGenerationDeletePopupComponent
    ],
    entryComponents: [
        ReportGenerationComponent,
        ReportGenerationUpdateComponent,
        ReportGenerationDeleteDialogComponent,
        ReportGenerationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapitalappReportGenerationModule {}
