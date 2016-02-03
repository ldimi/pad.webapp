<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<html:form action="dossierAfspraak">
    <html:hidden property="popup" value="yes"/>
    <html:hidden property="crudAction" value="save" />
    <html:hidden property="id" />
    <html:hidden property="dossier_id" />
    <table bgcolor="#eeeeee">
        <tr>
            <th colspan="2">
                Afspraak
            </th>
        </tr>
        <tr>
            <td>Datum</td>
            <td align='right'>
                <html:text property="datum" size="10" styleClass="input" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
            </td>
        </tr>
        <tr>
            <td colspan='2'><html:textarea property="omschrijving" cols="100" rows="30" styleClass="input"/></td>
        </tr>
        <tr>
            <td colspan='2' align='right'>
                <input type="button" value="Venster sluiten" name="dummy" onclick="pad_load('<html:rewrite action="dossierdetails.do?selectedtab=Project%20Fiche"/>', top.opener)" class="inputbtn" />
                <html:submit styleClass="inputbtn" value="Wijzigingen opslaan"/>
            </td>
        </tr>
    </table>
</html:form>