<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript"	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>

<script>

$(document).ready(function(){
	 $("#natuurlijkPersoon").hide();
  $("#showNP").click(function(){
	$("#natuurlijkPersoon").show();
    $("#rechtsPersoon").hide();
  });
  $("#showRP").click(function(){
    $("#rechtsPersoon").show();
    $("#natuurlijkPersoon").hide();
  });
});
</script>

<c:if test="${gelinkt==true}">
	<h1>Uw link is geslaagd!</h1>
</c:if>
<h1>Link een Rechtspersoon aan je adres</h1>
<h2>Zoek een rechtspersoon en selecteer</h2>
	<form action="">
		<input  id="showRP" type="radio" name="persoon" checked="checked">Rechtspersoon
		<input id="showNP" type="radio" name="persoon">Natuurlijk Persoon          
	</form>
	
	<form:form action="/pad/s/rechtspersoon/zoeken" id="rechtsPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
	<table>
			<tr>
				<td>Naam bedrijf:</td>
				<td>
					<form:input path="naam" cssClass="input"/>				
				</td>
			</tr>
			<tr>
				<td>Afdeling:</td>
				<td>
					<form:input path="naam2" cssClass="input"/>			
				</td>
			</tr>
			<tr>
				<td>Email:</td>
				<td>
					<form:input path="emailadres" cssClass="input"/>				
				</td>
			</tr>
					<tr>
				<td>Telefoonnummer:</td>
				<td>
					<form:input path="tel" cssClass="input"/>			
				</td>				
			</tr>		
			</tr>
					<tr>
				<td>website:</td>
				<td>
					<form:input path="website" cssClass="input"/>			
				</td>				
			</tr>		
		
		</table>
		
			<input type="hidden" name="adresid" value="${adresid}"/>
			
				<button type="submit">zoek de rechtspersoon</button></td>
		
	</form:form>
	<form:form action="/pad/s/rechtspersoon/zoeken" id="natuurlijkPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
	<table>
			<tr>
				<td>Achternaam:</td>
				<td>
					<form:input path="naam" cssClass="input"/>				
				</td>
			</tr>
			<tr>
				<td>Voornaam: </td>
				<td>
					<form:input path="naam2" cssClass="input"/>				
				</td>
			</tr>
			<tr>
				<td>Email:</td>
				<td>
					<form:input path="emailadres" cssClass="input"/>					
				</td>
			</tr>
					<tr>
				<td>Telefoonnummer:</td>
				<td>
					<form:input path="tel" cssClass="input"/>				
				</td>				
			</tr>		
			<tr>
				<td>
				
			</tr>
		</table>
		<input type="hidden" name="adresid" value="${adresid}"/>
			
				<button type="submit">zoek de persoon</button></td>
	</form:form>
	
	
<h2>Bevestig</h2>
<c:if test="${not empty gevondenpersonen and fn:length(gevondenpersonen) < 150 }">
		<br><br><br>
		<form:form action="/pad/s/adres_1/linkadresrechtspersoon" method="POST">
		<table>
		<thead>	
			<tr>
				<td>Select to link</td>
				<td>id</td>
				<td>naam</td>
				<td>naam2</td>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach items="${gevondenpersonen}" var="item" >
			<tr>
				<td>
				<input 
						type="radio"
						name="rechtspersoonid" 
						value="${item.id}"
						/>
				</td>
				<td>${item.id}
				</td>
				<td>${item.naam}
				</td>
				<td>${item.naam2}
				</td>
			</tr>
			</c:forEach>
		</tbody>
		</table>
		<input type="hidden" name="adresid" value="${adresid}"/>	
		<input type="submit" value="Link deze rechtspersoon"></button>
		</form:form>
	</c:if>
	
	<c:if test="${empty gevondenpersonen}">
		<br><br><br>
			<br>geen personen gevonden<br>	
	</c:if>

<%-- 	<c:if test="${adresid != null and rechtspersoonid != null}"> --%>
<%-- 	<form action="/linkadresrechtspersoon" method="post"> --%>
<%-- 		<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/> --%>
<%-- 		<input type="hidden" name="adresid" value="${adresid}"/> --%>
		
<!-- 		<button type="submit">Link het adres en de rechtspersoon</button> -->
<%-- 	</form> --%>
<%-- </c:if> --%>