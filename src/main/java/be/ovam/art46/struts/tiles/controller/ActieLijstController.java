package be.ovam.art46.struts.tiles.controller;

import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



public class ActieLijstController implements Controller {


	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

	
	@SuppressWarnings("rawtypes")
	public void perform(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {		
		String dossier_type = context.getAttribute("dossier_type").toString();
		String parent_id = context.getAttribute("parent_id").toString();
		List acties = null;
		
		if ("B".equals(dossier_type) || "A".equals(dossier_type)) {
			acties = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierActies", parent_id);
		} else if ("I".equals(dossier_type)) {
			acties = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierActiesNietGerealiseerd", parent_id);
		} else if ("J".equals(dossier_type)) {
			acties = sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierJdActies", parent_id);
		} else {
			throw new RuntimeException("Dit dossier_type wordt niet ondersteund : " + dossier_type);
		}
		request.setAttribute("acties", acties);
	}
	
	public void execute(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		perform(context, request, response, servletContext);	
	}
}
