package be.ovam.art46.service.meetstaat;

import be.ovam.art46.service.BestekService;
import be.ovam.pad.model.GenericRegel;
import com.itextpdf.text.DocumentException;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Koen Corstjens on 30-8-13.
 */
public interface MeetstaatExportService {

    public void createOfferteExport(Integer offerteId, OutputStream outputStream);

    public void createDraftExport(Long bestekId, OutputStream outputStream);

    public void createFinalExport(Long bestekId, OutputStream outputStream);

    public void createMeetstaatExport(String title, OutputStream outputStream, List<GenericRegel> meetstaatRegels, BigDecimal btw, boolean draft) throws DocumentException;

    public void setMeetstaatService(MeetstaatService meetstaatService);

    public void setBestekService(BestekService bestekService);

    void createOffertesExport(Long bestekId, OutputStream op) throws Exception;
}
