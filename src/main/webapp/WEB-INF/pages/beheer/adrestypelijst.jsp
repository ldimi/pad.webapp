<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<table>
	<tr>
		<td valign="top">
			<display:table id="adrestype" name="applicationScope.DDH.adrestypes" requestURI="/adrestypelijstview.do" defaultsort="1" class="lijst width300">
				<display:column property="adres_type_b" titleKey="beheer.adrestype.lijst.type" href="adrestype.do?crudAction=read" paramId="adrestype_id" paramProperty="adres_type_id"  sortable="true" />
				<display:column>
					<logic:present role="adminArt46">
						<html:link action="adrestype?crudAction=delete" paramId="adrestype_id" paramName="adrestype" paramProperty="adres_type_id">
							<html:img border="0" page="/resources/images/delete.gif" title="Verwijderen adrestype"/>
						</html:link>
					</logic:present>
				</display:column>
				<display:setProperty name="basic.msg.empty_list_row" value=""/>
			</display:table>
		</td>
		<td align="right" valign="top">
			<logic:present role="adminArt46">
				<html:link action="adrestype?crudAction=view">
					<html:img border="0" page="/resources/images/add.gif" title="Toevoegen adrestype"/>
				</html:link>
			</logic:present>
		</td>
	</tr>
</table>