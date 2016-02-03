package be.ovam.art46.controller.beheer;

import be.ovam.web.Response;
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
public class BriefTypeVosController {
	
	@Autowired
	private SqlSession sqlSession;
	
    @RequestMapping(value = "/beheer/briefTypeVosLijst", method = RequestMethod.GET)
	public String start(Model model, HttpServletRequest request) throws Exception {
		return "beheer.briefTypeVosLijst";
	}
	
	
	@RequestMapping(value = "/beheer/briefTypesVos", method = RequestMethod.GET)
	public @ResponseBody
	Response getBriefTypeVosLijst( HttpSession session,  Model model) throws Exception {
		return new Response(getBriefTypeVosList(), true, null);
	}

	@RequestMapping(value = "/beheer/briefTypeVos/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestBody HashMap<String, String> briefTypeVos) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.BriefTypeVosMapper.updateBriefTypeVos", briefTypeVos);		
		return new Response(getBriefTypeVosList(), true, null);
	}
	
	@RequestMapping(value = "/beheer/briefTypeVos/insert", method = RequestMethod.POST)
	public @ResponseBody Response insert(@RequestBody HashMap<String, String> briefTypeVos) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.BriefTypeVosMapper.insertBriefTypeVos", briefTypeVos);		
		return new Response(getBriefTypeVosList(), true, null);
	}
	
	@RequestMapping(value = "/beheer/briefTypeVos/delete", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestBody HashMap<String, String> briefTypeVos) throws Exception {
		Response response;
		try{
			sqlSession.update("be.ovam.art46.mappers.BriefTypeVosMapper.deleteBriefTypeVos", briefTypeVos.get("type_id"));
			response = new Response(getBriefTypeVosList(), true, null);
		} catch (DataIntegrityViolationException de) {
			response = new Response(null, false, "Dit briefTypeVos kan niet verwijderd worden omdat er al brieven naar verwijzen.");
		} catch (Exception ex) {
			response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
		}
		return response;
	}
	
	@SuppressWarnings("rawtypes")
    private List getBriefTypeVosList() {
		return sqlSession.selectList("be.ovam.art46.mappers.BriefTypeVosMapper.getBriefTypeVosList", null);
	}
}
