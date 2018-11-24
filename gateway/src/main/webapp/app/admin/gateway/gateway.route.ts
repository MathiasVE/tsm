import { Route } from '@angular/router';

import { TsmjhiGatewayComponent } from './gateway.component';

export const gatewayRoute: Route = {
    path: 'gateway',
    component: TsmjhiGatewayComponent,
    data: {
        pageTitle: 'gateway.title'
    }
};
