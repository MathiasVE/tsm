package be.mve.tsm.web.rest;

import be.mve.tsm.GatewayApp;

import be.mve.tsm.config.SecurityBeanOverrideConfiguration;

import be.mve.tsm.domain.TimesheetLabel;
import be.mve.tsm.repository.TimesheetLabelRepository;
import be.mve.tsm.service.TimesheetLabelService;
import be.mve.tsm.service.dto.TimesheetLabelDTO;
import be.mve.tsm.service.mapper.TimesheetLabelMapper;
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
 * Test class for the TimesheetLabelResource REST controller.
 *
 * @see TimesheetLabelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, GatewayApp.class})
public class TimesheetLabelResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private TimesheetLabelRepository timesheetLabelRepository;

    @Autowired
    private TimesheetLabelMapper timesheetLabelMapper;

    @Autowired
    private TimesheetLabelService timesheetLabelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimesheetLabelMockMvc;

    private TimesheetLabel timesheetLabel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimesheetLabelResource timesheetLabelResource = new TimesheetLabelResource(timesheetLabelService);
        this.restTimesheetLabelMockMvc = MockMvcBuilders.standaloneSetup(timesheetLabelResource)
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
    public static TimesheetLabel createEntity(EntityManager em) {
        TimesheetLabel timesheetLabel = new TimesheetLabel()
            .label(DEFAULT_LABEL);
        return timesheetLabel;
    }

    @Before
    public void initTest() {
        timesheetLabel = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimesheetLabel() throws Exception {
        int databaseSizeBeforeCreate = timesheetLabelRepository.findAll().size();

        // Create the TimesheetLabel
        TimesheetLabelDTO timesheetLabelDTO = timesheetLabelMapper.toDto(timesheetLabel);
        restTimesheetLabelMockMvc.perform(post("/api/timesheet-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetLabelDTO)))
            .andExpect(status().isCreated());

        // Validate the TimesheetLabel in the database
        List<TimesheetLabel> timesheetLabelList = timesheetLabelRepository.findAll();
        assertThat(timesheetLabelList).hasSize(databaseSizeBeforeCreate + 1);
        TimesheetLabel testTimesheetLabel = timesheetLabelList.get(timesheetLabelList.size() - 1);
        assertThat(testTimesheetLabel.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createTimesheetLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timesheetLabelRepository.findAll().size();

        // Create the TimesheetLabel with an existing ID
        timesheetLabel.setId(1L);
        TimesheetLabelDTO timesheetLabelDTO = timesheetLabelMapper.toDto(timesheetLabel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimesheetLabelMockMvc.perform(post("/api/timesheet-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetLabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimesheetLabel in the database
        List<TimesheetLabel> timesheetLabelList = timesheetLabelRepository.findAll();
        assertThat(timesheetLabelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimesheetLabels() throws Exception {
        // Initialize the database
        timesheetLabelRepository.saveAndFlush(timesheetLabel);

        // Get all the timesheetLabelList
        restTimesheetLabelMockMvc.perform(get("/api/timesheet-labels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheetLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }
    
    @Test
    @Transactional
    public void getTimesheetLabel() throws Exception {
        // Initialize the database
        timesheetLabelRepository.saveAndFlush(timesheetLabel);

        // Get the timesheetLabel
        restTimesheetLabelMockMvc.perform(get("/api/timesheet-labels/{id}", timesheetLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timesheetLabel.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimesheetLabel() throws Exception {
        // Get the timesheetLabel
        restTimesheetLabelMockMvc.perform(get("/api/timesheet-labels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimesheetLabel() throws Exception {
        // Initialize the database
        timesheetLabelRepository.saveAndFlush(timesheetLabel);

        int databaseSizeBeforeUpdate = timesheetLabelRepository.findAll().size();

        // Update the timesheetLabel
        TimesheetLabel updatedTimesheetLabel = timesheetLabelRepository.findById(timesheetLabel.getId()).get();
        // Disconnect from session so that the updates on updatedTimesheetLabel are not directly saved in db
        em.detach(updatedTimesheetLabel);
        updatedTimesheetLabel
            .label(UPDATED_LABEL);
        TimesheetLabelDTO timesheetLabelDTO = timesheetLabelMapper.toDto(updatedTimesheetLabel);

        restTimesheetLabelMockMvc.perform(put("/api/timesheet-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetLabelDTO)))
            .andExpect(status().isOk());

        // Validate the TimesheetLabel in the database
        List<TimesheetLabel> timesheetLabelList = timesheetLabelRepository.findAll();
        assertThat(timesheetLabelList).hasSize(databaseSizeBeforeUpdate);
        TimesheetLabel testTimesheetLabel = timesheetLabelList.get(timesheetLabelList.size() - 1);
        assertThat(testTimesheetLabel.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingTimesheetLabel() throws Exception {
        int databaseSizeBeforeUpdate = timesheetLabelRepository.findAll().size();

        // Create the TimesheetLabel
        TimesheetLabelDTO timesheetLabelDTO = timesheetLabelMapper.toDto(timesheetLabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimesheetLabelMockMvc.perform(put("/api/timesheet-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetLabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimesheetLabel in the database
        List<TimesheetLabel> timesheetLabelList = timesheetLabelRepository.findAll();
        assertThat(timesheetLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimesheetLabel() throws Exception {
        // Initialize the database
        timesheetLabelRepository.saveAndFlush(timesheetLabel);

        int databaseSizeBeforeDelete = timesheetLabelRepository.findAll().size();

        // Get the timesheetLabel
        restTimesheetLabelMockMvc.perform(delete("/api/timesheet-labels/{id}", timesheetLabel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimesheetLabel> timesheetLabelList = timesheetLabelRepository.findAll();
        assertThat(timesheetLabelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimesheetLabel.class);
        TimesheetLabel timesheetLabel1 = new TimesheetLabel();
        timesheetLabel1.setId(1L);
        TimesheetLabel timesheetLabel2 = new TimesheetLabel();
        timesheetLabel2.setId(timesheetLabel1.getId());
        assertThat(timesheetLabel1).isEqualTo(timesheetLabel2);
        timesheetLabel2.setId(2L);
        assertThat(timesheetLabel1).isNotEqualTo(timesheetLabel2);
        timesheetLabel1.setId(null);
        assertThat(timesheetLabel1).isNotEqualTo(timesheetLabel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimesheetLabelDTO.class);
        TimesheetLabelDTO timesheetLabelDTO1 = new TimesheetLabelDTO();
        timesheetLabelDTO1.setId(1L);
        TimesheetLabelDTO timesheetLabelDTO2 = new TimesheetLabelDTO();
        assertThat(timesheetLabelDTO1).isNotEqualTo(timesheetLabelDTO2);
        timesheetLabelDTO2.setId(timesheetLabelDTO1.getId());
        assertThat(timesheetLabelDTO1).isEqualTo(timesheetLabelDTO2);
        timesheetLabelDTO2.setId(2L);
        assertThat(timesheetLabelDTO1).isNotEqualTo(timesheetLabelDTO2);
        timesheetLabelDTO1.setId(null);
        assertThat(timesheetLabelDTO1).isNotEqualTo(timesheetLabelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timesheetLabelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(timesheetLabelMapper.fromId(null)).isNull();
    }
}
