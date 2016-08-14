package be.ovam.art46.controller.adres;

import be.ovam.art46.dao.AdresDAO;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.util.mybatis.SqlSession;
import be.ovam.web.Response;
import static be.ovam.web.util.JsView.jsview;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdresController {
	
	@Autowired
	private SqlSession sqlSession;
	
    private final DropDownHelper DDH = DropDownHelper.INSTANCE;
	
	@RequestMapping(value = "/adres/{adres_id}", method = RequestMethod.GET)
	public String getAdres(@PathVariable Integer adres_id, Model model) throws Exception {
        return getAdresContact(adres_id, null, model);
	}

    @RequestMapping(value = "/adres/save", method = RequestMethod.POST)
    public @ResponseBody Response save(@RequestBody AdresDO adresDO) throws Exception {
        sqlSession.saveInTable("art46", "adres", adresDO);
        return new Response(adresDO.getAdres_id());
    }

    @RequestMapping(value = "/adres/contact/save", method = RequestMethod.POST)
    public @ResponseBody Response save(@RequestBody Map adresContact) throws Exception {
        sqlSession.saveInTable("art46", "adres_contact", adresContact);
        return new Response(adresContact.get("adres_id"));
    }

    @RequestMapping(value = "/adres/{adres_id}/contact/{contact_id}", method = RequestMethod.GET)
	public String getAdresContact(@PathVariable Integer adres_id, @PathVariable Integer contact_id, Model model) throws Exception {
        model.addAttribute("menuId", "m_briefwisseling.detailAdres");
        model.addAttribute("title", "Adres details");
        
        model.addAttribute("selected_contact_id", contact_id);
        
        model.addAttribute("adresDO", sqlSession.selectOne("getAdres", adres_id));
		model.addAttribute("adresContactenlijst", sqlSession.selectList("getAdresContacten", adres_id));
        
        
        model.addAttribute("landen", DDH.getLanden());
        model.addAttribute("adrestypes", DDH.getAdrestypes());
		model.addAttribute("gemeentenLijst", sqlSession.selectList("getGemeentenLijst"));
        
        return jsview("adres/adresDetail", model);
	}

}
