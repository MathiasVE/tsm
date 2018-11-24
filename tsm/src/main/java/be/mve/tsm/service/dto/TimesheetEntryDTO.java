package be.mve.tsm.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TimesheetEntry entity.
 */
public class TimesheetEntryDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Instant from;

    private Instant until;

    private String remark;

    private Long labelsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getFrom() {
        return from;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getUntil() {
        return until;
    }

    public void setUntil(Instant until) {
        this.until = until;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getLabelsId() {
        return labelsId;
    }

    public void setLabelsId(Long timesheetLabelId) {
        this.labelsId = timesheetLabelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimesheetEntryDTO timesheetEntryDTO = (TimesheetEntryDTO) o;
        if (timesheetEntryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timesheetEntryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimesheetEntryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", from='" + getFrom() + "'" +
            ", until='" + getUntil() + "'" +
            ", remark='" + getRemark() + "'" +
            ", labels=" + getLabelsId() +
            "}";
    }
}
