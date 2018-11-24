import { Moment } from 'moment';

export interface IHolidayMve {
    id?: number;
    name?: string;
    date?: Moment;
}

export class HolidayMve implements IHolidayMve {
    constructor(public id?: number, public name?: string, public date?: Moment) {}
}
