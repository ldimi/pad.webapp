<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="basic-div">
<form:form method="post" accept-charset="UTF-8"
           action="/pad/s/bestek/${bestekId}/voorstel/${voorstelDeelopdrachtForm.id}"
           modelAttribute="voorstelDeelopdrachtForm">
    <table style="width: 100%">
        <tr style="width: 100%">
            <td colspan="2">Voorstel:</td>
        </tr>

        <tr>
            <td style="width: 50%; vertical-align: top; text-align: left;">
                <table style="width: 100%">
                    <tr>
                        <td style="width: 12%;">Nummer :</td>
                        <td>
                                ${voorstelDeelopdrachtForm.voorstelDeelopdracht.nummer}
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 12%;">Status :</td>
                        <td>
                                ${voorstelDeelopdrachtForm.voorstelDeelopdracht.voorstelDeelopdrachtStatus.name}
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 12%;">Offerte :</td>
                        <td>
                            <c:choose>
                                <c:when test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.locked}">
                                    ${voorstelDeelopdrachtForm.voorstelDeelopdracht.offerte.inzender}
                                </c:when>
                                <c:otherwise>
                                    <form:select path="voorstelDeelopdracht.offerte">
                                        <form:option value="" label=""/>
                                        <form:options items="${offertes}" itemValue="id" itemLabel="inzender"/>
                                    </form:select>
                                    <form:errors
                                            path="voorstelDeelopdracht.offerte"
                                            style="color: red;"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 12%;">Dossier :</td>
                        <td>
                            <c:choose>
                                <c:when test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.locked}">
                                    ${voorstelDeelopdrachtForm.voorstelDeelopdracht.dossier.titel}
                                </c:when>
                                <c:otherwise>
                                    <form:select path="voorstelDeelopdracht.dossier">
                                        <form:option value="" label=""/>
                                        <form:options items="${dossiers}" itemValue="id" itemLabel="titel"/>
                                    </form:select>
                                    <form:errors
                                            path="voorstelDeelopdracht.dossier"
                                            style="color: red;"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 12%;">Omschrijving :</td>
                        <td>
                            <c:choose>
                                <c:when test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.locked}">
                                    ${voorstelDeelopdrachtForm.voorstelDeelopdracht.omschrijving}
                                </c:when>
                                <c:otherwise>
                                    <form:textarea path="voorstelDeelopdracht.omschrijving" rows="5" cols="85"
                                                   maxlength="1024"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr><td colspan="2">
                        <c:if test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.locked}" >
                        <a href="/pad/s/bestek/${bestekId}/voorstel/${voorstelDeelopdrachtForm.id}/mail"><b>Verwittig leverancier</b></a>
                        </c:if><br/>
                        <c:if test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.locked}" >
                            <a href="/pad/s/bestek/${bestekId}/voorstel/orgineelVoorstel${voorstelDeelopdrachtForm.id}.xls"><b>Exporteer naar Excel</b></a>
                        </c:if>
                        <c:if test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.locked}" >
                            <a href="/pad/s/bestek/${bestekId}/voorstel/orgineelVoorstel${voorstelDeelopdrachtForm.id}.pdf"><b>Exporteer naar PDF</b></a>
                        </c:if>
                    </td></tr>
                </table>
            </td>
        </tr>
        <c:if test="${not empty voorstelDeelopdrachtForm.voorstelDeelopdracht.voorstelDeelopdrachtRegels}">
            <tr>
                <td style="width: 100%" td colspan="2">
                    <table>
                        <tr>
                            <td colspan="2">
                                <table style="width: 100%">
                                    <thead class="table-sub-header">
                                        <tr>
                                            <th></th>
                                            <th>Postnr.</th>
                                            <th>Taak</th>
                                            <th>Details</th>
                                            <th>Type</th>
                                            <th>Eenheid</th>
                                            <th>Eenheidsprijs / TP</th>
                                            <th>Aantal</th>
                                            <th>Bedrag</th>
                                            <th>Totaal</th>
                                            <th>Totaal incl. BTW</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${voorstelDeelopdrachtForm.voorstelDeelopdracht.voorstelDeelopdrachtRegels}"
                                                   var="voorstelDeelopdrachtRegel" varStatus="status">
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
                                                    <c:if test="${voorstelDeelopdrachtRegel.regelVoorzienInVoorstel}">
                                                        <img src="/pad/resources/images/checkmark.png" width="16" alt="Deze regel is voorzien in het voorstel" title="Deze regel is voorzien in het voorstel" />
                                                    </c:if>
                                                </td>
                                                <td>${voorstelDeelopdrachtRegel.postnr}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${voorstelDeelopdrachtRegel.meerwerkenRegel}">
                                                            ${voorstelDeelopdrachtRegel.beschrijving}
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${voorstelDeelopdrachtRegel.taak}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${voorstelDeelopdrachtRegel.offerteRegel.details}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${voorstelDeelopdrachtRegel.meerwerkenRegel}">
                                                            ${voorstelDeelopdrachtRegel.meerwerkenType}
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${voorstelDeelopdrachtRegel.type}
                                                        </c:otherwise>
                                                    </c:choose>

                                                </td>
                                                <td>
                                                    <c:if test="${voorstelDeelopdrachtRegel.type eq 'VH'}">
                                                        <c:choose>
                                                            <c:when test="${voorstelDeelopdrachtRegel.meerwerkenRegel}">
                                                                ${voorstelDeelopdrachtRegel.meerwerkenEenheid}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${voorstelDeelopdrachtRegel.eenheid}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                </td>
                                                <td style="text-align: right">
                                                    <c:choose>
                                                        <c:when test="${voorstelDeelopdrachtRegel.type eq 'VH'}">
                                                            <c:choose>
                                                                <c:when test="${voorstelDeelopdrachtRegel.meerwerkenRegel}">
                                                                    <fmt:formatNumber
                                                                            value="${voorstelDeelopdrachtRegel.meerwerkenEenheidsPrijs}"
                                                                            maxFractionDigits="2" minFractionDigits="2"
                                                                            currencySymbol="&euro;"
                                                                            type="currency"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <fmt:formatNumber value="${voorstelDeelopdrachtRegel.eenheidsprijs}"
                                                                                      maxFractionDigits="2" currencySymbol="&euro;"
                                                                                      type="currency"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:if test="${not empty voorstelDeelopdrachtRegel.offerteRegel}">
                                                                <fmt:formatNumber
                                                                        value="${voorstelDeelopdrachtRegel.offerteRegel.regelTotaal}"
                                                                        maxFractionDigits="2" currencySymbol="&euro;" type="currency"/>
                                                            </c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td style="text-align: right">
                                                    <c:if test="${voorstelDeelopdrachtRegel.type eq 'VH'}">
                                                        <fmt:formatNumber value="${voorstelDeelopdrachtRegel.afname}"
                                                                          maxFractionDigits="2" minFractionDigits="2"/>
                                                    </c:if>
                                                </td>
                                                <td style="text-align: right">
                                                    <c:if test="${voorstelDeelopdrachtRegel.type eq 'TP' or voorstelDeelopdrachtRegel.type eq 'SPM' }">
                                                        <fmt:formatNumber value="${voorstelDeelopdrachtRegel.afnameBedrag}"
                                                                          maxFractionDigits="2" minFractionDigits="2"
                                                                          currencySymbol="&euro;"
                                                                          type="currency"/>
                                                    </c:if></td>
                                                <td style="text-align: right">
                                                    <c:if test="${not empty voorstelDeelopdrachtRegel.type}">
                                                    <fmt:formatNumber
                                                            value="${voorstelDeelopdrachtRegel.regelTotaal}"
                                                            maxFractionDigits="2" minFractionDigits="2" type="currency"
                                                            currencySymbol="&euro;"/></td>
                                                </c:if>
                                                <td style="text-align: right">
                                                    <c:if test="${not empty voorstelDeelopdrachtRegel.type}">
                                                    <c:if test="${not (voorstelDeelopdrachtForm.voorstelDeelopdracht.offerte.btwTarief eq voorstelDeelopdrachtRegel.toeTePassenBtwTarief)}">
                                                        (${voorstelDeelopdrachtRegel.toeTePassenBtwTarief}%)
                                                    </c:if>
                                                    <fmt:formatNumber
                                                            value="${voorstelDeelopdrachtRegel.regelTotaalInclBtw}"
                                                            maxFractionDigits="2" minFractionDigits="2" type="currency"
                                                            currencySymbol="&euro;"/></td>
                                                </c:if>
                                                </td>

                                                <td>
                                                    <c:if test="${(not voorstelDeelopdrachtForm.voorstelDeelopdracht.locked) and voorstelDeelopdrachtRegel.meerwerkenRegel}">
                                                        <a href="/pad-webloket/webloket/offerte/${offerteId}/voorstel/${voorstelDeelopdrachtForm.id}/verwijder/${voorstelDeelopdrachtRegel.id}">Verwijder</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <c:if test="${not empty voorstelDeelopdrachtRegel.opmerking}">
                                                <tr>
                                                    <td colspan="9">
                                                        Opmerking: ${voorstelDeelopdrachtRegel.opmerking}
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                        <tr class="table-basic-row">
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td style="text-align: right">TOTAAL excl. BTW</td>
                                            <td style="text-align: right"><b><fmt:formatNumber
                                                    value="${voorstelDeelopdrachtForm.voorstelDeelopdracht.bedragExclBtw}"
                                                    maxFractionDigits="2" minFractionDigits="2" type="currency"
                                                    currencySymbol="&euro;"/></b></td>
                                        </tr>
                                        <tr class="table-basic-row">
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td style="text-align: right">TOTAAL incl. BTW</td>
                                            <td style="text-align: right"><b><fmt:formatNumber
                                                    value="${voorstelDeelopdrachtForm.voorstelDeelopdracht.bedragInclBtw}"
                                                    maxFractionDigits="2" minFractionDigits="2" type="currency"
                                                    currencySymbol="&euro;"/></b></td>
                                        </tr>
                                        <tr>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </c:if>
        <c:if test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.klaarVoorBeslissing or voorstelDeelopdrachtForm.voorstelDeelopdracht.toegekend}">
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>
                                Deelopdracht:
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.klaarVoorBeslissing}">
                                        <form:select path="voorstelDeelopdracht.deelOpdracht">
                                            <form:option value="" label=""/>
                                            <form:options items="${deelopdrachten}" itemValue="deelopdracht_id"
                                                          itemLabel="dossier.titel"/>
                                        </form:select>
                                        <form:errors path="voorstelDeelopdracht.deelOpdracht"
                                                     style="color: red;"/>
                                    </c:when>
                                    <c:otherwise>
                                        ${voorstelDeelopdrachtForm.voorstelDeelopdracht.deelOpdracht.dossier.titel}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <c:choose>
                    <c:when test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.locked}">
                        <c:if test="${voorstelDeelopdrachtForm.voorstelDeelopdracht.klaarVoorBeslissing}">
                            <input type="submit" value="Aanpassing vragen" class="inputbtn" name="action"/>
                            <input type="submit" value="Toekennen" class="inputbtn" name="action"/>
                            <input type="submit" value="Niet selecteren" class="inputbtn" name="action"/>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Bewaar" class="inputbtn" name="action"/>
                        <input type="submit" value="Aanvraag verzenden" class="inputbtn" name="action"/>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</form:form>
<table style="width: 100%">
    <tr>
        <td style="width: 50%; text-align: left; vertical-align: top">
            <b>Bijlagen:</b><br/>
            <table>
                <c:forEach items="${voorstelDeelopdrachtForm.voorstelDeelopdracht.bijlages}" var="bijlage">
                    <tr>
                        <td>
                            <a href='<%=System.getProperty("ovam.dms.url")%>/share/page/document-details?nodeRef=workspace://SpacesStore/${bijlage.alfrescoNodeId}' target="_blank">
                                <img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0"
                                     alt="Bijlage bekijken"
                                     title="Bijlage bekijken"/>${bijlage.name}
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br/>
            <c:choose>
                <c:when test="${not (voorstelDeelopdrachtForm.voorstelDeelopdracht.id == null)}">
                    <form action="/pad/s/bestek/${bestekId}/voorstel/${voorstelDeelopdrachtForm.voorstelDeelopdracht.id}/upload/"
                          enctype="multipart/form-data" method="post">
                        <input type="hidden" value="${voorstelDeelopdrachtForm.voorstelDeelopdracht.id}"
                               name="voorstelDeelopdrachtId"/>
                        <input type="file" name="file" onchange="this.form.submit()"/>
                    </form>
                    <c:if test="${not empty uploudError}">
                        <br/> <strong style="color: red;"> ${uploudError}</strong>
                    </c:if>
                </c:when>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <table style="width: 100%">
                <tr>
                    <th>Datum</th>
                    <th>Gebruiker</th>
                    <th>Status</th>
                    <th>Motivatie</th>
                </tr>
                <c:forEach items="${voorstelDeelopdrachtForm.voorstelDeelopdracht.voorstelDeelopdrachtHistorie}"
                           var="voorstelDeelopdrachtHistorie" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index mod 2 == 0 }">
                            <c:set var="styleclass" value="tableRowEven"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="styleclass" value="tableRowOdd"/>
                        </c:otherwise>
                    </c:choose>
                    <tr class="${styleclass}">
                        <td><fmt:formatDate type="both" dateStyle="full"
                                            value="${voorstelDeelopdrachtHistorie.datum.time}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${voorstelDeelopdrachtHistorie.webloket_gebruiker_email != null}">
                                    ${voorstelDeelopdrachtHistorie.webloket_gebruiker_email}
                                </c:when>
                                <c:otherwise>
                                    ${voorstelDeelopdrachtHistorie.dossierhouder.id}
                                </c:otherwise>
                            </c:choose>
                            <%--
                            --%>
                        </td>
                        <td>${voorstelDeelopdrachtHistorie.voorstelDeelopdrachtStatus.name}</td>
                        <td width="60%">${voorstelDeelopdrachtHistorie.motivatie}</td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>


<script>
    function changeByType() {
        if (document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenType').value == 'VH') {
            document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenAantal').style.display = 'block';
            document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenEenheid').style.display = 'block';
            document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenEenheidPrijs').style.display = 'block';
            document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenRegelRegelTotaal').style.display = 'none';
        } else {
            document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenEenheid').style.display = 'none';
            document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenAantal').style.display = 'none'
            document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenEenheidPrijs').style.display = 'none';
            document.getElementById('nieuwevoorstelDeelopdrachtRegelMeerwerkenRegelRegelTotaal').style.display = 'block';
        }
    }
    changeByType();
</script>

<br/>

</div>
