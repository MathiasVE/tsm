package be.mve.tsm.service.mapper;

import be.mve.tsm.domain.*;
import be.mve.tsm.service.dto.HolidayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Holiday and its DTO HolidayDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HolidayMapper extends EntityMapper<HolidayDTO, Holiday> {



    default Holiday fromId(Long id) {
        if (id == null) {
            return null;
        }
        Holiday holiday = new Holiday();
        holiday.setId(id);
        return holiday;
    }
}
