package be.ovam.art46.model;

import java.io.Serializable;
import java.util.Date;

public class Archief implements Serializable {

    private static final long serialVersionUID = 3901035990724940116L;

    private Integer archief_id;
    private String archief_nr;
    private Integer dossier_id;
    private String afdeling = "IVS";
    private Date archief_d;
    private String archief_b;
    private String plaats;

    public String getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(String afdeling) {
        this.afdeling = afdeling;
    }

    public String getArchief_b() {
        return archief_b;
    }

    public void setArchief_b(String archief_b) {
        this.archief_b = archief_b;
    }

    public Date getArchief_d() {
        return archief_d;
    }

    public void setArchief_d(Date archief_d) {
        this.archief_d = archief_d;
    }

    public Integer getArchief_id() {
        return archief_id;
    }

    public void setArchief_id(Integer archief_id) {
        this.archief_id = archief_id;
    }

    public Integer getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(Integer dossier_id) {
        this.dossier_id = dossier_id;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getArchief_nr() {
        return archief_nr;
    }

    public void setArchief_nr(String archief_nr) {
        this.archief_nr = archief_nr;
    }


}
