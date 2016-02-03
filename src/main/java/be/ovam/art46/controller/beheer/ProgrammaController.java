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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;

@Controller
public class ProgrammaController {
	
	@Autowired
	private SqlSession sqlSession;
	
    @RequestMapping(value = "/beheer/programmatypelijst", method = RequestMethod.GET)
    public String start(Model model) throws Exception {

        model.addAttribute("programmaList", getProgrammaList());
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        
        model.addAttribute("title", "Beheer Programmatypes");
        model.addAttribute("menuId", "m_toepassingsbeheer.programmatypes");
        
        return jsview("beheer/programmatypelijst", model);
    }
	
	@SuppressWarnings("rawtypes")
	public List getProgrammaList() throws Exception {
        return sqlSession.selectList("be.ovam.art46.mappers.ProgrammaMapper.getProgrammaList", null);
	}    

	@RequestMapping(value = "/programma/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestBody HashMap<String, String> programma) throws Exception {
	    sqlSession.updateInTable("art46", "programma_type", programma);
        clearCache();
		return new Response(getProgrammaList(), true, null);
	}
	
	@RequestMapping(value = "/programma/insert", method = RequestMethod.POST)
	public @ResponseBody Response insert(@RequestBody HashMap<String, String> programma) throws Exception {
        sqlSession.insertInTable("art46", "programma_type", programma);
        clearCache();
		return new Response(getProgrammaList(), true, null);
	}
	
	@RequestMapping(value = "/programma/delete", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestBody HashMap<String, String> programma) throws Exception {
		Response response;
		try{
		    sqlSession.deleteInTable("art46", "programma_type", programma);
	        clearCache();
			response = new Response(getProgrammaList(), true, null);
		} catch (DataIntegrityViolationException de) {
			response = new Response(null, false, "Dit programmaType kan niet verwijderd worden omdat er al dossiers naar verwijzen.");
		} catch (Exception ex) {
			response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
		}
		return response;
	}
	

	@RequestMapping(value = "/programma/jaarbudget/update", method = RequestMethod.POST)
	public @ResponseBody Response updateJaarbudget(@RequestBody HashMap<String, String> programma) throws Exception {
        sqlSession.updateInTable("art46", "jaarbudget_per_programma", programma);
        clearCache();
		return new Response(null, true, null);
	}
	
	@RequestMapping(value = "/programma/jaarbudget/insert", method = RequestMethod.POST)
	public @ResponseBody Response insertJaarbudget(@RequestBody HashMap<String, String> programma) throws Exception {
        sqlSession.insertInTable("art46", "jaarbudget_per_programma", programma);
        clearCache();
		return new Response(null, true, null);
	}
	
	@RequestMapping(value = "/programma/jaarbudget/delete", method = RequestMethod.POST)
	public @ResponseBody Response deleteJaarbudget(@RequestBody HashMap<String, String> programma) throws Exception {
        sqlSession.deleteInTable("art46", "jaarbudget_per_programma", programma);
        clearCache();
		return new Response(null, true, null);
	}
	

	
    private void clearCache() {
        sqlSession.getConfiguration().getCache("be.ovam.art46.mappers.ProgrammaMapper").clear();
    }

	
}
