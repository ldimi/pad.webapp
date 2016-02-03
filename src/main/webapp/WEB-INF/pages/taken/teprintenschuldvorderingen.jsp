<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<a onclick="window.checkedAll();" href="javascript:void(0);">Selecteer alles</a>
<logic:present role="secretariaat">
<c:if test="${not empty melding}">
    <b>${melding}</b><br/>
</c:if>
<table style="width: 100%">
    <tr>
        <th></th>
        <th>Status</th>
        <th>Vordering nr</th>
        <th>Inzender</th>
        <th>Dossierhouder</th>
        <th>Inkomende Brief</th>
        <th>Uitgaande Brief</th>
        <th>Totaal Incl BTW</th>
        <th></th>
    </tr>
    <form:form action="/pad/s/teprintenschuldvorderingen.pdf" modelAttribute="schuldvorderingenform" name="schuldvorderingenform">
        <c:forEach items="${schuldvorderingenform.schuldvorderingen}" var="schuldvordering" varStatus="status">
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
                    <form:checkbox path="schuldvorderingen[${status.index}].selected"/>
                    <form:hidden path="schuldvorderingen[${status.index}].vordering_id"/>
                    <a href='${schuldvordering.antwoordPDF.editUrl}' target="_blank">
                        <img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0"
                             alt="Brief bekijken"
                             title="Brief bekijken"/>
                    </a>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${schuldvordering.goedgekeurd}">
                            Goedkeuring
                        </c:when>
                        <c:otherwise>
                            Afkeuring
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: left">
                    <c:choose>
                        <c:when test="${not empty schuldvordering.aanvraagSchuldvordering}">
                            <a href="/pad/s/bestek/${schuldvordering.bestek_id}/aanvraagSchuldvordering/${schuldvordering.aanvraagSchuldvordering.id}">
                                    ${schuldvordering.nummer}
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="/pad/s/bestek/${schuldvordering.bestek_id}/schuldvorderingen/${schuldvordering.vordering_id}">
                                    ${schuldvordering.nummer}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: left">
                    <c:choose>
                        <c:when test="${not empty schuldvordering.aanvraagSchuldvordering}">
                            ${schuldvordering.aanvraagSchuldvordering.offerte.inzender}
                        </c:when>
                        <c:otherwise>
                            ${schuldvordering.brief.adresContactNaam}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty schuldvordering.deelOpdracht}">
                            ${schuldvordering.deelOpdracht.dossier.doss_hdr_id}
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${not empty schuldvordering.aanvraagSchuldvordering}">
                                    ${schuldvordering.aanvraagSchuldvordering.offerte.bestek.dossier.doss_hdr_id}
                                </c:when>
                                <c:otherwise>
                                    ${schuldvordering.bestek.dossier.doss_hdr_id}
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: left">
                        ${schuldvordering.brief.brief_nr}
                </td>
                <td style="text-align: left">
                        ${schuldvordering.antwoordPDF.brief_nr}
                </td>
                <td style="text-align: right">
                    <fmt:formatNumber value="${schuldvordering.goedkeuring_bedrag}"  minFractionDigits="2" currencySymbol="&euro;" type="currency"/>
                </td>
                <td>
                    <a href="#" onclick="window.isGeprint(${schuldvordering.vordering_id}, event);return false;">Correct afgedrukt</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="7">
                <input type="submit" value="Afdrukken"/>
            </td>
        </tr>
    </form:form>
        </table>
<a onclick="window.checkedAll();" href="javascript:void(0);">Selecteer alles</a>
<hr/>
</logic:present>
<tiles:insert definition="laadJS" />
<script type="text/javascript">
    laadBacking('taken/teprintenschuldvorderingenBacking');
</script>

