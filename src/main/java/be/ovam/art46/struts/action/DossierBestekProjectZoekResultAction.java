package be.ovam.art46.struts.action;

import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.actionform.DossierBestekProjectForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DossierBestekProjectZoekResultAction extends ZoekAction {

	private static NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);	

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map> resultList = sqlSession.selectList("be.ovam.art46.mappers.ProjectMapper.getDossierBestekProject", form);
		
		
		try {
			BigDecimal totaal = new BigDecimal(0);
			for (Map map : resultList) {
				totaal = totaal.add((BigDecimal) map.get("bedrag"));
			}
			
			((DossierBestekProjectForm) form).setTotaal_bedrag(formatter.format(totaal));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return resultList;
	}
}
