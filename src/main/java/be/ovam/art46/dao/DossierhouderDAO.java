package be.ovam.art46.dao;

import be.ovam.pad.model.Dossierhouder;
import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 29/01/14.
 */
@Repository
public class DossierhouderDAO extends GenericDAO<Dossierhouder> {

    public DossierhouderDAO() {
        super(Dossierhouder.class);
    }
}
