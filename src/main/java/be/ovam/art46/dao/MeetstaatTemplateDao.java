package be.ovam.art46.dao;

import be.ovam.pad.model.MeetstaatTemplate;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by koencorstjens on 12-9-13.
 */
@Repository
public class MeetstaatTemplateDao extends GenericDAO<MeetstaatTemplate> {

    public MeetstaatTemplateDao() {
        super(MeetstaatTemplate.class);
    }

    public MeetstaatTemplate getByNaam(String naam){
        return (MeetstaatTemplate) sessionFactory.getCurrentSession().createCriteria(type).add(Restrictions.eq("naam", naam)).uniqueResult();
    }


}
