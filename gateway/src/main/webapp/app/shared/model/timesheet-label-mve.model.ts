export interface ITimesheetLabelMve {
    id?: number;
    label?: string;
    timesheetEntryId?: number;
}

export class TimesheetLabelMve implements ITimesheetLabelMve {
    constructor(public id?: number, public label?: string, public timesheetEntryId?: number) {}
}
