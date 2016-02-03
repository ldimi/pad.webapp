package be.ovam.art46.dao;

import be.ovam.pad.model.BriefView;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Koen on 22/01/2015.
 */
@Repository
public class BriefViewDao extends GenericDAO<BriefView> {

    public BriefViewDao() {
        super(BriefView.class);
    }

    public List<BriefView> getQrBrievenZonderScan() {
        List<BriefView> list = null;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BriefView.class, "b");
        criteria.add(Restrictions.and(Restrictions.isNotNull("b.qrcode"), Restrictions.isNull("b.dmsId")));
        criteria.addOrder(Order.desc("b.laatsteWijzigingDatum"));
        list = criteria.list();
        return list;
    }

    public List<BriefView> getQrBrievenMetScanLaatste15Dagen() {
        List<BriefView> list = null;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BriefView.class, "b");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -5);
        criteria.add(Restrictions.and(Restrictions.isNotNull("b.qrcode"), Restrictions.and(Restrictions.isNotNull("b.dmsId"), Restrictions.ge("laatsteWijzigingDatum", c.getTime()))));
        criteria.addOrder(Order.desc("b.laatsteWijzigingDatum"));
        list = criteria.list();
        return list;
    }

    public List<BriefView> gegenereerdeBrievenZonderScan() throws ParseException {

        DetachedCriteria selectAllScansQuery = DetachedCriteria.forClass(BriefView.class, "scans");
        selectAllScansQuery.add(Restrictions.eqProperty("scans.standaardParentScanBestandsnaam", "scans.dmsFileName"));
        selectAllScansQuery.setProjection(Projections.property("scans.parentBrief.id"));

        Criteria rootCriteria = sessionFactory.getCurrentSession().createCriteria(BriefView.class, "b");
        PropertyExpression isGegenereerdBestandVoorDezeBrief = Restrictions.eqProperty("b.standaardGegenereerdeBestandsnaam", "b.dmsFileName");
        Criterion uitDatumNotNull = Restrictions.isNotNull("uitDatum");
        Criterion nietgescand = Subqueries.propertyNotIn("b.id", selectAllScansQuery);
        LogicalExpression gegenereerdZonderScan = Restrictions.and(isGegenereerdBestandVoorDezeBrief, nietgescand);
        LogicalExpression gegenereerdZonderScanUitgeschreven = Restrictions.and(uitDatumNotNull, gegenereerdZonderScan);
        rootCriteria.add(Restrictions.and(Restrictions.ge("b.id", 118527), gegenereerdZonderScanUitgeschreven));
        rootCriteria.addOrder(Order.desc("b.uitDatum"));
        List<BriefView> list = rootCriteria.list();
        return list;
    }

    public List<BriefView> gegenereerdeBrievenMetScan() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -15);

        DetachedCriteria selectAllScansQuery = DetachedCriteria.forClass(BriefView.class, "scans");
        selectAllScansQuery.add(Restrictions.and(Restrictions.ge("laatsteWijzigingDatum", c.getTime()), Restrictions.eqProperty("scans.standaardParentScanBestandsnaam", "scans.dmsFileName")));
        selectAllScansQuery.setProjection(Projections.property("scans.parentBrief.id"));

        Criteria rootCriteria = sessionFactory.getCurrentSession().createCriteria(BriefView.class, "b");
        rootCriteria.add(Subqueries.propertyIn("b.id", selectAllScansQuery));
        rootCriteria.addOrder(Order.desc("b.uitDatum"));
        List<BriefView> list = rootCriteria.list();
        return list;
    }
}
