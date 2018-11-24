package be.mve.tsm.service.impl;

import be.mve.tsm.service.TimesheetLabelService;
import be.mve.tsm.domain.TimesheetLabel;
import be.mve.tsm.repository.TimesheetLabelRepository;
import be.mve.tsm.service.dto.TimesheetLabelDTO;
import be.mve.tsm.service.mapper.TimesheetLabelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TimesheetLabel.
 */
@Service
@Transactional
public class TimesheetLabelServiceImpl implements TimesheetLabelService {

    private final Logger log = LoggerFactory.getLogger(TimesheetLabelServiceImpl.class);

    private final TimesheetLabelRepository timesheetLabelRepository;

    private final TimesheetLabelMapper timesheetLabelMapper;

    public TimesheetLabelServiceImpl(TimesheetLabelRepository timesheetLabelRepository, TimesheetLabelMapper timesheetLabelMapper) {
        this.timesheetLabelRepository = timesheetLabelRepository;
        this.timesheetLabelMapper = timesheetLabelMapper;
    }

    /**
     * Save a timesheetLabel.
     *
     * @param timesheetLabelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TimesheetLabelDTO save(TimesheetLabelDTO timesheetLabelDTO) {
        log.debug("Request to save TimesheetLabel : {}", timesheetLabelDTO);

        TimesheetLabel timesheetLabel = timesheetLabelMapper.toEntity(timesheetLabelDTO);
        timesheetLabel = timesheetLabelRepository.save(timesheetLabel);
        return timesheetLabelMapper.toDto(timesheetLabel);
    }

    /**
     * Get all the timesheetLabels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimesheetLabelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimesheetLabels");
        return timesheetLabelRepository.findAll(pageable)
            .map(timesheetLabelMapper::toDto);
    }


    /**
     * Get one timesheetLabel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimesheetLabelDTO> findOne(Long id) {
        log.debug("Request to get TimesheetLabel : {}", id);
        return timesheetLabelRepository.findById(id)
            .map(timesheetLabelMapper::toDto);
    }

    /**
     * Delete the timesheetLabel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimesheetLabel : {}", id);
        timesheetLabelRepository.deleteById(id);
    }
}
