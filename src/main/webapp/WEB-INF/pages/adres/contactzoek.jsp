<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<html:form action="contactzoekresult">

    <input type="hidden" name="popup" value="yes" />

    <table  class="windowfree">
        <tr>
            <th colspan="2">
                Zoek adres
            </th>
        </tr>
        <tr>
            <td>Naam</td>
            <td >
                <html:text property="naam_adres" styleClass="input"/>
            </td>
        </tr>
        <tr>
            <td>Naam contactpersoon</td>
            <td >
                <html:text property="naam_contact" styleClass="input"/>
            </td>
        </tr>
        <tr>
            <td>Gemeente</td>
            <td >
                <html:text property="gemeente" styleClass="input"/>
            </td>
        </tr>
        <tr>
            <td>Provincie</td>
            <td >
                <html:select property="provincie" styleClass="input">
                    <html:option value=""></html:option>
                    <html:option value="ANTWERPEN">Antwerpen</html:option>
                    <html:option value="LIMBURG">Limburg</html:option>
                    <html:option value="OOST VLAANDEREN">Oost Vlaanderen</html:option>
                    <html:option value="VLAAMS BRABANT">Vlaams Brabant</html:option>
                    <html:option value="WEST VLAANDEREN">West Vlaanderen</html:option>

                </html:select>
            </td>
        </tr>
        <tr>
            <td>Type</td>
            <td align='left'>
                <html:select property="type_id" styleClass="input">
                    <html:option value=""></html:option>
                    <html:optionsCollection name="DDH" property="adrestypes" label="adres_type_b" value="adres_type_id" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td>
                <html:checkbox property="actief_s" value="1" styleClass="checkbox" />Inclusief niet actief
            </td>
        </tr>
        <tr>
            <td colspan="2" align='right'>
                <html:submit styleClass="inputbtn" value="Zoeken"/>
            </td>
        </tr>
    </table>
</html:form>