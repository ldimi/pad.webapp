package be.ovam.art46.struts.action;

import be.ovam.art46.service.BaseService;
import be.ovam.art46.service.dossier.DossierJDService;
import be.ovam.art46.struts.action.base.CrudAction;
import be.ovam.art46.struts.plugin.LoadPlugin;

public class DossierJDCrudAction extends CrudAction {

	private DossierJDService dossierJDService = (DossierJDService) LoadPlugin.applicationContext.getBean("dossierJDService");
	
	public BaseService getService() {
		return dossierJDService;
	}
	
}
