package be.ovam.art46.struts.actionform;

import org.apache.struts.validator.ValidatorForm;

public abstract class SelectForm extends ValidatorForm {

	private String forward;

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}	
}
