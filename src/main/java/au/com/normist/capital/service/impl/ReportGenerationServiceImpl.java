package au.com.normist.capital.service.impl;

import au.com.normist.capital.service.ReportGenerationService;
import au.com.normist.capital.domain.ReportGeneration;
import au.com.normist.capital.repository.ReportGenerationRepository;
import au.com.normist.capital.service.dto.ReportGenerationDTO;
import au.com.normist.capital.service.mapper.ReportGenerationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ReportGeneration.
 */
@Service
@Transactional
public class ReportGenerationServiceImpl implements ReportGenerationService {

    private final Logger log = LoggerFactory.getLogger(ReportGenerationServiceImpl.class);

    private final ReportGenerationRepository reportGenerationRepository;

    private final ReportGenerationMapper reportGenerationMapper;

    public ReportGenerationServiceImpl(ReportGenerationRepository reportGenerationRepository, ReportGenerationMapper reportGenerationMapper) {
        this.reportGenerationRepository = reportGenerationRepository;
        this.reportGenerationMapper = reportGenerationMapper;
    }

    /**
     * Save a reportGeneration.
     *
     * @param reportGenerationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReportGenerationDTO save(ReportGenerationDTO reportGenerationDTO) {
        log.debug("Request to save ReportGeneration : {}", reportGenerationDTO);
        ReportGeneration reportGeneration = reportGenerationMapper.toEntity(reportGenerationDTO);
        reportGeneration = reportGenerationRepository.save(reportGeneration);
        return reportGenerationMapper.toDto(reportGeneration);
    }

    /**
     * Get all the reportGenerations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReportGenerationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportGenerations");
        return reportGenerationRepository.findAll(pageable)
            .map(reportGenerationMapper::toDto);
    }


    /**
     * Get one reportGeneration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReportGenerationDTO> findOne(Long id) {
        log.debug("Request to get ReportGeneration : {}", id);
        return reportGenerationRepository.findById(id)
            .map(reportGenerationMapper::toDto);
    }

    /**
     * Delete the reportGeneration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportGeneration : {}", id);
        reportGenerationRepository.deleteById(id);
    }
}
