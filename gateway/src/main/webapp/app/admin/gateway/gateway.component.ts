import { Component, OnInit } from '@angular/core';

import { GatewayRoutesService } from './gateway-routes.service';
import { GatewayRoute } from './gateway-route.model';

@Component({
    selector: 'tsmjhi-gateway',
    templateUrl: './gateway.component.html',
    providers: [GatewayRoutesService]
})
export class TsmjhiGatewayComponent implements OnInit {
    gatewayRoutes: GatewayRoute[];
    updatingRoutes: Boolean;

    constructor(private gatewayRoutesService: GatewayRoutesService) {}

    ngOnInit() {
        this.refresh();
    }

    refresh() {
        this.updatingRoutes = true;
        this.gatewayRoutesService.findAll().subscribe(gatewayRoutes => {
            this.gatewayRoutes = gatewayRoutes;
            this.updatingRoutes = false;
        });
    }
}
