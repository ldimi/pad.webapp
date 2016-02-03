package be.ovam.art46.struts.action;

import be.ovam.art46.dao.AdresDAO;
import be.ovam.art46.dao.BriefDAO;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.BriefForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.pad.model.Adres;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BriefLoadAdresAction extends Action {
	
	private AdresDAO adresDAO = (AdresDAO) LoadPlugin.applicationContext.getBean("adresDAO");
	private BriefDAO briefDAO = (BriefDAO) LoadPlugin.applicationContext.getBean("briefDAO");

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		BriefForm briefForm = (BriefForm) form;	
		if (briefForm.getAdres_id() != null && briefForm.getAdres_id().length() != 0) {
			Adres adres = adresDAO.get(Integer.valueOf(briefForm.getAdres_id()));
			briefForm.setAdres_naam(adres.getNaam());
			briefForm.setAdres_voornaam(adres.getVoornaam());
			briefForm.setAdres_gemeente(adres.getGemeente());
			briefForm.setAdres_straat(adres.getStraat());
			briefForm.setContactList(briefDAO.getContacts(Integer.valueOf(briefForm.getAdres_id())));						
		}
		else {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.brief.adres_id"));
		}		
		return null;
	}
}
