<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<%@ taglib uri="/tags/display-tags" prefix="display" %>

<script type="text/javascript"	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
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
		$("#gemeente1").autocomplete({
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
				$("#postcode1").val(ui.item.value);
			}
		});
	});
	
	
	$(function() {			
		$("#straatnaam1").autocomplete({				
			source: "/pad/s/adres/stelstratenvoor",
			minLength: 2,
			select: function(event, ui){}				
		});
	});
	//TODO: koppel postcode aan de gemeente
	//TODO: koppel straten aan de gemeente
// 	$(function() {			
// 		$("#straatnaam1").autocomplete({				
// 			source: "/pad/s/adres/stelstratenvoor2",
// 			minLength: 2,
// 			select: function(event, ui){}				
// 		});
// 	});

	
	

	$(function() {			
		$("#land1").autocomplete({				
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
				
				$("#land1").val(ui.item.value);
			}			
		});
	});
	
</script>
<script>
$(function() {		
	$("#postcode1").autocomplete({
		source: "/pad/s/adres/stelgemeentenvoorviapostcode",
		minLength: 2,
		focus: function(event, ui) {
			// prevent autocomplete from updating the textbox
			event.preventDefault();
			// manually update the textbox
//				$(this).val(ui.item.label+", "+ ui.item.value);
			$(this).val(ui.item.combo);
		},
		select: function(event, ui) {
			// prevent autocomplete from updating the textbox
			event.preventDefault();
			// manually update the textbox and hidden field
			$(this).val(ui.item.postcode);
			$("#gemeente1").val(ui.item.value);
		}
	});
});
</script>

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
<script>
$(document).ready(function(){
	$("#enableEditingRP").click(function(){
		if($(this).prop('checked')){
			$("#emailadres").prop("disabled", false);
			$("#tel").prop("disabled", false);
			$("#website").prop("disabled", false);
			$("#vkbonr").prop("disabled", false);
			$("#editbutton").prop("disabled", false);
			}else{
				$("#emailadres").prop("disabled", true);
				$("#tel").prop("disabled", true);
				$("#website").prop("disabled", true);
				$("#editbutton").prop("disabled", true);
				$("#vkbonr").prop("disabled", true);
				}		
		
		
		});
});
</script>

<script>
$(document).ready(function(){
	 $("#nieuwadres").hide();
	 $("#zoekadres").hide();
  $("#zoekAdresButton").click(function(){
	$("#zoekadres").show();
    $("#nieuwadres").hide();
  }); 
  $("#nieuwAdresButton").click(function(){
		$("#nieuwadres").show();
	    $("#zoekadres").hide();
	  }); 
});

</script>

<script>
$(document).ready(function(){
	 $("#nieuwcontactpersoon").hide();
	$("#CPtoevoegen").click(function(){
		if($(this).prop('checked')){
			$("#nieuwcontactpersoon").show();
			$("#maakCPbutton").prop("disabled", false);
			}else{
				$("#nieuwcontactpersoon").hide();
				$("#maakCPbutton").prop("disabled", true);
				}		
		
		
		});
});
</script>

<h1  >Beheer Rechtspersoon</h1>



<c:if test="${empty rechtspersoonid}">
	<h2>Zoek een rechtspersoon</h2>

	<form action="">
		<input  id="showRP" type="radio" name="persoon" checked="checked">Rechtspersoon
		<input id="showNP" type="radio" name="persoon">Natuurlijk Persoon          
	</form>
	
	<form:form action="/pad/s/beheer/zoek" id="rechtsPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
	<table>
			<tr>
				<td>Naam bedrijf:</td>
				<td>
					<form:input path="naam" cssClass="input" />				
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
						<button type="submit">zoek de rechtspersoon</button></td>
		
	</form:form>
	<form:form action="/pad/s/beheer/zoek" id="natuurlijkPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
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
				<button type="submit">zoek de persoon</button></td>
	</form:form>


	<c:if test="${not empty gevondenpersonen and fn:length(gevondenpersonen) < 200}">
			<br>
			<br>
			<br>
			
			<table>
			<thead>	
				<tr>
					<td></td>				
					<td>naam</td>
					<td>naam2</td>
				</tr>
			</thead>
			<tbody>
			
			<c:forEach items="${gevondenpersonen}" var="item" >
				<tr>
					<td>
					<form:form action="/pad/s/beheer/selecteer" method="POST">
					<input type="submit" value="selecteer" />
					<input type="hidden" name="rechtspersoonid" value="${item.id}" />
						</form:form>
					</td>				
					<td>${item.naam}
					</td>
					<td>${item.naam2}
					</td>
				</tr>
				</c:forEach>
			</tbody>
			</table>		
		
	</c:if>
	<c:if test="${not empty gevondenpersonen and fn:length(gevondenpersonen) >= 200}">
	Je hebt meer dan 200 resultaten! verfijn je zoekopdracht.
	</c:if>
	
<!-- Eerste mensen laten zoeken, dan, ook al zijn er resultaten aanbieden nieuwe persoon te maken	 -->
	<c:if test="${gevondenpersonen != null}">
		<br>
		<br>Zit de persoon niet tussen je zoekresultaten, probeer hem dan aan te maken.
		<br>
		<form action="/pad/s/nieuwerechtspersoon" method="get">
			<input type="submit" value="Nieuwe rechtspersoon maken" />
		</form>
		<inp
	</c:if>
</c:if>

<c:if test="${not empty rechtspersoonid}">

<%-- <form action="/pad/s/zoeken/beheer" method="get"> --%>
<!-- <button>Andere rechtspersoon kiezen</button> -->
<%-- </form> --%>
	<h2>Algemene info rechtspersoon</h2>

<form:form  action="/pad/s/beheer/update" method="POST" 
	id="rechtsPersoon" 
	modelAttribute="rechtspersoon" 
	name="rechtspersoon">
	<table>
		<tr>
			<td>Naam bedrijf:</td>
			<td>
				<form:input path="naam" cssClass="input"  readonly="true"/>
				<form:errors path="naam"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Afdeling:</td>
			<td>
				<form:input path="naam2" cssClass="input" readonly="true" />
				<form:errors path="naam2"  style="color: red;"/>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>
				<form:input path="emailadres" cssClass="input" disabled="true"/>
				<form:errors path="emailadres"  style="color: red;"/>
			</td>
		</tr>
				<tr>
			<td>Telefoonnummer:</td>
			<td>
				<form:input path="tel" cssClass="input" disabled="true"/>
				<form:errors path="tel"  style="color: red;"/>
			</td>				
		</tr>		
		</tr>
				<tr>
			<td>website:</td>
			<td>
				<form:input path="website" cssClass="input" disabled="true"/>
				<form:errors path="website"  style="color: red;"/>
			</td>				
		</tr>		
				<tr>
			<td>VKBOnummer:</td>
			<td>
				<form:input path="vkbonr" cssClass="input" disabled="true"/>
				<form:errors path="vkbonr"  style="color: red;"/>
			</td>				
		</tr>		
		<tr>
			<td>
			<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/>
			<input type="checkbox" id="enableEditingRP" value="false"/>
			<form:button type="submit" id="editbutton" disabled="true">rechtspersoon aanpassen</form:button></td>
		</tr>
	</table>
</form:form>

		
	<br>
	<h2>gelinkte adressen</h2>


<c:if test="${not empty gelinkteAdressen}">
<div  class="rechtspersoonbeheer">
		<table>


				<th>Maatschappelijke zetel</th>
				<th>land</th>
				<th>gemeente</th>
				<th>postcode</th>
				<th>straatnaam</th>
				<th>huisnummer</th>
				<th>busnummer</th>
				<th colspan="2">opties</th>

				</thead>
		<tbody>
			<c:forEach items="${gelinkteAdressen}" var="item">
				<tr>	
					<td>
						<c:if test="${item.id==rechtspersoon.MZ_adres_id}">
						MZ =>
						</c:if>
					</td>				
					<td>${item.land}</td>
					<td>${item.gemeente}</td>
					<td>${item.postcode}</td>
					<td>${item.straatnaam}</td>
					<td>${item.huisnummer}</td>
					<td>${item.busnummer}</td>
					<td>
								<form:form action="/pad/s/beheer/unlinkrechtspersoonadres" method="POST">									
									<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}" />
									<input type="hidden" name="adresid" value="${item.id}" />
									<input type="submit" value="unlink" />
								</form:form>
					</td>
					<td>
								<form:form action="/pad/s/beheer/markeerMZ" method="POST">									
									<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}" />
									<input type="hidden" name="adresid" value="${item.id}" />
									<input type="submit" value="Markeer als maatschappelijke zetel" />
								</form:form>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
<%-- 	<display:table id="detailOverzichtTable" name="requestScope.gelinkteAdressen" export="true" defaultsort="1"> --%>

<%--             <display:column title="Lijn_id" sortable="true" property="lijn_id" /> --%>
<%--             <display:column title="land" sortable="true" property="land" /> --%>
<%--             <display:column title="gemeente" sortable="true" property="gemeente" /> --%>
<%--             <display:column title="straatnaam" sortable="true" property="straatnaam" /> --%>
<%--             <display:column title="huisnummer" sortable="true" property="huisnummer" /> --%>
<%--             <display:column title="busnummer" sortable="true" property="busnummer" /> --%>
<%--              <display:column title="optie" sortable="false" property="busnummer" /> --%>
             
<!-- 			<td> -->
<%-- 				<form:form action="/pad/s/beheer/unlinkrechtspersoonadres" method="POST">									 --%>
<%-- 					<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}" /> --%>
<%-- 					<input type="hidden" name="adresid" value="${item.id}" /> --%>
<!-- 					<input type="submit" value="unlink" /> -->
<%-- 				</form:form> --%>
<!-- 			</td> -->
<!-- 			<td> -->
<%-- 				<form:form action="/pad/s/beheer/markeerMZ" method="POST">									 --%>
<%-- 					<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}" /> --%>
<%-- 					<input type="hidden" name="adresid" value="${item.id}" /> --%>
<!-- 					<input type="submit" value="Markeer als maatschappelijke zetel" /> -->
<%-- 				</form:form> --%>
<!-- 			</td> -->
<%--         </display:table> --%>
	
 </div>
</c:if>

<c:if test="${empty gelinkteAdressen}">
	Geen adressen gelinkt	
	<br>
	Wil je een nieuw adres toevoegen?
	<br>
</c:if>	
<c:if test="${not empty gevondenadressen and fn:length(gevondenadressen) < 200}">
		
		<h2>Gevonden adressen:</h2>
<div  class="rechtspersoonbeheer">
			<table>


				<th>Select to link</th>
				<th>land</th>
				<th>gemeente</th>
				<th>postcode</th>
				<th>straatnaam</th>
				<th>naam</th>
				<th>huisnummer</th>


				<tbody>			
					<c:forEach items="${gevondenadressen}" var="item">
						<tr>
							<td>
								<form:form action="/pad/s/beheer/linkrechtspersoonadres" method="POST">									
									<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}" />
									<input type="hidden" name="adresid" value="${item.id}" />
									<input type="submit" value="selecteer" />
								</form:form>
							</td>
							<td>${item.land}</td>
							<td>${item.gemeente}</td>
							<td>${item.postcode}</td>
							<td>${item.straatnaam}</td>
							<td>${item.huisnummer}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	<br><br>
 </div>
</c:if>
<c:if test="${not empty gevondenpersonen and fn:length(gevondenpersonen) >= 200}">
	Je hebt meer dan 200 resultaten! verfijn je zoekopdracht.
	</c:if>
	<form action="">
		<br>
		<input id="zoekAdresButton" type="radio" name="adres">zoek bestaand adres</input>
		<br>
		<input  id="nieuwAdresButton" type="radio" name="adres" >Adres maken en toevoegen</input>
	</form>	
	<c:if test="${overbodigAdres==true }">
		<p style="color: red;">Dit adres is al toegevoegd!</p>
	</c:if>
	<form:form action="/pad/s/beheer/maakadres"  modelAttribute="adres" name="adres" id="nieuwadres">
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
					<form:input path="land" id="land1" cssClass="input"/>
					<form:errors path="land"  style="color: red;"/>
				</td>
			</tr>
			<tr>
				<td>postcode:</td>
				<td>
					<form:input path="postcode" id="postcode1" cssClass="input"/>
					<form:errors path="postcode"  style="color: red;"/>
				</td>
			</tr>
			<tr>
				<td>gemeente:</td>
				<td>
					<form:input path="gemeente" id="gemeente1" cssClass="input"/>
					<form:errors path="gemeente"  style="color: red;"/>
				</td>
			</tr>
			
					<tr>
				<td>straatnaamm:</td>
				<td>
					<form:input path="straatnaam"  id="straatnaam1" cssClass="input"/>
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
				<button type="submit">Nieuw adres aanmaken</button></td>
			</tr>
		</table>
	</form:form>
	
	<form:form action="/pad/s/beheer/zoekadressen"  modelAttribute="adres" name="adres" id="zoekadres">
						
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
						<td>
						<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/>
						<button type="submit">zoeken</button></td>
					</tr>
				</table>
	</form:form>	
											
											

												







<br>
<h2>gelinkte contactpersonen</h2>

<c:if test="${not empty gelinkteContactpersonen}">
<div  class="rechtspersoonbeheer">
	<table>
									
				<th>achternaam</th>
				<th>voornaam</th>
				<th>tel</th>
				<th>gsm</th>
				<th>fax</th>
				<th>email</th>
				<th>opties</th>

		<tbody>
		
		<c:forEach items="${gelinkteContactpersonen}" var="item" >
			<tr>
							
				<td>${item.achternaam}
				</td>
				<td>${item.voornaam}
				</td>						
				<td>${item.tel}
				</td>
				<td>${item.gsm}
				</td>
				<td>${item.fax}
				</td>
				<td>
					<a href="mailto:${item.email}">${item.email}</a>
				</td>
				<td>
					<form:form action="/pad/s/beheer/verwijdercontactpersoon" method="POST">									
									<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}" />
									<input type="hidden" name="contactpersoonid" value="${item.id}" />
									<input type="submit" value="verwijder" />
					</form:form>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</c:if>

	<form:form action="/pad/s/beheer/maakcontact"  modelAttribute="contactpersoon" name="contactpersoon" id="nieuwcontactpersoon">
		
		<table>
			<tr>
				<td>achternaam:</td>
				<td>
					<form:input path="achternaam" cssClass="input"/>
				</td>
			</tr>
			<tr>
				<td>voornaam:</td>
				<td>
					<form:input path="voornaam" cssClass="input"/>
				</td>
			</tr>
			<tr>
				<td>tel:</td>
				<td>
					<form:input path="tel" cssClass="input"/>
				</td>
			</tr>
					<tr>
				<td>gsm:</td>
				<td>
					<form:input path="gsm" cssClass="input"/>
				</td>				
			</tr>
			<tr>
				<td>fax:</td>
				<td>
					<form:input path="fax" cssClass="input"/>
				</td>
			</tr>
			<tr>
				<td>email:</td>
				<td>
					<form:input path="email" cssClass="input"/>
				</td>
			</tr>
			<tr>
				<td>
				
				<form:input type="hidden" path="rechtspersoon_id" value="${rechtspersoonid}" />
				<input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}" />
				<button type="submit" id="maakCPbutton"disabled="true">Nieuwe contactpersoon aanmaken</button></td>
			</tr>
		</table>
	</form:form>			

	<form action="">
				<input type="checkbox" id="CPtoevoegen" value="false"/>Wil je een nieuwe contactpersoon toevoegen?	
	</form>



<c:if test="${empty gelinkteContactpersonen}">
		Er zijn geen contactpersonen gevonden.<br>
</c:if>
</c:if>
