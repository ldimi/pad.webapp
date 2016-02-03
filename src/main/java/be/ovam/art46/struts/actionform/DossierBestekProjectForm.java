package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class DossierBestekProjectForm extends ActionForm {
	
	private String doss_hdr_id;
	private String dossier_type;
	private String fase_id;
	private String bestek_jaar;
	private String vastlegging_van_datum;
	private String vastlegging_tot_datum;
	private String artikel_id;
	private String reset;
	private String dienst_id;
	private String programma_code;
	private String inclusiefAfgesloten = "1";
	
	private String totaal_bedrag;
	
	
	private String c1 = "1";
	private String c2 = "1";
	private String c3 = "1";
	private String c4 = "1";
	private String c5 = "1";
	private String c6 = "1";
	private String c7 = "1";
	private String c8 = "1";
	private String c9 = "1";
	private String c10 = "1";
	private String c11 = "1";
	private String c12 = "1";
	private String c13 = "1";
	private String c14 = "1";
	private String c15 = "1";
	private String c16 = "1";
	private String c_programma_code = "1";
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		if (request.getParameter("resetCheckbox") != null) {
			inclusiefAfgesloten = null;
		}		
		if ("/dossierbestekprojectcolumn".equals(mapping.getPath())) {
			if ("1".equals(request.getParameter("reset"))) {		
				c1 = "0";
				c2 = "0";
				c3 = "0";
				c4 = "0";
				c5 = "0";
				c6 = "0";
				c7 = "0";
				c8 = "0";
				c9 = "0";
				c10 = "0";
				c11 = "0";
				c12 = "0";
				c13 = "0";
				c14 = "0";
				c15 = "0";
				c16 = "0";
				c_programma_code = "0";
			}
		}
		if ("/dossierbestekprojectcolumnall".equals(mapping.getPath())) {
			c1 = "1";
			c2 = "1";
			c3 = "1";
			c4 = "1";
			c5 = "1";
			c6 = "1";
			c7 = "1";
			c8 = "1";
			c9 = "1";
			c10 = "1";
			c11 = "1";
			c12 = "1";
			c13 = "1";
			c14 = "1";
			c15 = "1";
			c16 = "1";
			c_programma_code = "1";
		}		
	}
	
	public String getC1() {
		return c1;
	}
	public void setC1(String c1) {
		this.c1 = c1;
	}
	public String getC2() {
		return c2;
	}
	public void setC2(String c2) {
		this.c2 = c2;
	}
	public String getC3() {
		return c3;
	}
	public void setC3(String c3) {
		this.c3 = c3;
	}
	public String getBestek_jaar() {
		return bestek_jaar;
	}
	public void setBestek_jaar(String bestek_jaar) {
		this.bestek_jaar = bestek_jaar;
	}
	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}
	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}
	public String getDossier_type() {
		return dossier_type;
	}
	public void setDossier_type(String dossier_type) {
		this.dossier_type = dossier_type;
	}
	public String getFase_id() {
		return fase_id;
	}
	public void setFase_id(String fase_id) {
		this.fase_id = fase_id;
	}
	public String getC10() {
		return c10;
	}

	public void setC10(String c10) {
		this.c10 = c10;
	}

	public String getC11() {
		return c11;
	}

	public void setC11(String c11) {
		this.c11 = c11;
	}

	public String getC12() {
		return c12;
	}

	public void setC12(String c12) {
		this.c12 = c12;
	}

	public String getC13() {
		return c13;
	}

	public void setC13(String c13) {
		this.c13 = c13;
	}

	public String getC14() {
		return c14;
	}

	public void setC14(String c14) {
		this.c14 = c14;
	}

	public String getC15() {
		return c15;
	}

	public void setC15(String c15) {
		this.c15 = c15;
	}

	public String getC4() {
		return c4;
	}

	public void setC4(String c4) {
		this.c4 = c4;
	}

	public String getC5() {
		return c5;
	}

	public void setC5(String c5) {
		this.c5 = c5;
	}

	public String getC6() {
		return c6;
	}

	public void setC6(String c6) {
		this.c6 = c6;
	}

	public String getC7() {
		return c7;
	}

	public void setC7(String c7) {
		this.c7 = c7;
	}

	public String getC8() {
		return c8;
	}

	public void setC8(String c8) {
		this.c8 = c8;
	}

	public String getC9() {
		return c9;
	}

	public void setC9(String c9) {
		this.c9 = c9;
	}

	public String getC16() {
		return c16;
	}

	public void setC16(String c16) {
		this.c16 = c16;
	}

	public void setC_programma_code(String c_programma_code) {
		this.c_programma_code = c_programma_code;
	}

	public String getC_programma_code() {
		return c_programma_code;
	}


	public String getArtikel_id() {
		return artikel_id;
	}

	public void setArtikel_id(String artikel_id) {
		this.artikel_id = artikel_id;
	}

	public String getReset() {
		return reset;
	}

	public void setReset(String reset) {
		this.reset = reset;
	}

	public String getVastlegging_tot_datum() {
		return vastlegging_tot_datum;
	}

	public void setVastlegging_tot_datum(String vastlegging_tot_datum) {
		this.vastlegging_tot_datum = vastlegging_tot_datum;
	}

	public String getVastlegging_van_datum() {
		return vastlegging_van_datum;
	}

	public void setVastlegging_van_datum(String vastlegging_van_datum) {
		this.vastlegging_van_datum = vastlegging_van_datum;
	}

	public String getProgramma_code() {
		return programma_code;
	}

	public void setProgramma_code(String programma_code) {
		this.programma_code = programma_code;
	}

	public String getInclusiefAfgesloten() {
		return inclusiefAfgesloten;
	}

	public void setInclusiefAfgesloten(String inclusiefAfgesloten) {
		this.inclusiefAfgesloten = inclusiefAfgesloten;
	}

	public void setDienst_id(String dienst_id) {
		this.dienst_id = dienst_id;
	}

	public String getDienst_id() {
		return dienst_id;
	}

	public void setTotaal_bedrag(String totaal_bedrag) {
		this.totaal_bedrag = totaal_bedrag;
	}

	public String getTotaal_bedrag() {
		return totaal_bedrag;
	}

	
}
