package be.ovam.art46.model;

import java.text.DecimalFormat;

import static be.ovam.pad.util.RoundUtils.roundBy2;

/**
 * Created by koencorstjens on 16-7-13.
 */
public class Spreiding {

    private Integer jaar;
    private Double bedrag;
    private int aanvraagid;

    public int getAanvraagid() {
        return aanvraagid;
    }

    public void setAanvraagid(int aanvraagid) {
        this.aanvraagid = aanvraagid;
    }

    public Integer getJaar() {
        return jaar;
    }

    public void setJaar(Integer jaar) {
        this.jaar = jaar;
    }

    public Double getBedrag() {
        return bedrag;
    }

    public void setBedrag(Double bedrag) {
        this.bedrag = roundBy2(bedrag);
    }

}
