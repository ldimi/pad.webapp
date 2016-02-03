package be.ovam.art46.controller.schuldvordering;

import be.ovam.art46.service.schuldvordering.SchuldvorderingExportService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by Koen on 23/05/2014.
 */
@Controller
public class DraftSchuldvorderingController {
    private Logger log = Logger.getLogger(DraftSchuldvorderingController.class);
    @Autowired
    SchuldvorderingExportService schuldvorderingExportService;

    @RequestMapping(value = "/bestek/{bestekId}/schuldvordering/draftSchuldvordering-{schuldvorderingId}.pdf", method = RequestMethod.GET)
    public void exportPdf(@PathVariable Long bestekId, @PathVariable Integer schuldvorderingId, HttpServletResponse response) throws Exception {
        ServletOutputStream op = null;
        try{
            op = response.getOutputStream();
            
            schuldvorderingExportService.createDraftExport(schuldvorderingId, op);
            
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftMeetstaat-" + bestekId + ".pdf");
        } finally {
            if(op!=null){
                op.flush();
                op.close();
            }
        }
    }
}
