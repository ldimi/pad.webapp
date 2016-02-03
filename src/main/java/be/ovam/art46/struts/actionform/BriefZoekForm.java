package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;

public class BriefZoekForm extends ValidatorForm {

	private String adres_naam;
	private String bestek_nr;	
	
	private Integer dossier_id;
	private String dossier_nr;
	private String dossier_id_boa;
	private String dossier_hdr_ivs;
	private String dossier_b_ivs;
	private String dossier_fase_id;
	private String dossier_gemeente;
	
	private String brief_nr;
	private String in_referte;
	private String betreft;
	private String inschrijf_d_van;
	private String inschrijf_d_tot;
	private String in_d_van;
	private String in_d_tot;
	private String uit_d_van;
	private String uit_d_tot;
	private String auteur_id;
    private String qr_code;
    private String in_aard_id;
    private String uit_aard_id;
		
	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}
	public String getAdres_naam() {
		return adres_naam;
	}
	public void setAdres_naam(String adres_naam) {
		this.adres_naam = adres_naam;
	}
	public String getBetreft() {
		return betreft;
	}
	public void setBetreft(String betreft) {
		this.betreft = betreft;
	}
	public String getBrief_nr() {
		return brief_nr;
	}
	public void setBrief_nr(String brief_nr) {
		this.brief_nr = brief_nr;
	}
	public String getDossier_b_ivs() {
		return dossier_b_ivs;
	}
	public void setDossier_b_ivs(String dossier_b_ivs) {
		this.dossier_b_ivs = dossier_b_ivs;
	}
	public String getDossier_fase_id() {
		return dossier_fase_id;
	}
	public void setDossier_fase_id(String dossier_fase_id) {
		this.dossier_fase_id = dossier_fase_id;
	}
	public String getDossier_hdr_ivs() {
		return dossier_hdr_ivs;
	}
	public void setDossier_hdr_ivs(String dossier_hdr_ivs) {
		this.dossier_hdr_ivs = dossier_hdr_ivs;
	}
	public String getDossier_nr() {
		return dossier_nr;
	}
	public void setDossier_nr(String dossier_nr) {
		this.dossier_nr = dossier_nr;
	}
	public String getIn_d_tot() {
		return in_d_tot;
	}
	public void setIn_d_tot(String in_d_tot) {
		this.in_d_tot = in_d_tot;
	}
	public String getIn_d_van() {
		return in_d_van;
	}
	public void setIn_d_van(String in_d_van) {
		this.in_d_van = in_d_van;
	}
	public String getIn_referte() {
		return in_referte;
	}
	public void setIn_referte(String in_referte) {
		this.in_referte = in_referte;
	}
	public String getInschrijf_d_tot() {
		return inschrijf_d_tot;
	}
	public void setInschrijf_d_tot(String inschrijf_d_tot) {
		this.inschrijf_d_tot = inschrijf_d_tot;
	}
	public String getInschrijf_d_van() {
		return inschrijf_d_van;
	}
	public void setInschrijf_d_van(String inschrijf_d_van) {
		this.inschrijf_d_van = inschrijf_d_van;
	}
	public String getUit_d_tot() {
		return uit_d_tot;
	}
	public void setUit_d_tot(String uit_d_tot) {
		this.uit_d_tot = uit_d_tot;
	}
	public String getUit_d_van() {
		return uit_d_van;
	}
	public void setUit_d_van(String uit_d_van) {
		this.uit_d_van = uit_d_van;
	}
	public String getDossier_gemeente() {
		return dossier_gemeente;
	}
	public void setDossier_gemeente(String dossier_gemeente) {
		this.dossier_gemeente = dossier_gemeente;
	}
	public String getBestek_nr() {
		return bestek_nr;
	}
	public void setBestek_nr(String bestek_nr) {
		this.bestek_nr = bestek_nr;
	}
	public String getAuteur_id() {
		return auteur_id;
	}
	public void setAuteur_id(String auteur_id) {
		this.auteur_id = auteur_id;
	}
	public Integer getDossier_id() {
		return dossier_id;
	}
	public void setDossier_id(Integer dossier_id) {
		this.dossier_id = dossier_id;
	}	
	public String getDossier_id_boa() {
		return dossier_id_boa;
	}
	public void setDossier_id_boa(String dossier_id_boa) {
		this.dossier_id_boa = dossier_id_boa;
	}
    public String getQr_code() {
        return qr_code;
    }
    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
    public String getIn_aard_id() {
        return in_aard_id;
    }
    public void setIn_aard_id(String in_aard_id) {
        this.in_aard_id = in_aard_id;
    }
    public String getUit_aard_id() {
        return uit_aard_id;
    }
    public void setUit_aard_id(String uit_aard_id) {
        this.uit_aard_id = uit_aard_id;
    }
	
}
