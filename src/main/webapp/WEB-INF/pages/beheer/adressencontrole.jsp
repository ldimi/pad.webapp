<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div style="margin: 50px;" align="left">
		<h3>Controleer adres</h3>


	<c:if test="${not empty errorMsg}">
		<div class="error" style="margin: 10px;">
			${errorMsg}
		</div>
	</c:if>

	<form action="./s/adres/controleresultaat">

		<logic:equal parameter="popup" value="yes">
			<input type="hidden" name="popup" value="yes" />
		</logic:equal>

		<table  class="windowfree">
			<tr>
				<td>Naam</td>
				<td >
					<input type="text" name="naam_adres" class="input"/>
				</td>
			</tr>
			<tr>
				<td>Naam contactpersoon</td>
				<td >
					<input type="text" name="naam_contact" class="input"/>
				</td>
			</tr>
			<tr>
				<td>Gemeente</td>
				<td >
					<input type="text" name="gemeente" class="input"/>
				</td>
			</tr>
			<tr>
				<td>Provincie</td>
				<td >
					<select name="provincie" class="input">
						<option value=""></option>
						<option value="ANTWERPEN">Antwerpen</option>
						<option value="LIMBURG">Limburg</option>
						<option value="OOST VLAANDEREN">Oost Vlaanderen</option>
						<option value="VLAAMS BRABANT">Vlaams Brabant</option>
						<option value="WEST VLAANDEREN">West Vlaanderen</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Type</td>
				<td align='left'>
					<select name="type_id" class="input">
						<option value=""></option>
	                    <c:forEach var="item" items="${DDH.adrestypes}" >
	                        <option value="${item.adres_type_id}">${item.adres_type_b}</option>
	                    </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="actief_s" value="1"  />Inclusief niet actief
				</td>
			</tr>
			<tr>
				<td colspan="2" align='right'>
					<input type="submit" class="inputbtn" value="Zoeken"/>
				</td>
			</tr>
		</table>
	</form>

</div>
