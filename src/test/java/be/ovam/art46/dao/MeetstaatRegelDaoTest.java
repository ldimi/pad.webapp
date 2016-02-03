package be.ovam.art46.dao;


import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.Schuldvordering;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Koen on 11/02/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-setup.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeetstaatRegelDaoTest extends GenericDaoTest{

    @Autowired
    MeetstaatRegelDao meetstaatRegelDao;

/*    @Test
    public void t1createId0() {
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setId(0l);
        meetstaatRegelDao.save(meetstaatRegel);
        Assert.assertNotNull(meetstaatRegel);
        Assert.assertNotNull(meetstaatRegel.getId());
        Assert.assertNotEquals(new Integer(0), meetstaatRegel.getId());
        System.out.println(meetstaatRegel.getId());
    }*/
    @Test
    public void t2createIdNull() {
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setId(null);
        meetstaatRegelDao.save(meetstaatRegel);
        Assert.assertNotNull(meetstaatRegel);
        Assert.assertNotNull(meetstaatRegel.getId());
        Assert.assertNotEquals(new Integer(0), meetstaatRegel.getId());
        System.out.println(meetstaatRegel.getId());
    }
}
