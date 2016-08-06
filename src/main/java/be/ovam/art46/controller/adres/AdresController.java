package be.ovam.art46.controller.adres;

import be.ovam.art46.dao.AdresDAO;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.util.mybatis.SqlSession;
import be.ovam.web.Response;
import static be.ovam.web.util.JsView.jsview;
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
	
	@Autowired
	private AdresDAO adresDAO;

    private final DropDownHelper DDH = DropDownHelper.INSTANCE;
	
	@RequestMapping(value = "/adres/{adres_id}", method = RequestMethod.GET)
	public String getAdres(@PathVariable Integer adres_id, Model model) throws Exception {
        model.addAttribute("menuId", "m_briefwisseling.detailAdres");
        model.addAttribute("title", "Adres details");
        
        model.addAttribute("adresDO", sqlSession.selectOne("getAdres", adres_id));
        model.addAttribute("landen", DDH.getLanden());
        model.addAttribute("adrestypes", DDH.getAdrestypes());
        
        return jsview("adres/adresDetail", model);
	}

    @RequestMapping(value = "/adres/save", method = RequestMethod.POST)
    public @ResponseBody Response save(@RequestBody AdresDO adresDO) throws Exception {
        sqlSession.saveInTable("art46", "adres", adresDO);
        return new Response(adresDO.getAdres_id());
    }

}
