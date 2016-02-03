package be.ovam.art46.controller.beheer;

import be.ovam.web.Response;
import be.ovam.art46.model.planning.PlanningFaseDO;
import be.ovam.art46.model.planning.PlanningFaseDetailDO;
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
import java.util.List;

@SuppressWarnings("rawtypes")
@Controller
public class PlanningFaseController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/beheer/planningfaselijst", method = RequestMethod.GET)
	public String start(Model model, HttpServletRequest request) throws Exception {
		return "beheer.planningfaselijst";
	}
	
	@RequestMapping(value = "/planningFasen", method = RequestMethod.GET)
	public @ResponseBody
	Response getPlanningFaseList() throws Exception {
		List result = sqlSession.selectList("be.ovam.art46.mappers.PlanningFaseMapper.getPlanningFaseList", null);
		return new Response(result, true, null);
	}

	@RequestMapping(value = "/planningFase/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestBody PlanningFaseDO fase) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.PlanningFaseMapper.updatePlanningFase", fase);		
		return getPlanningFaseList();
	}
	
	@RequestMapping(value = "/planningFase/insert", method = RequestMethod.POST)
	public @ResponseBody Response insert(@RequestBody PlanningFaseDO fase) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.PlanningFaseMapper.insertPlanningFase", fase);		
		return getPlanningFaseList();
	}
	
	@RequestMapping(value = "/planningFase/delete", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestBody PlanningFaseDO fase) throws Exception {
		Response response;
		try{
			sqlSession.update("be.ovam.art46.mappers.PlanningFaseMapper.deletePlanningFase", fase);
			response = getPlanningFaseList();
		} catch (DataIntegrityViolationException de) {
			response = new Response(null, false, "Deze plannings-fase kan niet verwijderd worden omdat er al planningslijnen naar verwijzen.");
		} catch (Exception ex) {
			response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
		}
		return response;
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

	@RequestMapping(value = "/planningFaseDetail/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestBody PlanningFaseDetailDO faseDetail) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.PlanningFaseMapper.updatePlanningFaseDetail", faseDetail);		
		return getPlanningFaseDetailList(faseDetail);
	}
	
	@RequestMapping(value = "/planningFaseDetail/insert", method = RequestMethod.POST)
	public @ResponseBody Response insert(@RequestBody PlanningFaseDetailDO faseDetail) throws Exception {
		sqlSession.update("be.ovam.art46.mappers.PlanningFaseMapper.insertPlanningFaseDetail", faseDetail);		
		return getPlanningFaseDetailList(faseDetail);
	}
	
	@RequestMapping(value = "/planningFaseDetail/delete", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestBody PlanningFaseDetailDO faseDetail) throws Exception {
		Response response;
		try{
			sqlSession.update("be.ovam.art46.mappers.PlanningFaseMapper.deletePlanningFaseDetail", faseDetail);
			response = getPlanningFaseDetailList(faseDetail);
		} catch (DataIntegrityViolationException de) {
			response = new Response(null, false, "Deze plannings-detailfase kan niet verwijderd worden omdat er al planningslijnen naar verwijzen.");
		} catch (Exception ex) {
			response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
		}
		return response;
	}
	
}
