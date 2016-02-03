<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:useAttribute id="titleKey" name="titleKey" classname="java.lang.String" ignore="true" />
<tiles:useAttribute id="title" name="title" classname="java.lang.String" ignore="true" />

<html>
	<head>
	    <c:if test="${empty title && !empty titleKey}">
	        <title><bean:message name="titleKey" scope="page"/></title>
	    </c:if>
	    <c:if test="${!empty title}">
	        <title>${title}</title>
	    </c:if>

	    <tiles:insert page="/WEB-INF/pages/layouts/headIncl.jsp" />

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
					<tiles:insert attribute="body" />
				</td>
			</tr>
		</table>
	</body>
</html>