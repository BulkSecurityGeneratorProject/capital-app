package au.com.normist.capital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import au.com.normist.capital.domain.enumeration.ReportGenerationStatus;

import au.com.normist.capital.domain.enumeration.ReportFileType;

/**
 * A ReportGeneration.
 */
@Entity
@Table(name = "report_generation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportGeneration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportGenerationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private ReportFileType fileType;

    @Column(name = "full_path")
    private String fullPath;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "creator")
    private String creator;

    @Column(name = "started_on")
    private Instant startedOn;

    @Column(name = "completion_date")
    private Instant completionDate;

    @Column(name = "jhi_comment")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Report report;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ReportGeneration description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportGenerationStatus getStatus() {
        return status;
    }

    public ReportGeneration status(ReportGenerationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ReportGenerationStatus status) {
        this.status = status;
    }

    public ReportFileType getFileType() {
        return fileType;
    }

    public ReportGeneration fileType(ReportFileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(ReportFileType fileType) {
        this.fileType = fileType;
    }

    public String getFullPath() {
        return fullPath;
    }

    public ReportGeneration fullPath(String fullPath) {
        this.fullPath = fullPath;
        return this;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Boolean isIsLocked() {
        return isLocked;
    }

    public ReportGeneration isLocked(Boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public ReportGeneration creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public ReportGeneration creator(String creator) {
        this.creator = creator;
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Instant getStartedOn() {
        return startedOn;
    }

    public ReportGeneration startedOn(Instant startedOn) {
        this.startedOn = startedOn;
        return this;
    }

    public void setStartedOn(Instant startedOn) {
        this.startedOn = startedOn;
    }

    public Instant getCompletionDate() {
        return completionDate;
    }

    public ReportGeneration completionDate(Instant completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public String getComment() {
        return comment;
    }

    public ReportGeneration comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Report getReport() {
        return report;
    }

    public ReportGeneration report(Report report) {
        this.report = report;
        return this;
    }

    public void setReport(Report report) {
        this.report = report;
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
        ReportGeneration reportGeneration = (ReportGeneration) o;
        if (reportGeneration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportGeneration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportGeneration{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fullPath='" + getFullPath() + "'" +
            ", isLocked='" + isIsLocked() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", creator='" + getCreator() + "'" +
            ", startedOn='" + getStartedOn() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
