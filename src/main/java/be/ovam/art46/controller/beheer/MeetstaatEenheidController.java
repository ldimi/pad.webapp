package be.ovam.art46.controller.beheer;

import be.ovam.art46.model.MeetstaatEenheid;
import be.ovam.art46.model.MeetstaatEenheidMapping;
import be.ovam.art46.service.meetstaat.MeetstaatEenheidService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by koencorstjens on 13-9-13.
 */
@Controller
public class MeetstaatEenheidController {
    @Autowired
    MeetstaatEenheidService meetstaatEenheidService;

    @RequestMapping(value = "/beheer/meetstaat/eenheden/", method = RequestMethod.GET)
    public String start(Model model) throws Exception {
        model.addAttribute("eenheden", meetstaatEenheidService.getAll());
        model.addAttribute("meetstaatEenheid", new MeetstaatEenheid());
        model.addAttribute("eenhedenmappers", meetstaatEenheidService.getAllMappings());
        model.addAttribute("meetstaatEenheidMapping", new MeetstaatEenheidMapping());
        return "beheer.meetstaat.eenheden";
    }

    @RequestMapping(value = "/beheer/meetstaat/eenheden/toevoegen/", method = RequestMethod.POST)
    public String toevoegen(MeetstaatEenheid meetstaatEenheid) throws Exception {
        if(StringUtils.isNotEmpty(meetstaatEenheid.getNaam())){
            meetstaatEenheidService.add(meetstaatEenheid);
        }
        return "redirect:/s/beheer/meetstaat/eenheden/";
    }
    @RequestMapping(value = "/beheer/meetstaat/eenheden/{naam}/verwijder/", method = RequestMethod.GET)
    public String verwijder(@PathVariable String naam) throws Exception {
        meetstaatEenheidService.delete(naam);
        return "redirect:/s/beheer/meetstaat/eenheden/";
    }

    @RequestMapping(value = "/beheer/meetstaat/eenheid/mapping/{code}/verwijder/", method = RequestMethod.GET)
    public String verwijderMapping(@PathVariable String code) throws Exception {
        meetstaatEenheidService.deleteMapping(code);
        return "redirect:/s/beheer/meetstaat/eenheden/";
    }

    @RequestMapping(value = "/beheer/meetstaat/eenheden/mapping/toevoegen/", method = RequestMethod.POST)
    public String toevoegenMapping(MeetstaatEenheidMapping meetstaatEenheidMapping) throws Exception {
        meetstaatEenheidService.add(meetstaatEenheidMapping);
        return "redirect:/s/beheer/meetstaat/eenheden/";
    }


}
