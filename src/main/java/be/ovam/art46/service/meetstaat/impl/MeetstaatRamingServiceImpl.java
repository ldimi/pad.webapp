package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.service.meetstaat.MeetstaatRamingService;
import be.ovam.art46.service.meetstaat.MeetstaatService;
import be.ovam.pad.model.MeetstaatRegel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by koencorstjens on 28-8-13.
 */
@Service
@Transactional
public class MeetstaatRamingServiceImpl implements MeetstaatRamingService {

    @Autowired
    MeetstaatService meetstaatService;

    public void update(List<MeetstaatRegel> meetstaatRegels) {
        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            if (MeetstaatRegel.REGEL_TYPE_VH.equals(meetstaatRegel.getType())) {
                meetstaatService.save(meetstaatRegel);
            } else if (MeetstaatRegel.REGEL_TYPE_TP.equals(meetstaatRegel.getType()) || MeetstaatRegel.REGEL_TYPE_SPM.equals(meetstaatRegel.getType())) {
                meetstaatRegel.setAantal(null);
                meetstaatRegel.setEenheidsprijs(null);
                meetstaatService.save(meetstaatRegel);
            }
        }
    }
}
