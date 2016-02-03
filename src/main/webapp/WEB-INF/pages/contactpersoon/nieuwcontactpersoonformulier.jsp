<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<h1>Maak een nieuwe contactpersoon</h1>
<br>
<c:if test="${not empty contactpersonen}">
	<div class="error" style="margin: 10px;font-size: 15px">	
		Opgelet: deze contactpersoon bestaat al! <br>
		<c:forEach items="${contactpersonen}" var="item">		
    		${item}<br>
		</c:forEach>
	</div>		
</c:if>
<br>
<form:form id="contactpersoon" modelAttribute="contactpersoon" name="contactpersoon">
	<table>
		<tr>
			<td>Achternaam:</td>
			<td>
				<form:input path="achternaam" cssClass="input"/>
				<form:errors path="achternaam"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Voornaam:</td>
			<td>
				<form:input path="voornaam" cssClass="input"/>
				<form:errors path="voornaam"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>
				<form:input path="email" cssClass="input"/>
				<form:errors path="email"  style="color: red;"/>
			</td>
		</tr>
				<tr>
			<td>Telefoonnummer:</td>
			<td>
				<form:input path="tel" cssClass="input"/>
				<form:errors path="tel"  style="color: red;"/>
			</td>				
		</tr>		
		</tr>
				<tr>
			<td>Gsm:</td>
			<td>
				<form:input path="gsm" cssClass="input"/>
				<form:errors path="gsm"  style="color: red;"/>
			</td>				
		</tr>	
		<tr>
			<td>Fax:</td>
			<td>
				<form:input path="fax" cssClass="input"/>
				<form:errors path="fax"  style="color: red;"/>
			</td>				
		</tr>	
		<tr>
			<td>
			<input type="hidden" value="${rechtspersoonid}">
			<button type="submit">Nieuwe Rechtspersoon aanmaken</button></td>
		</tr>
	</table>
</form:form>