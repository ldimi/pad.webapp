package be.ovam.art46.struts.action;

import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;
import java.util.List;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActieJDZoekResultAction extends ZoekAction {
	
	private final SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List result = sqlSession.selectList("getActieJDZoekResult", form);
		return result;
	}
	
}
