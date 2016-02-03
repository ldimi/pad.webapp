package be.ovam.art46.model;

import be.ovam.art46.service.schuldvordering.SchuldvorderingExportService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Koen on 2/04/2014.
 */
public class SchuldvorderingExportData {
    private String bestekNr;
    private String status;
    private String vorderingNr;
    private String afgekeurd_jn;
    private String goedkeuringBedrag;
    private String postcode;
    private String gemeente;
    private String land;
    private String straat;
    private Long bestekId;
    private String fase;
    private String goedkeuringDatum;
    private String briefNr;
    private String dossierOmschrijving;
    private String projectGemeente;
    private String vorderingDatum;
    private String auteur;
    private String leveranciersReferentie;
    private String bestekOmschrijving;
    private String opdrachtnemersNaam;
    private String wbs;
    private List<SchuldvorderingSapProjectExportData> projecten;
    private String auteurTelefoonnummer;
    private String auteurEmail;
    private String uitesrsteBetalingsDatum;
    private String dienstType;
    private String opmerking;
    private String vanDatum;
    private String totDatum;
    private String boetebedrag;
    private String vorderingBedrag;
    private String handtekeningNaam;
    private String ondertekenaarNaam;
    private String ondertekenaarFunctie;
    private Boolean opTijd;
    private Boolean ondertekendDoorDiensthoofd;

    public void setBestekNr(String bestekNr) {
        this.bestekNr = clean(bestekNr);
    }

    public String getBestekNr() {
        return bestekNr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVorderingNr(String vorderingNr) {
        this.vorderingNr = clean(vorderingNr);
    }

    public String getVorderingNr() {
        return vorderingNr;
    }

    public void setAfgekeurd_jn(String afgekeurd_jn) {
        this.afgekeurd_jn = afgekeurd_jn;
    }

    public void setGoedkeuringBedrag(String goedkeuringBedrag) {
        this.goedkeuringBedrag = goedkeuringBedrag;
    }

    public String getGoedkeuringBedrag() {
        return goedkeuringBedrag;
    }

    public void setPostcode(String postcode) {
        this.postcode = clean(postcode);
    }

    public String getPostcode() {
        return postcode;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = clean(gemeente);
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setLand(String land) {
        this.land = clean(land);
    }

    public String getLand() {
        return land;
    }

    public void setStraat(String straat) {
        this.straat = clean(straat);
    }

    public String getStraat() {
        return straat;
    }

    public Long getBestekId() {
        return bestekId;
    }

    public void setBestekId(Long bestekId) {
        this.bestekId = bestekId;
    }

    public void setFase(String fase) {
        this.fase = clean(fase);
    }

    public String getFase() {
        return fase;
    }

    public void setGoedkeuringDatum(String goedkeuringDatum) {
        this.goedkeuringDatum = clean(goedkeuringDatum);
    }

    public String getGoedkeuringDatum() {
        return goedkeuringDatum;
    }

    public void setBriefNr(String briefNr) {
        this.briefNr = clean(briefNr);
    }

    public String getBriefNr(){
        if(StringUtils.isEmpty(briefNr)){
            return StringUtils.EMPTY;
        }
        return briefNr;
    }

    public void setProjectGemeente(String projectGemeente) {
        this.projectGemeente = clean(projectGemeente);
    }

    public String getProjectGemeente() {
        if(StringUtils.isEmpty(projectGemeente)){
            return StringUtils.EMPTY;
        }
        return projectGemeente;
    }

    public void setDossierOmschrijving(String dossierOmschrijving) {
        this.dossierOmschrijving = clean(dossierOmschrijving);
    }

    public String getDossierOmschrijving() {
        return dossierOmschrijving;
    }

    public void setVorderingDatum(String vorderingDatum) {
        this.vorderingDatum = clean(vorderingDatum);
    }

    public String getVorderingDatum() {
        return vorderingDatum;
    }

    public void setAuteur(String auteur) {
        this.auteur = clean(auteur);
    }

    public String getAuteur() {
        return clean(auteur);
    }

    public void setLeveranciersReferentie(String leveranciersReferentie) {
        this.leveranciersReferentie = clean(leveranciersReferentie);
    }

    public String getLeveranciersReferentie() {
        return leveranciersReferentie;
    }

    public void setBestekOmschrijving(String bestekOmschrijving) {
        this.bestekOmschrijving = clean(bestekOmschrijving);
    }

    public String getBestekOmschrijving() {
        return bestekOmschrijving;
    }

    public void setOpdrachtnemersNaam(String opdrachtnemersNaam) {
        this.opdrachtnemersNaam = clean(opdrachtnemersNaam);
    }

    public String getOpdrachtnemersNaam() {
        return opdrachtnemersNaam;
    }

    public void setWbs(String wbs) {
        this.wbs = clean(wbs);
    }

    public String getWbs() {
        return wbs;
    }

    public List<SchuldvorderingSapProjectExportData> getProjecten() {
        return projecten;
    }

    public void setProjecten(List<SchuldvorderingSapProjectExportData> projecten) {
        this.projecten = projecten;
    }

    public String getPostcodeEnGemeente(){
        return getPostcode() +" "+ getGemeente();
    }

    public void setAuteurTelefoonnummer(String auteurTelefoonnummer) {
        this.auteurTelefoonnummer = clean(auteurTelefoonnummer);
    }

    public String getAuteurTelefoonnummer() {
        return auteurTelefoonnummer;
    }

    public void setAuteurEmail(String auteurEmail) {
        this.auteurEmail = clean(auteurEmail);
    }

    public String getAuteurEmail() {
        return auteurEmail;
    }

    private static final String clean(String s){
        if(StringUtils.isEmpty(s)){
            return StringUtils.EMPTY;
        }
        return StringUtils.trim(s);
    }
    public String getContactpersoon(){
        return auteur +  " (" + auteurTelefoonnummer+ ", "+auteurEmail + ")";
    }

    public Integer getAantalProjecten(){
        if(CollectionUtils.isEmpty(projecten)){
            return 0;
        }
        return projecten.size();
    }

    public void setUitesrsteBetalingsDatum(String uitesrsteBetalingsDatum) {
        this.uitesrsteBetalingsDatum = clean(uitesrsteBetalingsDatum);
    }

    public String getUitesrsteBetalingsDatum() {
        return uitesrsteBetalingsDatum;
    }

    public void setDienstType(String dienstType) {
        this.dienstType = clean(dienstType);
    }

    public String getDienstType() {
        return dienstType;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = clean(opmerking);
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setVanDatum(String vanDatum) {
        this.vanDatum = clean(vanDatum);
    }

    public String getVanDatum() {
        return vanDatum;
    }

    public void setTotDatum(String totDatum) {
        this.totDatum = clean(totDatum);
    }

    public String getTotDatum() {
        return totDatum;
    }

    public void setBoetebedrag(String boetebedrag) {
        this.boetebedrag = boetebedrag;
    }

    public String getBoetebedrag() {
        return boetebedrag;
    }

    public void setVorderingBedrag(String vorderingBedrag) {
        this.vorderingBedrag = vorderingBedrag;
    }

    public String getVorderingBedrag() {
        return vorderingBedrag;
    }

    public String getHandtekeningNaam() {
        return handtekeningNaam;
    }

    public void setHandtekeningNaam(String handtekeningNaam) {
        this.handtekeningNaam = handtekeningNaam;
    }

    public void setOndertekenaarNaam(String ondertekenaarNaam) {
        this.ondertekenaarNaam = ondertekenaarNaam;
    }

    public String getOndertekenaarNaam() {
        if(isDraft()){
            return clean(ondertekenaarNaam).replaceAll("(\\S)", "?");
        }
        return clean(ondertekenaarNaam);
    }

    public void setOndertekenaarFunctie(String ondertekenaarFunctie) {
        this.ondertekenaarFunctie = ondertekenaarFunctie;
    }

    public String getOndertekenaarFunctie() {
        if(StringUtils.isEmpty(ondertekenaarFunctie)){
            return StringUtils.EMPTY;
        }
        if(isDraft()){
            return clean(ondertekenaarFunctie).replaceAll("(\\S)", "?");
        }
        ondertekenaarFunctie = clean(ondertekenaarFunctie);
        char first = Character.toUpperCase(ondertekenaarFunctie.charAt(0));
        ondertekenaarFunctie = first + ondertekenaarFunctie.substring(1);
        return ondertekenaarFunctie;
    }

    public void setOpTijd(Boolean opTijd) {
        this.opTijd = opTijd;
    }

    public Boolean getOpTijd() {
        return opTijd;
    }

    public Boolean isOpTijd() {
        return opTijd;
    }

    public void setOndertekendDoorDiensthoofd(Boolean ondertekendDoorDiensthoofd) {
        this.ondertekendDoorDiensthoofd = ondertekendDoorDiensthoofd;
    }

    public Boolean getOndertekendDoorDiensthoofd() {
        return ondertekendDoorDiensthoofd;
    }

    public boolean isGoedgekeurd(){
        return StringUtils.equals("N", StringUtils.upperCase(afgekeurd_jn));
    }
    
    public boolean isDraft() {
        return StringUtils.equals(SchuldvorderingExportService.DRAFT, handtekeningNaam);
    }
    

}
