package be.ovam.art46.controller.budget;

import be.ovam.art46.controller.budget.BasicMeetstaatController;
import be.ovam.art46.controller.model.RamingForm;
import be.ovam.art46.service.meetstaat.MeetstaatRamingService;
import be.ovam.art46.service.meetstaat.MeetstaatService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by koencorstjens on 28-8-13.
 */
@Controller
public class MeetstaatRamingController extends BasicMeetstaatController{

    public static final String TILES_DEFINITION_NAME = "bestek.meetstaat.raming";
    public static final String MODEL_ATTRIBUTE_NAME_RAMING_FORM = "ramingForm";
    private static final String MODEL_ATTRIBUTE_DEFINITIEF_MAKEN_ERRORS = "defintiefMakenValidatieErrors";
    @Autowired
    private MeetstaatRamingService meetstaatRamingService;
    @Autowired
    MeetstaatService meetstaatService;

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/raming/", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model, HttpServletResponse response) throws Exception {
        return setBasicModel(bestekId, model);
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_NAME_RAMING_FORM)
    public RamingForm admodel(@RequestParam(required=false) Long bestekId){
    	if (bestekId!=null) {
    		return new RamingForm(meetstaatService.getAll(bestekId));
		}
    	return new RamingForm();
    	
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/raming/", method = RequestMethod.POST)
    public String save(@PathVariable Long bestekId, @Valid @ModelAttribute(MODEL_ATTRIBUTE_NAME_RAMING_FORM) RamingForm ramingForm, BindingResult result, Model model) throws Exception {
        if(result.hasErrors()) {
            model.addAttribute(MODEL_ATTRIBUTE_NAME_BINDING_RESULT,result);
            return setBasicModel(bestekId,model);
        }
        meetstaatRamingService.update(ramingForm.getMeetstaatRegels());
        return "redirect:/s/bestek/"+bestekId+"/meetstaat/raming/";
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/definitiefmaken", method = RequestMethod.GET)
    public String definitiefmaken(@PathVariable Long bestekId, Model model) throws Exception {
        List<String> errors = meetstaatService.definitiefmaken(bestekId);
         if (CollectionUtils.isNotEmpty(errors)){
            model.addAttribute(MODEL_ATTRIBUTE_DEFINITIEF_MAKEN_ERRORS, errors);
            return setBasicModel(bestekId, model);
        }
        return "redirect:/s/bestek/" + bestekId + "/meetstaat/";
    }

    private String setBasicModel(Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_RAMING_FORM, new RamingForm(meetstaatService.getAll(bestekId)));
        return TILES_DEFINITION_NAME;
    }
}
