package be.ovam.art46.struts.action.base;

import be.ovam.art46.struts.actionform.SelectForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse respons) throws Exception {	
		
			SelectForm dosForm = (SelectForm) form;			
			return mapping.findForward(dosForm.getForward());
	}

}
