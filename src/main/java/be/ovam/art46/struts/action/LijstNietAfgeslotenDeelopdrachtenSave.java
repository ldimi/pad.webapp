package be.ovam.art46.struts.action;

import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.DeelopdrachtLijstForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LijstNietAfgeslotenDeelopdrachtenSave extends Action {

	private DeelOpdrachtService service = (DeelOpdrachtService) LoadPlugin.applicationContext.getBean("deelOpdrachtService");

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		service.saveDeelopdrachtGoedkeuring_d((DeelopdrachtLijstForm) form);
		return null;
	}

	
	

}
