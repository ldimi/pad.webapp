package be.ovam.art46.struts.actionform.schuldvordering;

import org.apache.struts.action.ActionForm;

public class SchuldvorderingSelectForm extends ActionForm {
	
	private static final long serialVersionUID = 8617209757418230969L;
	
	private String forward = "lijstschuldvorderingenietbetaald";
	private String programma_code;
	private String doss_hdr_id;
	
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	
	public void setProgramma_code(String programma_code) {
		this.programma_code = programma_code;
	}
	public String getProgramma_code() {
		return programma_code;
	}
	
	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}
	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}
	
	
	
}
