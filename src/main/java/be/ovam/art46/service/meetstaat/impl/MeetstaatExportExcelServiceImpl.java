package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.model.OffertesExport;
import be.ovam.art46.model.OffertesExportRegel;
import be.ovam.art46.service.meetstaat.MeetstaatExportExcelService;
import be.ovam.pad.model.*;
import be.ovam.pad.util.excel.ExcelExportUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Koen Corstjens on 30-8-13.
 */
@Service
public class MeetstaatExportExcelServiceImpl extends MeetstaatExportServiceImpl implements MeetstaatExportExcelService {

    private Logger log = Logger.getLogger(MeetstaatExportPdfServiceImpl.class);

    @Override
    public void createMeetstaatExport(String title, OutputStream outputStream, List<GenericRegel> regels, BigDecimal btwTarief, boolean draft) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        HSSFFont font_kleiner = ExcelExportUtil.getHssfFontKleiner(workbook);
        HSSFCellStyle styleGetal = ExcelExportUtil.getHssfCellStyleGetal(sheet);
        HSSFCellStyle kolomHoofdingStijl = ExcelExportUtil.getHssfCellStyle(workbook, font_kleiner);
        HSSFCellStyle kolomTitleStijl = ExcelExportUtil.getHssfCellStyle(workbook, ExcelExportUtil.getHssfFontTitle(workbook));
        HSSFCellStyle kolomNormalStijl = ExcelExportUtil.getHssfCellStyle(workbook, font_kleiner);

        ExcelExportUtil.createHeaderFile(title, sheet, kolomHoofdingStijl, kolomTitleStijl, Arrays.asList(HEADERS));

        int rowNumber = 4;
        for (GenericRegel genericRegel : regels) {
            HSSFRow meetstaatRegelRow = sheet.createRow(rowNumber);
            meetstaatRegelRow.setRowStyle(kolomNormalStijl);
            ExcelExportUtil.addCell(genericRegel.getCleanPostnr(), meetstaatRegelRow, 0);
            ExcelExportUtil.addCell(genericRegel.getTaak(), meetstaatRegelRow, 1);
            ExcelExportUtil.addCell(genericRegel.getDetails(), meetstaatRegelRow, 2);
            ExcelExportUtil.addCell(genericRegel.getType(), meetstaatRegelRow, 3);
            ExcelExportUtil.addCell(genericRegel.getEenheid(), meetstaatRegelRow, 4);
            ExcelExportUtil.addCell(genericRegel.getAantal(), meetstaatRegelRow, 5, styleGetal);
            if (draft || StringUtils.equals(MeetstaatRegel.REGEL_TYPE_SPM, genericRegel.getType())) {
                ExcelExportUtil.addCell(genericRegel.getEenheidsprijs(), meetstaatRegelRow, 6, styleGetal);
                if (genericRegel.getRegelTotaal() == null) {
                    ExcelExportUtil.addCell(genericRegel.getPostTotaal(), meetstaatRegelRow, 7, styleGetal);
                } else {
                    ExcelExportUtil.addCell(genericRegel.getRegelTotaal(), meetstaatRegelRow, 7, styleGetal);
                }
            }
            rowNumber++;
        }
        if (!draft) {
            addOverzichtPrijsbieding(btwTarief, sheet, rowNumber, workbook);
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


    public void createOffertesExport(Long bestekId, OutputStream op) throws Exception {
        Bestek bestek = bestekService.getBestek(bestekId);
        OffertesExport offertesExport = meetstaatOfferteService.getOffertesRegelsForBestek(bestekId);
        String titel = "Offertes  voor bestek " + bestek.getBestek_nr() + " - " + bestek.getOmschrijving();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(titel);
        HSSFFont font_kleiner = ExcelExportUtil.getHssfFontKleiner(workbook);
        HSSFCellStyle styleGetal = ExcelExportUtil.getHssfCellStyleGetal(sheet);
        HSSFCellStyle kolomHoofdingStijl = ExcelExportUtil.getHssfCellStyle(workbook, font_kleiner);
        HSSFCellStyle kolomTitleStijl = ExcelExportUtil.getHssfCellStyle(workbook, ExcelExportUtil.getHssfFontTitle(workbook));
        HSSFCellStyle kolomNormalStijl = ExcelExportUtil.getHssfCellStyle(workbook, font_kleiner);
        List<String> headers1 = new ArrayList<String>();
        for (int i = 0; i <= 5; i++) {
            headers1.add(StringUtils.EMPTY);
        }
        List<String> headers2 = new ArrayList<String>(Arrays.asList(HEADERS_OFFERTES_EXPORT));
        for (Offerte offerte : offertesExport.getOffertes()) {
            headers1.add(offerte.getInzender());
            headers2.addAll(Arrays.asList(HEADERS_OFFERTES_EXPORT_OFFERTE));
            for (int i = 0; i < 2; i++) {
                headers1.add(StringUtils.EMPTY);
            }
        }
        ExcelExportUtil.createHeaderFile(titel, sheet, kolomHoofdingStijl, kolomTitleStijl, headers1, headers2);
        int rowNumber = 5;
        for (OffertesExportRegel offertesExportRegel : offertesExport.getOffertesExportRegels()) {
            HSSFRow meetstaatRegelRow = sheet.createRow(rowNumber);
            meetstaatRegelRow.setRowStyle(kolomNormalStijl);
            ExcelExportUtil.addCell(offertesExportRegel.getMeetstaatRegel().getCleanPostnr(), meetstaatRegelRow, 0);
            ExcelExportUtil.addCell(offertesExportRegel.getMeetstaatRegel().getTaak(), meetstaatRegelRow, 1);
            ExcelExportUtil.addCell(offertesExportRegel.getMeetstaatRegel().getDetails(), meetstaatRegelRow, 2);
            ExcelExportUtil.addCell(offertesExportRegel.getMeetstaatRegel().getType(), meetstaatRegelRow, 3);
            ExcelExportUtil.addCell(offertesExportRegel.getMeetstaatRegel().getEenheid(), meetstaatRegelRow, 4);
            ExcelExportUtil.addCell(offertesExportRegel.getAantal(), meetstaatRegelRow, 5, styleGetal);
            int kolomnummer = 6;
            for (OfferteRegel offerteRegel : offertesExportRegel.getOfferteRegels()) {
                ExcelExportUtil.addCell(offerteRegel.getAantal(), meetstaatRegelRow, kolomnummer++, styleGetal);
                ExcelExportUtil.addCell(offerteRegel.getEenheidsprijs(), meetstaatRegelRow, kolomnummer++, styleGetal);
                ExcelExportUtil.addCell(offerteRegel.getRegelTotaal(), meetstaatRegelRow, kolomnummer++, styleGetal);
            }
            rowNumber++;
        }
        try {
            workbook.write(op);
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


    private void addOverzichtPrijsbieding(BigDecimal btwTarief, HSSFSheet sheet, int rowNumber, HSSFWorkbook workbook) {
        if (btwTarief == null) {
            btwTarief = new BigDecimal(21);
        }
        sheet.createRow(rowNumber++);
        sheet.createRow(rowNumber++);

        HSSFRow meetstaatRegelRow = sheet.createRow(rowNumber++);
        ExcelExportUtil.addCell(" ", meetstaatRegelRow, 0);

        ExcelExportUtil.addCellBold("Overzicht Prijsbieding: ", meetstaatRegelRow, 1, workbook);
        meetstaatRegelRow = sheet.createRow(rowNumber++);
        ExcelExportUtil.addCell(" ", meetstaatRegelRow, 0);
        ExcelExportUtil.addCell("Totaal (excl. BTW) : ", meetstaatRegelRow, 1);
        meetstaatRegelRow = sheet.createRow(rowNumber++);
        ExcelExportUtil.addCell(" ", meetstaatRegelRow, 0);
        ExcelExportUtil.addCell("BTW (" + btwTarief.toString() + " %)", meetstaatRegelRow, 1);
        meetstaatRegelRow = sheet.createRow(rowNumber++);
        ExcelExportUtil.addCell(" ", meetstaatRegelRow, 0);
        ExcelExportUtil.addCell("Algemeen totaal (incl. BTW)", meetstaatRegelRow, 1);
        meetstaatRegelRow = sheet.createRow(rowNumber++);
        ExcelExportUtil.addCell(" ", meetstaatRegelRow, 0);
        meetstaatRegelRow = sheet.createRow(rowNumber++);
        ExcelExportUtil.addCell(" ", meetstaatRegelRow, 0);
        meetstaatRegelRow = sheet.createRow(rowNumber++);
        ExcelExportUtil.addCell(" ", meetstaatRegelRow, 0);
        ExcelExportUtil.addCellBold("Naam en adres van de inschrijver: ", meetstaatRegelRow, 1, workbook);
    }

}
