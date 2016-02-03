package be.ovam.art46.model;

import java.util.Date;


public class BriefOpmerking {
    
    private Integer opmerking_id;
    private Integer brief_id;
    private String opmerking;
    private String auteur;
    private Date creatie_d;
    private Date behandeld_d;
    private String verwijderd_jn;
    
    private Date wijzig_ts;
    private String wijzig_user;
    
    
    public Integer getOpmerking_id() {
        return opmerking_id;
    }
    public void setOpmerking_id(Integer opmerking_id) {
        this.opmerking_id = opmerking_id;
    }
    public Integer getBrief_id() {
        return brief_id;
    }
    public void setBrief_id(Integer brief_id) {
        this.brief_id = brief_id;
    }
    public String getOpmerking() {
        return opmerking;
    }
    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }
    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    public Date getCreatie_d() {
        return creatie_d;
    }
    public void setCreatie_d(Date creatie_d) {
        this.creatie_d = creatie_d;
    }
    public Date getBehandeld_d() {
        return behandeld_d;
    }
    public void setBehandeld_d(Date behandeld_d) {
        this.behandeld_d = behandeld_d;
    }
    public String getVerwijderd_jn() {
        return verwijderd_jn;
    }
    public void setVerwijderd_jn(String verwijderd_jn) {
        this.verwijderd_jn = verwijderd_jn;
    }
    public Date getWijzig_ts() {
        return wijzig_ts;
    }
    public void setWijzig_ts(Date wijzig_ts) {
        this.wijzig_ts = wijzig_ts;
    }
    public String getWijzig_user() {
        return wijzig_user;
    }
    public void setWijzig_user(String wijzig_user) {
        this.wijzig_user = wijzig_user;
    }

    
    
}
