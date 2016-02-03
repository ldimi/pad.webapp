package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.ActieSubType;
import be.ovam.art46.struts.actionform.base.CrudActionForm;

import java.io.Serializable;

public class ActieSubTypeForm extends CrudActionForm {
	
	private String actie_sub_type_id;
	private String actie_type_id;
	private String actie_sub_type_b;
	
	public void clear() {}
	
	public String getActie_sub_type_b() {
		return actie_sub_type_b;
	}
	public void setActie_sub_type_b(String actie_sub_type_b) {
		this.actie_sub_type_b = actie_sub_type_b;
	}
	public String getActie_sub_type_id() {
		return actie_sub_type_id;
	}
	public void setActie_sub_type_id(String actie_sub_type_id) {
		this.actie_sub_type_id = actie_sub_type_id;
	}
	public String getActie_type_id() {
		return actie_type_id;
	}
	public void setActie_type_id(String actie_type_id) {
		this.actie_type_id = actie_type_id;
	}
	public Serializable getCrudId() {
		return Integer.valueOf(actie_sub_type_id);
	}
	public Class getObjectClass() {
		return ActieSubType.class;
	}
	
	
}
