package be.ovam.art46.model;

import java.io.Serializable;
import java.util.Date;

import static be.ovam.pad.util.RoundUtils.roundBy2;


public class SchuldvorderingDO implements Serializable {

    private static final long serialVersionUID = -1270550782975540921L;
    
    private Integer vordering_id;
    private String schuldvordering_nr;

    private String contact_doss_hdr_id;
    private String contact_doss_hdr_email;
    
    
    private Long bestek_id;
    private Integer brief_id;
    
    private Integer antwoord_pdf_brief_id;
    private String antw_dms_folder;
    private String antw_dms_filename;
    
    private Integer deelopdracht_id;
    private Integer aanvr_schuldvordering_id;
    private Integer offerte_id;

    private Integer schuldvordering_fase_id;

    private Date vordering_d;
    private Date goedkeuring_d;
    private Date acceptatie_d;
    private Date uiterste_verific_d;
    private Date uiterste_d;
    private Double vordering_bedrag;
    private Double vordering_correct_bedrag;
    private Double goedkeuring_bedrag;
    private Double herziening_bedrag;
    private Double herziening_correct_bedrag;
    private Double boete_bedrag;
    private String commentaar;
    private String dossier_type;
    private Date van_d;
    private Date tot_d;
    private Date betaal_d;
    private String vordering_nr;
    private String bestek_nr;
    private String brief_nr;
    private String webloket_gebruiker_email;
    private String deelopdracht;
    private String afgekeurd_jn;
    
    private String status;
    
    private Date print_d;
    
    private String motivatie;
    
        
    public Integer getVordering_id() {
        return vordering_id;
    }

    public void setVordering_id(Integer vordering_id) {
        this.vordering_id = vordering_id;
    }

    public String getVordering_nr() {
        return vordering_nr;
    }

    public void setVordering_nr(String vordering_nr) {
        this.vordering_nr = vordering_nr;
    }

    public String getSchuldvordering_nr() {
        return schuldvordering_nr;
    }

    public void setSchuldvordering_nr(String schuldvordering_nr) {
        this.schuldvordering_nr = schuldvordering_nr;
    }

    public Date getTot_d() {
        return tot_d;
    }

    public void setTot_d(Date tot_d) {
        this.tot_d = tot_d;
    }

    public Date getVan_d() {
        return van_d;
    }

    public void setVan_d(Date van_d) {
        this.van_d = van_d;
    }

    public Long getBestek_id() {
        return bestek_id;
    }

    public void setBestek_id(Long bestek_id) {
        this.bestek_id = bestek_id;
    }

    public Double getBoete_bedrag() {
        return boete_bedrag;
    }

    public void setBoete_bedrag(Double boete_bedrag) {
        this.boete_bedrag = roundBy2(boete_bedrag);
    }

    public Integer getBrief_id() {
        return brief_id;
    }

    public void setBrief_id(Integer brief_id) {
        this.brief_id = brief_id;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public Double getGoedkeuring_bedrag() {
        return goedkeuring_bedrag;
    }

    public void setGoedkeuring_bedrag(Double goedkeuring_bedrag) {
        this.goedkeuring_bedrag = roundBy2(goedkeuring_bedrag);
    }

    public Date getGoedkeuring_d() {
        return goedkeuring_d;
    }

    public void setGoedkeuring_d(Date goedkeuring_d) {
        this.goedkeuring_d = goedkeuring_d;
    }

    public Date getAcceptatie_d() {
        return acceptatie_d;
    }

    public void setAcceptatie_d(Date acceptatie_d) {
        this.acceptatie_d = acceptatie_d;
    }

    public void setUiterste_verific_d(Date uiterste_verific_d) {
        this.uiterste_verific_d = uiterste_verific_d;
    }

    public Date getUiterste_verific_d() {
        return uiterste_verific_d;
    }

    public Double getHerziening_bedrag() {
        return herziening_bedrag;
    }

    public void setHerziening_bedrag(Double herziening_bedrag) {
        this.herziening_bedrag = roundBy2(herziening_bedrag);
    }

    public Double getHerziening_correct_bedrag() {
        return herziening_correct_bedrag;
    }

    public void setHerziening_correct_bedrag(Double herziening_correct_bedrag) {
        this.herziening_correct_bedrag = roundBy2(herziening_correct_bedrag);
    }

    public Double getVordering_bedrag() {
        return vordering_bedrag;
    }

    public void setVordering_bedrag(Double vordering_bedrag) {
        this.vordering_bedrag = roundBy2(vordering_bedrag);
    }

    public Double getVordering_correct_bedrag() {
        return vordering_correct_bedrag;
    }

    public void setVordering_correct_bedrag(Double vordering_correct_bedrag) {
        this.vordering_correct_bedrag = roundBy2(vordering_correct_bedrag);
    }

    public Date getVordering_d() {
        return vordering_d;
    }

    public void setVordering_d(Date vordering_d) {
        this.vordering_d = vordering_d;
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public Date getUiterste_d() {
        return uiterste_d;
    }

    public void setUiterste_d(Date uiterste_d) {
        this.uiterste_d = uiterste_d;
    }

    public Integer getDeelopdracht_id() {
        return deelopdracht_id;
    }

    public void setDeelopdracht_id(Integer deelopdracht_id) {
        this.deelopdracht_id = deelopdracht_id;
    }

    public Date getBetaal_d() {
        return betaal_d;
    }

    public void setBetaal_d(Date betaal_d) {
        this.betaal_d = betaal_d;
    }

    public String getBestek_nr() {
        return bestek_nr;
    }

    public void setBestek_nr(String bestek_nr) {
        this.bestek_nr = bestek_nr;
    }

    public String getBrief_nr() {
        return brief_nr;
    }

    public void setBrief_nr(String brief_nr) {
        this.brief_nr = brief_nr;
    }

    public void setDeelopdracht(String deelopdracht) {
        this.deelopdracht = deelopdracht;
    }

    public String getDeelopdracht() {
        return deelopdracht;
    }

    public String getAfgekeurd_jn() {
        return afgekeurd_jn;
    }

    public void setAfgekeurd_jn(String afgekeurd_jn) {
        this.afgekeurd_jn = afgekeurd_jn;
    }

    public Integer getAntwoord_pdf_brief_id() {
        return antwoord_pdf_brief_id;
    }

    public void setAntwoord_pdf_brief_id(Integer antwoord_pdf_brief_id) {
        this.antwoord_pdf_brief_id = antwoord_pdf_brief_id;
    }

    public String getAntw_dms_folder() {
        return antw_dms_folder;
    }

    public void setAntw_dms_folder(String antw_dms_folder) {
        this.antw_dms_folder = antw_dms_folder;
    }

    public String getAntw_dms_filename() {
        return antw_dms_filename;
    }

    public void setAntw_dms_filename(String antw_dms_filename) {
        this.antw_dms_filename = antw_dms_filename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSchuldvordering_fase_id() {
        return schuldvordering_fase_id;
    }

    public void setSchuldvordering_fase_id(Integer schuldvordering_fase_id) {
        this.schuldvordering_fase_id = schuldvordering_fase_id;
    }

    public Date getPrint_d() {
        return print_d;
    }

    public void setPrint_d(Date print_d) {
        this.print_d = print_d;
    }

    public String getMotivatie() {
        return motivatie;
    }

    public void setMotivatie(String motivatie) {
        this.motivatie = motivatie;
    }

    public Integer getAanvr_schuldvordering_id() {
        return aanvr_schuldvordering_id;
    }

    public void setAanvr_schuldvordering_id(Integer aanvr_schuldvordering_id) {
        this.aanvr_schuldvordering_id = aanvr_schuldvordering_id;
    }

    public String getWebloket_gebruiker_email() {
        return webloket_gebruiker_email;
    }
    public void setWebloket_gebruiker_email(String webloket_gebruiker_email) {
        this.webloket_gebruiker_email = webloket_gebruiker_email;
    }

    public String getContact_doss_hdr_id() {
        return contact_doss_hdr_id;
    }
    public void setContact_doss_hdr_id(String contact_doss_hdr_id) {
        this.contact_doss_hdr_id = contact_doss_hdr_id;
    }

    public String getContact_doss_hdr_email() {
        return contact_doss_hdr_email;
    }
    public void setContact_doss_hdr_email(String contact_doss_hdr_email) {
        this.contact_doss_hdr_email = contact_doss_hdr_email;
    }

    public Integer getOfferte_id() {
        return offerte_id;
    }

    public void setOfferte_id(Integer offerte_id) {
        this.offerte_id = offerte_id;
    }
    

}