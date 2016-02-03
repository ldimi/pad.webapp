package be.ovam.art46.model;

import java.util.Date;

public class DossierAfspraak {

    private Integer id;
    private Integer dossier_id;
    private Date datum = new Date();
    private String omschrijving;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(Integer dossierId) {
        dossier_id = dossierId;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

}
