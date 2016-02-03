package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;

public class DossierBOAForm extends ActionForm {

	private String id;
	private String smeg_id;
	private String smeg_naam;
	
	private String dossier_id_boa;
	private String commentaar_bodem;
	
	
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getSmeg_id() {
		return smeg_id;
	}
	public void setSmeg_id(String smeg_id) {
		this.smeg_id = smeg_id;
	}
	public String getSmeg_naam() {
		return smeg_naam;
	}
	public void setSmeg_naam(String smeg_naam) {
		this.smeg_naam = smeg_naam;
	}
	public String getDossier_id_boa() {
		return dossier_id_boa;
	}
	public void setDossier_id_boa(String dossier_id_boa) {
		this.dossier_id_boa = dossier_id_boa;
	}
	public String getCommentaar_bodem() {
		return commentaar_bodem;
	}
	public void setCommentaar_bodem(String commentaar_bodem) {
		this.commentaar_bodem = commentaar_bodem;
	}


}
