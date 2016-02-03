package be.ovam.art46.service.schuldvordering;

import be.ovam.art46.dao.AanvraagSchuldvorderingDAO;
import be.ovam.art46.dao.OfferteDao;
import be.ovam.art46.dto.ReportViewRegelDto;
import be.ovam.art46.dto.SchuldvorderingRegelDto;
import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.art46.service.meetstaat.impl.MeetstaatExportPdfServiceImpl;
import be.ovam.pad.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
        LinkedHashMap<String, ReportViewRegelDto> regels = createHeaderReportViewRegel(offerte);
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

    private LinkedHashMap<String, ReportViewRegelDto> createHeaderReportViewRegel(Offerte offerte) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<OfferteRegel> offerteRegels = offerte.getOfferteRegels();

        LinkedHashMap<String, ReportViewRegelDto> regels = new LinkedHashMap<String, ReportViewRegelDto>();
        Collections.sort(offerteRegels);
        for (OfferteRegel offerteRegel : offerteRegels) {
            if (StringUtils.isNotEmpty(offerteRegel.getPostnr())) {
                ReportViewRegelDto reportViewRegelDto = new ReportViewRegelDto();
                reportViewRegelDto.setTaak(offerteRegel.getTaak());
                reportViewRegelDto.setPostnr(offerteRegel.getPostnr());
                reportViewRegelDto.setRegelTotaalOfferte(offerteRegel.getRegelTotaalInclBTW());
                regels.put(offerteRegel.getPostnr(), reportViewRegelDto);
            }
        }
        ReportViewRegelDto herzieningReportViewRegelDto = new ReportViewRegelDto();
        herzieningReportViewRegelDto.setTaak("Herziening");
        herzieningReportViewRegelDto.setPostnr("");
        regels.put("h", herzieningReportViewRegelDto);
        return regels;
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
        LinkedHashMap<String, ReportViewRegelDto> regels = createHeaderReportViewRegel(offerte);
        addTotaalRegel(regels, offerte.getTotaalInclBtw());
        if (deelOpdracht.getVoorstelDeelopdracht() != null) {
            addVoorstelDeelopdracht(deelOpdracht.getVoorstelDeelopdracht(), regels);
        }
        verwerkSchuldvorderingRegels(regels, aanvraagSchuldvorderingen, true);
        return regels;
    }

    public void createExportOFferte(OutputStream outputStream, Long offerteId, Integer deelOpdrachtId) throws Exception {
        Offerte offerte = offerteDao.get(offerteId);
        LinkedHashMap<String, ReportViewRegelDto> reportViewDto = getReportViewDtoForOfferte(offerteId);
        String title = "Schuldvorderingen offerte " + offerte.getInzender();
        createExport(reportViewDto, outputStream, title, 0);

    }

    public void createExport(LinkedHashMap<String, ReportViewRegelDto> reportViewDto, OutputStream outputStream, String title, Integer deelopdrachtId) {
        ArrayList<ReportViewRegelDto> reportViewRegelDtos = new ArrayList<ReportViewRegelDto>(reportViewDto.values());
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        HSSFFont font_kleiner = getHssfFontKleiner(workbook);
        HSSFCellStyle styleGetal = getHssfCellStyleGetal(sheet);
        HSSFCellStyle kolomHoofdingStijl = getHssfCellStyle(workbook, font_kleiner);
        HSSFCellStyle kolomTitleStijl = getHssfCellStyle(workbook, getHssfFontTitle(workbook));
        HSSFCellStyle kolomNormalStijl = getHssfCellStyle(workbook, font_kleiner);
        sheet.createRow(0);
        HSSFRow titleRow = sheet.createRow(1);
        titleRow.createCell(0);
        titleRow.setRowStyle(kolomTitleStijl);
        createCell(title, kolomTitleStijl, titleRow);
        sheet.createRow(2);
        HSSFRow headerRow1 = sheet.createRow(3);
        List<String> headers1 = new ArrayList<String>();
        headers1.add("Postnr");
        headers1.add("Taak");
        if (deelopdrachtId > 0) {
            headers1.add("Voorstel totaal");
        } else {
            headers1.add("Offerte totaal");
        }
        headers1.add("Totaal");
        for (SchuldvorderingRegelDto schuldvorderingRegelDto : reportViewRegelDtos.get(0).getSchuldvorderingRegelDtoList()) {
            headers1.add(schuldvorderingRegelDto.getSchuldvorderingNr());
        }
        createHeaderRow(kolomHoofdingStijl, headerRow1, 0, headers1);

        int rowNumber = 4;
        for (ReportViewRegelDto regel : reportViewRegelDtos) {
            HSSFRow meetstaatRegelRow = sheet.createRow(rowNumber);
            meetstaatRegelRow.setRowStyle(kolomNormalStijl);
            addCell(regel.getPostnr(), meetstaatRegelRow, 0);
            addCell(regel.getTaak(), meetstaatRegelRow, 1);
            int i = 2;
            if (deelopdrachtId > 0) {
                addCell(regel.getVoorstelDeelopdrachtTotaal(), meetstaatRegelRow, i++, styleGetal);
            } else {
                addCell(regel.getRegelTotaalOfferte(), meetstaatRegelRow, i++, styleGetal);
            }
            addCell(regel.getTotaalViewRegel(), meetstaatRegelRow, i++, styleGetal);
            List<SchuldvorderingRegelDto> schuldvorderingRegelDtoList = regel.getSchuldvorderingRegelDtoList();
            for (SchuldvorderingRegelDto schuldvorderingRegelDto : schuldvorderingRegelDtoList) {
                addCell(schuldvorderingRegelDto.getRegelTotaal(), meetstaatRegelRow, i, styleGetal);
                i++;
            }
            rowNumber++;
        }

        try {
            workbook.write(outputStream);
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
        createExport(reportViewDto, op, title, deelopdrachtId);
    }

    private void addCell(String tekst, HSSFRow meetstaatRegelRow, int positie) {
        HSSFCell cell = meetstaatRegelRow.createCell(positie);
        cell.setCellValue(tekst);
    }

    private void createCell(String title, HSSFCellStyle kolomTitleStijl, HSSFRow titleRow) {
        HSSFCell titleCell = titleRow.createCell(1);
        titleCell.setCellStyle(kolomTitleStijl);
        titleCell.setCellValue(title);
    }

    private void addCell(BigDecimal getal, HSSFRow meetstaatRegelRow, int positie, HSSFCellStyle styleGetal) {
        HSSFCell cell = meetstaatRegelRow.createCell(positie);
        if (getal != null) {
            cell.setCellValue(getal.doubleValue());
        }
        cell.setCellStyle(styleGetal);
    }

    private void createHeaderRow(HSSFCellStyle kolomHoofdingStijl, HSSFRow headerRow, int i, List<String> headers) {
        for (String header : headers) {
            HSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(header);
            headerCell.setCellStyle(kolomHoofdingStijl);
            i++;
        }
    }

    private HSSFCellStyle getHssfCellStyle(HSSFWorkbook workbook, HSSFFont font) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    private HSSFFont getHssfFontTitle(HSSFWorkbook workbook) {
        HSSFFont font_title = workbook.createFont();
        short hoogte_title = 12;
        font_title.setFontHeightInPoints(hoogte_title);
        font_title.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font_title.setUnderline(HSSFFont.U_NONE);
        return font_title;
    }

    private HSSFFont getHssfFontKleiner(HSSFWorkbook workbook) {
        HSSFFont font_kleiner = workbook.createFont();
        short hoogte_kleiner = 10;
        font_kleiner.setFontHeightInPoints(hoogte_kleiner);
        font_kleiner.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font_kleiner.setUnderline(HSSFFont.U_NONE);
        return font_kleiner;
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

    private HSSFCellStyle getHssfCellStyleGetal(HSSFSheet sheet) {
        HSSFCellStyle styleGetal = sheet.getWorkbook().createCellStyle();
        HSSFDataFormat cf = sheet.getWorkbook().createDataFormat();
        styleGetal.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        styleGetal.setDataFormat(cf.getFormat("#,##0.00"));
        return styleGetal;
    }

}

