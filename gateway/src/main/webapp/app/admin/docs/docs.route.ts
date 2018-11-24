import { Route } from '@angular/router';

import { TsmjhiDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: TsmjhiDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
