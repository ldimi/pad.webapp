<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table style="width: 100%">
    <tr class="table-header" style="width: 100%">
        <td>Voorstellen voor: ${offerte.bestek.bestek_nr} ${offerte.bestek.omschrijving} </td>
    </tr>
    <tr>
        <td>
            <div class="basic-div">
                <table style="width: 100%">

                    <thead class="table-sub-header">
                    <tr>
                        <th>Nummer</th>
                        <th>Offerte</th>
                        <th>Gemeente</th>
                        <th>Status</th>
                        <th>Aanvraag Datum</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${voorstellen}" var="voorstel" varStatus="status">
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
                                ${voorstel.nummer}
                            </td>
                            <td>
                                ${voorstel.offerte.inzender}
                            </td>
                            <td>
                                 ${voorstel.dossier.titel}
                            </td>
                            <td>
                                 ${voorstel.voorstelDeelopdrachtStatus.name}
                            </td>
                            <td>
                                <fmt:formatDate type="both" dateStyle="full" value="${voorstel.aanvraagDatum.time}"/>
                            </td>
                            <td>
                                <a href="/pad/s/bestek/${voorstel.offerte.bestekId}/voorstel/${voorstel.id}">Bekijken</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <a href="/pad/s/bestek/${bestekId}/voorstel/nieuw">Nieuw voorstel aanvragen</a>
            </div>
        </td>
    </tr>
</table>