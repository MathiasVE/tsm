package be.mve.tsm.api.timesheet.data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class Timesheet {
    private Collection<TimesheetEntry> timesheetEntries;

    public Timesheet(Collection<TimesheetEntry> timesheetEntries) {
        this.timesheetEntries = timesheetEntries;
    }

    public LocalDate getStartDate() {
        LocalDate startDate = null;
        for (TimesheetEntry entry : timesheetEntries) {
            if (startDate == null) {
                startDate = entry.getDate();
            } else if (startDate.isAfter(entry.getDate())) {
                startDate = entry.getDate();
            }
        }
        return startDate;
    }

    public LocalDate getEndDate() {
        LocalDate endDate = null;
        for (TimesheetEntry entry : timesheetEntries) {
            if (endDate == null) {
                endDate = entry.getDate();
            } else if (endDate.isBefore(entry.getDate())) {
                endDate = entry.getDate();
            }
        }
        return endDate;
    }

    public Collection<TimesheetEntry> getTimesheetEntries() {
        return Collections.unmodifiableCollection(timesheetEntries);
    }

    public boolean hasEntriesForDate(LocalDate currentDate) {
        for (TimesheetEntry timesheetEntry : timesheetEntries) {
            if (timesheetEntry.getDate().equals(currentDate)) {
                return true;
            }
        }
        return false;
    }
}
