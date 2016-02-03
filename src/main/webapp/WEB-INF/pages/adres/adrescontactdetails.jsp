<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html:form action="adrescontact">			
	<table class="windowfree">
		<html:hidden property="adres_id"/>
		<html:hidden property="contact_id"/>
		<html:hidden property="crudAction" value="save"/>
		<logic:equal parameter="popup" value="yes">
			<input type="hidden" name="popup" value="yes" />
		</logic:equal>
		<tr>
			<th colspan="2">
				Contact
			</th>
		</tr>
		<tr>
			<td>Naam</td>
			<td align='left'>
				<html:text property="naam" styleClass="input" maxlength="50" size="40" errorStyleClass="errorclass"/>
			</td>
		</tr>	
		<tr>
			<td>Voornaam</td>
			<td align='left'>
				<html:text property="voornaam" styleClass="input" maxlength="50" size="40"/>
			</td>
		</tr>	
		<tr>
			<td>Functie</td>
			<td align='left'>
				<html:text property="functie" styleClass="input" maxlength="80" size="40"/>
			</td>
		</tr>				
		<tr>
			<td>Tel</td>
			<td align='left'>
				<html:text property="tel" styleClass="input" maxlength="12" size="12"/>
			</td>
		</tr>	
		<tr>
			<td>Fax</td>
			<td align='left'>
				<html:text property="fax" styleClass="input" maxlength="12" size="12"/>
			</td>
		</tr>	
		<tr>
			<td>Gsm</td>
			<td align='left'>
				<html:text property="gsm" styleClass="input" maxlength="20" size="20"/>
			</td>
		</tr>	
		<tr>
			<td>Email</td>
			<td align='left'>
				<html:text property="email" styleClass="input" maxlength="40" size="40" errorStyleClass="errorclass"/>
			</td>
		</tr>			
		<tr>
			<td>Niet actief</td>
			<td align='left'>
				<html:checkbox property="stop_s" styleClass="checkbox" value="1"/>
			</td>
		</tr>
		<tr>
			<td>Commentaar</td>
			<td><html:textarea property="commentaar" cols="40" rows="5" styleClass="input"/></td>
		</tr>
		<tr>				
			<td>
				Referentie postcodes
			</td>			
			<td>
				<html:textarea property="referentie_postcodes" cols="40" rows="3" styleClass="input">
				
				</html:textarea>
			</td>
		</tr>							
		<tr>			
			<td colspan="2" align='right'>
				<html:submit styleClass="inputbtn" value="Wijzigingen opslaan"/>
			</td>
		</tr>		
	</table>		
</html:form>