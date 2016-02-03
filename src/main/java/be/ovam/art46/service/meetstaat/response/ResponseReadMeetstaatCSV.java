package be.ovam.art46.service.meetstaat.response;

import be.ovam.pad.model.MeetstaatRegel;

import java.util.List;

/**
 * Created by Koen on 16/12/13.
 */
public class ResponseReadMeetstaatCSV {
    private List<String> errors;
    private List<MeetstaatRegel> meetstaatRegels;

    public List<MeetstaatRegel> getMeetstaatRegels() {
        return meetstaatRegels;
    }

    public void setMeetstaatRegels(List<MeetstaatRegel> meetstaatRegels) {
        this.meetstaatRegels = meetstaatRegels;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
