package be.ovam.art46.controller;

import be.ovam.web.Response;
import be.ovam.art46.service.sap.SapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@Scope("prototype")
public class SapController {

	@Autowired
	private SapService sapService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sap/getOrCreateProject", method = RequestMethod.GET)
	public @ResponseBody
	String getOrCreateProject(@RequestParam("id") Integer id, Model model) throws Exception {
		Map wbsNumbers = sapService.getOrCreateProject(id);
		return "PROJECT_DEF_NUMBER : " + wbsNumbers.get("PROJECT_DEF_NUMBER")  +
				"WBS_IVS_NR : " + wbsNumbers.get("WBS_IVS_NR");
	}

	@RequestMapping(value = "/sap/getOrCreateBestek", method = RequestMethod.GET)
	public @ResponseBody
	Response getOrCreateBestek(@RequestParam("id") Long id, Model model) throws Exception {
		
		try {
			String sv_wbs_nr =  sapService.getOrCreateBestek(id);
			return buildResponse(sv_wbs_nr);
			
		} catch (Exception e) {
			
			return errorResponse(e.getMessage());
			
		}
		
	}
	
	@RequestMapping(value = "/sap/getOrCreateVastlegging", method = RequestMethod.GET)
	public @ResponseBody
	String getOrCreateVastlegging(@RequestParam("twaalfNr") String twaalfNr, Model model) throws Exception {
		String vl_wbs_nr = sapService.getOrCreateVastlegging(twaalfNr);
		return "VASTLEGGING_WBS_NR : " + vl_wbs_nr;
	}
	
	
	@RequestMapping(value = "/sap/createSchuldVorderingen", method = RequestMethod.POST)
	public @ResponseBody
	Response createSchuldVorderingen(@RequestBody CreateSchuldVorderingenParams params) throws Exception {
		
		if (params.twaalfNr_2 != null && params.bedrag_2 == null)    {
			return errorResponse("Bedrag voor vastlegging 2 is niet gekend.");
		}
		if (params.twaalfNr_2 == null && params.bedrag_2 != null)    {
			return errorResponse("Vastlegging 2 is niet gekend, maar er is wel een bedrag voor ingevuld");
		}
		try {
			String msg = sapService.createSchuldVorderingen(params.vorderingId, params.twaalfNr, params.bedrag, params.twaalfNr_2, params.bedrag_2);
			return buildResponse(msg);
		} catch (Exception e) {
			return errorResponse(e.getMessage());
		}
	}
	
	private Response buildResponse(String wbsId) {
		return new Response(wbsId, true, null);
	}

	private Response errorResponse(String error) {
		return new Response(null, false,error);
	}


	public static class CreateSchuldVorderingenParams {

		public Integer vorderingId;
		public String twaalfNr;
		public BigDecimal bedrag;
		public String twaalfNr_2;
		public BigDecimal bedrag_2;

	}

}
