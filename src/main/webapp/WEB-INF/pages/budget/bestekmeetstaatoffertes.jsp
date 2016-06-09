<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/custom"  prefix="custom" %>




<table style="width: 90%">
    <tr>
        <th>Inzender</th>
        <th>Totaal Excl BTW</th>
        <th>Totaal Incl BTW</th>
        <th>Status</th>
        <th>Organisatie</th>
    </tr>
    <c:forEach items="${offertes}" var="offerte" varStatus="status">
        <c:choose>
            <c:when test="${status.index mod 2 == 0 }">
                <c:set var="styleclass" value="tableRowEven"/>
            </c:when>
            <c:otherwise>
                <c:set var="styleclass" value="tableRowOdd"/>
            </c:otherwise>
        </c:choose>
        <tr class="${styleclass}">
            <td>
                <a href="/pad/s/bestek/${bestekId}/meetstaat/offertes/${offerte.id}/">
                    ${offerte.id} ${offerte.inzender}
               </a>
            </td>
            <td style="text-align: right">
                <fmt:formatNumber value="${offerte.totaal}" maxFractionDigits="2" minFractionDigits="2"/>
            </td>
            <td style="text-align: right">
                <fmt:formatNumber value="${offerte.totaal_incl_btw}" maxFractionDigits="2" minFractionDigits="2"/>
            </td>
            <td>
                ${offerte.status}
                <c:if test="${not empty offerte.status}">
                    <a href="/pad/s/bestek/${bestekId}/meetstaat/offertes/${offerte.id}/toekenningverwijderen/">
                        Toewijzing verwijderen
                    </a>
                </c:if>
            </td>
            <td>
                <select name="organisatie_id"
                        style="width: 100%;"
                        onchange="onChangeOrganisatie_id(this, ${bestekId}, ${offerte.id});"
                    >
                    <option value=""></option>
                    <custom:options items="${organisaties_dd}"
                                    itemValue="organisatie_id"
                                    selectedValue="${offerte.organisatie_id}" />
            </td>
        </tr>
    </c:forEach>
</table>
<hr/>
<form method="get" action="/pad/s/bestek/meetstaat/offertes/export/draftOffertes-${bestekId}.xls" target="_blank" >
    <button type="submit" class="inputbtn">Exporteren voor rekenkundige controle: Excel</button>
</form>
Rapport financi&euml;le controle:
<c:if test="${not empty bestekDO.controle_dms_id}">
    <a href='${bestekDO.controle_dms_folder}/${bestekDO.controle_dms_filename}' target="_blank">
        ${bestekDO.controle_dms_filename} <html:img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>
    </a>
</c:if>
<form action="/pad/s/bestek/meetstaat/offertes/uploaden"
      enctype="multipart/form-data" method="post">
    <input type="hidden" value="${bestekId}"
           name="bestekId"/>
    <input type="file" name="file" onchange="this.form.submit()"/>
</form>



<tiles:insert definition="laadJS" />

<script type="text/javascript">
    laadBacking('budget/meetstaat/offertesBacking');
</script>
