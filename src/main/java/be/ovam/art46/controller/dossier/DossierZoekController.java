package be.ovam.art46.controller.dossier;

import be.ovam.art46.struts.actionform.DossierZoekForm;
import be.ovam.art46.util.DropDownHelper;
import java.util.List;

import javax.servlet.http.HttpSession;

import be.ovam.util.mybatis.SqlSession;
import static be.ovam.web.util.JsView.jsview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DossierZoekController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/dossier/zoek", method = RequestMethod.GET)
	public String zoek(Model model) throws Exception {
		//model.addAttribute("params", new ZoekParams());
		//return "dossier.zoek";
        
        DropDownHelper DDH = DropDownHelper.INSTANCE;
        
        model.addAttribute("dossierhouders", DDH.getDossierhouders());
        model.addAttribute("dossierhoudersBB", DDH.getAmbtenarenBOA());
        model.addAttribute("dossierhoudersJD", DDH.getAmbtenarenJD());
        model.addAttribute("programmaTypes", DDH.getProgrammaTypes());
        model.addAttribute("doelgroepen_dd", DDH.getDoelgroepen_dd());

        model.addAttribute("fasen", DDH.getDossierFasen_dd());
        model.addAttribute("rechtsgronden", DDH.getDossierRechtsgronden());
        
        model.addAttribute("provincies", DDH.getProvincies());
        model.addAttribute("fusiegemeenten", DDH.getFusiegemeenten());
        
        model.addAttribute("art46_artikels", DDH.getArtikels());
        model.addAttribute("art46_lijsten", DDH.getLijsten());
        
        
        model.addAttribute("title", "Zoek Dossier");
        model.addAttribute("menuId", "m_dossier.zoeken");
        
        return jsview("dossier/zoek", model);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dossier/zoek/result", method = RequestMethod.GET)
	public String zoekResult (@ModelAttribute DossierZoekForm form, Model model, HttpSession session,final RedirectAttributes redirectAttributes) throws Exception {
        
		List result = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierZoekResult", form);
		if (result.size() > 1000) {
            redirectAttributes.addFlashAttribute("errorMsg", "Verfijn uw zoekopdracht, er werden meer dan 1000 resultaten gevonden.");
			//model.addAttribute("foutBoodschap", "Verfijn uw zoekopdracht, er werden meer dan 1000 resultaten gevonden.");
            return "redirect:/s/dossier/zoek";
		}
		
		session.setAttribute("zoeklijst", result);	
		session.setAttribute("sublijst", null);		
		session.setAttribute("zoekaction", "/s/dossier/lijst");
		return "dossier.zoek.result";
	}
	
	@RequestMapping(value = "/dossier/lijst", method = RequestMethod.GET)
	public String lijst() throws Exception {
		return "dossier.zoek.result";
	}
}
