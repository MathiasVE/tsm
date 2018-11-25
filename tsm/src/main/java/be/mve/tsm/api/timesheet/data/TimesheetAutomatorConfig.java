package be.mve.tsm.api.timesheet.data;

import be.mve.tsm.api.timesheet.util.TimesheetLabelFactory;

import java.io.File;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

public class TimesheetAutomatorConfig implements Serializable {

    private String company;
    private String initialen;
    private String name;
    private File manicTimeExport;
    private File exportTo;
    private LocalTime workFrom;
    private HashMap<DayOfWeek, Integer> idealMinutesForDay;
    private String userName;
    private String password;
    private HashMap<String, String> mapTagsToWebsiteRegistration;
    private TimesheetLabelFactory labelFactory;

    public TimesheetAutomatorConfig(
        File manicTimeExport,
        LocalTime workFrom,
        HashMap<DayOfWeek, Integer> idealMinutesForDay,
        String userName,
        String password) {
        this.manicTimeExport = manicTimeExport;
        this.workFrom = workFrom;
        this.idealMinutesForDay = idealMinutesForDay;
        this.userName = userName;
        this.password = password;
    }

    public TimesheetAutomatorConfig() {

    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInitialen() {
        return initialen;
    }

    public void setInitialen(String initialen) {
        this.initialen = initialen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getManicTimeExport() {
        return manicTimeExport;
    }

    public void setManicTimeExport(File manicTimeExport) {
        this.manicTimeExport = manicTimeExport;
    }

    public File getExportTo() {
        return exportTo;
    }

    public void setExportTo(File exportTo) {
        this.exportTo = exportTo;
    }

    public LocalTime getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(LocalTime workFrom) {
        this.workFrom = workFrom;
    }

    public HashMap<DayOfWeek, Integer> getIdealMinutesForDay() {
        return idealMinutesForDay;
    }

    public void setIdealMinutesForDay(HashMap<DayOfWeek, Integer> idealMinutesForDay) {
        this.idealMinutesForDay = idealMinutesForDay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, String> getMapTagsToWebsiteRegistration() {
        return mapTagsToWebsiteRegistration;
    }

    public void setMapTagsToWebsiteRegistration(HashMap<String, String> mapTagsToWebsiteRegistration) {
        this.mapTagsToWebsiteRegistration = mapTagsToWebsiteRegistration;
    }

    public TimesheetLabelFactory getLabelFactory() {
        return labelFactory;
    }

    public void setLabelFactory(TimesheetLabelFactory labelFactory) {
        this.labelFactory = labelFactory;
    }
}
