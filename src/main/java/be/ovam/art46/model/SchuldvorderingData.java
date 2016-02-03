package be.ovam.art46.model;

import java.io.Serializable;
import java.util.List;


public class SchuldvorderingData implements Serializable {

    private SchuldvorderingDO schuldvordering;
    private List<SchuldvorderingProjectDO> projecten;

    @SuppressWarnings("rawtypes")
    private List deelopdrachten;
    @SuppressWarnings("rawtypes")
    private List vastleggingen;

    private List<SchuldvorderingFactuurDO> facturen;

    @SuppressWarnings("rawtypes")
    private List briefcategorieLijst;


    public SchuldvorderingDO getSchuldvordering() {
        return schuldvordering;
    }

    public void setSchuldvordering(SchuldvorderingDO schuldvordering) {
        this.schuldvordering = schuldvordering;
    }


    public List<SchuldvorderingProjectDO> getProjecten() {
        return projecten;
    }

    public void setProjecten(List<SchuldvorderingProjectDO> projecten) {
        this.projecten = projecten;
    }


    @SuppressWarnings("rawtypes")
    public List getDeelopdrachten() {
        return deelopdrachten;
    }

    @SuppressWarnings("rawtypes")
    public void setDeelopdrachten(List deelopdrachten) {
        this.deelopdrachten = deelopdrachten;
    }

    @SuppressWarnings("rawtypes")
    public void setVastleggingen(List vastleggingen) {
        this.vastleggingen = vastleggingen;
    }

    @SuppressWarnings("rawtypes")
    public List getVastleggingen() {
        return vastleggingen;
    }

    public void setFacturen(List<SchuldvorderingFactuurDO> facturen) {
        this.facturen = facturen;
    }

    public List<SchuldvorderingFactuurDO> getFacturen() {
        return facturen;
    }

    public List getBriefcategorieLijst() {
        return briefcategorieLijst;
    }

    public void setBriefcategorieLijst(List briefcategorieLijst) {
        this.briefcategorieLijst = briefcategorieLijst;
    }


}