package be.ovam.art46.util;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.collections.Predicate;

import java.util.Map;

public class ProjectFactuurPredicate implements Predicate{

	Map projectFactuurVorderingen;
	
	public ProjectFactuurPredicate(Map projectFactuurVorderingen) {
		this.projectFactuurVorderingen = projectFactuurVorderingen;
	}
	
	
	public boolean evaluate(Object object) {
		if (object instanceof DynaBean) {
			DynaBean bean = (DynaBean) object;
			String project_factuur_id = bean.get("project_id") + "_" + bean.get("factuur_id");
			return projectFactuurVorderingen.containsKey(project_factuur_id);
		}
		return false;
	}
	
	

}
