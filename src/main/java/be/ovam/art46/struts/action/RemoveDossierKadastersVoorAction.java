
package be.ovam.art46.struts.action;

import be.ovam.art46.dao.DossierKadasterDAO;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action om records uit de tabel RS.ART46_VOOR_GOEDK te verwijderen.  Records worden verwijdert via de methode {@link be.ovam.art46.dao.DossierKadasterDAO#deleteDossierKadasterVG deleteDossierKadasterVG()}. 
 * 
 * @author Tonny Bruckers
 * @version 0.1 (2004/04/24)
 */

public class RemoveDossierKadastersVoorAction extends Action {

	private DossierKadasterDAO dao = (DossierKadasterDAO) LoadPlugin.applicationContext.getBean("dossierKadasterDAO");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		dao.deleteDossierKadasterVG(request.getParameterValues("dossierkadasters"));
		return null;
	}

}
