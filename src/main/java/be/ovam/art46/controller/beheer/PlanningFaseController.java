package be.ovam.art46.controller.beheer;

import be.ovam.web.Response;
import be.ovam.art46.model.planning.PlanningFaseDO;
import be.ovam.art46.model.planning.PlanningFaseDetailDO;
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
import java.util.List;

@SuppressWarnings("rawtypes")
@Controller
public class PlanningFaseController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/beheer/planningfaselijst", method = RequestMethod.GET)
	public String start2(Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("title", "Beheer Planning fasen");
        model.addAttribute("menuId", "m_toepassingsbeheer.planningfasen");
        
		model.addAttribute("budgetCodeDD", sqlSession.selectList("be.ovam.art46.mappers.BudgetCodeMapper.getBudgetCodeDD"));

        return jsview("beheer/planningFaseLijst", model);
	}

	@RequestMapping(value = "/planningFasen", method = RequestMethod.GET)
	public @ResponseBody
	Response getPlanningFaseList() throws Exception {
		List result = sqlSession.selectList("be.ovam.art46.mappers.PlanningFaseMapper.getPlanningFaseList", null);
		return new Response(result, true, null);
	}

    
	
	@RequestMapping(value = "/planningFaseDetails", method = RequestMethod.POST)
	public @ResponseBody
	Response getPlanningFaseDetailList(@RequestBody PlanningFaseDO fase) throws Exception {
		List result = sqlSession.selectList("be.ovam.art46.mappers.PlanningFaseMapper.getPlanningFaseDetailList", fase);
		return new Response(result, true, null);
	}

	private Response getPlanningFaseDetailList(PlanningFaseDetailDO faseDetail) throws Exception {
		PlanningFaseDO fase = new PlanningFaseDO();
		fase.setFase_code(faseDetail.getFase_code());
		return getPlanningFaseDetailList(fase);
	}

    
    @RequestMapping(value = "/beheer/planningFase/save", method = RequestMethod.POST)
    public @ResponseBody Response saveFase(@RequestBody PlanningFaseDO fase) throws Exception  {
        sqlSession.saveInTable("art46", "planning_fase", fase);
        clearCache();
        return getPlanningFaseList();
    }

    @RequestMapping(value = "/beheer/planningFaseDetail/save", method = RequestMethod.POST)
    public @ResponseBody Response saveFaseDetail(@RequestBody PlanningFaseDetailDO faseDetail) throws Exception  {
        sqlSession.saveInTable("art46", "planning_fase_detail", faseDetail);
        clearCache();
        return getPlanningFaseDetailList(faseDetail);
    }

    private void clearCache() {
        sqlSession.getConfiguration().getCache("be.ovam.art46.mappers.PlanningFaseMapper").clear();
    }
    
    
    
}
