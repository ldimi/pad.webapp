package be.ovam.art46.model;

import java.math.BigDecimal;
import java.util.Date;

public class VastleggingOrdonanceringLijstDO {

    private Integer id;
    private String dossier_nr;
    private String dossier_b;
    private String gemeente_b;
    private String dossier_type;
    private Integer bestek_id;
    private String bestek_nr;
    private Date datum;
    private Date afsluit_d;
    private String project_id;
    private String vastlegging_id;
    private BigDecimal vekVoorzien;
    private BigDecimal gefactureerd;

    private String doss_hdr_id;
    private String programma;

    private BigDecimal begrotingVEK;

    private Date SPREIDING_VALIDATIE_TS;
    private String SPREIDING_VALIDATIE_UID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDossier_nr() {
        return dossier_nr;
    }

    public void setDossier_nr(String dossier_nr) {
        this.dossier_nr = dossier_nr;
    }

    public String getDossier_b() {
        return dossier_b;
    }

    public void setDossier_b(String dossier_b) {
        this.dossier_b = dossier_b;
    }

    public String getGemeente_b() {
        return gemeente_b;
    }

    public void setGemeente_b(String gemeente_b) {
        this.gemeente_b = gemeente_b;
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public Integer getBestek_id() {
        return bestek_id;
    }

    public void setBestek_id(Integer bestek_id) {
        this.bestek_id = bestek_id;
    }

    public String getBestek_nr() {
        return bestek_nr;
    }

    public void setBestek_nr(String bestek_nr) {
        this.bestek_nr = bestek_nr;
    }


    public Date getAfsluit_d() {
        return afsluit_d;
    }

    public void setAfsluit_d(Date afsluit_d) {
        this.afsluit_d = afsluit_d;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }


    public String getVastlegging_id() {
        return vastlegging_id;
    }

    public void setVastlegging_id(String vastlegging_id) {
        this.vastlegging_id = vastlegging_id;
    }

    public BigDecimal getVekVoorzien() {
        return vekVoorzien;
    }

    public void setVekVoorzien(BigDecimal vekVoorzien) {
        this.vekVoorzien = vekVoorzien;
    }

    public BigDecimal getGefactureerd() {
        return gefactureerd;
    }

    public void setGefactureerd(BigDecimal gefactureerd) {
        this.gefactureerd = gefactureerd;
    }

    public Date getSPREIDING_VALIDATIE_TS() {
        return SPREIDING_VALIDATIE_TS;
    }

    public void setSPREIDING_VALIDATIE_TS(Date sPREIDING_VALIDATIE_TS) {
        SPREIDING_VALIDATIE_TS = sPREIDING_VALIDATIE_TS;
    }

    public String getSPREIDING_VALIDATIE_UID() {
        return SPREIDING_VALIDATIE_UID;
    }

    public void setSPREIDING_VALIDATIE_UID(String sPREIDING_VALIDATIE_UID) {
        SPREIDING_VALIDATIE_UID = sPREIDING_VALIDATIE_UID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public BigDecimal getBegrotingVEK() {
        return begrotingVEK;
    }

    public void setBegrotingVEK(BigDecimal begrotingVEK) {
        this.begrotingVEK = begrotingVEK;
    }

    public String getDoss_hdr_id() {
        return doss_hdr_id;
    }

    public void setDoss_hdr_id(String doss_hdr_id) {
        this.doss_hdr_id = doss_hdr_id;
    }

    public String getProgramma() {
        return programma;
    }

    public void setProgramma(String programma) {
        this.programma = programma;
    }
}
