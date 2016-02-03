package be.ovam.art46.service.schuldvordering;

import be.ovam.pad.model.AanvraagSchuldvordering;
import be.ovam.pad.model.Brief;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * Created by Koen on 24/04/2014.
 */
public interface AanvraagSchuldvorderingExportService {

    void createDraftAanvraagSchuldvorderingExport(Long aanvr_schuldvordering_id, ServletOutputStream op) throws Exception;

    byte[] createMeetstaatExportFile(AanvraagSchuldvordering aanvraagSchuldvordering) throws IOException;

    //Brief createMeetstaatExport(AanvraagSchuldvordering aanvraagSchuldvordering) throws Exception;
}
