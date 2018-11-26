import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { GatewaySharedLibsModule, GatewaySharedCommonModule, TsmjhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [GatewaySharedLibsModule, GatewaySharedCommonModule],
    declarations: [TsmjhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [TsmjhiLoginModalComponent],
    exports: [GatewaySharedCommonModule, TsmjhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewaySharedModule {
    static forRoot() {
        return {
            ngModule: GatewaySharedModule
        };
    }
}
