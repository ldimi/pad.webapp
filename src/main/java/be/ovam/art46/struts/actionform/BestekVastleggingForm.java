package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class BestekVastleggingForm extends ActionForm {
	
	private String[] vastleggingsIds;
	private String bestek_id;	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		vastleggingsIds = new String[0];
	}
	public String getBestek_id() {
		return bestek_id;
	}
	public void setBestek_id(String bestek_id) {
		this.bestek_id = bestek_id;
	}
	public String[] getVastleggingsIds() {
		return vastleggingsIds;
	}
	public void setVastleggingsIds(String[] vastleggingsIds) {
		this.vastleggingsIds = vastleggingsIds;
	}
	
	

}
