package be.ovam.art46.controller.taken;

import be.ovam.art46.form.SchuldvorderingenForm;
import be.ovam.art46.service.schuldvordering.SchuldvorderingService;
import be.ovam.pad.model.Schuldvordering;
import com.itextpdf.text.DocumentException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Koen on 25/04/2014.
 */
@Controller
public class PrintAntwoordSchuldvorderingenController {
    @Autowired
    private SchuldvorderingService schuldvorderingService;

    @RequestMapping(value = "/afgedrukt/{id}", method = RequestMethod.GET)
    public String afgedrukt(@PathVariable("id") Integer id) {
        schuldvorderingService.isAfgedrukt(id);
        return "redirect:/s/teprintenschuldvorderingen";
    }

    @RequestMapping(value = "/teprintenschuldvorderingen", method = RequestMethod.GET)
    public String get(@RequestParam(required = false, value = "melding") String melding, Model model) {
        SchuldvorderingenForm schuldvorderingenForm = new SchuldvorderingenForm();
        schuldvorderingenForm.setSchuldvorderingen(schuldvorderingService.getTePrintenSchuldvorderingen());
        model.addAttribute("schuldvorderingenform", schuldvorderingenForm);
        if (StringUtils.isNotEmpty(melding)) {
            model.addAttribute("melding", melding);
        }
        return "teprintenschuldvorderingen";
    }

    @RequestMapping(value = "/teprintenschuldvorderingen.pdf", method = RequestMethod.POST)
    public String print(@ModelAttribute SchuldvorderingenForm schuldvorderingenForm, HttpServletResponse response, Model model) throws IOException, DocumentException {
        StringBuilder melding = new StringBuilder("Schuldvorderingen: ");
        for (Schuldvordering schuldvordering : schuldvorderingenForm.getSchuldvorderingen()) {
            if (schuldvordering.getSelected()) {
                if (schuldvorderingService.print(schuldvordering.getVordering_id())) {
                    melding.append(schuldvordering.getNummer()).append(" ,");
                }
            }
        }
        melding.append(" zijn correct verzonden naar de printer.");
        return "redirect:/s/teprintenschuldvorderingen?melding=" + melding.toString();
    }

}

