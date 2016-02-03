package be.ovam.art46.decorator;

import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyDecoratorExport implements ColumnDecorator {

//	private NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);	
	private NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMANY);
	
	public String decorate(Object obj) throws DecoratorException {
		if (obj == null) {
            return null;
		}	
		return formatter.format(new Double(obj.toString())).replace(".","");
	}		
}
