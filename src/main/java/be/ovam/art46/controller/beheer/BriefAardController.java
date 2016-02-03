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
public class BriefAardController {
	
	@Autowired
	private SqlSession sqlSession;
	
    @RequestMapping(value = "/beheer/briefAardLijst", method = RequestMethod.GET)
	public String start(Model model, HttpServletRequest request) throws Exception {
		return "beheer.briefAardLijst";
	}
	
	
	@RequestMapping(value = "/beheer/briefAarden", method = RequestMethod.GET)
	public @ResponseBody
	Response getBriefAardLijst( HttpSession session,  Model model) throws Exception {
		return new Response(getBriefAardList(), true, null);
	}

	@RequestMapping(value = "/beheer/briefAard/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestBody HashMap<String, String> briefAard) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.BriefAardMapper.updateBriefAard", briefAard);		
		return new Response(getBriefAardList(), true, null);
	}
	
	@RequestMapping(value = "/beheer/briefAard/insert", method = RequestMethod.POST)
	public @ResponseBody Response insert(@RequestBody HashMap<String, String> briefAard) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.BriefAardMapper.insertBriefAard", briefAard);		
		return new Response(getBriefAardList(), true, null);
	}
	
	@RequestMapping(value = "/beheer/briefAard/delete", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestBody HashMap<String, String> briefAard) throws Exception {
		Response response;
		try{
			sqlSession.update("be.ovam.art46.mappers.BriefAardMapper.deleteBriefAard", briefAard.get("brief_aard_id"));
			response = new Response(getBriefAardList(), true, null);
		} catch (DataIntegrityViolationException de) {
			response = new Response(null, false, "Deze briefAard kan niet verwijderd worden omdat er al brieven naar verwijzen.");
		} catch (Exception ex) {
			response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
		}
		return response;
	}
	
	private List getBriefAardList() {
		return sqlSession.selectList("be.ovam.art46.mappers.BriefAardMapper.getBriefAardList", null);
	}
}
