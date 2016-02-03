package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.Archief;
import be.ovam.art46.struts.actionform.base.CrudActionForm;

import java.io.Serializable;

public class ArchiefForm extends CrudActionForm {
		
	private String archief_id;
	private String archief_nr;	
	private String dossier_id;
	private String afdeling = "IVS";
	private String dienst_id;
	private String doss_hdr_id;
	private String archief_d;
	private String archief_b;
	private String plaats;
	
	public void clear() {}
	
	public String getAfdeling() {
		return afdeling;
	}
	public void setAfdeling(String afdeling) {
		this.afdeling = afdeling;
	}
	public String getArchief_b() {
		return archief_b;
	}
	public void setArchief_b(String archief_b) {
		this.archief_b = archief_b;
	}
	public String getArchief_d() {
		return archief_d;
	}
	public void setArchief_d(String archief_d) {
		this.archief_d = archief_d;
	}
	public String getArchief_id() {
		return archief_id;
	}
	public void setArchief_id(String archief_id) {
		this.archief_id = archief_id;
	}
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
	public String getPlaats() {
		return plaats;
	}
	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}
	public String getArchief_nr() {
		return archief_nr;
	}
	public void setArchief_nr(String archief_nr) {
		this.archief_nr = archief_nr;
	}
	public Class getObjectClass() {
		return Archief.class;
	}
	public Serializable getCrudId() {
		return Integer.valueOf(archief_id);
	}
	
	
	
}
