package be.ovam.art46.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Immutable
@Table(name = "V_JURIDISCHDOSSIER", schema = "ART46")
public class JuridischDossier {


    @Column(name = "ID", insertable = false, updatable = false)
    @Id
    private Long id;

    @Column(name = "NUMMER", insertable = false, updatable = false)
    private String nummer;

    @Column(name = "NAAM", insertable = false, updatable = false)
    private String naam;

    @Column(name = "CREATIEDATUM", insertable = false, updatable = false)
    private String creatiedatum;

    @Column(name = "DOSSIERHOUDER", insertable = false, updatable = false)
    private String dossierhouder;

    @Column(name = "ART46_DOSSIER_ID", insertable = false, updatable = false)
    private Integer art46DossierId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getCreatiedatum() {
        return creatiedatum;
    }

    public void setCreatiedatum(String creatiedatum) {
        this.creatiedatum = creatiedatum;
    }

    public String getDossierhouder() {
        return dossierhouder;
    }

    public void setDossierhouder(String dossierhouder) {
        this.dossierhouder = dossierhouder;
    }

    public Integer getArt46DossierId() {
        return art46DossierId;
    }

    public void setArt46DossierId(Integer art46DossierId) {
        this.art46DossierId = art46DossierId;
    }


}
