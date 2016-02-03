package be.ovam.art46.controller.planning.jaar;

import java.io.Serializable;


public class ParamsDO implements Serializable {
	
	
	private static final long serialVersionUID = -9160368941935359927L;
	
	private Integer jaar;
	private String doss_hdr_id;
	private String programma_code;
	private Integer markering_id;
	
	
	public Integer getJaar() {
		return jaar;
	}
	public void setJaar(Integer jaar) {
		this.jaar = jaar;
	}

	
	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}
	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}
	
	
	public String getProgramma_code() {
		return programma_code;
	}
	public void setProgramma_code(String programma_code) {
		this.programma_code = programma_code;
	}
	
	
	public void setMarkering_id(Integer markering_id) {
		this.markering_id = markering_id;
	}
	public Integer getMarkering_id() {
		return markering_id;
	}
		
}
