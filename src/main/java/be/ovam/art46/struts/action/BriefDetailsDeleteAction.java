package be.ovam.art46.struts.action;

import be.ovam.art46.service.BriefService;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.BriefForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BriefDetailsDeleteAction extends Action {
		
	private BriefService briefService = (BriefService) LoadPlugin.applicationContext.getBean("briefService");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		briefService.deleteBrief(Integer.valueOf(((BriefForm) form).getBrief_id()));		
		return null;
	}
}
