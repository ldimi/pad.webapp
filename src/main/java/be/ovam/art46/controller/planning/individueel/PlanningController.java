package be.ovam.art46.controller.planning.individueel;

import be.ovam.art46.model.planning.ParamsDO;
import be.ovam.art46.model.planning.PlanningDataDO;
import be.ovam.art46.service.planning.PlanningService;
import be.ovam.art46.util.Application;
import be.ovam.art46.util.DropDownHelper;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class PlanningController {
	
	@Autowired
	private PlanningService planningService;

	@Autowired
	private SqlSession sqlSession;

	
	@RequestMapping(value = "/planning/individueel/bodemEnAfval", method = RequestMethod.GET)
	public String bodemEnAfval(HttpServletRequest request, Model model) throws Exception {
		String doss_hdr_id =  getDossierhouderId(request);
		model.addAttribute("dossiersDD", sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getDossiersDDbyUid", doss_hdr_id));
        addDossierhouder(model,doss_hdr_id);		
		return "planning.individueel.bodemEnAfval";
	}

    @RequestMapping(value = "/planning/individueel/bodemEnAfval2", method = RequestMethod.GET)
	public String bodemEnAfval2(HttpServletRequest request, Model model) throws Exception {
		String doss_hdr_id =  getDossierhouderId(request);
        
		model.addAttribute("dossiersDD", sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getDossiersDDbyUid", doss_hdr_id));
        model.addAttribute("jaren", DropDownHelper.INSTANCE.getJaren());
        
		model.addAttribute("faseDD", sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getFaseDD"));
		model.addAttribute("faseDetailDD", sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getFaseDetailDD"));
        
		model.addAttribute("contractenDD", sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getContractenDD", doss_hdr_id));
       
        
		addDossierhouder(model,doss_hdr_id);
        return jsview("planning.individueel.bodemEnAfval", "planning/individueel/bodemEnAfval2", model);
		//return "planning.individueel.bodemEnAfval";
	}


	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/planning/individueel/raamcontracten", method = RequestMethod.GET)
	public String raamcontracten(HttpServletRequest request, Model model) throws Exception {
		String doss_hdr_id =  getDossierhouderId(request);
		List raamContractenDD = sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getRaamcontractenDDbyUid", doss_hdr_id);
		model.addAttribute("raamContractenDD", raamContractenDD);
		addDossierhouder(model, doss_hdr_id);
		return "planning.individueel.raamcontracten";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/planning/individueel/gegroepeerdeOpdrachten", method = RequestMethod.GET)
	public String gegroepeerdeOpdrachten(HttpServletRequest request, Model model) throws Exception {
		String doss_hdr_id =  getDossierhouderId(request);		
		List gegroepeerdeOpdrachtenDD = sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getGegroepeerdeOpdrachtenDDbyUid", doss_hdr_id);
		model.addAttribute("gegroepeerdeOpdrachtenDD", gegroepeerdeOpdrachtenDD);
		addDossierhouder(model, doss_hdr_id);
		return "planning.individueel.gegroepeerdeOpdrachten";
	}

	
	@RequestMapping(value = "/planning/individueel/takenlijst", method = RequestMethod.GET)
	public String takenlijst(HttpServletRequest request, Model model) throws Exception {
		String doss_hdr_id =  getDossierhouderId(request);
		addDossierhouder(model, doss_hdr_id);
		return "planning.individueel.takenlijst";
	}

	@RequestMapping(value = "/planning/individueel/grafieken", method = RequestMethod.GET)
	public String grafieken(HttpServletRequest request, Model model) throws Exception {
		String doss_hdr_id =  getDossierhouderId(request);
		//addDossierhouder(model, doss_hdr_id);
		model.addAttribute("doss_hdr_id", doss_hdr_id);
		model.addAttribute("dossierhouders",DropDownHelper.INSTANCE.getDossierhouders());
		model.addAttribute("jaren",DropDownHelper.INSTANCE.getJaren());
		model.addAttribute("markeringen",DropDownHelper.INSTANCE.getPlanningMarkeringenDD());
        
		return jsview("planning.individueel.grafieken", "planning/individueel/grafieken", model);
	}
	
	@RequestMapping(value = "/planning/getPlanning", method = RequestMethod.POST)
	public @ResponseBody
	PlanningDataDO getPlanning(@RequestBody ParamsDO params ,  Model model) throws Exception {
		if (params.getDoss_hdr_id() == null && StringUtils.isBlank(params.getDossier_id()) ) {
			params.setDoss_hdr_id(Application.INSTANCE.getUser_id());
		}
		return planningService.getPlanning(params);
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/planning/getTaken", method = RequestMethod.POST)
	public @ResponseBody
	List getTaken(@RequestBody ParamsDO params) throws Exception {
		if (params.getDoss_hdr_id() == null) {
			params.setDoss_hdr_id(Application.INSTANCE.getUser_id());
		}
		params.setBenut_jn("N");
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getPlanningDetails", params);
	}

	
	@RequestMapping(value = "/planning/bewaar", method = RequestMethod.POST)
	public @ResponseBody
	PlanningDataDO bewaar(@RequestBody PlanningDataDO planning) throws Exception {		
		planning =  planningService.bewaar(planning);		
		return planning;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/planning/getBestekkenByDossier", method = RequestMethod.POST)
	public @ResponseBody
	List getBestekkenByDossier(@RequestBody Integer dossier_id) throws Exception {
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getBestekkenByDossier", dossier_id);
	}

		
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/planning/getOverzichtRaamcontract", method = RequestMethod.POST)
	public @ResponseBody
	List getOverzichtRaamcontract(@RequestBody Integer dossier_id) throws Exception {
		return planningService.getOverzichtRaamcontract(dossier_id);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/planning/getDetailsVoorBestek", method = RequestMethod.POST)
	public @ResponseBody
	List getDetailsVoorBestek(@RequestBody Integer bestek_id) throws Exception {
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getDetailsVoorBestek", bestek_id);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/planning/getDetailsVoorFase", method = RequestMethod.POST)
	public @ResponseBody
	List getDetailsVoorFase(@RequestBody Map params) throws Exception {
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getDetailsVoorFase", params);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/planning/individueel/grafiek/jaarData", method = RequestMethod.POST)
	public @ResponseBody
	Map getIB_jaardata(@RequestBody be.ovam.art46.controller.planning.jaar.ParamsDO params ,  Model model) throws Exception {
		if (params.getDoss_hdr_id() == null) {
			params.setDoss_hdr_id(Application.INSTANCE.getUser_id());
		}
		if (params.getJaar() == null) {
			throw new RuntimeException("Jaar is een verplichte parameter.");
		}
		if (params.getProgramma_code() != null) {
			throw new RuntimeException("Programma_code is geen toegelaten parameter.");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("gepland", sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getGeplandeBedragen", params));
		result.put("vastgelegd", sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getVastgelegdeBedragen", params));
		if (params.getMarkering_id() == null) {
			// lege lijst indien er geen markering geselecteerd was.
			result.put("gemarkeerd", new ArrayList());
		} else {
			result.put("gemarkeerd", sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getGemarkeerdePlanningBedragen", params));
		}
		return result;
	}
	
	private void addDossierhouder(Model model, String doss_hdr_id) {
		model.addAttribute("dossierhouders",DropDownHelper.INSTANCE.getDossierhouders());
		model.addAttribute("hdr_id", doss_hdr_id);      // TODO :  deprecated, te verwijderen
		model.addAttribute("doss_hdr_id", doss_hdr_id);
	}
	
	private String getDossierhouderId(HttpServletRequest request){
		String doss_hdr_id =  request.getParameter("doss_hdr_id");
		if(StringUtils.isEmpty(doss_hdr_id)){
			doss_hdr_id = Application.INSTANCE.getUser_id();
		}	
		return doss_hdr_id;
	}

	public void setPlanningService(PlanningService planningService) {
		this.planningService = planningService;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}	

}
