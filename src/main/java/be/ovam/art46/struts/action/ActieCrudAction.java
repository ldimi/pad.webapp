package be.ovam.art46.struts.action;

import be.ovam.art46.service.ActieService;
import be.ovam.art46.struts.action.base.CrudAction;
import be.ovam.art46.struts.plugin.LoadPlugin;

public class ActieCrudAction extends CrudAction {
	
	private ActieService actieService = (ActieService) LoadPlugin.applicationContext.getBean("actieService");
	
	public ActieService getService() {
		return actieService;
	}
}
