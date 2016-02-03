package be.ovam.art46.controller.dossier;


import java.io.Serializable;
import java.util.Date;

public class DossierDO implements Serializable{

	private static final long serialVersionUID = 50460355623282696L;
	
	private Integer id;
	private String dossier_id;
	private Integer dossier_id_boa;
    private String smeg_naam;
	private Integer smeg_id;
	private String doss_hdr_id;
	private String dossier_b;
	private String dossier_b_l;
	private String dossier_type;
	private String adres;
	private String nis_id;
	private String deelgemeente;
	private String postcode;
	private String land;
	private Date afsluit_d;
	private String commentaar;
    
	private Integer dossier_fase_id;

    private String aanpak_s;
    private String aanpak_onderzocht_s;
	private String aanpak_onderzocht_l;
	private String financiele_info;
	private String onderzoek_id;

	private Date conform_bbo_d;
	private Date conform_bsp_d;
	private Date eindverklaring_d;
	private String commentaar_bodem;
	private String sap_project_nr;
	private String wbs_ivs_nr;
	private String programma_code;
    private String rechtsgrond_code;
    private Integer doelgroep_type_id;
	    
    public String getDossier_nr() {
        return dossier_id;
    }

    public void setDossier_nr(String dossier_id) {
        this.dossier_id = dossier_id;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(String dossier_id) {
        this.dossier_id = dossier_id;
    }

    public Integer getDossier_id_boa() {
        return dossier_id_boa;
    }

    public void setDossier_id_boa(Integer dossier_id_boa) {
        this.dossier_id_boa = dossier_id_boa;
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

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getNis_id() {
        return nis_id;
    }

    public void setNis_id(String nis_id) {
        this.nis_id = nis_id;
    }

    public String getDeelgemeente() {
        return deelgemeente;
    }

    public void setDeelgemeente(String deelgemeente) {
        this.deelgemeente = deelgemeente;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public Date getAfsluit_d() {
        return afsluit_d;
    }

    public void setAfsluit_d(Date afsluit_d) {
        this.afsluit_d = afsluit_d;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public Integer getDossier_fase_id() {
        return dossier_fase_id;
    }

    public void setDossier_fase_id(Integer dossier_fase_id) {
        this.dossier_fase_id = dossier_fase_id;
    }

    public String getAanpak_onderzocht_s() {
        return aanpak_onderzocht_s;
    }

    public void setAanpak_onderzocht_s(String aanpak_onderzocht_s) {
        this.aanpak_onderzocht_s = aanpak_onderzocht_s;
    }

    public String getAanpak_onderzocht_l() {
        return aanpak_onderzocht_l;
    }

    public void setAanpak_onderzocht_l(String aanpak_onderzocht_l) {
        this.aanpak_onderzocht_l = aanpak_onderzocht_l;
    }

    public String getFinanciele_info() {
        return financiele_info;
    }

    public void setFinanciele_info(String financiele_info) {
        this.financiele_info = financiele_info;
    }

    public String getOnderzoek_id() {
        return onderzoek_id;
    }

    public void setOnderzoek_id(String onderzoek_id) {
        this.onderzoek_id = onderzoek_id;
    }

    public Date getConform_bbo_d() {
        return conform_bbo_d;
    }

    public void setConform_bbo_d(Date conform_bbo_d) {
        this.conform_bbo_d = conform_bbo_d;
    }

    public Date getConform_bsp_d() {
        return conform_bsp_d;
    }

    public void setConform_bsp_d(Date conform_bsp_d) {
        this.conform_bsp_d = conform_bsp_d;
    }

    public Date getEindverklaring_d() {
        return eindverklaring_d;
    }

    public void setEindverklaring_d(Date eindverklaring_d) {
        this.eindverklaring_d = eindverklaring_d;
    }

    public String getCommentaar_bodem() {
        return commentaar_bodem;
    }

    public void setCommentaar_bodem(String commentaar_bodem) {
        this.commentaar_bodem = commentaar_bodem;
    }

    public String getSap_project_nr() {
        return sap_project_nr;
    }

    public void setSap_project_nr(String sap_project_nr) {
        this.sap_project_nr = sap_project_nr;
    }

    public String getWbs_ivs_nr() {
        return wbs_ivs_nr;
    }

    public void setWbs_ivs_nr(String wbs_ivs_nr) {
        this.wbs_ivs_nr = wbs_ivs_nr;
    }

    public String getProgramma_code() {
        return programma_code;
    }

    public void setProgramma_code(String programma_code) {
        this.programma_code = programma_code;
    }

    public String getRechtsgrond_code() {
        return rechtsgrond_code;
    }

    public void setRechtsgrond_code(String rechtsgrond_code) {
        this.rechtsgrond_code = rechtsgrond_code;
    }

    public Integer getDoelgroep_type_id() {
        return doelgroep_type_id;
    }

    public void setDoelgroep_type_id(Integer doelgroep_type_id) {
        this.doelgroep_type_id = doelgroep_type_id;
    }

    public String getAanpak_s() {
        return aanpak_s;
    }

    public void setAanpak_s(String aanpak_s) {
        this.aanpak_s = aanpak_s;
    }

    public String getSmeg_naam() {
        return smeg_naam;
    }

    public void setSmeg_naam(String smeg_naam) {
        this.smeg_naam = smeg_naam;
    }

    public Integer getSmeg_id() {
        return smeg_id;
    }

    public void setSmeg_id(Integer smeg_id) {
        this.smeg_id = smeg_id;
    }

    public String getDossier_b_l() {
        return dossier_b_l;
    }

    public void setDossier_b_l(String dossier_b_l) {
        this.dossier_b_l = dossier_b_l;
    }

}
