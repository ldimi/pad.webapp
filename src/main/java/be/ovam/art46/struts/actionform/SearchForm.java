package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;

public class SearchForm extends ActionForm {
	
	private String forwardURL;
	private String searchFlag;
	private String searchForward;
	private String searchId;
	
	public String getSearchId() {
		return searchId;
	}
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	public String getForwardURL() {
		return forwardURL;
	}
	public void setForwardURL(String forwardURL) {
		this.forwardURL = forwardURL;
	}
	public String getSearchFlag() {
		return searchFlag;
	}
	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}
	public String getSearchForward() {
		return searchForward;
	}
	public void setSearchForward(String searchForward) {
		this.searchForward = searchForward;
	}
	

}
