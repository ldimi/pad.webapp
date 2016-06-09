package be.ovam.art46.model;


import java.io.Serializable;
import java.util.Date;

public class BestekDO implements Serializable {
	
    private static final long serialVersionUID = 1223571581561574997L;
    
    private Integer bestek_id;
	private String bestek_nr;
    private String bestek_hdr_id;

    private Integer dossier_id;
	private String dossier_nr;
	private String dossier_type;
    
	private Integer type_id;
	private Integer procedure_id;
	private Integer fase_id;
	private Integer dienst_id;
	private String omschrijving;
	private String commentaar;
	private Integer btw_tarief;	
	private Date start_d;
	private Date stop_d;
    private Date afsluit_d;
	private String verlenging_s;
	private String wbs_nr;
	private String screening_jn;
    private Integer screening_organisatie_id;
    
    private Integer meetstaat_pdf_brief_id;
    private Integer meetstaat_excel_brief_id;
    
    private String controle_dms_id;
    private String controle_dms_folder;
    private String controle_dms_filename;
    
    private String voorstellen_opm;
	
	private String raamcontract_jn;
	
	
	
    public Integer getBestek_id() {
        return bestek_id;
    }
    public void setBestek_id(Integer bestek_id) {
        this.bestek_id = bestek_id;
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
    public String getDossier_type() {
        return dossier_type;
    }
    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }
    public String getBestek_nr() {
        return bestek_nr;
    }
    public void setBestek_nr(String bestek_nr) {
        this.bestek_nr = bestek_nr;
    }
    public Integer getType_id() {
        return type_id;
    }
    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }
    public Integer getProcedure_id() {
        return procedure_id;
    }
    public void setProcedure_id(Integer procedure_id) {
        this.procedure_id = procedure_id;
    }
    public Integer getFase_id() {
        return fase_id;
    }
    public void setFase_id(Integer fase_id) {
        this.fase_id = fase_id;
    }
    public Integer getDienst_id() {
        return dienst_id;
    }
    public void setDienst_id(Integer dienst_id) {
        this.dienst_id = dienst_id;
    }
    public String getOmschrijving() {
        return omschrijving;
    }
    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
    public String getCommentaar() {
        return commentaar;
    }
    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }
    public Integer getBtw_tarief() {
        return btw_tarief;
    }
    public void setBtw_tarief(Integer btw_tarief) {
        this.btw_tarief = btw_tarief;
    }
    public Date getStart_d() {
        return start_d;
    }
    public void setStart_d(Date start_d) {
        this.start_d = start_d;
    }
    public Date getStop_d() {
        return stop_d;
    }
    public void setStop_d(Date stop_d) {
        this.stop_d = stop_d;
    }
    public Date getAfsluit_d() {
        return afsluit_d;
    }
    public void setAfsluit_d(Date afsluit_d) {
        this.afsluit_d = afsluit_d;
    }
    public String getVerlenging_s() {
        return verlenging_s;
    }
    public void setVerlenging_s(String verlenging_s) {
        this.verlenging_s = verlenging_s;
    }
    public String getWbs_nr() {
        return wbs_nr;
    }
    public void setWbs_nr(String wbs_nr) {
        this.wbs_nr = wbs_nr;
    }
    public String getScreening_jn() {
        if (screening_jn == null) {
            screening_jn = "N"; // default
        }
        return screening_jn;
    }
    public void setScreening_jn(String screening_jn) {
        this.screening_jn = screening_jn;
    }
    public String getRaamcontract_jn() {
        return raamcontract_jn;
    }
    public void setRaamcontract_jn(String raamcontract_jn) {
        this.raamcontract_jn = raamcontract_jn;
    }

    public Integer getScreening_organisatie_id() {
        return screening_organisatie_id;
    }

    public void setScreening_organisatie_id(Integer screening_organisatie_id) {
        this.screening_organisatie_id = screening_organisatie_id;
    }

    public String getBestek_hdr_id() {
        return bestek_hdr_id;
    }

    public void setBestek_hdr_id(String bestek_hdr_id) {
        this.bestek_hdr_id = bestek_hdr_id;
    }

    public String getVoorstellen_opm() {
        return voorstellen_opm;
    }

    public void setVoorstellen_opm(String voorstellen_opm) {
        this.voorstellen_opm = voorstellen_opm;
    }

    public Integer getMeetstaat_pdf_brief_id() {
        return meetstaat_pdf_brief_id;
    }

    public void setMeetstaat_pdf_brief_id(Integer meetstaat_pdf_brief_id) {
        this.meetstaat_pdf_brief_id = meetstaat_pdf_brief_id;
    }

    public Integer getMeetstaat_excel_brief_id() {
        return meetstaat_excel_brief_id;
    }

    public void setMeetstaat_excel_brief_id(Integer meetstaat_excel_brief_id) {
        this.meetstaat_excel_brief_id = meetstaat_excel_brief_id;
    }

    public String getControle_dms_id() {
        return controle_dms_id;
    }

    public void setControle_dms_id(String controle_dms_id) {
        this.controle_dms_id = controle_dms_id;
    }

    public String getControle_dms_folder() {
        return controle_dms_folder;
    }

    public void setControle_dms_folder(String controle_dms_folder) {
        this.controle_dms_folder = controle_dms_folder;
    }

    public String getControle_dms_filename() {
        return controle_dms_filename;
    }

    public void setControle_dms_filename(String controle_dms_filename) {
        this.controle_dms_filename = controle_dms_filename;
    }

	
}
