package be.ovam.art46.model;

import static be.ovam.pad.util.RoundUtils.roundBy2;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by koencorstjens on 9-7-13.
 */
public class AanvraagVastlegging {

    private Integer id;
    private Integer bestekid;
    private String bestek_nr;
    private String type_b;
    private String procedure_b;
    private String project_id;
    private String initieel_acht_nr;
    private String project_b;
    private String budgetair_artikel_b;
    private String contactpersoon;
    private Double initieel_bedrag;
    private Double verbruik;
    private String commentaar;
    private String raamcontract_s;
    private String verlenging_s;
    private String omschrijving;
    private String fase_b;
    private String commentaarweigering;
    private String geweigerd;
    private Long aanvraagid;
    private Integer planningsitem;
    private Integer budgetairartikel;
    private String kostenplaats;
    private Date inspectievanfinancien;
    private Date voogdijminister;
    private Date ministervanbegroting;
    private Date vlaamseregering;
    private Double vast_bedrag;
    private Integer gunningsverslag;
    private Integer gunningsbeslissing;
    private Integer overeenkomst;
    private Integer opdrachthouder_id;
    
    private String gunningsverslagDms;
    private String gunningsbeslissingDms;
    private String overeenkomstDms;
    private String uid;
    private String watcher;
    
    private String schuldeiserNaam;
    private String schuldeiserGemeente;
    private String schuldeiserPostcode;
    private String schuldeiserAdres;
    private String wbsBestek;
    
    private List<Spreiding> spreiding = new ArrayList<Spreiding>();
    private List<OptioneelBestand> optineleBestanden;

    public String getBestek_nr() {
        return bestek_nr;
    }

    public void setBestek_nr(String bestek_nr) {
        this.bestek_nr = bestek_nr;
    }

    public String getType_b() {
        return type_b;
    }

    public void setType_b(String type_b) {
        this.type_b = type_b;
    }

    public String getProcedure_b() {
        return procedure_b;
    }

    public void setProcedure_b(String procedure_b) {
        this.procedure_b = procedure_b;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getInitieel_acht_nr() {
        return initieel_acht_nr;
    }

    public void setInitieel_acht_nr(String initieel_acht_nr) {
        this.initieel_acht_nr = initieel_acht_nr;
    }

    public String getProject_b() {
        return project_b;
    }

    public void setProject_b(String project_b) {
        this.project_b = project_b;
    }

    public String getBudgetair_artikel_b() {
        return budgetair_artikel_b;
    }

    public void setBudgetair_artikel_b(String budgetair_artikel_b) {
        this.budgetair_artikel_b = budgetair_artikel_b;
    }

    public String getContactpersoon() {
        return contactpersoon;
    }

    public void setContactpersoon(String contactpersoon) {
        this.contactpersoon = contactpersoon;
    }

    public Double getInitieel_bedrag() {
        return initieel_bedrag;
    }

    public void setInitieel_bedrag(Double initieel_bedrag) {
        this.initieel_bedrag = roundBy2(initieel_bedrag);
    }

    public void setVerbruik(Double verbruik) {
        this.verbruik = roundBy2(verbruik);
    }

    public Double getVerbruik() {
        return verbruik;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public List<OptioneelBestand> getOptineleBestanden() {
        return optineleBestanden;
    }

    public void setOptineleBestanden(List<OptioneelBestand> optineleBestanden) {
        this.optineleBestanden = optineleBestanden;
    }

    public List<Spreiding> getSpreiding() {
        return spreiding;
    }

    public void setSpreiding(List<Spreiding> spreiding) {
        this.spreiding = spreiding;
    }

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGunningsverslagDms() {
        return gunningsverslagDms;
    }

    public void setGunningsverslagDms(String gunningsverslagDms) {
        this.gunningsverslagDms = gunningsverslagDms;
    }

    public String getGunningsbeslissingDms() {
        return gunningsbeslissingDms;
    }

    public void setGunningsbeslissingDms(String gunningsbeslissingDms) {
        this.gunningsbeslissingDms = gunningsbeslissingDms;
    }

    public Integer getOvereenkomst() {
        return overeenkomst;
    }

    public void setOvereenkomst(Integer overeenkomst) {
        this.overeenkomst = overeenkomst;
    }

    public String getOvereenkomstDms() {
        return overeenkomstDms;
    }

    public void setOvereenkomstDms(String overeenkomstDms) {
        this.overeenkomstDms = overeenkomstDms;
    }

    public Integer getOpdrachthouder_id() {
        return opdrachthouder_id;
    }

    public void setOpdrachthouder_id(Integer opdrachthouder_id) {
        this.opdrachthouder_id = opdrachthouder_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBestekid() {
        return bestekid;
    }

    public void setBestekid(Integer bestekid) {
        this.bestekid = bestekid;
    }

    public Long getAanvraagid() {
        return aanvraagid;
    }

    public void setAanvraagid(Long aanvraagid) {
        this.aanvraagid = aanvraagid;
    }

    public Integer getPlanningsitem() {
        return planningsitem;
    }

    public void setPlanningsitem(Integer planningsitem) {
        this.planningsitem = planningsitem;
    }

    public Integer getBudgetairartikel() {
        return budgetairartikel;
    }

    public void setBudgetairartikel(Integer budgetairartikel) {
        this.budgetairartikel = budgetairartikel;
    }

    public String getKostenplaats() {
        return kostenplaats;
    }

    public void setKostenplaats(String kostenplaats) {
        this.kostenplaats = kostenplaats;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getVoogdijministerRest() {
        return voogdijminister;
    }

    public Date getVoogdijminister() {
        return voogdijminister;
    }

    public void setVoogdijminister(Date voogdijminister) {
        this.voogdijminister = voogdijminister;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getInspectievanfinancienRest() {
        return inspectievanfinancien;
    }

    public Date getInspectievanfinancien() {
        return inspectievanfinancien;
    }

    public void setInspectievanfinancien(Date inspectievanfinancien) {
        this.inspectievanfinancien = inspectievanfinancien;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getMinistervanbegrotingRest() {
        return ministervanbegroting;
    }

    public Date getMinistervanbegroting() {
        return ministervanbegroting;
    }

    public void setMinistervanbegroting(Date ministervanbegroting) {
        this.ministervanbegroting = ministervanbegroting;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getVlaamseregeringRest() {
        return vlaamseregering;
    }

    public Date getVlaamseregering() {
        return vlaamseregering;
    }

    public void setVlaamseregering(Date vlaamseregering) {
        this.vlaamseregering = vlaamseregering;
    }

    public Double getVast_bedrag() {
        return vast_bedrag;
    }

    public void setVast_bedrag(Double vast_bedrag) {
        this.vast_bedrag = roundBy2(vast_bedrag);
    }

    public Integer getGunningsverslag() {
        return gunningsverslag;
    }

    public void setGunningsverslag(Integer gunningsverslag) {
        this.gunningsverslag = gunningsverslag;
    }

    public Integer getGunningsbeslissing() {
        return gunningsbeslissing;
    }

    public void setGunningsbeslissing(Integer gunningsbeslissing) {
        this.gunningsbeslissing = gunningsbeslissing;
    }


    public String getSchuldeiserNaam() {
        return schuldeiserNaam;
    }

    public void setSchuldeiserNaam(String schuldeiserNaam) {
        this.schuldeiserNaam = schuldeiserNaam;
    }

    public String getSchuldeiserPostcode() {
        return schuldeiserPostcode;
    }

    public void setSchuldeiserPostcode(String schuldeiserPostcode) {
        this.schuldeiserPostcode = schuldeiserPostcode;
    }

    public String getSchuldeiserGemeente() {
        return schuldeiserGemeente;
    }

    public void setSchuldeiserGemeente(String schuldeiserGemeente) {
        this.schuldeiserGemeente = schuldeiserGemeente;
    }

    public String getWbsBestek() {
        return wbsBestek;
    }

    public void setWbsBestek(String wbsBestek) {
        this.wbsBestek = wbsBestek;
    }

    public String getSchuldeiserAdres() {
        return schuldeiserAdres;
    }

    public void setSchuldeiserAdres(String schuldeiserAdres) {
        this.schuldeiserAdres = schuldeiserAdres;
    }

    public String getRaamcontract_s() {
        return raamcontract_s;
    }

    public void setRaamcontract_s(String raamcontract_s) {
        this.raamcontract_s = raamcontract_s;
    }

    public String getVerlenging_s() {
        return verlenging_s;
    }

    public void setVerlenging_s(String verlenging_s) {
        this.verlenging_s = verlenging_s;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getFase_b() {
        return fase_b;
    }

    public void setFase_b(String fase_b) {
        this.fase_b = fase_b;
    }

    public String getStatus() {
        if (StringUtils.isNotEmpty(geweigerd)) {
            return "Geweigerd";
        }
        if (this.project_id != null) {
            return "Goedgekeurd";
        }
        if (this.getAanvraagid() != null && this.getAanvraagid() != 0) {
            return "In aanvraag";
        }
        return "In opmaak";
    }

    public String getCommentaarweigering() {
        return commentaarweigering;
    }

    public void setCommentaarweigering(String commentaarweigering) {
        this.commentaarweigering = commentaarweigering;
    }

    public String getGeweigerd() {
        return geweigerd;
    }

    public void setGeweigerd(String geweigerd) {
        this.geweigerd = geweigerd;
    }

    public String getWatcher() {
        return watcher;
    }

    public void setWatcher(String watcher) {
        this.watcher = watcher;
    }


}
