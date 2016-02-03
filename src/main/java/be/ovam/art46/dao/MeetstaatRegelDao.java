package be.ovam.art46.dao;

import be.ovam.pad.model.MeetstaatRegel;

import be.ovam.util.mybatis.SqlSession;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by koencorstjens on 12-9-13.
 */
@Repository
public class MeetstaatRegelDao extends GenericDAO<MeetstaatRegel> {
    private Logger log = Logger.getLogger(MeetstaatRegelDao.class);

    public static final String PROPERTY_NAME_MEETSTAATREGEL_BESTEK_BESTEK_ID = "bestek.bestek_id";
    public static final String PROPERTY_NAME_MEETSTAATREGEL_MEETSTAAT_TEMPLATE_ID = "meetstaatTemplate.id";

    public MeetstaatRegelDao() {
        super(MeetstaatRegel.class);
    }

    public List<MeetstaatRegel> getTemplateRegels(Long templateId) {
        return sessionFactory.getCurrentSession().createCriteria(type).add(Restrictions.and(Restrictions.eq(PROPERTY_NAME_MEETSTAATREGEL_MEETSTAAT_TEMPLATE_ID, templateId),Restrictions.isNull(PROPERTY_NAME_MEETSTAATREGEL_BESTEK_BESTEK_ID))).list();
    }

    public void deleteTemplateRegels(Long templateId) {
        for(MeetstaatRegel meetstaatRegel: getTemplateRegels(templateId)){
           delete(meetstaatRegel);
        }
    }

    public void deleteBestekRegels(Long bestekId) {
        for(MeetstaatRegel meetstaatRegel: getBestekRegels(bestekId)){
            sessionFactory.getCurrentSession().delete(meetstaatRegel);
        }
        sessionFactory.getCurrentSession().flush();
    }

    public List<MeetstaatRegel> getBestekRegels(Long bestekId) {
        log.debug(type + "getBestekRegels(" + bestekId+")");
        return sessionFactory.getCurrentSession().createCriteria(type).add(Restrictions.eq(PROPERTY_NAME_MEETSTAATREGEL_BESTEK_BESTEK_ID, bestekId)).list();
    }

    public MeetstaatRegel get(Long bestekId, String postnr) {
        return (MeetstaatRegel) sessionFactory.getCurrentSession().createCriteria(type).add(Restrictions.and(Restrictions.eq(PROPERTY_NAME_MEETSTAATREGEL_BESTEK_BESTEK_ID, bestekId), Restrictions.eq("postnr", postnr))).uniqueResult();
    }

    public void save(MeetstaatRegel meetstaatRegel) {
        if (!meetstaatRegel.isTotaal()) {
            super.save(meetstaatRegel);
        }
    }
    public void save(List<MeetstaatRegel> meetstaatRegels) {
        for(MeetstaatRegel meetstaatRegel : meetstaatRegels){
            if (!meetstaatRegel.isTotaal()) {
                sessionFactory.getCurrentSession().merge(meetstaatRegel);
            }
        }
        sessionFactory.getCurrentSession().flush();
    }
    public void delete(List<MeetstaatRegel> list) {
        super.delete(list);
        sessionFactory.getCurrentSession().flush();
    }
}
