import { Route } from '@angular/router';

import { TsmjhiConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'tsmjhi-configuration',
    component: TsmjhiConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
