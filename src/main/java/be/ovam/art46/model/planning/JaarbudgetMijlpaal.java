package be.ovam.art46.model.planning;

import java.io.Serializable;
import java.util.Date;

public class JaarbudgetMijlpaal implements Serializable {

    private static final long serialVersionUID = 119823064002481615L;

    private Integer jaar;
    private Date mijlpaal_d;
    private Integer percentage;
    private String status_crud;

    public Integer getJaar() {
        return jaar;
    }

    public void setJaar(Integer jaar) {
        this.jaar = jaar;
    }

    public Date getMijlpaal_d() {
        return mijlpaal_d;
    }

    public void setMijlpaal_d(Date mijlpaal_d) {
        this.mijlpaal_d = mijlpaal_d;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getStatus_crud() {
        return status_crud;
    }

    public void setStatus_crud(String status_crud) {
        this.status_crud = status_crud;
    }


}
