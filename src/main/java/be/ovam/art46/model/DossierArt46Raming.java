/*
 * Filename: DossierBOARaming.java
 * Creator: torghal
 * Created: Dec 25, 2004
 *
 * Purpose:
 * 
 *
 */
package be.ovam.art46.model;

/**
 * @author torghal
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DossierArt46Raming extends Raming {
    public Integer dossier_id;
    private String dossier_type;

    public Object getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(Object dossier_id) {
        if (dossier_id != null) {
            this.dossier_id = Integer.valueOf(dossier_id.toString());
        }
    }

    public String getDossier_type() {
        return dossier_type;
    }

    public void setDossier_type(String dossier_type) {
        this.dossier_type = dossier_type;
    }

}
