package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;

public class ArchiefZoekForm extends ActionForm {
		
	private String dossier_id="-1";
	private String dienst_id="-1";
	private String doss_hdr_id;
	
	public String getDienst_id() {
		return dienst_id;
	}
	public void setDienst_id(String dienst_id) {
		this.dienst_id = dienst_id;
	}
	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}
	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}
	public String getDossier_id() {
		return dossier_id;
	}
	public void setDossier_id(String dossier_id) {
		this.dossier_id = dossier_id;
	}
		
	
}
