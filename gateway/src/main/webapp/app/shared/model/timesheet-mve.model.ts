export interface ITimesheetMve {
    id?: number;
    user?: string;
    entriesId?: number;
}

export class TimesheetMve implements ITimesheetMve {
    constructor(public id?: number, public user?: string, public entriesId?: number) {}
}
