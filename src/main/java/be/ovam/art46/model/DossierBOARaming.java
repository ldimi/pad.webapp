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
public class DossierBOARaming extends Raming {

    private Integer dossier_id;

    public Object getDossier_id() {
        return dossier_id;
    }

    public void setDossier_id(Object dossier_id) {
        if (dossier_id != null) {
            this.dossier_id = Integer.valueOf(dossier_id.toString());
        }
    }
}
