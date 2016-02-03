<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/display-tags" prefix="display"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h3>Login formulier</h3>   


<c:set var="pageTitle" value="Please Login" scope="request" />
<c:url value="/s/j_spring_security_check" var="loginUrl" />
<form action="${loginUrl}" method="post">
	<c:if test="${param.error != null}">
		<div class="alert alert-error">
			Failed to login.
			<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
				Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
			</c:if>
		</div>
	</c:if> 
	
	
	<c:if test="${param.denied != null}">
		U heeft geen toegang tot deze applicatie: de rol mistral in LDAP ontbreekt. Gelieve hiervoor contact op te nemen met de helpdesk via het kace systeem. 
	</c:if> 
	
	
	<c:if test="${param.logout != null}">
		<div class="alert alert-success">Je bent afgemeld.</div>
	</c:if>



	<table class="displayTable" style="width: 400px;">
		<tr>
			<td><label for="username">gebruikersnaam</label></td>
			<td width="300px"><input type="text" id="username" name="username" /></td>
		</tr>
		<tr>
			<td><label for="password">Paswoord</label></td>
			<td><input type="password" id="password" name="password" style="width: 100%;" /></td>
		</tr>
	</table>

	
	<div class="form-actions">
		<input id="submit" class="inputbtn" name="submit" type="submit"	value="Login" />
	</div>
</form>
