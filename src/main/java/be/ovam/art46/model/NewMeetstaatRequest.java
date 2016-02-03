package be.ovam.art46.model;

import be.ovam.pad.model.MeetstaatRegel;

import java.util.ArrayList;

/**
 * Created by koencorstjens on 9-8-13.
 */
public class NewMeetstaatRequest {

    private ArrayList<MeetstaatRegel> meetstaatRegels = new ArrayList<MeetstaatRegel>();
    private MeetstaatRegel newMeetstaatRegel;

    public ArrayList<MeetstaatRegel> getMeetstaatRegels() {
        return meetstaatRegels;
    }

    public void setMeetstaatRegels(ArrayList<MeetstaatRegel> meetstaatRegels) {
        this.meetstaatRegels = meetstaatRegels;
    }

    public MeetstaatRegel getNewMeetstaatRegel() {
        return newMeetstaatRegel;
    }

    public void setNewMeetstaatRegel(MeetstaatRegel newMeetstaatRegel) {
        this.newMeetstaatRegel = newMeetstaatRegel;
    }
}