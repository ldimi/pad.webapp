
package be.ovam.art46.struts.action;

import be.ovam.art46.dao.DossierKadasterDAO;
import be.ovam.art46.struts.actionform.Art46BaseForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Action om goedkeuring_s van records in RS.ART46_VOOR_GOEDK te updaten via de mehode {@link be.ovam.art46.dao.DossierKadasterDAO#updateDossierKadasterVG updateDossierKadasterVG()}.
 * 
 * @author Tonny Bruckers
 * @version 0.1 (2004/04/24)
 */

public class UpdateDossierKadastersVoorAction extends Action {

	private DossierKadasterDAO dao = (DossierKadasterDAO) LoadPlugin.applicationContext.getBean("dossierKadasterDAO");
	
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse respons)
		throws Exception {			
			ActionErrors errors = new ActionErrors();
			Art46BaseForm dosKadsform = (Art46BaseForm) form;							
			try {							
				dao.updateDossierKadasterVG(request.getParameterValues("dossierkadasters"),dosKadsform.getGoedkeuring_s());				
			} catch (SQLException e) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.update.dossierkadaster",e));								
			}
		if (!errors.isEmpty()) {
			saveErrors(request,errors);	
			if ("1".equals(dosKadsform.getGoedkeuring_s())) {
				return mapping.findForward("error_opname");	
			} 
			else {
				return mapping.findForward("error_goedk");
			}			
		}	
		if ("1".equals(dosKadsform.getGoedkeuring_s())) {
			return mapping.findForward("success_opname");	
		} 
		else {
			return mapping.findForward("success_goedk");
		}	
	}

}
