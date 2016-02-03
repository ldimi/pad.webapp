<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:useAttribute id="titleKey" name="titleKey" classname="java.lang.String" ignore="true" />
<tiles:useAttribute id="title" name="title" classname="java.lang.String" ignore="true" />
<tiles:useAttribute id="menuId" name="menuId" classname="java.lang.String" ignore="true" />

<tiles:useAttribute id="tab_selected" name="tab_selected" ignore="true" />
<tiles:useAttribute id="tab_body" name="tab_body" ignore="true" />

<html>
    <head>
        <c:if test="${empty custom_title && empty title && !empty titleKey}">
            <title><bean:message name="titleKey" scope="page"/></title>
        </c:if>
        <c:if test="${empty custom_title && !empty title}">
            <title>${title}</title>
        </c:if>
        <c:if test="${!empty custom_title}">
            <title>${custom_title}</title>
        </c:if>

        <tiles:insert attribute="customHeadIncl"   ignore="true" />
        <tiles:insert page="/WEB-INF/pages/layouts/headIncl.jsp" />

        <link rel="shortcut icon" href="/pad/resources/images/p.16.gif" type="image/x-icon" >

    </head>
    <body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0"  >
        <div style="height: 100%; position: relative;">
            <div id="sidebarDiv" >
                <tiles:insert attribute="menu" >
                    <tiles:put name="menuId" value="<%= menuId%>" />
                </tiles:insert>
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
                <tiles:insert attribute="body" >
                    <tiles:put name="tab_selected" value="<%= tab_selected%>" />
                    <tiles:put name="tab_body" value="<%= tab_body%>" />
                </tiles:insert>
            </div>
        </div>

        <tiles:insert page="/WEB-INF/pages/layouts/notifyContainer.jsp" />

    </body>


</html>