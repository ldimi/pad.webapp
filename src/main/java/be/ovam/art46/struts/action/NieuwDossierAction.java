package be.ovam.art46.struts.action;

import be.ovam.art46.struts.actionform.BriefDossierForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NieuwDossierAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse respons)
			throws Exception {
					
			BriefDossierForm briefDossierForm = (BriefDossierForm) form;				
			if (briefDossierForm.getDossier_type_ivs()!= null && briefDossierForm.getDossier_type_ivs().equals("A")) {
				return mapping.findForward("success_afval");
			} 
			if (briefDossierForm.getDossier_type_ivs()!= null && briefDossierForm.getDossier_type_ivs().equals("X")) {
				return mapping.findForward("success_ander");
			} 
			return mapping.findForward("success_bodem");	 		
		}

}
