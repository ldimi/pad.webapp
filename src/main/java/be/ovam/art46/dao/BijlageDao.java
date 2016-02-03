package be.ovam.art46.dao;

import be.ovam.pad.model.Bijlage;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 15/05/2014.
 */
@Repository
public class BijlageDao extends GenericDAO<Bijlage> {
    public BijlageDao() {
        super(Bijlage.class);
    }

    public boolean voorstelDeelopdrachtBestaat(String originalFilename, Long id) {
        Bijlage bijlage = (Bijlage) sessionFactory.getCurrentSession().createCriteria(type, "b").add(Restrictions.and(Restrictions.eq("b.voorstelDeelopdracht.id", id), Restrictions.eq("b.name", originalFilename))).uniqueResult();
        return bijlage != null;
    }

}