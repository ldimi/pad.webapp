package be.ovam.art46.service.meetstaat;

import be.ovam.pad.model.MeetstaatRegel;

import java.util.List;

/**
 * Created by koencorstjens on 28-8-13.
 */
public interface MeetstaatRamingService {
    void update(List<MeetstaatRegel> meetstaatRegels);
}
