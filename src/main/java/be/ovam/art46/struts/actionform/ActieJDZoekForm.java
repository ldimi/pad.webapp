package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;

public class ActieJDZoekForm extends ActionForm {

	private String doss_hdr_id;
	private String jaar_actie_d = "-1";
	private String jaar_dossier_d = "-1";
	private String actie_type_id;
	
	public String getActie_type_id() {
		return actie_type_id;
	}
	public void setActie_type_id(String actie_id) {
		this.actie_type_id = actie_id;
	}
	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}
	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}
	public String getJaar_actie_d() {
		return jaar_actie_d;
	}
	public void setJaar_actie_d(String jaar_actie_d) {
		this.jaar_actie_d = jaar_actie_d;
	}
	public String getJaar_dossier_d() {
		return jaar_dossier_d;
	}
	public void setJaar_dossier_d(String jaar_dossier_d) {
		this.jaar_dossier_d = jaar_dossier_d;
	}	
		
}
