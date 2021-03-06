<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<tilesx:useAttribute id="title" name="title" classname="java.lang.String" ignore="true" />
<tilesx:useAttribute id="menuId" name="menuId" classname="java.lang.String" />

<tilesx:useAttribute id="tab_selected" name="tab_selected" ignore="true" />
<tilesx:useAttribute id="tab_body" name="tab_body" ignore="true" />
<tilesx:useAttribute id="sub_tab_body" name="sub_tab_body" ignore="true" />
<tilesx:useAttribute id="sub_tab_selected" name="sub_tab_selected" ignore="true" />

<html style="height: 100%;">
    <head>
        <c:if test="${empty custom_title && !empty title}">
            <title>${title}</title>
        </c:if>
        <c:if test="${!empty custom_title}">
            <title>${custom_title}</title>
        </c:if>

        <tiles:insertAttribute name="customHeadIncl"  ignore="true" />
        <tiles:insertTemplate template="/WEB-INF/pages/layouts/headIncl.jsp" />


        <link rel="shortcut icon" href="/pad/resources/images/favicon.ico" type="image/x-icon" />

    </head>
    <body  style="height: 100%; ">
        <div style="height: 100%; position: relative;">
            <div id="sidebarDiv" >
                <tiles:insertAttribute name="menu" >
                    <tiles:putAttribute name="menuId" value="${menuId}" />
                </tiles:insertAttribute>
            </div>
            <div id="contentDiv">
                <logic:messagesPresent message="true">
                    <div style="background-color: FFFFFF;" class="notification">
                        <strong>
                            <html:messages id="message" message="true">
                                <bean:write name="message"/>
                            </html:messages>
                        </strong>
                    </div>
                </logic:messagesPresent>
                <div><html:errors /></div>
                <div id="pageContentDiv">
                    <tiles:insertAttribute name="body" >
                        <tiles:putAttribute name="tab_selected" value="${tab_selected}" />
                        <tiles:putAttribute name="tab_body" value="${tab_body}" />
                        <tiles:putAttribute name="sub_tab_body" value="${sub_tab_body}" />
                        <tiles:putAttribute name="sub_tab_selected" value="${sub_tab_selected}" />

                    </tiles:insertAttribute>
                </div>
            </div>
        </div>

        <tiles:insertTemplate template="/WEB-INF/pages/layouts/notifyContainer.jsp" />

    </body>
</html>