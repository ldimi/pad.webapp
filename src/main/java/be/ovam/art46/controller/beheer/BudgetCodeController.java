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
public class BudgetCodeController {
	
	@Autowired
	private SqlSession sqlSession;

	
	@RequestMapping(value = "/beheer/budgetcodelijst", method = RequestMethod.GET)
	public String start(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("artikels", getBudgetairArtikelList());
		return "beheer.budgetcodelijst";
	}

	
	@RequestMapping(value = "/beheer/budgetcodes", method = RequestMethod.GET)
	public @ResponseBody
	List getBudgetCodeList( HttpSession session,  Model model) throws Exception {
		return getBudgetCodeList();
	}

	@RequestMapping(value = "/beheer/budgetcode/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestBody HashMap<String, String> budgetCode) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.BudgetCodeMapper.updateBudgetCode", budgetCode);		
		return new Response(getBudgetCodeList(), true, null);
	}
	
	@RequestMapping(value = "/beheer/budgetcode/insert", method = RequestMethod.POST)
	public @ResponseBody Response insert(@RequestBody HashMap<String, String> budgetCode) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.BudgetCodeMapper.insertBudgetCode", budgetCode);		
		return new Response(getBudgetCodeList(), true, null);
	}
	
	@RequestMapping(value = "/beheer/budgetcode/delete", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestBody HashMap<String, String> budgetCode) throws Exception {
		Response response;
		try{
			sqlSession.update("be.ovam.art46.mappers.BudgetCodeMapper.deleteBudgetCode", budgetCode.get("budget_code"));
			response = new Response(getBudgetCodeList(), true, null);
		} catch (DataIntegrityViolationException de) {
			response = new Response(null, false, "Deze budgetcode kan niet verwijderd worden omdat er al gegevens naar verwijzen.");
		} catch (Exception ex) {
			response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
		}
		return response;
	}
	
	private List getBudgetCodeList() {
		return sqlSession.selectList("be.ovam.art46.mappers.BudgetCodeMapper.getBudgetCodeList", null);
	}
	
	private List getBudgetairArtikelList() {
		return sqlSession.selectList("be.ovam.art46.mappers.BudgetairArtikelMapper.budgetairArtikels", null);
	}
}
