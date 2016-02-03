<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table cellpadding='0' cellspacing='0'>
	<html:form action="lijsthistoriek" method="get">
	<tr valign='middle'>
		<td valign='middle'>
			<html:select property="lijst_id" styleClass="input">
				<html:optionsCollection name="DDH" property="lijsten" label="lijst_b" value="lijst_id" />
			</html:select>
		</td>
		<td >&nbsp;</td>
		<td valign='middle'>
			<html:select property="artikelid" styleClass="input">
				<html:option value="-1">Alle Artikels</html:option>
				<html:optionsCollection name="DDH" property="artikels" label="artikel_b" value="artikel_id" />
			</html:select>
		</td>
		<td >&nbsp;</td>
		<td valign='middle'>
			<html:submit styleClass="inputbtn">
				<bean:message key="lijst.opname.button.ophalen" />
			</html:submit>
		</td >
	</tr>
	</html:form>
</table>
<br>
<display:table id="dossierkadaster" name="sessionScope.lijst" requestURI="/lijsthistoriekview.do" class="lijst" >
	<display:column property="type" titleKey="lijst.historiek.type"/>
	<display:column property="dossiers" titleKey="lijst.historiek.dossiers" class="center"/>
	<display:column property="kadasters" titleKey="lijst.historiek.kadasters" class="center"/>
</display:table>


