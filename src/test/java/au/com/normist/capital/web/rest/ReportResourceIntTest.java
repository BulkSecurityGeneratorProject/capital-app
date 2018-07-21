package au.com.normist.capital.web.rest;

import au.com.normist.capital.CapitalappApp;

import au.com.normist.capital.domain.Report;
import au.com.normist.capital.repository.ReportRepository;
import au.com.normist.capital.service.ReportService;
import au.com.normist.capital.service.dto.ReportDTO;
import au.com.normist.capital.service.mapper.ReportMapper;
import au.com.normist.capital.web.rest.errors.ExceptionTranslator;
import au.com.normist.capital.service.dto.ReportCriteria;
import au.com.normist.capital.service.ReportQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static au.com.normist.capital.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReportResource REST controller.
 *
 * @see ReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapitalappApp.class)
public class ReportResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;

    @Autowired
    private ReportRepository reportRepository;


    @Autowired
    private ReportMapper reportMapper;
    

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportQueryService reportQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReportMockMvc;

    private Report report;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportResource reportResource = new ReportResource(reportService, reportQueryService);
        this.restReportMockMvc = MockMvcBuilders.standaloneSetup(reportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Report createEntity(EntityManager em) {
        Report report = new Report()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .displayOrder(DEFAULT_DISPLAY_ORDER);
        return report;
    }

    @Before
    public void initTest() {
        report = createEntity(em);
    }

    @Test
    @Transactional
    public void createReport() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);
        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isCreated());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate + 1);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReport.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void createReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report with an existing ID
        report.setId(1L);
        ReportDTO reportDTO = reportMapper.toDto(report);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReports() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList
        restReportMockMvc.perform(get("/api/reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)));
    }
    

    @Test
    @Transactional
    public void getReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(report.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER));
    }

    @Test
    @Transactional
    public void getAllReportsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where name equals to DEFAULT_NAME
        defaultReportShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the reportList where name equals to UPDATED_NAME
        defaultReportShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReportsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReportShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the reportList where name equals to UPDATED_NAME
        defaultReportShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReportsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where name is not null
        defaultReportShouldBeFound("name.specified=true");

        // Get all the reportList where name is null
        defaultReportShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where description equals to DEFAULT_DESCRIPTION
        defaultReportShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the reportList where description equals to UPDATED_DESCRIPTION
        defaultReportShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReportsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReportShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the reportList where description equals to UPDATED_DESCRIPTION
        defaultReportShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReportsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where description is not null
        defaultReportShouldBeFound("description.specified=true");

        // Get all the reportList where description is null
        defaultReportShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportsByDisplayOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where displayOrder equals to DEFAULT_DISPLAY_ORDER
        defaultReportShouldBeFound("displayOrder.equals=" + DEFAULT_DISPLAY_ORDER);

        // Get all the reportList where displayOrder equals to UPDATED_DISPLAY_ORDER
        defaultReportShouldNotBeFound("displayOrder.equals=" + UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllReportsByDisplayOrderIsInShouldWork() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where displayOrder in DEFAULT_DISPLAY_ORDER or UPDATED_DISPLAY_ORDER
        defaultReportShouldBeFound("displayOrder.in=" + DEFAULT_DISPLAY_ORDER + "," + UPDATED_DISPLAY_ORDER);

        // Get all the reportList where displayOrder equals to UPDATED_DISPLAY_ORDER
        defaultReportShouldNotBeFound("displayOrder.in=" + UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllReportsByDisplayOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where displayOrder is not null
        defaultReportShouldBeFound("displayOrder.specified=true");

        // Get all the reportList where displayOrder is null
        defaultReportShouldNotBeFound("displayOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportsByDisplayOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where displayOrder greater than or equals to DEFAULT_DISPLAY_ORDER
        defaultReportShouldBeFound("displayOrder.greaterOrEqualThan=" + DEFAULT_DISPLAY_ORDER);

        // Get all the reportList where displayOrder greater than or equals to UPDATED_DISPLAY_ORDER
        defaultReportShouldNotBeFound("displayOrder.greaterOrEqualThan=" + UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllReportsByDisplayOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where displayOrder less than or equals to DEFAULT_DISPLAY_ORDER
        defaultReportShouldNotBeFound("displayOrder.lessThan=" + DEFAULT_DISPLAY_ORDER);

        // Get all the reportList where displayOrder less than or equals to UPDATED_DISPLAY_ORDER
        defaultReportShouldBeFound("displayOrder.lessThan=" + UPDATED_DISPLAY_ORDER);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReportShouldBeFound(String filter) throws Exception {
        restReportMockMvc.perform(get("/api/reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReportShouldNotBeFound(String filter) throws Exception {
        restReportMockMvc.perform(get("/api/reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingReport() throws Exception {
        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Update the report
        Report updatedReport = reportRepository.findById(report.getId()).get();
        // Disconnect from session so that the updates on updatedReport are not directly saved in db
        em.detach(updatedReport);
        updatedReport
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .displayOrder(UPDATED_DISPLAY_ORDER);
        ReportDTO reportDTO = reportMapper.toDto(updatedReport);

        restReportMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isOk());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReport.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingReport() throws Exception {
        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReportMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        int databaseSizeBeforeDelete = reportRepository.findAll().size();

        // Get the report
        restReportMockMvc.perform(delete("/api/reports/{id}", report.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Report.class);
        Report report1 = new Report();
        report1.setId(1L);
        Report report2 = new Report();
        report2.setId(report1.getId());
        assertThat(report1).isEqualTo(report2);
        report2.setId(2L);
        assertThat(report1).isNotEqualTo(report2);
        report1.setId(null);
        assertThat(report1).isNotEqualTo(report2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportDTO.class);
        ReportDTO reportDTO1 = new ReportDTO();
        reportDTO1.setId(1L);
        ReportDTO reportDTO2 = new ReportDTO();
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
        reportDTO2.setId(reportDTO1.getId());
        assertThat(reportDTO1).isEqualTo(reportDTO2);
        reportDTO2.setId(2L);
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
        reportDTO1.setId(null);
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reportMapper.fromId(null)).isNull();
    }
}
