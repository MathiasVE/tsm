package be.mve.tsm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TimesheetLabel entity.
 */
public class TimesheetLabelDTO implements Serializable {

    private Long id;

    private String label;

    private Long timesheetEntryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getTimesheetEntryId() {
        return timesheetEntryId;
    }

    public void setTimesheetEntryId(Long timesheetEntryId) {
        this.timesheetEntryId = timesheetEntryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimesheetLabelDTO timesheetLabelDTO = (TimesheetLabelDTO) o;
        if (timesheetLabelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timesheetLabelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimesheetLabelDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", timesheetEntry=" + getTimesheetEntryId() +
            "}";
    }
}
