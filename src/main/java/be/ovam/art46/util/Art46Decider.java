package be.ovam.art46.util;

import com.free2be.dimensions.decider.AbstractDeciderImpl;
import com.free2be.dimensions.decider.Decision;

import javax.servlet.http.HttpServletRequest;

public class Art46Decider extends AbstractDeciderImpl {
	
	private String parameterName;	
		
	public Decision decide(HttpServletRequest request) {
		Decision descision = new Decision();		
		if (request.getParameter(parameterName) != null && "yes".equals(request.getParameter(parameterName))) {
			descision.setParameter("popup", "yes"); 
		}		
		return descision;
	}

	public String getParameterName() {
		return parameterName;
	}
	
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	

}
