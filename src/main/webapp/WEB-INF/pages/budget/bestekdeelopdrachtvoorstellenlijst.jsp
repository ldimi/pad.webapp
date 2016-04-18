<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form class="formlayout"
      action="/pad/s/bestek/${bestekId}/voorstellen_opm/update"
      method="post" >
    <table  style="width: 700px;" >
        <tr>
            <td width="80px" >Opmerking:</td>
            <td width="500px" >
                <textarea name="voorstellen_opm" rows="3" cols="100" maxlength="250" >${bestekDO.voorstellen_opm}</textarea>
            </td>
            <td><input type="submit" class="inputbtn" value="Bewaar" /></td>
        </tr>
    </table>
</form>

<table style="width: 100%">
    <tr style="width: 100%">
        <td>Voorstellen voor: ${bestekDO.bestek_nr} ${bestekDO.omschrijving} </td>
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