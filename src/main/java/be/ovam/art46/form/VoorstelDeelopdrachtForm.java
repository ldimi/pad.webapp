package be.ovam.art46.form;

import be.ovam.pad.model.VoorstelDeelopdracht;
import be.ovam.pad.model.VoorstelDeelopdrachtRegel;

/**
 * Created by Koen on 2/05/2014.
 */
public class VoorstelDeelopdrachtForm {
    private VoorstelDeelopdracht voorstelDeelopdracht;
    private VoorstelDeelopdrachtRegel nieuwevoorstelDeelopdrachtRegel;

    public VoorstelDeelopdracht getVoorstelDeelopdracht() {
        return voorstelDeelopdracht;
    }

    public void setVoorstelDeelopdracht(VoorstelDeelopdracht voorstelDeelopdracht) {
        this.voorstelDeelopdracht = voorstelDeelopdracht;
    }

    public VoorstelDeelopdrachtRegel getNieuwevoorstelDeelopdrachtRegel() {
        return nieuwevoorstelDeelopdrachtRegel;
    }

    public void setNieuwevoorstelDeelopdrachtRegel(VoorstelDeelopdrachtRegel nieuwevoorstelDeelopdrachtRegel) {
        this.nieuwevoorstelDeelopdrachtRegel = nieuwevoorstelDeelopdrachtRegel;
    }

    public String getId() {
        if (voorstelDeelopdracht.getId() == null) {
            return "nieuw";
        }
        return "" + voorstelDeelopdracht.getId();
    }

}
