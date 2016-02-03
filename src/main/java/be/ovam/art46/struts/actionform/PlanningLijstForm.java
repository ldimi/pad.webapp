package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class PlanningLijstForm extends ActionForm {
	private String prioriteit_id;
	private String[] fase_id;
	private String[] programma_code;
	private String jaar_id;
	private String maand_id;
	private String doss_hdr_id;
	private String type_id;
	private String datumGoedkeuring;
	private String[] dossierFaseJaartal;
	private String[] dossierFaseMaand;
	private String ivsDossierType;
	private Integer[] raming_ids;
	private String datumVan;
	private String datumTot;

	public String[] getDossierFaseMaand() {
		return dossierFaseMaand;
	}

	public void setDossierFaseMaand(String[] dossierFaseMaand) {
		this.dossierFaseMaand = dossierFaseMaand;
	}

	/**
	 * @return Returns the dossierFaseJaartal.
	 */
	public String[] getDossierFaseJaartal() {
		return dossierFaseJaartal;
	}

	/**
	 * @param dossierFaseJaartal
	 *            The dossierFaseJaartal to set.
	 */
	public void setDossierFaseJaartal(String[] dossierFaseJaartal) {
		this.dossierFaseJaartal = dossierFaseJaartal;
	}

	/**
	 * @return Returns the fase_id.
	 */
	public String[] getFase_id() {
		return fase_id;
	}

	/**
	 * @param fase_id
	 *            The fase_id to set.
	 */
	public void setFase_id(String[] fase_id) {
		this.fase_id = fase_id;
	}

	public void setProgramma_code(String[] programma_code) {
		this.programma_code = programma_code;
	}

	public String[] getProgramma_code() {
		return programma_code;
	}

	/**
	 * @return Returns the jaar_id.
	 */
	public String getJaar_id() {
		return jaar_id;
	}

	/**
	 * @param jaar_id
	 *            The jaar_id to set.
	 */
	public void setJaar_id(String jaartal) {
		this.jaar_id = jaartal;
	}

	/**
	 * @return Returns the prioriteit_id.
	 */
	public String getPrioriteit_id() {
		return prioriteit_id;
	}

	/**
	 * @param prioriteit_id
	 *            The prioriteit_id to set.
	 */
	public void setPrioriteit_id(String prioriteit_id) {
		this.prioriteit_id = prioriteit_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		dossierFaseJaartal = new String[0];
		dossierFaseMaand = new String[0];
		raming_ids = new Integer[0];
	}

	/**
	 * @return Returns the datumGoedkeuring.
	 */
	public String getDatumGoedkeuring() {
		return datumGoedkeuring;
	}

	/**
	 * @param datumGoedkeuring
	 *            The datumGoedkeuring to set.
	 */
	public void setDatumGoedkeuring(String datumGoedkeuring) {
		this.datumGoedkeuring = datumGoedkeuring;
	}

	public String getDoss_hdr_id() {
		return doss_hdr_id;
	}

	public void setDoss_hdr_id(String doss_hdr_id) {
		this.doss_hdr_id = doss_hdr_id;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getMaand_id() {
		return maand_id;
	}

	public void setMaand_id(String maand_id) {
		this.maand_id = maand_id;
	}

	public String getIvsDossierType() {
		return ivsDossierType;
	}

	public void setIvsDossierType(String ivsDossierType) {
		this.ivsDossierType = ivsDossierType;
	}

	public Integer[] getRaming_ids() {
		return raming_ids;
	}

	public void setRaming_ids(Integer[] ramingIds) {
		raming_ids = ramingIds;
	}

	public String getDatumVan() {
		return datumVan;
	}

	public void setDatumVan(String datumVan) {
		this.datumVan = datumVan;
	}

	public String getDatumTot() {
		return datumTot;
	}

	public void setDatumTot(String datumTot) {
		this.datumTot = datumTot;
	}

}
