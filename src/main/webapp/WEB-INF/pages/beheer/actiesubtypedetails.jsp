<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/tags/display-tags" prefix="display" %>
<html:form action="actiesubtype">			
	<table bgcolor="#eeeeee">		
		<html:hidden property="crudAction" value="save" />
		<html:hidden property="actie_type_id"/>
		<html:hidden property="actie_sub_type_id"/>
		<tr>
			<th colspan="2">
				Actie Subtype
			</th>			
		</tr>
		<tr>
			<td>Beschrijving</td>
			<td align='right'>
				<html:text property="actie_sub_type_b" styleClass="input" errorStyleClass="errorclass"/>
			</td>
		</tr>			
		<tr>			
			<td colspan="2" align='right'>
				<logic:present role="adminArt46">
					<html:submit styleClass="inputbtn" value="Wijzigingen opslaan"/>
				</logic:present>				
			</td>
		</tr>		
	</table>		
</html:form>