import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CapitalappReportModule } from './report/report.module';
import { CapitalappReportGenerationModule } from './report-generation/report-generation.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CapitalappReportModule,
        CapitalappReportGenerationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapitalappEntityModule {}
