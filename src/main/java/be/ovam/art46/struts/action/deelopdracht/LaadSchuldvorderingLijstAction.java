package be.ovam.art46.struts.action.deelopdracht;

import be.ovam.art46.struts.plugin.LoadPlugin;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LaadSchuldvorderingLijstAction  extends Action {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons) throws Exception {
		
	    String deelopdracht_id = request.getParameter("deelopdracht_id");
	    
        List lijst = sqlSession.selectList("be.ovam.art46.mappers.DeelopdrachtMapper.getSchuldvorderingen", deelopdracht_id);
        request.setAttribute("deelopdrachtlijstschuldvordering", lijst);
        
		
		return mapping.findForward("success");
	}
}