package be.ovam.art46.struts.tiles.controller;

import be.ovam.art46.struts.actionform.AdresForm;
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



public class AdresContactLijstController implements Controller {

    private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");
	
	public void perform(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		
		String adres_id = ((AdresForm) request.getAttribute("adresform")).getAdres_id();
		List contacten = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getAdresContacten", adres_id);
		request.setAttribute("adresContactenlijst", contacten);
	}
	
	public void execute(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		perform(context, request, response, servletContext);	
	}

}
