package be.ovam.art46.model.planning;

import java.io.Serializable;


public class ParamsDO implements Serializable {

    private static final long serialVersionUID = -4244544938688973656L;

    private Integer jaar;
    private String doss_hdr_id;

    // TODO : dossier_id naar Integer omzetten.
    private String dossier_id;
    private String dossier_type;
    private String benut_jn;


    public Integer getJaar() {
        return jaar;
    }

    public void setJaar(Integer jaar) {
        this.jaar = jaar;
    }

    public String getDoss_hdr_id() {
        return doss_hdr_id;
    }

    public void setDoss_hdr_id(String doss_hdr_id) {
        this.doss_hdr_id = doss_hdr_id;
    }

    public void setDossier_id(String dossier_id) {
        this.dossier_id = dossier_id;
    }

    public String getDossier_id() {
        return dossier_id;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setBenut_jn(String benut_jn) {
        this.benut_jn = benut_jn;
    }

    public String getBenut_jn() {
        return benut_jn;
    }

}
