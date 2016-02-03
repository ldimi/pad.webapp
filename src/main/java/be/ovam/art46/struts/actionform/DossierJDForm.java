package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.DossierJD;
import be.ovam.art46.struts.actionform.base.CrudActionForm;

import java.io.Serializable;



public class DossierJDForm extends CrudActionForm {
	
	private String dossier_id;
	private String dossier_id_jd;
	private String doss_hdr_id;
	private String dossier_b;
	private String commentaar;
	private String stand_terugvordering;	
	
	public void clear() {}
	
	public Class getObjectClass() {
		return DossierJD.class;
	}

	public Serializable getCrudId() {
		return dossier_id_jd;
	}

	public String getCommentaar() {
		return commentaar;
	}

	public void setCommentaar(String commentaar) {
		this.commentaar = commentaar;
	}

	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}

	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}

	public String getDossier_b() {
		return dossier_b;
	}

	public void setDossier_b(String dossier_b) {
		this.dossier_b = dossier_b;
	}

	public String getDossier_id() {
		return dossier_id;
	}

	public void setDossier_id(String dossier_id) {
		this.dossier_id = dossier_id;
	}

	public String getDossier_id_jd() {
		return dossier_id_jd;
	}

	public void setDossier_id_jd(String dossier_id_jd) {
		this.dossier_id_jd = dossier_id_jd;
	}

	public String getStand_terugvordering() {
		return stand_terugvordering;
	}

	public void setStand_terugvordering(String stand_terugvordering) {
		this.stand_terugvordering = stand_terugvordering;
	}
}
