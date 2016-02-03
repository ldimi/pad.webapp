package be.ovam.art46.decorator;

import org.displaytag.decorator.TableDecorator;

import java.util.Date;
import java.util.Map;

public class BestekDeelopdrachtLijstDecorator extends TableDecorator {

	@SuppressWarnings("unchecked")
	public String addRowClass() {
		
		Map<String, Object> row = (Map<String, Object>) getCurrentRowObject();
						
		Date afsluit_d = (Date) row.get("afsluit_d");
	
		String clazz = "";
		if (afsluit_d != null) {
			clazz = "afgesloten";
		}
		
		return clazz;
	}
	
}
