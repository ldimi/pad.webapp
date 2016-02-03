package be.ovam.art46.controller.schuldvordering;

import be.ovam.art46.service.schuldvordering.AanvraagSchuldvorderingExportService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Koen on 23/05/2014.
 */
@Controller
public class DraftAanvrSchuldvorderingController {
    private Logger log = Logger.getLogger(DraftAanvrSchuldvorderingController.class);
    @Autowired
    AanvraagSchuldvorderingExportService aanvraagSchuldvorderingExportService;

    @RequestMapping(value = "/draftAanvraagSchuldvordering-{aanvr_schuldvordering_id}.pdf", method = RequestMethod.GET)
    public void exportPdf(@PathVariable Long aanvr_schuldvordering_id, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try{
            op = response.getOutputStream();
            aanvraagSchuldvorderingExportService.createDraftAanvraagSchuldvorderingExport(aanvr_schuldvordering_id, op);
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftAanvrSchuldvordering-" + aanvr_schuldvordering_id + ".pdf");
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if(op!=null){
                op.flush();
                op.close();
            }
        }
    }
}
