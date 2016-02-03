package be.ovam.art46.struts.action;

import be.ovam.art46.dao.DossierKadasterDAO;
import be.ovam.art46.struts.actionform.Art46BaseForm;
import be.ovam.art46.struts.actionform.DossierKadasterForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * Action om records aan RS.ART46_NA_GOEDK toe te voegen via de methode {@link be.ovam.art46.dao.DossierKadasterDAO#insertDossierKadasterNG insertDossierKadasterNG()}   
 * 
 * @author Tonny Bruckers
 * @version 0.1 (2004/04/24)
 */

public class SetGoedkeuringsDatumAction extends Action {
	
	private DossierKadasterDAO dao = (DossierKadasterDAO) LoadPlugin.applicationContext.getBean("dossierKadasterDAO");
	
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse respons)
		throws Exception {
			// Lijst met alle reeds bestaande dossierkadasters met lijst_id	
			List<String> dosKadOld = null;		
			ActionErrors errors = new ActionErrors();			
			DossierKadasterForm dosKadsform = (DossierKadasterForm) form;			
			try {				
				dosKadOld = dao.insertDossierKadasterNG(dosKadsform.getDossierkadasters(),dosKadsform.getArtikelid(),dosKadsform.getGoedkeuring_d(), dosKadsform.getLijst_id());
			} catch (SQLException e) {				
				
			}		
		if (!errors.isEmpty()) {
			saveErrors(request,errors);			
			return mapping.findForward("error");
		}
		if (!dosKadOld.isEmpty()) {			
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("warn.update.dossierkadaster"));	
			saveErrors(request, errors);			
			((Art46BaseForm) request.getSession().getAttribute("dossierkadastersform")).setDossierkadasters((String[]) dosKadOld.toArray(new String[0]));			
			return mapping.findForward("warn");	
		}
		dosKadsform.setDossierkadasters(new String[0]);			
		return mapping.findForward("success");	 		
	}

}
