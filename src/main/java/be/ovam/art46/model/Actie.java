package be.ovam.art46.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Actie implements Serializable {

    private static final long serialVersionUID = -8330487824465122457L;

    private Integer actie_id;
    private Integer actie_type_id;
    private Integer actie_sub_type_id;
    private Date actie_d;
    private Date realisatie_d;
    private String commentaar;
    private BigDecimal rate;
    private Date stop_d;
    private String zimbraIdVan;
    private String zimbraIdTot;
    private String zimbraSave;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getStop_d() {
        return stop_d;
    }

    public void setStop_d(Date stop_d) {
        this.stop_d = stop_d;
    }

    public Date getActie_d() {
        return actie_d;
    }

    public void setActie_d(Date actie_d) {
        this.actie_d = actie_d;
    }

    public Integer getActie_id() {
        return actie_id;
    }

    public void setActie_id(Integer actie_id) {
        this.actie_id = actie_id;
    }

    public Integer getActie_type_id() {
        return actie_type_id;
    }

    public void setActie_type_id(Integer actie_type_id) {
        this.actie_type_id = actie_type_id;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public Date getRealisatie_d() {
        return realisatie_d;
    }

    public void setRealisatie_d(Date realisatie_d) {
        this.realisatie_d = realisatie_d;
    }

    public Integer getActie_sub_type_id() {
        return actie_sub_type_id;
    }

    public void setActie_sub_type_id(Integer actie_sub_type_id) {
        this.actie_sub_type_id = actie_sub_type_id;
    }

    public String getZimbraSave() {
        return zimbraSave;
    }

    public void setZimbraSave(String zimbraSave) {
        this.zimbraSave = zimbraSave;
    }

    public String getZimbraIdTot() {
        return zimbraIdTot;
    }

    public void setZimbraIdTot(String zimbraIdTot) {
        this.zimbraIdTot = zimbraIdTot;
    }

    public String getZimbraIdVan() {
        return zimbraIdVan;
    }

    public void setZimbraIdVan(String zimbraIdVan) {
        this.zimbraIdVan = zimbraIdVan;
    }


}
