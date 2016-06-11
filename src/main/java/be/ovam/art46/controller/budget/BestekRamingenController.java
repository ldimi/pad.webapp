package be.ovam.art46.controller.budget;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by koencorstjens on 20-8-13.
 */
@Controller
public class BestekRamingenController extends BasicBestekController{


    @RequestMapping(value = "/bestek/{bestekId}/planning", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        model.addAttribute("ramingen", bestekService.getRamingByBestekId(bestekId));
        return "bestek.planning";
    }


}
