package be.ovam.art46.controller.beheer;

import be.ovam.art46.model.BarcodeForm;
import be.ovam.art46.service.EsbService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Koen on 25/09/2014.
 */
@Controller
public class GenerateBarcodeController {
    @Autowired
    private EsbService esbService;


    @RequestMapping(value = "/beheer/generatebarcode", method = RequestMethod.GET)
    public String generateBarcode(Model model) {
        BarcodeForm barcodeForm;
        model.addAttribute("barcodeForm", new BarcodeForm());
        return "beheer.generatebarcode";
    }

    @RequestMapping(value = "/beheer/generatebarcode.pdf", method = RequestMethod.POST)
    public void saveGenerateBarcode(@ModelAttribute BarcodeForm barcodeForm, HttpServletResponse response) throws IOException {
        byte[] is = esbService.getBarcodesPdf("OVAM-", "IVS-", "I", barcodeForm.getStartnummer(), barcodeForm.getEindnummer(), 1);
        ServletOutputStream op = null;
        try {
            op = response.getOutputStream();
            InputStream input = new ByteArrayInputStream(is);;
            IOUtils.copy(input, op);
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.addHeader("Content-Type", "application/pdf");
            response.setHeader("Content-disposition", "attachment;filename=generatebarcode.pdf");
            response.setContentType("application/pdf");
        } catch (Exception e) {

        } finally {
            if (op != null) {
                op.flush();
                op.close();
            }
        }
    }
}
