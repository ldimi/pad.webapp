package be.ovam.art46.struts.tiles.controller;

import be.ovam.art46.service.ActieService;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class ActieTypeLijstController implements Controller {

	private ActieService service = (ActieService) LoadPlugin.applicationContext.getBean("actieService");	
	
	public void perform(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {		
		request.setAttribute("actietypes", service.getAllActieTypes());	
	}
	
	public void execute(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		perform(context, request, response, servletContext);	
	}
}
