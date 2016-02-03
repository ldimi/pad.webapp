<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<br>
<logic:iterate id="kadasterdetail" name="kadasterdetailspublicatie" scope="request" length="1">
	Kadaster <bean:write name="kadasterdetail" property="kadaster_afd_b"/> <bean:write name="kadasterdetail" property="kadaster_id_sh"/>
</logic:iterate>
<br><br>
<table cellpadding="3" cellspacing="5">
	<tr>
		<th>Lijst</th>
		<th>Goedkeuring</th>
		<th>Publicatie</th>
		<th>Artikel</th>
		<th>Dossier</th>
		<th>Dossier naam</th>
		<th>Commentaar</th>
	</tr>
	<logic:iterate id="dossierkadaster" name="kadasterdetailspublicatie" scope="request">
		<tr>
			<td width="80" align='center'>
				<bean:write name="dossierkadaster" property="lijst_b"/>
			</td>
			<td width="80" align='center'>
				<bean:write name="dossierkadaster" property="goedkeuring_d"/>
			</td>
			<td width="80" align='center'>
				<bean:write name="dossierkadaster" property="publicatie_d"/>
			</td>
			<td align='center'>
				<bean:write name="dossierkadaster" property="artikel_b"/>
			</td>
			<td align='center'>
				<bean:write name="dossierkadaster" property="dossier_id_boa"/>
			</td>
			<td width="200">
				<a href='dossierdetailsArt46.do?id=<bean:write name="dossierkadaster" property="id"/>'>
					<bean:write name="dossierkadaster" property="dossier_b"/>
				</a>
			</td>
			<td width="220">
				<bean:write name="dossierkadaster" property="commentaar"/>
			</td>
		</tr>
	</logic:iterate>
</table>


