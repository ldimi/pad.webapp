package be.ovam.art46.struts.action;

import be.ovam.art46.dao.DossierKadasterDAO;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.DossierKadasterForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action om de publicatie_d van records in RS.ART46_NA_GOEDK te updaten via de mehode {@link be.ovam.art46.dao.DossierKadasterDAO#updateDossierKadasterNG updateDossierKadasterNG()}. 
 * Nadat de publicatiedatum is aangepast worden de dossierkadasters op de lijst voor opname geplaats.  Dossierkadasters van Art2 worden klaargezet voor opname Art3.1.
 * 
 * @author Tonny Bruckers
 * @version 0.1 (2004/04/24)
 */

public class SetPublicatieDatumAction extends Action {
	
	private DossierKadasterDAO dao = (DossierKadasterDAO) LoadPlugin.applicationContext.getBean("dossierKadasterDAO");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		DossierKadasterForm dosKadsform = (DossierKadasterForm) form;	
		dao.updateDossierKadasterNG(dosKadsform.getDossierkadasters(),dosKadsform.getPublicatie_d(), dosKadsform.getArtikelid(),dosKadsform.getLijst_id());
		return null;
	}

}
