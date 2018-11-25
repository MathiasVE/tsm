package be.mve.tsm.api.timesheet.data;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimesheetLabel implements Comparable<TimesheetLabel> {
    private String label;
    private TimesheetLabel parentLabel;

    public TimesheetLabel(String label) {
        this.label = label;
    }

    public TimesheetLabel(String label, TimesheetLabel parentLabel) {
        this.label = label;
        this.parentLabel = parentLabel;
    }

    public String getLabel() {
        return label;
    }

    private static Pattern BRACKET_PATTERN = Pattern.compile("(.*)\\[([^\\]]*)\\](.*)");

    public String getLabelBrackedFiltered() {
        Matcher bracketMatcher = BRACKET_PATTERN.matcher(label);
        if (bracketMatcher.matches()) {
            String preBrackets = bracketMatcher.group(1);
            String postBrackets = bracketMatcher.group(3);
            return preBrackets + postBrackets;
        }
        return label;
    }


    public TimesheetLabel getParentLabel() {
        return parentLabel;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TimesheetLabel && this.compareTo((TimesheetLabel) obj) == 0;
    }

    @Override
    public int compareTo(TimesheetLabel o) {
        return new CompareToBuilder()
            .append(this.getLevel(), o.getLevel())
            .append(this.parentLabel, o.parentLabel)
            .append(this.label, o.label)
            .toComparison();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(97, 107).
            append(getLevel()).
            append(label).
            append(parentLabel).
            toHashCode();
    }


    public int getLevel() {
        if (getParentLabel() != null) {
            return getParentLabel().getLevel() + 1;
        }
        return 1;
    }
}
