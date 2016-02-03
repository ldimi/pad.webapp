package be.ovam.art46.util;

import be.ovam.art46.model.User;

import javax.servlet.http.HttpServletRequest;


public enum Application {
	INSTANCE;

	private final ThreadLocal<User> threadLocalUser = new ThreadLocal<User>();
	private final ThreadLocal<HttpServletRequest> threadLocalRequest = new ThreadLocal<HttpServletRequest>();
	
	public void setUser(User user) {
		threadLocalUser.set(user);
	}
	
	public void setRequest(HttpServletRequest request) {
		threadLocalRequest.set(request);
	}

	public User getUser() {
		return threadLocalUser.get();
	}
	
	public boolean isUserInRole(String role) {
		
		if (getUser()!=null) {
			 return threadLocalRequest.get().isUserInRole(role.trim());
		} else {
			
			return false; 

		}
		
		
	}
	
	public String getUser_id () {
		User user = getUser();
		if (user == null) {
			return null;
		} else {
			return user.getUser_id();
		}
	}

}