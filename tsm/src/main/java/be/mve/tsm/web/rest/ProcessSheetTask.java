package be.mve.tsm.web.rest;

import be.mve.tsm.api.timesheet.TimesheetException;
import be.mve.tsm.service.ProcessSheetService;
import be.mve.tsm.service.TimesheetService;
import be.mve.tsm.service.dto.TimesheetDTO;
import be.mve.tsm.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ProcessSheetTask {

    private final Logger log = LoggerFactory.getLogger(ProcessSheetTask.class);

    private final ProcessSheetService processSheetService;
    private final TimesheetService timesheetService;

    private static final String ENTITY_NAME = "tmsTimesheet";

    public ProcessSheetTask(ProcessSheetService processSheetService, TimesheetService timesheetService) {
        this.processSheetService = processSheetService;
        this.timesheetService = timesheetService;
    }

    @PostMapping("/timesheet-csv")
    @Timed
    public ResponseEntity<TimesheetDTO> uploadTimesheetCsv(@RequestParam("sheet") MultipartFile sheet) throws URISyntaxException {
        try (InputStream is = sheet.getInputStream()) {
            TimesheetDTO timesheetDTO = processSheetService.processSheet(is);
            timesheetDTO.setUser("test");
            timesheetDTO = timesheetService.save(timesheetDTO);
            ResponseEntity.created(new URI("/api/timesheets/" + timesheetDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, timesheetDTO.getId().toString()))
                .body(timesheetDTO);
        } catch (IOException | TimesheetException e) {
            log.error("Error processing timesheet", e);
        }
        return ResponseEntity.badRequest().build();
    }

}
