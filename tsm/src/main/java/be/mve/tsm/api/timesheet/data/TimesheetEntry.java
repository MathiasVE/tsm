package be.mve.tsm.api.timesheet.data;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

public class TimesheetEntry implements Comparable<TimesheetEntry> {
    private Collection<String> labels;
    private LocalDate date;
    private LocalTime from;
    private LocalTime until;
    private String notitie;

    public TimesheetEntry(Collection<String> labels, LocalDate date, LocalTime from, LocalTime until, String notitie) {
        this(labels, date, from, until);
        this.notitie = notitie;
    }

    public TimesheetEntry(Collection<String> labels, LocalDate date, LocalTime from, LocalTime until) {
        this.labels = labels;
        this.date = date;
        if (from.isAfter(until)) {
            throw new IllegalArgumentException("From should be before until");
        }
        this.from = from;
        this.until = until;
        this.notitie = "";
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getUntil() {
        return until;
    }

    public Duration getDuration() {
        return Duration.between(from, until);
    }

    public String getNotitie() {
        return notitie;
    }

    public Duration getRoundedDuration() {
        long minutes = Duration.between(from, until).toMinutes();
        long hours = Duration.between(from, until).toHours();
        minutes -= hours * 60;
        if (minutes > 30) {
            hours++;
        }
        return Duration.ofHours(hours);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TimesheetEntry && this.compareTo((TimesheetEntry) obj) == 0;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
            append(date).
            append(from).
            append(until).
            append(labels).
            toHashCode();
    }

    public boolean hasLabel(String label) {
        return labels.contains(label);
    }

    public Collection<String> getLabels() {
        return Collections.unmodifiableCollection(labels);
    }

    public String getLabelString() {
        return labels.stream().reduce((s, s2) -> s + ", " + s2).orElse("");
    }

    @Override
    public int compareTo(TimesheetEntry o) {
        return new CompareToBuilder()
            .append(this.date, o.date)
            .append(this.from, o.from)
            .append(this.until, o.until)
            .append(this.labels, o.labels)
            .toComparison();
    }

    public String toString() {
        return DateTimeFormatter.ISO_DATE.format(date) + ": " + DateTimeFormatter.ISO_TIME.format(from) + " - " + DateTimeFormatter.ISO_TIME.format(until) + "(Duration " + getDuration().toMinutes() + "m)";
    }
}
