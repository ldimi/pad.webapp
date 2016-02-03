package be.ovam.art46.struts.tiles.controller;

import be.ovam.art46.struts.actionform.BriefForm;
import be.ovam.art46.struts.plugin.LoadPlugin;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BriefLoadBestekkenController implements Controller {

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	@SuppressWarnings("rawtypes")
	public void perform(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {		

		
		if (request.getSession().getAttribute("briefform") != null) {
			BriefForm briefForm = (BriefForm) request.getSession().getAttribute("briefform");
			
			Integer  dossierId = briefForm.getDossier_id();
			if (dossierId != null) {
		        Map<String, Object> params= new HashMap<String, Object>();
		        params.put("id", dossierId);
		        params.put("afgesloten_jn", "N");
		                
				List bestekLijst = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getBestekLijst", params);
				request.setAttribute("briefdetailsopenbesteklijst", bestekLijst);
			}
		}
	}
	
	public void execute(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		perform(context, request, response, servletContext);	
	}
}