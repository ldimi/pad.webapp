package be.ovam.art46.util;

import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Transactional
// (value="organisatie",propagation=Propagation.REQUIRED)
public class Hibernate4DaoSupport {

	/*
	 * @Autowired private SessionFactory sessionFactory;
	 */

//	@PersistenceContext
//	private EntityManager em;
	
	@Autowired
	private SessionFactory sessionFactory;
	

	// I cheated!
	protected Hibernate4DaoSupport getHibernateTemplate() {
		return this;
	}

	public void delete(Object obj) {
		getCurrentSession().delete(obj);
	}

	public <T> void deleteAll(Collection<T> items) {
		for (T item : items) {
			delete(item);
		}
	}

	public void saveOrUpdate(Object obj) {
		getCurrentSession().saveOrUpdate(obj);
	}

	public <T> void saveOrUpdateAll(Collection<T> items) {
		for (T item : items) {
			getCurrentSession().saveOrUpdate(item);
		}
	}

	public void save(Object obj) {
		getCurrentSession().save(obj);
	}

	public void merge(Object obj) {
		getCurrentSession().merge(obj);
	}

	protected Session getCurrentSession() {
		return  sessionFactory.getCurrentSession();  //(Session) em.getDelegate();
	}

	protected Session getSession() {
		return getCurrentSession();

	}

	/**
	 * Simulate spring 2's "getHibernateTemplate().get()" method.
	 */
	protected <T> T get(Class<T> clazz, Integer id) {
		@SuppressWarnings("unchecked")
		T result = (T) getCurrentSession().get(clazz, id);
		return result;
	}

	protected <T> T get(Class<T> clazz, Long id) {
		@SuppressWarnings("unchecked")
		T result = (T) getCurrentSession().get(clazz, id);
		return result;
	}

	/**
	 * Simulate spring 2's "getHibernateTemplate().loadAll()" method.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> loadAll(Class<T> clazz) {
		return getCurrentSession().createCriteria(clazz).list();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> find(String query) {
		return getCurrentSession().createQuery(query).list();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> find(String query, List<Object> args) {
		Query q = getCurrentSession().createQuery(query);

		for (int i = 0; i < args.size(); i++) {
			q.setParameter(i, args.get(i));
		}

		return q.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String query, Object... args) {
		Query q = getCurrentSession().createQuery(query);

		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}

		return q.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByCriteria(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getCurrentSession()).list();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> findByCriteria(DetachedCriteria criteria,
			int firstResult, int maxResults) {
		return criteria.getExecutableCriteria(getCurrentSession())
				.setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// public EntityManager getEm() {
	// return em;
	// }
	//
	// public void setEm(EntityManager em) {
	// this.em = em;
	// }

	public <T> T get(Class<T> clazz, Serializable id) {
		@SuppressWarnings("unchecked")
		T result = (T) getCurrentSession().get(clazz, id);
		return result;
	}

	public <T> List<T> findByNamedQuery(String queryName, Object value) {
		// TODO Auto-generated method stub

		return findByNamedQuery(queryName, new Object[] {value});

	}
	
	
	public <T> List<T> findByNamedQuery(final String queryName, final Object... values) {
			
		
		Query queryObject = getCurrentSession().getNamedQuery(queryName);
	
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject.list();
			
			
		}
	
	

	public <T> T load(Class<T> clazz, Serializable id) {
		return load(clazz, id, null);
	}

	public <T> T load(final Class<T> clazz, final Serializable id,
			final LockMode lockMode) {

		if (lockMode != null) {
			return (T) getCurrentSession().load(clazz, id, lockMode);
		} else {
			return (T) getCurrentSession().load(clazz, id);
		}

	}

	public List findByExample(Object exampleEntity) {
		return findByExample(null, exampleEntity, -1, -1);
	}

	public List findByExample(final String entityName,
			final Object exampleEntity, final int firstResult,
			final int maxResults) throws DataAccessException {

		Session session = getCurrentSession();
		Example create = Example.create(exampleEntity);
		Criteria createCriteria = session.createCriteria(exampleEntity
				.getClass());
		createCriteria.add(create);

		if (firstResult >= 0) {
			createCriteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			createCriteria.setMaxResults(maxResults);
		}
		return createCriteria.list();

	}

	public <T> List<T> findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values) {
		// TODO Auto-generated method stub
		Query queryObject = getCurrentSession().getNamedQuery(queryName);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				applyNamedParameterToQuery(queryObject, paramNames[i],
						values[i]);
			}
		}
		return queryObject.list();
	}

	public <T> List<T> findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) throws DataAccessException {

		return findByNamedQueryAndNamedParam(queryName,
				new String[] { paramName }, new Object[] { value });
	}

	protected void applyNamedParameterToQuery(Query queryObject,
			String paramName, Object value) throws HibernateException {

		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection) value);
		} else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[]) value);
		} else {
			queryObject.setParameter(paramName, value);
		}
	}

}