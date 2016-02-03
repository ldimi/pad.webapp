package be.ovam.art46.model;

import java.io.Serializable;

public class ActieSubType implements Serializable {

    private static final long serialVersionUID = 1263815171445671579L;

    private Integer actie_sub_type_id;
    private Integer actie_type_id;
    private String actie_sub_type_b;

    public String getActie_sub_type_b() {
        return actie_sub_type_b;
    }

    public void setActie_sub_type_b(String actie_sub_type_b) {
        this.actie_sub_type_b = actie_sub_type_b;
    }

    public Integer getActie_sub_type_id() {
        return actie_sub_type_id;
    }

    public void setActie_sub_type_id(Integer actie_sub_type_id) {
        this.actie_sub_type_id = actie_sub_type_id;
    }

    public Integer getActie_type_id() {
        return actie_type_id;
    }

    public void setActie_type_id(Integer actie_type_id) {
        this.actie_type_id = actie_type_id;
    }

}
