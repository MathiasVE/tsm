package be.mve.tsm.service.mapper;

import be.mve.tsm.domain.*;
import be.mve.tsm.service.dto.TimesheetEntryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TimesheetEntry and its DTO TimesheetEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {TimesheetLabelMapper.class, TimesheetMapper.class})
public interface TimesheetEntryMapper extends EntityMapper<TimesheetEntryDTO, TimesheetEntry> {

    @Mapping(source = "labels.id", target = "labelsId")
    @Mapping(source = "timesheet.id", target = "timesheetId")
    TimesheetEntryDTO toDto(TimesheetEntry timesheetEntry);

    @Mapping(source = "labelsId", target = "labels")
    @Mapping(source = "timesheetId", target = "timesheet")
    TimesheetEntry toEntity(TimesheetEntryDTO timesheetEntryDTO);

    default TimesheetEntry fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimesheetEntry timesheetEntry = new TimesheetEntry();
        timesheetEntry.setId(id);
        return timesheetEntry;
    }
}
