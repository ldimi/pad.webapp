package be.ovam.art46.dao;

import be.ovam.pad.model.OvamGemeente;

import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 5/03/14.
 */
@Repository
public class OvamGemeenteDao extends GenericDAO<OvamGemeente> {
    public OvamGemeenteDao() {
        super(OvamGemeente.class);
    }
}
