package be.ovam.art46.struts.action;

import be.ovam.art46.model.User;
import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IndexAction extends Action {
	
    private static Log log = LogFactory.getLog(IndexAction.class);
	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse respons)
			throws Exception {			
				
		ActionErrors errors = new ActionErrors();					
		try {		
			String user_id = ((User) request.getSession().getAttribute("user")).getUser_id();
			List<?> result = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossiersByDossHdr", user_id);
			request.getSession().setAttribute("zoeklijst", result);				
			ZoekAction.resetZoekAction(mapping,request);
			if (result.size()==0) {
				return mapping.findForward("success_empty");
			}			
		} catch (Exception e) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.db", e));	
			log.error(e.getMessage());
		} 
		if (!errors.isEmpty()) {
			saveErrors(request, errors);							
			return mapping.findForward("error");
		}
		return mapping.findForward("success");	 	 		
	}
}
