<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/tags/display-tags" prefix="display" %>
<html:form action="actietype">			
	<table bgcolor="#eeeeee">
		<html:hidden property="crudAction" value="save"/>		
		<html:hidden property="actie_type_id"/>
		<tr>
			<th colspan="2">
				Actie Type
			</th>			
		</tr>
		<tr>
			<td>Beschrijving</td>
			<td align='right'>
				<html:text property="actie_type_b" styleClass="input" errorStyleClass="errorclass"/>
			</td>
		</tr>
		<tr>
			<td>Dossiertype</td>
			<td align='right'>
				<html:select property="dossier_type">						
					<html:option value="A">Afval</html:option>
					<html:option value="X">Ander</html:option>
					<html:option value="K">Bestek</html:option>
					<html:option value="B">Bodem</html:option>
					<html:option value="J">Juridisch</html:option>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>Gebruikte tijd</td>
			<td align='right'>
				<html:text property="rate" styleClass="input" size="5"/> %
			</td>
		</tr>	
		<tr>			
			<td colspan="2">
				<html:checkbox property="ingebreke_s" value="1" styleClass="checkbox"/> Ingebrekestelling
			</td>
		</tr>			
		<tr>			
			<td colspan="2" align='right'>
				<logic:present role="adminArt46">
					<html:submit styleClass="inputbtn" value="Wijzigingen opslaan"/>
				</logic:present>				
			</td>
		</tr>
		<tr>
			<td height="50"></td>
		</tr>
		<logic:notEqual name="actietypeform" property="actie_type_id" scope="request" value="0">
			<tr>
				<td colspan="2">
					<table>				
						<tr>								
							<td valign="top">						
								<display:table id="subtype" name="requestScope.subtypes" class="lijst">										
									<display:column property="actie_sub_type_b" title="Actiesubtype" href="actiesubtype.do?crudAction=read" paramId="actie_sub_type_id" paramProperty="actie_sub_type_id"/>
									<display:column class="center">
										<logic:present role="adminArt46">			
											<a href='actiesubtype.do?crudAction=delete&actie_sub_type_id=<bean:write name="subtype" property="actie_sub_type_id"/>&actie_type_id=<bean:write name="subtype" property="actie_type_id"/>'>
													<html:img border="0" page="/resources/images/delete.gif" title="Verwijderen"/>
											</a>
										</logic:present>
									</display:column>										
									<display:setProperty name="basic.msg.empty_list_row" value=""/>	
								</display:table>							
							</td>
							<td valign="top">
								<logic:present role="adminArt46">			
									<html:link action="actiesubtype?crudAction=view&actie_sub_type_id=0" paramName="actietypeform" paramProperty="actie_type_id" paramScope="request" paramId="actie_type_id">
										<html:img border="0" page="/resources/images/add.gif" title="Toevoegen actie subtype"/>
									</html:link>
								</logic:present>
							</td>
						</tr>
					</table>	
				</td>		
			</tr>	
		</logic:notEqual>	
	</table>		
</html:form>