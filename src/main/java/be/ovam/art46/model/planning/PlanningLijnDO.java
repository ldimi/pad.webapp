package be.ovam.art46.model.planning;

import java.io.Serializable;
import java.util.Date;


public class PlanningLijnDO implements Serializable {

    private static final long serialVersionUID = 4262121174849067580L;

    private Integer lijn_id;
    private Integer planning_dossier_versie;
    private String doss_hdr_id;
    private Integer jaar;

    private Integer dossier_id;
    private String dossier_nr;
    private String dossier_gemeente_b;
    private String dossier_b;
    private String dossier_type;
    private String dossier_is_raamcontract_jn;

    private String fase_code;
    private Integer fase_prijs; 
    private Integer fase_looptijd; 
    private String fase_code_heeft_details_jn;
    private String fase_detail_code;
    private String actie_code;

    private Integer contract_id;
    private String contract_type;
    private String contract_b;
    private String contract_nr;
    private String contract_is_raamcontract_jn;

    private Integer bestek_id;
    private String bestek_nr;
    private String bestek_omschrijving;
    private String commentaar;

    private Date igb_d;
    private Integer ig_bedrag;

    private Integer deelopdracht_id;
    private Integer aanvraagvastlegging_id;

    private Date ibb_d;
    private Double ib_bedrag;

    private Integer benut_bestek_id;
    private String benut_bestek_nr;
    private String benut_bestek_omschrijving;


    private String deleted_jn;
    private String status_crud;
    private String wijzig_user;


    public Integer getLijn_id() {
        return lijn_id;
    }

    public void setLijn_id(Integer lijn_id) {
        this.lijn_id = lijn_id;
    }

    public Integer getPlanning_dossier_versie() {
        return planning_dossier_versie;
    }

    public void setPlanning_dossier_versie(Integer planning_dossier_versie) {
        this.planning_dossier_versie = planning_dossier_versie;
    }

    public String getDoss_hdr_id() {
        return doss_hdr_id;
    }

    public void setDoss_hdr_id(String doss_hdr_id) {
        this.doss_hdr_id = doss_hdr_id;
    }

    public Integer getJaar() {
        return jaar;
    }

    public void setJaar(Integer jaar) {
        this.jaar = jaar;
    }

    public Integer getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(Integer dossier_id) {
        this.dossier_id = dossier_id;
    }

    public String getDossier_nr() {
        return dossier_nr;
    }

    public void setDossier_nr(String dossier_nr) {
        this.dossier_nr = dossier_nr;
    }

    public void setDossier_gemeente_b(String dossier_gemeente_b) {
        this.dossier_gemeente_b = dossier_gemeente_b;
    }

    public String getDossier_gemeente_b() {
        return dossier_gemeente_b;
    }

    public String getDossier_b() {
        return dossier_b;
    }

    public void setDossier_b(String dossier_b) {
        this.dossier_b = dossier_b;
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public void setDossier_is_raamcontract_jn(String dossier_is_raamcontract_jn) {
        this.dossier_is_raamcontract_jn = dossier_is_raamcontract_jn;
    }

    public String getDossier_is_raamcontract_jn() {
        return dossier_is_raamcontract_jn;
    }

    public String getFase_code() {
        return fase_code;
    }

    public void setFase_code(String fase_code) {
        this.fase_code = fase_code;
    }

    public void setFase_code_heeft_details_jn(String fase_code_heeft_details_jn) {
        this.fase_code_heeft_details_jn = fase_code_heeft_details_jn;
    }

    public String getFase_code_heeft_details_jn() {
        return fase_code_heeft_details_jn;
    }

    public String getFase_detail_code() {
        return fase_detail_code;
    }

    public void setFase_detail_code(String fase_detail_code) {
        this.fase_detail_code = fase_detail_code;
    }

    public void setActie_code(String actie_code) {
        this.actie_code = actie_code;
    }

    public String getActie_code() {
        return actie_code;
    }

    public Integer getContract_id() {
        return contract_id;
    }

    public void setContract_id(Integer contract_id) {
        this.contract_id = contract_id;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getContract_b() {
        return contract_b;
    }

    public void setContract_b(String contract_b) {
        this.contract_b = contract_b;
    }

    public String getContract_nr() {
        return contract_nr;
    }

    public void setContract_nr(String contract_nr) {
        this.contract_nr = contract_nr;
    }

    public void setContract_is_raamcontract_jn(String contract_is_raamcontract_jn) {
        this.contract_is_raamcontract_jn = contract_is_raamcontract_jn;
    }

    public String getContract_is_raamcontract_jn() {
        return contract_is_raamcontract_jn;
    }

    public Date getIgb_d() {
        return igb_d;
    }

    public void setIgb_d(Date igb_d) {
        this.igb_d = igb_d;
    }

    public Integer getIg_bedrag() {
        return ig_bedrag;
    }

    public void setIg_bedrag(Integer ig_bedrag) {
        this.ig_bedrag = ig_bedrag;
    }

    public void setDeelopdracht_id(Integer deelopdracht_id) {
        this.deelopdracht_id = deelopdracht_id;
    }

    public void setAanvraagvastlegging_id(Integer aanvraagvastlegging_id) {
        this.aanvraagvastlegging_id = aanvraagvastlegging_id;
    }

    public Integer getAanvraagvastlegging_id() {
        return aanvraagvastlegging_id;
    }

    public Integer getDeelopdracht_id() {
        return deelopdracht_id;
    }

    public Date getIbb_d() {
        return ibb_d;
    }

    public void setIbb_d(Date ibb_d) {
        this.ibb_d = ibb_d;
    }

    public Double getIb_bedrag() {
        return ib_bedrag;
    }

    public void setIb_bedrag(Double ib_bedrag) {
        this.ib_bedrag = ib_bedrag;
    }

    public Integer getBenut_bestek_id() {
        return benut_bestek_id;
    }

    public void setBenut_bestek_id(Integer benut_bestek_id) {
        this.benut_bestek_id = benut_bestek_id;
    }

    public String getBenut_bestek_nr() {
        return benut_bestek_nr;
    }

    public void setBenut_bestek_nr(String benut_bestek_nr) {
        this.benut_bestek_nr = benut_bestek_nr;
    }

    public String getBenut_bestek_omschrijving() {
        return benut_bestek_omschrijving;
    }

    public void setBenut_bestek_omschrijving(String benut_bestek_omschrijving) {
        this.benut_bestek_omschrijving = benut_bestek_omschrijving;
    }

    public Integer getBestek_id() {
        return bestek_id;
    }

    public void setBestek_id(Integer bestek_id) {
        this.bestek_id = bestek_id;
    }

    public void setBestek_omschrijving(String bestek_omschrijving) {
        this.bestek_omschrijving = bestek_omschrijving;
    }

    public String getBestek_omschrijving() {
        return bestek_omschrijving;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public String getBestek_nr() {
        return bestek_nr;
    }

    public void setBestek_nr(String bestek_nr) {
        this.bestek_nr = bestek_nr;
    }

    public String getDeleted_jn() {
        return deleted_jn;
    }

    public void setDeleted_jn(String deleted_jn) {
        this.deleted_jn = deleted_jn;
    }

    public String getStatus_crud() {
        return status_crud;
    }

    public void setStatus_crud(String status_crud) {
        this.status_crud = status_crud;
    }

    public String getWijzig_user() {
        return wijzig_user;
    }

    public void setWijzig_user(String wijzig_user) {
        this.wijzig_user = wijzig_user;
    }

    public Integer getFase_looptijd() {
        return fase_looptijd;
    }

    public void setFase_looptijd(Integer fase_looptijd) {
        this.fase_looptijd = fase_looptijd;
    }

    public Integer getFase_prijs() {
        return fase_prijs;
    }

    public void setFase_prijs(Integer fase_prijs) {
        this.fase_prijs = fase_prijs;
    }


}