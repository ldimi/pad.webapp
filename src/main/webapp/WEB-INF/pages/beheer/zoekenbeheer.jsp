<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript"	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>

<script>
$(document).ready(function(){
	 $("#persoon").hide();
	 $("#contactpersoon").hide();
	    $("#adres").hide();
  $("#zoekpersoon").click(function(){
	$("#persoon").show();
    $("#contactpersoon").hide();
    $("#adres").hide();
  });
  $("#zoekadres").click(function(){
    $("#adres").show();
    $("#persoon").hide();
    $("#contactpersoon").hide();
  });
  $("#zoekcontactpersoon").click(function(){
	    $("#contactpersoon").show();
	    $("#adres").hide();
	    $("#persoon").hide();
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




<h1>Zoeken naar: </h1>
<form action="">
		<h3><input id="zoekpersoon" type="radio" name="zoek"  >Rechtspersonen en Natuurlijke personen<br></h3>
		<h3><input id="zoekadres" type="radio" name="zoek">Adressen      <br></h3>
		<h3><input id="zoekcontactpersoon" type="radio" name="zoek">contactpersonen      <br></h3>
	</form>
<div id="persoon">

	<form action="/pad/s/zoeken/beheer/rechtspersoon">
		<input  id="showRP" type="radio" name="persoon" checked="checked">Rechtspersoon
		<input id="showNP" type="radio" name="persoon">Natuurlijk Persoon          
	</form>
	
	<form:form action="/pad/s/zoeken/beheer/rechtspersoon" id="rechtsPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
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
	<form:form action="/pad/s/zoeken/beheer/rechtspersoon" id="natuurlijkPersoon" modelAttribute="rechtspersoon" name="rechtspersoon">
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


</div>	
<div id="adres">
<form:form action="/pad/s/zoeken/beheer/adres"  modelAttribute="adres" name="adres" >
						
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
</div>
<div id="contactpersoon">
	<form:form action="/pad/s/zoeken/beheer/contactpersoon"  modelAttribute="contactpersoon" name="contactpersoon" >
		
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
				<td>				
				<button type="submit">Contactpersoon zoeken</button></td>
			</tr>
		</table>
	</form:form>
</div>	

<c:if test="${gevondenpersonen!=null and fn:length(gevondenpersonen) >= 200}">
Je hebt meer als 50 resultaten gevonden, pas je query aan!
</c:if>
<c:if test="${gevondenpersonen!=null and fn:length(gevondenpersonen) < 200}">
<div class="rechtspersoonbeheer">
			<h2>gevonden personen</h2>
			<table>

			<th>naam</th>
			<th>naam2</th>
			<th>emailadres</th>
			<th>telefoonnummer</th>
			<th>website</th>
			<th>Opties</th>

			<tbody>
			
			<c:forEach items="${gevondenpersonen}" var="item" >
				<tr>								
					<td>${item.naam}</td>
					<td>${item.naam2}</td>
					<td>${item.emailadres}</td>
					<td>${item.tel}</td>
					<td>${item.website}</td>
					<td>
					<form:form action="/pad/s/beheer/selecteer" method="POST">
						<input type="submit" value="selecteer" />
						<input type="hidden" name="rechtspersoonid" value="${item.id}" />
					</form:form>
					</td>	
				</tr>
				</c:forEach>
			</tbody>
			</table>		

	
	
	
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
	</div>
</c:if>
<c:if test="${gevondenadressen!=null and fn:length(gevondenadressen) >= 50}">
Je hebt meer als 50 resultaten gevonden, pas je query aan!
</c:if>
<c:if test="${gevondenadressen!=null and fn:length(gevondenadressen) < 50}">
<div class="rechtspersoonbeheer">
<h2>gevonden personen</h2>
			<table>
				
						
						<th>land</th>
						<th>gemeente</th>
						<th>postcode</th>
						<th>straatnaam</th>
						
						<th>huisnummer</th>
						<th>naam</th>
						<th></th>
					
				<tbody>			
					<c:forEach items="${gevondenadressen}" var="item">
						<tr>
							
							<td>${item.land}</td>
							<td>${item.gemeente}</td>
							<td>${item.postcode}</td>
							<td>${item.straatnaam}</td>
							<td>${item.huisnummer}</td>
							<td>${item.rechtspersoonFullname}</td>
							<td>
								<form:form action="/pad/s/beheer/selecteer" method="POST">								
									<input type="hidden" name="rechtspersoonid" value="${item.rechtspersoonid}" />
									<input type="submit" value="selecteer" />
								</form:form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
</c:if>

<c:if test="${gevondencontactpersonen!=null and fn:length(gevondencontactpersonen) >= 50}">
Je hebt meer als 50 resultaten gevonden, pas je query aan!
</c:if>

<c:if test="${gevondencontactpersonen!=null and fn:length(gevondencontactpersonen) < 50 and fn:length(gevondencontactpersonen) >1}">
<div class="rechtspersoonbeheer">
<h2>gevonden contactpersonen</h2>
			<table>
				<th>Naam rechtspersoon</th>								
				<th>achternaam</th>
				<th>voornaam</th>
				<th>tel</th>
				<th>gsm</th>
				<th>fax</th>
				<th>email</th>
				<th>opties</th>
		
		<tbody>
		
		<c:forEach items="${gevondencontactpersonen}" var="item" >
			<tr>
				<td>${item.rechtspersoonFullname }			
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
					<form:form action="/pad/s/beheer/selecteer" method="POST">									
									<input type="hidden" name="rechtspersoonid" value="${item.rechtspersoon_id}" />		
									<input type="submit" value="Beheer rechtspersoon" />
					</form:form>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</c:if>