<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
	response.sendRedirect( ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform") ).getUrl());
%>