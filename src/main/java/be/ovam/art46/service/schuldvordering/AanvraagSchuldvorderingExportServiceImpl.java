package be.ovam.art46.service.schuldvordering;

import be.ovam.art46.dao.AanvraagSchuldvorderingDAO;
import be.ovam.art46.service.BriefService;
import be.ovam.pad.model.AanvraagSchuldvordering;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Koen on 24/04/2014.
 */
public abstract class AanvraagSchuldvorderingExportServiceImpl implements AanvraagSchuldvorderingExportService {

    @Autowired
    BriefService briefService;
    private Logger log = Logger.getLogger(AanvraagSchuldvorderingExportServiceImpl.class);
    @Autowired
    private AanvraagSchuldvorderingDAO aanvraagSchuldvorderingDAO;


    public void createDraftAanvraagSchuldvorderingExport(Long aanvr_schuldvordering_id, ServletOutputStream op) throws Exception {
        AanvraagSchuldvordering aanvrSv = aanvraagSchuldvorderingDAO.get(aanvr_schuldvordering_id);
        byte[] exportBytes = createMeetstaatExportFile(aanvrSv);
        StreamUtils.copy(new ByteArrayInputStream(exportBytes), op);
    }


//    public Brief createMeetstaatExport(AanvraagSchuldvordering aanvraagSchuldvordering) throws Exception {
//        Schuldvordering schuldvordering = aanvraagSchuldvordering.getSchuldvordering();
//        byte[] pdfFileBytes = createMeetstaatExportFile(aanvraagSchuldvordering);
//
//        Integer briefCatId = 12;
//        if (schuldvordering == null) {
//            log.error("schuldvorderin is null");
//        } else if (schuldvordering.getBestek() == null) {
//            log.error("schuldvordering.getBestek is null");
//        } else if (schuldvordering.getBriefCategorie() == null) {
//            log.error("schuldvordering.getBriefCategorie is null");
//        } else {
//            briefCatId = schuldvordering.getBriefCategorie().getId();
//        }
//
//        String filenaam = "Aanvraag_schuldvordering_" + aanvraagSchuldvordering.getSchuldvordering().getNummer() + ".pdf";
//
//        Brief brief = briefService.makeDocument(schuldvordering.getBestek().getDossier_id(), briefCatId, Application.INSTANCE.getUser_id(), filenaam, schuldvordering.getBestek_id());
//        if (brief == null) {
//            log.error("brief is null");
//        }
//        briefService.uploadBrief(brief.getBrief_id(), pdfFileBytes, filenaam);
//        return brief;
//    }

    public byte[] createMeetstaatExportFile(AanvraagSchuldvordering aanvraagSchuldvordering) throws IOException {

        String title = aanvraagSchuldvordering.getTitle();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        createMeetstaatExport(title, baos, aanvraagSchuldvordering);
        return baos.toByteArray();
    }


    protected abstract void createMeetstaatExport(String title, OutputStream outputStream, AanvraagSchuldvordering aanvraagSchuldvordering);
}
