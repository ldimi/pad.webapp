package be.ovam.art46.struts.action;

import be.ovam.art46.service.BestekService;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.SAPFactuurDossierForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DossierDetailsFacturenSaveAction extends Action {
	
	private BestekService service = (BestekService) LoadPlugin.applicationContext.getBean("bestekService");

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		service.addFactuurDossier((SAPFactuurDossierForm) form);
		return null;
	}
}
