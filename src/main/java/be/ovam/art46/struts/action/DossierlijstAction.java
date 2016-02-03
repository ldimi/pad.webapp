package be.ovam.art46.struts.action;

import be.ovam.art46.model.User;
import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DossierlijstAction extends ZoekAction {
	
	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");
	
	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String user_id = ((User) request.getSession().getAttribute("user")).getUser_id();
		List<?> result = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossiersByDossHdr", user_id);
		return result;
	}
}
