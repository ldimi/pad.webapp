<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<bean:define id="artikelid" name="art46selectform" property="artikelid" scope="session" type="java.lang.String"></bean:define>
<bean:define id="lijst_id" name="art46selectform" property="lijst_id" scope="session" type="java.lang.String"></bean:define>
<html:form action="setpublicatiedatum">
	<html:hidden property="artikelid" value="<%=artikelid %>"/>
	<html:hidden property="lijst_id" value="<%=lijst_id %>"/>
	<table cellpadding='0' cellspacing='0'>
		<tr valign='middle'>
			<td valign='middle'>
				<bean:message key="lijst.nagoedk.date.date" />
			</td>
			<td >&nbsp;</td>
			<td valign='middle'>
				<html:text maxlength="10" size="10" styleClass="input" property="publicatie_d" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
			</td >
		</tr>
	</table>
	<br>
	<html:submit styleClass="inputbtn" value=">>"/>
</html:form>