<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script type="text/javascript"	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>

<script>

$(document).ready(function(){
	 $("#nieuwAdres").hide();
  $("#showNA").click(function(){
	$("#nieuwAdres").show();
    $("#bestaandAdres").hide();
  });
  $("#showBA").click(function(){
    $("#bestaandAdres").show();
    $("#nieuwAdres").hide();
  });
});
</script>

<style>
.ui-autocomplete-loading {
	background: white url('resources/images/ui-anim_basic_16x16.gif') right center
		no-repeat;
}
</style>
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.min.css" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href='style/slickGrid/slick.grid.css' />
<link rel="stylesheet" type="text/css" href='style/slickGrid/slick-default-theme.css' />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script data-main="resources/js/pad/main" src="resources/js/require-2.1.6.min.js"></script>


<script>
	$(function() {		
		$("#gemeente2").autocomplete({
			source: "/pad/s/adres/stelgemeentenvoor",
			minLength: 2,
			focus: function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// manually update the textbox
// 				$(this).val(ui.item.label+", "+ ui.item.value);
				$(this).val(ui.item.combo);
			},
			select: function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// manually update the textbox and hidden field
				$(this).val(ui.item.gemeente);
				$("#postcode2").val(ui.item.value);
			}
		});
	});
	
	//TODO: koppel postcode aan de gemeente
	//TODO: koppel straten aan de gemeente
	$(function() {			
		$("#straatnaam2").autocomplete({				
			source: "/pad/s/adres/stelstratenvoor",
			minLength: 2,
			select: function(event, ui){}				
		});
	});
	
	//TODO: nog landtabel creeëren?
	$(function() {			
		$("#land2").autocomplete({				
			source: "/pad/s/adres/stellandvoor",
			minLength: 1,
			focus: function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// manually update the textbox
				$(this).val(ui.item.label+", "+ ui.item.value);
			},
			select: function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// manually update the textbox and hidden field
				
				$("#land2").val(ui.item.value);
			}			
		});
	});
	
</script>


<c:if test="${gelinkt==true}">
	<h1>Uw link is geslaagd!</h1>
</c:if>

<h1>Link een Adres aan je Rechtspersoon</h1>



<c:if test="${gelinkteAdressen != null and not empty gelinkteAdressen }">
<h2>Deze adressen zijn al aan ${rechtspersoon.naam} gelinkt</h2>
	<table>
		<thead>
			<tr>
			
				<td>land</td>
				<td>gemeente</td>
				<td>postcode</td>
				<td>straatnaam</td>
				<td>huisnummer</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${gelinkteAdressen}" var="item">
				<tr>					
					<td>${item.land}</td>
					<td>${item.gemeente}</td>
					<td>${item.postcode}</td>
					<td>${item.straatnaam}</td>
					<td>${item.huisnummer}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<br>
<br>
</c:if>
<form action="">
<input  id="showBA" type="radio" name="adres" checked="checked">Bestaand adres zoeken
<input id="showNA" type="radio" name="adres">Nieuw adres maken
</form>


	<form:form action="/pad/s/adres_1/zoeken" id="bestaandAdres" modelAttribute="adres" name="adres">
	<h2>Zoek je adres en selecteer het</h2>
	<table>
			<tr>
				<td>Land:</td>
				<td>
					<form:input path="land" cssClass="input"/>				
				</td>
			</tr>
			<tr>
				<td>gemeente:</td>
				<td>
					<form:input path="gemeente" cssClass="input"/>				
				</td>
			</tr>
			<tr>
				<td>postcode:</td>
				<td>
					<form:input path="postcode" cssClass="input"/>				
				</td>
			</tr>
					<tr>
				<td>straatnaam:</td>
				<td>
					<form:input path="straatnaam" cssClass="input"/>				
				</td>				
			</tr>
			<tr>
				<td>huisnummer:</td>
				<td>
					<form:input path="huisnummer" cssClass="input"/>			
				</td>
			</tr>
			<tr>
				<td>busnummer:</td>
				<td>
					<form:input path="busnummer" cssClass="input"/>				
				</td>
			</tr>
			<tr>
				<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/>
				<td><button type="submit">Nieuw adres zoeken</button></td>
			</tr>
		</table>
	</form:form>


<form:form id="nieuwAdres" modelAttribute="adres" name="adres">
	<h2>Maak een nieuw adres</h2>
<c:if test="${not empty bestaandeAdressen}">
	<div class="error" style="margin: 10px;font-size: 15px">	
		Opgelet: dit adres bestaat al! --> eventueel zeggen of en aan wie dat het gelinkt is?<br>
		<c:forEach items="${bestaandeAdressen}" var="item">		
    		${item}<br>
		</c:forEach>
	</div>		
</c:if>

	<table>
		<tr>
			<td>Land:</td>
			<td>
				<form:input path="land" id="land2" cssClass="input"/>
				<form:errors path="land"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>gemeente:</td>
			<td>
				<form:input path="gemeente" id="gemeente2" cssClass="input"/>
				<form:errors path="gemeente"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>postcode:</td>
			<td>
				<form:input path="postcode" id="postcode2" cssClass="input"/>
				<form:errors path="postcode"  style="color: red;"/>
			</td>
		</tr>
				<tr>
			<td>straatnaam:</td>
			<td>
				<form:input path="straatnaam" id="straatnaam2" cssClass="input"/>
				<form:errors path="straatnaam"  style="color: red;"/>
			</td>				
		</tr>
		<tr>
			<td>huisnummer:</td>
			<td>
				<form:input path="huisnummer" cssClass="input"/>
				<form:errors path="huisnummer"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>busnummer:</td>
			<td>
				<form:input path="busnummer" cssClass="input"/>
				<form:errors path="busnummer"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>	
				<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/>
				<button type="submit">Nieuw adres aanmaken</button>
			</td>
		</tr>
	</table>
</form:form>
		
				
	
	<h1>Nog in te bouwen controles</h1>
		<ol>
			<li>kijk of de link die je wilt maken al in de databank zit
			</li>
			<li>maak een link naar "adres neit gevonden?"</li>
		
			<li></li>
			<li></li>
		</ol>
<h2>Bevestig</h2>

<c:if test="${not empty gevondenadressen and fn:length(gevondenadressen) < 50}">
		<br>
		<br>
		<br>
	<form action="/pad/s/rechtspersoon/linkrechtspersoonadres"	method="POST">
			<table>
				<thead>	
					<tr>
						<td>Select to link</td>
						<td>land</td>
						<td>gemeente</td>
						<td>postcode</td>
						<td>straatnaam</td>
						<td>naam</td>
						<td>huisnummer</td>
					</tr>
				</thead>
				<tbody>			
					<c:forEach items="${gevondenadressen}" var="item">
						<tr>
							<td><input 
										type="radio" 
										name="adresid"
										value="${item.id}"
								/></td>
							<td>${item.land}</td>
							<td>${item.gemeente}</td>
							<td>${item.postcode}</td>
							<td>${item.straatnaam}</td>
							<td>${item.huisnummer}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/>	
			<input type="submit" value="Link dit adres"></button>
	</form>
</c:if>
	
<c:if test="${empty gevondenadressen}">
	<br><br><br>
		<br>geen adressen gevonden<br>	
</c:if>
	
<%-- <c:if test="${adresid != null and rechtspersoonid != null}"> --%>
<%-- 	<form action="/linkrechtspersoonadres" method="post"> --%>
<%-- 		<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/> --%>
<%-- 		<input type="hidden" name="adresid" value="${adresid}"/> --%>
		
<!-- 		<button type="submit">Link het adres en de rechtspersoon</button> -->
<%-- 	</form> --%>
<%-- </c:if> --%>


			