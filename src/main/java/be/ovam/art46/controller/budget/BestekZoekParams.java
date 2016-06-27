package be.ovam.art46.controller.budget;

import java.io.Serializable;

public class BestekZoekParams implements Serializable {

    private String bestek_nr;
    private String bestek_hdr_id;
    private String omschrijving;
    private String wbs_nr;
    private Integer fase_id;
    private String dossier_nr;
    private String dossier_type;
    private String doss_hdr_id;
    private String dossier_b;
    private String programma_code;
    private String raamcontract_jn;
    private String incl_afgesloten_s;

    public String getBestek_nr() {
        return bestek_nr;
    }

    public void setBestek_nr(String bestek_nr) {
        this.bestek_nr = bestek_nr;
    }

    public String getBestek_hdr_id() {
        return bestek_hdr_id;
    }

    public void setBestek_hdr_id(String bestek_hdr_id) {
        this.bestek_hdr_id = bestek_hdr_id;
    }

    public String getWbs_nr() {
        return wbs_nr;
    }

    public void setWbs_nr(String wbs_nr) {
        this.wbs_nr = wbs_nr;
    }

    public Integer getFase_id() {
        return fase_id;
    }

    public void setFase_id(Integer fase_id) {
        this.fase_id = fase_id;
    }

    public String getDossier_nr() {
        return dossier_nr;
    }

    public void setDossier_nr(String dossier_nr) {
        this.dossier_nr = dossier_nr;
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

    public String getProgramma_code() {
        return programma_code;
    }

    public void setProgramma_code(String programma_code) {
        this.programma_code = programma_code;
    }

    public String getRaamcontract_jn() {
        return raamcontract_jn;
    }

    public void setRaamcontract_jn(String raamcontract_jn) {
        this.raamcontract_jn = raamcontract_jn;
    }

    public String getIncl_afgesloten_s() {
        return incl_afgesloten_s;
    }

    public void setIncl_afgesloten_s(String incl_afgesloten_s) {
        this.incl_afgesloten_s = incl_afgesloten_s;
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    
    
}
