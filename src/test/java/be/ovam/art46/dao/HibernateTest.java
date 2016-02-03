package be.ovam.art46.dao;

import be.ovam.art46.model.AdresType;
import be.ovam.pad.model.MeetstaatRegel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.sql.DataSource;


/**
 * Created by Koen on 10/02/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-setup.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateTest extends GenericDaoTest {

    @Test
    public void testSeup() {
        Assert.notNull(sessionFactory);
    }

    @Test
    public void save(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setId(0l);
        sessionFactory.getCurrentSession().save(meetstaatRegel);
        System.out.println("session.save: "+meetstaatRegel.getId());
    }
    @Test
    public void saveOrUpdate(){
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setId(0l);
        sessionFactory.getCurrentSession().saveOrUpdate(meetstaatRegel2);
        System.out.println("session.saveOrUpdate: "+meetstaatRegel2.getId());

    }

    @Test
    public void merge(){
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setId(0l);
        sessionFactory.getCurrentSession().merge(meetstaatRegel2);
        System.out.println("session.merge: "+meetstaatRegel2.getId());
    }

}
