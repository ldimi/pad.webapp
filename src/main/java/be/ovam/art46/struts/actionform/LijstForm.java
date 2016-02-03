package be.ovam.art46.struts.actionform;

import be.ovam.art46.model.Lijst;
import be.ovam.art46.struts.actionform.base.CrudActionForm;

import java.io.Serializable;

public class LijstForm extends CrudActionForm {

	private Integer lijst_id;
	private String programma_code;
	private String jaar;
	private String lijst_b;
	private String afgesloten_s;
	
	
	public void clear() {}
	
	public Class getObjectClass() {
		return Lijst.class;
	}

	public Serializable getCrudId() {
		return lijst_id;
	}

	
	
	
	public Integer getLijst_id() {
		return lijst_id;
	}

	public void setLijst_id(Integer lijst_id) {
		this.lijst_id = lijst_id;
	}

	public String getProgramma_code() {
		return programma_code;
	}

	public void setProgramma_code(String programma_code) {
		this.programma_code = programma_code;
	}

	public String getJaar() {
		return jaar;
	}

	public void setJaar(String jaar) {
		this.jaar = jaar;
	}

	public String getLijst_b() {
		return lijst_b;
	}

	public void setLijst_b(String lijst_b) {
		this.lijst_b = lijst_b;
	}

	public String getAfgesloten_s() {
		return afgesloten_s;
	}

	public void setAfgesloten_s(String afgesloten_s) {
		this.afgesloten_s = afgesloten_s;
	}

}
