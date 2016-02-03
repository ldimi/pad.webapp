package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.BudgetairArtikel;
import be.ovam.art46.struts.actionform.base.CrudActionForm;

import java.io.Serializable;

public class BudgetairArtikelForm extends CrudActionForm {
	
	private String artikel_id;
	private String artikel_b;
	
	public void clear() {}
	
	public String getArtikel_b() {
		return artikel_b;
	}
	public void setArtikel_b(String artikel_b) {
		this.artikel_b = artikel_b;
	}
	public String getArtikel_id() {
		return artikel_id;
	}
	public void setArtikel_id(String artikel_id) {
		this.artikel_id = artikel_id;
	}
	public Class getObjectClass() {
		return BudgetairArtikel.class;
	}
	public Serializable getCrudId() {
		return Integer.valueOf(artikel_id);
	}

}
