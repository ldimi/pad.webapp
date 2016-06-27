package be.ovam.art46.controller.adres;

import java.io.Serializable;

public class AdresZoekParams implements Serializable {

	private String naam_adres;
	private String naam_contact;
	private Integer type_id;
	private String gemeente;
	private String provincie;
	private String actief_s;
	
	// op 'J' te zetten als je contacten zoekt,
	//  default wordt er naar adressen gezocht
	private String zoek_contact = "N";

    public String getNaam_adres() {
        return naam_adres;
    }

    public void setNaam_adres(String naam_adres) {
        this.naam_adres = naam_adres;
    }

    public String getNaam_contact() {
        return naam_contact;
    }

    public void setNaam_contact(String naam_contact) {
        this.naam_contact = naam_contact;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getProvincie() {
        return provincie;
    }

    public void setProvincie(String provincie) {
        this.provincie = provincie;
    }

    public String getActief_s() {
        return actief_s;
    }

    public void setActief_s(String actief_s) {
        this.actief_s = actief_s;
    }

    public String getZoek_contact() {
        return zoek_contact;
    }

    public void setZoek_contact(String zoek_contact) {
        this.zoek_contact = zoek_contact;
    }

	
	
	
}
