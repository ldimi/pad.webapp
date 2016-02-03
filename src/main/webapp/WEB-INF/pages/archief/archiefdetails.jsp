<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html:form action="archief">
	<input type="hidden" name="popup" value="yes" />
	<html:hidden property="crudAction" value="save"/>
	<html:hidden property="dossier_id" />
	<html:hidden property="archief_id" />
	<logic:present parameter="lijst">
		<input type="hidden" name="lijst" value="yes"/>
	</logic:present>
	<table bgcolor="#eeeeee">
		<tr>
			<th colspan="2">
				Archief
			</th>
		</tr>
		<tr>
			<td>IVS nr.</td>
			<td align="right">
				<html:text property="archief_id" styleClass="input" errorStyleClass="errorclass" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td>Logistiek nr.</td>
			<td align="right">
				<html:text property="archief_nr" styleClass="input" errorStyleClass="errorclass"/>
			</td>
		</tr>
		<%--tr>
			<td>Dossiernr.</td>
			<td align="right">
				<html:text property="dossier_id" disabled="true" styleClass="input"/>
			</td>
		</tr--%>
		<tr>
			<td>Afdeling</td>
			<td align="right">
				<html:text property="afdeling" value="IVS" styleClass="input"/>
			</td>
		</tr>
		<tr>
			<td>Datum</td>
			<td align="right">
				<html:text property="archief_d" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
			</td>
		</tr>
		<tr>
			<td>Plaats</td>
			<td align="right">
				<html:text property="plaats" styleClass="input" errorStyleClass="errorclass"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				Inhoud
				<html:textarea property="archief_b" cols="55" rows="10" styleClass="input"/>
			</td>
		</tr>
		<tr>
			<td colspan='2' align='right'>
				<logic:present parameter="lijst">
					<input type="button" value="Venster sluiten" name="dummy" onclick="pad_load('<html:rewrite action="/archiefzoekresult.do"/>', top.opener)" class="inputbtn" />
				</logic:present>
				<logic:notPresent parameter="lijst">
					<input type="button" value="Venster sluiten" name="dummy" onclick="pad_load('<html:rewrite action="dossierdetails.do?selectedtab=Archief"/>', top.opener)" class="inputbtn" />
				</logic:notPresent>
				<html:submit styleClass="inputbtn" value="Wijzigingen opslaan"/>
			</td>
		</tr>
	</table>
</html:form>