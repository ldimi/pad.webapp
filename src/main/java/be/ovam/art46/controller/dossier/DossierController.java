package be.ovam.art46.controller.dossier;

import be.ovam.art46.service.ActieService;
import be.ovam.art46.service.dossier.DossierService;
import be.ovam.art46.struts.actionform.DossierArt46Form;
import be.ovam.art46.struts.actionform.DossierKadasterForm;
import be.ovam.art46.util.Application;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.web.Response;
import static be.ovam.web.util.JsView.jsview;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DossierController extends BasisDossierController {

    @Autowired
    protected ActieService actieService;

    @Autowired
    protected DossierService dossierService;
    
    private final DropDownHelper DDH = DropDownHelper.INSTANCE;

    @RequestMapping(value = "/dossier", method = RequestMethod.GET)
    public String start(
                @RequestParam(required = false) Integer id,
                @RequestParam(required = false) String dossier_nr,
                @RequestParam(required = false) String selectedtab,
                HttpSession session,
                HttpServletRequest request,
                Model model
    ) throws Exception  {

        if (selectedtab != null) {
            selectedtab = selectedtab.toLowerCase();
            
            if ("project fiche".equals(selectedtab)) {
                selectedtab = "projectfiche";
            } else if ("toegang webloket".equals(selectedtab)) {
                selectedtab = "toegangwebloket";
            } else if ("basisgegevens".equals(selectedtab)) {
                selectedtab = "basis";
            } else if ("contactpersonen".equals(selectedtab)) {
                selectedtab = "contact";
            }
        } else {
            selectedtab = "basis";
        }
        
        // zorgen dat alle requestparams bij redirect doorgegeven worden
        //  (nodig briefhierarchie, lijst paging,..)
        Map pmap =  request.getParameterMap();
        model.addAllAttributes(pmap);
        
        
        String dossier_id;
        if (id != null) {
            dossier_id = id.toString();
        } else if (dossier_nr != null) {
            DossierDO dossier = sqlSession.selectOne("getDossierDObyNr", dossier_nr);
            dossier_id = dossier.getId().toString();
        } else {
            DossierArt46Form dossierart46form = (DossierArt46Form) session.getAttribute("dossierart46form");
            if (dossierart46form == null) {
                throw new RuntimeException("Geen Dossier in Session.");
            }
            dossier_id = dossierart46form.getId();
        }

        return "redirect:/s/dossier/" + dossier_id + "/" + selectedtab;
    }

    @RequestMapping(value = "/dossier/{dossier_id}/basis", method = RequestMethod.GET)
    public String startBasis(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception  {
        
		DossierDO dossierDO = putDossierInModelAndSession(dossier_id, model, session);

        addModelAttrsForBasisscherm(model, dossierDO);

                
        return jsview("dossier.basis", "dossier/basis", model);
    }

    @RequestMapping(value = "/dossier/afval/nieuw", method = RequestMethod.GET)
    public String startAfvalNieuw(Model model, HttpSession session) throws Exception  {
        DossierDO dossierDO = new DossierDO();
        dossierDO.setDossier_type("A");
        dossierDO.setDoss_hdr_id(Application.INSTANCE.getUser_id());
        
        plaatsVersDossierInModelEnSessie(dossierDO, model, session);
        
        addModelAttrsForBasisscherm(model, dossierDO);
    
        return jsview("dossier.basis", "dossier/basis", model);
    }
   
    @RequestMapping(value = "/dossier/ander/nieuw", method = RequestMethod.GET)
    public String startAnderNieuw(Model model, HttpSession session) throws Exception  {
        DossierDO dossierDO = new DossierDO();
        dossierDO.setDossier_type("X");
        dossierDO.setDoss_hdr_id(Application.INSTANCE.getUser_id());
        
        plaatsVersDossierInModelEnSessie(dossierDO, model, session);
        
        addModelAttrsForBasisscherm(model, dossierDO);
    
        return jsview("dossier.basis", "dossier/basis", model);
    }
   
    private void addModelAttrsForBasisscherm(Model model, DossierDO dossierDO) {
        model.addAttribute("isAdminIVS", Application.INSTANCE.isUserInRole("adminIVS") );
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46") );
        
        model.addAttribute("rechtsgronden", DDH.getDossierRechtsgronden());
        model.addAttribute("fasen", DDH.getDossierFasen_dd());
        model.addAttribute("dossierhouders", DDH.getDossierhouders());
        model.addAttribute("fusiegemeenten", DDH.getFusiegemeenten()); 
        model.addAttribute("programmaTypes", DDH.getProgrammaTypes());
        model.addAttribute("doelgroepen_dd", DDH.getDoelgroepen_dd());
       
        if (dossierDO.getId() != null) {
            if ("B".equals(dossierDO.getDossier_type())) {
                model.addAttribute("dossierAdressen", sqlSession.selectList("getDossierAdressen", dossierDO.getId()));
                model.addAttribute("mistralUrl", System.getProperty("pad.mistral2Url"));
            }

            List bestekNrs = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getBestekNrsByDossierId", dossierDO.getId());
            if (bestekNrs.size() > 0) {
                dossierDO.setAanpak_s("1");
            } else {
                dossierDO.setAanpak_s("0");
            }
        }
    }

    
    
    @RequestMapping(value = "/dossier/save", method = RequestMethod.POST)
    public @ResponseBody Response save(@RequestBody DossierDO dossierDO) throws Exception {
        DossierDO dossier = dossierService.saveDossier(dossierDO);
        return new Response(dossier.getId());
    }

    @RequestMapping(value = "/dossier/financieel/save", method = RequestMethod.POST)
    public String save(@RequestParam Integer id, @RequestParam String financiele_info) throws Exception {
        Map dossier = new HashMap();
        dossier.put("id", id);
        dossier.put("financiele_info", financiele_info);
        sqlSession.updateInTable("art46", "dossier", dossier);
        return "redirect:/s/dossier/" + id + "/projectfiche";
    }

    
    
    
    
    @RequestMapping(value = "/dossier/{dossier_id}/jd", method = RequestMethod.GET)
    public String startJd(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);
        
		model.addAttribute("dossiers", dossierJDService.getDossiersJDById(dossier_id));		
	    model.addAttribute("juridischedossiers", dossierJDService.getJuridischDossiersById(dossier_id));
        
        
        return "dossier.jd";
    }
    
    @RequestMapping(value = "/dossier/{dossier_id}/opname", method = RequestMethod.GET)
    public String startOpname(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);
        
        DossierKadasterForm dossierKadasterForm = new DossierKadasterForm();
        dossierKadasterForm.reset(session);
        session.setAttribute("dossierkadastersform", dossierKadasterForm);
        
		model.addAttribute("dossierdetailsopname", sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getOpnameLijst", dossier_id));
        
        return "dossier.opname";
    }
    
    
    @RequestMapping(value = "/dossier/{dossier_id}/publicatie", method = RequestMethod.GET)
    public String startPublicatie(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);
        
		model.addAttribute("dossierdetailspublicatie", sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getPublicatieLijst", dossier_id));        
        
        return "dossier.publicatie";
    }
    
    @RequestMapping(value = "/dossier/{dossier_id}/planning", method = RequestMethod.GET)
    public String startPlanning(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		DossierDO dossier = putDossierInModelAndSession(dossier_id, model, session);
        
        model.addAttribute("ramingenHistoriek", sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierRamingenHistoriek", dossier_id));
		model.addAttribute("ramingen", sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierRamingen", dossier_id));
		
		String dossierType = dossier.getDossier_type();
		if ("A".equals(dossierType)) {
			model.addAttribute("fases", DDH.getFaseRamingAfval());
		} 
		else {
			model.addAttribute("fases", DDH.getFaseRaming());
		}
        
        return "dossier.planning";
    }
    
    @RequestMapping(value = "/dossier/{dossier_id}/bestek", method = RequestMethod.GET)
    public String startBestek(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);
        
		model.addAttribute("dossierdetailsopenbesteklijst", getOpenBestekLijst(dossier_id)); 
		model.addAttribute("dossierdetailsafgeslotenbesteklijst", getAfgeslotenBestekLijst(dossier_id)); 
        
        return "dossier.bestek";
    }
    
    @RequestMapping(value = "/dossier/{dossier_id}/facturen", method = RequestMethod.GET)
    public String startFacturen(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);
        
	    Map<String, Object> params= new HashMap<String, Object>();
	    params.put("id", dossier_id);
	    params.put("afgesloten_jn", "N");
	    	    
		List openFactuurLijst = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getFactuurLijst", params);
		model.addAttribute("lijstsapfacturen", openFactuurLijst);
		
		params.put("afgesloten_jn", "J");
        List geslotenFactuurLijst = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getFactuurLijst", params);
        model.addAttribute("lijstsapfacturengesloten", geslotenFactuurLijst);
        
        List dossierLijst = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getFacturenDossiersLijst", dossier_id);
        model.addAttribute("lijstsapfacturendossiers", dossierLijst);
        
        return "dossier.facturen";
    }

    @RequestMapping(value = "/dossier/{dossier_id}/archief", method = RequestMethod.GET)
    public String startArchief(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);
        
		model.addAttribute("dossierdetailsarchieflijst", sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDetailsArchiefLijst", dossier_id)); 
        
        return "dossier.archief";
    }
        
    @RequestMapping(value = "/dossier/{dossier_id}/projectfiche", method = RequestMethod.GET)
    public String startProjectfiche(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		DossierDO dossier = putDossierInModelAndSession(dossier_id, model, session);
        
		model.addAttribute("dossierafspraken", actieService.getDossierAfspraken(dossier_id));
        
        if ("B".equals(dossier.getDossier_type())) {
            model.addAttribute("overdrachtsFiches", sqlSession.selectList("getOverdrachtsFiches", dossier.getDossier_id_boa()));
        }
        
        return "dossier.projectfiche";
    }
        

    
    

    private List getAfgeslotenBestekLijst(Integer dossier_id) {
	    Map<String, Object> params= new HashMap<String, Object>();
	    params.put("id", dossier_id);
	    params.put("afgesloten_jn", "J");
	    	    
		return sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getBestekLijst", params);
    }
}
