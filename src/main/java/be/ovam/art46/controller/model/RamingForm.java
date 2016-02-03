package be.ovam.art46.controller.model;

import be.ovam.pad.model.MeetstaatRegel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koencorstjens on 28-8-13.
 */
public class RamingForm {
    private List<MeetstaatRegel> meetstaatRegels = new ArrayList<MeetstaatRegel>();

    public RamingForm() {
        super();
        meetstaatRegels = new ArrayList<MeetstaatRegel>();
    }

    public RamingForm(List<MeetstaatRegel> meetstaatRegels) {
        this.meetstaatRegels = meetstaatRegels;
    }

    public List<MeetstaatRegel> getMeetstaatRegels() {
        return meetstaatRegels;
    }

    public void setMeetstaatRegels(List<MeetstaatRegel> meetstaatRegels) {
        this.meetstaatRegels = meetstaatRegels;
    }
}
