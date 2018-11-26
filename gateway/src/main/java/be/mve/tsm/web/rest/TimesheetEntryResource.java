package be.mve.tsm.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.mve.tsm.service.TimesheetEntryService;
import be.mve.tsm.web.rest.errors.BadRequestAlertException;
import be.mve.tsm.web.rest.util.HeaderUtil;
import be.mve.tsm.web.rest.util.PaginationUtil;
import be.mve.tsm.service.dto.TimesheetEntryDTO;
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
 * REST controller for managing TimesheetEntry.
 */
@RestController
@RequestMapping("/api")
public class TimesheetEntryResource {

    private final Logger log = LoggerFactory.getLogger(TimesheetEntryResource.class);

    private static final String ENTITY_NAME = "timesheetEntry";

    private final TimesheetEntryService timesheetEntryService;

    public TimesheetEntryResource(TimesheetEntryService timesheetEntryService) {
        this.timesheetEntryService = timesheetEntryService;
    }

    /**
     * POST  /timesheet-entries : Create a new timesheetEntry.
     *
     * @param timesheetEntryDTO the timesheetEntryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timesheetEntryDTO, or with status 400 (Bad Request) if the timesheetEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timesheet-entries")
    @Timed
    public ResponseEntity<TimesheetEntryDTO> createTimesheetEntry(@RequestBody TimesheetEntryDTO timesheetEntryDTO) throws URISyntaxException {
        log.debug("REST request to save TimesheetEntry : {}", timesheetEntryDTO);
        if (timesheetEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new timesheetEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimesheetEntryDTO result = timesheetEntryService.save(timesheetEntryDTO);
        return ResponseEntity.created(new URI("/api/timesheet-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timesheet-entries : Updates an existing timesheetEntry.
     *
     * @param timesheetEntryDTO the timesheetEntryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timesheetEntryDTO,
     * or with status 400 (Bad Request) if the timesheetEntryDTO is not valid,
     * or with status 500 (Internal Server Error) if the timesheetEntryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timesheet-entries")
    @Timed
    public ResponseEntity<TimesheetEntryDTO> updateTimesheetEntry(@RequestBody TimesheetEntryDTO timesheetEntryDTO) throws URISyntaxException {
        log.debug("REST request to update TimesheetEntry : {}", timesheetEntryDTO);
        if (timesheetEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimesheetEntryDTO result = timesheetEntryService.save(timesheetEntryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timesheetEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timesheet-entries : get all the timesheetEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timesheetEntries in body
     */
    @GetMapping("/timesheet-entries")
    @Timed
    public ResponseEntity<List<TimesheetEntryDTO>> getAllTimesheetEntries(Pageable pageable) {
        log.debug("REST request to get a page of TimesheetEntries");
        Page<TimesheetEntryDTO> page = timesheetEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timesheet-entries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /timesheet-entries/:id : get the "id" timesheetEntry.
     *
     * @param id the id of the timesheetEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timesheetEntryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timesheet-entries/{id}")
    @Timed
    public ResponseEntity<TimesheetEntryDTO> getTimesheetEntry(@PathVariable Long id) {
        log.debug("REST request to get TimesheetEntry : {}", id);
        Optional<TimesheetEntryDTO> timesheetEntryDTO = timesheetEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timesheetEntryDTO);
    }

    /**
     * DELETE  /timesheet-entries/:id : delete the "id" timesheetEntry.
     *
     * @param id the id of the timesheetEntryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timesheet-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimesheetEntry(@PathVariable Long id) {
        log.debug("REST request to delete TimesheetEntry : {}", id);
        timesheetEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
