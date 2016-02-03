package be.ovam.art46.dto;

import java.util.Date;

/**
 * Created by Koen on 4/02/2015.
 */
public class BriefViewDto {

    private Integer id;
    private String briefNummer;
    private String uitAartBeschrijving;
    private String uitTypeBeschrijvingVos;
    private String dossierNummer;
    private String dossierhouderId;
    private String gemeente;
    private String adresNaam;
    private String qrcode;
    private String laatsteWijzigingUser;
    private Date laatsteWijzigingDatum;
    private Date inschrijfDatum;
    private Date uitDatum;
    private String dmsFolder;
    private String dmsFileName;
    private String scanDmsFolder;
    private String scanDmsFileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBriefNummer() {
        return briefNummer;
    }

    public void setBriefNummer(String briefNummer) {
        this.briefNummer = briefNummer;
    }

    public String getUitAartBeschrijving() {
        return uitAartBeschrijving;
    }

    public void setUitAartBeschrijving(String uitAartBeschrijving) {
        this.uitAartBeschrijving = uitAartBeschrijving;
    }

    public String getUitTypeBeschrijvingVos() {
        return uitTypeBeschrijvingVos;
    }

    public void setUitTypeBeschrijvingVos(String uitTypeBeschrijvingVos) {
        this.uitTypeBeschrijvingVos = uitTypeBeschrijvingVos;
    }

    public String getDossierNummer() {
        return dossierNummer;
    }

    public void setDossierNummer(String dossierNummer) {
        this.dossierNummer = dossierNummer;
    }

    public String getDossierhouderId() {
        return dossierhouderId;
    }

    public void setDossierhouderId(String dossierhouderId) {
        this.dossierhouderId = dossierhouderId;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getAdresNaam() {
        return adresNaam;
    }

    public void setAdresNaam(String adresNaam) {
        this.adresNaam = adresNaam;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getLaatsteWijzigingUser() {
        return laatsteWijzigingUser;
    }

    public void setLaatsteWijzigingUser(String laatsteWijzigingUser) {
        this.laatsteWijzigingUser = laatsteWijzigingUser;
    }

    public Date getLaatsteWijzigingDatum() {
        return laatsteWijzigingDatum;
    }

    public void setLaatsteWijzigingDatum(Date laatsteWijzigingDatum) {
        this.laatsteWijzigingDatum = laatsteWijzigingDatum;
    }

    public Date getInschrijfDatum() {
        return inschrijfDatum;
    }

    public void setInschrijfDatum(Date inschrijfDatum) {
        this.inschrijfDatum = inschrijfDatum;
    }

    public Date getUitDatum() {
        return uitDatum;
    }

    public void setUitDatum(Date uitDatum) {
        this.uitDatum = uitDatum;
    }

    public String getDmsFolder() {
        return dmsFolder;
    }

    public void setDmsFolder(String dmsFolder) {
        this.dmsFolder = dmsFolder;
    }

    public String getDmsFileName() {
        return dmsFileName;
    }

    public void setDmsFileName(String dmsFileName) {
        this.dmsFileName = dmsFileName;
    }

    public void setScanDmsFolder(String scanDmsFolder) {
        this.scanDmsFolder = scanDmsFolder;
    }

    public String getScanDmsFolder() {
        return scanDmsFolder;
    }

    public void setScanDmsFileName(String scanDmsFileName) {
        this.scanDmsFileName = scanDmsFileName;
    }

    public String getScanDmsFileName() {
        return scanDmsFileName;
    }
}
