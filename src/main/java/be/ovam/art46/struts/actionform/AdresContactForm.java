package be.ovam.art46.struts.actionform;

import be.ovam.art46.struts.actionform.base.CrudActionForm;
import be.ovam.pad.model.AdresContact;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;

public class AdresContactForm extends CrudActionForm {

	private String contact_id;
	private String adres_id;
	private String naam;	
	private String voornaam;
	private String tel;
	private String fax;
	private String gsm;
	private String email;
	private String functie;
	private String commentaar;
	private String stop_s;
	private String referentie_postcodes;
	
	public void clear() {}
	
	public String getAdres_id() {
		return adres_id;
	}
	public void setAdres_id(String adres_id) {
		this.adres_id = adres_id;
	}
	public String getCommentaar() {
		return commentaar;
	}
	public void setCommentaar(String commentaar) {
		this.commentaar = commentaar;
	}
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getFunctie() {
		return functie;
	}
	public void setFunctie(String functie) {
		this.functie = functie;
	}
	public String getGsm() {
		return gsm;
	}
	public void setGsm(String gsm) {
		this.gsm = gsm;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		stop_s = "0";
	}
	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public String getStop_s() {
		return stop_s;
	}
	public void setStop_s(String stop_s) {
		this.stop_s = stop_s;
	}
	public Class getObjectClass() {
		return AdresContact.class;
	}
	public Serializable getCrudId() {
		return Integer.valueOf(contact_id);
	}
	public String getReferentie_postcodes() {
		return referentie_postcodes;
	}

	public void setReferentie_postcodes(String referentie_postcodes) {
		this.referentie_postcodes = referentie_postcodes;
	}
	

}
