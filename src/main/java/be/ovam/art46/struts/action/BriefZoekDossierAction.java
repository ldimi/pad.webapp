package be.ovam.art46.struts.action;

import be.ovam.art46.struts.actionform.BriefForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BriefZoekDossierAction extends Action {
		
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse respons)
			throws Exception {			
				
			BriefForm briefForm = (BriefForm) form;
			if ("A".equals(briefForm.getDossier_type_ivs())) {
				return mapping.findForward("succes_afval");	
			}				
			return mapping.findForward("success_bodem");	 		
		}
}
