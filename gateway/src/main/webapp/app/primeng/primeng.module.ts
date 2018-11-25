
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayButtonDemoModule } from './buttons/button/buttondemo.module';
import { GatewaySplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { GatewayDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { GatewayConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { GatewayLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { GatewayTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { GatewayOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { GatewaySideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { GatewayKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { GatewayInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { GatewayInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { GatewayInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { GatewayCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { GatewayCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { GatewayChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { GatewayColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { GatewayInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { GatewayInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { GatewayPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { GatewayAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { GatewaySliderDemoModule } from './inputs/slider/sliderdemo.module';
import { GatewaySpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { GatewayRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { GatewaySelectDemoModule } from './inputs/select/selectdemo.module';
import { GatewaySelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { GatewayListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { GatewayRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { GatewayToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { GatewayEditorDemoModule } from './inputs/editor/editordemo.module';

import { GatewayGrowlDemoModule } from './messages/growl/growldemo.module';
import { GatewayMessagesDemoModule } from './messages/messages/messagesdemo.module';
/*import { GatewayToastDemoModule } from './messages/toast/toastdemo.module';*/
import { GatewayGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { GatewayFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { GatewayAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { GatewayPanelDemoModule } from './panel/panel/paneldemo.module';
import { GatewayTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { GatewayFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { GatewayToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { GatewayGridDemoModule } from './panel/grid/griddemo.module';
import { GatewayScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { GatewayCardDemoModule } from './panel/card/carddemo.module';

import { GatewayDataTableDemoModule } from './data/datatable/datatabledemo.module';
import { GatewayTableDemoModule } from './data/table/tabledemo.module';
import { GatewayDataGridDemoModule } from './data/datagrid/datagriddemo.module';
import { GatewayDataListDemoModule } from './data/datalist/datalistdemo.module';
import { GatewayDataScrollerDemoModule } from './data/datascroller/datascrollerdemo.module';
import { GatewayPickListDemoModule } from './data/picklist/picklistdemo.module';
import { GatewayOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { GatewayScheduleDemoModule } from './data/schedule/scheduledemo.module';
import { GatewayTreeDemoModule } from './data/tree/treedemo.module';
import { GatewayTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { GatewayPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { GatewayGmapDemoModule } from './data/gmap/gmapdemo.module';
import { GatewayOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { GatewayCarouselDemoModule } from './data/carousel/carouseldemo.module';
import { GatewayDataViewDemoModule } from './data/dataview/dataviewdemo.module';

import { GatewayBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { GatewayDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { GatewayLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { GatewayPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { GatewayPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { GatewayRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { GatewayDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { GatewayMenuDemoModule } from './menu/menu/menudemo.module';
import { GatewayContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { GatewayPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { GatewayStepsDemoModule } from './menu/steps/stepsdemo.module';
import { GatewayTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { GatewayBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { GatewayMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { GatewayMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { GatewaySlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { GatewayTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { GatewayBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { GatewayCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { GatewayDeferDemoModule } from './misc/defer/deferdemo.module';
import { GatewayInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { GatewayProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { GatewayRTLDemoModule } from './misc/rtl/rtldemo.module';
import { GatewayTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { GatewayValidationDemoModule } from './misc/validation/validationdemo.module';
import { GatewayProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        GatewayMenuDemoModule,
        GatewayContextMenuDemoModule,
        GatewayPanelMenuDemoModule,
        GatewayStepsDemoModule,
        GatewayTieredMenuDemoModule,
        GatewayBreadcrumbDemoModule,
        GatewayMegaMenuDemoModule,
        GatewayMenuBarDemoModule,
        GatewaySlideMenuDemoModule,
        GatewayTabMenuDemoModule,

        GatewayBlockUIDemoModule,
        GatewayCaptchaDemoModule,
        GatewayDeferDemoModule,
        GatewayInplaceDemoModule,
        GatewayProgressBarDemoModule,
        GatewayInputMaskDemoModule,
        GatewayRTLDemoModule,
        GatewayTerminalDemoModule,
        GatewayValidationDemoModule,

        GatewayButtonDemoModule,
        GatewaySplitbuttonDemoModule,

        GatewayInputTextDemoModule,
        GatewayInputTextAreaDemoModule,
        GatewayInputGroupDemoModule,
        GatewayCalendarDemoModule,
        GatewayChipsDemoModule,
        GatewayInputMaskDemoModule,
        GatewayInputSwitchDemoModule,
        GatewayPasswordIndicatorDemoModule,
        GatewayAutoCompleteDemoModule,
        GatewaySliderDemoModule,
        GatewaySpinnerDemoModule,
        GatewayRatingDemoModule,
        GatewaySelectDemoModule,
        GatewaySelectButtonDemoModule,
        GatewayListboxDemoModule,
        GatewayRadioButtonDemoModule,
        GatewayToggleButtonDemoModule,
        GatewayEditorDemoModule,
        GatewayColorPickerDemoModule,
        GatewayCheckboxDemoModule,
        GatewayKeyFilterDemoModule,

        GatewayGrowlDemoModule,
        GatewayMessagesDemoModule,
        /*GatewayToastDemoModule,*/
        GatewayGalleriaDemoModule,

        GatewayFileUploadDemoModule,

        GatewayAccordionDemoModule,
        GatewayPanelDemoModule,
        GatewayTabViewDemoModule,
        GatewayFieldsetDemoModule,
        GatewayToolbarDemoModule,
        GatewayGridDemoModule,
        GatewayScrollPanelDemoModule,
        GatewayCardDemoModule,

        GatewayBarchartDemoModule,
        GatewayDoughnutchartDemoModule,
        GatewayLinechartDemoModule,
        GatewayPiechartDemoModule,
        GatewayPolarareachartDemoModule,
        GatewayRadarchartDemoModule,

        GatewayDragDropDemoModule,

        GatewayDialogDemoModule,
        GatewayConfirmDialogDemoModule,
        GatewayLightboxDemoModule,
        GatewayTooltipDemoModule,
        GatewayOverlayPanelDemoModule,
        GatewaySideBarDemoModule,

        GatewayDataTableDemoModule,
        GatewayTableDemoModule,
        GatewayDataGridDemoModule,
        GatewayDataListDemoModule,
        GatewayDataViewDemoModule,
        GatewayDataScrollerDemoModule,
        GatewayScheduleDemoModule,
        GatewayOrderListDemoModule,
        GatewayPickListDemoModule,
        GatewayTreeDemoModule,
        GatewayTreeTableDemoModule,
        GatewayPaginatorDemoModule,
        GatewayOrgChartDemoModule,
        GatewayGmapDemoModule,
        GatewayCarouselDemoModule,
        GatewayProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayprimengModule {}
