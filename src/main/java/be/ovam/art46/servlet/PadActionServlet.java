package be.ovam.art46.servlet;

import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.struts.action.ActionServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PadActionServlet extends ActionServlet {

	private static final long serialVersionUID = -7716628102637165333L;
	

	protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (request.getParameter("selectedSub") != null) {
			request.getSession().setAttribute("selectedSub", request.getParameter("selectedSub"));
		}
		if (request.getSession().getAttribute("selectedSub") == null) {
			request.getSession().setAttribute("selectedSub", "0");
		}
		if (LoadPlugin.url == null) {
			String url = request.getRequestURL().toString();
			LoadPlugin.url = url.substring(0, url.indexOf(request.getContextPath())) + request.getContextPath();
		}
		
		
		super.process(request, response);
	}
}
