package be.ovam.art46.dao;

import be.ovam.pad.model.Dossier;
import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 4/03/2015.
 */
@Repository
public class DossierArt46Dao extends GenericDAO<Dossier> {
    public DossierArt46Dao() {
        super(Dossier.class);
    }
}
