package be.ovam.art46.controller.planning.jaar;

import be.ovam.web.Response;
import be.ovam.art46.service.planning.PlanningService;
import be.ovam.art46.util.Application;
import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.*;



@Controller("jaarPlanningController")
public class PlanningController {

	@Autowired
	private PlanningService planningService;


	@Autowired
	private SqlSession sqlSession;

	
	@RequestMapping(value = "/planning/jaar/overzichtbudgetten", method = RequestMethod.GET)
	public String overzichtbudgetten(Model model) throws Exception {
		model.addAttribute("programmaList",sqlSession.selectList("be.ovam.art46.mappers.ProgrammaMapper.getProgrammaList", null));
		return "planning.jaar.overzichtbudgetten";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/planning/jaar/budgetPerBudgetcode", method = RequestMethod.GET)
	public @ResponseBody
	List getBudgetPerBudgetcode(@RequestParam Integer jaar) throws Exception {
		HashMap params = new HashMap();
		params.put("jaar", jaar);
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getBudgetPerBudgetcode", params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/planning/jaar/budgetPerProgrammacode", method = RequestMethod.GET)
	public @ResponseBody
	List getBudgetPerProgrammacode(@RequestParam Integer jaar) throws Exception {
		HashMap params = new HashMap();
		params.put("jaar", jaar);
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getBudgetPerProgrammacode", params);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "planning/jaar/getProgrammaGeplandData", method = RequestMethod.POST)
	public @ResponseBody
	List getJB_ProgrammaGeplandData(@RequestBody ParamsDO params) throws Exception {
		if (params.getJaar() == null) {
			throw new RuntimeException("Jaar is een verplichte parameter.");
		}
		if (params.getDoss_hdr_id() != null) {
			throw new RuntimeException("Doss_hdr_id is geen toegelaten parameter.");
		}
		
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getGeplandeBedragen", params);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "planning/jaar/getProgrammaBenutData", method = RequestMethod.POST)
	public @ResponseBody
	List getJB_ProgrammaBenutData(@RequestBody ParamsDO params) throws Exception {
		if (params.getJaar() == null) {
			throw new RuntimeException("Jaar is een verplichte parameter.");
		}
		if (params.getDoss_hdr_id() != null) {
			throw new RuntimeException("Doss_hdr_id is geen toegelaten parameter.");
		}
		
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getVastgelegdeBedragen", params);
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "planning/jaar/getProgrammaGemarkeerdePlanningData", method = RequestMethod.POST)
	public @ResponseBody
	List getJB_ProgrammaGemarkeerdePlanningData(@RequestBody ParamsDO params) throws Exception {
		if (params.getMarkering_id() == null) {
			// lege lijst indien er geen markering geselecteerd was.
			return new ArrayList();
		}
		if (params.getJaar() == null) {
			throw new RuntimeException("Jaar is een verplichte parameter.");
		}
		if (params.getDoss_hdr_id() != null) {
			throw new RuntimeException("Doss_hdr_id is geen toegelaten parameter.");
		}
		
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getGemarkeerdePlanningBedragen", params);
	}

	
    @RequestMapping(value = "/planning/jaar/detailoverzicht/gepland", method = RequestMethod.GET)
    public String detailoverzichtGepland(@ModelAttribute("params") DetailOverzichtParamsDO params) throws Exception {
        params.setDeelopdracht_jn("N");
        return "planning.jaar.detailoverzicht.gepland";
    }

    @RequestMapping(value = "/planning/jaar/detailoverzicht/vastgelegd", method = RequestMethod.GET)
    public String detailoverzichtVastgelegd(@ModelAttribute("params") DetailOverzichtParamsDO params) throws Exception {
        params.setDeelopdracht_jn("N");
        return "planning.jaar.detailoverzicht.vastgelegd";
    }

    @SuppressWarnings({ "rawtypes", "unchecked"})
    @RequestMapping(value = "/planning/jaar/detailoverzicht/gepland/zoek", method = RequestMethod.GET)
    public String zoekDetailoverzichtGepland(@ModelAttribute("params") DetailOverzichtParamsDO params, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            return "planning.jaar.detailoverzicht.gepland";
        }
        List<Map> detailoverzichtLijst =  sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getDetailOverzichtGepland", params);
        
        double totaal_gepland = 0;
        double totaal_vastgelegd = 0;
        for (Map map : detailoverzichtLijst) {
            Object ig_bedrag = map.get("ig_bedrag");
            if (ig_bedrag != null) {
                totaal_gepland = totaal_gepland + ((Double) ig_bedrag);
            }
            Object ib_bedrag = map.get("ib_bedrag");
            if (ib_bedrag != null) {
                totaal_vastgelegd = totaal_vastgelegd + ((Double) ib_bedrag);
            }
        }
        
        model.addAttribute("detailoverzichtLijst", detailoverzichtLijst);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        model.addAttribute("totaal_gepland", formatter.format(totaal_gepland));
        model.addAttribute("totaal_vastgelegd", formatter.format(totaal_vastgelegd));
        return "planning.jaar.detailoverzicht.gepland";
    }

    @SuppressWarnings({ "rawtypes", "unchecked"})
    @RequestMapping(value = "/planning/jaar/detailoverzicht/vastgelegd/zoek", method = RequestMethod.GET)
    public String zoekDetailoverzichtVastgelegd(@ModelAttribute("params") DetailOverzichtParamsDO params, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            return "planning.jaar.detailoverzicht.vastgelegd";
        }
        List<Map> detailoverzichtLijst =  sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getDetailOverzichtVastgelegd", params);
        
        double totaal_gepland = 0;
        double totaal_vastgelegd = 0;
        for (Map map : detailoverzichtLijst) {
            Object ig_bedrag = map.get("ig_bedrag");
            if (ig_bedrag != null) {
                totaal_gepland = totaal_gepland + ((Double) ig_bedrag);
            }
            Object ib_bedrag = map.get("ib_bedrag");
            if (ib_bedrag != null) {
                totaal_vastgelegd = totaal_vastgelegd + ((Double) ib_bedrag);
            }
        }
        
        
        
        model.addAttribute("detailoverzichtLijst", detailoverzichtLijst);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        model.addAttribute("totaal_gepland", formatter.format(totaal_gepland));
        model.addAttribute("totaal_vastgelegd", formatter.format(totaal_vastgelegd));
        return "planning.jaar.detailoverzicht.vastgelegd";
    }

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/planning/jaar/markeer", method = RequestMethod.POST)
	public @ResponseBody Response markeer(@RequestBody HashMap markering) throws Exception {
		markering.put("creatie_user", Application.INSTANCE.getUser_id());
		Integer markering_id = planningService.markeer(markering); 
		return new Response(markering_id, true, null);
	}

}
