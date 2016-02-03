package be.ovam.art46.struts.action;

import be.ovam.art46.dao.AdresDAO;
import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.actionform.AdresZoekForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdresZoekResultAction extends ZoekAction {
	
	private AdresDAO adresDAO = (AdresDAO) LoadPlugin.applicationContext.getBean("adresDAO");
	
	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AdresZoekForm adresZoekForm = (AdresZoekForm) form;
		adresZoekForm.setZoek_contact("N");
		return adresDAO.getAdresZoekResult((AdresZoekForm) form);
	}
}
