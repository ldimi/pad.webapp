package be.ovam.art46.util.converters;

import be.ovam.art46.util.DateFormatArt46;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.SqlDateConverter;

import java.util.Date;

public class DateConverterArt46 implements Converter {		
	

	public Object convert(Class type, Object value) {
        if (value == null) {
        	return value;
        }
        if (value instanceof Date) {
        	return value;
        }

		if (value instanceof String) {
			try {
				return DateFormatArt46.getDate(value.toString());
			} catch (Exception e) {				
			}			
		}
		SqlDateConverter converter = new SqlDateConverter(null);
		return converter.convert(type, value);		
	}

}
