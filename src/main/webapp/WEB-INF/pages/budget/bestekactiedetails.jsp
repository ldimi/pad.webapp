<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
    function reloadform(){
        var form = document.getElementById('command');
        setAction(form,'/pad/s/bestek/${bestekId}/acties/${actieId}/wijzig/');
        form.submit();
    }

</script>

<form:form action="/pad/s/bestek/${bestekId}/acties/${actieId}/bewaar/" name="formActie">
	<html:hidden name="bestekActie" property="popup" value="yes"/>
	<html:hidden name="bestekActie" property="crudAction" value="save" />
	<html:hidden name="bestekActie" property="actie_id" />
	<html:hidden name="bestekActie" property="bestek_id" />
	<html:hidden name="bestekActie" property="zimbraIdVan" />
	<html:hidden name="bestekActie" property="zimbraIdTot" />
	<table bgcolor="#eeeeee">
		<tr>
			<th colspan="2">
				Actie
			</th>
		</tr>
		<tr>
				<td>Bestek</td>
				<td align='right'>
                    <html:text name="bestekActie" property="bestek_nr" disabled="disabled" styleClass="input"/>
				</td>
		</tr>
		<tr>
			<td>Type</td><td align='right'>
				<html:select name="bestekActie" property="actie_type_id" styleClass="input" errorStyleClass="errorClass"
					onchange="reloadform()">
					<html:option value=""></html:option>
					<html:optionsCollection name="types" label="actie_type_b" value="actie_type_id" />
				</html:select>
			</td>
		</tr>
		<logic:notEmpty name="subtypes" scope="request">
			<tr>
				<td>Type</td><td align='right'>
					<html:select name="bestekActie" property="actie_sub_type_id" styleClass="input">
						<html:option value=""></html:option>
						<html:optionsCollection name="subtypes" label="actie_sub_type_b" value="actie_sub_type_id" />
					</html:select>
				</td>
			</tr>
		</logic:notEmpty>
		<tr>
			<td>Datum gepland van</td>
			<td align='right'>
				<html:text name="bestekActie" property="actie_d" size="10" styleClass="input" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
			</td>
		</tr>
		<tr>
			<td>Datum gepland tot</td>
			<td align='right'>
				<html:text name="bestekActie" property="stop_d" size="10" styleClass="input" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
			</td>
		</tr>
		<tr>
			<td>Datum gerealiseerd</td>
			<td align='right'>
				<html:text  name="bestekActie" property="realisatie_d" size="10" styleClass="input" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
			</td>
		</tr>
		<tr>
			<td>Gebruikte tijd</td>
			<td align='right'>
				<html:text  name="bestekActie" property="rate" size="5" styleClass="input" errorStyleClass="errorclass" /> %
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<html:checkbox name="bestekActie" property="zimbraSave"  styleClass="checkbox" value="1"/> Opslaan in agenda
			</td>
		</tr>
		<tr>
			<td colspan='2'><html:textarea name="bestekActie" property="commentaar" cols="50" rows="10" styleClass="input"/></td>
		</tr>
		<tr>
			<td colspan='2' align='right'>
				<input type="button" value="Venster sluiten" name="dummy" onclick="pad_load('/pad/s/bestek/${bestekId}/acties/', top.opener)" class="inputbtn" />
                <input type="submit" value="Wijzigingen opslaan" class="inputbtn"/>
			</td>
		</tr>
	</table>
</form:form>