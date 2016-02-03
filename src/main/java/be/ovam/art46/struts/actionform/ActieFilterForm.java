package be.ovam.art46.struts.actionform;

import org.apache.struts.validator.ValidatorForm;

public class ActieFilterForm extends ValidatorForm {
	
	private String doss_hdr_id;

	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}

	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}
}
