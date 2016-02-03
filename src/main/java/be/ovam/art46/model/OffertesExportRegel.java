package be.ovam.art46.model;

import be.ovam.pad.model.OfferteRegel;

import java.util.List;

/**
 * Created by Koen on 2/01/14.
 */
public class OffertesExportRegel extends OfferteRegel {
    private List<OfferteRegel> offerteRegels;

    public List<OfferteRegel> getOfferteRegels() {
        return offerteRegels;
    }

    public void setOfferteRegels(List<OfferteRegel> offerteRegels) {
        this.offerteRegels = offerteRegels;
    }
}
