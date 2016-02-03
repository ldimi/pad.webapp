package be.ovam.art46.controller.dossier;


import java.io.Serializable;
import java.util.Date;

public class BriefFilterParamsDO implements Serializable{

	private static final long serialVersionUID = 50460355623282696L;
	
	private Integer uit_type_id_vos;
	private Integer bestek_id;
	private Date datum_van;
	private Date datum_tot;

    
    public Integer getUit_type_id_vos() {
        return uit_type_id_vos;
    }

    public void setUit_type_id_vos(Integer uit_type_id_vos) {
        this.uit_type_id_vos = uit_type_id_vos;
    }

    public Integer getBestek_id() {
        return bestek_id;
    }

    public void setBestek_id(Integer bestek_id) {
        this.bestek_id = bestek_id;
    }

    public Date getDatum_van() {
        return datum_van;
    }

    public void setDatum_van(Date datum_van) {
        this.datum_van = datum_van;
    }

    public Date getDatum_tot() {
        return datum_tot;
    }

    public void setDatum_tot(Date datum_tot) {
        this.datum_tot = datum_tot;
    }
	


}
