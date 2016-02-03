package be.ovam.art46.dao;

import be.ovam.pad.model.Schuldvordering;
import be.ovam.pad.model.SchuldvorderingStatusEnum;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SchuldvorderingDAO extends BaseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveBetaal_d(Map betaal_d) throws  Exception {
		Iterator<String> iter = betaal_d.keySet().iterator();
		List<Object[]> params = new ArrayList<Object[]>();
		String vordering_id = null;
		while (iter.hasNext()) {
			vordering_id = (String) iter.next();
			params.add(new Object[] {((String) betaal_d.get(vordering_id)).length() == 0 ? null : (String) betaal_d.get(vordering_id),  Integer.parseInt(vordering_id)});			
		}
		executeUpdateAll("update ART46.SCHULDVORDERING set BETAAL_D = ? where VORDERING_ID = ?", params);
	}	
	
	public void deleteSchuldvorderingProjectFactuur(String vordering_id, String project_id, String factuur_id) throws  Exception {
		executeUpdate("delete from ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR  where VORDERING_ID = ? and PROJECT_ID = ? and FACTUUR_ID =?",  new String[] {vordering_id, project_id, factuur_id});		
	}
	
	private Date addDays(Date date, int numberOfDays) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, numberOfDays);		
		return cal.getTime();
	}
    public void saveSchuldvordering(Schuldvordering schuldvordering){
        sessionFactory.getCurrentSession().saveOrUpdate(schuldvordering);
        sessionFactory.getCurrentSession().flush();
    }
    public Schuldvordering getForBriefId(Integer briefId){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Schuldvordering.class, "s");
        criteria.add(Restrictions.eq("s.brief_id", briefId));
        Schuldvordering schuldvordering = (Schuldvordering) criteria.uniqueResult();
        return schuldvordering;
    }


    public Integer getVorderingId(Integer briefId) {
        Schuldvordering schuldvordering = getForBriefId(briefId);
        if(schuldvordering==null){
            return null;
        }
        return schuldvordering.getVordering_id();
    }

    public List<Schuldvordering> getForBestek(Long bestekId) {
        return sessionFactory.getCurrentSession().createCriteria(Schuldvordering.class, "s").add(Restrictions.eq("s.bestek_id", bestekId)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @SuppressWarnings("unchecked")
    public List<Schuldvordering> getTeKeurenSchuldvorderingen() {
        return sessionFactory.getCurrentSession().createCriteria(Schuldvordering.class, "s").createAlias("s.brief", "b", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.and(
                        (Restrictions.and(Restrictions.isNull("s.goedkeuring_d"), Restrictions.eq("s.status", SchuldvorderingStatusEnum.BEOORDEELD.getValue()))),
                        (Restrictions.or(Restrictions.isNotNull("s.aanvraagSchuldvordering"), Restrictions.isNotNull("b.dms_id")))
                )).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    public Schuldvordering get(Integer id){
        return get(Schuldvordering.class, id);
    }

    public List<Schuldvordering> getTeScannenSchuldvorderingen() {
        return sessionFactory.getCurrentSession().createCriteria(Schuldvordering.class, "s").createAlias("s.brief", "b").add
                (Restrictions.and(
                        (Restrictions.and(
                                (Restrictions.and(Restrictions.isNull("s.goedkeuring_d"), Restrictions.isNotNull("s.acceptatieDatum"))),
                                Restrictions.isNull("b.dms_id"))
                        ), Restrictions.isNull("s.aanvraagSchuldvordering"))
                    )
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
    }

    public List<Schuldvordering> getTePrintenSchuldvorderingen() {
        return sessionFactory.getCurrentSession().createCriteria(Schuldvordering.class, "s").createAlias("s.brief", "b").add
                (Restrictions.and(
                        (Restrictions.and(
                                Restrictions.eq("s.status", SchuldvorderingStatusEnum.ONDERTEKEND.getValue()),
                                Restrictions.isNotNull("b.dms_id"))
                        ), Restrictions.isNull("s.aanvraagSchuldvordering"))
                    )
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
    public void save(Schuldvordering schuldvordering) {
        super.save(schuldvordering);
    }


    public void refresh(Schuldvordering schuldvordering) {
        sessionFactory.getCurrentSession().refresh(schuldvordering);
    }

}


