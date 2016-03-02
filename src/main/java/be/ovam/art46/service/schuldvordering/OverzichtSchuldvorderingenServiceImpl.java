package be.ovam.art46.service.schuldvordering;

import be.ovam.art46.dao.AanvraagSchuldvorderingDAO;
import be.ovam.art46.dao.OfferteDao;
import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.art46.service.meetstaat.impl.MeetstaatExportPdfServiceImpl;
import be.ovam.pad.dto.ReportViewRegelDto;
import be.ovam.pad.dto.SchuldvorderingRegelDto;
import be.ovam.pad.model.*;
import be.ovam.pad.util.excel.ExcelExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Koen on 12/01/2015.
 */
@Service
public class OverzichtSchuldvorderingenServiceImpl implements OverzichtSchuldvorderingenService {
    private Logger log = Logger.getLogger(MeetstaatExportPdfServiceImpl.class);

    @Autowired
    private MeetstaatOfferteService meetstaatOfferteService;
    @Autowired
    private AanvraagSchuldvorderingDAO aanvraagSchuldvorderingDAO;
    @Autowired
    private DeelOpdrachtService deelOpdrachtService;
    @Autowired
    private OfferteDao offerteDao;


    public LinkedHashMap<String, ReportViewRegelDto> getReportViewDtoForOfferte(Long offerteId) throws Exception {
        Offerte offerte = meetstaatOfferteService.get(offerteId);
        LinkedHashMap<String, ReportViewRegelDto> regels = ExcelExportUtil.createHeaderReportViewRegel(offerte);
        BigDecimal totaalInclBtw = offerte.getTotaalInclBtw();
        addTotaalRegel(regels, totaalInclBtw);
        List<AanvraagSchuldvordering> aanvraagSchuldvorderingen = aanvraagSchuldvorderingDAO.getAllForOfferte(offerteId);
        verwerkSchuldvorderingRegels(regels, aanvraagSchuldvorderingen, false);
        return regels;
    }

    private void addTotaalRegel(LinkedHashMap<String, ReportViewRegelDto> regels, BigDecimal totaalInclBtw) {
        ReportViewRegelDto offerteTotaalReportViewRegelDto = new ReportViewRegelDto();
        offerteTotaalReportViewRegelDto.setTaak("Totaal");
        offerteTotaalReportViewRegelDto.setRegelTotaalOfferte(totaalInclBtw);
        regels.put("t", offerteTotaalReportViewRegelDto);
    }



    private void verwerkSchuldvorderingRegels(LinkedHashMap<String, ReportViewRegelDto> regels, List<AanvraagSchuldvordering> aanvraagSchuldvorderingen, boolean deelopdracht) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ReportViewRegelDto totaalReportViewRegel = regels.get("t");
        ReportViewRegelDto herzienningenReportViewRegel = regels.get("h");
        if (CollectionUtils.isNotEmpty(aanvraagSchuldvorderingen)) {
            BigDecimal totaalHerzienningen = new BigDecimal(0);
            List<AanvraagSchuldvordering> teverwerken = new ArrayList<AanvraagSchuldvordering>();
            for (AanvraagSchuldvordering aanvraagSchuldvordering : aanvraagSchuldvorderingen) {
                if ((!StringUtils.equals(aanvraagSchuldvordering.getStatus(), SchuldvorderingStatusEnum.IN_OPMAAK.getValue())) && (!aanvraagSchuldvordering.getSchuldvordering().isAfgekeurd())) {
                    teverwerken.add(aanvraagSchuldvordering);
                }
            }
            int huidigeKolom = 0;
            int meerwerkenTeller = 1;
            for (AanvraagSchuldvordering aanvraagSchuldvordering : teverwerken) {
                String vorderingNr = aanvraagSchuldvordering.getSchuldvordering().getNummer();
                for (AanvraagSchuldvorderingRegel aanvraagSchuldvorderingRegel : aanvraagSchuldvordering.getAanvraagSchuldvorderingRegels()) {
                    SchuldvorderingRegelDto schuldvorderingRegelDto = new SchuldvorderingRegelDto();
                    schuldvorderingRegelDto.setPostnr(aanvraagSchuldvorderingRegel.getPostnr());
                    schuldvorderingRegelDto.setTaak(aanvraagSchuldvorderingRegel.getTaak());
                    schuldvorderingRegelDto.setRegelTotaal(aanvraagSchuldvorderingRegel.getRegelTotaalInclBtw());
                    schuldvorderingRegelDto.setSchuldvorderingNr(vorderingNr);
                    ReportViewRegelDto reportViewRegelDto = regels.get(schuldvorderingRegelDto.getPostnr());
                    //voorstel
                    if (deelopdracht && reportViewRegelDto == null && aanvraagSchuldvorderingRegel.getVoorstelDeelopdrachtRegel() != null) {
                        reportViewRegelDto = regels.get("vd" + aanvraagSchuldvorderingRegel.getVoorstelDeelopdrachtRegel().getId());
                    }
                    if (reportViewRegelDto != null && schuldvorderingRegelDto.getRegelTotaal() != null) {
                        reportViewRegelDto.setTotaalViewRegel(reportViewRegelDto.getTotaalViewRegel().add(schuldvorderingRegelDto.getRegelTotaal()));
                        reportViewRegelDto.getSchuldvorderingRegelDtoList().add(schuldvorderingRegelDto);
                    }
                    // meerwerken

                    if (aanvraagSchuldvorderingRegel.isMeerwerkenRegel()) {
                        ReportViewRegelDto meerwerkenRegel = new ReportViewRegelDto();
                        meerwerkenRegel.setPostnr("m " + meerwerkenTeller);
                        meerwerkenRegel.setTaak(aanvraagSchuldvorderingRegel.getTaak());
                        meerwerkenRegel.setTotaalViewRegel(aanvraagSchuldvorderingRegel.getRegelTotaalInclBtw());
                        for (int i = 0; i < teverwerken.size(); i++) {
                            SchuldvorderingRegelDto meerwerkenRegelDto = new SchuldvorderingRegelDto();
                            meerwerkenRegelDto.setPostnr(aanvraagSchuldvorderingRegel.getPostnr());
                            meerwerkenRegelDto.setTaak(aanvraagSchuldvordering.getTitle());
                            if (i == huidigeKolom) {
                                meerwerkenRegelDto.setRegelTotaal(aanvraagSchuldvorderingRegel.getRegelTotaalInclBtw());
                            }
                            meerwerkenRegel.getSchuldvorderingRegelDtoList().add(meerwerkenRegelDto);
                        }
                        regels.put("m" + meerwerkenTeller, meerwerkenRegel);
                        meerwerkenTeller++;
                    }
                }
                SchuldvorderingRegelDto herzieningSchuldvorderingRegelDto = new SchuldvorderingRegelDto();
                Double toetepassenHerzieningBedrag = aanvraagSchuldvordering.getSchuldvordering().getToetepassenHerzieningBedrag();
                if (toetepassenHerzieningBedrag != null && herzieningSchuldvorderingRegelDto.getRegelTotaal() != null) {
                    totaalHerzienningen = totaalHerzienningen.add(new BigDecimal(toetepassenHerzieningBedrag));
                    herzienningenReportViewRegel.setTotaalViewRegel(totaalHerzienningen);
                    herzieningSchuldvorderingRegelDto.setRegelTotaal(new BigDecimal(toetepassenHerzieningBedrag).add(herzieningSchuldvorderingRegelDto.getRegelTotaal()));
                }
                herzienningenReportViewRegel.getSchuldvorderingRegelDtoList().add(herzieningSchuldvorderingRegelDto);

                SchuldvorderingRegelDto schuldvorderingRegelDto = new SchuldvorderingRegelDto();
                schuldvorderingRegelDto.setRegelTotaal(new BigDecimal(aanvraagSchuldvordering.getSchuldvordering().getTotaalInclPrijsherziening()));
                totaalReportViewRegel.getSchuldvorderingRegelDtoList().add(schuldvorderingRegelDto);
                if (totaalReportViewRegel.getTotaalViewRegel() == null) {
                    totaalReportViewRegel.setTotaalViewRegel(new BigDecimal(0));
                }
                totaalReportViewRegel.setTotaalViewRegel(totaalReportViewRegel.getTotaalViewRegel().add(schuldvorderingRegelDto.getRegelTotaal()));
                huidigeKolom++;
            }
        }

        //verplaats naar laatste plaats
        regels.remove("h");
        regels.put("h", herzienningenReportViewRegel);
        regels.remove("t");
        regels.put("t", totaalReportViewRegel);
    }

    public LinkedHashMap<String, ReportViewRegelDto> getReportViewDtoForDeelopdracht(Integer deelOpdrachtId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        DeelOpdracht deelOpdracht = deelOpdrachtService.get(deelOpdrachtId);
        Offerte offerte = deelOpdracht.getOfferte();
        List<AanvraagSchuldvordering> aanvraagSchuldvorderingen = deelOpdracht.getAanvraagSchuldvorderingen();
        LinkedHashMap<String, ReportViewRegelDto> regels = ExcelExportUtil.createHeaderReportViewRegel(offerte);
        addTotaalRegel(regels, offerte.getTotaalInclBtw());
        if (deelOpdracht.getVoorstelDeelopdracht() != null) {
            addVoorstelDeelopdracht(deelOpdracht.getVoorstelDeelopdracht(), regels);
        }
        verwerkSchuldvorderingRegels(regels, aanvraagSchuldvorderingen, true);
        return regels;
    }

    public void createExportOfferte(OutputStream outputStream, Long offerteId, Integer deelOpdrachtId) throws Exception {
        Offerte offerte = offerteDao.get(offerteId);
        LinkedHashMap<String, ReportViewRegelDto> reportViewDto = getReportViewDtoForOfferte(offerteId);
        String title = "Schuldvorderingen offerte " + offerte.getInzender();
        try {
            ExcelExportUtil.createExport(reportViewDto, outputStream, title, 0);
        } catch (IOException e) {
            log.error(e, e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e, e);
                }
            }
        }
    }



    public void createExportDeelOpdracht(ServletOutputStream op, String title, Integer deelopdrachtId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        LinkedHashMap<String, ReportViewRegelDto> reportViewDto = reportViewDto = getReportViewDtoForDeelopdracht(deelopdrachtId);
        try {
            ExcelExportUtil.createExport(reportViewDto, op, title, deelopdrachtId);
        } catch (IOException e) {
            log.error(e, e);
        } finally {
            if (op != null) {
                try {
                    op.flush();
                    op.close();
                } catch (IOException e) {
                    log.error(e, e);
                }
            }
        }
    }


    private void addVoorstelDeelopdracht(VoorstelDeelopdracht voorstelDeelopdracht, LinkedHashMap<String, ReportViewRegelDto> regels) {
        if (voorstelDeelopdracht != null) {
            BigDecimal totaal = new BigDecimal(0);
            int teller = 1;
            for (VoorstelDeelopdrachtRegel voorstelDeelopdrachtRegel : voorstelDeelopdracht.getVoorstelDeelopdrachtRegels()) {
                ReportViewRegelDto reportViewRegelDto = regels.get(voorstelDeelopdrachtRegel.getPostnr());
                if (reportViewRegelDto == null) {
                    reportViewRegelDto = new ReportViewRegelDto();
                    reportViewRegelDto.setPostnr(voorstelDeelopdrachtRegel.getPostnr());
                    reportViewRegelDto.setTaak(voorstelDeelopdrachtRegel.getTaak());
                    reportViewRegelDto.setRegelTotaalOfferte(new BigDecimal(0));
                    if (StringUtils.isEmpty(voorstelDeelopdrachtRegel.getPostnr())) {
                        reportViewRegelDto.setPostnr("vd " + teller);
                    }
                    regels.put("vd" + voorstelDeelopdrachtRegel.getId(), reportViewRegelDto);
                }
                reportViewRegelDto.setVoorstelDeelopdrachtTotaal(voorstelDeelopdrachtRegel.getRegelTotaalInclBtw());
                if (voorstelDeelopdrachtRegel.getRegelTotaalInclBtw() != null) {
                    totaal = totaal.add(voorstelDeelopdrachtRegel.getRegelTotaalInclBtw());
                }
            }
            ReportViewRegelDto reportViewRegelDto = regels.get("t");
            reportViewRegelDto.setVoorstelDeelopdrachtTotaal(totaal);
        }
    }

}

