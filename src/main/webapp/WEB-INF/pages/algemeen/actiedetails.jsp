<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
Url: <%=request.getParameter("extraUrl") %>
<html:form action="actie">
	<html:hidden property="popup" value="yes"/>
	<html:hidden property="crudAction" value="save" />
	<html:hidden property="forward"/>
	<html:hidden property="actie_id" />
	<html:hidden property="dossier_id" />
	<html:hidden property="dossier_type" />
	<html:hidden property="bestek_id" />
	<html:hidden property="zimbraIdVan" />
	<html:hidden property="zimbraIdTot" />
	<table bgcolor="#eeeeee">
		<tr>
			<th colspan="2">
				Actie
			</th>
		</tr>
		<tr>
			<logic:equal name="actieform" property="dossier_type" scope="request" value="K">
				<td>Bestek</td>
				<td align='right'>
					<html:hidden property="bestek_id"/>
					<html:hidden property="bestek_nr"/>
					<html:text property="bestek_nr" disabled="true" styleClass="input"/>
				</td>
			</logic:equal>
			<%--logic:notEqual name="actieform" property="dossier_type" scope="request" value="K">
				<td>Dossier</td>
				<td align='right'>
					<html:text property="dossier_id" disabled="true" styleClass="input"/>
				</td>
			</logic:notEqual--%>
		</tr>
		<tr>
			<td>Type</td><td align='right'>
				<html:select property="actie_type_id" styleClass="input" errorStyleClass="errorClass"
					onchange="setAction(this.form,'actie.do'); setCrudAction(this.form, 'view'); submit();">
					<html:option value=""></html:option>
					<html:optionsCollection name="types" label="actie_type_b" value="actie_type_id" />
				</html:select>
			</td>
		</tr>
		<logic:notEmpty name="subtypes" scope="request">
			<tr>
				<td>Type</td><td align='right'>
					<html:select property="actie_sub_type_id" styleClass="input">
						<html:option value=""></html:option>
						<html:optionsCollection name="subtypes" label="actie_sub_type_b" value="actie_sub_type_id" />
					</html:select>
				</td>
			</tr>
		</logic:notEmpty>
		<tr>
			<td>Datum gepland van</td>
			<td align='right'>
				<html:text property="actie_d" size="10" styleClass="input" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
			</td>
		</tr>
		<tr>
			<td>Datum gepland tot</td>
			<td align='right'>
				<html:text property="stop_d" size="10" styleClass="input" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
			</td>
		</tr>
		<tr>
			<td>Datum gerealiseerd</td>
			<td align='right'>
				<html:text  property="realisatie_d" size="10" styleClass="input" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
			</td>
		</tr>
		<tr>
			<td>Gebruikte tijd</td>
			<td align='right'>
				<html:text  property="rate" size="5" styleClass="input" errorStyleClass="errorclass" /> %
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<html:checkbox property="zimbraSave"  styleClass="checkbox" value="1"/> Opslaan in agenda
			</td>
		</tr>
		<tr>
			<td colspan='2'><html:textarea property="commentaar" cols="50" rows="10" styleClass="input"/></td>
		</tr>
		<tr>
			<td colspan='2' align='right'>
				<input type="button" value="Venster sluiten" name="dummy" onclick="pad_load('<html:rewrite action="actie"/>?useForward=true&forward=<bean:write name="actieform" property="forward"/>&dossier_id_jd=<bean:write name="actieform" property="dossier_id"/>&dossier_id=<bean:write name="actieform" property="dossier_id"/>', top.opener)" class="inputbtn" />
				<html:submit styleClass="inputbtn" value="Wijzigingen opslaan"/>
			</td>
		</tr>
	</table>
</html:form>