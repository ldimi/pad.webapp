<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>



<h1>Consolideer hier de dubbele adressen!</h1>



<c:if test="${aantaldubbeleAdressen>0 }">
<br>
je heb er nog ${aantaldubbeleAdressen} (dit is het aantal dubbele adressen, dus niet 1 adres dat er 5 keer instaat, maar er wordt dan "5" geteld)
<br>
<b>Je kan pas met consolideren beginnen als alle adressen gecorrigeerd zijn!</b>
<br>
Het volgende te consolideren adres is:
<br>
<br>
<div class="rechtspersoonbeheer">
	<table>
		<th>land</th>
		<th>gemeente</th>
		<th>postcode</th>
		<th>straatnaam</th>
		<th>huisnummer</th>
		<th>busnummer</th>
		<th>optie</th>		
		<tbody>
			<c:forEach items="${dubbeleAdressen}" var="item">
				<tr>					
					<td>${item.land}</td>
					<td>${item.gemeente}</td>
					<td>${item.postcode}</td>
					<td>${item.straatnaam}</td>
					<td>${item.huisnummer}</td>
					<td>${item.busnummer}</td>
					<td>
						<form action="/pad/s/adres/markeerprimairadres" method="POST">
							<input type="hidden" name="adresid" value="${item.id }"/>
							<input type="submit" value="Markeer als Primair adres"/>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:if>	
<c:if test="${aantaldubbeleAdressen<=0 }">
Er zijn geen dubbele adressen meer in de databank
</c:if>	
	
	
	