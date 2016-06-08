package be.ovam.art46.controller.dossier;

import be.ovam.art46.service.dossier.DossierWebloketService;
import be.ovam.art46.struts.actionform.DossierArt46Form;
import be.ovam.util.mybatis.SqlSession;
import be.ovam.web.Response;
import static be.ovam.web.util.JsView.jsview;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        model.addAttribute("dossier_id", dossier_id);
        model.addAttribute("dossierUrl", dossierWebloketService.getWebloketUrl(dossier_id));
        
        model.addAttribute("dossierOrganisatie_lijst", sqlSession.selectList("dossierOrganisatie_lijst", dossier_id));
        
        // gegevens uit het LAA
        model.addAttribute("organisatietypes", ovamcore_sqlSession.selectList("organisatietype_dossierBeheer"));
        model.addAttribute("organisatie_organisatietype_lijst", ovamcore_sqlSession.selectList("organisatie_organisatietype_dossierBeheer_lijst"));
        model.addAttribute("organisatie_lijst", ovamcore_sqlSession.selectList("organisatie_dossierBeheer_lijst"));
   
        
        return jsview("dossier.toegangwebloket", "dossier/webloket", model);
    }
        
    
    
    @RequestMapping(value = "/dossier/add/organisatie", method = RequestMethod.POST)
	public @ResponseBody Response addOrganisatie(@RequestBody HashMap<String, String> dosOrg) throws Exception {
	    sqlSession.insertInTable("art46", "dossier_organisatie", dosOrg);
        dossierWebloketService.createFolderBasedOnTemplate(new Integer(dosOrg.get("dossier_id")));
		return new Response(true, null);
	}
    
    @RequestMapping(value = "/dossier/remove/organisatie", method = RequestMethod.POST)
	public @ResponseBody Response removeOrganisatie(@RequestBody HashMap<String, String> dosOrg) throws Exception {
	    sqlSession.deleteInTable("art46", "dossier_organisatie", dosOrg);
		return new Response(true, null);
	}
    
    @RequestMapping(value = "/dossier/toegangwebloket/organisatie/{organisatie_id}/logins", method = RequestMethod.GET)
    public @ResponseBody List getLoginsVoorOrganisatie (@PathVariable Integer organisatie_id) {
        return new ArrayList();
    }
}
