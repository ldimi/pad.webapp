package be.ovam.art46.decorator;

import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class BigDecimalDecorator implements ColumnDecorator {

	private static NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMANY);	
	
	public String decorate(Object obj) throws DecoratorException {
		if (obj == null) {
			return "0";
		}	
		String result = formatter.format((BigDecimal) obj); 
		return result;
	}	
	
	public static String format(BigDecimal value) {
		if (value != null) {
			return formatter.format(value.doubleValue());
		}
		return "";
	}
}
