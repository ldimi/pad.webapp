<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table border="0">
	<tr>
		<td valign="top">
			<display:table id="actietype" name="requestScope.actietypes" requestURI="/actietypelijst.do" defaultsort="1" class="lijst width300">
				<display:column property="actie_type_b" title="Actie type" href="actietype.do?crudAction=read" paramId="actie_type_id" paramProperty="actie_type_id"  sortable="true" />
				<display:column title="Dossiertype" sortable="true">
					<html:select name="actietype" property="dossier_type" disabled="true">
						<html:option value="A">Afval</html:option>
						<html:option value="X">Ander</html:option>
						<html:option value="K">Bestek</html:option>
						<html:option value="B">Bodem</html:option>
						<html:option value="J">Juridisch</html:option>
					</html:select>
				</display:column>
				<display:column title="Ingebrekestelling" class="center">
					<html:checkbox name="actietype" property="ingebreke_s" value="1" styleClass="checkbox" disabled="true"/>
				</display:column>
				<display:column property="rate" title="Gebruikte tijd" style="text-align:right;" decorator="be.ovam.art46.decorator.BigDecimalDecorator"/>
				<display:column class="center">
					<logic:present role="adminArt46">
						<html:link action="actietype?crudAction=delete" paramId="actie_type_id" paramName="actietype" paramProperty="actie_type_id">
							<html:img border="0" page="/resources/images/delete.gif" title="Verwijderen actie type"/>
						</html:link>
					</logic:present>
				</display:column>
				<display:setProperty name="basic.msg.empty_list_row" value=""/>
			</display:table>
		</td>
		<td align="right" valign="top">
			<logic:present role="adminArt46">
				<html:link action="actietype?crudAction=view&actie_type_id=0">
					<html:img border="0" page="/resources/images/add.gif" title="Toevoegen actie type"/>
				</html:link>
			</logic:present>
		</td>
	</tr>
</table>