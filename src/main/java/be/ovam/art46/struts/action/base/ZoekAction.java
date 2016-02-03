package be.ovam.art46.struts.action.base;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ZoekAction extends Action {
	
	public abstract Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		request.getSession().setAttribute("zoeklijst", fecthResult(mapping, form, request, response));	
		resetZoekAction(mapping, request);
		return null;
	}
	
	public static void resetZoekAction(ActionMapping mapping, HttpServletRequest request) {
		request.getSession().setAttribute("sublijst", null);		
		request.getSession().setAttribute("zoekaction", mapping.findForwardConfig("success").getPath());
	}
}
