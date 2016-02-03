package be.ovam.art46.controller.model;

public class ActieForm {
	
	private String actie_id;	
	private String dossier_id;
	private Long bestek_id;
	private String bestek_nr;	
	private String actie_type_id;
	private String actie_sub_type_id;
	private String actie_d;
	private String realisatie_d;
	private String stop_d;
	private String rate;
	private String commentaar;
	private String dossier_type;
	private String zimbraId;
	private String zimbraIdVan;
	private String zimbraIdTot;
	private String zimbraSave = "0";

    public String getActie_id() {
        return actie_id;
    }

    public void setActie_id(String actie_id) {
        this.actie_id = actie_id;
    }

    public String getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(String dossier_id) {
        this.dossier_id = dossier_id;
    }

    public Long getBestek_id() {
        return bestek_id;
    }

    public void setBestek_id(Long bestek_id) {
        this.bestek_id = bestek_id;
    }

    public String getBestek_nr() {
        return bestek_nr;
    }

    public void setBestek_nr(String bestek_nr) {
        this.bestek_nr = bestek_nr;
    }

    public String getActie_type_id() {
        return actie_type_id;
    }

    public void setActie_type_id(String actie_type_id) {
        this.actie_type_id = actie_type_id;
    }

    public String getActie_sub_type_id() {
        return actie_sub_type_id;
    }

    public void setActie_sub_type_id(String actie_sub_type_id) {
        this.actie_sub_type_id = actie_sub_type_id;
    }

    public String getActie_d() {
        return actie_d;
    }

    public void setActie_d(String actie_d) {
        this.actie_d = actie_d;
    }

    public String getRealisatie_d() {
        return realisatie_d;
    }

    public void setRealisatie_d(String realisatie_d) {
        this.realisatie_d = realisatie_d;
    }

    public String getStop_d() {
        return stop_d;
    }

    public void setStop_d(String stop_d) {
        this.stop_d = stop_d;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public String getZimbraId() {
        return zimbraId;
    }

    public void setZimbraId(String zimbraId) {
        this.zimbraId = zimbraId;
    }

    public String getZimbraIdVan() {
        return zimbraIdVan;
    }

    public void setZimbraIdVan(String zimbraIdVan) {
        this.zimbraIdVan = zimbraIdVan;
    }

    public String getZimbraIdTot() {
        return zimbraIdTot;
    }

    public void setZimbraIdTot(String zimbraIdTot) {
        this.zimbraIdTot = zimbraIdTot;
    }

    public String getZimbraSave() {
        return zimbraSave;
    }

    public void setZimbraSave(String zimbraSave) {
        this.zimbraSave = zimbraSave;
    }
}
