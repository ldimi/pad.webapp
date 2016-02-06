package be.ovam.art46.struts.actionform;

import be.ovam.pad.model.Dossier;
import be.ovam.art46.struts.actionform.base.CrudActionForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;

public class DossierArt46Form extends CrudActionForm {

	private static final long serialVersionUID = 50460355623282696L;
	
	private String dossier_nr;
	private String dossier_id_boa;
	private String doss_hdr_id;
	private String dossier_b;
	private String dossier_type;
	private String adres;
	private String nis_id;
	private String deelgemeente;
	private String postcode;
	private String land;
	private String afsluit_d;
	private String commentaar;
	private String dossier_fase_id;
	private String bestek_nr;
	private String aanpak_onderzocht_s;
	private String aanpak_onderzocht_l;
	private String id;
	private String financiele_info;
	private String onderzoek_id;

	private String uit_type_id_vos;
	private String bestek_id;
	private String datum_van;
	private String datum_tot;
	
	private String conform_bbo_d;
	private String conform_bsp_d;
	private String eindverklaring_d;
	private String commentaar_bodem;
	private String sap_project_nr;
	private String wbs_ivs_nr;
	private String wbs_migratie;
	private String programma_code;
    private String rechtsgrond_code;
    private String doelgroep_type_id;


	
	private Boolean disabled;
    

    @Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if (errors == null) {
			errors = new ActionErrors();
		}
		if (mapping.getPath().equals("/dossierdetailsafvalsluiten")
				|| mapping.getPath().equals("/dossierdetailsandersluiten")
				|| mapping.getPath().equals("/dossierdetailssluiten")) {
			if (afsluit_d == null || afsluit_d.length() == 0) {
				errors.add("afsluit_d", new ActionMessage(
						"error.required.field", "Afsluitingsdatum"));
			}
		}
		if ("/dossierivs".equals(mapping.getPath())
				&& "0".equals(request.getParameter("selected"))) {
			if ("1".equals(aanpak_onderzocht_s)
					&& (aanpak_onderzocht_l == null || aanpak_onderzocht_l
							.length() == 0)) {
				errors.add("aanpak_onderzocht_l", new ActionMessage(
						"error.required.field",
						"Commentaar Geintegreerde oplossing"));
			}

			if (dossier_b == null || dossier_b.length() == 0) {
				if (       (afsluit_d != null && afsluit_d.length() > 0)
						|| (conform_bsp_d != null && conform_bsp_d.length() > 0)
						|| (eindverklaring_d != null && eindverklaring_d
								.length() > 0)
						|| (commentaar != null && commentaar.length() > 0)
						|| (aanpak_onderzocht_s != null && aanpak_onderzocht_s
								.length() > 0)
						|| (aanpak_onderzocht_l != null && aanpak_onderzocht_l
								.length() > 0)) {

					errors.add("dossier_b", new ActionMessage(
							"error.required.field", "Titel"));
				}
			}
		}
		return errors;
	}

    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		if ("/dossierivs".equals(mapping.getPath())) {
			if ("0".equals(request.getParameter("selected"))) {
				aanpak_onderzocht_s = null;
			}
		}
	}
    
	public void clearForm() {
		id = null;
		dossier_nr = null;
		dossier_id_boa = null;
		doss_hdr_id = null;
		dossier_b = null;
		dossier_type = null;
		adres = null;
		nis_id = null;
		deelgemeente = null;
		postcode = null;
		land = null;
		afsluit_d = null;
		commentaar = null;
		dossier_fase_id = null;
		aanpak_onderzocht_l = null;
		aanpak_onderzocht_s = null;
		conform_bbo_d = null;
		conform_bsp_d = null;
		eindverklaring_d = null;
		commentaar_bodem = null;
		sap_project_nr = null;
		wbs_ivs_nr = null;
		wbs_migratie = null;
		programma_code = null;
		rechtsgrond_code = null;
	}

    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommentaar() {
		return commentaar;
	}

	public void setCommentaar(String string) {
		commentaar = emptyToNull(string);
	}

	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}

	public void setDoss_hdr_id(String string) {
		doss_hdr_id = emptyToNull(string);
	}

	public String getDossier_b() {
		return dossier_b;
	}

	public void setDossier_b(String string) {
		dossier_b = emptyToNull(string);
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getAfsluit_d() {
		return afsluit_d;
	}

	public void setAfsluit_d(String afsluit_d) {
		this.afsluit_d = afsluit_d;
	}

	public String getDeelgemeente() {
		return deelgemeente;
	}

	public void setDeelgemeente(String deelgemeente) {
		this.deelgemeente = deelgemeente;
	}

	public String getDossier_nr() {
		return dossier_nr;
	}

	public void setDossier_nr(String dossier_nr) {
		this.dossier_nr = dossier_nr;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getNis_id() {
		return nis_id;
	}

	public void setNis_id(String nis_id) {
		this.nis_id = nis_id;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getDossier_type() {
		return dossier_type;
	}

	public void setDossier_type(String dossier_type) {
		this.dossier_type = dossier_type;
	}

	public String getDossier_fase_id() {
		return dossier_fase_id;
	}

	public void setDossier_fase_id(String dossier_fase_id) {
		this.dossier_fase_id = dossier_fase_id;
	}

	public String getDossier_id_boa() {
		return dossier_id_boa;
	}

	public void setDossier_id_boa(String dossier_id_boa) {
		this.dossier_id_boa = dossier_id_boa;
	}

	public String getBestek_nr() {
		return bestek_nr;
	}

	public void setBestek_nr(String bestek_nr) {
		this.bestek_nr = bestek_nr;
	}

	public Class getObjectClass() {
		return Dossier.class;
	}

	public Serializable getCrudId() {
		return dossier_nr;
	}

	public String getAanpak_onderzocht_l() {
		return aanpak_onderzocht_l;
	}

	public void setAanpak_onderzocht_l(String aanpak_onderzocht_l) {
		this.aanpak_onderzocht_l = emptyToNull(aanpak_onderzocht_l);
	}

	public String getAanpak_onderzocht_s() {
		return aanpak_onderzocht_s;
	}

	public void setAanpak_onderzocht_s(String aanpak_onderzocht_s) {
		this.aanpak_onderzocht_s = aanpak_onderzocht_s;
	}

	public String getFinanciele_info() {
		return financiele_info;
	}

	public void setFinanciele_info(String financiele_info) {
		this.financiele_info = financiele_info;
	}

	public String getOnderzoek_id() {
		return onderzoek_id;
	}

	public void setOnderzoek_id(String onderzoekId) {
		onderzoek_id = onderzoekId;
	}

	public String getUit_type_id_vos() {
		return uit_type_id_vos;
	}

	public void setUit_type_id_vos(String uitTypeIdVos) {
		uit_type_id_vos = uitTypeIdVos;
	}

	public String getBestek_id() {
		return bestek_id;
	}

	public void setBestek_id(String bestekId) {
		bestek_id = bestekId;
	}

	public String getDatum_van() {
		return datum_van;
	}

	public void setDatum_van(String datumVan) {
		datum_van = datumVan;
	}

	public String getDatum_tot() {
		return datum_tot;
	}

	public void setDatum_tot(String datumTot) {
		datum_tot = datumTot;
	}

	public String getConform_bbo_d() {
		return conform_bbo_d;
	}

	public void setConform_bbo_d(String conform_bbo_d) {
		this.conform_bbo_d = conform_bbo_d;
	}

	public String getConform_bsp_d() {
		return conform_bsp_d;
	}

	public void setConform_bsp_d(String conform_bsp_d) {
		this.conform_bsp_d = conform_bsp_d;
	}

	public String getEindverklaring_d() {
		return eindverklaring_d;
	}

	public void setEindverklaring_d(String eindverklaring_d) {
		this.eindverklaring_d = eindverklaring_d;
	}

	public String getCommentaar_bodem() {
		return commentaar_bodem;
	}

	public void setCommentaar_bodem(String commentaar_bodem) {
		this.commentaar_bodem = commentaar_bodem;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	private String emptyToNull(String string) {
		if (string != null && string.trim().length() == 0) {
			return null;
		} else {
			return string;
		}

	}

	public String getSap_project_nr() {
		return sap_project_nr;
	}

	public void setSap_project_nr(String sap_project_nr) {
		this.sap_project_nr = sap_project_nr;
	}

	public String getWbs_ivs_nr() {
		return wbs_ivs_nr;
	}

	public void setWbs_ivs_nr(String wbs_ivs_nr) {
		this.wbs_ivs_nr = wbs_ivs_nr;
	}

	public String getWbs_migratie() {
		return wbs_migratie;
	}

	public void setWbs_migratie(String wbs_migratie) {
		this.wbs_migratie = wbs_migratie;
	}

	public String getProgramma_code() {
		return programma_code;
	}

	public void setProgramma_code(String programma_code) {
		this.programma_code = programma_code;
	}

    public String getRechtsgrond_code() {
        return rechtsgrond_code;
    }

    public void setRechtsgrond_code(String rechtsgrond_code) {
        this.rechtsgrond_code = rechtsgrond_code;
    }

    public String getDoelgroep_type_id() {
        return doelgroep_type_id;
    }

    public void setDoelgroep_type_id(String doelgroep_type_id) {
        this.doelgroep_type_id = doelgroep_type_id;
    }

    @Override
    public void clear() {
        // no action
    }


}
