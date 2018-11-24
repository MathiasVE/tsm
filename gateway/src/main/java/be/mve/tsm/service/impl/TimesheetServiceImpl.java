package be.mve.tsm.service.impl;

import be.mve.tsm.service.TimesheetService;
import be.mve.tsm.domain.Timesheet;
import be.mve.tsm.repository.TimesheetRepository;
import be.mve.tsm.service.dto.TimesheetDTO;
import be.mve.tsm.service.mapper.TimesheetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Timesheet.
 */
@Service
@Transactional
public class TimesheetServiceImpl implements TimesheetService {

    private final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);

    private final TimesheetRepository timesheetRepository;

    private final TimesheetMapper timesheetMapper;

    public TimesheetServiceImpl(TimesheetRepository timesheetRepository, TimesheetMapper timesheetMapper) {
        this.timesheetRepository = timesheetRepository;
        this.timesheetMapper = timesheetMapper;
    }

    /**
     * Save a timesheet.
     *
     * @param timesheetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TimesheetDTO save(TimesheetDTO timesheetDTO) {
        log.debug("Request to save Timesheet : {}", timesheetDTO);

        Timesheet timesheet = timesheetMapper.toEntity(timesheetDTO);
        timesheet = timesheetRepository.save(timesheet);
        return timesheetMapper.toDto(timesheet);
    }

    /**
     * Get all the timesheets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimesheetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Timesheets");
        return timesheetRepository.findAll(pageable)
            .map(timesheetMapper::toDto);
    }


    /**
     * Get one timesheet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimesheetDTO> findOne(Long id) {
        log.debug("Request to get Timesheet : {}", id);
        return timesheetRepository.findById(id)
            .map(timesheetMapper::toDto);
    }

    /**
     * Delete the timesheet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Timesheet : {}", id);
        timesheetRepository.deleteById(id);
    }
}
