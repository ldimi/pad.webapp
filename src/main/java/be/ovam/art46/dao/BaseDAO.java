package be.ovam.art46.dao;

import be.ovam.art46.util.Hibernate4DaoSupport;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.struts.action.ActionForm;

import javax.sql.DataSource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

/**
 * @deprecated Use GenericDAO
 */
public class BaseDAO extends Hibernate4DaoSupport {

	protected DataSource dataSource;

	public List getAll(Class clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	public void saveObject(Object object) throws Exception {
		getHibernateTemplate().saveOrUpdate(object);
	}

	public Object getObject(Class clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public void deleteObject(Object object) {
		getHibernateTemplate().delete(object);
	}

	public List<DynaBean> getDynaBeans(String sql) throws Exception {
		return getDynaBeans(sql, null);
	}

	public List<DynaBean> getDynaBeans(String sql, Object[] params)
			throws Exception {
		return getDynaBeansByRowset(sql, params).getRows();
	}

	// TODO move method to service layer
	public void saveObjectFromForm(ActionForm form, Class objectClass)
			throws Exception {
		Object object = objectClass.newInstance();
		BeanUtils.copyProperties(object, form);
		saveObject(object);
		BeanUtils.copyProperties(form, object);
	}

	public int executeUpdate(String sql, Object[] params) throws Exception {
		int t = 0;
		Connection conn = getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			setParameters(st, params);
			t = st.executeUpdate();

		} finally {
			conn.close();
		}
		return t;
	}

	public void executeUpdateAll(List<String> sql, List<List<Object[]>> params)
			throws Exception {
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		try {
			for (int s = 0; s < sql.size(); s++) {
				PreparedStatement updateSQL = conn
						.prepareStatement((String) sql.get(s));
				List<Object[]> paramsSql = (List<Object[]>) params.get(s);
				for (int t = 0; t < paramsSql.size(); t++) {
					setParameters(updateSQL, (Object[]) paramsSql.get(t));
					updateSQL.executeUpdate();
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void executeUpdateAll(String sql, List params) throws Exception {
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		try {
			PreparedStatement updateSQL = conn.prepareStatement(sql);
			for (int t = 0; t < params.size(); t++) {
				System.out.println("Query: " + sql);
				System.out.println("Setting parameters ....");
				setParameters(updateSQL, (Object[]) params.get(t));
				System.out.println("Parameters set.");
				System.out.println("Adding to batch....");
				updateSQL.addBatch();
				System.out.println("Added to batch.");
			}
			System.out.println("Executing batch....");
			updateSQL.executeBatch();
			System.out.println("Commiting ....");
			conn.commit();
			System.out.println("Commited.");
		} catch (Exception e) {
			System.out.println("Rolling back ....");
			conn.rollback();
			System.out.println("Rolled back.");
			throw e;
		} finally {
			System.out.println("Closing connection ....");
			conn.close();
			System.out.println("Closed connection.");
		}
	}

	public RowSetDynaClass getDynaBeansByRowset(String sql, Object[] params)
			throws Exception {
		Connection conn = getConnection();
		RowSetDynaClass rsd = null;
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			setParameters(st, params);
			ResultSet rs = st.executeQuery();
			rsd = new RowSetDynaClass(rs);
		} finally {
			conn.close();
		}
		return rsd;
	}

	private void setParameters(PreparedStatement st, Object[] params)
			throws SQLException {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof Integer) {
					st.setInt(i + 1, ((Integer) params[i]).intValue());
				} else if (params[i] instanceof Long) {
					st.setLong(i + 1, ((Long) params[i]).longValue());
				} else if (params[i] instanceof Date) {
					st.setDate(i + 1, ((Date) params[i]));
				} else if (params[i] instanceof java.util.Date) {
					st.setDate(i + 1,
							new Date(((java.util.Date) params[i]).getTime()));
				} else if (params[i] instanceof BigDecimal) {
					st.setBigDecimal(i + 1, (BigDecimal) params[i]);
				} else {
					st.setString(i + 1, (String) params[i]);
				}
				System.out.println("Param: '" + params[i] + "'");
			}
		}
	}

	public Object findFirstByNamedQuery(String queryName, Object parameter) {
		List result = getHibernateTemplate().findByNamedQuery(queryName,
				parameter);
		if (result != null && result.size() != 0) {
			return result.get(0);
		}
		return null;
	}

	public DynaBean findFirstByDynaBeans(String sql, Object[] params)
			throws Exception {
		List result = getDynaBeans(sql, params);
		if (result != null && result.size() != 0) {
			return (DynaBean) result.get(0);
		}
		return null;
	}

	

	public Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
