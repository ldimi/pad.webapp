package be.ovam.art46.struts.action;

import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.plugin.LoadPlugin;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public class BriefZoekResultAction extends ZoekAction {

    private static Logger log = LoggerFactory.getLogger(BriefZoekResultAction.class);

    private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("zoek brief");
		List result = sqlSession.selectList("be.ovam.art46.mappers.BriefMapper.zoekBrieven", form);
	    if (result.size() > 5000) {
	    	throw new Exception("Verfijn uw zoekopdracht, er werden meer dan 5000 resultaten gevonden.");
	    }
		return result;
	}
}
