package be.mve.tsm.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.mve.tsm.service.TimesheetService;
import be.mve.tsm.web.rest.errors.BadRequestAlertException;
import be.mve.tsm.web.rest.util.HeaderUtil;
import be.mve.tsm.web.rest.util.PaginationUtil;
import be.mve.tsm.service.dto.TimesheetDTO;
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
 * REST controller for managing Timesheet.
 */
@RestController
@RequestMapping("/api")
public class TimesheetResource {

    private final Logger log = LoggerFactory.getLogger(TimesheetResource.class);

    private static final String ENTITY_NAME = "timesheet";

    private final TimesheetService timesheetService;

    public TimesheetResource(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    /**
     * POST  /timesheets : Create a new timesheet.
     *
     * @param timesheetDTO the timesheetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timesheetDTO, or with status 400 (Bad Request) if the timesheet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timesheets")
    @Timed
    public ResponseEntity<TimesheetDTO> createTimesheet(@RequestBody TimesheetDTO timesheetDTO) throws URISyntaxException {
        log.debug("REST request to save Timesheet : {}", timesheetDTO);
        if (timesheetDTO.getId() != null) {
            throw new BadRequestAlertException("A new timesheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimesheetDTO result = timesheetService.save(timesheetDTO);
        return ResponseEntity.created(new URI("/api/timesheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timesheets : Updates an existing timesheet.
     *
     * @param timesheetDTO the timesheetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timesheetDTO,
     * or with status 400 (Bad Request) if the timesheetDTO is not valid,
     * or with status 500 (Internal Server Error) if the timesheetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timesheets")
    @Timed
    public ResponseEntity<TimesheetDTO> updateTimesheet(@RequestBody TimesheetDTO timesheetDTO) throws URISyntaxException {
        log.debug("REST request to update Timesheet : {}", timesheetDTO);
        if (timesheetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimesheetDTO result = timesheetService.save(timesheetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timesheetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timesheets : get all the timesheets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timesheets in body
     */
    @GetMapping("/timesheets")
    @Timed
    public ResponseEntity<List<TimesheetDTO>> getAllTimesheets(Pageable pageable) {
        log.debug("REST request to get a page of Timesheets");
        Page<TimesheetDTO> page = timesheetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timesheets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /timesheets/:id : get the "id" timesheet.
     *
     * @param id the id of the timesheetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timesheetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timesheets/{id}")
    @Timed
    public ResponseEntity<TimesheetDTO> getTimesheet(@PathVariable Long id) {
        log.debug("REST request to get Timesheet : {}", id);
        Optional<TimesheetDTO> timesheetDTO = timesheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timesheetDTO);
    }

    /**
     * DELETE  /timesheets/:id : delete the "id" timesheet.
     *
     * @param id the id of the timesheetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timesheets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimesheet(@PathVariable Long id) {
        log.debug("REST request to delete Timesheet : {}", id);
        timesheetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
