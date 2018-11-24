package be.mve.tsm.service;

import be.mve.tsm.service.dto.HolidayDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Holiday.
 */
public interface HolidayService {

    /**
     * Save a holiday.
     *
     * @param holidayDTO the entity to save
     * @return the persisted entity
     */
    HolidayDTO save(HolidayDTO holidayDTO);

    /**
     * Get all the holidays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HolidayDTO> findAll(Pageable pageable);


    /**
     * Get the "id" holiday.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HolidayDTO> findOne(Long id);

    /**
     * Delete the "id" holiday.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
