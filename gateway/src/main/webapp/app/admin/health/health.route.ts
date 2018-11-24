import { Route } from '@angular/router';

import { TsmjhiHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'tsmjhi-health',
    component: TsmjhiHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
