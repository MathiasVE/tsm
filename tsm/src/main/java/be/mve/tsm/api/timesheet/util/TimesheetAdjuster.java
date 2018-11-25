package be.mve.tsm.api.timesheet.util;

import be.mve.tsm.api.timesheet.data.Timesheet;
import be.mve.tsm.api.timesheet.data.TimesheetEntry;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class TimesheetAdjuster {

    public static Timesheet adjustTimesheet(
        Timesheet timesheet,
        LocalTime workFrom,
        HashMap<DayOfWeek, Integer> idealMinutesForDay,
        String customerLabel) {
        ArrayList<TimesheetEntry> newTimesheetEntries = new ArrayList<>();

        LocalDate currentDate = timesheet.getStartDate();

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeekNumber = currentDate.get(weekFields.weekOfWeekBasedYear());
        int currentMonthNumber = currentDate.getMonthValue();
        List<TimesheetEntry> timesheetEntriesInAdjustablePeriod = new ArrayList<>();
        while (currentDate.isBefore(timesheet.getEndDate()) || currentDate.isEqual(timesheet.getEndDate())) {
            for (TimesheetEntry timesheetEntry : timesheet.getTimesheetEntries()) {
                if (currentDate.isEqual(timesheetEntry.getDate())) {
                    if (isConsideredNormalWorkEntry(timesheetEntry, workFrom, idealMinutesForDay.get(timesheetEntry.getDate().getDayOfWeek()))) {
                        timesheetEntriesInAdjustablePeriod.add(timesheetEntry);
                    }
                }
            }
            currentDate = currentDate.plus(Period.ofDays(1));
            if (isEndOfPeriod(currentWeekNumber, currentMonthNumber, timesheet.getEndDate(), currentDate)) {
                newTimesheetEntries.addAll(getAdjustedTimesheetEntries(timesheetEntriesInAdjustablePeriod, idealMinutesForDay, customerLabel));
                timesheetEntriesInAdjustablePeriod.clear();
                currentWeekNumber = currentDate.get(weekFields.weekOfWeekBasedYear());
                currentMonthNumber = currentDate.getMonthValue();
            }
        }

        return new Timesheet(newTimesheetEntries);
    }

    public static boolean isEndOfPeriod(int weekNumber, int monthNumber, LocalDate endDate, LocalDate checkDate) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return weekNumber != checkDate.get(weekFields.weekOfYear()) || checkDate.isAfter(endDate) || isEndOfMonth(monthNumber, checkDate);
    }

    public static boolean isEndOfWeek(int weekNumber, LocalDate endDate, LocalDate checkDate) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return weekNumber != checkDate.get(weekFields.weekOfYear()) || checkDate.isAfter(endDate);
    }

    public static boolean isEndOfMonth(int monthNumber, LocalDate checkDate) {
        return monthNumber != checkDate.getMonthValue();
    }

    private static Collection<? extends TimesheetEntry> getAdjustedTimesheetEntries(List<TimesheetEntry> timesheetEntriesInAdjustablePeriod, HashMap<DayOfWeek, Integer> idealMinutesForDay, String customerLabel) {
        if (timesheetEntriesInAdjustablePeriod.isEmpty()) {
            return new ArrayList<>();
        }
        double adjustFactor = 100;
        double fixedMinutes = 0;
        double idealMinutesForPeriod = 0;
        HashMap<LocalDate, Duration> dailyDuration = new HashMap<>();
        Duration actualFlexibleDurationForPeriod = Duration.ZERO;
        LocalDate currentDay = timesheetEntriesInAdjustablePeriod.iterator().next().getDate();
        idealMinutesForPeriod += idealMinutesForDay.get(currentDay.getDayOfWeek());
        dailyDuration.put(currentDay, Duration.ZERO);
        for (TimesheetEntry timesheetEntry : timesheetEntriesInAdjustablePeriod) {
            if (!timesheetEntry.getDate().equals(currentDay)) {
                currentDay = timesheetEntry.getDate();
                dailyDuration.put(currentDay, Duration.ZERO);
                idealMinutesForPeriod += idealMinutesForDay.get(currentDay.getDayOfWeek());
            }
            if (isNotForCustomerDuration(timesheetEntry, customerLabel)) {
                fixedMinutes += timesheetEntry.getRoundedDuration().toMinutes();
                actualFlexibleDurationForPeriod = actualFlexibleDurationForPeriod.plus(Duration.ofMinutes(timesheetEntry.getRoundedDuration().toMinutes()));
                dailyDuration.put(currentDay, dailyDuration.get(currentDay).plus(Duration.ofMinutes(timesheetEntry.getRoundedDuration().toMinutes())));
            } else {
                actualFlexibleDurationForPeriod = actualFlexibleDurationForPeriod.plus(timesheetEntry.getDuration());
                dailyDuration.put(currentDay, dailyDuration.get(currentDay).plus(timesheetEntry.getDuration()));
            }
        }

        if (idealMinutesForPeriod - fixedMinutes != 0) {
            adjustFactor = (((double) actualFlexibleDurationForPeriod.toMinutes()) - fixedMinutes) * 100 / (idealMinutesForPeriod - fixedMinutes);
        }

        HashMap<TimesheetEntry, Long> adjustedDurations = new HashMap<>();
        for (TimesheetEntry timesheetEntry : timesheetEntriesInAdjustablePeriod) {
            long adjustedTimesheetDurationInMinutes = timesheetEntry.getRoundedDuration().toMinutes();
            if (!isNotForCustomerDuration(timesheetEntry, customerLabel)) {
                adjustedTimesheetDurationInMinutes = (long) (timesheetEntry.getDuration().toMinutes() * 100 / adjustFactor);
            }
            idealMinutesForPeriod -= adjustedTimesheetDurationInMinutes;
            adjustedDurations.put(timesheetEntry, adjustedTimesheetDurationInMinutes);
        }
        Iterator<TimesheetEntry> iterator = adjustedDurations.keySet().iterator();
        boolean isAlsoForCustomer = false;
        for (TimesheetEntry timesheetEntry : adjustedDurations.keySet()) {
            if (!isNotForCustomerDuration(timesheetEntry, customerLabel)) {
                isAlsoForCustomer = true;
                break;
            }
        }
        while (iterator.hasNext() && idealMinutesForPeriod != 0) {
            TimesheetEntry next = iterator.next();
            if (!isAlsoForCustomer || !isNotForCustomerDuration(next, customerLabel)) {
                if (adjustedDurations.get(next) + (idealMinutesForPeriod > 0 ? 1 : -1) > 0) {
                    adjustedDurations.put(next, adjustedDurations.get(next) + (idealMinutesForPeriod > 0 ? 1 : -1));

                    if (idealMinutesForPeriod > 0) {
                        idealMinutesForPeriod--;
                    } else {
                        idealMinutesForPeriod++;
                    }
                }
            }
            if (!iterator.hasNext()) {
                iterator = adjustedDurations.keySet().iterator();
            }
        }

        ArrayList<TimesheetEntry> adjustedEntries = new ArrayList<>();
        for (LocalDate day : dailyDuration.keySet()) {
            Collection<? extends TimesheetEntry> adjustedTimesheetEntriesForDay = getAdjustedTimesheetEntriesForDay(timesheetEntriesInAdjustablePeriod, day, adjustedDurations);
            adjustedEntries.addAll(adjustedTimesheetEntriesForDay);
        }

        return adjustedEntries;
    }

    public static long calculateDuration(Collection<? extends TimesheetEntry> entries) {
        long sum = 0l;
        for (TimesheetEntry entry : entries) {
            sum += entry.getDuration().toMinutes();
        }
        return sum;
    }

    private static Collection<? extends TimesheetEntry> getAdjustedTimesheetEntriesForDay(List<TimesheetEntry> timesheetEntriesToAdjust, LocalDate day, HashMap<TimesheetEntry, Long> adjustedDurations) {
        ArrayList<TimesheetEntry> timesheetEntries = new ArrayList<>();
        Collections.sort(timesheetEntriesToAdjust);
        LocalTime startPoint = null;
        LocalTime lastUntil = null;
        for (TimesheetEntry timesheetEntry : timesheetEntriesToAdjust) {
            if (timesheetEntry.getDate().equals(day)) {
                if (startPoint == null) {
                    startPoint = timesheetEntry.getFrom();
                }
                if (lastUntil != null && Duration.between(lastUntil, timesheetEntry.getFrom()).abs().toMinutes() >= 1) {
                    startPoint = startPoint.plus(Duration.between(lastUntil, timesheetEntry.getFrom()).abs());
                }
                LocalTime newUntil = startPoint.plusMinutes(adjustedDurations.get(timesheetEntry));
                lastUntil = timesheetEntry.getUntil();
                if (newUntil.isBefore(startPoint)) {
                    newUntil = startPoint;
                    newUntil.plusMinutes(1);
                }
                timesheetEntries.add(new TimesheetEntry(timesheetEntry.getLabels(), timesheetEntry.getDate(), startPoint, newUntil, timesheetEntry.getNotitie()));
                startPoint = newUntil;
            }
        }
        return timesheetEntries;
    }

    private static boolean isNotForCustomerDuration(TimesheetEntry timesheetEntry, String customerLabel) {
        return !timesheetEntry.getLabels().contains(customerLabel);
    }

    public static boolean isConsideredNormalWorkEntry(TimesheetEntry timesheetEntry, LocalTime workFrom, int idealMinutesOfWork) {
        return isConsideredNormalWorkEntry(timesheetEntry.getFrom(), timesheetEntry.getUntil(), workFrom, idealMinutesOfWork);
    }

    public static boolean isConsideredNormalWorkEntry(LocalTime realWorkFrom, LocalTime realWorkUntil, LocalTime workFrom, int idealMinutesOfWork) {
        if (idealMinutesOfWork == 0) {
            return false;
        }
        idealMinutesOfWork += 60; // always count an extra hour of break
        return idealMinutesOfWork != 0 && !realWorkFrom.isAfter(workFrom.plus(idealMinutesOfWork, ChronoUnit.MINUTES)) &&
            !realWorkUntil.isBefore(workFrom.minus(1, ChronoUnit.HOURS));
    }
}
