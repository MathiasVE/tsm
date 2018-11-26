package be.mve.tsm.web.rest;

import be.mve.tsm.GatewayApp;

import be.mve.tsm.config.SecurityBeanOverrideConfiguration;

import be.mve.tsm.domain.Timesheet;
import be.mve.tsm.repository.TimesheetRepository;
import be.mve.tsm.service.TimesheetService;
import be.mve.tsm.service.dto.TimesheetDTO;
import be.mve.tsm.service.mapper.TimesheetMapper;
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
import java.util.List;


import static be.mve.tsm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimesheetResource REST controller.
 *
 * @see TimesheetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, GatewayApp.class})
public class TimesheetResourceIntTest {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private TimesheetMapper timesheetMapper;

    @Autowired
    private TimesheetService timesheetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimesheetMockMvc;

    private Timesheet timesheet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimesheetResource timesheetResource = new TimesheetResource(timesheetService);
        this.restTimesheetMockMvc = MockMvcBuilders.standaloneSetup(timesheetResource)
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
    public static Timesheet createEntity(EntityManager em) {
        Timesheet timesheet = new Timesheet()
            .user(DEFAULT_USER);
        return timesheet;
    }

    @Before
    public void initTest() {
        timesheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimesheet() throws Exception {
        int databaseSizeBeforeCreate = timesheetRepository.findAll().size();

        // Create the Timesheet
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);
        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isCreated());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeCreate + 1);
        Timesheet testTimesheet = timesheetList.get(timesheetList.size() - 1);
        assertThat(testTimesheet.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    public void createTimesheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timesheetRepository.findAll().size();

        // Create the Timesheet with an existing ID
        timesheet.setId(1L);
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimesheets() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList
        restTimesheetMockMvc.perform(get("/api/timesheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())));
    }
    
    @Test
    @Transactional
    public void getTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get the timesheet
        restTimesheetMockMvc.perform(get("/api/timesheets/{id}", timesheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timesheet.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimesheet() throws Exception {
        // Get the timesheet
        restTimesheetMockMvc.perform(get("/api/timesheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        int databaseSizeBeforeUpdate = timesheetRepository.findAll().size();

        // Update the timesheet
        Timesheet updatedTimesheet = timesheetRepository.findById(timesheet.getId()).get();
        // Disconnect from session so that the updates on updatedTimesheet are not directly saved in db
        em.detach(updatedTimesheet);
        updatedTimesheet
            .user(UPDATED_USER);
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(updatedTimesheet);

        restTimesheetMockMvc.perform(put("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isOk());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeUpdate);
        Timesheet testTimesheet = timesheetList.get(timesheetList.size() - 1);
        assertThat(testTimesheet.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingTimesheet() throws Exception {
        int databaseSizeBeforeUpdate = timesheetRepository.findAll().size();

        // Create the Timesheet
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimesheetMockMvc.perform(put("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        int databaseSizeBeforeDelete = timesheetRepository.findAll().size();

        // Get the timesheet
        restTimesheetMockMvc.perform(delete("/api/timesheets/{id}", timesheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timesheet.class);
        Timesheet timesheet1 = new Timesheet();
        timesheet1.setId(1L);
        Timesheet timesheet2 = new Timesheet();
        timesheet2.setId(timesheet1.getId());
        assertThat(timesheet1).isEqualTo(timesheet2);
        timesheet2.setId(2L);
        assertThat(timesheet1).isNotEqualTo(timesheet2);
        timesheet1.setId(null);
        assertThat(timesheet1).isNotEqualTo(timesheet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimesheetDTO.class);
        TimesheetDTO timesheetDTO1 = new TimesheetDTO();
        timesheetDTO1.setId(1L);
        TimesheetDTO timesheetDTO2 = new TimesheetDTO();
        assertThat(timesheetDTO1).isNotEqualTo(timesheetDTO2);
        timesheetDTO2.setId(timesheetDTO1.getId());
        assertThat(timesheetDTO1).isEqualTo(timesheetDTO2);
        timesheetDTO2.setId(2L);
        assertThat(timesheetDTO1).isNotEqualTo(timesheetDTO2);
        timesheetDTO1.setId(null);
        assertThat(timesheetDTO1).isNotEqualTo(timesheetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timesheetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(timesheetMapper.fromId(null)).isNull();
    }
}
