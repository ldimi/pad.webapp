package be.ovam.art46.controller.budget;

import be.ovam.art46.controller.BasicBestekController;
import be.ovam.art46.controller.model.ActieForm;
import be.ovam.art46.decorator.BigDecimalDecorator;
import be.ovam.art46.model.ActieType;
import be.ovam.art46.model.BestekActie;
import be.ovam.art46.service.ActieService;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.pad.model.Bestek;

import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by koencorstjens on 21-8-13.
 */
@Controller
public class BestekActieController extends BasicBestekController{
    @Autowired
    ActieService actieService;

    @RequestMapping(value = "/bestek/{bestekId}/acties/{actieId}/verwijder/", method = RequestMethod.GET)
    public String delete(@PathVariable Integer bestekId, @PathVariable("actieId") String actieId) throws Exception {
        BestekActie bestekActie = actieService.get(Integer.valueOf(actieId));
        actieService.delete(bestekActie);
        return "redirect:/s/bestek/"+bestekId+"/acties/";
    }

    @RequestMapping(value = "/bestek/{bestekId}/acties/{actieId}/wijzig/", method = RequestMethod.GET)
    public String wijzig(@PathVariable Long bestekId, @PathVariable("actieId") String actieId, Model model) throws Exception {
        BestekActie bestekActie = actieService.get(Integer.valueOf(actieId));
        ActieForm actieForm = new ActieForm();
        LocaleBeanUtils.copyProperties(actieForm,bestekActie);
        Bestek bestek = bestekService.getBestek(bestekId);
        actieForm.setBestek_nr(bestek.getBestek_nr());
        return reload(bestekId,actieId,actieForm, model);
    }

    @RequestMapping(value = "/bestek/{bestekId}/acties/{actieId}/wijzig/", method = RequestMethod.POST)
    public String reload(@PathVariable Long bestekId,@PathVariable String actieId, @ModelAttribute ActieForm actieForm, Model model) throws Exception {
        if (actieForm != null) {
            model.addAttribute("types", DropDownHelper.INSTANCE.getActieTypesByDossierType("K"));
            if ( "0".equals(actieForm.getActie_id()) &&
                    actieForm.getActie_type_id() != null &&
                    !"0".equals(actieForm.getActie_type_id()) &&
                    actieForm.getActie_type_id().length() != 0 &&
                    (actieForm.getRate() == null || actieForm.getRate().length() == 0) ) {

                ActieType type = actieService.getActieType(Integer.valueOf(actieForm.getActie_type_id()));
                actieForm.setRate(BigDecimalDecorator.format(type.getRate()));
            }
            if (!"0".equals(actieForm.getActie_type_id()) &&
                    actieForm.getActie_type_id() != null &&
                    actieForm.getActie_type_id().length() != 0) {
                model.addAttribute("subtypes", actieService.getActieSubTypes(Integer.valueOf(actieForm.getActie_type_id())));
            }
        }
        model.addAttribute("bestekActie",actieForm);
        return "bestek.actie.wijzig";
    }
    @RequestMapping(value = "/bestek/{bestekId}/acties/aanmaken/", method = RequestMethod.GET)
    public String aanmaken(@PathVariable Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        ActieForm actieForm = new ActieForm();
        actieForm.setBestek_nr(super.bestekNr);
        actieForm.setDossier_id(super.dossierId.toString());
        actieForm.setBestek_id(bestekId);
        actieForm.setDossier_type(dossier_type);
        actieForm.setActie_id("0");
        model.addAttribute("types", DropDownHelper.INSTANCE.getActieTypesByDossierType("K"));
        model.addAttribute("actieId","0");
        model.addAttribute("bestekActie", actieForm);
        return "bestek.actie.wijzig";
    }

    @RequestMapping(value = "/bestek/{bestekId}/acties/{actieId}/bewaar/", method = RequestMethod.POST)
    public String bewaar(@PathVariable Integer bestekId, @PathVariable String actieId, @ModelAttribute ActieForm actieForm) throws Exception {
        BestekActie bestekActie = new BestekActie();
        LocaleBeanUtils.copyProperties(bestekActie,actieForm);
        actieService.saveObject(bestekActie);
        return "redirect:/s/bestek/"+bestekId+"/acties/"+bestekActie.getActie_id()+"/wijzig/";
    }


    @RequestMapping(value = "/bestek/{bestekId}/acties/", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        model.addAttribute("forward","forwardbestek");
        model.addAttribute("acties", actieService.getBestekActies(bestekId));
        model.addAttribute("dossier_type", "K");
        model.addAttribute("extraUrl", "redirect:/s/bestek/"+bestekId+"/acties/");
        model.addAttribute("types", actieService.getAllActieTypes());
        model.addAttribute("subtypes", actieService.getActieAllSubTypes());
        return "bestek.acties";
    }


}
