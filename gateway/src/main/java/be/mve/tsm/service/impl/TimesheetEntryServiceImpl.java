package be.mve.tsm.service.impl;

import be.mve.tsm.service.TimesheetEntryService;
import be.mve.tsm.domain.TimesheetEntry;
import be.mve.tsm.repository.TimesheetEntryRepository;
import be.mve.tsm.service.dto.TimesheetEntryDTO;
import be.mve.tsm.service.mapper.TimesheetEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TimesheetEntry.
 */
@Service
@Transactional
public class TimesheetEntryServiceImpl implements TimesheetEntryService {

    private final Logger log = LoggerFactory.getLogger(TimesheetEntryServiceImpl.class);

    private final TimesheetEntryRepository timesheetEntryRepository;

    private final TimesheetEntryMapper timesheetEntryMapper;

    public TimesheetEntryServiceImpl(TimesheetEntryRepository timesheetEntryRepository, TimesheetEntryMapper timesheetEntryMapper) {
        this.timesheetEntryRepository = timesheetEntryRepository;
        this.timesheetEntryMapper = timesheetEntryMapper;
    }

    /**
     * Save a timesheetEntry.
     *
     * @param timesheetEntryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TimesheetEntryDTO save(TimesheetEntryDTO timesheetEntryDTO) {
        log.debug("Request to save TimesheetEntry : {}", timesheetEntryDTO);

        TimesheetEntry timesheetEntry = timesheetEntryMapper.toEntity(timesheetEntryDTO);
        timesheetEntry = timesheetEntryRepository.save(timesheetEntry);
        return timesheetEntryMapper.toDto(timesheetEntry);
    }

    /**
     * Get all the timesheetEntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimesheetEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimesheetEntries");
        return timesheetEntryRepository.findAll(pageable)
            .map(timesheetEntryMapper::toDto);
    }


    /**
     * Get one timesheetEntry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimesheetEntryDTO> findOne(Long id) {
        log.debug("Request to get TimesheetEntry : {}", id);
        return timesheetEntryRepository.findById(id)
            .map(timesheetEntryMapper::toDto);
    }

    /**
     * Delete the timesheetEntry by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimesheetEntry : {}", id);
        timesheetEntryRepository.deleteById(id);
    }
}
