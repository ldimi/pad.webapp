package be.ovam.art46.controller.dossier;

import be.ovam.art46.service.dossier.DossierWebloketService;
import be.ovam.art46.struts.actionform.DossierArt46Form;
import be.ovam.util.mybatis.SqlSession;
import be.ovam.web.Response;
import static be.ovam.web.util.JsView.jsview;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DossierWebloketController extends BasisDossierController {
    
    @Autowired
    private DossierWebloketService dossierWebloketService;
    
    @Autowired
    private SqlSession ovamcore_sqlSession;


    @RequestMapping(value = "/dossier/{dossier_id}/toegangwebloket", method = RequestMethod.GET)
    public String startToegangwebloket(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);
        
        model.addAttribute("dossierOrganisatieEmail_lijst", sqlSession.selectList("dossierOrganisatieEmail_lijst", dossier_id));
        
        model.addAttribute("dossier_id", dossier_id);
        model.addAttribute("dossierUrl", dossierWebloketService.getWebloketUrl(dossier_id));
        
        model.addAttribute("organisatietypes", ovamcore_sqlSession.selectList("organisatietypeDossierBeheer"));
        model.addAttribute("organisatiesVoorDossiers", ovamcore_sqlSession.selectList("organisatiesVoorDossiers"));
        
        return jsview("dossier.toegangwebloket", "dossier/webloket", model);
    }
        
    
    
    @RequestMapping(value = "/dossier/webloket", method = RequestMethod.GET)
    public String get(HttpSession session, Model model) {
        
        // TODO : Niet meer uit session ophalen
        DossierArt46Form dossierart46form = (DossierArt46Form) session.getAttribute("dossierart46form");
        Integer dossier_id = Integer.valueOf(dossierart46form.getId());
        
        model.addAttribute("dossierOrganisatieEmail_lijst", sqlSession.selectList("dossierOrganisatieEmail_lijst", dossier_id));
        
        model.addAttribute("dossier_id", dossier_id);
        model.addAttribute("dossierUrl", dossierWebloketService.getWebloketUrl(dossier_id));
        
        model.addAttribute("organisatietypes", ovamcore_sqlSession.selectList("organisatietypeDossierBeheer"));
        model.addAttribute("organisatiesVoorDossiers", ovamcore_sqlSession.selectList("organisatiesVoorDossiers"));
        
        return jsview("noMenu", "dossier/webloket", model);
    }

	@RequestMapping(value = "/dossier/emailsVanOrganisatie/{organisatie_id}", method = RequestMethod.GET)
	public @ResponseBody Response getEmailsVanOrganisatie(@PathVariable Integer organisatie_id) {
	    List emails = ovamcore_sqlSession.selectList("emailsVanOrganisatieVoorDossiers",organisatie_id);
		return new Response(emails);
	}

    @RequestMapping(value = "/dossier/add/organisatieAndEmail", method = RequestMethod.POST)
	public @ResponseBody Response addOrganisatieAndEmail(@RequestBody HashMap<String, String> dosOrgEmail) throws Exception {
	    sqlSession.insertInTable("art46", "dossier_organisatie_email", dosOrgEmail);
        dossierWebloketService.createFolderBasedOnTemplate(new Integer(dosOrgEmail.get("dossier_id")));
		return new Response(true, null);
	}
    
    @RequestMapping(value = "/dossier/remove/organisatieAndEmail", method = RequestMethod.POST)
	public @ResponseBody Response removeOrganisatieAndEmail(@RequestBody HashMap<String, String> dosOrgEmail) throws Exception {
	    sqlSession.deleteInTable("art46", "dossier_organisatie_email", dosOrgEmail);
		return new Response(true, null);
	}
    
}
