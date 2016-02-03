package be.ovam.art46.controller.bestek;


import be.ovam.art46.controller.BasicBestekController;
import be.ovam.art46.form.VoorstelDeelopdrachtForm;
import be.ovam.art46.model.*;
import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.service.dossier.DossierService;
import be.ovam.art46.service.VoorstelDeelopdrachtService;
import be.ovam.art46.service.meetstaat.MeetstaatEenheidService;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.art46.util.OvamCustomCalendarEditor;
import be.ovam.art46.util.OvamCustomNumberEditor;
import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.Dossier;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.Offerte;
import be.ovam.pad.model.VoorstelDeelopdracht;
import be.ovam.pad.model.VoorstelDeelopdrachtStatus;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Koen on 2/05/2014.
 */
@Controller
public class VoorstelDeelopdrachtController extends BasicBestekController {
    
    public static final String MODEL_ATTRIBUTE_NAME_EENHEDEN = "eenheden";
    public static final String MODEL_ATTRIBUTE_NAME_TYPES = "types";
    private static final String MODEL_ATTRIBUTE_VOORSTEL_DEELOPDRACHT_FORM = "voorstelDeelopdrachtForm";
    private static final String MODEL_ATTRIBUTE_OFFERTES = "offertes";
    private static final String MODEL_ATTRIBUTE_DOSSIERS = "dossiers";
    private static final String MODEL_ATTRIBUTE_DEELOPDRACHTEN = "deelopdrachten";
    @Autowired
    private VoorstelDeelopdrachtService voorstelDeelopdrachtService;
    @Autowired
    private MeetstaatEenheidService meetstaatEenheidService;
    @Autowired
    private Validator validator;
    @Autowired
    private MeetstaatOfferteService offerteService;
    
    @Autowired
    private DeelOpdrachtService deelopdrachtService;



    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Dossier.class, "voorstelDeelopdracht.dossier", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if(StringUtils.isNotEmpty(text)) {
                    Dossier dossier = dossierService.get(Integer.parseInt(text));
                    setValue(dossier);
                }
            }
        });
        binder.registerCustomEditor(Offerte.class, "voorstelDeelopdracht.offerte", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if(StringUtils.isNotEmpty(text)) {
                    Offerte offerte = offerteService.get(Long.parseLong(text));
                    setValue(offerte);
                }
            }
        });
        binder.registerCustomEditor(DeelOpdracht.class, "voorstelDeelopdracht.deelOpdracht", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if(StringUtils.isNotEmpty(text)) {
                    DeelOpdracht deelOpdracht = deelopdrachtService.get(Integer.parseInt(text));
                    setValue(deelOpdracht);
                }
            }
        });
        binder.setValidator(validator);
        binder.registerCustomEditor(BigDecimal.class, new OvamCustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(Calendar.class, new OvamCustomCalendarEditor(true));
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_DEELOPDRACHTEN)
    public List<DeelOpdracht> adDeelopdracht(@PathVariable String voorstelId) {
        if(!(voorstelId == null || StringUtils.equals("nieuw", voorstelId))){
            return deelopdrachtService.getMogelijkeDeelopdrachtenVoorVoorstel(Long.valueOf(voorstelId));
        }else{
            return new ArrayList<DeelOpdracht>();
        }
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_VOORSTEL_DEELOPDRACHT_FORM)
    public VoorstelDeelopdrachtForm admodel(@PathVariable String voorstelId) {
        VoorstelDeelopdrachtForm voorstelDeelopdrachtForm = new VoorstelDeelopdrachtForm();
        if (voorstelId == null || StringUtils.equals("nieuw", voorstelId)) {
            VoorstelDeelopdracht voorstelDeelopdracht = new VoorstelDeelopdracht();
            voorstelDeelopdrachtForm.setVoorstelDeelopdracht(voorstelDeelopdracht);
        } else {
            voorstelDeelopdrachtForm.setVoorstelDeelopdracht(voorstelDeelopdrachtService.get(Long.valueOf(voorstelId)));
        }
        return voorstelDeelopdrachtForm;
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_NAME_EENHEDEN)
    public ArrayList<SelectElement> setEenhedenModel() {
        return meetstaatEenheidService.getAllUniqueEenheden();
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_NAME_TYPES)
    public List<String> setTypesModel() {
        return MeetstaatRegel.TYPES;
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_OFFERTES)
    public List<Offerte> setOffertesModel(@PathVariable Long bestekId) {
        return offerteService.getToegekendeOffertes(bestekId);
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_DOSSIERS)
    public List<Dossier> setDossiersModel(Principal principal) {
        return dossierService.getIvsDossiersTypeNietAnders(principal.getName());
    }

    @RequestMapping(value = "/bestek/{bestekId}/voorstel/{voorstelId}", method = RequestMethod.GET)
    public String get(@PathVariable Long bestekId, @PathVariable String voorstelId, String error, Model model) throws Exception {
        super.startBasic(bestekId, model);
        model.addAttribute("bestekId", bestekId);
        return "bestek.deelopdracht.voorstel";
    }

    @RequestMapping(value = "/bestek/{bestekId}/voorstel/{voorstelId}", method = RequestMethod.POST)
    public String save(@PathVariable Long bestekId, @PathVariable String voorstelId, @RequestParam String action,
                       @Valid @ModelAttribute(MODEL_ATTRIBUTE_VOORSTEL_DEELOPDRACHT_FORM) VoorstelDeelopdrachtForm voorstelDeelopdrachtForm,
                       BindingResult errors, Model model,
                       Principal principal
    ) throws Exception {
        super.startBasic(bestekId, model);
        VoorstelDeelopdracht voorstelDeelopdracht = voorstelDeelopdrachtForm.getVoorstelDeelopdracht();
        if(StringUtils.equals(action,"Bewaar")){
            voorstelDeelopdracht.setStatus(VoorstelDeelopdrachtStatus.Status.IN_OPMAAK.getValue());
        }else if(StringUtils.equals(action,"Aanvraag verzenden")){
            if(voorstelDeelopdracht.getDossier()==null){
                FieldError error = new FieldError("voorstelDeelopdrachtForm","voorstelDeelopdracht.dossier","Om een voorstel aan te maken moet u een dossier selecteren!");
                errors.addError(error);
            }
            if(voorstelDeelopdracht.getOfferte()==null){
                FieldError error = new FieldError("voorstelDeelopdrachtForm","voorstelDeelopdracht.offerte","Om een voorstel aan te maken moet u een offerte selecteren!");
                errors.addError(error);
            }
            if (!errors.hasErrors()) {
                voorstelDeelopdracht.setStatus(VoorstelDeelopdrachtStatus.Status.IN_AANVRAAG.getValue());
                voorstelDeelopdracht.setAanvraagDatum(Calendar.getInstance());
            }
        }else if(StringUtils.equals(action,"Toekennen")){
            if(voorstelDeelopdracht.getDeelOpdracht()==null){
                FieldError error = new FieldError("voorstelDeelopdrachtForm","voorstelDeelopdracht.deelOpdracht","Om een voorstel toe te kennen moet u een deelopdracht selecteren!");
                errors.addError(error);
            }
            if (!errors.hasErrors()) {
                voorstelDeelopdracht.setStatus(VoorstelDeelopdrachtStatus.Status.TOEGEKEND.getValue());
                voorstelDeelopdracht.setBeslissingsDatum(Calendar.getInstance());
            }
        }else if(StringUtils.equals(action,"Niet selecteren")){
            voorstelDeelopdracht.setDeelOpdracht(null);
            voorstelDeelopdracht.setStatus(VoorstelDeelopdrachtStatus.Status.NIET_GESELECTEERD.getValue());
            voorstelDeelopdracht.setBeslissingsDatum(Calendar.getInstance());
        }else if(StringUtils.equals(action,"Aanpassing vragen")){
            voorstelDeelopdracht.setDeelOpdracht(null);
            voorstelDeelopdracht.setStatus(VoorstelDeelopdrachtStatus.Status.AANPASSING_GEVRAAGD.getValue());
            voorstelDeelopdracht.setBeslissingsDatum(Calendar.getInstance());
        }
        if (errors.hasErrors()) {
            return "bestek.deelopdracht.voorstel";
        }
        Long id = voorstelDeelopdrachtService.save(voorstelDeelopdrachtForm.getVoorstelDeelopdracht(), principal.getName());
        model.addAttribute("aanvraagSchuldvorderingId", voorstelDeelopdrachtForm.getVoorstelDeelopdracht().getId());
        if(StringUtils.equals(VoorstelDeelopdrachtStatus.Status.IN_OPMAAK.getValue(), voorstelDeelopdracht.getStatus())) {
            return "redirect:/s/bestek/" + bestekId + "/voorstel/" + id + "#totaal";
        }else {
            return "redirect:/s/bestek/" + bestekId + "/voorstel/" + id + "/mail";
        }

    }

}
