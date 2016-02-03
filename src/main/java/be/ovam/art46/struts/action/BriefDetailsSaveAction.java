package be.ovam.art46.struts.action;

import be.ovam.art46.dao.BriefDAO;
import be.ovam.art46.model.User;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.BriefForm;
import be.ovam.art46.struts.plugin.LoadPlugin;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Timestamp;


public class BriefDetailsSaveAction extends Action {
		
	private BriefDAO briefDAO = (BriefDAO) LoadPlugin.applicationContext.getBean("briefDAO");

    private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");;

	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		BriefForm briefForm = (BriefForm) form;
		
		// validatie : qrcode mag nog niet gebruikt zijn.
		if (briefForm.getParent_brief_id() == null && briefForm.getQr_code() != null) {
		    int count = (Integer) sqlSession.selectOne("be.ovam.art46.mappers.BriefMapper.countQrCode", briefForm);
		    
		    if (count > 0) {
	            errors.add("qr_code", new ActionError("error.qr_code.uniek"));  
	            saveErrors(request, errors);
	            return mapping.findForward("error");    
		    }
		}
		
		
		briefForm.setLtst_wzg_user_id( ((User) request.getSession().getAttribute("user") ).getUser_id());
		briefForm.setLtst_wzg_d(new Timestamp(System.currentTimeMillis()));
		
		briefDAO.saveOrUpdate(briefForm);
		
		ActionForward forward = mapping.findForward("success");
		ActionRedirect redirect = new ActionRedirect(forward);
		redirect.addParameter("brief_id", briefForm.getBrief_id());
		return redirect;
	}
}
