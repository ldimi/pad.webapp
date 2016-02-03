package be.ovam.art46.model;

import java.math.BigDecimal;

public class Project {

    private String project_id;
    private Long bestek_id;
    private BigDecimal credit_totaal;
    private BigDecimal debet_totaal;
    private BigDecimal initieel_bedrag;
    private String initieel_acht_nr;
    private Integer boekjaar;
    private String wbs_nr;


    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Long getBestek_id() {
        return bestek_id;
    }

    public void setBestek_id(Long bestek_id) {
        this.bestek_id = bestek_id;
    }

    public BigDecimal getCredit_totaal() {
        return credit_totaal;
    }

    public void setCredit_totaal(BigDecimal credit_totaal) {
        this.credit_totaal = credit_totaal;
    }

    public BigDecimal getDebet_totaal() {
        return debet_totaal;
    }

    public void setDebet_totaal(BigDecimal debet_totaal) {
        this.debet_totaal = debet_totaal;
    }

    public BigDecimal getInitieel_bedrag() {
        return initieel_bedrag;
    }

    public void setInitieel_bedrag(BigDecimal initieel_bedrag) {
        this.initieel_bedrag = initieel_bedrag;
    }

    public String getInitieel_acht_nr() {
        return initieel_acht_nr;
    }

    public void setInitieel_acht_nr(String initieel_acht_nr) {
        this.initieel_acht_nr = initieel_acht_nr;
    }

    public Integer getBoekjaar() {
        return boekjaar;
    }

    public void setBoekjaar(Integer boekjaar) {
        this.boekjaar = boekjaar;
    }

    public String getWbs_nr() {
        return wbs_nr;
    }

    public void setWbs_nr(String wbs_nr) {
        this.wbs_nr = wbs_nr;
    }


}
