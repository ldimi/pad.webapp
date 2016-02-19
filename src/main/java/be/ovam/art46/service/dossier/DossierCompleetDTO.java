package be.ovam.art46.service.dossier;

import be.ovam.pad.model.dossier.InstrumentDTO;
import be.ovam.pad.model.dossier.ParameterDTO;
import be.ovam.pad.model.dossier.StofgroepDTO;
import be.ovam.pad.model.dossier.VerontreinigendeActiviteitDTO;
import java.util.List;

public class DossierCompleetDTO {
    
    private DossierDTO dossier;

    private List<ParameterDTO> parameter_lijst;
    private List<StofgroepDTO> stofgroep_lijst;

    private List<VerontreinigendeActiviteitDTO> activiteit_lijst;
    private List<InstrumentDTO> instrument_lijst;

    public DossierDTO getDossier() {
        return dossier;
    }

    public void setDossier(DossierDTO dossier) {
        this.dossier = dossier;
    }

    public List<ParameterDTO> getParameter_lijst() {
        return parameter_lijst;
    }

    public void setParameter_lijst(List<ParameterDTO> parameter_lijst) {
        this.parameter_lijst = parameter_lijst;
    }

    public List<StofgroepDTO> getStofgroep_lijst() {
        return stofgroep_lijst;
    }

    public void setStofgroep_lijst(List<StofgroepDTO> stofgroep_lijst) {
        this.stofgroep_lijst = stofgroep_lijst;
    }

    public List<VerontreinigendeActiviteitDTO> getActiviteit_lijst() {
        return activiteit_lijst;
    }

    public void setActiviteit_lijst(List<VerontreinigendeActiviteitDTO> activiteit_lijst) {
        this.activiteit_lijst = activiteit_lijst;
    }

    public List<InstrumentDTO> getInstrument_lijst() {
        return instrument_lijst;
    }

    public void setInstrument_lijst(List<InstrumentDTO> instrument_lijst) {
        this.instrument_lijst = instrument_lijst;
    }
    
}
