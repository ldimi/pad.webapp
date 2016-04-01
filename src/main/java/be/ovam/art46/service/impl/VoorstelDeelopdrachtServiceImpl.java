package be.ovam.art46.service.impl;

import be.ovam.art46.dao.DossierhouderDAO;
import be.ovam.art46.dao.VoorstelDeelopdrachtDao;
import be.ovam.art46.dao.VoorstelDeelopdrachtHistorieDao;
import be.ovam.art46.dao.VoorstelDeelopdrachtRegelDao;
import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.service.VoorstelDeelopdrachtService;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.pad.model.*;
import be.ovam.pad.util.excel.ExcelExportUtil;
import be.ovam.pad.util.pdf.PdfExportUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Koen on 2/05/2014.
 */
@Service
public class VoorstelDeelopdrachtServiceImpl implements VoorstelDeelopdrachtService {
    private Logger log = Logger.getLogger(VoorstelDeelopdrachtServiceImpl.class);
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
        if (voorstelDeelopdracht.getDeelOpdracht() != null) {
            DeelOpdracht deelOpdracht = voorstelDeelopdracht.getDeelOpdracht();
            if (deelOpdracht.getVoorstelDeelopdracht() == null) {
                deelOpdracht.setVoorstelDeelopdracht(voorstelDeelopdracht);
            }
            deelOpdrachtService.save(deelOpdracht);
        }
        voorstelDeelopdrachtDao.save(voorstelDeelopdracht);
        for (VoorstelDeelopdrachtRegel voorstelDeelopdrachtRegel : voorstelDeelopdracht.getVoorstelDeelopdrachtRegels()) {
            voorstelDeelopdrachtRegel.setVoorstelDeelopdracht(voorstelDeelopdracht);
            voorstelDeelopdrachtRegelDao.save(voorstelDeelopdrachtRegel);
        }

        if (!StringUtils.equals(VoorstelDeelopdrachtStatus.Status.IN_OPMAAK.getValue(), voorstelDeelopdracht.getStatus())) {
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
        return urlWebloket + "/webloket/offerte/" + voorstelDeelopdracht.getOfferte().getId() + "/voorstel/" + voorstelDeelopdracht.getId();
    }

    @Override
    public void exportToExcel(VoorstelDeelopdracht voorstelDeelopdracht, OutputStream outputStream) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Voorstel " + voorstelDeelopdracht.getNummer());
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 15 * 256);
        sheet.setColumnWidth(7, 15 * 256);
        sheet.setColumnWidth(8, 15 * 256);
        sheet.setColumnWidth(9, 15 * 256);

        HSSFFont font_title = workbook.createFont();
        short hoogte_title = 12;

        font_title.setFontHeightInPoints(hoogte_title);
        font_title.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font_title.setUnderline(HSSFFont.U_NONE);

        HSSFCellStyle kolomTitleStijl = workbook.createCellStyle();
        kolomTitleStijl.setFont(font_title);


        HSSFFont font_kleiner = workbook.createFont();
        short hoogte_kleiner = 10;

        font_kleiner.setFontHeightInPoints(hoogte_kleiner);
        font_kleiner.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font_kleiner.setUnderline(HSSFFont.U_NONE);
        HSSFCellStyle kolomHoofdingStijl = workbook.createCellStyle();
        kolomHoofdingStijl.setFont(font_kleiner);

        HSSFRow titleRow0 = sheet.createRow(0);
        addCell(titleRow0, 0, "Voorstel: ", kolomHoofdingStijl);
        HSSFRow titleRow1 = sheet.createRow(1);
        addCell(titleRow1, 0, "Nummer: ", kolomHoofdingStijl);
        addCell(titleRow1, 1, voorstelDeelopdracht.getNummer(), kolomHoofdingStijl);
        HSSFRow titleRow2 = sheet.createRow(2);
        addCell(titleRow2, 0, "Status: ", kolomHoofdingStijl);
        addCell(titleRow2, 1, voorstelDeelopdracht.getVoorstelDeelopdrachtStatus().getName(), kolomHoofdingStijl);
        HSSFRow titleRow3 = sheet.createRow(3);
        addCell(titleRow3, 0, "Offerte: ", kolomHoofdingStijl);
        addCell(titleRow3, 1, voorstelDeelopdracht.getOfferte().getInzender(), kolomHoofdingStijl);
        HSSFRow titleRow4 = sheet.createRow(4);
        addCell(titleRow4, 0, "Dossier: ", kolomHoofdingStijl);
        addCell(titleRow4, 1, voorstelDeelopdracht.getDossier().getTitel(), kolomHoofdingStijl);

        HSSFRow titleRow = sheet.createRow(6);
        HSSFCellStyle styleGetal = ExcelExportUtil.getHssfCellStyleGetal(sheet);
        addCell(titleRow, 0, "Postnr", kolomHoofdingStijl);
        addCell(titleRow, 1, "Taak", kolomHoofdingStijl);
        addCell(titleRow, 2, "Details", kolomHoofdingStijl);
        addCell(titleRow, 3, "Type", kolomHoofdingStijl);
        addCell(titleRow, 4, "Eenheid", kolomHoofdingStijl);
        addCell(titleRow, 5, "Eenheidsprijs / TP", kolomHoofdingStijl);
        addCell(titleRow, 6, "Aantal", kolomHoofdingStijl);
        addCell(titleRow, 7, "Bedrag", kolomHoofdingStijl);
        addCell(titleRow, 8, "Totaal", kolomHoofdingStijl);
        addCell(titleRow, 9, "Totaal incl. BTW", kolomHoofdingStijl);
        int projectCounter = 8;
        for (VoorstelDeelopdrachtRegel voorstelDeelopdrachtRegel : voorstelDeelopdracht.getVoorstelDeelopdrachtRegels()) {
            HSSFRow projectrow = sheet.createRow(projectCounter);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getPostnr(), projectrow, 0);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getTaak(), projectrow, 1);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getBeschrijving(), projectrow, 2);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getType(), projectrow, 3);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getEenheid(), projectrow, 4);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getEenheidsprijs(), projectrow, 5, styleGetal);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getAfname(), projectrow, 6, styleGetal);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getAfnameBedrag(), projectrow, 7, styleGetal);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getRegelTotaal(), projectrow, 8, styleGetal);
            ExcelExportUtil.addCell(voorstelDeelopdrachtRegel.getRegelTotaalInclBtw(), projectrow, 9, styleGetal);
            projectCounter++;
        }
        HSSFRow projectrow = sheet.createRow(projectCounter);
        ExcelExportUtil.addCell("", projectrow, 0);
        ExcelExportUtil.addCell("", projectrow, 1);
        ExcelExportUtil.addCell("", projectrow, 2);
        ExcelExportUtil.addCell("", projectrow, 3);
        ExcelExportUtil.addCell("", projectrow, 4);
        ExcelExportUtil.addCell("", projectrow, 5);
        ExcelExportUtil.addCell("", projectrow, 6);
        ExcelExportUtil.addCell("Totaal", projectrow, 7);
        ExcelExportUtil.addCell(voorstelDeelopdracht.getBedragExclBtw(), projectrow, 8, styleGetal);
        ExcelExportUtil.addCell(voorstelDeelopdracht.getBedragInclBtw(), projectrow, 9, styleGetal);

        workbook.write(outputStream);
    }

    @Override
    public void exportToPdf(VoorstelDeelopdracht voorstelDeelopdracht, ServletOutputStream op) throws DocumentException {
        Document document = new Document(PageSize.A4);
        String title = "Voorstel " + voorstelDeelopdracht.getNummer();
        try {
            PdfExportUtil.addHeaderAndFooter(op, document, title);
            document.open();

            PdfPTable basicTable = new PdfPTable(1);
            basicTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            basicTable.addCell(PdfExportUtil.createCellNoBorder(createTableRegels(voorstelDeelopdracht)));
            basicTable.addCell(PdfExportUtil.createCellNoBorder(createTableInfo(voorstelDeelopdracht)));
            basicTable.setHeaderRows(0);
            document.add(basicTable);
        } catch (DocumentException de) {
            log.error("Error bij het aanmaken van pdf " + title + ": " + de, de);
        } finally {
            document.close();
        }
    }

    private Element createTableRegels(VoorstelDeelopdracht voorstelDeelopdracht) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(40);
        table.setWidths(new int[]{50, 50});
        table.setHeaderRows(1);
        table.addCell(PdfExportUtil.buildCell("Voorstel: ") );
        table.addCell(PdfExportUtil.buildCell(""));
        table.addCell(PdfExportUtil.buildCell("Nummer"));
        table.addCell(PdfExportUtil.buildCell(voorstelDeelopdracht.getNummer()));
        table.addCell(PdfExportUtil.buildCell("Status"));
        table.addCell(PdfExportUtil.buildCell(voorstelDeelopdracht.getVoorstelDeelopdrachtStatus().getName()));
        table.addCell(PdfExportUtil.buildCell("Offerte"));
        table.addCell(PdfExportUtil.buildCell(voorstelDeelopdracht.getOfferte().getInzender()));
        table.addCell(PdfExportUtil.buildCell("Dossier"));
        table.addCell(PdfExportUtil.buildCell(voorstelDeelopdracht.getDossier().getTitel()));
        return table;
    }

    private Element createTableInfo(VoorstelDeelopdracht voorstelDeelopdracht) {
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.addCell(PdfExportUtil.addHeaderCell("Postnr: "));
        table.addCell(PdfExportUtil.addHeaderCell("Taak: "));
        table.addCell(PdfExportUtil.addHeaderCell("Details: "));
        table.addCell(PdfExportUtil.addHeaderCell("Type: "));
        table.addCell(PdfExportUtil.addHeaderCell("Eenheid: "));
        table.addCell(PdfExportUtil.addHeaderCell("Eenheidsprijs / TP: "));
        table.addCell(PdfExportUtil.addHeaderCell("Aantal: "));
        table.addCell(PdfExportUtil.addHeaderCell("Bedrag: "));
        table.addCell(PdfExportUtil.addHeaderCell("Totaal: "));
        table.addCell(PdfExportUtil.addHeaderCell("Totaal incl. BTW: "));
        for(VoorstelDeelopdrachtRegel voorstelDeelopdrachtRegel : voorstelDeelopdracht.getVoorstelDeelopdrachtRegels()){
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getPostnr()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getTaak()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getBeschrijving()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getType()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getEenheid()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getEenheidsprijs()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getAfname()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getAfnameBedrag()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getRegelTotaal()));
            table.addCell(PdfExportUtil.buildCell(voorstelDeelopdrachtRegel.getRegelTotaalInclBtw()));
        }
        table.addCell(PdfExportUtil.addHeaderCell(""));
        table.addCell(PdfExportUtil.addHeaderCell(""));
        table.addCell(PdfExportUtil.addHeaderCell(""));
        table.addCell(PdfExportUtil.addHeaderCell(""));
        table.addCell(PdfExportUtil.addHeaderCell(""));
        table.addCell(PdfExportUtil.addHeaderCell(""));
        table.addCell(PdfExportUtil.addHeaderCell(""));
        table.addCell(PdfExportUtil.addHeaderCell("Totaal"));
        table.addCell(PdfExportUtil.buildCell(voorstelDeelopdracht.getBedragExclBtw()));
        table.addCell(PdfExportUtil.buildCell(voorstelDeelopdracht.getBedragInclBtw()));
        return table;
    }

    private void addCell(HSSFRow row, int position, Object value, HSSFCellStyle style) {
        HSSFCell cel = row.createCell(position);
        if (value != null) {
            cel.setCellValue(new HSSFRichTextString(value.toString()));
        } else {
            cel.setCellValue(new HSSFRichTextString("-"));
        }
        if (style != null) {
            cel.setCellStyle(style);
        }
    }

    private void addCellGetal(HSSFRow row, int position, double value, HSSFCellStyle style) {
        HSSFCell cel = row.createCell(position);
        cel.setCellValue(value);


        if (style != null) {
            cel.setCellStyle(style);
        }
    }

}
