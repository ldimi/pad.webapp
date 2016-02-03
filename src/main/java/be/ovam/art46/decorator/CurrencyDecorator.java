package be.ovam.art46.decorator;

import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyDecorator implements ColumnDecorator {

	
	public String decorate(Object obj) throws DecoratorException {
//		if (obj == null) {
//			return null;
//		}
//		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
//		String result = formatter.format(new Double(obj.toString())).replace("€", "&euro;"); 
//		return result;
	    return format(obj);
	}
	
    public static String format(Object obj) {
        if (obj == null) {
            return null;
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        String result = formatter.format(new Double(obj.toString())).replace("€", "&euro;"); 
        return result;
    }

}
