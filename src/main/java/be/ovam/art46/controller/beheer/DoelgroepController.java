package be.ovam.art46.controller.beheer;

import static be.ovam.web.util.JsView.jsview;

import be.ovam.web.Response;
import be.ovam.art46.util.Application;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class DoelgroepController {
	
	@Autowired
	private SqlSession sqlSession;
	
    @RequestMapping(value = "/beheer/doelgroepen", method = RequestMethod.GET)
    public String start(Model model) throws Exception {

        model.addAttribute("doelgroepen", getDoelgroepen());
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        
        model.addAttribute("title", "Beheer Doelgroepen");
        model.addAttribute("menuId", "m_toepassingsbeheer.doelgroepen");
        
        return jsview("beheer/doelgroep", model);
    }
	
	@SuppressWarnings("rawtypes")
	public List getDoelgroepen() throws Exception {
        return sqlSession.selectList("get_doelgroepen", null);
	}

	@RequestMapping(value = "/doelgroep/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestBody HashMap<String, String> doelgroep) throws Exception {
	    sqlSession.updateInTable("art46", "doelgroep_type", doelgroep);
        clearCache();
		return new Response(getDoelgroepen(), true, null);
	}
	
	@RequestMapping(value = "/doelgroep/insert", method = RequestMethod.POST)
	public @ResponseBody Response insert(@RequestBody HashMap<String, String> doelgroep) throws Exception {
        sqlSession.insertInTable("art46", "doelgroep_type", doelgroep);
        clearCache();
		return new Response(getDoelgroepen(), true, null);
	}
	
	@RequestMapping(value = "/doelgroep/delete", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestBody HashMap<String, String> doelgroep) throws Exception {
		Response response;
		try{
		    sqlSession.deleteInTable("art46", "doelgroep_type", doelgroep);
	        clearCache();
			response = new Response(getDoelgroepen(), true, null);
		} catch (DataIntegrityViolationException de) {
			response = new Response(null, false, "Deze doelgroep kan niet verwijderd worden omdat er al dossiers naar verwijzen.");
		} catch (Exception ex) {
			response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
		}
		return response;
	}
	

	
    private void clearCache() {
        sqlSession.getConfiguration().getCache("be.ovam.art46.mappers.DoelgroepMapper").clear();
    }

	
}
