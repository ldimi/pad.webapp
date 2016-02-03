package be.ovam.art46.model.rest;

import be.ovam.art46.util.OvamCalenderUtils;
import be.ovam.pad.model.SchuldVorderingSapProjectId;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Koen on 1/07/2014.
 */
public  class SchuldvorderingBudget {

    private Long id;
    private Long padId;
    private String padNr;
    private BigDecimal bedrag;
    private String wbs;
    private Calendar datum;
    private Calendar betaalDatum;
    private String dossierhouder;
    private String nodeRef;
    private String initieelAchtNummer;
    private Long sapProjectId;
    private Calendar vanDatum;
    private Calendar totDatum;
    private Boolean inactief = Boolean.FALSE;
    private String factuurNummer;
    private String inzender;
    
    
    public void setVordering_d(Date datum) {
        setDatum(OvamCalenderUtils.dateToCalendar(datum));
    }

    public void setUiterste_d(Date betaalDatum) {
        setBetaalDatum(OvamCalenderUtils.dateToCalendar(betaalDatum));
    }

    public void setTot_d(Date totDatum) {
        setTotDatum(OvamCalenderUtils.dateToCalendar(totDatum));
    }

    public void setVan_d(Date vanDatum) {
        setVanDatum(OvamCalenderUtils.dateToCalendar(vanDatum));
    }


        
        
        
        
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPadId() {
        return padId;
    }

    public void setPadId(Long padId) {
        this.padId = padId;
    }

    public String getPadNr() {
        return padNr;
    }

    public void setPadNr(String padNr) {
        this.padNr = padNr;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public Calendar getDatum() {
        return datum;
    }

    public void setDatum(Calendar datum) {
        this.datum = datum;
    }

    public Calendar getBetaalDatum() {
        return betaalDatum;
    }

    public void setBetaalDatum(Calendar betaalDatum) {
        this.betaalDatum = betaalDatum;
    }

    public String getDossierhouder() {
        return dossierhouder;
    }

    public void setDossierhouder(String dossierhouder) {
        this.dossierhouder = dossierhouder;
    }

    public String getNodeRef() {
        return nodeRef;
    }

    public void setNodeRef(String nodeRef) {
        this.nodeRef = nodeRef;
    }

    public void setInitieelAchtNummer(String initieelAchtNummer) {
        this.initieelAchtNummer = initieelAchtNummer;
    }

    public String getInitieelAchtNummer() {
        return initieelAchtNummer;
    }


    public void setSapProjectId(Long sapProjectId) {
        this.sapProjectId = sapProjectId;
    }

    public Long getSapProjectId() {
        return sapProjectId;
    }

    public void setVanDatum(Calendar vanDatum) {
        this.vanDatum = vanDatum;
    }

    public Calendar getVanDatum() {
        return vanDatum;
    }

    public void setTotDatum(Calendar totDatum) {
        this.totDatum = totDatum;
    }

    public Calendar getTotDatum() {
        return totDatum;
    }

    public void setInactief(Boolean inactief) {
        this.inactief = inactief;
    }

    public Boolean getInactief() {
        return inactief;
    }

    public String getFactuurNummer() {
        return factuurNummer;
    }

    public void setFactuurNummer(String factuurNummer) {
        this.factuurNummer = factuurNummer;
    }

    public void setInzender(String inzender) {
        this.inzender = inzender;
    }

    public String getInzender() {
        return inzender;
    }
}
