package be.ovam.art46.util.converters;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.util.Locale;

public class DoubleConverterArt46 implements Converter {		
	
	private NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);	
	
	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		Object doubl = null;		
		try {
			if (value != null && StringUtils.isNotBlank(value.toString())) {
				doubl = formatter.parseObject(value.toString() + " €");
				if (doubl instanceof Long) {
					doubl = new Double(((Long) doubl).doubleValue());
				}
			}
			
		} catch (Exception e) {		
			System.out.println("Error on converting Double: " + e.getMessage());
		}			
		return doubl;		
	}

}
