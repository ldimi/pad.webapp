package be.ovam.art46.controller.adres;

import be.ovam.art46.dao.AdresDAO;
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
        
        return jsview("adres/zoekAdres", model);
	}

	@RequestMapping(value = "/adres/zoek2", method = RequestMethod.GET)
	public String zoek2(Model model) throws Exception {
        model.addAttribute("menuId", "m_briefwisseling.zoekAdres");
        model.addAttribute("title", "Adressen");
        
        return jsview("adres/zoekAdres2", model);
	}



	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/adres/zoek/result", method = RequestMethod.GET)
	public String zoekResult (@ModelAttribute("params") AdresZoekParams params, Model model, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		List adreslijst = adresDAO.getAdresZoekResult(params);
		
		if (adreslijst.size() > 3000) {
			redirectAttributes.addFlashAttribute("errorMsg", "Verfijn uw zoekopdracht, er werden meer dan 3000 resultaten gevonden.");
			redirectAttributes.addFlashAttribute("params", model.asMap().get("params"));
			return "redirect:/s/adres/zoek";
		}
		
		session.setAttribute("zoeklijst", adreslijst);	
		session.setAttribute("sublijst", null);		
		session.setAttribute("zoekaction", "/s/adres/lijst");
		return "adres.zoek.result";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/adres/zoeken", method = RequestMethod.GET)
	public String zoekResult2 (@ModelAttribute AdresZoekParams params, Model model, HttpSession session,final RedirectAttributes redirectAttributes) throws Exception {
		List adreslijst = adresDAO.getAdresZoekResult(params);
		
		if (adreslijst.size() > 3000) {
			redirectAttributes.addFlashAttribute("errorMsg", "Verfijn uw zoekopdracht, er werden meer dan 3000 resultaten gevonden.");
			redirectAttributes.addFlashAttribute("params", model.asMap().get("params"));
			return "redirect:/s/adres/zoek";
		}
		
		session.setAttribute("zoeklijst", adreslijst);	
		session.setAttribute("sublijst", null);		
		session.setAttribute("zoekaction", "/s/adres/lijst2");
		return "adres.zoek.result2";
	}
	
	@RequestMapping(value = "/adres/lijst", method = RequestMethod.GET)
	public String lijst() throws Exception {
		return "adres.zoek.result";
	}

	@RequestMapping(value = "/adres/lijst2", method = RequestMethod.GET)
	public String lijst2() throws Exception {
		return "adres.zoek.result2";
	}

}
