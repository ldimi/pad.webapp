package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.DossierAfspraak;
import be.ovam.art46.struts.actionform.base.CrudActionForm;
import be.ovam.art46.util.DateFormatArt46;

import java.io.Serializable;
import java.util.Date;

//import be.impact.framework.converters.DateConverter;

public class DossierAfspraakForm extends CrudActionForm {
	
	private Integer id;	
	private String dossier_id;
	//private String datum = DateConverter.dateFormat.format(new Date());
	private String datum = DateFormatArt46.formatDate(new Date());
	private String omschrijving;

	@Override
	public void clear() {		

	}

	@Override
	public Serializable getCrudId() {		
		return getId();
	}

	@Override
	public Class getObjectClass() {		
		return DossierAfspraak.class;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDossier_id() {
		return dossier_id;
	}

	public void setDossier_id(String dossierId) {
		dossier_id = dossierId;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

}
