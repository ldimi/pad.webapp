package be.ovam.art46.controller.adres;

import be.ovam.art46.dao.AdresDAO;
import be.ovam.art46.struts.actionform.AdresZoekForm;
import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdresZoekController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private AdresDAO adresDAO;

	
	@RequestMapping(value = "/adres/zoek", method = RequestMethod.GET)
	public String zoek(Model model) throws Exception {
		return "adres.zoek";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/adres/zoek/result", method = RequestMethod.GET)
	public String zoekResult (@ModelAttribute AdresZoekForm form, Model model, HttpSession session) throws Exception {
		List adreslijst = adresDAO.getAdresZoekResult(form);
		
		if (adreslijst.size() > 3000) {
			model.addAttribute("errorMsg", "Verfijn uw zoekopdracht, er werden meer dan 3000 resultaten gevonden.");
			return "adres.zoek";
		}
		
		session.setAttribute("zoeklijst", adreslijst);	
		session.setAttribute("sublijst", null);		
		session.setAttribute("zoekaction", "/s/adres/lijst");
		return "adres.zoek.result";
	}
	
	@RequestMapping(value = "/adres/lijst", method = RequestMethod.GET)
	public String lijst() throws Exception {
		return "adres.zoek.result";
	}
}
