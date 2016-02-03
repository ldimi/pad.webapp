package be.ovam.art46.dao;

import be.ovam.pad.model.AdresContact;
import be.ovam.pad.model.Offerte;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;

import java.util.List;

/**
 * Created by Koen Corstjens on 3-9-13.
 */

@Repository
public class OfferteDao extends GenericDAO<Offerte> {

    public OfferteDao() {
        super(Offerte.class);
    }

    public List<Offerte> getForBestekId(Long bestekId) {
        return sessionFactory.getCurrentSession().createCriteria(Offerte.class).add(Restrictions.eq("bestekId", bestekId)).list();
    }


    public List<Offerte> getToegekendForBestekId(Long bestekId) {
        return sessionFactory.getCurrentSession().createCriteria(Offerte.class).add(Restrictions.eq("bestekId", bestekId)).add(Restrictions.eq("status", Offerte.STATUS_TOEGEWEZEN )).list();
    }

    public Offerte get(String offerteId) {
        return get(Long.valueOf(offerteId));
    }

    public Offerte getForBriefId(Integer briefId) {
        return (Offerte) sessionFactory.getCurrentSession().createCriteria(Offerte.class).add(Restrictions.eq("brief.brief_id", briefId)).uniqueResult();
    }

    public Offerte getOrgineelHerlanceringForNewOfferte(Offerte offerte) {
        if(offerte==null || offerte.getBrief()==null || offerte.getBrief().getAdresContactNaam()==null){
            return null;
        }
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Offerte.class, "o");
        criteria.createAlias("o.brief", "b");
        criteria.add(Restrictions.and(
                        Restrictions.and(Restrictions.eq("b.adres", offerte.getBrief().getAdres()),Restrictions.eq("b.contact_id", offerte.getBrief().getContact_id())),
                        Restrictions.eq("o.status", Offerte.STATUS_TOEGEWEZEN)
                    )
        );
        criteria.addOrder(Order.asc("id"));
        List<Offerte> offertes = criteria.list();
        if(CollectionUtils.isNotEmpty(offertes)){
           return offertes.get(0);
        }
        return null;
    }
}
