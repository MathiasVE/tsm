package be.mve.tsm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TimesheetEntry.
 */
@Entity
@Table(name = "timesheet_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimesheetEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tsmjhi_date")
    private LocalDate date;

    @Column(name = "tsmjhi_from")
    private Instant from;

    @Column(name = "tsmjhi_until")
    private Instant until;

    @Column(name = "remark")
    private String remark;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TimesheetLabel labels;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public TimesheetEntry date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getFrom() {
        return from;
    }

    public TimesheetEntry from(Instant from) {
        this.from = from;
        return this;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getUntil() {
        return until;
    }

    public TimesheetEntry until(Instant until) {
        this.until = until;
        return this;
    }

    public void setUntil(Instant until) {
        this.until = until;
    }

    public String getRemark() {
        return remark;
    }

    public TimesheetEntry remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public TimesheetLabel getLabels() {
        return labels;
    }

    public TimesheetEntry labels(TimesheetLabel timesheetLabel) {
        this.labels = timesheetLabel;
        return this;
    }

    public void setLabels(TimesheetLabel timesheetLabel) {
        this.labels = timesheetLabel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimesheetEntry timesheetEntry = (TimesheetEntry) o;
        if (timesheetEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timesheetEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimesheetEntry{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", from='" + getFrom() + "'" +
            ", until='" + getUntil() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
