package be.ovam.art46.controller.budget;

import be.ovam.art46.struts.actionform.DossierZoekForm;
import be.ovam.art46.util.Application;
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
public class BestekZoekController {
	
	@Autowired
	private SqlSession sqlSession;
	
    @RequestMapping(value = "/mijnbestekken", method = RequestMethod.GET)
	public String mijnbestekken(Model model) throws Exception {
        
        model.addAttribute("mijnbestekken", sqlSession.selectList("be.ovam.art46.mappers.BestekMapper.mijnbestekken", Application.INSTANCE.getUser_id()));
        
        return "bestek.mijnbestekken";
    }

	@RequestMapping(value = "/bestek/zoek", method = RequestMethod.GET)
	public String zoek(Model model) throws Exception {
        
        DropDownHelper DDH = DropDownHelper.INSTANCE;
        
        model.addAttribute("dossierhouders", DDH.getDossierhouders());
        model.addAttribute("programmaTypes", DDH.getProgrammaTypes());

        model.addAttribute("fasen", DDH.getDossierFasen_dd());
        
        model.addAttribute("title", "Zoek Bestek");
        model.addAttribute("menuId", "m_bestek.zoeken");
        
        return jsview("bestek/zoek", model);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/bestek/zoek/result", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/bestek/lijst", method = RequestMethod.GET)
	public String lijst() throws Exception {
		return "dossier.zoek.result";
	}
}
