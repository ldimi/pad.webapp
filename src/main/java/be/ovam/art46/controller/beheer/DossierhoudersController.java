package be.ovam.art46.controller.beheer;

import be.ovam.art46.util.Application;
import be.ovam.web.Response;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.util.mybatis.SqlSession;
import static be.ovam.web.util.JsView.jsview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class DossierhoudersController {

	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/beheer/dossierhouders", method = RequestMethod.GET)
	public String start(Model model, HttpServletRequest request) {
		model.addAttribute("beheerDossierhouders",DropDownHelper.INSTANCE.getBeheerDossierhouders());
		model.addAttribute("programmaTypes",DropDownHelper.INSTANCE.getProgrammaTypes());
		model.addAttribute("personeelsleden",DropDownHelper.INSTANCE.getAmbtenaars());
		model.addAttribute("dossierhouders",DropDownHelper.INSTANCE.getDossierhouders());
        
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        model.addAttribute("title", "Beheer Dossierhouders");
        model.addAttribute("menuId", "m_toepassingsbeheer.dossierhouders");
        
        return jsview("beheer/dossierhouders", model);
	}

	
    @RequestMapping(value = "/beheer/dossierhouder/save", method = RequestMethod.POST)
    public @ResponseBody Response save(@RequestBody HashMap<String, String> dossierhouder) {
        
        sqlSession.saveInTable("art46", "dossier_houder", dossierhouder);
        clearCache();
        return new Response(DropDownHelper.INSTANCE.getBeheerDossierhouders(), true, null);
    }

    private void clearCache() {
        sqlSession.getConfiguration().getCache("be.ovam.art46.mappers.DossierhoudersMapper").clear();
    }

   
    
    
}
