<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/tags/display-tags" prefix="display" %>
<html:form action="query">
	<html:hidden property="crudAction" value="save" />
	<html:hidden property="query_id" />		
		<table bgcolor="#eeeeee" class="lijst width980">
		<tr>
			<th colspan="2">
				Query
			</th>		
		</tr>		
		<tr>
			<td>Naam</td>
			<td align="left">
				<html:text property="query_b" size="130" styleClass="input" errorStyleClass="errorclass"/>
			</td>
		</tr>		
		<tr>
			<td>Query</td>
			<td><html:textarea property="query_l" cols="129" rows="20" styleClass="input" errorStyleClass="errorclass"/></td>
		</tr>
		<tr>			
			<td colspan='2' align='right'>				
				<logic:notEqual name="queryform" property="query_id" value="0">
					<input type="button" value="Query uitvoeren" name="dummy" class="inputbtn" onclick="window.open('<html:rewrite action="queryexecute?popup=yes" paramId="query_id" paramName="queryform" paramProperty="query_id" paramScope="request"/>','Queryresult', '')"/>
				</logic:notEqual>
				<logic:present role="adminArt46">
					<html:submit styleClass="inputbtn" value="Wijzigingen opslaan"/>
				</logic:present>
			</td>
		</tr>		
	</table>	
</html:form>