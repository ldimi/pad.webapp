<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html:form action="lijsten">
	<html:hidden property="lijst_id"/>
	<html:hidden property="crudAction" value="save"/>
	<table>				
		<tr>			
			<td>Lijst naam</td><td><html:text property="lijst_b" styleClass="input" errorStyleClass="errorClass"/></td>			
		</tr>
		<tr>			
			<td>Lijst jaar</td><td><html:text property="jaar" styleClass="input" errorStyleClass="errorClass"/></td>			
		</tr>
		<tr>
			<td>Afgesloten</td><td><html:checkbox property="afgesloten_s" value="1" styleClass="checkbox"/></td>
		</tr>		
		<tr>
			<logic:present role="adminArt46">
				<td colspan='2' align='right'>
					<html:submit styleClass="inputbtn" value="Opslaan"/>					
				</td>
			</logic:present>
		</tr>
	</table>	
</html:form>


