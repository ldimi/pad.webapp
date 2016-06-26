package be.ovam.art46.controller.adres;

import be.ovam.art46.dao.AdresDAO;
import be.ovam.art46.struts.actionform.AdresZoekForm;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.util.mybatis.SqlSession;
import static be.ovam.web.util.JsView.jsview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdresZoekController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private AdresDAO adresDAO;

	
	@RequestMapping(value = "/adres/zoek", method = RequestMethod.GET)
	public String zoek(Model model) throws Exception {
        model.addAttribute("menuId", "m_briefwisseling.zoekAdres");
        model.addAttribute("title", "Adressen");
        
        //model.addAttribute("provincies", DropDownHelper.INSTANCE.getProvincies());
        
        return jsview("adres/zoekAdres", model);
	}

	@RequestMapping(value = "/adres/zoek2", method = RequestMethod.GET)
	public String zoek2(Model model) throws Exception {
        model.addAttribute("menuId", "m_briefwisseling.zoekAdres");
        model.addAttribute("title", "Adressen");
        
        //model.addAttribute("provincies", DropDownHelper.INSTANCE.getProvincies());
        
        return jsview("adres/zoekAdres2", model);
	}



	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/adres/zoek/result", method = RequestMethod.GET)
	public String zoekResult (@ModelAttribute AdresZoekForm form, Model model, HttpSession session) throws Exception {
		List adreslijst = adresDAO.getAdresZoekResult(form);
		
		if (adreslijst.size() > 3000) {
			model.addAttribute("errorMsg", "Verfijn uw zoekopdracht, er werden meer dan 3000 resultaten gevonden.");
			return zoek(model);
		}
		
		session.setAttribute("zoeklijst", adreslijst);	
		session.setAttribute("sublijst", null);		
		session.setAttribute("zoekaction", "/s/adres/lijst");
		return "adres.zoek.result";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/adres/zoeken", method = RequestMethod.GET)
	public String zoekResult2 (@ModelAttribute AdresZoekForm form, Model model, HttpSession session,final RedirectAttributes redirectAttributes) throws Exception {
		List adreslijst = adresDAO.getAdresZoekResult(form);
		
//		if (adreslijst.size() > 3000) {
//			redirectAttributes.addFlashAttribute("errorMsg", "Verfijn uw zoekopdracht, er werden meer dan 3000 resultaten gevonden.");
//			return "redirect:/s/adres/zoek2";
//		}
		
		session.setAttribute("zoeklijst", adreslijst);	
		session.setAttribute("sublijst", null);		
		session.setAttribute("zoekaction", "/s/adres/lijst");
		return "adres.zoek.result2";
	}
	
	@RequestMapping(value = "/adres/lijst", method = RequestMethod.GET)
	public String lijst() throws Exception {
		return "adres.zoek.result";
	}
}
