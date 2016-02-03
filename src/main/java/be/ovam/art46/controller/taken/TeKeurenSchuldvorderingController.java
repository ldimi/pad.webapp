package be.ovam.art46.controller.taken;

import be.ovam.art46.controller.form.AfkeurenAntwoordBriefSchuldvorderingForm;
import be.ovam.art46.service.schuldvordering.SchuldvorderingService;
import be.ovam.pad.model.Schuldvordering;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by Koen on 2/04/2014.
 */
@Controller
public class TeKeurenSchuldvorderingController {
    @Autowired
    private SchuldvorderingService schuldvorderingService;

    @RequestMapping(value = "/tekeurenschuldvorderingen", method = RequestMethod.GET)
    public String gettekeurenschuldvorderingen(Model model){
        model.addAttribute("schuldvorderingen",schuldvorderingService.getTeKeurenSchuldvorderingen());
        model.addAttribute("error", schuldvorderingService.handtekenningBeschikbaar());
        return "tekeurenschuldvorderingen";
    }

    @RequestMapping(value = "/goedkeurenAntwoordBriefSchuldvordering/{schuldvorderingId}", method = RequestMethod.GET)
    public String goedkeurenAntwoordBriefSchuldvordering(Model model, @PathVariable Integer schuldvorderingId, Principal principal) throws Exception {
        schuldvorderingService.ondertekenen(schuldvorderingId, principal.getName());
        return "redirect:/s/tekeurenschuldvorderingen";
    }

    @RequestMapping(value = "/afkeurenAntwoordBriefSchuldvordering/{schuldvorderingId}", method = RequestMethod.GET)
    public String afkeurenAntwoordBriefSchuldvordering(Model model, @PathVariable Integer schuldvorderingId, Principal principal) throws Exception {
        Schuldvordering schuldvordering = schuldvorderingService.get(schuldvorderingId);
        model.addAttribute("schuldvordering", schuldvordering);
        model.addAttribute("afkeurenAntwoordBriefSchuldvorderingForm",new AfkeurenAntwoordBriefSchuldvorderingForm(schuldvordering.getVordering_id()));
        return "afkeurenAntwoordBriefSchuldvordering";
    }

    @RequestMapping(value = "/afkeurenAntwoordBriefSchuldvordering/opslaan", method = RequestMethod.POST)
    public String afkeurenAntwoordBriefSchuldvordering(@RequestParam String action, @ModelAttribute(value = "afkeurenAntwoordBriefSchuldvorderingForm") AfkeurenAntwoordBriefSchuldvorderingForm afkeurenAntwoordBriefSchuldvorderingForm , Principal principal) throws Exception {
        if(StringUtils.equals("Annuleren",action)){
            return "redirect:/s/tekeurenschuldvorderingen";
        }
        schuldvorderingService.terugNaarDossierhouder(afkeurenAntwoordBriefSchuldvorderingForm.getId(), afkeurenAntwoordBriefSchuldvorderingForm.getMotivatie());
        return "redirect:/s/tekeurenschuldvorderingen";
    }
}
