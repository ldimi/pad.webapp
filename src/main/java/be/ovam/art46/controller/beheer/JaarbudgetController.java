package be.ovam.art46.controller.beheer;

import be.ovam.art46.util.Application;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.util.mybatis.SqlSession;
import static be.ovam.web.util.JsView.jsview;
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
		model.addAttribute("budgetCode_dd", sqlSession.selectList("be.ovam.art46.mappers.BudgetCodeMapper.getBudgetCode_dd"));
        
        model.addAttribute("jaren", DropDownHelper.INSTANCE.getJaren());
        
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        model.addAttribute("title", "Beheer jaarbudget");
        model.addAttribute("menuId", "m_toepassingsbeheer.jaarbudget");
    
        return jsview("beheer/jaarbudgetlijst", model);
	}
    
    
	@RequestMapping(value = "/beheer/getjaarbudgetten", method = RequestMethod.GET)
	public @ResponseBody
	List getJaarbudgetList(@RequestParam int jaar) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("jaar", jaar);
		return sqlSession.selectList("be.ovam.art46.mappers.JaarbudgetMapper.getJaarbudgetList", map);
	}

	@RequestMapping(value = "/beheer/jaarbudget/save", method = RequestMethod.POST)
	public @ResponseBody List save(@RequestBody Map jaarbudget) throws Exception {
        sqlSession.saveInTable("art46", "jaarbudget", jaarbudget);
        clearCache();
		return getJaarbudgetList((Integer) jaarbudget.get("jaar"));
	}

	private void clearCache() {
        // no action : er is geen cache
	}
    
    
    
}
