package be.ovam.art46.struts.actionform;

import org.apache.struts.validator.ValidatorForm;

public class BriefDossierForm extends ValidatorForm {
	
	private String dossier_type_ivs = "B";

	public String getDossier_type_ivs() {
		return dossier_type_ivs;
	}

	public void setDossier_type_ivs(String dossier_type_ivs) {
		this.dossier_type_ivs = dossier_type_ivs;
	}
	
	

}
