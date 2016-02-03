package be.ovam.art46.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Koen on 11/02/14.
 */
public abstract class GenericDaoTest {
    @Autowired
    DataSource dataSource;
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    HibernateTransactionManager transactionManager;

    private Session session;

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        ThreadLocalSessionContext.bind(session);
    }
    @After
    public void afther() {
        ThreadLocalSessionContext.unbind(sessionFactory);
    }
}
