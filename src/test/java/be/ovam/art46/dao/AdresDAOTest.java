package be.ovam.art46.dao;

import be.ovam.pad.model.Adres;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
public class AdresDAOTest extends GenericDaoTest {
    @Autowired
    AdresDAO adresDAO;

    @Test
    public void t1create() {
        Adres adres = new Adres();
        adres.setNaam("test naam");
        adresDAO.save(adres);
        Assert.assertEquals(new Integer(1), adres.getAdres_id());
    }

    @Test
    public void t2get() {
        Adres adres2 = adresDAO.get(1);
        Assert.assertEquals("test naam", adres2.getNaam());
    }

    @Test
    public void t3update() {
        Adres adres = adresDAO.get(1);
        adres.setNaam("test2");
        adresDAO.save(adres);
        Adres adres2 = adresDAO.get(1);
        Assert.assertEquals("test2", adres2.getNaam());
    }

    @Test
    public void t4delete() {
        adresDAO.delete(adresDAO.get(1));
        Adres adres = adresDAO.get(1);
        Assert.assertNull(adres);
    }
   @Test
    public void t5createId0() {
        Adres adres = new Adres();
        adres.setAdres_id(0);
        adres.setNaam("test naam");
        adresDAO.save(adres);
        Assert.assertNotNull(adres);
        Assert.assertNotNull(adres.getAdres_id());
        Assert.assertNotEquals(new Integer(0), adres.getAdres_id());
    }
    @Test
    public void t5createIdNull() {
        Adres adres = new Adres();
        adres.setAdres_id(null);
        adres.setNaam("test naam");
        adresDAO.save(adres);
        Assert.assertNotNull(adres);
        Assert.assertNotNull(adres.getAdres_id());
        Assert.assertNotEquals(new Integer(0), adres.getAdres_id());
    }
}
