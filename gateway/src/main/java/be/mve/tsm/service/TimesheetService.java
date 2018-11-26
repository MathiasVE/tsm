package be.mve.tsm.service;

import be.mve.tsm.service.dto.TimesheetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Timesheet.
 */
public interface TimesheetService {

    /**
     * Save a timesheet.
     *
     * @param timesheetDTO the entity to save
     * @return the persisted entity
     */
    TimesheetDTO save(TimesheetDTO timesheetDTO);

    /**
     * Get all the timesheets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimesheetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" timesheet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TimesheetDTO> findOne(Long id);

    /**
     * Delete the "id" timesheet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
