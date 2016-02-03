package be.ovam.art46.struts.actionform;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class Art46BaseForm extends ValidatorForm {		
	
	private static final long serialVersionUID = 2415473698498841137L;
	
	private String commentaar;	
	private String[] dossierkadasters;
	private String goedkeuring_d;
	private String goedkeuring_s;
	private String publicatie_d;
	private String kadaster_afd_id;
	
	private String sum;	
	
	protected static Log log = LogFactory.getLog(Art46BaseForm.class);
	protected transient HttpSession session;
	
	public String getKadaster_afd_id() {
		return kadaster_afd_id;
	}
	public void setKadaster_afd_id(String kadaster_afd_id) {
		this.kadaster_afd_id = kadaster_afd_id;
	}

	
	
	/**
	 * @return
	 */
	public String getArtikelid() {
		return (String) session.getAttribute("artikelid");
	}

	public String getPrioriteit_id() {
		//log.debug("Getting artikelid from session: " + (String) session.getAttribute("artikelid"));
		return (String) session.getAttribute("prioriteit_id");
	}
	/**
	 * @return
	 */
	public String getCommentaar() {
		return commentaar;
	}

	/**
	 * @return
	 */
	public String getDossier_id() {
		//log.debug("Getting dossier_id from session: " + (String) session.getAttribute("dossier_id"));
		return (String) session.getAttribute("dossier_id");
	}

	/**
	 * @return
	 */
	public String[] getDossierkadasters() {
		return dossierkadasters;
	}

	/**
	 * @return
	 */
	public String getGoedkeuring_d() {
		return goedkeuring_d;
	}

	/**
	 * @return
	 */
	public String getGoedkeuring_s() {
		return goedkeuring_s;
	}

	/**
	 * @return
	 */
	public String getLijst_id() {		
		//log.debug("Getting lijst_id from session: " + (String) session.getAttribute("lijst_id"));
		return (String) session.getAttribute("lijst_id");
	}

	/**
	 * @return
	 */
	public String getKadaster_id() {
		//log.debug("Getting kadaster_id from session: " + (String) session.getAttribute("kadaster_id"));
		return (String) session.getAttribute("kadaster_id");		
	}

	/**
	 * @return
	 */
	public String getNextpage() {
		//log.debug("Getting nextpage from session: " + (String) session.getAttribute("nextpage"));
		return (String) session.getAttribute("nextpage");		
	}
	
	/**
	 * @return
	 */
	public String getNexterrorpage() {
		//log.debug("Getting nexterrorpage from session: " + (String) session.getAttribute("nexterrorpage"));
		return (String) session.getAttribute("nexterrorpage");		
	}
	
	/**
	 * @return
	 */
	public String getPublicatie_d() {
		return publicatie_d;
	}

	/**
	 * @return
	 */
	public String getSelected() {
		//log.debug("Getting selected from session: " + (String) session.getAttribute("selected"));
		return (String) session.getAttribute("selected");
	}

	protected String listKadasterdossier() {
		String res = "";
		if (dossierkadasters != null) {
			for (int t=0; t<dossierkadasters.length; t++) {
				res += dossierkadasters[t] + " <> ";
			}
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		session = request.getSession();		
		commentaar = null;
		goedkeuring_d = null;
		publicatie_d = null;	
		sum = null;	
		dossierkadasters = new String[0];			
	}

	/**
	 * @param string
	 */
	public void setArtikelid(String string) {		
		session.setAttribute("artikelid", string);
		log.debug("artikelid in session set to " + string);
	}

	public void setPrioriteit_id(String string) {		
		session.setAttribute("prioriteit_id", string);
		log.debug("prioriteit_id in session set to " + string);
	}
	
	/**
	 * @param string
	 */
	public void setCommentaar(String string) {
		commentaar = string;
	}

	/**
	 * @param string
	 */
	public void setDossier_id(String string) {		
		session.setAttribute("dossier_id", string);
		log.debug("dossier_id in session set to " + string);
	}

	/**
	 * @param strings
	 */
	public void setDossierkadasters(String[] strings) {
		dossierkadasters = strings;		
		log.debug("Kadasterdossiers set to: " + listKadasterdossier());
	}
	
	public void setDossierkadasters(ArrayList strings) {
		dossierkadasters = new String[strings.size()];
		for (int t=0; t<strings.size(); t++) {
			dossierkadasters[t] = (String) strings.get(t);
		}	
		log.debug("Kadasterdossiers set to: " + listKadasterdossier());
	}

	/**
	 * @param string
	 */
	public void setGoedkeuring_d(String string) {
		goedkeuring_d = string;
	}

	/**
	 * @param string
	 */
	public void setGoedkeuring_s(String string) {
		goedkeuring_s = string;
	}

	/**
	 * @param string
	 */
	public void setLijst_id(String string) {
		session.setAttribute("lijst_id", string);
		log.debug("lijst_id in session set to " + string);
	}

	/**
	 * @param string
	 */
	public void setKadaster_id(String string) {		
		session.setAttribute("kadaster_id", string);
		log.debug("kadaster_id in session set to " + string);
	}

	/**
	 * @param string
	 */
	public void setNextpage(String string) {		
		session.setAttribute("nextpage", string);
		log.debug("nextpage in session set to " + string);
	}
	
	/**
	 * @param string
	 */
	public void setNexterrorpage(String string) {		
		session.setAttribute("nexterrorpage", string);
		log.debug("nexterrorpage in session set to " + string);
	}

	/**
	 * @param string
	 */
	public void setPublicatie_d(String string) {
		publicatie_d = string;
	}

	/**
	 * @param string
	 */
	public void setSelected(String string) {		
		session.setAttribute("selected", string);
		log.debug("selected in session set to " + string);
	}

	/**
	 * @return
	 */
	public String getSum() {
		return sum;
	}

	/**
	 * @param string
	 */
	public void setSum(String string) {
		sum = string;
	}
	
	public String getForward() {
		String view_type = (String) session.getAttribute("forward");
		if (view_type == null) {
			return "kadaster";
		}
		else return view_type;
	}
	
	public void setForward(String view_type) {		
		session.setAttribute("forward", view_type);
		
	}

}
