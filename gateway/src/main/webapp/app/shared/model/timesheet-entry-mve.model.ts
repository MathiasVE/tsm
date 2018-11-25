import { Moment } from 'moment';

export interface ITimesheetEntryMve {
    id?: number;
    date?: Moment;
    from?: Moment;
    until?: Moment;
    remark?: string;
    labelsId?: number;
    timesheetId?: number;
}

export class TimesheetEntryMve implements ITimesheetEntryMve {
    constructor(
        public id?: number,
        public date?: Moment,
        public from?: Moment,
        public until?: Moment,
        public remark?: string,
        public labelsId?: number,
        public timesheetId?: number
    ) {}
}
