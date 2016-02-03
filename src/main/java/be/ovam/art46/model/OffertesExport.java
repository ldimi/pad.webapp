package be.ovam.art46.model;

import be.ovam.pad.model.Offerte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koen on 3/01/14.
 */
public class OffertesExport {
    private List<OffertesExportRegel> offertesExportRegels = new ArrayList<OffertesExportRegel>();
    private List<Offerte> offertes = new ArrayList<Offerte>();

    public List<OffertesExportRegel> getOffertesExportRegels() {
        return offertesExportRegels;
    }

    public void setOffertesExportRegels(List<OffertesExportRegel> offertesExportRegels) {
        this.offertesExportRegels = offertesExportRegels;
    }

    public List<Offerte> getOffertes() {
        return offertes;
    }

    public void setOffertes(List<Offerte> offertes) {
        this.offertes = offertes;
    }
}
