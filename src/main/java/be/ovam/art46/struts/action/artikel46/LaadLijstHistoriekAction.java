package be.ovam.art46.struts.action.artikel46;

import be.ovam.art46.struts.actionform.Art46SelectForm;
import be.ovam.art46.struts.plugin.LoadPlugin;

import org.apache.commons.collections.map.HashedMap;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public class LaadLijstHistoriekAction  extends Action {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings({ "rawtypes" })
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons) throws Exception {
		
        Art46SelectForm art46selectform = (Art46SelectForm) form;
        
        List lijst = sqlSession.selectList("be.ovam.art46.mappers.Artikel46Mapper.getLijstHistoriek", art46selectform);
        request.getSession().setAttribute("lijst", lijst);
        
		return mapping.findForward("success");
	}
}