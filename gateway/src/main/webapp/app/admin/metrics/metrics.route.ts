import { Route } from '@angular/router';

import { TsmjhiMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'tsmjhi-metrics',
    component: TsmjhiMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
