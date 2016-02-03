package be.ovam.art46.model.planning;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("rawtypes")
public class PlanningDataDO implements Serializable {

    private static final long serialVersionUID = 4262121174849067580L;

    private List<PlanningLijnDO> lijnen;

    private List dossierDD;
    private List faseDD;
    private List faseDetailDD;
    private List raamcontractenDD;


    public void setLijnen(List<PlanningLijnDO> lijnen) {
        this.lijnen = lijnen;
    }

    public List<PlanningLijnDO> getLijnen() {
        return lijnen;
    }


    public void setDossierDD(List dossierDD) {
        this.dossierDD = dossierDD;
    }

    public List getDossierDD() {
        return dossierDD;
    }

    public List getFaseDD() {
        return faseDD;
    }

    public void setFaseDD(List faseDD) {
        this.faseDD = faseDD;
    }

    public List getFaseDetailDD() {
        return faseDetailDD;
    }

    public void setFaseDetailDD(List faseDetailDD) {
        this.faseDetailDD = faseDetailDD;
    }

    public void setContractenDD(List raamcontractenDD) {
        this.raamcontractenDD = raamcontractenDD;
    }

    public List getContractenDD() {
        return raamcontractenDD;
    }

}