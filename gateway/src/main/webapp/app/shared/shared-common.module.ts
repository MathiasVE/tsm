import { NgModule } from '@angular/core';

import { GatewaySharedLibsModule, FindLanguageFromKeyPipe, TsmjhiAlertComponent, TsmjhiAlertErrorComponent } from './';

@NgModule({
    imports: [GatewaySharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, TsmjhiAlertComponent, TsmjhiAlertErrorComponent],
    exports: [GatewaySharedLibsModule, FindLanguageFromKeyPipe, TsmjhiAlertComponent, TsmjhiAlertErrorComponent]
})
export class GatewaySharedCommonModule {}
