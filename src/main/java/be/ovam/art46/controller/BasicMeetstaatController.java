package be.ovam.art46.controller;

import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.art46.util.OvamCustomNumberEditor;
import be.ovam.pad.model.Bestek;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.math.BigDecimal;

/**
 * Created by Koen Corstjens on 29-8-13.
 */
public class BasicMeetstaatController extends BasicBestekController {
    
    public static final String MODEL_ATTRIBUTE_NAME_TYPES = "types";
    public static final String MODEL_ATTRIBUTE_NAME_EENHEDEN = "eenheden";
    public static final String MODEL_ATTRIBUTE_NAME_MEETSTAAT_LOCKED = "meetstaatLocked";
    private static final String MODEL_ATTRIBUTE_BESTEK = "bestek";
    
    protected Bestek bestek;
    
    private Logger log = Logger.getLogger(BasicMeetstaatController.class);

    @Autowired
    protected MeetstaatOfferteService meetstaatOfferteService;

    @Autowired
    protected Validator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
        binder.registerCustomEditor(BigDecimal.class, new OvamCustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(Double.class, new OvamCustomNumberEditor(Double.class, true));
    }

    public void startBasic(Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        Boolean meetstaatLocked = Boolean.FALSE;
        
        bestek = bestekService.getBestek(super.bestekId);
        
        if (bestek != null ) {
            model.addAttribute(MODEL_ATTRIBUTE_BESTEK, bestek);
            if (bestek.getMeetstaatPDF() != null) {
                meetstaatLocked = Boolean.TRUE;
            }
        }
        model.addAttribute(MODEL_ATTRIBUTE_NAME_MEETSTAAT_LOCKED, meetstaatLocked);
    }
}
