import { NgModule } from '@angular/core';

import { CapitalappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CapitalappSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CapitalappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CapitalappSharedCommonModule {}
