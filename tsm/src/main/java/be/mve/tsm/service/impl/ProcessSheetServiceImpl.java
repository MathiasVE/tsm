package be.mve.tsm.service.impl;

import be.mve.tsm.api.timesheet.TimesheetException;
import be.mve.tsm.api.timesheet.data.Timesheet;
import be.mve.tsm.api.timesheet.data.TimesheetEntry;
import be.mve.tsm.api.timesheet.input.TimesheetReader;
import be.mve.tsm.service.ProcessSheetService;
import be.mve.tsm.service.TimesheetEntryService;
import be.mve.tsm.service.TimesheetLabelService;
import be.mve.tsm.service.TimesheetService;
import be.mve.tsm.service.dto.TimesheetDTO;
import be.mve.tsm.service.dto.TimesheetEntryDTO;
import be.mve.tsm.service.dto.TimesheetLabelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

@Service
@Transactional
public class ProcessSheetServiceImpl implements ProcessSheetService {

    private final Logger log = LoggerFactory.getLogger(ProcessSheetServiceImpl.class);

    private final TimesheetEntryService timesheetEntryService;
    private final TimesheetService timesheetService;
    private final TimesheetLabelService timesheetLabelService;

    public ProcessSheetServiceImpl(TimesheetEntryService timesheetEntryService,
                                   TimesheetService timesheetService,
                                   TimesheetLabelService timesheetLabelService) {
        this.timesheetEntryService = timesheetEntryService;
        this.timesheetService = timesheetService;
        this.timesheetLabelService = timesheetLabelService;
    }

    @Override
    public TimesheetDTO processSheet(InputStream inputstream) throws TimesheetException {
        Timesheet timesheet = TimesheetReader.getTimesheet(inputstream);
        TimesheetDTO timesheetDTO = timesheetService.save(new TimesheetDTO());
        Collection<TimesheetEntry> timesheetEntries = timesheet.getTimesheetEntries();
        for (TimesheetEntry timesheetEntry : timesheetEntries) {
            TimesheetEntryDTO timesheetEntryDTO = new TimesheetEntryDTO();
            timesheetEntryDTO.setFrom(LocalDateTime.of(timesheetEntry.getDate(), timesheetEntry.getFrom()).toInstant(ZoneOffset.UTC));
            timesheetEntryDTO.setUntil(LocalDateTime.of(timesheetEntry.getDate(), timesheetEntry.getUntil()).toInstant(ZoneOffset.UTC));
            timesheetEntryDTO.setDate(timesheetEntry.getDate());
            timesheetEntryDTO.setRemark(timesheetEntry.getNotitie());
            timesheetEntryDTO.setTimesheetId(timesheetDTO.getId());
            timesheetEntryDTO = timesheetEntryService.save(timesheetEntryDTO);
            for (String label : timesheetEntry.getLabels()) {
                TimesheetLabelDTO timesheetLabelDTO = new TimesheetLabelDTO();
                timesheetLabelDTO.setLabel(label);
                timesheetLabelDTO.setTimesheetEntryId(timesheetEntryDTO.getId());
                timesheetLabelService.save(timesheetLabelDTO);
            }
        }
        return timesheetDTO;
    }
}
