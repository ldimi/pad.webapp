package be.ovam.art46.dao;


import be.ovam.pad.model.Brief;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class GenericDAO<T extends Object> {
    @Autowired
    protected SessionFactory sessionFactory;
    protected Class<T> type;
    private Logger log = Logger.getLogger(GenericDAO.class);

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    public void save(T object) {
        saveObjectNoFlush(object);
        flush();
    }

    private void saveObjectNoFlush(T object) {
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    public T get(String id) {
        log.debug(type + " - get (" + id + ")");
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }

    public T get(Long id) {
        log.debug(type + " - get (" + id + ")");
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }

    public T get(Integer id) {
        log.debug(type + " - get(" + id + ")");
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }

    public void delete(T object) {
        deleteObject(object);
        flush();
    }

    private void deleteObject(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }

    public void delete(Long id) {
        delete(get(id));
    }

    public void delete(String id) {
        delete(get(id));
    }

    public void delete(Integer deelopdrachtId) {
        T object = get(deelopdrachtId);
        if (object != null) {
            delete(object);
        }
    }

    public List<T> getAll() {
        log.debug(type + "getall()");
        return sessionFactory.getCurrentSession().createCriteria(type).list();
    }

    public List<T> getAllAsc(String propertyName) {
        log.debug(type + "getAllAsc(" + propertyName + ")");
        return sessionFactory.getCurrentSession().createCriteria(type).addOrder(Order.asc(propertyName)).list();
    }

    public void save(List<T> list) {
        for (T object : list) {
            save(object);
        }
        flush();
    }

    public void delete(List<T> list) {
        for (T object : list) {
            delete(object);
        }
        flush();
    }

    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    public void sessionClear() {
        sessionFactory.getCurrentSession().clear();
    }

    protected void saveFromForm(ActionForm form, Class<Brief> briefClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T object = type.newInstance();
        BeanUtils.copyProperties(object, form);
        save(object);
        BeanUtils.copyProperties(form, object);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
