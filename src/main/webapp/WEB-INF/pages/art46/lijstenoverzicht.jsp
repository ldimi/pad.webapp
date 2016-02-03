<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<table class="lijst" >
	<tr>
		<th>Naam</th>
		<th>Jaar</th>
		<th>Afgesloten</th>
		<th>
			<html:link action="lijsten?crudAction=view&lijst_id=0">
				<html:img border="0" page="/resources/images/add.gif" />
			</html:link>
		</th>
	</tr>
	<logic:iterate id="lijst" name="DDH" property="lijsten" scope="application">
		<tr>
			<td>
				<html:link action="lijsten?crudAction=read" paramId="lijst_id" paramName="lijst" paramProperty="lijst_id">
					<bean:write name="lijst" property="lijst_b"/>
				</html:link>
			</td>
			<td>
				<bean:write name="lijst" property="jaar"/>
			</td>
			<td align='center'>
				<html:checkbox styleClass="checkbox" name="lijst" property="afgesloten_s" value="1" />
			</td>
			<td></td>
		</tr>
	</logic:iterate>
</table>

