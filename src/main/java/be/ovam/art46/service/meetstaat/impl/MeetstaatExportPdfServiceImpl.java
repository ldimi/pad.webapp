package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.service.meetstaat.MeetstaatExportPdfService;
import be.ovam.pad.model.GenericRegel;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.util.pdf.PdfExportUtil;
import be.ovam.pad.util.pdf.PdfHeaderFooter;
import be.ovam.pad.util.pdf.PdfUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by koencorstjens on 14-8-13.
 */
@Service
public class MeetstaatExportPdfServiceImpl extends MeetstaatExportServiceImpl implements MeetstaatExportPdfService {

    public static final Font WATERMARK_FONT = PdfUtil.getWatermarkFont();
    private Logger log = Logger.getLogger(MeetstaatExportPdfServiceImpl.class);

    public MeetstaatExportPdfServiceImpl() {
        super();
    }

    public void createMeetstaatExport(String title, OutputStream outputStream, List<GenericRegel> genericRegels, BigDecimal btw, boolean draft) {
        log.debug("Start aanmaken pdf");
        Document document = new Document(PageSize.A4.rotate());
        try {
            addHeaderAndFooter(outputStream, document, title, draft);
            document.open();
            document.addTitle(title);
            PdfPTable basicTable = new PdfPTable(1);
            basicTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            basicTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(title, PdfExportUtil.TITLE_FONT)));
            basicTable.addCell(PdfExportUtil.createCellNoBorder(PdfExportUtil.createInschrijverGegevens()));
            basicTable.addCell(PdfExportUtil.createCellNoBorder(createTable(genericRegels, draft)));
            basicTable.addCell(PdfExportUtil.createCellNoBorder(PdfExportUtil.createLegende()));
            if (!draft) {
                basicTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(" ", PdfExportUtil.TITLE_FONT)));
                basicTable.addCell(PdfExportUtil.createCellNoBorder(createOverzichtPrijsbieding(btw)));
                basicTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph(" ", PdfExportUtil.TITLE_FONT)));
                basicTable.addCell(PdfExportUtil.createCellNoBorder(createHandEnDagTekening()));
            }
            basicTable.setHeaderRows(0);
            document.add(basicTable);
        } catch (DocumentException de) {
            log.error("Error bij het aanmaken van pdf " + title + ": " + de, de);
        } finally {
            document.close();
        }
        log.debug("Einde aanmaken pdf");
    }

    public void createOffertesExport(Long bestekId, OutputStream op) {

    }

    private PdfPTable createHandEnDagTekening() {
        PdfPTable pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Hand- en dagtekening inschrijver:", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("......................................", PdfExportUtil.BASIC_CELL_FONT)));
        return pdfPTable;
    }


    private PdfPTable createOverzichtPrijsbieding(BigDecimal btwTarief) {
        if (btwTarief == null) {
            btwTarief = new BigDecimal(21);
        }
        PdfPTable pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        pdfPTable.addCell(PdfExportUtil.addHeaderCell("Overzicht Prijsbieding: "));
        pdfPTable.addCell(PdfExportUtil.addHeaderCell(" "));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Totaal (excl. BTW): ", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("......................................", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("BTW (" + btwTarief.toString() + " %) ", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("......................................", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("Algemeen totaal (incl. BTW)", PdfUtil.getBasicCellFontBold())));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("......................................", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));
        pdfPTable.addCell(PdfExportUtil.createCellNoBorder(new Paragraph("", PdfExportUtil.BASIC_CELL_FONT)));

        return pdfPTable;
    }

    private void addHeaderAndFooter(OutputStream outputStream, Document document, String title, boolean draft) throws DocumentException {
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        PdfHeaderFooter event = new PdfHeaderFooter(title, "Pagina %d");
        pdfWriter.setBoxSize("art", new Rectangle(0, 40, 600, 900));
        pdfWriter.setPageEvent(event);
        if (draft) {
            pdfWriter.setPageEvent(new Watermark());
        }
    }

    private PdfPTable createTable(List<GenericRegel> regelList, boolean draft) throws DocumentException {
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{12, 45, 40, 8, 25, 12, 18, 15, 15, 20});
        table.setHeaderRows(1);
        for (String header : HEADERS_PDF) {
            table.addCell(PdfExportUtil.addHeaderCell(header));
        }
        for (GenericRegel genericRegel : regelList) {
            table.addCell(PdfExportUtil.addCell(genericRegel.getCleanPostnr()));
            table.addCell(PdfExportUtil.addCell(genericRegel.getTaak()));
            table.addCell(PdfExportUtil.addCell(genericRegel.getDetails()));
            table.addCell(PdfExportUtil.addCell(genericRegel.getType()));
            table.addCell(PdfExportUtil.addCell(genericRegel.getEenheid()));
            table.addCell(PdfExportUtil.addCell(genericRegel.getAantal()));
            if (draft || StringUtils.equals(MeetstaatRegel.REGEL_TYPE_SPM, genericRegel.getType())) {
                table.addCell(PdfExportUtil.addCell(genericRegel.getEenheidsprijs()));
                table.addCell(PdfExportUtil.addCell(genericRegel.getDetailTotaal()));
                table.addCell(PdfExportUtil.addCell(genericRegel.getSubTotaal()));
                table.addCell(PdfExportUtil.addCell(genericRegel.getPostTotaal()));

            } else {
                table.addCell(PdfExportUtil.addCell(StringUtils.EMPTY));
                table.addCell(PdfExportUtil.addCell(StringUtils.EMPTY));
                table.addCell(PdfExportUtil.addCell(StringUtils.EMPTY));
                table.addCell(PdfExportUtil.addCell(StringUtils.EMPTY));
            }
        }
        return table;
    }

    class Watermark extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContentUnder(),
                    Element.ALIGN_CENTER, new Phrase("DRAFT", WATERMARK_FONT),
                    421, 260, writer.getPageNumber() % 2 == 1 ? 45 : -45);
        }
    }
}
