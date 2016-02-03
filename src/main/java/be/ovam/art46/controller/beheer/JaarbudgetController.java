package be.ovam.art46.controller.beheer;

import be.ovam.web.Response;
import be.ovam.art46.model.planning.Jaarbudget;
import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class JaarbudgetController {
	
	@Autowired
	private SqlSession sqlSession;

	
	@RequestMapping(value = "/beheer/jaarbudget", method = RequestMethod.GET)
	public String start(Model model) throws Exception {
		model.addAttribute("budgetCodeList", getBudgetCodeList());
		return "beheer.jaarbudget";
	}

	@RequestMapping(value = "/beheer/getjaarbudgetten", method = RequestMethod.GET)
	public @ResponseBody
	List getJaarbudgetList(@RequestParam int jaar) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("jaar", jaar);
		return sqlSession.selectList("be.ovam.art46.mappers.JaarbudgetMapper.getJaarbudgetList", map);
	}

	@RequestMapping(value = "/beheer/jaarbudget/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestBody Jaarbudget jaarbudget) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.JaarbudgetMapper.updateJaarbudget", jaarbudget);		
		return new Response(getJaarbudgetList(jaarbudget.getJaar()), true, null);
	}
	
	@RequestMapping(value = "/beheer/jaarbudget/insert", method = RequestMethod.POST)
	public @ResponseBody Response insert(@RequestBody Jaarbudget jaarbudget) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.JaarbudgetMapper.insertJaarbudget", jaarbudget);		
		return new Response(getJaarbudgetList(jaarbudget.getJaar()), true, null);
	}	
	
	@RequestMapping(value = "/beheer/jaarbudget/delete", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestBody Jaarbudget jaarbudget) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.JaarbudgetMapper.delete", jaarbudget);		
		return new Response(getJaarbudgetList(jaarbudget.getJaar()), true, null);
	}
	
	private List getBudgetCodeList() {
		return sqlSession.selectList("be.ovam.art46.mappers.BudgetCodeMapper.getBudgetCodeList", null);
	}
	
}
