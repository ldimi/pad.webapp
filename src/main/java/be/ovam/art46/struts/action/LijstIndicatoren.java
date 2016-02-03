package be.ovam.art46.struts.action;

import be.ovam.art46.decorator.BigDecimalDecorator;
import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class LijstIndicatoren extends ZoekAction {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings({ "rawtypes" })
	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List indicatoren = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getIndicatoren", form);
		setIndicatoren(indicatoren, request);
		return indicatoren;		
	}
	
	@SuppressWarnings("rawtypes")
	private void setIndicatoren(List indicatoren, HttpServletRequest request) {
		float aanpak_s = 0;
		float aanpak_onderzocht_s = 0;		
		if (indicatoren != null && indicatoren.size() != 0) {
			for (int t=0; t<indicatoren.size(); t++) {
				Map indicator = (Map) indicatoren.get(t);
				if ("1".equals(indicator.get("aanpak_s"))) {
					aanpak_s++;
				}
				if ("1".equals(indicator.get("aanpak_onderzocht_s"))) {
					aanpak_onderzocht_s++;
				}
			}
			request.getSession().setAttribute("aanpak_s_total", Double.valueOf(aanpak_s));
			request.getSession().setAttribute("aanpak_onderzocht_s_total", Double.valueOf(aanpak_onderzocht_s));
			request.getSession().setAttribute("aanpak_s_perc", BigDecimalDecorator.format(BigDecimal.valueOf(aanpak_s * 100/indicatoren.size())));
			request.getSession().setAttribute("aanpak_onderzocht_s_perc", BigDecimalDecorator.format(BigDecimal.valueOf(aanpak_onderzocht_s * 100/indicatoren.size())));
		} else {
			request.getSession().setAttribute("aanpak_s_total", null);
			request.getSession().setAttribute("aanpak_onderzocht_s_total", null);
			request.getSession().setAttribute("aanpak_s_perc", null);
			request.getSession().setAttribute("aanpak_onderzocht_s_perc", null);
		}
	}
	
	

}
