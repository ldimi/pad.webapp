package be.ovam.art46.dao;

import be.ovam.pad.model.Ambtenaar;

import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 23/06/2014.
 */
@Repository
public class AmbtenaarDao extends GenericDAO<Ambtenaar> {
    public AmbtenaarDao() {
        super(Ambtenaar.class);
    }
}
