package be.ovam.art46.struts.action.dossier.fz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import be.ovam.art46.struts.plugin.LoadPlugin;

public class LaadDossiersFzIVSAction  extends Action {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons) throws Exception {
		
		List result = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossiersFzIVS", null);
		request.setAttribute("dossiersfz", result);

		return mapping.findForward("success");
	}
}