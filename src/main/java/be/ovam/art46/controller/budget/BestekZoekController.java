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
	public String mijnbestekken(HttpSession session) throws Exception {
        List result = sqlSession.selectList("be.ovam.art46.mappers.BestekMapper.mijnbestekken", Application.INSTANCE.getUser_id());

		session.setAttribute("zoeklijst", result);	
		session.setAttribute("sublijst", null);		
		session.setAttribute("zoekaction", "/s//mijnbestekken/lijst");
        return "bestek.mijnbestekken";
    }
    
    @RequestMapping(value = "/mijnbestekken/lijst", method = RequestMethod.GET)
	public String mijnbestekken() throws Exception {
        return "bestek.mijnbestekken";
    }

    
    
	@RequestMapping(value = "/bestek/zoek", method = RequestMethod.GET)
	public String zoek(Model model) throws Exception {
        
        DropDownHelper DDH = DropDownHelper.INSTANCE;
        
        model.addAttribute("dossierhouders", DDH.getDossierhouders());
        model.addAttribute("programmaTypes", DDH.getProgrammaTypes());

        model.addAttribute("bestekBodemFase_dd", DDH.getBestekBodemFase());
        
        model.addAttribute("title", "Zoek Bestek");
        model.addAttribute("menuId", "m_bestek.zoeken");
        
        return jsview("bestek/zoek", model);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/bestek/zoeken", method = RequestMethod.GET)
	public String zoeken (@ModelAttribute("params") BestekZoekParams params, Model model, HttpSession session,final RedirectAttributes redirectAttributes) throws Exception {
        
		List result = sqlSession.selectList("be.ovam.art46.mappers.BestekMapper.getBestekZoekResult", params);
		if (result.size() > 1000) {
            redirectAttributes.addFlashAttribute("errorMsg", "Verfijn uw zoekopdracht, er werden meer dan 1000 resultaten gevonden.");
			redirectAttributes.addFlashAttribute("params", model.asMap().get("params"));
            return "redirect:/s/bestek/zoek";
		}
		
		session.setAttribute("zoeklijst", result);	
		session.setAttribute("sublijst", null);		
		session.setAttribute("zoekaction", "/s/bestek/zoek/lijst");
		return "bestek.zoek.result";
	}
	
	@RequestMapping(value = "/bestek/zoek/lijst", method = RequestMethod.GET)
	public String lijst() throws Exception {
		return "bestek.zoek.result";
	}
}
