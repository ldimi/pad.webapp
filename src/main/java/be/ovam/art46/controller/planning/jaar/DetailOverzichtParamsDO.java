package be.ovam.art46.controller.planning.jaar;

import java.io.Serializable;
import java.util.Date;


public class DetailOverzichtParamsDO implements Serializable {
	
	
	private static final long serialVersionUID = -9160368941935359927L;

	private String dossier_type;
	private String doss_hdr_id;
	private String programma_code;
	private String budget_code;
	private String fase_code;
	private String deelopdracht_jn;
	private Date gepland_van_d;
	private Date gepland_tot_d;
	private Date benut_van_d;
	private Date benut_tot_d;
	
	
	public void setDossier_type(String dossier_type) {
		this.dossier_type = dossier_type;
	}
	public String getDossier_type() {
		return dossier_type;
	}
	
	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}
	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}
		
	public String getProgramma_code() {
		return programma_code;
	}
	public void setProgramma_code(String programma_code) {
		this.programma_code = programma_code;
	}
	
	public void setBudget_code(String budget_code) {
		this.budget_code = budget_code;
	}
	public String getBudget_code() {
		return budget_code;
	}
	
	public String getFase_code() {
		return fase_code;
	}
	public void setFase_code(String fase_code) {
		this.fase_code = fase_code;
	}
	
	public void setDeelopdracht_jn(String deelopdracht_jn) {
		this.deelopdracht_jn = deelopdracht_jn;
	}
	public String getDeelopdracht_jn() {
		return deelopdracht_jn;
	}
	
	public void setGepland_van_d(Date gepland_van_d) {
		this.gepland_van_d = gepland_van_d;
	}
	public Date getGepland_van_d() {
		return gepland_van_d;
	}
	
	public void setGepland_tot_d(Date gepland_tot_d) {
		this.gepland_tot_d = gepland_tot_d;
	}
	public Date getGepland_tot_d() {
		return gepland_tot_d;
	}
	
	public Date getBenut_van_d() {
		return benut_van_d;
	}
	public void setBenut_van_d(Date benut_van_d) {
		this.benut_van_d = benut_van_d;
	}
	
	public Date getBenut_tot_d() {
		return benut_tot_d;
	}
	public void setBenut_tot_d(Date benut_tot_d) {
		this.benut_tot_d = benut_tot_d;
	}
	
    @Override
    public String toString() {
        return "DetailOverzichtParamsDO [dossier_type=" + dossier_type + ", doss_hdr_id=" + doss_hdr_id + ", programma_code=" + programma_code
                + ", budget_code=" + budget_code + ", fase_code=" + fase_code + ", deelopdracht_jn=" + deelopdracht_jn + ", gepland_van_d="
                + gepland_van_d + ", gepland_tot_d=" + gepland_tot_d + ", benut_van_d=" + benut_van_d + ", benut_tot_d=" + benut_tot_d + "]";
    }

	
}
