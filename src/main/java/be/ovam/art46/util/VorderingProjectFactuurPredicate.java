package be.ovam.art46.util;

import org.apache.commons.collections.Predicate;

public class VorderingProjectFactuurPredicate implements Predicate{

	private String project_factuur_id;
	
	public VorderingProjectFactuurPredicate(String project_factuur_id) {
		this.project_factuur_id = project_factuur_id;
	}
	
	
	public boolean evaluate(Object object) {
		if (object instanceof String) {
			return ((String) object).endsWith(project_factuur_id);
		}
		return false;
	}
}
