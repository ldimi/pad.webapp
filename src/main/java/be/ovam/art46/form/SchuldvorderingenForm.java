package be.ovam.art46.form;

import be.ovam.pad.model.Schuldvordering;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koen on 28/04/2014.
 */
public class SchuldvorderingenForm {

    private List<Schuldvordering> schuldvorderingen = new ArrayList<Schuldvordering>();

    public List<Schuldvordering> getSchuldvorderingen() {
        return schuldvorderingen;

    }

    public void setSchuldvorderingen(List<Schuldvordering> schuldvorderingen) {
        this.schuldvorderingen = schuldvorderingen;
    }
}
