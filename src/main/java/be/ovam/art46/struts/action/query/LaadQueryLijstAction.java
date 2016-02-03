package be.ovam.art46.struts.action.query;

import be.ovam.art46.struts.actionform.DossierArt46Form;
import be.ovam.art46.struts.plugin.LoadPlugin;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public class LaadQueryLijstAction  extends Action {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons) throws Exception {
		
	    DossierArt46Form dossierForm = (DossierArt46Form) form;
	    
		List lijst = sqlSession.selectList("be.ovam.art46.mappers.QueryMapper.getAllQueries", null);
		request.setAttribute("querylijst", lijst);

		return mapping.findForward("success");
	}
}