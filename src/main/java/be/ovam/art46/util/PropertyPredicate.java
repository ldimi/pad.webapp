/*
 * Created on 7-jun-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package be.ovam.art46.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.Predicate;

/**
 * @author sluypaer
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PropertyPredicate implements Predicate {
	
	private String propertyValue;
	private String propertyName;
	
	public PropertyPredicate(String propertyName, Object propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue.toString();		
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(Object obj) {
		try {			
			return propertyValue.equals(BeanUtils.getProperty(obj, propertyName).toString());
		} catch (Exception e) {			
		} 		
		return false;
	}

}
