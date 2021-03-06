package be.ovam.art46.struts.action.artikel46;

import be.ovam.art46.struts.actionform.Art46SelectForm;
import be.ovam.art46.struts.plugin.LoadPlugin;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LaadLijstNaGoedkeuringDossierAction  extends Action {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons) throws Exception {
		
	    Art46SelectForm art46selectform = (Art46SelectForm) form;
	    
        Map params = new HashMap();
        params.put("query_type", "NA_GOEDKEURING");
        params.put("artikel_id", art46selectform.getArtikelid());
        params.put("lijst_id", art46selectform.getLijst_id());
        
        List lijst = sqlSession.selectList("be.ovam.art46.mappers.Artikel46Mapper.getLijstDossier", params);
        request.getSession().setAttribute("lijst", lijst);
		
		return mapping.findForward("success");
	}
}