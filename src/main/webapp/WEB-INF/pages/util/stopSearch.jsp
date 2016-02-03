<%@ page language="java" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%
	String forwardURL = ((be.ovam.art46.struts.actionform.SearchForm) session.getAttribute("searchform")).getForwardURL().replaceAll("\"","");
	session.setAttribute("searchform", null);	
 	pageContext.forward(forwardURL);
%>
