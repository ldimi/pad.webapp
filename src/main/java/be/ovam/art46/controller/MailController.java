package be.ovam.art46.controller;

import be.ovam.art46.common.mail.MailService;
import be.ovam.art46.form.MailForm;
import be.ovam.art46.service.GebruikerService;
import be.ovam.art46.service.VoorstelDeelopdrachtService;
import be.ovam.pad.model.Ambtenaar;
import be.ovam.pad.model.OvamMail;
import be.ovam.pad.model.VoorstelDeelopdracht;
import be.ovam.pad.model.VoorstelDeelopdrachtStatus;
import be.ovam.util.mybatis.SqlSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by Koen on 3/07/2014.
 */
@Controller
public class MailController extends BasicBestekController {
    
    private static final String MODEL_ATTRIBUTE_MAILFORM= "mailForm";
    
    @Autowired
    private VoorstelDeelopdrachtService voorstelDeelopdrachtService;
    
    @Autowired
    private GebruikerService gebruikerService;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private SqlSession sqlSession;
   
    @Autowired
    private SqlSession ovamcore_sqlSession;

    @ModelAttribute(value = MODEL_ATTRIBUTE_MAILFORM)
    public MailForm createMailForm(@PathVariable Long voorstelId){
        MailForm mailForm = new MailForm();
        Ambtenaar ambtenaar = gebruikerService.getHuidigAmbtenaar();
        if(StringUtils.isEmpty(ambtenaar.getEmail())){
            mailForm.setVan("noreply@ovam.be");
        }else{
            mailForm.setVan(ambtenaar.getEmail());
        }
        VoorstelDeelopdracht voorstelDeelopdracht = voorstelDeelopdrachtService.get(voorstelId);
        String link = voorstelDeelopdrachtService.getWebloketLink(voorstelDeelopdracht);
        mailForm.setOnderwerp("Aanvraag voorstel deelopdracht voor offerte: " + voorstelDeelopdracht.getOfferte().getBestek().getOmschrijving());
        mailForm.setBericht(mailForm.getOnderwerp() +"\n"+link);
        return mailForm;
    }



    @RequestMapping(value = "/bestek/{bestekId}/voorstel/{voorstelId}/sendmail", method = RequestMethod.POST)
    public String sendMail(
            @PathVariable Long bestekId,
            @PathVariable Long voorstelId,
            @RequestParam String action,
            @ModelAttribute(MODEL_ATTRIBUTE_MAILFORM) MailForm mailForm,
            BindingResult errors,
            Model model) throws Exception {
        
        if ( mailForm.getAan() == null ){
            FieldError error = new FieldError("mailForm","aan","'Aan' moet ingevuld zijn.");
            errors.addError(error);
        }
        if ( mailForm.getOnderwerp()== null ){
            FieldError error = new FieldError("mailForm","onderwerp","'Onderwerp' moet ingevuld zijn.");
            errors.addError(error);
        }
        if ( mailForm.getBericht() == null ){
            FieldError error = new FieldError("mailForm","bericht","'Bericht' moet ingevuld zijn.");
            errors.addError(error);
        }
        if (errors.hasErrors()) {
            return createMailVoorstelDeelopdracht(bestekId, voorstelId, model);
        }
        
        if(!StringUtils.equals("Annuleer",action)) {
            VoorstelDeelopdracht voorstelDeelopdracht = voorstelDeelopdrachtService.get(voorstelId);

            if ((!StringUtils.equals(VoorstelDeelopdrachtStatus.Status.TOEGEKEND.getValue(),voorstelDeelopdracht.getStatus())) ||
                    (voorstelDeelopdracht.getDeelOpdracht() != null &&  voorstelDeelopdracht.getDeelOpdracht().getGoedkeuring_d() != null) ){
                mailService.sendHTMLMail(mailForm.getAan(), mailForm.getOnderwerp(), mailForm.getVan(), mailForm.getBericht());
            }else{
                OvamMail mail = mailService.save(mailForm.getAan(), mailForm.getOnderwerp(), mailForm.getVan(), mailForm.getBericht());
                voorstelDeelopdracht.setOvamMail(mail);
                voorstelDeelopdrachtService.save(voorstelDeelopdracht);
            }
        }
        return "redirect:/s/bestek/" + bestekId + "/voorstel/" + voorstelId;
    }

    @RequestMapping(value = "/bestek/{bestekId}/voorstel/{voorstelId}/mail", method = RequestMethod.GET)
    public String createMailVoorstelDeelopdracht(@PathVariable Long bestekId, @PathVariable Long voorstelId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        
        model.addAttribute("voorstelId", voorstelId);
        
        Integer organisatie_id = sqlSession.selectOne("get_organisatieId_voor_voorstelDeelopdrachtId", voorstelId);
        model.addAttribute("emails", ovamcore_sqlSession.selectList("emailsVanOrganisatieVoorFinancieel", organisatie_id));
        
        return "bestek.deelopdracht.voorstelmail";
    }
}
