package be.ovam.art46.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class Art46RequestWrapper extends HttpServletRequestWrapper {

	private HttpServletRequest request;
	private String userRoles = null;
		
	public Art46RequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}


	public boolean isUserInRole(String role) {/*
		if (userRoles == null) {
			// ingeval van josso sso
			return super.isUserInRole(role);
		} else {
			return userRoles.contains(role);
		}
	*/
		boolean antwoord =  super.isUserInRole(role);
		return antwoord; 
		
	/*	SecurityContextHolderAwareRequestWrapper.isUserInRole( role);*/
		
		
	
	}
	
	

	
}
