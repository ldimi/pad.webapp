/*
 * Created on 7-jun-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package be.ovam.art46.struts.action.base;

import be.ovam.art46.util.PropertyPredicate;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class FilterAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons)
			throws Exception {
					
			List subList = new ArrayList();
            subList.addAll((List) ( request.getSession().getAttribute("zoeklijst")));          
			Field[] fields = form.getClass().getDeclaredFields();
			for (int i=0; i<fields.length;i++) {
				String propertyName = fields[i].getName();
				Object propertie =  PropertyUtils.getProperty(form, propertyName);				
				if (propertie != null && propertie.toString() != null && propertie.toString().length()>0 && !propertie.toString().equals("0")) {
					Predicate propertyPredicate = new PropertyPredicate(propertyName, propertie);			
					CollectionUtils.filter(subList, propertyPredicate);		
				}
			}			
			request.getSession().setAttribute("sublijst", subList);			
			return mapping.findForward("success");
		}
}
