package be.ovam.art46.struts.actionform;

public class Art46SelectForm extends SelectForm {
	
	private String artikelid="1";
	private String lijst_id = "1";
	private String prioriteit_id = "-1";
	
	public String getArtikelid() {
		return artikelid;
	}

	public void setArtikelid(String artikelid) {
		this.artikelid = artikelid;
	}

	public String getLijst_id() {
		return lijst_id;
	}

	public void setLijst_id(String lijst_id) {
		this.lijst_id = lijst_id;
	}

	public Art46SelectForm() {
		setForward("kadaster");
	}

	public String getPrioriteit_id() {
		return prioriteit_id;
	}

	public void setPrioriteit_id(String prioriteit_id) {
		this.prioriteit_id = prioriteit_id;
	}

}
