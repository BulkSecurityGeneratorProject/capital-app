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

import au.com.normist.capital.domain.Report;
import au.com.normist.capital.domain.*; // for static metamodels
import au.com.normist.capital.repository.ReportRepository;
import au.com.normist.capital.service.dto.ReportCriteria;

import au.com.normist.capital.service.dto.ReportDTO;
import au.com.normist.capital.service.mapper.ReportMapper;

/**
 * Service for executing complex queries for Report entities in the database.
 * The main input is a {@link ReportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportDTO} or a {@link Page} of {@link ReportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportQueryService extends QueryService<Report> {

    private final Logger log = LoggerFactory.getLogger(ReportQueryService.class);

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    public ReportQueryService(ReportRepository reportRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    /**
     * Return a {@link List} of {@link ReportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportDTO> findByCriteria(ReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Report> specification = createSpecification(criteria);
        return reportMapper.toDto(reportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportDTO> findByCriteria(ReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Report> specification = createSpecification(criteria);
        return reportRepository.findAll(specification, page)
            .map(reportMapper::toDto);
    }

    /**
     * Function to convert ReportCriteria to a {@link Specification}
     */
    private Specification<Report> createSpecification(ReportCriteria criteria) {
        Specification<Report> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Report_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Report_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Report_.description));
            }
            if (criteria.getDisplayOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisplayOrder(), Report_.displayOrder));
            }
        }
        return specification;
    }

}
