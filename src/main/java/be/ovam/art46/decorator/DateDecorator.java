package be.ovam.art46.decorator;

import be.ovam.art46.util.DateFormatArt46;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

import java.util.Date;

public class DateDecorator implements ColumnDecorator {	
	
	public String decorate(Object obj) throws DecoratorException {
		if (obj == null) {
			return "";
		}
		if (obj instanceof Date) {
			return DateFormatArt46.formatDate((Date) obj);
		}	
		return obj.toString();
	}	
}
