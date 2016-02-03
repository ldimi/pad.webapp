package be.ovam.art46.util.converters;

import org.apache.commons.beanutils.locale.converters.StringLocaleConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Tonny Bruckers
 * @version 0.1 (May 2, 2004)
 */
public class StringConverterArt46 extends StringLocaleConverter {

	private NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);	
	private NumberFormat decimalFormattter = NumberFormat.getNumberInstance(Locale.GERMANY);
	
	public StringConverterArt46(Object defaultValue) {
		super(defaultValue);
	}
	
	protected Object parse(Object value, String pattern) throws ParseException {
		if (value instanceof String && "".equals(value)) {
			return null;
		}
		
		if ((value instanceof Integer) ||			
			(value instanceof Long) ||
			(value instanceof BigInteger) ||
			(value instanceof Byte) ||
			(value instanceof String) ||
			(value instanceof Short)) {
			return value.toString();
		}
		else if (value instanceof Double) {			
			return formatter.format(value).replace("€", "").trim();
		}
		else if (value instanceof Date) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			return dateFormat.format(value);
		}
		else if (value instanceof BigDecimal) {			
			return decimalFormattter.format(value).trim();
		}
		return super.parse(value, pattern);
	}

}
