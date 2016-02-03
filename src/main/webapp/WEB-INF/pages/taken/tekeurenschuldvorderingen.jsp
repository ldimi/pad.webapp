<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>

<logic:present role="ondertekenaar">
<table style="width: 100%">
    <tr>
        <th></th>
        <th>Vordering nr</th>
        <th>Dossier</th>
        <th>Inzender</th>
        <th>Dossierhouder</th>
        <th>Totaal Incl BTW</th>
        <th></th>
    </tr>
    <c:forEach items="${schuldvorderingen}" var="schuldvordering" varStatus="status">
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
                <c:if test="${not empty schuldvordering.bestek.dossier.gemeente}">
                    ${schuldvordering.bestek.dossier.gemeente.naam}
                </c:if>
                    ${schuldvordering.bestek.dossier.dossier_b}
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
            <td style="text-align: right">
                <fmt:formatNumber value="${schuldvordering.goedkeuring_bedrag}"  minFractionDigits="2" currencySymbol="&euro;" type="currency"/>
            </td>
            <td>
                <a href="/pad/s/bestek/${schuldvordering.bestek_id}/schuldvordering/draftSchuldvordering-${schuldvordering.vordering_id}.pdf" target="_blank">draft</a>
            </td>
            <td>
                <c:choose>
                    <c:when test="${not empty error}">
                        Geen handtekening
                    </c:when>
                    <c:otherwise>
                        <a href="/pad/s/goedkeurenAntwoordBriefSchuldvordering/${schuldvordering.vordering_id}">
                            Aanvaarden
                        </a> &nbsp;
                        <a href="/pad/s/afkeurenAntwoordBriefSchuldvordering/${schuldvordering.vordering_id}">
                            Niet Aanvaarden
                        </a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
</logic:present>
<hr/>


