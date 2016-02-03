package be.ovam.art46.struts.action.kadaster;

import be.ovam.art46.struts.actionform.Art46BaseForm;
import be.ovam.art46.struts.plugin.LoadPlugin;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public class LaadDetailsPublicatieAction  extends Action {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons) throws Exception {
		
	    Art46BaseForm dossierkadastersform = (Art46BaseForm) form;

		List opnameLijst = sqlSession.selectList("be.ovam.art46.mappers.KadasterMapper.getDetailsPublicatieLijst", dossierkadastersform.getKadaster_id());
		request.setAttribute("kadasterdetailspublicatie", opnameLijst);

		return mapping.findForward("success");
	}
}