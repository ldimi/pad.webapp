package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.ActieType;
import be.ovam.art46.struts.actionform.base.CrudActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class ActieTypeForm extends CrudActionForm {
	
	private String actie_type_id;
	private String actie_type_b;
	private String dossier_type;
	private String ingebreke_s;
	private String rate;
	
	public void clear() {}
	
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		ingebreke_s = "0";
	}
	public String getActie_type_b() {
		return actie_type_b;
	}
	public void setActie_type_b(String actie_type_b) {
		this.actie_type_b = actie_type_b;
	}
	public String getActie_type_id() {
		return actie_type_id;
	}
	public void setActie_type_id(String actie_type_id) {
		this.actie_type_id = actie_type_id;
	}
	public String getDossier_type() {
		return dossier_type;
	}
	public void setDossier_type(String dossier_type) {
		this.dossier_type = dossier_type;
	}
	public String getIngebreke_s() {
		return ingebreke_s;
	}
	public void setIngebreke_s(String ingebreke_s) {
		this.ingebreke_s = ingebreke_s;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public Class getObjectClass() {
		return ActieType.class;
	}
	public Serializable getCrudId() {
		return Integer.valueOf(actie_type_id);
	}

	
}
