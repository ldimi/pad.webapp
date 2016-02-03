package be.ovam.art46.dao;

import be.ovam.pad.model.OfferteRegel;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Koen Corstjens on 4-9-13.
 */
@Repository
public class OfferteRegelDao extends GenericDAO<OfferteRegel> {
    private Logger log = Logger.getLogger(OfferteRegelDao.class);

    public OfferteRegelDao() {
        super(OfferteRegel.class);
    }

    public void save(OfferteRegel offerteRegel){
        if(!offerteRegel.isTotaal()){
            super.save(offerteRegel);
        }
    }
    public void save(List<OfferteRegel> offerteRegels){
        for(OfferteRegel offerteRegel : offerteRegels){
            if(!offerteRegel.isTotaal()){
                super.save(offerteRegel);
            }
        }
    }

    public List<OfferteRegel> getWhereOfferteId(Long offerteId) {
        log.debug(type +"dao getWhereOfferteId("+offerteId+")");
        return sessionFactory.getCurrentSession().createCriteria(type).add(Restrictions.eq("offerte.id",offerteId)).list();
    }

    public void deleteWhereOfferteId(Long offerteId) {
        List<OfferteRegel> offerteRegels = getWhereOfferteId(offerteId);
        for(OfferteRegel offerteRegel: offerteRegels){
            super.delete(offerteRegel);
        }
    }
}
