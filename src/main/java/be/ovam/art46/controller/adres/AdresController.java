package be.ovam.art46.controller.adres;

import be.ovam.art46.dao.AdresDAO;
import be.ovam.util.mybatis.SqlSession;
import static be.ovam.web.util.JsView.jsview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdresController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private AdresDAO adresDAO;

	
	@RequestMapping(value = "/adres/{adres_id}", method = RequestMethod.GET)
	public String getAdres(@PathVariable Integer adres_id, Model model) throws Exception {
        model.addAttribute("menuId", "m_briefwisseling.detailAdres");
        model.addAttribute("title", "Adres details");
        
        return jsview("adres/adresDetail", model);
	}


}
