package be.mve.tsm.service;

import be.mve.tsm.api.timesheet.TimesheetException;
import be.mve.tsm.service.dto.TimesheetDTO;

import java.io.InputStream;

public interface ProcessSheetService {

    TimesheetDTO processSheet(InputStream inputstream) throws TimesheetException;
}
