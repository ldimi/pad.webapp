package be.ovam.art46.struts.actionform;

import be.ovam.art46.util.DateFormatArt46;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("rawtypes")
public class BriefForm extends ValidatorForm {

    private static final long serialVersionUID = -6509357973423246615L;
    
    private FormFile file;
	private String brief_id;
	private String adres_id;
	private String adres_naam;
	private String adres_voornaam;
	private String adres_straat;
	private String adres_gemeente;	
	private String contact_id;
	private String contact_naam;	
	private String dossier_nr;	
	private String dossier_type_ivs;
	private String dossier_hdr_ivs;
	private String dossier_b_ivs;
	private String nis_id;
	private String brief_nr;
	private String inschrijf_d;
	private String betreft;
	private String in_aard_id;
	private String in_d;
	private String in_stuk_d;
	private String in_referte;
	private String in_bijlage;
	private String in_type_id;
	private String uit_aard_id;
	private String uit_d;
	private String uit_referte;
	private String uit_bijlage;
	private String uit_type_id;
	private String uit_type_id_vos;
	private String commentaar;
	private String ltst_wzg_user_id;
	private Timestamp ltst_wzg_d;
	private List contactList;
	private String dossier_id_boa;
	private String vordering_id;
	private String vordering_definitief_jn;
    private String volgnummer;
    private String auteur_id;
    private Integer dossier_id;
    private String dms_id;
    private String dms_filename;
    private String dms_folder;
    private String categorie_id = "20";	
    private String reactie_d;
    private String reactie_voor_d;
    private String parent_brief_id;
    private String forward;
    private String fromTree;
    private String qr_code;
    private String teprinten_jn;
    private String print_d;
    
    private List categorieen;
    
    private Integer bestek_id;
        
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {		
		super.reset(mapping, request);			
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		
		if (StringUtils.isBlank(adres_id)) {
			errors.add("adres_id", new ActionError("error.adres_id.required"));
		}
		
        if (StringUtils.isBlank(inschrijf_d)) {
            errors.add("inschrijf_d", new ActionError("error.inschrijf_d.required"));
        }
        
        if (!StringUtils.isBlank(in_aard_id)) {
            if (StringUtils.isBlank(in_d)) {
                errors.add("in_d", new ActionError("error.in_d.required"));
            }
        }


        if ( !isUploadCategorie() &&
			 (in_aard_id == null || "0".equals(in_aard_id) ) &&
			 (uit_aard_id == null || "0".equals(uit_aard_id) )   ) {
			errors.add("in_aard_id", new ActionError("error.invalid.aard"));
			errors.add("uit_aard_id", new ActionError("error.invalid.aard"));			
		}
		if ("X".equals(dossier_type_ivs) && (bestek_id == null || bestek_id.intValue() == 0)) {
			errors.add("bestek_id", new ActionError("error.bestek_id.required"));
		}
		if (!errors.isEmpty()) {
			return errors;
		}
		return null;
	}
	
	public boolean getIsUitAardEnabled() {
		if (getBrief_nr() == null || getBrief_nr().equals("")) {
			return true;
		} else {
			if (getUit_aard_id() == null || getUit_aard_id().equals("0")) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private boolean isUploadCategorie() {
		if (categorie_id == null || categorie_id.length() == 0) {
			return false;
		} else {
			return be.ovam.art46.service.BriefService.isUploadCategorie(Integer.valueOf(categorie_id));
		}
	}

	
	public String getVordering_id() {
		return vordering_id;
	}

	public void setVordering_id(String isSchuldvordering) {
		this.vordering_id = isSchuldvordering;
	}

	public String getDossier_id_boa() {
		return dossier_id_boa;
	}

	public void setDossier_id_boa(String dossier_id_boa) {
		this.dossier_id_boa = dossier_id_boa;
	}

	public BriefForm() {
		dossier_type_ivs = "B";
		contactList = new ArrayList();
		inschrijf_d = DateFormatArt46.formatDate(new Date(System.currentTimeMillis()));
	}

	public String getAdres_gemeente() {
		return adres_gemeente;
	}

	public void setAdres_gemeente(String adres_gemeente) {
		this.adres_gemeente = adres_gemeente;
	}

	public String getAdres_id() {
		return adres_id;
	}

	public void setAdres_id(String adres_id) {
		this.adres_id = adres_id;
	}

	public String getAdres_naam() {
		return adres_naam;
	}

	public void setAdres_naam(String adres_naam) {
		this.adres_naam = adres_naam;
	}

	public String getAdres_straat() {
		return adres_straat;
	}

	public void setAdres_straat(String adres_straat) {
		this.adres_straat = adres_straat;
	}

	public String getBetreft() {
		return betreft;
	}

	public void setBetreft(String betreft) {
		this.betreft = betreft;
	}

	public String getBrief_id() {
		return brief_id;
	}

	public void setBrief_id(String brief_id) {
		this.brief_id = brief_id;
	}

	public String getBrief_nr() {
		return brief_nr;
	}

	public void setBrief_nr(String brief_nr) {
		this.brief_nr = brief_nr;
	}

	public String getCommentaar() {
		return commentaar;
	}

	public void setCommentaar(String commentaar) {
		this.commentaar = commentaar;
	}

	public String getContact_id() {
	    if (contact_id == null) {
	        return contact_id; 
	    }
	    if ("0".equals(contact_id)) {
	        return null;
	    }
		return contact_id;
	}

	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}

	public String getContact_naam() {
		return contact_naam;
	}

	public void setContact_naam(String contact_naam) {
		this.contact_naam = contact_naam;
	}

    public List getContactList() {
		return contactList;
	}

	public void setContactList(List contactList) {
		this.contactList = contactList;
	}

	public String getDossier_b_ivs() {
		return dossier_b_ivs;
	}

	public void setDossier_b_ivs(String dossier_b_ivs) {
		this.dossier_b_ivs = dossier_b_ivs;
	}

	public String getDossier_hdr_ivs() {
		return dossier_hdr_ivs;
	}

	public void setDossier_hdr_ivs(String dossier_hdr_ivs) {
		this.dossier_hdr_ivs = dossier_hdr_ivs;
	}
	
	public String getDossier_nr() {
		return dossier_nr;
	}

	public void setDossier_nr(String dossier_nr) {
		this.dossier_nr = dossier_nr;
	}

	public String getDossier_type_ivs() {
		return dossier_type_ivs;
	}

	public void setDossier_type_ivs(String dossier_type_ivs) {
		this.dossier_type_ivs = dossier_type_ivs;
	}

	public String getIn_aard_id() {
		return in_aard_id;
	}

	public void setIn_aard_id(String in_aard_id) {
		this.in_aard_id = in_aard_id;
	}

	public String getIn_bijlage() {
		return in_bijlage;
	}

	public void setIn_bijlage(String in_bijlage) {
		this.in_bijlage = in_bijlage;
	}

	public String getIn_d() {
		return in_d;
	}

	public void setIn_d(String in_d) {
		this.in_d = in_d;
	}

	public String getIn_referte() {
		return in_referte;
	}

	public void setIn_referte(String in_referte) {
		this.in_referte = in_referte;
	}

	public String getIn_type_id() {
		return in_type_id;
	}

	public void setIn_type_id(String in_type_id) {
		this.in_type_id = in_type_id;
	}

	public String getInschrijf_d() {
		return inschrijf_d;
	}

	public void setInschrijf_d(String inschrijf_d) {
		this.inschrijf_d = inschrijf_d;
	}

	public Timestamp getLtst_wzg_d() {
		return ltst_wzg_d;
	}

	public void setLtst_wzg_d(Timestamp ltst_wzg_d) {
		this.ltst_wzg_d = ltst_wzg_d;
	}

	public String getLtst_wzg_user_id() {
		return ltst_wzg_user_id;
	}

	public void setLtst_wzg_user_id(String ltst_wzg_user_id) {
		this.ltst_wzg_user_id = ltst_wzg_user_id;
	}

	public String getUit_aard_id() {
		return uit_aard_id;
	}

	public void setUit_aard_id(String uit_aard_id) {
		this.uit_aard_id = uit_aard_id;
	}

	public String getUit_bijlage() {
		return uit_bijlage;
	}

	public void setUit_bijlage(String uit_bijlage) {
		this.uit_bijlage = uit_bijlage;
	}

	public String getUit_d() {
		return uit_d;
	}

	public void setUit_d(String uit_d) {
		this.uit_d = uit_d;
	}

	public String getUit_referte() {
		return uit_referte;
	}

	public void setUit_referte(String uit_referte) {
		this.uit_referte = uit_referte;
	}

	public String getUit_type_id() {
		return uit_type_id;
	}

	public void setUit_type_id(String uit_type_id) {
		this.uit_type_id = uit_type_id;
	}

	public String getIn_stuk_d() {
		return in_stuk_d;
	}

	public void setIn_stuk_d(String in_stuk_d) {
		this.in_stuk_d = in_stuk_d;
	}

	public String getNis_id() {
		return nis_id;
	}

	public void setNis_id(String nis_id) {
		this.nis_id = nis_id;
	}

    public String getVolgnummer()
    {
        return volgnummer;
    }

    public void setVolgnummer(String volgnummer)
    {
        this.volgnummer = volgnummer;
    }

	public String getAdres_voornaam() {
		return adres_voornaam;
	}

	public void setAdres_voornaam(String adres_voornaam) {
		this.adres_voornaam = adres_voornaam;
	}

	public String getAuteur_id() {
		return auteur_id;
	}

	public void setAuteur_id(String auteur_id) {
		this.auteur_id = auteur_id;
	}

	public Integer getDossier_id() {
		return dossier_id;
	}

	public void setDossier_id(Integer dossier_id) {
		this.dossier_id = dossier_id;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getCategorie_id() {
		return categorie_id;
	}
	public void setCategorie_id(String categorie_id) {
		this.categorie_id = categorie_id;
	}

	public String getUit_type_id_vos() {
		return uit_type_id_vos;
	}
	public void setUit_type_id_vos(String uit_type_id_vos) {
		this.uit_type_id_vos = uit_type_id_vos;
	}

	public String getReactie_d() {
		return reactie_d;
	}

	public void setReactie_d(String reactie_d) {
		this.reactie_d = reactie_d;
	}

	public String getReactie_voor_d() {
		return reactie_voor_d;
	}

	public void setReactie_voor_d(String reactie_voor_d) {
		this.reactie_voor_d = reactie_voor_d;
	}

	public String getDms_id() {
		return dms_id;
	}

	public void setDms_id(String dms_id) {
		this.dms_id = dms_id;
	}

	public String getDms_filename() {
		return dms_filename;
	}

	public void setDms_filename(String dms_filename) {
		this.dms_filename = dms_filename;
	}

	public String getDms_folder() {
		return dms_folder;
	}

	public void setDms_folder(String dms_folder) {
		this.dms_folder = dms_folder;
	}

	public String getParent_brief_id() {
		return parent_brief_id;
	}

	public void setParent_brief_id(String parentBriefId) {
		parent_brief_id = parentBriefId;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getFromTree() {
		return fromTree;
	}

	public void setFromTree(String fromTree) {
		this.fromTree = fromTree;
	}

	public Integer getBestek_id() {
		return bestek_id;
	}

	public void setBestek_id(Integer bestekId) {
		bestek_id = bestekId;
	}

	public List getCategorieen() {
		return categorieen;
	}

	public void setCategorieen(List categorieen) {
		this.categorieen = categorieen;
	}

    public String getVordering_definitief_jn() {
        return vordering_definitief_jn;
    }

    public void setVordering_definitief_jn(String vordering_definitief_jn) {
        this.vordering_definitief_jn = vordering_definitief_jn;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getTeprinten_jn() {
        if (teprinten_jn == null) {
            return "N";
        }
        return teprinten_jn;
    }

    public void setTeprinten_jn(String teprinten_jn) {
        this.teprinten_jn = teprinten_jn;
    }

    public String getPrint_d() {
        return print_d;
    }

    public void setPrint_d(String print_d) {
        this.print_d = print_d;
    }
    
    
}
