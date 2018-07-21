package au.com.normist.capital.web.rest;

import com.codahale.metrics.annotation.Timed;
import au.com.normist.capital.service.ReportGenerationService;
import au.com.normist.capital.web.rest.errors.BadRequestAlertException;
import au.com.normist.capital.web.rest.util.HeaderUtil;
import au.com.normist.capital.web.rest.util.PaginationUtil;
import au.com.normist.capital.service.dto.ReportGenerationDTO;
import au.com.normist.capital.service.dto.ReportGenerationCriteria;
import au.com.normist.capital.service.ReportGenerationQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReportGeneration.
 */
@RestController
@RequestMapping("/api")
public class ReportGenerationResource {

    private final Logger log = LoggerFactory.getLogger(ReportGenerationResource.class);

    private static final String ENTITY_NAME = "reportGeneration";

    private final ReportGenerationService reportGenerationService;

    private final ReportGenerationQueryService reportGenerationQueryService;

    public ReportGenerationResource(ReportGenerationService reportGenerationService, ReportGenerationQueryService reportGenerationQueryService) {
        this.reportGenerationService = reportGenerationService;
        this.reportGenerationQueryService = reportGenerationQueryService;
    }

    /**
     * POST  /report-generations : Create a new reportGeneration.
     *
     * @param reportGenerationDTO the reportGenerationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reportGenerationDTO, or with status 400 (Bad Request) if the reportGeneration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/report-generations")
    @Timed
    public ResponseEntity<ReportGenerationDTO> createReportGeneration(@RequestBody ReportGenerationDTO reportGenerationDTO) throws URISyntaxException {
        log.debug("REST request to save ReportGeneration : {}", reportGenerationDTO);
        if (reportGenerationDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportGeneration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportGenerationDTO result = reportGenerationService.save(reportGenerationDTO);
        return ResponseEntity.created(new URI("/api/report-generations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /report-generations : Updates an existing reportGeneration.
     *
     * @param reportGenerationDTO the reportGenerationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reportGenerationDTO,
     * or with status 400 (Bad Request) if the reportGenerationDTO is not valid,
     * or with status 500 (Internal Server Error) if the reportGenerationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/report-generations")
    @Timed
    public ResponseEntity<ReportGenerationDTO> updateReportGeneration(@RequestBody ReportGenerationDTO reportGenerationDTO) throws URISyntaxException {
        log.debug("REST request to update ReportGeneration : {}", reportGenerationDTO);
        if (reportGenerationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportGenerationDTO result = reportGenerationService.save(reportGenerationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reportGenerationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /report-generations : get all the reportGenerations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of reportGenerations in body
     */
    @GetMapping("/report-generations")
    @Timed
    public ResponseEntity<List<ReportGenerationDTO>> getAllReportGenerations(ReportGenerationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReportGenerations by criteria: {}", criteria);
        Page<ReportGenerationDTO> page = reportGenerationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/report-generations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /report-generations/:id : get the "id" reportGeneration.
     *
     * @param id the id of the reportGenerationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reportGenerationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/report-generations/{id}")
    @Timed
    public ResponseEntity<ReportGenerationDTO> getReportGeneration(@PathVariable Long id) {
        log.debug("REST request to get ReportGeneration : {}", id);
        Optional<ReportGenerationDTO> reportGenerationDTO = reportGenerationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportGenerationDTO);
    }

    /**
     * DELETE  /report-generations/:id : delete the "id" reportGeneration.
     *
     * @param id the id of the reportGenerationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/report-generations/{id}")
    @Timed
    public ResponseEntity<Void> deleteReportGeneration(@PathVariable Long id) {
        log.debug("REST request to delete ReportGeneration : {}", id);
        reportGenerationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
