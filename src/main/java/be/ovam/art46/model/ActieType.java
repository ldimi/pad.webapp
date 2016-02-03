package be.ovam.art46.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ActieType implements Serializable {

    private static final long serialVersionUID = 2409883025998420737L;

    private Integer actie_type_id;
    private String actie_type_b;
    private String dossier_type;
    private String ingebreke_s;
    private BigDecimal rate;

    public Integer getActie_type_id() {
        return actie_type_id;
    }

    public void setActie_type_id(Integer actie_type_id) {
        this.actie_type_id = actie_type_id;
    }

    public String getActie_type_b() {
        return actie_type_b;
    }

    public void setActie_type_b(String actie_type_b) {
        this.actie_type_b = actie_type_b;
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public String getIngebreke_s() {
        return ingebreke_s;
    }

    public void setIngebreke_s(String ingebreke_s) {
        this.ingebreke_s = ingebreke_s;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }


}
