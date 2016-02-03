package be.ovam.art46.struts.action.kadaster;

import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class KadasterZoekAction extends ZoekAction {
	
    private static Log log = LogFactory.getLog(KadasterZoekAction.class);

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("zoek kadaster");
		
		String kadaster_afd_id = request.getParameter("kadaster_afd_id");
		
		List result = sqlSession.selectList("be.ovam.art46.mappers.KadasterMapper.getKadasterZoekResult", kadaster_afd_id);
		return result;
	}
}
