package be.ovam.art46.filter;

import be.ovam.art46.model.User;
import be.ovam.art46.service.UserService;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.art46.util.Application;
import org.apache.struts.Globals;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

public class AuthenticationFilter implements Filter {

	private UserService userService = null;

	private Locale locale = Locale.GERMAN;

	public void init(FilterConfig config) throws ServletException {		
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		

        HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		Art46RequestWrapper wrapper = new Art46RequestWrapper(httpRequest);	
		User user = (User) httpRequest.getSession(true).getAttribute("user");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (user == null && !(auth instanceof AnonymousAuthenticationToken)) {

            UserDetails principal = (UserDetails)auth.getPrincipal();
            request.setAttribute("username", principal.getUsername());

            if (userService == null) {
                userService = (UserService) LoadPlugin.applicationContext.getBean("userService");
            }
            user = userService.authenticateUser(principal.getUsername(), null);
            if (user == null) {
                throw new RuntimeException("User niet gekend: " + principal.getUsername());
            }

            // user wordt in session bewaard 
            httpRequest.getSession(true).setAttribute("user", user);
        }			

		// fill threadlocals
		Application.INSTANCE.setUser(user);
		Application.INSTANCE.setRequest(wrapper);
		
		httpRequest.getSession().setAttribute(Globals.LOCALE_KEY, locale);
		httpRequest.getSession().setAttribute("helpaction", httpRequest.getRequestURL().toString());
		
		chain.doFilter(wrapper, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
