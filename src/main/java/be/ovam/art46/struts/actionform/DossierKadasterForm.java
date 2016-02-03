/*
 * Created on Mar 29, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author torghal
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DossierKadasterForm extends Art46BaseForm {
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.reset(request.getSession());					
	}
    
	public void reset(HttpSession session) {
		this.session = session;					
		log.debug("Kadasterdossiers reset to: " + listKadasterdossier());					
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors =  super.validate(mapping, request);
		if (mapping.getPath().equals("/setgoedkeuringsdatum")) {
    		if (getGoedkeuring_d() == null || getGoedkeuring_d().length() == 0) {
    			errors.add("goedkeuring_d", new ActionMessage("error.required.field", "Datum goedkeuring"));     	
    		}    		
    	}
		if (mapping.getPath().equals("/setpublicatiedatum")) {
    		if (getPublicatie_d() == null || getPublicatie_d().length() == 0) {
    			errors.add("publicatie_d", new ActionMessage("error.required.field", "Datum publicatie"));     	
    		}    		
    	}
		return errors;
	}
	
	

}
