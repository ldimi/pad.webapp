package be.ovam.art46.struts.action;

import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import be.ovam.util.mybatis.SqlSession;
import java.util.List;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DossierArt46ZoekAction extends ZoekAction {

    private static final Log log = LogFactory.getLog(DossierArt46ZoekAction.class);

	private final SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//((DossierZoekForm) form).setDossier_type(null);
		log.info("zoek dossier ...");
		List result = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierZoekResult", form);
	    if (result.size() > 1000) {
	    	throw new Exception("Verfijn uw zoekopdracht, er werden meer dan 1000 resultaten gevonden.");
	    }
		return result;
	}
}
