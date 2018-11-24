package be.mve.tsm.service;

import be.mve.tsm.service.dto.TimesheetLabelDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TimesheetLabel.
 */
public interface TimesheetLabelService {

    /**
     * Save a timesheetLabel.
     *
     * @param timesheetLabelDTO the entity to save
     * @return the persisted entity
     */
    TimesheetLabelDTO save(TimesheetLabelDTO timesheetLabelDTO);

    /**
     * Get all the timesheetLabels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimesheetLabelDTO> findAll(Pageable pageable);


    /**
     * Get the "id" timesheetLabel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TimesheetLabelDTO> findOne(Long id);

    /**
     * Delete the "id" timesheetLabel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
