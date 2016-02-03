package be.ovam.art46.util;

import com.free2be.dimensions.decider.Decision;
import com.free2be.dimensions.tiles.inheritance.AbstractInheritanceResolverImpl;

public class Art46InheritanceResolver extends AbstractInheritanceResolverImpl {
	
	public void resolveInheritances() throws Exception {
		Decision decision = new Decision();
		decision.setParameter("popup", "yes");
		addPath(decision, "/WEB-INF/tiles-defs_popup.xml");
		addPath(decision, "/WEB-INF/tiles-defs.xml");
		addPath(new Decision(), "/WEB-INF/tiles-defs.xml");
    }	
}
