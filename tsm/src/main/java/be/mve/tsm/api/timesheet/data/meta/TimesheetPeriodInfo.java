package be.mve.tsm.api.timesheet.data.meta;

import be.mve.tsm.api.timesheet.data.Timesheet;
import be.mve.tsm.api.timesheet.data.TimesheetEntry;
import be.mve.tsm.api.timesheet.data.TimesheetLabel;
import be.mve.tsm.api.timesheet.util.TimesheetLabelFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;

public class TimesheetPeriodInfo {
    private HashMap<TimesheetLabel, TimesheetPeriodInfo> minutesPerLabel = new HashMap<>();
    private Duration totalMinutes = Duration.ZERO;

    public static TimesheetPeriodInfo getPeriodLabelInfo(TimesheetLabelFactory tslf, Timesheet timesheet, LocalDate from, LocalDate until, String filterLabel) {
        return getPeriodLabelInfo(tslf, timesheet, null, from, until, filterLabel);
    }

    public static TimesheetPeriodInfo getPeriodLabelInfo(TimesheetLabelFactory tslf, Timesheet timesheet, TimesheetLabel parentLabel, LocalDate from, LocalDate until, String filterLabel) {
        TimesheetPeriodInfo periodLabelInfo = new TimesheetPeriodInfo();
        for (TimesheetEntry timesheetEntry : timesheet.getTimesheetEntries()) {
            if ((timesheetEntry.getDate().isEqual(from) || timesheetEntry.getDate().isAfter(from)) &&
                (timesheetEntry.getDate().isBefore(until) || timesheetEntry.getDate().isEqual(until)) &&
                (filterLabel == null || timesheetEntry.getLabels().contains(filterLabel))) {
                if (parentLabel == null || tslf.getTimesheetLabel(timesheetEntry).contains(parentLabel)) {
                    periodLabelInfo.totalMinutes = periodLabelInfo.totalMinutes.plus(timesheetEntry.getDuration());
                }
            }
        }
        for (TimesheetLabel timesheetLabel : tslf.getAllTimesheetLabels()) {
            if ((parentLabel == null && timesheetLabel.getParentLabel() == null) ||
                (parentLabel != null && parentLabel.equals(timesheetLabel.getParentLabel()))) {
                TimesheetPeriodInfo subPeriodLabelInfo = getPeriodLabelInfo(tslf, timesheet, timesheetLabel, from, until, filterLabel);
                if (subPeriodLabelInfo.totalMinutes.toMinutes() > 0) {
                    periodLabelInfo.minutesPerLabel.put(timesheetLabel, subPeriodLabelInfo);
                }
            }
        }
        return periodLabelInfo;
    }

    public HashMap<TimesheetLabel, TimesheetPeriodInfo> getMinutesPerLabel() {
        return minutesPerLabel;
    }

    public Duration getTotalMinutes() {
        return totalMinutes;
    }
}
