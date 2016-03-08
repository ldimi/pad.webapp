package be.ovam.art46.service.impl;

import be.ovam.art46.dao.DossierhouderDAO;
import be.ovam.art46.dao.VoorstelDeelopdrachtDao;
import be.ovam.art46.dao.VoorstelDeelopdrachtHistorieDao;
import be.ovam.art46.dao.VoorstelDeelopdrachtRegelDao;
import be.ovam.art46.model.*;
import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.service.VoorstelDeelopdrachtService;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.Offerte;
import be.ovam.pad.model.OfferteRegel;
import be.ovam.pad.model.VoorstelDeelopdracht;
import be.ovam.pad.model.VoorstelDeelopdrachtHistorie;
import be.ovam.pad.model.VoorstelDeelopdrachtRegel;
import be.ovam.pad.model.VoorstelDeelopdrachtStatus;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * Created by Koen on 2/05/2014.
 */
@Service
public class VoorstelDeelopdrachtServiceImpl implements VoorstelDeelopdrachtService {
    @Autowired
    private VoorstelDeelopdrachtDao voorstelDeelopdrachtDao;
    @Autowired
    private MeetstaatOfferteService offerteService;
    @Autowired
    private VoorstelDeelopdrachtRegelDao voorstelDeelopdrachtRegelDao;
    @Autowired
    private DossierhouderDAO dossierhouderDAO;
    @Autowired
    private VoorstelDeelopdrachtHistorieDao voorstelDeelopdrachtHistorieDao;
    @Autowired
    private DeelOpdrachtService deelOpdrachtService;
    @Value("${ovam.webloket.url}")
    private String urlWebloket;

    public VoorstelDeelopdracht getEmptyVoorstelDeelopdrachtId(Long offerteId) {

        VoorstelDeelopdracht voorstelDeelopdracht = new VoorstelDeelopdracht();
        Offerte offerte = offerteService.get(offerteId);
        voorstelDeelopdracht.setOfferte(offerte);
        List<VoorstelDeelopdrachtRegel> voorstelDeelopdrachtRegels = new ArrayList<VoorstelDeelopdrachtRegel>();
        for (OfferteRegel offerteRegel : offerte.getOfferteRegels()) {
            VoorstelDeelopdrachtRegel voorstelDeelopdrachtRegel = new VoorstelDeelopdrachtRegel();
            voorstelDeelopdrachtRegel.setOfferteRegel(offerteRegel);
            voorstelDeelopdrachtRegels.add(voorstelDeelopdrachtRegel);
        }
        voorstelDeelopdracht.setVoorstelDeelopdrachtRegels(voorstelDeelopdrachtRegels);
        return voorstelDeelopdracht;
    }

    public VoorstelDeelopdracht get(Long id) {
        return voorstelDeelopdrachtDao.get(id);
    }

    public Long save(VoorstelDeelopdracht voorstelDeelopdracht, String gebruiker) {
        BigDecimal totaal = new BigDecimal(0);
        BigDecimal totaalIncBtw = new BigDecimal(0);
        for (VoorstelDeelopdrachtRegel voorstelDeelopdrachtRegel : voorstelDeelopdracht.getVoorstelDeelopdrachtRegels()) {
            totaal = totaal.add(voorstelDeelopdrachtRegel.getRegelTotaal());
            totaalIncBtw = totaalIncBtw.add(voorstelDeelopdrachtRegel.getRegelTotaalInclBtw());
            voorstelDeelopdrachtRegel.setVoorstelDeelopdracht(voorstelDeelopdracht);
        }
        voorstelDeelopdracht.setBedragExclBtw(totaal);
        voorstelDeelopdracht.setBedragInclBtw(totaalIncBtw);
        if(voorstelDeelopdracht.getDeelOpdracht()!=null){
            DeelOpdracht deelOpdracht = voorstelDeelopdracht.getDeelOpdracht();
            if(deelOpdracht.getVoorstelDeelopdracht()==null){
                deelOpdracht.setVoorstelDeelopdracht(voorstelDeelopdracht);
            }
            deelOpdrachtService.save(deelOpdracht);
        }
        voorstelDeelopdrachtDao.save(voorstelDeelopdracht);
        for (VoorstelDeelopdrachtRegel voorstelDeelopdrachtRegel : voorstelDeelopdracht.getVoorstelDeelopdrachtRegels()) {
            voorstelDeelopdrachtRegel.setVoorstelDeelopdracht(voorstelDeelopdracht);
            voorstelDeelopdrachtRegelDao.save(voorstelDeelopdrachtRegel);
        }

        if(! StringUtils.equals(VoorstelDeelopdrachtStatus.Status.IN_OPMAAK.getValue(), voorstelDeelopdracht.getStatus())) {
            VoorstelDeelopdrachtHistorie voorstelDeelopdrachtHistorie = new VoorstelDeelopdrachtHistorie();
            voorstelDeelopdrachtHistorie.setDossierhouder(dossierhouderDAO.get(gebruiker));
            voorstelDeelopdrachtHistorie.setStatus(voorstelDeelopdracht.getStatus());
            voorstelDeelopdrachtHistorie.setDatum(Calendar.getInstance());
            voorstelDeelopdrachtHistorie.setVoorstelDeelopdracht(voorstelDeelopdracht);
            voorstelDeelopdrachtHistorieDao.save(voorstelDeelopdrachtHistorie);
        }
        return voorstelDeelopdracht.getId();
    }

    public void save(VoorstelDeelopdracht voorstelDeelopdracht) {
         voorstelDeelopdrachtDao.save(voorstelDeelopdracht);
    }

    public void verwijderRegel(Long regelId) {
        voorstelDeelopdrachtRegelDao.delete(regelId);
    }

    public List<VoorstelDeelopdracht> getAll(Long offerteId) {
        return voorstelDeelopdrachtDao.getForOfferte(offerteId);
    }

    public List<DeelOpdracht> getAllForBestek(Long bestekId) {
        return voorstelDeelopdrachtDao.getForBestek(bestekId);
    }

    public String getWebloketLink(VoorstelDeelopdracht voorstelDeelopdracht) {
        return urlWebloket+"/webloket/offerte/"+voorstelDeelopdracht.getOfferte().getId()+"/voorstel/"+voorstelDeelopdracht.getId();
    }
}
