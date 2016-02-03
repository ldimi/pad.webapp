package be.ovam.art46.dao;

import be.ovam.pad.model.Adres;
import be.ovam.pad.model.Schuldvordering;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * Created by Koen on 10/02/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-setup.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchuldvorderingDAOTest extends GenericDaoTest {
    @Autowired
    SchuldvorderingDAO schuldvorderingDAO;

    @Test
    public void t1createId0() {
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setVordering_id(0);
        schuldvordering.setCommentaar("test");
        schuldvorderingDAO.save(schuldvordering);
        Assert.assertNotNull(schuldvordering);
        Assert.assertNotNull(schuldvordering.getVordering_id());
        Assert.assertNotEquals(new Integer(0), schuldvordering.getVordering_id());
        System.out.println(schuldvordering.getVordering_id());
    }
    @Test
    public void t2createIdNull() {
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setVordering_id(null);
        schuldvordering.setCommentaar("test");
        schuldvorderingDAO.save(schuldvordering);
        Assert.assertNotNull(schuldvordering);
        Assert.assertNotNull(schuldvordering.getVordering_id());
        Assert.assertNotEquals(new Integer(0), schuldvordering.getVordering_id());
        System.out.println(schuldvordering.getVordering_id());
    }

}
