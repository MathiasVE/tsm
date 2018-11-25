package be.mve.tsm.api.timesheet.input;

import be.mve.tsm.api.timesheet.TimesheetException;
import be.mve.tsm.api.timesheet.data.Timesheet;
import be.mve.tsm.api.timesheet.data.TimesheetEntry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TimesheetReader {

    private final static Logger log = LoggerFactory.getLogger(TimesheetReader.class);

    public static Timesheet getTimesheet(InputStream timesheetStream) throws TimesheetException {
        Collection<TimesheetEntry> timesheetEntries = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(timesheetStream, "UTF-8")) {
            CSVParser csvParser = new CSVParser(isr, CSVFormat.RFC4180);
            try {
                boolean skippedTitle = false;

                for (CSVRecord csvRecord : csvParser) {
                    if (skippedTitle) {
                        timesheetEntries.add(processCsvRecord(csvRecord));
                    } else {
                        skippedTitle = true;
                    }
                }
            } finally {
                try {
                    csvParser.close();
                } catch (IOException e) {
                    log.error("Error closing timesheet csv", e);
                }
            }
        } catch (IOException e) {
            throw new TimesheetException("Issue processing timesheet", e);
        }
        return new Timesheet(timesheetEntries);
    }

    private static TimesheetEntry processCsvRecord(CSVRecord csvRecord) {
        List<String> labels = Arrays.asList(csvRecord.get(0).split("\\s*,\\s*"));
        LocalDateTime from = LocalDateTime.parse(csvRecord.get(1), DateTimeFormatter.ofPattern("d/MM/yyyy H:mm:ss"));
        LocalDateTime to = LocalDateTime.parse(csvRecord.get(2), DateTimeFormatter.ofPattern("d/MM/yyyy H:mm:ss"));
        LocalTime fromTime = from.toLocalTime();
        LocalTime untilTime = to.toLocalTime();
        if (untilTime.equals(LocalTime.MIN)) {
            untilTime = LocalTime.MAX;
        }
        String notitie = csvRecord.get(4);
        return new TimesheetEntry(labels, from.toLocalDate(), fromTime, untilTime, notitie);
    }
}
