package be.mve.tsm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Timesheet entity.
 */
public class TimesheetDTO implements Serializable {

    private Long id;

    private String user;

    private Long entriesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getEntriesId() {
        return entriesId;
    }

    public void setEntriesId(Long timesheetEntryId) {
        this.entriesId = timesheetEntryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimesheetDTO timesheetDTO = (TimesheetDTO) o;
        if (timesheetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timesheetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimesheetDTO{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", entries=" + getEntriesId() +
            "}";
    }
}
