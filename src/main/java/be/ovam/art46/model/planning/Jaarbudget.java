package be.ovam.art46.model.planning;

import java.io.Serializable;

public class Jaarbudget implements Serializable {

    private static final long serialVersionUID = 119823064002481615L;

    private Integer jaar;
    private String budget_code;
    private Integer budget;
    private Integer vek_budget;
    private Integer effectief_budget;
    private String status_crud;


    public Integer getJaar() {
        return jaar;
    }

    public void setJaar(Integer jaar) {
        this.jaar = jaar;
    }

    public String getBudget_code() {
        return budget_code;
    }

    public void setBudget_code(String budget_code) {
        this.budget_code = budget_code;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getStatus_crud() {
        return status_crud;
    }

    public void setStatus_crud(String status_crud) {
        this.status_crud = status_crud;
    }

    public Integer getVek_budget() {
        return vek_budget;
    }

    public void setVek_budget(Integer vek_budget) {
        this.vek_budget = vek_budget;
    }

    public Integer getEffectief_budget() {
        return effectief_budget;
    }

    public void setEffectief_budget(Integer effectief_budget) {
        this.effectief_budget = effectief_budget;
    }


}
