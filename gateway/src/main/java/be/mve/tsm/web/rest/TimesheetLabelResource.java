package be.mve.tsm.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.mve.tsm.service.TimesheetLabelService;
import be.mve.tsm.web.rest.errors.BadRequestAlertException;
import be.mve.tsm.web.rest.util.HeaderUtil;
import be.mve.tsm.web.rest.util.PaginationUtil;
import be.mve.tsm.service.dto.TimesheetLabelDTO;
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
 * REST controller for managing TimesheetLabel.
 */
@RestController
@RequestMapping("/api")
public class TimesheetLabelResource {

    private final Logger log = LoggerFactory.getLogger(TimesheetLabelResource.class);

    private static final String ENTITY_NAME = "timesheetLabel";

    private final TimesheetLabelService timesheetLabelService;

    public TimesheetLabelResource(TimesheetLabelService timesheetLabelService) {
        this.timesheetLabelService = timesheetLabelService;
    }

    /**
     * POST  /timesheet-labels : Create a new timesheetLabel.
     *
     * @param timesheetLabelDTO the timesheetLabelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timesheetLabelDTO, or with status 400 (Bad Request) if the timesheetLabel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timesheet-labels")
    @Timed
    public ResponseEntity<TimesheetLabelDTO> createTimesheetLabel(@RequestBody TimesheetLabelDTO timesheetLabelDTO) throws URISyntaxException {
        log.debug("REST request to save TimesheetLabel : {}", timesheetLabelDTO);
        if (timesheetLabelDTO.getId() != null) {
            throw new BadRequestAlertException("A new timesheetLabel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimesheetLabelDTO result = timesheetLabelService.save(timesheetLabelDTO);
        return ResponseEntity.created(new URI("/api/timesheet-labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timesheet-labels : Updates an existing timesheetLabel.
     *
     * @param timesheetLabelDTO the timesheetLabelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timesheetLabelDTO,
     * or with status 400 (Bad Request) if the timesheetLabelDTO is not valid,
     * or with status 500 (Internal Server Error) if the timesheetLabelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timesheet-labels")
    @Timed
    public ResponseEntity<TimesheetLabelDTO> updateTimesheetLabel(@RequestBody TimesheetLabelDTO timesheetLabelDTO) throws URISyntaxException {
        log.debug("REST request to update TimesheetLabel : {}", timesheetLabelDTO);
        if (timesheetLabelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimesheetLabelDTO result = timesheetLabelService.save(timesheetLabelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timesheetLabelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timesheet-labels : get all the timesheetLabels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timesheetLabels in body
     */
    @GetMapping("/timesheet-labels")
    @Timed
    public ResponseEntity<List<TimesheetLabelDTO>> getAllTimesheetLabels(Pageable pageable) {
        log.debug("REST request to get a page of TimesheetLabels");
        Page<TimesheetLabelDTO> page = timesheetLabelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timesheet-labels");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /timesheet-labels/:id : get the "id" timesheetLabel.
     *
     * @param id the id of the timesheetLabelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timesheetLabelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timesheet-labels/{id}")
    @Timed
    public ResponseEntity<TimesheetLabelDTO> getTimesheetLabel(@PathVariable Long id) {
        log.debug("REST request to get TimesheetLabel : {}", id);
        Optional<TimesheetLabelDTO> timesheetLabelDTO = timesheetLabelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timesheetLabelDTO);
    }

    /**
     * DELETE  /timesheet-labels/:id : delete the "id" timesheetLabel.
     *
     * @param id the id of the timesheetLabelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timesheet-labels/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimesheetLabel(@PathVariable Long id) {
        log.debug("REST request to delete TimesheetLabel : {}", id);
        timesheetLabelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
