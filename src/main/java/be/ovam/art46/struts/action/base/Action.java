package be.ovam.art46.struts.action.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action extends org.apache.struts.action.Action  {
	
    private static Log log = LogFactory.getLog(Action.class);
	
	public abstract ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception; 
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {			
				
		ActionErrors errors = new ActionErrors();	
		ActionForward forward = null;
		try {			
			forward = execute(mapping, form, request, response, errors);
		} catch (org.springframework.dao.DataIntegrityViolationException ce) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.db.DataIntegrityViolation", ce.getCause()));	
			log.error(ce.getMessage());			
		} catch (org.hibernate.exception.GenericJDBCException he) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.db", he.getCause()));	
			log.error(he.getMessage());			
		} catch (Exception e) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.db", e));	
			log.error(e.getMessage()+e,e);
		} 
		if (!errors.isEmpty()) {
			saveErrors(request, errors);							
			return mapping.findForward("error");
		}
		if (forward != null) {
			return forward;
		}
		return mapping.findForward("success");	 	 		
	}
}
