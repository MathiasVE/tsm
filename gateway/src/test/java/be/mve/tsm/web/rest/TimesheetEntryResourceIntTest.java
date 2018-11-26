package be.mve.tsm.web.rest;

import be.mve.tsm.GatewayApp;

import be.mve.tsm.config.SecurityBeanOverrideConfiguration;

import be.mve.tsm.domain.TimesheetEntry;
import be.mve.tsm.repository.TimesheetEntryRepository;
import be.mve.tsm.service.TimesheetEntryService;
import be.mve.tsm.service.dto.TimesheetEntryDTO;
import be.mve.tsm.service.mapper.TimesheetEntryMapper;
import be.mve.tsm.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static be.mve.tsm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimesheetEntryResource REST controller.
 *
 * @see TimesheetEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, GatewayApp.class})
public class TimesheetEntryResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private TimesheetEntryRepository timesheetEntryRepository;

    @Autowired
    private TimesheetEntryMapper timesheetEntryMapper;

    @Autowired
    private TimesheetEntryService timesheetEntryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimesheetEntryMockMvc;

    private TimesheetEntry timesheetEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimesheetEntryResource timesheetEntryResource = new TimesheetEntryResource(timesheetEntryService);
        this.restTimesheetEntryMockMvc = MockMvcBuilders.standaloneSetup(timesheetEntryResource)
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
    public static TimesheetEntry createEntity(EntityManager em) {
        TimesheetEntry timesheetEntry = new TimesheetEntry()
            .date(DEFAULT_DATE)
            .from(DEFAULT_FROM)
            .until(DEFAULT_UNTIL)
            .remark(DEFAULT_REMARK);
        return timesheetEntry;
    }

    @Before
    public void initTest() {
        timesheetEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimesheetEntry() throws Exception {
        int databaseSizeBeforeCreate = timesheetEntryRepository.findAll().size();

        // Create the TimesheetEntry
        TimesheetEntryDTO timesheetEntryDTO = timesheetEntryMapper.toDto(timesheetEntry);
        restTimesheetEntryMockMvc.perform(post("/api/timesheet-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the TimesheetEntry in the database
        List<TimesheetEntry> timesheetEntryList = timesheetEntryRepository.findAll();
        assertThat(timesheetEntryList).hasSize(databaseSizeBeforeCreate + 1);
        TimesheetEntry testTimesheetEntry = timesheetEntryList.get(timesheetEntryList.size() - 1);
        assertThat(testTimesheetEntry.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTimesheetEntry.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testTimesheetEntry.getUntil()).isEqualTo(DEFAULT_UNTIL);
        assertThat(testTimesheetEntry.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createTimesheetEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timesheetEntryRepository.findAll().size();

        // Create the TimesheetEntry with an existing ID
        timesheetEntry.setId(1L);
        TimesheetEntryDTO timesheetEntryDTO = timesheetEntryMapper.toDto(timesheetEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimesheetEntryMockMvc.perform(post("/api/timesheet-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimesheetEntry in the database
        List<TimesheetEntry> timesheetEntryList = timesheetEntryRepository.findAll();
        assertThat(timesheetEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimesheetEntries() throws Exception {
        // Initialize the database
        timesheetEntryRepository.saveAndFlush(timesheetEntry);

        // Get all the timesheetEntryList
        restTimesheetEntryMockMvc.perform(get("/api/timesheet-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheetEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].until").value(hasItem(DEFAULT_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }
    
    @Test
    @Transactional
    public void getTimesheetEntry() throws Exception {
        // Initialize the database
        timesheetEntryRepository.saveAndFlush(timesheetEntry);

        // Get the timesheetEntry
        restTimesheetEntryMockMvc.perform(get("/api/timesheet-entries/{id}", timesheetEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timesheetEntry.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.until").value(DEFAULT_UNTIL.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimesheetEntry() throws Exception {
        // Get the timesheetEntry
        restTimesheetEntryMockMvc.perform(get("/api/timesheet-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimesheetEntry() throws Exception {
        // Initialize the database
        timesheetEntryRepository.saveAndFlush(timesheetEntry);

        int databaseSizeBeforeUpdate = timesheetEntryRepository.findAll().size();

        // Update the timesheetEntry
        TimesheetEntry updatedTimesheetEntry = timesheetEntryRepository.findById(timesheetEntry.getId()).get();
        // Disconnect from session so that the updates on updatedTimesheetEntry are not directly saved in db
        em.detach(updatedTimesheetEntry);
        updatedTimesheetEntry
            .date(UPDATED_DATE)
            .from(UPDATED_FROM)
            .until(UPDATED_UNTIL)
            .remark(UPDATED_REMARK);
        TimesheetEntryDTO timesheetEntryDTO = timesheetEntryMapper.toDto(updatedTimesheetEntry);

        restTimesheetEntryMockMvc.perform(put("/api/timesheet-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetEntryDTO)))
            .andExpect(status().isOk());

        // Validate the TimesheetEntry in the database
        List<TimesheetEntry> timesheetEntryList = timesheetEntryRepository.findAll();
        assertThat(timesheetEntryList).hasSize(databaseSizeBeforeUpdate);
        TimesheetEntry testTimesheetEntry = timesheetEntryList.get(timesheetEntryList.size() - 1);
        assertThat(testTimesheetEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTimesheetEntry.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testTimesheetEntry.getUntil()).isEqualTo(UPDATED_UNTIL);
        assertThat(testTimesheetEntry.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingTimesheetEntry() throws Exception {
        int databaseSizeBeforeUpdate = timesheetEntryRepository.findAll().size();

        // Create the TimesheetEntry
        TimesheetEntryDTO timesheetEntryDTO = timesheetEntryMapper.toDto(timesheetEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimesheetEntryMockMvc.perform(put("/api/timesheet-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimesheetEntry in the database
        List<TimesheetEntry> timesheetEntryList = timesheetEntryRepository.findAll();
        assertThat(timesheetEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimesheetEntry() throws Exception {
        // Initialize the database
        timesheetEntryRepository.saveAndFlush(timesheetEntry);

        int databaseSizeBeforeDelete = timesheetEntryRepository.findAll().size();

        // Get the timesheetEntry
        restTimesheetEntryMockMvc.perform(delete("/api/timesheet-entries/{id}", timesheetEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimesheetEntry> timesheetEntryList = timesheetEntryRepository.findAll();
        assertThat(timesheetEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimesheetEntry.class);
        TimesheetEntry timesheetEntry1 = new TimesheetEntry();
        timesheetEntry1.setId(1L);
        TimesheetEntry timesheetEntry2 = new TimesheetEntry();
        timesheetEntry2.setId(timesheetEntry1.getId());
        assertThat(timesheetEntry1).isEqualTo(timesheetEntry2);
        timesheetEntry2.setId(2L);
        assertThat(timesheetEntry1).isNotEqualTo(timesheetEntry2);
        timesheetEntry1.setId(null);
        assertThat(timesheetEntry1).isNotEqualTo(timesheetEntry2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimesheetEntryDTO.class);
        TimesheetEntryDTO timesheetEntryDTO1 = new TimesheetEntryDTO();
        timesheetEntryDTO1.setId(1L);
        TimesheetEntryDTO timesheetEntryDTO2 = new TimesheetEntryDTO();
        assertThat(timesheetEntryDTO1).isNotEqualTo(timesheetEntryDTO2);
        timesheetEntryDTO2.setId(timesheetEntryDTO1.getId());
        assertThat(timesheetEntryDTO1).isEqualTo(timesheetEntryDTO2);
        timesheetEntryDTO2.setId(2L);
        assertThat(timesheetEntryDTO1).isNotEqualTo(timesheetEntryDTO2);
        timesheetEntryDTO1.setId(null);
        assertThat(timesheetEntryDTO1).isNotEqualTo(timesheetEntryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timesheetEntryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(timesheetEntryMapper.fromId(null)).isNull();
    }
}
