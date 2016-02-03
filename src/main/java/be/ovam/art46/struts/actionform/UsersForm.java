package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;

public class UsersForm extends ActionForm {
	
	private String[] adminArt46;
	private String[] adminBOA;
	private String[] adminIVS;
	private String[] adminJD;
	private String[] adminBoek;
	private String[] user_id;
	
	public String[] getAdminArt46() {
		return adminArt46;
	}
	public void setAdminArt46(String[] adminArt46) {
		this.adminArt46 = adminArt46;
	}
	public String[] getAdminBOA() {
		return adminBOA;
	}
	public void setAdminBOA(String[] adminBOA) {
		this.adminBOA = adminBOA;
	}
	public String[] getAdminIVS() {
		return adminIVS;
	}
	public void setAdminIVS(String[] adminIVS) {
		this.adminIVS = adminIVS;
	}
	public String[] getAdminJD() {
		return adminJD;
	}
	public void setAdminJD(String[] adminJD) {
		this.adminJD = adminJD;
	}
	public String[] getUser_id() {
		return user_id;
	}
	public void setUser_id(String[] user_id) {
		this.user_id = user_id;
	}
	public String[] getAdminBoek() {
		return adminBoek;
	}
	public void setAdminBoek(String[] adminBoek) {
		this.adminBoek = adminBoek;
	}
	
	

}
