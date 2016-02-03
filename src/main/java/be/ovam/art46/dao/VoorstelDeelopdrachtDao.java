package be.ovam.art46.dao;

import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.VoorstelDeelopdracht;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Koen on 2/05/2014.
 */
@Repository
@Transactional
public class VoorstelDeelopdrachtDao extends GenericDAO<VoorstelDeelopdracht>  {
    public VoorstelDeelopdrachtDao() {
        super(VoorstelDeelopdracht.class);
    }

    public List<VoorstelDeelopdracht> getForOfferte(Long id) {
        return sessionFactory.getCurrentSession().createCriteria(type).add(Restrictions.eq("offerte.id", id)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.desc("id")).list();
    }

    public List<DeelOpdracht> getForBestek(Long bestekId) {
        return sessionFactory.getCurrentSession().createCriteria(type,"v").createAlias("v.offerte","o").add(Restrictions.eq("o.bestekId", bestekId)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.desc("id")).list();
    }
}
