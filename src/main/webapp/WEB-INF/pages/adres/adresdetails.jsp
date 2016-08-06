<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<html:form action="adres" >
    <html:hidden property="adres_id"/>
    <html:hidden property="crudAction" value="save"/>
    <logic:equal parameter="popup" value="yes">
        <input type="hidden" name="popup" value="yes" />
    </logic:equal>
    <table class="windowfree">
        <tr>
            <th colspan="4">
                Details Adres
            </th>
        </tr>
        <tr>
            <td>Naam / Bedrijf</td>
            <td align='left'>
                <html:text property="naam" styleClass="input" maxlength="50" size="40" errorStyleClass="errorclass"/>
            </td>
            <td>Tel</td>
            <td align='left'>
                <html:text property="tel" styleClass="input" maxlength="12" size="12"/>
            </td>
        </tr>
        <tr>
            <td>Voornaam / Afdeling</td>
            <td align='left'>
                <html:text property="voornaam" styleClass="input" maxlength="50" size="40"/>
            </td>
            <td>Fax</td>
            <td align='left'>
                <html:text property="fax" styleClass="input" maxlength="12" size="12"/>
            </td>
        </tr>
        <tr>
            <td>Straat & nr.</td>
            <td align='left'>
                <html:text property="straat" styleClass="input" maxlength="40" size="40"/>
            </td>
            <td>Gsm</td>
            <td align='left'>
                <html:text property="gsm" styleClass="input" maxlength="20" size="20"/>
            </td>
        </tr>
        <tr>
            <td>Postcode</td>
            <td align='left'>
                <html:text property="postcode" styleClass="input" maxlength="8" size="8" />
            </td>
            <td>Email</td>
            <td align='left'>
                <html:text property="email" styleClass="input" maxlength="40" size="40" errorStyleClass="errorclass"/>
            </td>
        </tr>
        <tr>
            <td>Gemeente</td>
            <td align='left'>
                <html:text property="gemeente" styleClass="input" maxlength="40" size="40"/>
            </td>
            <td>Website</td>
            <td align='left'>
                <html:text property="website" styleClass="input" maxlength="40" size="40"/>
            </td>
        </tr>
        <tr>
            <td>Land</td>
            <td align='left'>
                <html:select property="land" styleClass="input">
                    <html:optionsCollection name="DDH" property="landen" />
                </html:select>
            </td>
            <td>Type</td>
            <td align='left'>
                <html:select property="type_id" styleClass="input">
                    <html:optionsCollection name="DDH" property="adrestypes" label="adres_type_b" value="adres_type_id" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td>Maatschappelijke zetel</td>
            <td align='left'>
                <html:checkbox property="maatsch_zetel" styleClass="checkbox" value="1"/>
            </td>
            <td>Niet actief</td>
            <td align='left'>
                <html:checkbox property="stop_s" styleClass="checkbox" value="1"/>
            </td>
        </tr>
        <tr>
            <td>
                Referentie postcodes

            </td>
            <td colspan="3">
                <html:textarea property="referentie_postcodes" cols="90" rows="3" styleClass="input">

                </html:textarea>
            </td>
        </tr>
        <tr>
            <td colspan="4" align='right'>
                <c:if test="${!empty adresform.adres_id}">
                    <html:submit styleClass="inputbtn" value="Wijzigingen opslaan"/>
                </c:if>
                <c:if test="${empty adresform.adres_id}">
                    <html:submit styleClass="inputbtn" value="Nieuw adres toevoegen"/>
                </c:if>
            </td>
        </tr>
    </table>
</html:form>
<c:if test="${!empty adresform.adres_id}">
    <tiles:insert definition="adrescontactlijst"/>
</c:if>

<tiles:insert definition="laadJS" />

<script type="text/javascript">
    laadBacking('adres/adresdetailsBacking');
</script>

