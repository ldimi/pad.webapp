package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.AdresType;
import be.ovam.art46.struts.actionform.base.CrudActionForm;

import java.io.Serializable;

public class AdresTypeForm extends CrudActionForm {

	private String adrestype_id;
	private String adrestype_b;
	
	public void clear() {}
	
	public String getAdrestype_b() {
		return adrestype_b;
	}
	public void setAdrestype_b(String adrestype_b) {
		this.adrestype_b = adrestype_b;
	}
	public String getAdrestype_id() {
		return adrestype_id;
	}
	public void setAdrestype_id(String adrestype_id) {
		this.adrestype_id = adrestype_id;
	}
	public Class getObjectClass() {
		return AdresType.class;
	}
	public Serializable getCrudId() {
		return Integer.valueOf(adrestype_id);
	}	
}
