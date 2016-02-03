package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.model.OfferteForm;
import be.ovam.art46.service.BestekService;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.art46.service.meetstaat.MeetstaatService;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.GenericRegel;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.OfferteRegel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koen Corstjens on 30-8-13.
 */
public abstract class MeetstaatExportServiceImpl {
    public static final String[] HEADERS_PDF = {"Postnr", "Taak", "Details", "Type", "Eenheid", "Aantal", "Eenheidsprijs", "Detail totaal", "Subtotaal", "Totaal per post"};
    public static final String[] HEADERS = {"Postnr", "Taak", "Details", "Type", "Eenheid", "Aantal", "Eenheidsprijs", "Totaal per post"};
    public static final String[] HEADERS_OFFERTES_EXPORT = {"Postnr", "Taak", "Details", "Type", "Eenheid", "Aantal"};
    public static final String[] HEADERS_OFFERTES_EXPORT_OFFERTE = {"Aantal", "Eenheidsprijs", "Totaal "};
    @Autowired
    protected BestekService bestekService;
    @Autowired
    protected MeetstaatService meetstaatService;
    @Autowired
    protected MeetstaatOfferteService meetstaatOfferteService;
    private Logger log = Logger.getLogger(MeetstaatExportServiceImpl.class);

    public void createOfferteExport(Integer offerteId, OutputStream outputStream) {
        createOfferteExport(offerteId, outputStream, Boolean.TRUE);
    }

    public void createDraftExport(Long bestekId, OutputStream outputStream) {
        createExport(bestekId, outputStream, Boolean.TRUE);
    }

    public void createFinalExport(Long bestekId, OutputStream outputStream) {
        createExport(bestekId, outputStream, Boolean.FALSE);
    }

    private void createExport(Long bestekId, OutputStream outputStream, boolean draft) {
        Bestek bestek = bestekService.getBestek(bestekId);
        List<MeetstaatRegel> meetstaatRegels = meetstaatService.getAll(bestekId);
        List<GenericRegel> regels = new ArrayList<GenericRegel>();
        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            regels.add(meetstaatRegel);
        }
        log.debug("Meetstaat opgehaald");
        if (CollectionUtils.isNotEmpty(meetstaatRegels)) {
            createMeetstaatExport(getTitle(bestek.getBestek_nr(), bestek.getOmschrijving(), draft), outputStream, regels, bestek.getBtw_tarief(), draft);
        }
    }

    private void createOfferteExport(Integer offerteId, OutputStream outputStream, boolean draft) {
        OfferteForm offerteForm = meetstaatOfferteService.getOfferte((long) offerteId);
        Bestek bestek = bestekService.getBestek(offerteForm.getOfferte().getBestekId());
        List<GenericRegel> offerteRegels = new ArrayList<GenericRegel>();
        for (OfferteRegel offerteRegel : offerteForm.getOfferteRegels()) {
            offerteRegels.add(offerteRegel);
        }
        log.debug("Meetstaat opgehaald");
        if (CollectionUtils.isNotEmpty(offerteRegels)) {
            String inzender = offerteForm.getOfferte().getInzender().replace(" ", "_");
            String titel = "Offerte van " + inzender +
                    ", voor bestek : " + bestek.getBestek_nr() + " - " + bestek.getOmschrijving();
            createMeetstaatExport(titel, outputStream, offerteRegels, bestek.getBtw_tarief(), draft);
        }
    }

    protected String getTitle(String bestekNr, String bestekNaam, boolean draft) {
        if (StringUtils.isEmpty(bestekNaam)) {
            bestekNaam = StringUtils.EMPTY;
        }
        if (draft) {
            return "DRAFT Samenvattende Opmetingsstaat - " + bestekNr + " - " + bestekNaam;
        } else {
            return "Samenvattende Opmetingsstaat - " + bestekNr + " - " + bestekNaam;
        }
    }

    protected abstract void createMeetstaatExport(String titel, OutputStream outputStream, List<GenericRegel> meetstaatRegels, BigDecimal btwTarief, boolean draft);

    public void setMeetstaatService(MeetstaatService meetstaatService) {
        this.meetstaatService = meetstaatService;
    }

    public void setBestekService(BestekService bestekService) {
        this.bestekService = bestekService;
    }

    public void setMeetstaatOfferteService(MeetstaatOfferteService meetstaatOfferteService) {
        this.meetstaatOfferteService = meetstaatOfferteService;
    }
}
