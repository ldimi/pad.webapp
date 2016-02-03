<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<html:form action="dossierjd">
	<bean:define id="dossier_id_boa" name="dossierart46form" scope="session" property="dossier_id_boa" type="java.lang.String" />
	<html:hidden property="dossier_id"/>
	<html:hidden property="selected" value="4"/>
	<html:hidden property="crudAction" value="save"/>
	<html:hidden property="popup" value="yes" />
	<table bgcolor="#ececec" class="lijst width980">
		<tr>
			<th colspan="2">
				Juridisch dossier
			</th>
		</tr>
		<tr>
			<td valign='top'>
				<table>
					<tr>
						<td>Dossier BB</td><td><%=dossier_id_boa %></td>
					</tr>
					<tr>
						<td>Dossier JD</td>
						<td>
							<html:text property="dossier_id_jd" styleClass="input" errorStyleClass="errorclass"/>
						</td>
					</tr>
					<tr>
						<td>Titel</td><td><html:text property="dossier_b" size="60" maxlength="80" styleClass="input" errorStyleClass="errorclass"/></td>
					</tr>
					<tr>
						<td>Dossierhouder JD</td>
						<td>
							<html:select property="doss_hdr_id" styleClass="input">
								<html:optionsCollection name="DDH" property="ambtenarenJD" label="ambtenaar_b" value="ambtenaar_id" />
							</html:select>
						</td>
					</tr>
				</table>
			</td>
			<td valign='top' align="right">
				<table width="200">
					<tr>
						<td colspan='2'>
							Commentaar<br/>
							<html:textarea property="commentaar" cols="30" rows="6"/>
						</td>
					</tr>
					<tr>
						<td colspan='2'>
							Stand van zaken terugvordering
							<html:textarea  property="stand_terugvordering" cols="30" rows="6"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan='2' align='right'>
				<input type="button" value="Venster sluiten" name="dummy" onclick="pad_load('<html:rewrite action="dossierdetails.do?selectedtab=JD"/>', top.opener)" class="inputbtn" />
				<logic:present role="adminJD">
					<html:submit value="Wijzigingen opslaan" styleClass="inputbtn"/>
				</logic:present>
			</td>
		</tr>
		<logic:notEmpty name="dossierjdform" property="dossier_id_jd">
			<tr>
				<td colspan="2">
					<div class="planning">
						<strong>Acties</strong>
						<tiles:insert definition="actielijst">
							<tiles:put name="dossier_type" value="J"/>
							<tiles:put name="forward" value="forwardjd"/>
							<tiles:put name="parent_id" beanName="dossierjdform" beanProperty="dossier_id_jd" beanScope="request"/>
							<tiles:put name="roles" value="adminJD"/>
							<tiles:put name="extraUrl" value=""/>
						</tiles:insert>
					</span>
				</td>
			</tr>
		</logic:notEmpty>
	</table>

</html:form>




