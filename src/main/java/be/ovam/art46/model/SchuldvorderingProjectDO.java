package be.ovam.art46.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SchuldvorderingProjectDO implements Serializable {

    private Integer vordering_id;
    private Integer volg_nr;

    private String project_id;
    private BigDecimal bedrag;
    private String wbs_nr;

    private String initieel_acht_nr;


    public Integer getVordering_id() {
        return vordering_id;
    }

    public void setVordering_id(Integer vordering_id) {
        this.vordering_id = vordering_id;
    }

    public void setVolg_nr(Integer volg_nr) {
        this.volg_nr = volg_nr;
    }

    public Integer getVolg_nr() {
        return volg_nr;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }


    public String getWbs_nr() {
        return wbs_nr;
    }

    public void setWbs_nr(String wbs_nr) {
        this.wbs_nr = wbs_nr;
    }

    public void setInitieel_acht_nr(String initieel_acht_nr) {
        this.initieel_acht_nr = initieel_acht_nr;
    }

    public String getInitieel_acht_nr() {
        return initieel_acht_nr;
    }

}