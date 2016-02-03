package be.ovam.art46.struts.action.schuldvordering;

import be.ovam.art46.dao.SchuldvorderingDAO;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.schuldvordering.SchuldvorderingLijstForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SchuldvorderinglijstSaveAction extends Action {
		
	private SchuldvorderingDAO dao = new SchuldvorderingDAO();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		dao.saveBetaal_d(((SchuldvorderingLijstForm) form).getBetaal_ds());
		return null;		
	}
}
