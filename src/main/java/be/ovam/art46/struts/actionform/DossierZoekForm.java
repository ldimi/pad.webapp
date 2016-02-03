package be.ovam.art46.struts.actionform;

import org.apache.struts.validator.ValidatorForm;

public class DossierZoekForm extends ValidatorForm {

    private String dossier_id = null;
    private String dossier_id_boa = null;
    private String dossier_id_jd = null;

    private String doss_hdr_id = null;
    private String doss_hdr_id_boa = null;
    private String doss_hdr_id_jd = null;

    private String dossier_b;
    private String sap_project_nr;

    private String ivs_s = null;

    private String dossier_type = null;
    
    private String nis_id = null;
    private String provincie = null;
    private String adres;

	private String bestek_nr;

    private String project_id;
    

    private String art46_lijst_id = null;
    private String historiek_lijst_id = null;

    private String programma_code = null;
    private String rechtsgrond_code = null;
    private String dossier_fase_id = null;
    private String doelgroep_type_id = null;

	private String raamcontract_J_N;
    
    private String incl_afgesloten_s = null;

    private String ig_s = null;
    private String ob_s = null;

    private String aanpak_s;
    private String aanpak_onderzocht_s;

    public String getIg_s() {
        return ig_s;
    }

    public void setIg_s(String ig_s) {
        this.ig_s = ig_s;
    }

    public String getOb_s() {
        return ob_s;
    }

    public void setOb_s(String ob_s) {
        this.ob_s = ob_s;
    }

    public String getArt46_lijst_id() {
        return art46_lijst_id;
    }

    public void setArt46_lijst_id(String art46_lijst_id) {
        this.art46_lijst_id = art46_lijst_id;
    }

    public String getDoss_hdr_id() {
        return doss_hdr_id;
    }

    public void setDoss_hdr_id(String doss_hdr_id) {
        this.doss_hdr_id = doss_hdr_id;
    }

    public String getDoss_hdr_id_boa() {
        return doss_hdr_id_boa;
    }

    public void setDoss_hdr_id_boa(String doss_hdr_id_boa) {
        this.doss_hdr_id_boa = doss_hdr_id_boa;
    }

    public String getDoss_hdr_id_jd() {
        return doss_hdr_id_jd;
    }

    public void setDoss_hdr_id_jd(String doss_hdr_id_jd) {
        this.doss_hdr_id_jd = doss_hdr_id_jd;
    }

    /**
     * @return Returns the dossier_id_jd.
     */
    public String getDossier_id_jd() {
        return dossier_id_jd;
    }

    /**
     * @param dossier_id_jd The dossier_id_jd to set.
     */
    public void setDossier_id_jd(String dossier_id_jd) {
        this.dossier_id_jd = dossier_id_jd;
    }

    /**
     * @return Returns the ivs_s.
     */
    public String getIvs_s() {
        return ivs_s;
    }

    /**
     * @param ivs_s The ivs_s to set.
     */
    public void setIvs_s(String ivs_s) {
        this.ivs_s = ivs_s;
    }

    /**
     * @return Returns the nis_id.
     */
    public String getNis_id() {
        return nis_id;
    }

    /**
     * @param nis_id The nis_id to set.
     */
    public void setNis_id(String nis_id) {
        this.nis_id = nis_id;
    }

    public String getProvincie() {
        return provincie;
    }

    public void setProvincie(String provincie) {// public ActionForward execute(ActionMapping mapping, ActionForm form,
//      HttpServletRequest request, HttpServletResponse response,
//      ActionErrors errors) throws Exception {
//  request.getSession().setAttribute("dossierzoekform", new be.ovam.art46.struts.actionform.DossierZoekForm());
//  return mapping.findForward("success");
//}

        this.provincie = provincie;
    }

    public String getHistoriek_lijst_id() {
        return historiek_lijst_id;
    }

    public void setHistoriek_lijst_id(String historiek_lijst_id) {
        this.historiek_lijst_id = historiek_lijst_id;
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public String getIncl_afgesloten_s() {
        return incl_afgesloten_s;
    }

    public void setIncl_afgesloten_s(String incl_afgesloten_s) {
        this.incl_afgesloten_s = incl_afgesloten_s;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
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

    public String getDossier_id_boa() {
        return dossier_id_boa;
    }

    public void setDossier_id_boa(String dossier_id_boa) {
        this.dossier_id_boa = dossier_id_boa;
    }

    public String getAanpak_onderzocht_s() {
        return aanpak_onderzocht_s;
    }

    public void setAanpak_onderzocht_s(String aanpak_onderzocht_s) {
        this.aanpak_onderzocht_s = aanpak_onderzocht_s;
    }

    public String getAanpak_s() {
        return aanpak_s;
    }

    public void setAanpak_s(String aanpak_s) {
        this.aanpak_s = aanpak_s;
    }

    public String getSap_project_nr() {
        return sap_project_nr;
    }

    public void setSap_project_nr(String sap_project_nr) {
        this.sap_project_nr = sap_project_nr;
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

    public String getDossier_fase_id() {
        return dossier_fase_id;
    }

    public void setDossier_fase_id(String dossier_fase_id) {
        this.dossier_fase_id = dossier_fase_id;
    }

    public String getDoelgroep_type_id() {
        return doelgroep_type_id;
    }

    public void setDoelgroep_type_id(String doelgroep_type_id) {
        this.doelgroep_type_id = doelgroep_type_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getRaamcontract_J_N() {
        return raamcontract_J_N;
    }

    public void setRaamcontract_J_N(String raamcontract_J_N) {
        this.raamcontract_J_N = raamcontract_J_N;
    }

    public String getBestek_nr() {
        return bestek_nr;
    }

    public void setBestek_nr(String bestek_nr) {
        this.bestek_nr = bestek_nr;
    }

}
