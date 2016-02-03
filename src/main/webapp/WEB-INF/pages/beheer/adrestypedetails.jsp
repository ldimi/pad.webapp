<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html:form action="adrestype">			
	<table>		
		<html:hidden property="crudAction" value="save"/>
		<html:hidden property="adrestype_id"/>
		<tr>
			<td>Adrestype</td>
			<td align='right'>
				<html:text property="adrestype_b" styleClass="input"/>
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