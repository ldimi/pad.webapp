package be.ovam.art46.model.planning;

import java.io.Serializable;


public class PlanningFaseDetailDO implements Serializable {

    private static final long serialVersionUID = -4244544938688973656L;

    private String fase_code;
    private String fase_detail_code;
    private String fase_detail_code_b;


    public String getFase_code() {
        return fase_code;
    }

    public void setFase_code(String fase_code) {
        this.fase_code = fase_code;
    }

    public String getFase_detail_code() {
        return fase_detail_code;
    }

    public void setFase_detail_code(String fase_detail_code) {
        this.fase_detail_code = fase_detail_code;
    }

    public String getFase_detail_code_b() {
        return fase_detail_code_b;
    }

    public void setFase_detail_code_b(String fase_detail_code_b) {
        this.fase_detail_code_b = fase_detail_code_b;
    }

}
