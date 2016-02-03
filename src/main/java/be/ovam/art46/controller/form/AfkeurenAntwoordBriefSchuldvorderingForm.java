package be.ovam.art46.controller.form;

/**
 * Created by Koen on 11/04/2014.
 */
public class AfkeurenAntwoordBriefSchuldvorderingForm {
    private Integer id;
    private String motivatie;

    public AfkeurenAntwoordBriefSchuldvorderingForm(){};
    public AfkeurenAntwoordBriefSchuldvorderingForm(Integer vordering_id) {
        id = vordering_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotivatie() {
        return motivatie;
    }

    public void setMotivatie(String motivatie) {
        this.motivatie = motivatie;
    }
}
