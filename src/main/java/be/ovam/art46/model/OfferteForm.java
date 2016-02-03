package be.ovam.art46.model;


import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.Offerte;
import be.ovam.pad.model.OfferteRegel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koen Corstjens on 29-8-13.
 */
public class OfferteForm {

    private List<OfferteRegel> offerteRegels = new ArrayList<OfferteRegel>();

    private Offerte offerte;

    private List<String> opmerkingen = new ArrayList<String>();

    private OfferteRegel nieuweOfferteRegel = new OfferteRegel();

    public OfferteForm() {
        super();
        nieuweOfferteRegel.setExtraRegelType(MeetstaatRegel.REGEL_TYPE_VH);
    }

    public OfferteForm(List<OfferteRegel> offerteRegels) {
        this.offerteRegels = offerteRegels;
    }

    public OfferteForm(Offerte offerte, List<OfferteRegel> offerteRegels) {
        this.offerteRegels = offerteRegels;
    }

    public List<OfferteRegel> getOfferteRegels() {
        return offerteRegels;
    }

    public void setOfferteRegels(List<OfferteRegel> offerteRegels) {
        this.offerteRegels = offerteRegels;
    }

    public Offerte getOfferte() {
        return offerte;
    }

    public void setOfferte(Offerte offerte) {
        this.offerte = offerte;
    }

    public List<String> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<String> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public OfferteRegel getNieuweOfferteRegel() {
        return nieuweOfferteRegel;
    }

    public void setNieuweOfferteRegel(OfferteRegel nieuweOfferteRegel) {
        this.nieuweOfferteRegel = nieuweOfferteRegel;
    }
}
