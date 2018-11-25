package be.mve.tsm.api.timesheet.util;

import be.mve.tsm.api.timesheet.data.Timesheet;
import be.mve.tsm.api.timesheet.data.TimesheetEntry;
import be.mve.tsm.api.timesheet.data.TimesheetLabel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TimesheetLabelFactory implements Serializable {
    private HashMap<String, TimesheetLabel> timesheetLabels;

    public TimesheetLabelFactory() {
        timesheetLabels = new HashMap<>();
    }

    public HashMap<String, TimesheetLabel> getTimesheetLabels() {
        return timesheetLabels;
    }

    public void setTimesheetLabels(HashMap<String, TimesheetLabel> timesheetLabels) {
        this.timesheetLabels = timesheetLabels;
    }

    public void updateLabels(Timesheet timesheet) {
        Set<String> allLabels = new HashSet<>();
        List<List<TimesheetLabel>> possibleLabelCombinations = new ArrayList<>();

        Collection<TimesheetEntry> timesheetEntries = timesheet.getTimesheetEntries();
        Iterator<TimesheetEntry> timesheetEntryIterator = timesheetEntries.iterator();
        for (int i = 0; i < timesheetEntries.size(); i++) {
            TimesheetEntry timesheetEntry = timesheetEntryIterator.next();
            if (!mappedLabelsAllready(timesheetLabels, timesheetEntry.getLabels())) {
                List<List<TimesheetLabel>> entryPossibleTimesheetLabels = getPossibleTimesheetLabels(timesheetEntry.getLabels(), timesheet.getTimesheetEntries());
                for (List<TimesheetLabel> possibleTimesheetLabels : entryPossibleTimesheetLabels) {
                    if (!containsCollection(possibleLabelCombinations, possibleTimesheetLabels)) {
                        possibleLabelCombinations.add(possibleTimesheetLabels);
                    }
                }
            }
            allLabels.add(timesheetEntry.getLabelString());
        }
        for (List<TimesheetLabel> possibleTimesheetLabels : possibleLabelCombinations) {
            for (TimesheetLabel timesheetLabel : possibleTimesheetLabels) {
                if (!timesheetLabels.containsKey(timesheetLabel.getLabel()) ||
                    timesheetLabels.get(timesheetLabel.getLabel()).getLevel() < timesheetLabel.getLevel()) {
                    timesheetLabels.put(timesheetLabel.getLabel(), timesheetLabel);
                }
            }
        }
        // TODO: Fix weird scenario with parent with only 1 child causing double combination parent-child
        // This is kind of a Schrodingers cat as we are not in a position to fully
        // measure the state both can equally be possible but we'll just take a pick :-)
        for (String label : timesheetLabels.keySet()) {
            TimesheetLabel timesheetLabel = timesheetLabels.get(label);
            for (String checkLabel : timesheetLabels.keySet()) {
                TimesheetLabel checkTimesheetLabel = timesheetLabels.get(checkLabel);
                if (timesheetLabel.getLevel() == checkTimesheetLabel.getLevel() &&
                    timesheetLabel.getParentLabel() != null &&
                    checkTimesheetLabel.getParentLabel() != null &&
                    timesheetLabel.getParentLabel().getLabel().equals(checkTimesheetLabel.getLabel()) &&
                    checkTimesheetLabel.getParentLabel().getLabel().equals(timesheetLabel.getLabel())) {
                    timesheetLabels.put(label, checkTimesheetLabel.getParentLabel());
                }
            }
        }
    }

    private boolean mappedLabelsAllready(HashMap<String, TimesheetLabel> timesheetLabels, Collection<String> labels) {
        for (String label : labels) {
            if (!timesheetLabels.containsKey(label)) {
                return false;
            }
        }
        return true;
    }

    public Collection<TimesheetLabel> getAllTimesheetLabels() {
        return timesheetLabels.values();
    }

    public TimesheetLabel getTimesheetLabel(String label) {
        return timesheetLabels.get(label);
    }

    public Collection<TimesheetLabel> getTimesheetLabel(TimesheetEntry timesheetEntry) {
        Collection<TimesheetLabel> timesheetLabelsForTimesheetEntry = new ArrayList<>();
        for (String label : timesheetEntry.getLabels()) {
            TimesheetLabel timesheetLabel = timesheetLabels.get(label);
            if (timesheetLabel != null) {
                timesheetLabelsForTimesheetEntry.add(timesheetLabel);
            }
        }
        return timesheetLabelsForTimesheetEntry;
    }

    private boolean areAllValidLabels(TimesheetEntry timesheetEntry, Collection<TimesheetLabel> timesheetLabels) {
        boolean isValid = true;
        for (TimesheetLabel timesheetLabel : timesheetLabels) {
            if (!isValidTimesheetLabel(timesheetLabel, timesheetEntry)) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private boolean isValidTimesheetLabel(TimesheetLabel timesheetLabel, TimesheetEntry timesheetEntry) {
        return isValidTimesheetLabel(timesheetLabel, timesheetEntry, false);
    }

    private boolean isValidTimesheetLabel(TimesheetLabel timesheetLabel, TimesheetEntry timesheetEntry, boolean shouldContain) {
        if (shouldContain && !timesheetEntry.getLabels().contains(timesheetLabel.getLabel())) {
            return false;
        }
        return timesheetLabel.getParentLabel() == null ||
            isValidTimesheetLabel(timesheetLabel.getParentLabel(), timesheetEntry, timesheetEntry.getLabels().contains(timesheetLabel.getLabel()));
    }

    private List<List<TimesheetLabel>> getPossibleTimesheetLabels(Collection<String> allLabels, Collection<TimesheetEntry> timesheetEntries) {
        if (allLabels.size() == 1) {
            ArrayList<List<TimesheetLabel>> singlePossibleLabel = new ArrayList<>();
            List<TimesheetLabel> timesheetLabels = new ArrayList<>();
            timesheetLabels.add(new TimesheetLabel(allLabels.iterator().next()));
            singlePossibleLabel.add(timesheetLabels);
            return singlePossibleLabel;
        }
        List<List<TimesheetLabel>> possibleTimesheetLabels = new ArrayList<>();
        List<TimesheetLabel> allUnique = new ArrayList<>();
        for (String label : allLabels) {
            allUnique.add(new TimesheetLabel(label));
            List<String> subSet = new ArrayList<>();
            subSet.addAll(allLabels);
            subSet.remove(label);
            List<List<TimesheetLabel>> subPossibleTimesheetLabels = getPossibleTimesheetLabels(subSet, timesheetEntries);
            for (List<TimesheetLabel> timesheetLabels : subPossibleTimesheetLabels) {
                List<TimesheetLabel> leafLinkedSubPossibleTimesheetLabels = new ArrayList<>();
                for (TimesheetLabel timesheetLabel : timesheetLabels) {
                    leafLinkedSubPossibleTimesheetLabels.add(new TimesheetLabel(label, timesheetLabel));
                }
                possibleTimesheetLabels.add(leafLinkedSubPossibleTimesheetLabels);
                timesheetLabels.add(new TimesheetLabel(label));
                possibleTimesheetLabels.add(timesheetLabels);
            }
        }
        possibleTimesheetLabels.add(allUnique);
        return verifyPossibleTimesheetLabels(possibleTimesheetLabels, timesheetEntries);
    }

    private List<List<TimesheetLabel>> verifyPossibleTimesheetLabels(List<List<TimesheetLabel>> possibleTimesheetLabelsCombination, Collection<TimesheetEntry> timesheetEntries) {
        List<List<TimesheetLabel>> remaining = new ArrayList<>();
        for (List<TimesheetLabel> possibleTimesheetLabels : possibleTimesheetLabelsCombination) {
            boolean mayAdd = true;
            for (TimesheetEntry timesheetEntry : timesheetEntries) {
                if (!areAllValidLabels(timesheetEntry, possibleTimesheetLabels)) {
                    mayAdd = false;
                }
            }
            if (mayAdd) {
                if (!containsCollection(remaining, possibleTimesheetLabels)) {
                    remaining.add(possibleTimesheetLabels);
                }
            }
        }
        return remaining;
    }

    private boolean containsCollection(List<List<TimesheetLabel>> remaining, List<TimesheetLabel> possibleTimesheetLabels) {
        for (List<TimesheetLabel> collection : remaining) {
            if (collection.size() == possibleTimesheetLabels.size()) {
                boolean differentTimesheetLabel = false;
                for (TimesheetLabel timesheetLabel : collection) {
                    boolean timesheetLabelFound = false;
                    for (TimesheetLabel checkTimesheetLabel : possibleTimesheetLabels) {
                        if (timesheetLabel.equals(checkTimesheetLabel)) {
                            timesheetLabelFound = true;
                            break;
                        }
                    }
                    if (!timesheetLabelFound) {
                        differentTimesheetLabel = true;
                        break;
                    }
                }
                if (!differentTimesheetLabel) {
                    return true;
                }
            }
        }
        return false;
    }

}
