package be.ovam.art46.controller.form;

/**
 * Created by Koen on 20/01/2015.
 */
public class OverzichtSchuldvorderingenForm {
    long offerteId;
    Integer deelOpdrachtId;

    public long getOfferteId() {
        return offerteId;
    }

    public void setOfferteId(long offerteId) {
        this.offerteId = offerteId;
    }

    public Integer getDeelOpdrachtId() {
        return deelOpdrachtId;
    }

    public void setDeelOpdrachtId(Integer deelOpdrachtId) {
        this.deelOpdrachtId = deelOpdrachtId;
    }
}