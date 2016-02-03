package be.ovam.art46.struts.actionform;

import be.ovam.art46.util.DateFormatArt46;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DeelopdrachtLijstForm extends ActionForm {
	
	private Map goedkeuring_d = new HashMap();
	
	public Object getGoedkeuring_d(String key) {
		return (String) goedkeuring_d.get(key);		
	}
	


	public void setGoedkeuring_d(String key, Object value) {
		this.goedkeuring_d.put(key, value);
	}


	public Map getGoedkeuring_ds() {
		return this.goedkeuring_d;
	}

	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		String deelopdracht_id = null;
	 	Iterator iter = goedkeuring_d.keySet().iterator();
	 	while (iter.hasNext()) {
	 		deelopdracht_id = (String)iter.next();
		 	if (!DateFormatArt46.isDate((String) goedkeuring_d.get(deelopdracht_id))) {
		 		errors.add("goedkeuring_d(" + deelopdracht_id + ")", new ActionMessage("error.date.format", "Datum goedkeuring"));
		 	}	
	 	}
		return errors;
	}
	
	
	
}
