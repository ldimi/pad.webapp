package be.ovam.art46.dto;

import java.util.Date;

/**
 * Created by Koen on 22/01/2015.
 */
public class BriefDto {
    private Date inschrijfDatum;
    private Date uitDatum;
    private String aartUit;
    private String briefNummer;
    private Integer id;

    public void setInschrijfDatum(Date inschrijfDatum) {
        this.inschrijfDatum = inschrijfDatum;
    }

    public Date getInschrijfDatum() {
        return inschrijfDatum;
    }

    public void setUitDatum(Date uitDatum) {
        this.uitDatum = uitDatum;
    }

    public Date getUitDatum() {
        return uitDatum;
    }

    public void setAartUit(String aartUit) {
        this.aartUit = aartUit;
    }

    public String getAartUit() {
        return aartUit;
    }

    public void setBriefNummer(String briefNummer) {
        this.briefNummer = briefNummer;
    }

    public String getBriefNummer() {
        return briefNummer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
