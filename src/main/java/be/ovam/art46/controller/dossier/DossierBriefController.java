package be.ovam.art46.controller.dossier;

import be.ovam.art46.service.ActieService;
import be.ovam.art46.struts.actionform.DossierArt46Form;
import be.ovam.art46.struts.actionform.DossierKadasterForm;
import be.ovam.art46.util.DateFormatArt46;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.pad.model.Dossier;
import static be.ovam.web.util.JsView.jsview;
import com.jenkov.prizetags.tree.itf.ITree;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("briefFilterParams")
@Controller
public class DossierBriefController extends BasisDossierController  {

    
    @RequestMapping(value = "/dossier/{dossier_id}/brieven", method = RequestMethod.GET)
    public String startBrieven(
            @ModelAttribute("briefFilterParams") BriefFilterParamsDO briefFilterParams,
            @PathVariable Integer dossier_id,
            @RequestParam(required = false) String view,
            Model model,
            HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);

		model.addAttribute("dossierdetailsopenbesteklijst", getOpenBestekLijst(dossier_id));        
        
		//BriefFilterParamsDO briefFilterParams = (BriefFilterParamsDO) session.getAttribute("briefFilterParams");
		if (view == null) {
			buildTree(dossier_id, briefFilterParams, session);			
		}
        return "dossier.brieven";
    }
    
    @RequestMapping(value = "/dossier/{dossier_id}/brieven", method = RequestMethod.POST)
    public String filterBrieven(
            @ModelAttribute("briefFilterParams") BriefFilterParamsDO briefFilterParams,
            @PathVariable Integer dossier_id,
            @RequestParam(required = false) String view,
            Model model,
            HttpSession session) throws Exception {

		putDossierInModelAndSession(dossier_id, model, session);

		model.addAttribute("dossierdetailsopenbesteklijst", getOpenBestekLijst(dossier_id));
        
		BriefFilterParamsDO briefFilterParamsS = (BriefFilterParamsDO) session.getAttribute("briefFilterParams");
        
		if (view == null) {
			buildTree(dossier_id, briefFilterParams, session);			
		}
        return "dossier.brieven";
    }

    private void buildTree(Integer dossier_id, BriefFilterParamsDO briefFilterParams, HttpSession session) throws Exception {
        ITree tree = briefService.buildTree(dossier_id,
                briefFilterParams.getUit_type_id_vos(),
                briefFilterParams.getBestek_id(),
                briefFilterParams.getDatum_van(),
                briefFilterParams.getDatum_tot());
        
        if (briefFilterParams.getUit_type_id_vos() != null ||
                briefFilterParams.getBestek_id() != null ||
                briefFilterParams.getDatum_van() != null ||
                briefFilterParams.getDatum_tot() != null     ) {
            tree.expandAll();
        }
        session.setAttribute("briefTree", tree);
        
        DossierArt46Form dossierart46form = (DossierArt46Form) session.getAttribute("dossierart46form");
        if (dossierart46form != null) {
            if ( briefFilterParams.getBestek_id() != null) {
                dossierart46form.setBestek_id(briefFilterParams.getBestek_id().toString());
            } else {
                dossierart46form.setBestek_id(null);
            }
            
        }
    }


}
