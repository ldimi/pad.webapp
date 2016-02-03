package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.Query;
import be.ovam.art46.struts.actionform.base.CrudActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class QueryForm extends CrudActionForm {

	private String query_id;
	private String query_b;
	private String query_l;
	
	public void clear() {}
	
	public Class getObjectClass() {
		return Query.class;
	}
	public Serializable getCrudId() {
		return Integer.valueOf(query_id);
	}
	public String getQuery_b() {
		return query_b;
	}
	public void setQuery_b(String query_b) {
		this.query_b = query_b;
	}
	public String getQuery_id() {
		return query_id;
	}
	public void setQuery_id(String query_id) {
		this.query_id = query_id;
	}
	public String getQuery_l() {
		return query_l;
	}
	public void setQuery_l(String query_l) {
		this.query_l = query_l;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if ("save".equals(crudAction)) {
			if (query_l.indexOf("create") != -1  || query_l.indexOf("drop") != -1 || query_l.indexOf("delete") != -1 || query_l.indexOf("update") != -1 || query_l.indexOf("update") != -1) {
				if (errors == null) {
					errors = new ActionErrors();
				}
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.db.invalid.action"));
			}
		}
		return errors;
	}
	
}
