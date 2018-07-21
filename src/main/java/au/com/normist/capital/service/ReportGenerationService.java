package au.com.normist.capital.service;

import au.com.normist.capital.service.dto.ReportGenerationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ReportGeneration.
 */
public interface ReportGenerationService {

    /**
     * Save a reportGeneration.
     *
     * @param reportGenerationDTO the entity to save
     * @return the persisted entity
     */
    ReportGenerationDTO save(ReportGenerationDTO reportGenerationDTO);

    /**
     * Get all the reportGenerations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReportGenerationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reportGeneration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReportGenerationDTO> findOne(Long id);

    /**
     * Delete the "id" reportGeneration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
