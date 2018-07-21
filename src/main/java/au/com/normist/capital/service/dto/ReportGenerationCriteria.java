package au.com.normist.capital.service.dto;

import java.io.Serializable;
import au.com.normist.capital.domain.enumeration.ReportGenerationStatus;
import au.com.normist.capital.domain.enumeration.ReportFileType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the ReportGeneration entity. This class is used in ReportGenerationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /report-generations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReportGenerationCriteria implements Serializable {
    /**
     * Class for filtering ReportGenerationStatus
     */
    public static class ReportGenerationStatusFilter extends Filter<ReportGenerationStatus> {
    }

    /**
     * Class for filtering ReportFileType
     */
    public static class ReportFileTypeFilter extends Filter<ReportFileType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter description;

    private ReportGenerationStatusFilter status;

    private ReportFileTypeFilter fileType;

    private StringFilter fullPath;

    private BooleanFilter isLocked;

    private InstantFilter creationDate;

    private StringFilter creator;

    private InstantFilter startedOn;

    private InstantFilter completionDate;

    private StringFilter comment;

    private LongFilter reportId;

    public ReportGenerationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ReportGenerationStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ReportGenerationStatusFilter status) {
        this.status = status;
    }

    public ReportFileTypeFilter getFileType() {
        return fileType;
    }

    public void setFileType(ReportFileTypeFilter fileType) {
        this.fileType = fileType;
    }

    public StringFilter getFullPath() {
        return fullPath;
    }

    public void setFullPath(StringFilter fullPath) {
        this.fullPath = fullPath;
    }

    public BooleanFilter getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(BooleanFilter isLocked) {
        this.isLocked = isLocked;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public StringFilter getCreator() {
        return creator;
    }

    public void setCreator(StringFilter creator) {
        this.creator = creator;
    }

    public InstantFilter getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(InstantFilter startedOn) {
        this.startedOn = startedOn;
    }

    public InstantFilter getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(InstantFilter completionDate) {
        this.completionDate = completionDate;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public LongFilter getReportId() {
        return reportId;
    }

    public void setReportId(LongFilter reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "ReportGenerationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (fileType != null ? "fileType=" + fileType + ", " : "") +
                (fullPath != null ? "fullPath=" + fullPath + ", " : "") +
                (isLocked != null ? "isLocked=" + isLocked + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (creator != null ? "creator=" + creator + ", " : "") +
                (startedOn != null ? "startedOn=" + startedOn + ", " : "") +
                (completionDate != null ? "completionDate=" + completionDate + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (reportId != null ? "reportId=" + reportId + ", " : "") +
            "}";
    }

}
