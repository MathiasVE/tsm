package be.mve.tsm.service;

import be.mve.tsm.service.dto.TimesheetEntryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TimesheetEntry.
 */
public interface TimesheetEntryService {

    /**
     * Save a timesheetEntry.
     *
     * @param timesheetEntryDTO the entity to save
     * @return the persisted entity
     */
    TimesheetEntryDTO save(TimesheetEntryDTO timesheetEntryDTO);

    /**
     * Get all the timesheetEntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimesheetEntryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" timesheetEntry.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TimesheetEntryDTO> findOne(Long id);

    /**
     * Delete the "id" timesheetEntry.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
