export interface ITimesheetLabelMve {
    id?: number;
    label?: string;
}

export class TimesheetLabelMve implements ITimesheetLabelMve {
    constructor(public id?: number, public label?: string) {}
}
