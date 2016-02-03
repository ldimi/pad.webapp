package be.ovam.art46.struts.action;

import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.actionform.ActieZoekForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActieZoekResultAction extends ZoekAction {
	
	//private ActieService actieService = (ActieService) LoadPlugin.applicationContext.getBean("actieService");
	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActieZoekForm zoekForm = (ActieZoekForm) form;
		if (zoekForm.getDoss_hdr_id() == null || zoekForm.getDoss_hdr_id().length() == 0) {
			zoekForm.setDossier_id(null);
		}
		//return actieService.getActieZoekResult(zoekForm);
		return sqlSession.selectList("be.ovam.art46.mappers.ActieMapper.getActieZoekResult", zoekForm);
	}
}
