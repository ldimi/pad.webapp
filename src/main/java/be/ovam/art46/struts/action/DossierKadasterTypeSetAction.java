package be.ovam.art46.struts.action;

import be.ovam.art46.dao.DossierKadasterDAO;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.Art46BaseForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Action om records in RS.ART46_DOS_KAD_TYPE toe te voegen en te verwijderen. 
 * Afhankelijk of de parameter <code>dossier_id</code> of <code>kadaster_id</code> voorkomt in de request worden respectivelijk de methode {@link be.ovam.art46.dao.DossierKadasterDAO#setDossierKadasterTypesetDossierKadasterType setDossierKadasterType()} of {@link be.ovam.art46.dao.DossierKadasterDAO#setKadasterDossierType setKadasterDossierType()} aangeroepen.
 * 
 * 
 * @author Tonny Bruckers
 * @version 0.1 (2004/04/24)
 */

public class DossierKadasterTypeSetAction extends Action {
	
	
	private DossierKadasterDAO dao = (DossierKadasterDAO) LoadPlugin.applicationContext.getBean("dossierKadasterDAO");

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			ActionErrors errors) throws Exception {
		Art46BaseForm dosKadsform = (Art46BaseForm) form;			
		try {					
			if (request.getParameter("dossier_id") != null) {
				dao.setDossierKadasterType(dosKadsform.getDossier_id(), request.getParameterValues("onschuldige_eig_s"), request.getParameterValues("ingebreke_stel_s") );
			}
			if (request.getParameter("kadaster_id") != null) {
				dao.setKadasterDossierType(dosKadsform.getKadaster_id(), request.getParameterValues("onschuldige_eig_s"), request.getParameterValues("ingebreke_stel_s") );
			}					
		} catch (SQLException e) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.update.dossierkadaster",e));		
		}
		if (dosKadsform.getNextpage() != null && dosKadsform.getNextpage().length() > 2) {
			return mapping.findForward(dosKadsform.getNextpage());					
		}	
		return null;
	}
	
}
