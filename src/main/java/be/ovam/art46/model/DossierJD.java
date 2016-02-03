package be.ovam.art46.model;

/**
 * @author Tonny Bruckers
 * @version 0.1 (Apr 27, 2004)
 */
public class DossierJD {

    private String dossier_id_jd;
    private Integer dossier_id;
    private String doss_hdr_id;
    private String dossier_b;
    private String commentaar;
    private String stand_terugvordering;

    public String getStand_terugvordering() {
        return stand_terugvordering;
    }

    public void setStand_terugvordering(String stand_terugvordering) {
        this.stand_terugvordering = stand_terugvordering;
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
    public String getDoss_hdr_id() {
        return doss_hdr_id;
    }

    /**
     * @return
     */
    public String getDossier_b() {
        return dossier_b;
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
    public void setDoss_hdr_id(String string) {
        doss_hdr_id = string;
    }

    /**
     * @param string
     */
    public void setDossier_b(String string) {
        dossier_b = string;
    }

    public Integer getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(Integer dossier_id) {
        this.dossier_id = dossier_id;
    }

    public String getDossier_id_jd() {
        return dossier_id_jd;
    }

    public void setDossier_id_jd(String dossier_id_jd) {
        this.dossier_id_jd = dossier_id_jd;
    }


}
