package be.ovam.art46.struts.tiles.controller;

import be.ovam.art46.decorator.BigDecimalDecorator;
import be.ovam.art46.model.ActieType;
import be.ovam.art46.service.ActieService;
import be.ovam.art46.struts.actionform.ActieForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class ActieDetailsController implements Controller {

	private ActieService service = (ActieService) LoadPlugin.applicationContext.getBean("actieService");	
	
	public void perform(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {		
		if (request.getAttribute("actieform") != null) {
			ActieForm form = (ActieForm) request.getAttribute("actieform");
			request.setAttribute("types", service.getActieTypes(form.getDossier_type()));			
			if ( "0".equals(form.getActie_id()) &&
				 form.getActie_type_id() != null &&
				 !"0".equals(form.getActie_type_id()) &&
				 form.getActie_type_id().length() != 0 &&
				 (form.getRate() == null || form.getRate().length() == 0) ) {
				
				ActieType type = service.getActieType(Integer.valueOf(form.getActie_type_id()));
				form.setRate(BigDecimalDecorator.format(type.getRate()));				
			}
			if (!"0".equals(form.getActie_type_id()) &&
				form.getActie_type_id() != null &&
				form.getActie_type_id().length() != 0) {
				
				request.setAttribute("subtypes", service.getActieSubTypes(Integer.valueOf(form.getActie_type_id())));
			}			
		}
	}
	
	public void execute(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		perform(context, request, response, servletContext);	
	}
}
