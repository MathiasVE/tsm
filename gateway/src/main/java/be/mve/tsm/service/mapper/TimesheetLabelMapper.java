package be.mve.tsm.service.mapper;

import be.mve.tsm.domain.*;
import be.mve.tsm.service.dto.TimesheetLabelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TimesheetLabel and its DTO TimesheetLabelDTO.
 */
@Mapper(componentModel = "spring", uses = {TimesheetEntryMapper.class})
public interface TimesheetLabelMapper extends EntityMapper<TimesheetLabelDTO, TimesheetLabel> {

    @Mapping(source = "timesheetEntry.id", target = "timesheetEntryId")
    TimesheetLabelDTO toDto(TimesheetLabel timesheetLabel);

    @Mapping(source = "timesheetEntryId", target = "timesheetEntry")
    TimesheetLabel toEntity(TimesheetLabelDTO timesheetLabelDTO);

    default TimesheetLabel fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimesheetLabel timesheetLabel = new TimesheetLabel();
        timesheetLabel.setId(id);
        return timesheetLabel;
    }
}
