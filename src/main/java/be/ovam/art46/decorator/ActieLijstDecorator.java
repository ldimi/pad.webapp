package be.ovam.art46.decorator;

import org.displaytag.decorator.TableDecorator;

import java.util.Date;
import java.util.Map;

public class ActieLijstDecorator extends TableDecorator {

	private static long two_weeks = 2*7*24*60*60*1000;

	@SuppressWarnings("unchecked")
	public String addRowClass() {
		
		Map<String, Object> row = (Map<String, Object>) getCurrentRowObject();
				
		Date realisatie_d = (Date) row.get("realisatie_d");
		Date stop_d = (Date) row.get("stop_d");
	
		String clazz = "";
		if (realisatie_d != null) {
			clazz = "green";
		} else if (stop_d != null && stop_d.before(new Date(System.currentTimeMillis()))) {
			clazz = "red";
		} else if (stop_d != null && new Date(System.currentTimeMillis() + two_weeks).after(stop_d)) {
			clazz = "yellow";
		}
		
		Integer actieSubType = (Integer) row.get("actie_sub_type_id");
		if (actieSubType == null || actieSubType.equals(0)) {
			clazz += " Bold";
		}
		
		return clazz;
	}

	
}
