package be.ovam.art46.controller.beheer;

import java.io.Serializable;


public class DossierRechtsgrondDO implements Serializable {

    private static final long serialVersionUID = -4244544938688973656L;

    private String dossier_type;
    private String rechtsgrond_code;
    private String rechtsgrond_b;
    private String screening_jn;

    private String status_crud;
    
    public String getDossier_type() {
        return dossier_type;
    }
    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }
    public String getRechtsgrond_code() {
        return rechtsgrond_code;
    }
    public void setRechtsgrond_code(String rechtsgrond_code) {
        this.rechtsgrond_code = rechtsgrond_code;
    }
    public String getRechtsgrond_b() {
        return rechtsgrond_b;
    }
    public void setRechtsgrond_b(String rechtsgrond_b) {
        this.rechtsgrond_b = rechtsgrond_b;
    }
    public String getScreening_jn() {
        return screening_jn;
    }
    public void setScreening_jn(String screening_jn) {
        this.screening_jn = screening_jn;
    }
    public String getStatus_crud() {
        return status_crud;
    }
    public void setStatus_crud(String status_crud) {
        this.status_crud = status_crud;
    }

}
