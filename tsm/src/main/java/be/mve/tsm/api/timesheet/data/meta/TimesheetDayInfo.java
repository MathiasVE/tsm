package be.mve.tsm.api.timesheet.data.meta;

import be.mve.tsm.api.timesheet.data.Timesheet;
import be.mve.tsm.api.timesheet.data.TimesheetEntry;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimesheetDayInfo {
    public LocalTime dayStart;
    public LocalTime dayEnd;
    Duration labeledMinutesDuration = Duration.ZERO;

    public long getBreakInMinutes() {
        int startMinutes = dayStart.getHour() * 60 + dayStart.getMinute();
        int endMinutes = dayEnd.getHour() * 60 + dayEnd.getMinute();
        return endMinutes - startMinutes - labeledMinutesDuration.toMinutes();
    }

    public static TimesheetDayInfo getDayInfo(Timesheet timesheet, LocalDate day, String filterLabel) {
        TimesheetDayInfo dayInfo = new TimesheetDayInfo();
        for (TimesheetEntry timesheetEntry : timesheet.getTimesheetEntries()) {
            if (timesheetEntry.getDate().isEqual(day) && (filterLabel == null || timesheetEntry.getLabels().contains(filterLabel))) {
                dayInfo.labeledMinutesDuration = dayInfo.labeledMinutesDuration.plus(timesheetEntry.getDuration());
                if (dayInfo.dayStart == null || timesheetEntry.getFrom().isBefore(dayInfo.dayStart)) {
                    dayInfo.dayStart = timesheetEntry.getFrom();
                }
                if (dayInfo.dayEnd == null || timesheetEntry.getUntil().isAfter(dayInfo.dayEnd)) {
                    dayInfo.dayEnd = timesheetEntry.getUntil();
                }
            }
        }
        return dayInfo;
    }

}
