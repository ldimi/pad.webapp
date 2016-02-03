package be.ovam.art46.util.converters;

import org.apache.commons.beanutils.Converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class BigDecimalConverterArt46 implements Converter {		
	
	private static NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMANY);	
	
	public static BigDecimal parseBigDecimal(String value) {
		try {
			if (value != null && value.length() != 0) {
				return BigDecimal.valueOf(formatter.parse(value).doubleValue());
			}
		} catch (ParseException e) {
			
		}
		return BigDecimal.ZERO;
	}
	
	public static String formatBigDecimal(BigDecimal value) {
		return formatter.format(value.doubleValue());
	}
	
	public Object convert(Class type, Object value) {
		Object decimal = null;		
		try {
			if (value != null && !"".equals(value)) {
				return BigDecimal.valueOf(formatter.parse(value.toString()).doubleValue());
			}			
		} catch (Exception e) {		
			System.out.println("Error on converting BigDecimal: " + e.getMessage());
		}			
		return decimal;
	}

}
