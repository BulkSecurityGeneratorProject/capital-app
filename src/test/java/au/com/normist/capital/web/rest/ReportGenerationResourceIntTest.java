package au.com.normist.capital.web.rest;

import au.com.normist.capital.CapitalappApp;

import au.com.normist.capital.domain.ReportGeneration;
import au.com.normist.capital.domain.Report;
import au.com.normist.capital.repository.ReportGenerationRepository;
import au.com.normist.capital.service.ReportGenerationService;
import au.com.normist.capital.service.dto.ReportGenerationDTO;
import au.com.normist.capital.service.mapper.ReportGenerationMapper;
import au.com.normist.capital.web.rest.errors.ExceptionTranslator;
import au.com.normist.capital.service.dto.ReportGenerationCriteria;
import au.com.normist.capital.service.ReportGenerationQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static au.com.normist.capital.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import au.com.normist.capital.domain.enumeration.ReportGenerationStatus;
import au.com.normist.capital.domain.enumeration.ReportFileType;
/**
 * Test class for the ReportGenerationResource REST controller.
 *
 * @see ReportGenerationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapitalappApp.class)
public class ReportGenerationResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ReportGenerationStatus DEFAULT_STATUS = ReportGenerationStatus.PENDING;
    private static final ReportGenerationStatus UPDATED_STATUS = ReportGenerationStatus.GENERATING;

    private static final ReportFileType DEFAULT_FILE_TYPE = ReportFileType.CSV;
    private static final ReportFileType UPDATED_FILE_TYPE = ReportFileType.PDF;

    private static final String DEFAULT_FULL_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FULL_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_LOCKED = false;
    private static final Boolean UPDATED_IS_LOCKED = true;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATOR = "AAAAAAAAAA";
    private static final String UPDATED_CREATOR = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_COMPLETION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private ReportGenerationRepository reportGenerationRepository;


    @Autowired
    private ReportGenerationMapper reportGenerationMapper;
    

    @Autowired
    private ReportGenerationService reportGenerationService;

    @Autowired
    private ReportGenerationQueryService reportGenerationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReportGenerationMockMvc;

    private ReportGeneration reportGeneration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportGenerationResource reportGenerationResource = new ReportGenerationResource(reportGenerationService, reportGenerationQueryService);
        this.restReportGenerationMockMvc = MockMvcBuilders.standaloneSetup(reportGenerationResource)
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
    public static ReportGeneration createEntity(EntityManager em) {
        ReportGeneration reportGeneration = new ReportGeneration()
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .fileType(DEFAULT_FILE_TYPE)
            .fullPath(DEFAULT_FULL_PATH)
            .isLocked(DEFAULT_IS_LOCKED)
            .creationDate(DEFAULT_CREATION_DATE)
            .creator(DEFAULT_CREATOR)
            .startedOn(DEFAULT_STARTED_ON)
            .completionDate(DEFAULT_COMPLETION_DATE)
            .comment(DEFAULT_COMMENT);
        return reportGeneration;
    }

    @Before
    public void initTest() {
        reportGeneration = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportGeneration() throws Exception {
        int databaseSizeBeforeCreate = reportGenerationRepository.findAll().size();

        // Create the ReportGeneration
        ReportGenerationDTO reportGenerationDTO = reportGenerationMapper.toDto(reportGeneration);
        restReportGenerationMockMvc.perform(post("/api/report-generations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportGenerationDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportGeneration in the database
        List<ReportGeneration> reportGenerationList = reportGenerationRepository.findAll();
        assertThat(reportGenerationList).hasSize(databaseSizeBeforeCreate + 1);
        ReportGeneration testReportGeneration = reportGenerationList.get(reportGenerationList.size() - 1);
        assertThat(testReportGeneration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReportGeneration.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReportGeneration.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testReportGeneration.getFullPath()).isEqualTo(DEFAULT_FULL_PATH);
        assertThat(testReportGeneration.isIsLocked()).isEqualTo(DEFAULT_IS_LOCKED);
        assertThat(testReportGeneration.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testReportGeneration.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testReportGeneration.getStartedOn()).isEqualTo(DEFAULT_STARTED_ON);
        assertThat(testReportGeneration.getCompletionDate()).isEqualTo(DEFAULT_COMPLETION_DATE);
        assertThat(testReportGeneration.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createReportGenerationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportGenerationRepository.findAll().size();

        // Create the ReportGeneration with an existing ID
        reportGeneration.setId(1L);
        ReportGenerationDTO reportGenerationDTO = reportGenerationMapper.toDto(reportGeneration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportGenerationMockMvc.perform(post("/api/report-generations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportGenerationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportGeneration in the database
        List<ReportGeneration> reportGenerationList = reportGenerationRepository.findAll();
        assertThat(reportGenerationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReportGenerations() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList
        restReportGenerationMockMvc.perform(get("/api/report-generations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportGeneration.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fullPath").value(hasItem(DEFAULT_FULL_PATH.toString())))
            .andExpect(jsonPath("$.[*].isLocked").value(hasItem(DEFAULT_IS_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creator").value(hasItem(DEFAULT_CREATOR.toString())))
            .andExpect(jsonPath("$.[*].startedOn").value(hasItem(DEFAULT_STARTED_ON.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
    

    @Test
    @Transactional
    public void getReportGeneration() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get the reportGeneration
        restReportGenerationMockMvc.perform(get("/api/report-generations/{id}", reportGeneration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reportGeneration.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.fullPath").value(DEFAULT_FULL_PATH.toString()))
            .andExpect(jsonPath("$.isLocked").value(DEFAULT_IS_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.creator").value(DEFAULT_CREATOR.toString()))
            .andExpect(jsonPath("$.startedOn").value(DEFAULT_STARTED_ON.toString()))
            .andExpect(jsonPath("$.completionDate").value(DEFAULT_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where description equals to DEFAULT_DESCRIPTION
        defaultReportGenerationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the reportGenerationList where description equals to UPDATED_DESCRIPTION
        defaultReportGenerationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReportGenerationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the reportGenerationList where description equals to UPDATED_DESCRIPTION
        defaultReportGenerationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where description is not null
        defaultReportGenerationShouldBeFound("description.specified=true");

        // Get all the reportGenerationList where description is null
        defaultReportGenerationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where status equals to DEFAULT_STATUS
        defaultReportGenerationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the reportGenerationList where status equals to UPDATED_STATUS
        defaultReportGenerationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultReportGenerationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the reportGenerationList where status equals to UPDATED_STATUS
        defaultReportGenerationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where status is not null
        defaultReportGenerationShouldBeFound("status.specified=true");

        // Get all the reportGenerationList where status is null
        defaultReportGenerationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByFileTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where fileType equals to DEFAULT_FILE_TYPE
        defaultReportGenerationShouldBeFound("fileType.equals=" + DEFAULT_FILE_TYPE);

        // Get all the reportGenerationList where fileType equals to UPDATED_FILE_TYPE
        defaultReportGenerationShouldNotBeFound("fileType.equals=" + UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByFileTypeIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where fileType in DEFAULT_FILE_TYPE or UPDATED_FILE_TYPE
        defaultReportGenerationShouldBeFound("fileType.in=" + DEFAULT_FILE_TYPE + "," + UPDATED_FILE_TYPE);

        // Get all the reportGenerationList where fileType equals to UPDATED_FILE_TYPE
        defaultReportGenerationShouldNotBeFound("fileType.in=" + UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByFileTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where fileType is not null
        defaultReportGenerationShouldBeFound("fileType.specified=true");

        // Get all the reportGenerationList where fileType is null
        defaultReportGenerationShouldNotBeFound("fileType.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByFullPathIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where fullPath equals to DEFAULT_FULL_PATH
        defaultReportGenerationShouldBeFound("fullPath.equals=" + DEFAULT_FULL_PATH);

        // Get all the reportGenerationList where fullPath equals to UPDATED_FULL_PATH
        defaultReportGenerationShouldNotBeFound("fullPath.equals=" + UPDATED_FULL_PATH);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByFullPathIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where fullPath in DEFAULT_FULL_PATH or UPDATED_FULL_PATH
        defaultReportGenerationShouldBeFound("fullPath.in=" + DEFAULT_FULL_PATH + "," + UPDATED_FULL_PATH);

        // Get all the reportGenerationList where fullPath equals to UPDATED_FULL_PATH
        defaultReportGenerationShouldNotBeFound("fullPath.in=" + UPDATED_FULL_PATH);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByFullPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where fullPath is not null
        defaultReportGenerationShouldBeFound("fullPath.specified=true");

        // Get all the reportGenerationList where fullPath is null
        defaultReportGenerationShouldNotBeFound("fullPath.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByIsLockedIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where isLocked equals to DEFAULT_IS_LOCKED
        defaultReportGenerationShouldBeFound("isLocked.equals=" + DEFAULT_IS_LOCKED);

        // Get all the reportGenerationList where isLocked equals to UPDATED_IS_LOCKED
        defaultReportGenerationShouldNotBeFound("isLocked.equals=" + UPDATED_IS_LOCKED);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByIsLockedIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where isLocked in DEFAULT_IS_LOCKED or UPDATED_IS_LOCKED
        defaultReportGenerationShouldBeFound("isLocked.in=" + DEFAULT_IS_LOCKED + "," + UPDATED_IS_LOCKED);

        // Get all the reportGenerationList where isLocked equals to UPDATED_IS_LOCKED
        defaultReportGenerationShouldNotBeFound("isLocked.in=" + UPDATED_IS_LOCKED);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByIsLockedIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where isLocked is not null
        defaultReportGenerationShouldBeFound("isLocked.specified=true");

        // Get all the reportGenerationList where isLocked is null
        defaultReportGenerationShouldNotBeFound("isLocked.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where creationDate equals to DEFAULT_CREATION_DATE
        defaultReportGenerationShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the reportGenerationList where creationDate equals to UPDATED_CREATION_DATE
        defaultReportGenerationShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultReportGenerationShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the reportGenerationList where creationDate equals to UPDATED_CREATION_DATE
        defaultReportGenerationShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where creationDate is not null
        defaultReportGenerationShouldBeFound("creationDate.specified=true");

        // Get all the reportGenerationList where creationDate is null
        defaultReportGenerationShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCreatorIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where creator equals to DEFAULT_CREATOR
        defaultReportGenerationShouldBeFound("creator.equals=" + DEFAULT_CREATOR);

        // Get all the reportGenerationList where creator equals to UPDATED_CREATOR
        defaultReportGenerationShouldNotBeFound("creator.equals=" + UPDATED_CREATOR);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCreatorIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where creator in DEFAULT_CREATOR or UPDATED_CREATOR
        defaultReportGenerationShouldBeFound("creator.in=" + DEFAULT_CREATOR + "," + UPDATED_CREATOR);

        // Get all the reportGenerationList where creator equals to UPDATED_CREATOR
        defaultReportGenerationShouldNotBeFound("creator.in=" + UPDATED_CREATOR);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCreatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where creator is not null
        defaultReportGenerationShouldBeFound("creator.specified=true");

        // Get all the reportGenerationList where creator is null
        defaultReportGenerationShouldNotBeFound("creator.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByStartedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where startedOn equals to DEFAULT_STARTED_ON
        defaultReportGenerationShouldBeFound("startedOn.equals=" + DEFAULT_STARTED_ON);

        // Get all the reportGenerationList where startedOn equals to UPDATED_STARTED_ON
        defaultReportGenerationShouldNotBeFound("startedOn.equals=" + UPDATED_STARTED_ON);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByStartedOnIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where startedOn in DEFAULT_STARTED_ON or UPDATED_STARTED_ON
        defaultReportGenerationShouldBeFound("startedOn.in=" + DEFAULT_STARTED_ON + "," + UPDATED_STARTED_ON);

        // Get all the reportGenerationList where startedOn equals to UPDATED_STARTED_ON
        defaultReportGenerationShouldNotBeFound("startedOn.in=" + UPDATED_STARTED_ON);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByStartedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where startedOn is not null
        defaultReportGenerationShouldBeFound("startedOn.specified=true");

        // Get all the reportGenerationList where startedOn is null
        defaultReportGenerationShouldNotBeFound("startedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where completionDate equals to DEFAULT_COMPLETION_DATE
        defaultReportGenerationShouldBeFound("completionDate.equals=" + DEFAULT_COMPLETION_DATE);

        // Get all the reportGenerationList where completionDate equals to UPDATED_COMPLETION_DATE
        defaultReportGenerationShouldNotBeFound("completionDate.equals=" + UPDATED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where completionDate in DEFAULT_COMPLETION_DATE or UPDATED_COMPLETION_DATE
        defaultReportGenerationShouldBeFound("completionDate.in=" + DEFAULT_COMPLETION_DATE + "," + UPDATED_COMPLETION_DATE);

        // Get all the reportGenerationList where completionDate equals to UPDATED_COMPLETION_DATE
        defaultReportGenerationShouldNotBeFound("completionDate.in=" + UPDATED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where completionDate is not null
        defaultReportGenerationShouldBeFound("completionDate.specified=true");

        // Get all the reportGenerationList where completionDate is null
        defaultReportGenerationShouldNotBeFound("completionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where comment equals to DEFAULT_COMMENT
        defaultReportGenerationShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the reportGenerationList where comment equals to UPDATED_COMMENT
        defaultReportGenerationShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultReportGenerationShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the reportGenerationList where comment equals to UPDATED_COMMENT
        defaultReportGenerationShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        // Get all the reportGenerationList where comment is not null
        defaultReportGenerationShouldBeFound("comment.specified=true");

        // Get all the reportGenerationList where comment is null
        defaultReportGenerationShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportGenerationsByReportIsEqualToSomething() throws Exception {
        // Initialize the database
        Report report = ReportResourceIntTest.createEntity(em);
        em.persist(report);
        em.flush();
        reportGeneration.setReport(report);
        reportGenerationRepository.saveAndFlush(reportGeneration);
        Long reportId = report.getId();

        // Get all the reportGenerationList where report equals to reportId
        defaultReportGenerationShouldBeFound("reportId.equals=" + reportId);

        // Get all the reportGenerationList where report equals to reportId + 1
        defaultReportGenerationShouldNotBeFound("reportId.equals=" + (reportId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReportGenerationShouldBeFound(String filter) throws Exception {
        restReportGenerationMockMvc.perform(get("/api/report-generations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportGeneration.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fullPath").value(hasItem(DEFAULT_FULL_PATH.toString())))
            .andExpect(jsonPath("$.[*].isLocked").value(hasItem(DEFAULT_IS_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creator").value(hasItem(DEFAULT_CREATOR.toString())))
            .andExpect(jsonPath("$.[*].startedOn").value(hasItem(DEFAULT_STARTED_ON.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReportGenerationShouldNotBeFound(String filter) throws Exception {
        restReportGenerationMockMvc.perform(get("/api/report-generations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingReportGeneration() throws Exception {
        // Get the reportGeneration
        restReportGenerationMockMvc.perform(get("/api/report-generations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportGeneration() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        int databaseSizeBeforeUpdate = reportGenerationRepository.findAll().size();

        // Update the reportGeneration
        ReportGeneration updatedReportGeneration = reportGenerationRepository.findById(reportGeneration.getId()).get();
        // Disconnect from session so that the updates on updatedReportGeneration are not directly saved in db
        em.detach(updatedReportGeneration);
        updatedReportGeneration
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .fileType(UPDATED_FILE_TYPE)
            .fullPath(UPDATED_FULL_PATH)
            .isLocked(UPDATED_IS_LOCKED)
            .creationDate(UPDATED_CREATION_DATE)
            .creator(UPDATED_CREATOR)
            .startedOn(UPDATED_STARTED_ON)
            .completionDate(UPDATED_COMPLETION_DATE)
            .comment(UPDATED_COMMENT);
        ReportGenerationDTO reportGenerationDTO = reportGenerationMapper.toDto(updatedReportGeneration);

        restReportGenerationMockMvc.perform(put("/api/report-generations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportGenerationDTO)))
            .andExpect(status().isOk());

        // Validate the ReportGeneration in the database
        List<ReportGeneration> reportGenerationList = reportGenerationRepository.findAll();
        assertThat(reportGenerationList).hasSize(databaseSizeBeforeUpdate);
        ReportGeneration testReportGeneration = reportGenerationList.get(reportGenerationList.size() - 1);
        assertThat(testReportGeneration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReportGeneration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReportGeneration.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testReportGeneration.getFullPath()).isEqualTo(UPDATED_FULL_PATH);
        assertThat(testReportGeneration.isIsLocked()).isEqualTo(UPDATED_IS_LOCKED);
        assertThat(testReportGeneration.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testReportGeneration.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testReportGeneration.getStartedOn()).isEqualTo(UPDATED_STARTED_ON);
        assertThat(testReportGeneration.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testReportGeneration.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingReportGeneration() throws Exception {
        int databaseSizeBeforeUpdate = reportGenerationRepository.findAll().size();

        // Create the ReportGeneration
        ReportGenerationDTO reportGenerationDTO = reportGenerationMapper.toDto(reportGeneration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReportGenerationMockMvc.perform(put("/api/report-generations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportGenerationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportGeneration in the database
        List<ReportGeneration> reportGenerationList = reportGenerationRepository.findAll();
        assertThat(reportGenerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReportGeneration() throws Exception {
        // Initialize the database
        reportGenerationRepository.saveAndFlush(reportGeneration);

        int databaseSizeBeforeDelete = reportGenerationRepository.findAll().size();

        // Get the reportGeneration
        restReportGenerationMockMvc.perform(delete("/api/report-generations/{id}", reportGeneration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReportGeneration> reportGenerationList = reportGenerationRepository.findAll();
        assertThat(reportGenerationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportGeneration.class);
        ReportGeneration reportGeneration1 = new ReportGeneration();
        reportGeneration1.setId(1L);
        ReportGeneration reportGeneration2 = new ReportGeneration();
        reportGeneration2.setId(reportGeneration1.getId());
        assertThat(reportGeneration1).isEqualTo(reportGeneration2);
        reportGeneration2.setId(2L);
        assertThat(reportGeneration1).isNotEqualTo(reportGeneration2);
        reportGeneration1.setId(null);
        assertThat(reportGeneration1).isNotEqualTo(reportGeneration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportGenerationDTO.class);
        ReportGenerationDTO reportGenerationDTO1 = new ReportGenerationDTO();
        reportGenerationDTO1.setId(1L);
        ReportGenerationDTO reportGenerationDTO2 = new ReportGenerationDTO();
        assertThat(reportGenerationDTO1).isNotEqualTo(reportGenerationDTO2);
        reportGenerationDTO2.setId(reportGenerationDTO1.getId());
        assertThat(reportGenerationDTO1).isEqualTo(reportGenerationDTO2);
        reportGenerationDTO2.setId(2L);
        assertThat(reportGenerationDTO1).isNotEqualTo(reportGenerationDTO2);
        reportGenerationDTO1.setId(null);
        assertThat(reportGenerationDTO1).isNotEqualTo(reportGenerationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reportGenerationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reportGenerationMapper.fromId(null)).isNull();
    }
}
