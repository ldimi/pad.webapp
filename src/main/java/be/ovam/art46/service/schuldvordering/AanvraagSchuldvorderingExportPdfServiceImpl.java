package be.ovam.art46.service.schuldvordering;

import be.ovam.pad.model.AanvraagSchuldvordering;
import be.ovam.pad.model.AsvDO;
import be.ovam.pad.model.AsvRegelDO;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.util.BigDecimalFormatter;
import be.ovam.pad.util.pdf.PdfExportUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.math.BigDecimal;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Created by Koen on 23/04/2014.
 */
@Service
public class AanvraagSchuldvorderingExportPdfServiceImpl
        extends AanvraagSchuldvorderingExportServiceImpl
        implements AanvraagSchuldvorderingExportService {

    public static final String[] HEADERS_PDF = {"Postnr", "Taak", "Details", "Type", "Eenheid", "Aantal", "Eenheidsprijs", "Bedrag", "Regel totaal", "Regel totaal Inc BTW"};

    private Logger log = Logger.getLogger(AanvraagSchuldvorderingExportPdfServiceImpl.class);

    @Autowired
    private AsvService asvService;
    
    @Override
    protected void createMeetstaatExport(String title, OutputStream outputStream, AanvraagSchuldvordering aanvraagSchuldvordering) {
        log.debug("Start aanmaken pdf");

        AsvDO asv = asvService.getAsv(aanvraagSchuldvordering.getId());
        
        
        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfExportUtil.addHeaderAndFooter(outputStream, document, title);
            document.open();
            document.addTitle(title);
            PdfPTable basicTable = new PdfPTable(1);
            basicTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            basicTable.addCell(PdfExportUtil.createCellNoBorder(createTable(asv)));
            basicTable.addCell(PdfExportUtil.createCellNoBorder(createTotalen(asv)));
            basicTable.addCell(PdfExportUtil.createCellNoBorder(PdfExportUtil.createLegende()));
            basicTable.setHeaderRows(0);
            document.add(basicTable);
        } catch (DocumentException de) {
            log.error("Error bij het aanmaken van pdf " + title + ": " + de, de);
        } finally {
            document.close();
        }
        log.debug("Einde aanmaken pdf");
    }


    private PdfPTable createTable(AsvDO asv) throws DocumentException {
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{12, 45, 40, 8, 25, 12, 18, 15, 15, 20});
        table.setHeaderRows(1);
        for (String header : HEADERS_PDF) {
            table.addCell(PdfExportUtil.addHeaderCell(header));
        }
        
        for (AsvRegelDO regel : asv.getAsvRegels()) {
            addRegel(table, regel);
        }
        
        return table;
    }


    private void addRegel(PdfPTable table, AsvRegelDO regel) {
        table.addCell(PdfExportUtil.buildCell(regel.getPostnr()));
        table.addCell(PdfExportUtil.buildCell(regel.getTaak()));
        table.addCell(PdfExportUtil.buildCell(regel.getDetails()));
        table.addCell(PdfExportUtil.buildCell(regel.getType()));
        table.addCell(PdfExportUtil.buildCell(regel.getEenheid()));
        if (regel.getType() == null) {
            table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
            table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
            table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
            table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
            table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
        } else {
            if (MeetstaatRegel.REGEL_TYPE_VH.equals(regel.getType())) {
                table.addCell(PdfExportUtil.buildCellCorrection(regel.getAfname(), regel.getGecorrigeerdafname()));
                table.addCell(PdfExportUtil.buildCellCorrection(regel.getEenheidsprijs(), regel.getGecorrigeerd_meerwerken_eenheidsprijs()));
                table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
            } else {
                table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
                table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
                table.addCell(PdfExportUtil.buildCellCorrection(regel.getAfnamebedrag(), regel.getGecorrigeerdafnamebedrag()));
            }
            table.addCell(PdfExportUtil.buildCell(regel.getRegelTotaal()));
            table.addCell(PdfExportUtil.buildCell(regel.getRegelTotaalInclBtw()));
        }
        if (StringUtils.isNotEmpty(regel.getOpmerking())) {
            table.addCell(PdfExportUtil.buildCell(StringUtils.EMPTY));
            PdfPCell pdfPCell = PdfExportUtil.buildCell(regel.getOpmerking());
            pdfPCell.setColspan(9);
            table.addCell(pdfPCell);
        }
    }


    private PdfPTable createTotalen(AsvDO asv) {

        BigDecimal excl_btw_bedrag = asv.getExcl_btw_bedrag();
        BigDecimal incl_btw_bedrag = null;
        if (asv.getVordering_correct_bedrag() != null) {
            incl_btw_bedrag = asv.getVordering_correct_bedrag();
        } else {
            incl_btw_bedrag = asv.getVordering_bedrag();
        }

        BigDecimal toeTePassenHerzieningBedrag = asv.getToetepassenHerzieningBedrag();
        BigDecimal totaalInclPrijsherziening = asv.getTotaalInclPrijsherziening();
        BigDecimal TotaalInclPrijsherzieningEnBoete = asv.getTotaalInclPrijsherzieningEnBoete();
        BigDecimal boeteBedrag = asv.getBoete_bedrag();


        PdfPTable pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        pdfPTable.addCell(PdfExportUtil.addHeaderCell("Overzicht Prijsbieding: "));
        pdfPTable.addCell(PdfExportUtil.addHeaderCell(" "));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal (excl. BTW): ", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(excl_btw_bedrag),
                PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal (Incl. BTW): ", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(incl_btw_bedrag), PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        if (toeTePassenHerzieningBedrag != null) {
            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Bedrag prijsherziening incl. BTW: ", PdfExportUtil.BASIC_CELL_FONT)));
            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(toeTePassenHerzieningBedrag), PdfExportUtil.BASIC_CELL_FONT)));
            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal bedrag incl. BTW en incl. prijsherziening:): ", PdfExportUtil.BASIC_CELL_FONT)));
            pdfPTable.addCell(PdfExportUtil.createCellNoBorderAlignRight(new Paragraph(BigDecimalFormatter.formatCurency(totaalInclPrijsherziening), PdfExportUtil.BASIC_CELL_FONT)));
            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        }

        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Bedrag boete excl. BTW: ", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(boeteBedrag), PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal bedrag incl. BTW, incl. prijsherziening en boete:): ", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(TotaalInclPrijsherzieningEnBoete), PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        return pdfPTable;
    }

//    private PdfPTable createTotalen(AanvraagSchuldvordering aanvraagSchuldvordering) {
//
//        Schuldvordering sv = aanvraagSchuldvordering.getSchuldvordering();
//
//        BigDecimal excl_btw_bedrag = new BigDecimal(sv.getExcl_btw_bedrag());
//        BigDecimal vordering_bedrag = new BigDecimal(sv.getVordering_bedrag());
//
//        BigDecimal toeTePassenHerzieningBedrag = null;
//        if (sv.getToetepassenHerzieningBedrag() != null) {
//            toeTePassenHerzieningBedrag = new BigDecimal(sv.getToetepassenHerzieningBedrag());
//        }
//        BigDecimal totaalInclPrijsherziening = new BigDecimal(sv.getTotaalInclPrijsherziening());
//        BigDecimal TotaalInclPrijsherzieningEnBoete = new BigDecimal(sv.getTotaalInclPrijsherzieningEnBoete());
//        BigDecimal boeteBedrag;
//        if (sv.getBoete_bedrag() != null) {
//            boeteBedrag = new BigDecimal(sv.getBoete_bedrag());
//        } else {
//            boeteBedrag = new BigDecimal(0.0);
//        }
//
//
//        PdfPTable pdfPTable = new PdfPTable(3);
//        pdfPTable.setWidthPercentage(100);
//        pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
//
//        pdfPTable.addCell(PdfExportUtil.addHeaderCell("Overzicht Prijsbieding: "));
//        pdfPTable.addCell(PdfExportUtil.addHeaderCell(" "));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
//
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal (excl. BTW): ", PdfExportUtil.BASIC_CELL_FONT)));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(excl_btw_bedrag),
//                PdfExportUtil.BASIC_CELL_FONT)));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
//
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal (Incl. BTW): ", PdfExportUtil.BASIC_CELL_FONT)));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(vordering_bedrag), PdfExportUtil.BASIC_CELL_FONT)));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
//
//        if (toeTePassenHerzieningBedrag != null) {
//            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Bedrag prijsherziening incl. BTW: ", PdfExportUtil.BASIC_CELL_FONT)));
//            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(toeTePassenHerzieningBedrag), PdfExportUtil.BASIC_CELL_FONT)));
//            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
//
//            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal bedrag incl. BTW en incl. prijsherziening:): ", PdfExportUtil.BASIC_CELL_FONT)));
//            pdfPTable.addCell(PdfExportUtil.createCellNoBorderAlignRight(new Paragraph(BigDecimalFormatter.formatCurency(totaalInclPrijsherziening), PdfExportUtil.BASIC_CELL_FONT)));
//            pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
//
//        }
//
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Bedrag boete excl. BTW: ", PdfExportUtil.BASIC_CELL_FONT)));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(boeteBedrag), PdfExportUtil.BASIC_CELL_FONT)));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
//
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal bedrag incl. BTW, incl. prijsherziening en boete:): ", PdfExportUtil.BASIC_CELL_FONT)));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(BigDecimalFormatter.formatCurency(TotaalInclPrijsherzieningEnBoete), PdfExportUtil.BASIC_CELL_FONT)));
//        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
//
//        return pdfPTable;
//    }

}
