package be.mve.tsm.service.mapper;

import be.mve.tsm.domain.*;
import be.mve.tsm.service.dto.TimesheetLabelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TimesheetLabel and its DTO TimesheetLabelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TimesheetLabelMapper extends EntityMapper<TimesheetLabelDTO, TimesheetLabel> {



    default TimesheetLabel fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimesheetLabel timesheetLabel = new TimesheetLabel();
        timesheetLabel.setId(id);
        return timesheetLabel;
    }
}
