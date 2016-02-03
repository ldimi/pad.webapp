<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<html:form action="kadasterzoekresult">
	<table class="windowfree">
		<tr>
			<th colspan="2">
				Zoek dossier via kadaster
			</th>
		</tr>
		<tr>
			<td>
				<bean:message key="kadaster.zoek.kadasterafdid" />
			</td>
			<td>
				<html:select property="kadaster_afd_id" styleClass="input">
					<html:optionsCollection name="DDH" property="kadasterafdelingen" label="kadaster_afd_b" value="kadaster_afd_id" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td colspan='2' align='right'>
				<html:submit styleClass="inputbtn">
					<bean:message key="kadaster.zoek.button" />
				</html:submit>
			</td>
		</tr>
	</table>
</html:form>