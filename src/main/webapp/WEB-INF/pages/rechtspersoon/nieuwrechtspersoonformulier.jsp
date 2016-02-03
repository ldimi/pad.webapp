<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


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

<h1>Maak een nieuwe rechtspersoon</h1>
<br>

<c:if test="${not empty bestaanderechtspersonen}">
	<div class="error" style="margin: 10px;font-size: 15px">	
		Opgelet: deze perso(o)n(en) bestaat/bestaan al!<br>
		<ol>
		<c:forEach items="${bestaanderechtspersonen}" var="item">		
    		<li>Naam = ${item.naam}, ${item.naam2}<br>
    		Adres=${item.gemeente }, ${item.straatnaam } ${item.huisnummer } (${item.land }). <br> <br>
		
		</c:forEach>
		</ol>
	</div>		
</c:if>

<br>

<form action="">
	<input  id="showRP" type="radio" name="persoon" checked="checked">Rechtspersoon
	<input id="showNP" type="radio" name="persoon">Natuurlijk Persoon          
</form>
<br>
<br>
<c:if test="${confirm!=true}">
<form:form  method="POST" id="rechtsPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
	<table>
		<tr>
			<td>Naam bedrijf:</td>
			<td>
				<form:input path="naam" cssClass="input"/>
				<form:errors path="naam"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Afdeling:</td>
			<td>
				<form:input path="naam2" cssClass="input"/>
				<form:errors path="naam2"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>
				<form:input path="emailadres" cssClass="input"/>
				<form:errors path="emailadres"  style="color: red;"/>
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
			<td>website:</td>
			<td>
				<form:input path="website" cssClass="input"/>
				<form:errors path="website"  style="color: red;"/>
			</td>				
		</tr>	
		<tr>
			<td>vkbonr:</td>
			<td>
				<form:input path="vkbonr" cssClass="input"/>
				<form:errors path="vkbonr"  style="color: red;"/>
			</td>				
		</tr>		
		<tr>
			<td>
<%-- 			<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/> --%>
			<form:input path="id" cssClass="input" type="hidden"/>
			<button type="submit">Nieuwe Rechtspersoon aanmaken</button></td>
		</tr>
	</table>


</form:form>

<form:form id="natuurlijkPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
<table>
		<tr>
			<td>Achternaam:</td>
			<td>
				<form:input path="naam" cssClass="input"/>
				<form:errors path="naam"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Voornaam: </td>
			<td>
				<form:input path="naam2" cssClass="input"/>
				<form:errors path="naam2"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>
				<form:input path="emailadres" cssClass="input"/>
				<form:errors path="emailadres"  style="color: red;"/>
			</td>
		</tr>
				<tr>
			<td>Telefoonnummer:</td>
			<td>
				<form:input path="tel" cssClass="input"/>
				<form:errors path="tel"  style="color: red;"/>
			</td>				
		</tr>		
		<tr>
			<td>
<%-- 			<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/> --%>
	<form:input path="id" cssClass="input" type="hidden"/>
			<button type="submit">Nieuwe natuurlijke persoon aanmaken</button></td>
		</tr>
	</table>
</form:form>

</c:if>



<c:if test="${confirm==true}">
<form:form action="/pad/s/rechtspersoonbeheer/confirmdouble" method="POST" id="rechtsPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
	<table>
		<tr>
			<td>Naam bedrijf:</td>
			<td>
				<form:input path="naam" cssClass="input"/>
				<form:errors path="naam"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Afdeling:</td>
			<td>
				<form:input path="naam2" cssClass="input"/>
				<form:errors path="naam2"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>
				<form:input path="emailadres" cssClass="input"/>
				<form:errors path="emailadres"  style="color: red;"/>
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
			<td>website:</td>
			<td>
				<form:input path="website" cssClass="input"/>
				<form:errors path="website"  style="color: red;"/>
			</td>				
		</tr>	
		<tr>
			<td>vkbonr:</td>
			<td>
				<form:input path="vkbonr" cssClass="input"/>
				<form:errors path="vkbonr"  style="color: red;"/>
			</td>				
		</tr>			
		<tr>
			<td>
<%-- 			<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/> --%>
			<form:input path="id" cssClass="input" type="hidden"/>
			<button type="submit">Toch nieuwe Rechtspersoon aanmaken</button></td>
		</tr>
	</table>


</form:form>

<form:form id="natuurlijkPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
<table>
		<tr>
			<td>Achternaam:</td>
			<td>
				<form:input path="naam" cssClass="input"/>
				<form:errors path="naam"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Voornaam: </td>
			<td>
				<form:input path="naam2" cssClass="input"/>
				<form:errors path="naam2"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>
				<form:input path="emailadres" cssClass="input"/>
				<form:errors path="emailadres"  style="color: red;"/>
			</td>
		</tr>
				<tr>
			<td>Telefoonnummer:</td>
			<td>
				<form:input path="tel" cssClass="input"/>
				<form:errors path="tel"  style="color: red;"/>
			</td>				
		</tr>	
			
		<tr>
			<td>
<%-- 		<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/> --%>
			<form:input path="id" cssClass="input" type="hidden"/>
			<button type="submit">Toch nieuwe persoon aanmaken</button></td>
		</tr>
	</table>
</form:form>
</c:if>
