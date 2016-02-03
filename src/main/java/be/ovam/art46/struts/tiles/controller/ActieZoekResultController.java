package be.ovam.art46.struts.tiles.controller;

import be.ovam.art46.service.dossier.DossierService;
import be.ovam.art46.struts.actionform.ActieZoekForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class ActieZoekResultController implements Controller {

	private DossierService service = (DossierService) LoadPlugin.applicationContext.getBean("dossierService");	
	
	public void perform(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {		
		if (request.getSession().getAttribute("actiezoekform") != null) {
			ActieZoekForm form = (ActieZoekForm) request.getSession().getAttribute("actiezoekform");
			if (form.getDoss_hdr_id() != null && form.getDoss_hdr_id().length()>0) {
				request.setAttribute("dossiers", service.getIvsDossiers(form.getDoss_hdr_id()));
			}
		}
	}
	
	public void execute(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		perform(context, request, response, servletContext);	
	}
}
