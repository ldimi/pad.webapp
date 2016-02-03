package be.ovam.art46.model.planning;

import java.io.Serializable;


public class PlanningFaseDO implements Serializable {

    private static final long serialVersionUID = -4244544938688973656L;

    private String dossier_type;
    private String fase_code;
    private String fase_code_b;
    private String budget_code;


    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

    public String getFase_code() {
        return fase_code;
    }

    public void setFase_code(String fase_code) {
        this.fase_code = fase_code;
    }

    public String getFase_code_b() {
        return fase_code_b;
    }

    public void setFase_code_b(String fase_code_b) {
        this.fase_code_b = fase_code_b;
    }

    public void setBudget_code(String budget_code) {
        this.budget_code = budget_code;
    }

    public String getBudget_code() {
        return budget_code;
    }

}
