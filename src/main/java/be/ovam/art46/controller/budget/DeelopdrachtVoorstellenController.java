package be.ovam.art46.controller.budget;

import be.ovam.art46.controller.BasicBestekController;
import be.ovam.art46.service.VoorstelDeelopdrachtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * Created by Koen on 7/02/14.
 */
@Controller
public class DeelopdrachtVoorstellenController extends BasicBestekController {
    @Autowired
    private VoorstelDeelopdrachtService voorstelDeelopdrachtService;

    @RequestMapping(value = "/bestek/{bestekId}/deelopdrachtvoorstellen", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        model.addAttribute("voorstellen", voorstelDeelopdrachtService.getAllForBestek(bestekId));
        return "bestek.deelopdracht.voorstellen";
    }


}
