package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;

public class BestekRamingForm extends ActionForm {
	
	private Long bestek_id;
	private String raming_id;
	
	public Long getBestek_id() {
		return bestek_id;
	}
	public void setBestek_id(Long bestek_id) {
		this.bestek_id = bestek_id;
	}
	public String getRaming_id() {
		return raming_id;
	}
	public void setRaming_id(String raming_id) {
		this.raming_id = raming_id;
	}

}
