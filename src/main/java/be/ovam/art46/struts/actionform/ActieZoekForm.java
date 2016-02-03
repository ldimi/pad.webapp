package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class ActieZoekForm extends ActionForm {

	private static final long serialVersionUID = -2557826133523841451L;
	
	private String doss_hdr_id;
	private String jaar_actie_d = "-1";
	private String jaar_realisatie_d = "-1";
	private String actie_type_id;
	private String dossier_fase_id = "-1";
	private String ingebrekestelling_s;
	private String ander_s;
	private String lijst_id = "-1";
	private String dossier_type;
	private String dossier_id = null;
	private String actie_sub_type;
	private String actie_sub_type_id;
	private String programma_code;
		
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		ingebrekestelling_s = null;		
		ander_s = null;
		actie_sub_type = null;
	}

	public String getActie_type_id() {
		return actie_type_id;
	}

	public void setActie_type_id(String actie_id) {
		this.actie_type_id = actie_id;
	}

	public String getAnder_s() {
		return ander_s;
	}

	public void setAnder_s(String ander_s) {
		this.ander_s = ander_s;
	}	

	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}

	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}

	public String getDossier_fase_id() {
		return dossier_fase_id;
	}

	public void setDossier_fase_id(String dossier_fase_id) {
		this.dossier_fase_id = dossier_fase_id;
	}

	public String getDossier_type() {
		return dossier_type;
	}

	public void setDossier_type(String dossier_type) {
		this.dossier_type = dossier_type;
	}

	public String getIngebrekestelling_s() {
		return ingebrekestelling_s;
	}

	public void setIngebrekestelling_s(String ingebrekestelling_s) {
		this.ingebrekestelling_s = ingebrekestelling_s;
	}

	public String getJaar_actie_d() {
		return jaar_actie_d;
	}

	public void setJaar_actie_d(String jaar_actie_d) {
		this.jaar_actie_d = jaar_actie_d;
	}

	public String getJaar_realisatie_d() {
		return jaar_realisatie_d;
	}

	public void setJaar_realisatie_d(String jaar_realisatie_d) {
		this.jaar_realisatie_d = jaar_realisatie_d;
	}

	public String getLijst_id() {
		return lijst_id;
	}

	public void setLijst_id(String lijst_id) {
		this.lijst_id = lijst_id;
	}

	public String getDossier_id() {
		return dossier_id;
	}

	public void setDossier_id(String dossier_id) {
		this.dossier_id = dossier_id;
	}

	public String getActie_sub_type() {
		return actie_sub_type;
	}

	public void setActie_sub_type(String actie_sub_type) {
		this.actie_sub_type = actie_sub_type;
	}

	public String getActie_sub_type_id() {
		return actie_sub_type_id;
	}

	public void setActie_sub_type_id(String actie_sub_type_id) {
		this.actie_sub_type_id = actie_sub_type_id;
	}

	public String getProgramma_code() {
		return programma_code;
	}

	public void setProgramma_code(String programma_code) {
		this.programma_code = programma_code;
	}	
	
	
	
	
	
}
