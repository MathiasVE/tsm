package be.mve.tsm.service.mapper;

import be.mve.tsm.domain.*;
import be.mve.tsm.service.dto.TimesheetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Timesheet and its DTO TimesheetDTO.
 */
@Mapper(componentModel = "spring", uses = {TimesheetEntryMapper.class})
public interface TimesheetMapper extends EntityMapper<TimesheetDTO, Timesheet> {

    @Mapping(source = "entries.id", target = "entriesId")
    TimesheetDTO toDto(Timesheet timesheet);

    @Mapping(source = "entriesId", target = "entries")
    Timesheet toEntity(TimesheetDTO timesheetDTO);

    default Timesheet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Timesheet timesheet = new Timesheet();
        timesheet.setId(id);
        return timesheet;
    }
}
