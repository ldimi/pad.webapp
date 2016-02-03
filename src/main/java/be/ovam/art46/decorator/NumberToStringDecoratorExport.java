package be.ovam.art46.decorator;

import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

public class NumberToStringDecoratorExport implements ColumnDecorator {
	
	public String decorate(Object obj) throws DecoratorException {
		if (obj == null) {
			return "0";
		}	
		return "" + obj.toString();
	}	
}
