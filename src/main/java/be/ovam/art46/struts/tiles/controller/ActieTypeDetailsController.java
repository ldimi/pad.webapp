package be.ovam.art46.struts.tiles.controller;

import be.ovam.art46.service.ActieService;
import be.ovam.art46.struts.actionform.ActieTypeForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class ActieTypeDetailsController implements Controller {

	private ActieService service = (ActieService) LoadPlugin.applicationContext.getBean("actieService");	
	
	public void perform(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {		
		if (request.getAttribute("actietypeform") != null) {
			ActieTypeForm form = (ActieTypeForm) request.getAttribute("actietypeform");
			request.setAttribute("subtypes", service.getActieSubTypes(Integer.valueOf(form.getActie_type_id())));
		}
	}
	
	public void execute(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		perform(context, request, response, servletContext);	
	}
}
