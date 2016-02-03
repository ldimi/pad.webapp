package be.ovam.art46.service;

import be.ovam.art46.dao.BaseDAO;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.struts.action.ActionForm;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class  BaseService {


	private BaseDAO dao;
	
	public void saveObject(Object object) throws Exception {
		dao.saveObject(object);
	}
	
	public void deleteObject(Object object) throws Exception {
		dao.deleteObject(object);
	}
	
	public void saveObject(ActionForm form, Class objectClass) throws Exception, InvocationTargetException {
		Object object = objectClass.newInstance();
		BeanUtils.copyProperties(object, form);
		dao.saveObject(object);
		BeanUtils.copyProperties(form, object);
	}
	
	public void deleteObject(ActionForm form, Class objectClass) throws Exception {
		Object object = objectClass.newInstance();
		BeanUtils.copyProperties(object, form);
		dao.deleteObject(object);
	}

	public Object getObject(Class objectClass, Serializable id) {
		return dao.getObject(objectClass, id);
	}
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	public RowSetDynaClass getDynaBeansByRowset(String sql) throws Exception {
		return dao.getDynaBeansByRowset(sql, null);
	}
	
	

}
