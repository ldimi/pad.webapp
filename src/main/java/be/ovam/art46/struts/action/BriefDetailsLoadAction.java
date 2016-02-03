package be.ovam.art46.struts.action;

import be.ovam.art46.dao.BriefDAO;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.BriefForm;
import be.ovam.art46.struts.plugin.LoadPlugin;

import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

public class BriefDetailsLoadAction extends Action {

    private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	private BriefDAO briefDAO = (BriefDAO) LoadPlugin.applicationContext.getBean("briefDAO");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionErrors errors) throws Exception {
		BriefForm briefForm = (BriefForm) form;
        Map briefData;
				
        briefData = sqlSession.selectOne("be.ovam.art46.mappers.BriefMapper.getBriefDetails", briefForm.getBrief_id());
        LocaleBeanUtils.copyProperties(briefForm, briefData);
        
//        if(briefData.get("qr_code")!=null) {
//            briefForm.setQr_code(briefData.get("qr_code").toString());
//        }
            
        briefForm.setContactList(briefDAO.getContacts(Integer.valueOf(briefForm.getAdres_id())));
        
		briefForm.setCategorieen(briefDAO.getBriefCategorien(briefForm.getDossier_id()));
		
		return null;
	}
}
