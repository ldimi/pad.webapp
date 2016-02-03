package be.ovam.art46.struts.actionform.schuldvordering;

import be.ovam.art46.util.DateFormatArt46;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SchuldvorderingLijstForm extends ActionForm {
	
	private Map betaal_d = new HashMap();
	
	public Object getBetaal_d(String key) {
		return (String) betaal_d.get(key);		
	}

	public void setBetaal_d(String key, Object value) {
		betaal_d.put(key, value);
	}

	public Map getBetaal_ds() {
		return betaal_d;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		String vordering_id = null;
	 	Iterator iter = betaal_d.keySet().iterator();
	 	while (iter.hasNext()) {
	 		vordering_id = (String)iter.next();
		 	if (!DateFormatArt46.isDate((String) betaal_d.get(vordering_id))) {
		 		errors.add("betaal_d(" + vordering_id + ")", new ActionMessage("error.date.format", "Datum betaling"));
		 	}	
	 	}
		return errors;
	}
	
	
	
}
