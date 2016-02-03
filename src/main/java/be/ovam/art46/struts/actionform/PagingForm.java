package be.ovam.art46.struts.actionform;

import org.apache.struts.validator.ValidatorForm;

public class PagingForm extends ValidatorForm {		
	
	private String pagesize = "50";
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}	
	
	public int getPagesizeInt() {
		if (pagesize == null || pagesize.length()==0) {
			return 0;
		}
		return Integer.parseInt(pagesize);
	}
}
