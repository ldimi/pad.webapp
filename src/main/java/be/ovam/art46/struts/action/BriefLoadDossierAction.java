package be.ovam.art46.struts.action;

import be.ovam.art46.dao.BriefDAO;
import be.ovam.art46.dao.DossierDAO;
import be.ovam.pad.model.Dossier;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.BriefForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BriefLoadDossierAction extends Action {
	
    private BriefDAO briefDAO = (BriefDAO) LoadPlugin.applicationContext.getBean("briefDAO");
	private DossierDAO dossierDAO = (DossierDAO) LoadPlugin.applicationContext.getBean("dossierDAO");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		BriefForm briefForm = (BriefForm) form;			
		Dossier dossier = (Dossier) dossierDAO.getDossierById(briefForm.getDossier_id());
		briefForm.setDossier_nr(dossier.getDossier_nr());
		briefForm.setDossier_type_ivs(dossier.getDossier_type());
		briefForm.setDossier_hdr_ivs(dossier.getDoss_hdr_id());
		briefForm.setDossier_b_ivs(dossier.getDossier_b());		
		briefForm.setNis_id(dossier.getNis_id());		
		briefForm.setAuteur_id(briefForm.getDossier_hdr_ivs());	
		
		briefForm.setCategorieen(briefDAO.getBriefCategorien(briefForm.getDossier_id()));
		
		return null;
	}
}
