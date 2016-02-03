package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.dao.BestekDAO;
import be.ovam.art46.dao.MeetstaatRegelDao;
import be.ovam.art46.dao.MeetstaatTemplateDao;
import be.ovam.art46.service.meetstaat.MeetstaatService;
import be.ovam.art46.service.meetstaat.MeetstaatTemplateService;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.MeetstaatTemplate;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by koencorstjens on 13-8-13.
 */
@Service
@Transactional
public class MeetstaatTemplateServiceImpl implements MeetstaatTemplateService {

    public static final Mapper MAPPER = new DozerBeanMapper();
    @Autowired
    MeetstaatService meetstaatService;
    @Autowired
    MeetstaatRegelDao meetstaatRegelDao;
    @Autowired
    MeetstaatTemplateDao meetstaatTemplateDao;
    @Autowired
    BestekDAO bestekDAO;

    public List<MeetstaatTemplate> getAll() {
        return meetstaatTemplateDao.getAll();
    }

    public List<MeetstaatRegel> laadTemplate(Long templateId, Long bestekId) throws InvocationTargetException, IllegalAccessException {
        List<MeetstaatRegel> templateMeetstaatRegels = getAllregels(templateId);
        List<MeetstaatRegel> meetstaatRegels = new ArrayList<MeetstaatRegel>();
        for (MeetstaatRegel templateMeetstaatRegel : templateMeetstaatRegels) {
            MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
            MAPPER.map(templateMeetstaatRegel, meetstaatRegel);
            meetstaatRegel.setId(null);
            meetstaatRegel.setBestek(bestekDAO.get(bestekId));
            meetstaatRegels.add(meetstaatRegel);
        }
        return meetstaatRegels;
    }

    public List<MeetstaatRegel> getAllregels(Long templateId) {
        List<MeetstaatRegel> meetstaatRegels = meetstaatRegelDao.getTemplateRegels(templateId);
        Collections.sort(meetstaatRegels);
        meetstaatService.herbereken(meetstaatRegels, new BigDecimal(21));
        Collections.sort(meetstaatRegels);
        return meetstaatRegels;
    }

    public List<MeetstaatRegel> save(ArrayList<MeetstaatRegel> nieuweMeetstaatRegelList, String naam) {
        MeetstaatTemplate meetstaatTemplate = meetstaatTemplateDao.getByNaam(naam);
        if (meetstaatTemplate == null) {
            meetstaatTemplate = new MeetstaatTemplate();
            meetstaatTemplate.setNaam(naam);
            meetstaatTemplateDao.save(meetstaatTemplate);
        }
        if (nieuweMeetstaatRegelList != null && nieuweMeetstaatRegelList.size() > 0) {
            for (MeetstaatRegel meetstaatRegel : nieuweMeetstaatRegelList) {
                meetstaatRegel.setBestek(null);
                meetstaatRegel.setMeetstaatTemplate(meetstaatTemplate);
            }
            List<MeetstaatRegel> deleteMeetstaatRegels = new ArrayList<MeetstaatRegel>();
            for (MeetstaatRegel oudeMeestaatRegel : meetstaatTemplate.getMeetstaatRegels()) {
                boolean gevonden = Boolean.FALSE;
                for (MeetstaatRegel meetstaatRegel : nieuweMeetstaatRegelList) {
                    if (meetstaatRegel.getId() != null && oudeMeestaatRegel.getId().compareTo(meetstaatRegel.getId()) == 0) {
                        gevonden = Boolean.TRUE;
                        break;
                    }
                }
                if (!gevonden) {
                    deleteMeetstaatRegels.add(oudeMeestaatRegel);
                }
            }
            meetstaatRegelDao.delete(deleteMeetstaatRegels);
            meetstaatRegelDao.save(nieuweMeetstaatRegelList);

        }

        return getAllregels(meetstaatTemplate.getId());
    }

    public MeetstaatTemplate get(Long templateId) {
        return meetstaatTemplateDao.get(templateId);
    }

    public void delete(Long templateId) {
        meetstaatTemplateDao.delete(templateId);
        meetstaatRegelDao.deleteTemplateRegels(templateId);
    }

    public List<MeetstaatRegel> getFromOtherBestek(String bestekNr, Long bestekId) {
        Bestek bestek = bestekDAO.getForBestekNr(bestekNr);
        if (bestek == null) {
            return null;
        }
        List<MeetstaatRegel> meetstaatRegels = meetstaatRegelDao.getBestekRegels(bestek.getBestek_id());
        List<MeetstaatRegel> newMeetstaatRegels = new ArrayList<MeetstaatRegel>();
        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            MeetstaatRegel newMeetstaatRegel = new MeetstaatRegel();
            MAPPER.map(meetstaatRegel, newMeetstaatRegel);
            newMeetstaatRegel.setId(null);
            newMeetstaatRegel.setBestek(bestekDAO.get(bestekId));
            newMeetstaatRegels.add(newMeetstaatRegel);
        }
        Collections.sort(newMeetstaatRegels);
        meetstaatService.herbereken(newMeetstaatRegels, new BigDecimal(21));
        Collections.sort(newMeetstaatRegels);
        return newMeetstaatRegels;
    }


}
