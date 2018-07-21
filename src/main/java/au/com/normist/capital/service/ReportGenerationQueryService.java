package au.com.normist.capital.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import au.com.normist.capital.domain.ReportGeneration;
import au.com.normist.capital.domain.*; // for static metamodels
import au.com.normist.capital.repository.ReportGenerationRepository;
import au.com.normist.capital.service.dto.ReportGenerationCriteria;

import au.com.normist.capital.service.dto.ReportGenerationDTO;
import au.com.normist.capital.service.mapper.ReportGenerationMapper;

/**
 * Service for executing complex queries for ReportGeneration entities in the database.
 * The main input is a {@link ReportGenerationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportGenerationDTO} or a {@link Page} of {@link ReportGenerationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportGenerationQueryService extends QueryService<ReportGeneration> {

    private final Logger log = LoggerFactory.getLogger(ReportGenerationQueryService.class);

    private final ReportGenerationRepository reportGenerationRepository;

    private final ReportGenerationMapper reportGenerationMapper;

    public ReportGenerationQueryService(ReportGenerationRepository reportGenerationRepository, ReportGenerationMapper reportGenerationMapper) {
        this.reportGenerationRepository = reportGenerationRepository;
        this.reportGenerationMapper = reportGenerationMapper;
    }

    /**
     * Return a {@link List} of {@link ReportGenerationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportGenerationDTO> findByCriteria(ReportGenerationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReportGeneration> specification = createSpecification(criteria);
        return reportGenerationMapper.toDto(reportGenerationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportGenerationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportGenerationDTO> findByCriteria(ReportGenerationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReportGeneration> specification = createSpecification(criteria);
        return reportGenerationRepository.findAll(specification, page)
            .map(reportGenerationMapper::toDto);
    }

    /**
     * Function to convert ReportGenerationCriteria to a {@link Specification}
     */
    private Specification<ReportGeneration> createSpecification(ReportGenerationCriteria criteria) {
        Specification<ReportGeneration> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReportGeneration_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ReportGeneration_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ReportGeneration_.status));
            }
            if (criteria.getFileType() != null) {
                specification = specification.and(buildSpecification(criteria.getFileType(), ReportGeneration_.fileType));
            }
            if (criteria.getFullPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullPath(), ReportGeneration_.fullPath));
            }
            if (criteria.getIsLocked() != null) {
                specification = specification.and(buildSpecification(criteria.getIsLocked(), ReportGeneration_.isLocked));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), ReportGeneration_.creationDate));
            }
            if (criteria.getCreator() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreator(), ReportGeneration_.creator));
            }
            if (criteria.getStartedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartedOn(), ReportGeneration_.startedOn));
            }
            if (criteria.getCompletionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompletionDate(), ReportGeneration_.completionDate));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), ReportGeneration_.comment));
            }
            if (criteria.getReportId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getReportId(), ReportGeneration_.report, Report_.id));
            }
        }
        return specification;
    }

}
