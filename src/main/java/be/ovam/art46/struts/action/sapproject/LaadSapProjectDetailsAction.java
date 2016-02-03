package be.ovam.art46.struts.action.sapproject;

import be.ovam.art46.struts.actionform.DossierArt46Form;
import be.ovam.art46.struts.actionform.DossierBOAForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LaadSapProjectDetailsAction  extends Action {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons) throws Exception {
		
		String project_id = request.getParameter("project_id");

		List sapProjectLijst = sqlSession.selectList("be.ovam.art46.mappers.ProjectMapper.getSapProjectLijst", project_id);
		request.setAttribute("sapproject", sapProjectLijst);

        List sapProjectFactuurLijst = sqlSession.selectList("be.ovam.art46.mappers.ProjectMapper.getSapProjectFactuurLijst", project_id);
        request.setAttribute("sapprojectfactuurlijst", sapProjectFactuurLijst);

        List sapProjectVastleggingLijst = sqlSession.selectList("be.ovam.art46.mappers.ProjectMapper.getSapProjectVastleggingLijst", project_id);
        request.setAttribute("sapprojectvastlegginglijst", sapProjectVastleggingLijst);

        List sapProjectOrdonnanceringLijst = sqlSession.selectList("be.ovam.art46.mappers.ProjectMapper.getSapProjectOrdonnanceringLijst", project_id);
        request.setAttribute("sapprojectordonnanceringlijst", sapProjectOrdonnanceringLijst);

		return mapping.findForward("success");
	}
}