package be.ovam.art46.struts.action;

import be.ovam.art46.dao.DossierKadasterDAO;
import be.ovam.art46.struts.actionform.Art46BaseForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * Action om records uit de tabel RS.ART46_VOOR_GOEDK te verwijderen en toe te voegen.  Records worden verwijdert via de methode {@link be.ovam.art46.dao.DossierKadasterDAO#deleteDossierKadasterVG deleteDossierKadasterVG()}, 
 * nadien worden eventuele nieuwe records toegevoegd via de {@link be.ovam.art46.dao.DossierKadasterDAO#insertDossierKadasterVG insertDossierKadasterVG()} methode.
 * 
 * @author Tonny Bruckers
 * @version 0.1 (2004/04/24)
 */
public class AddRemoveDossierKadastersVoorAction extends Action {

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
				dao.deleteDossierKadasterVG(request.getParameterValues("dossierkadasters"));
				if (!"0".equals(dosKadsform.getArtikelid())) {
					List<String> doskadNietConform = dao.insertDossierKadasterVG(request.getParameterValues("dossierkadasters"), dosKadsform.getArtikelid()); 
				    if (!doskadNietConform.isEmpty()) {					    
					    for (int t=0; t<doskadNietConform.size(); t++) {
					        errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("warn.doskad.niet.conform", (String) doskadNietConform.get(t)));
					    }				    
					}
				}								
			} catch (SQLException e) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.update.dossierkadaster",e));							
			}
		if (!errors.isEmpty()) {
			saveErrors(request,errors);			
			return mapping.findForward(dosKadsform.getNexterrorpage());
		}							
		return mapping.findForward(dosKadsform.getNextpage());	 		
	}

}
