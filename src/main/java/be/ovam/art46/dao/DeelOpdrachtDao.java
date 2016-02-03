package be.ovam.art46.dao;

import be.ovam.pad.model.DeelOpdracht;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Koen on 6/05/2014.
 */
@Repository
public class DeelOpdrachtDao extends GenericDAO<DeelOpdracht> {
    public DeelOpdrachtDao() {
        super(DeelOpdracht.class);
    }

    public List<DeelOpdracht> getDeelopdrachten(Long offerteId, Integer dossierId) {
    return sessionFactory.getCurrentSession().createCriteria(type).createAlias("offerte", "o").createAlias("dossier", "d")
                .add(Restrictions.and(Restrictions.isNull("voorstelDeelopdracht"),Restrictions.and(Restrictions.eq("d.id",dossierId), Restrictions.eq("o.id",offerteId))))
                .list();
    }

}