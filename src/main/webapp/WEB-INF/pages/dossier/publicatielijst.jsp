<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<table cellpadding="3" cellspacing="5">
    <tr>
        <th>Lijst</th>
        <th>Goedkeuring</th>
        <th>publicatie</th>
        <th>Artikel</th>
        <th>Kadaster</th>
        <th>Commentaar</th>
    </tr>
    <logic:iterate id="dossierkadaster" name="dossierdetailspublicatie" scope="request" indexId="index">
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
            <td width="290">
                <bean:write name="dossierkadaster" property="kadaster_afd_b"/>
                <bean:write name="dossierkadaster" property="kadaster_id_sh"/>
            </td>
            <td width="130">
                <bean:write name="dossierkadaster" property="commentaar"/>
            </td>
        </tr>
    </logic:iterate>
    <logic:notPresent name="index" scope="page">
        <tr>
            <td colspan='7'>
                Geen publicaties gevonden.
            <td>
        </tr>
    </logic:notPresent>
</table>



