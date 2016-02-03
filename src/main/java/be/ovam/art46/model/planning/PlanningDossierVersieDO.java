package be.ovam.art46.model.planning;

import java.io.Serializable;


public class PlanningDossierVersieDO implements Serializable {

    private static final long serialVersionUID = -4244544938688973656L;

    private Integer dossier_id;
    private Integer planning_versie;
    private String wijzig_user;


    public Integer getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(Integer dossier_id) {
        this.dossier_id = dossier_id;
    }

    public Integer getPlanning_versie() {
        return planning_versie;
    }

    public void setPlanning_versie(Integer planning_versie) {
        this.planning_versie = planning_versie;
    }

    public void setWijzig_user(String wijzig_user) {
        this.wijzig_user = wijzig_user;
    }

    public String getWijzig_user() {
        return wijzig_user;
    }

}
