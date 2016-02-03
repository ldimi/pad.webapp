<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<tilesx:useAttribute id="titleKey" name="titleKey" classname="java.lang.String" ignore="true" />
<tilesx:useAttribute id="title" name="title" classname="java.lang.String" ignore="true" />

<html>
    <head>
        <c:if test="${empty title && !empty titleKey}">
            <title><bean:message name="titleKey" scope="page"/></title>
        </c:if>
        <c:if test="${!empty title}">
            <title>${title}</title>
        </c:if>

        <tiles:insertTemplate template="/WEB-INF/pages/layouts/headIncl.jsp" />

    </head>
    <body  >
        <table width="100%">
            <tr>
                <td align="center">
                    <html:errors />
                    <logic:messagesPresent message="true">
                        <strong>
                            <ul class="notification">
                                <html:messages id="message" message="true">
                                    <bean:write name="message"/>
                                </html:messages>
                            </ul>
                        </strong>
                    </logic:messagesPresent>
                </tr>
            </tr>
            <tr>
                <td align="center">
                    <tiles:insertAttribute name="body" />
                </td>
            </tr>
        </table>
    </body>
</html>