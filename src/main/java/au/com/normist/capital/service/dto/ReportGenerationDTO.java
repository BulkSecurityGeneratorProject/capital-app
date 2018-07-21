package au.com.normist.capital.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import au.com.normist.capital.domain.enumeration.ReportGenerationStatus;
import au.com.normist.capital.domain.enumeration.ReportFileType;

/**
 * A DTO for the ReportGeneration entity.
 */
public class ReportGenerationDTO implements Serializable {

    private Long id;

    private String description;

    private ReportGenerationStatus status;

    private ReportFileType fileType;

    private String fullPath;

    private Boolean isLocked;

    private Instant creationDate;

    private String creator;

    private Instant startedOn;

    private Instant completionDate;

    private String comment;

    private Long reportId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportGenerationStatus getStatus() {
        return status;
    }

    public void setStatus(ReportGenerationStatus status) {
        this.status = status;
    }

    public ReportFileType getFileType() {
        return fileType;
    }

    public void setFileType(ReportFileType fileType) {
        this.fileType = fileType;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Instant getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Instant startedOn) {
        this.startedOn = startedOn;
    }

    public Instant getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportGenerationDTO reportGenerationDTO = (ReportGenerationDTO) o;
        if (reportGenerationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportGenerationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportGenerationDTO{" +
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
            ", report=" + getReportId() +
            "}";
    }
}
