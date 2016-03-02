package be.ovam.art46.dao;

import be.ovam.pad.model.AanvraagSchuldvordering;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Koen on 27/01/14.
 */
@Repository
public class AanvraagSchuldvorderingDAO extends GenericDAO<AanvraagSchuldvordering> {

    public AanvraagSchuldvorderingDAO(){
        super(AanvraagSchuldvordering.class);
    }

    public List<AanvraagSchuldvordering> getAllForOfferte(Long offerteId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type, "a");
        criteria.add(Restrictions.eq("a.offerte.id",offerteId));
        List<AanvraagSchuldvordering> list = criteria.list();
        return list;
    }
}
