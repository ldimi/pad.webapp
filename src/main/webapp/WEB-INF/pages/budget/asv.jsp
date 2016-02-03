<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form:form method="post" accept-charset="UTF-8"
           action="/pad/s/bestek/${asvDO.bestek_id}/aanvraagSchuldvordering/${asvDO.aanvr_schuldvordering_id}"
           modelAttribute="asvDO">
    <table width="100%">
        <tr>
            <td style="width: 50%">
                <table style="width: 100%">
                    <tr>
                        <td style="width: 30%;">Nummer :</td>
                        <td>
                                ${asvDO.schuldvordering_nr}
                        </td>
                    </tr>
                    <tr class="table-basic-row">
                        <td>Bestek :</td>
                        <td>  ${asvDO.bestek_nr}</td>
                    </tr>
                    <c:if test="${not empty asvDO.deelopdracht_id}">
                        <tr class="table-basic-row">
                            <td style="width: 30%;">Deelopdracht :</td>
                            <td>
                                ${asvDO.deelopdracht_nr} ${asvDO.deelopdracht_dossier_b_l} (${asvDO.deelopdracht_dossier_nr})
                            </td>
                        </tr>
                        <c:if test="${not empty asvDO.voorstel_deelopdracht_id}">
                            <tr class="table-basic-row">
                                <td style="width: 30%;">Voorstel:</td>
                                <td>
                                    <a href="/pad/s/bestek/${asvDO.bestek_id}/voorstel/${asvDO.voorstel_deelopdracht_id}">${asvDO.voorstel_deelopdracht_nr}</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:if>
                    <tr class="table-basic-row">
                        <td>Opdracht houder:</td>
                        <td>${asvDO.opdrachtgever_naam_voornaam}
                        </td>
                    </tr>
                    <tr class="table-basic-row">
                        <td>Datum schuldvordering :</td>
                        <td>  <fmt:formatDate value="${asvDO.vordering_d}" type="date" dateStyle="full" /></td>
                    </tr>
                    <tr class="table-basic-row">
                        <td>Datum uiterste verificatie:</td>
                        <td>  <fmt:formatDate value="${asvDO.uiterste_verific_d}" type="date" dateStyle="full" /></td>
                    </tr>
                    <tr class="table-basic-row">
                        <td>Datum effectieve verificatie:</td>
                        <td>  <fmt:formatDate value="${asvDO.goedkeuring_d}" type="date" dateStyle="full" /></td>
                    </tr>
                    <tr class="table-basic-row">
                        <td>Uiterste datum betaling:</td>
                        <td>  <fmt:formatDate value="${asvDO.uiterste_d}" type="date" dateStyle="full" /></td>
                    </tr>
                    <tr class="table-basic-row"><td>Bedrag schuldvordering (incl. BTW):</td><td>  <fmt:formatNumber
                            value="${asvDO.vordering_bedrag}"
                            maxFractionDigits="2" currencySymbol="&euro;" type="currency"/></td></tr>
                    <tr>
                        <td style="width: 30%;">Status :</td>
                        <td>
                            ${asvDO.status_pad} <c:if test="${asvDO.afgekeurd_jn eq 'J'}">(AFGEKEURD)</c:if>
                        </td>
                    </tr>
                    <tr class="table-basic-row"><td>Van Datum: </td><td>
                        <c:if test="${not empty asvDO.van_d}">
                            <fmt:formatDate
                                value="${asvDO.van_d}"
                                type="date" dateStyle="full" />
                        </c:if>
                    </td></tr>
                    <tr class="table-basic-row"><td>Tot Datum: </td><td>
                        <c:if test="${not empty asvDO.tot_d}">
                            <fmt:formatDate
                                value="${asvDO.tot_d}"
                                type="date" dateStyle="full" />
                        </c:if>
                    </td></tr>
                    <c:if test="${not empty asvDO.antwoord_pdf_dms_id}">
                        <tr class="table-basic-row"><td>Antwoord: </td><td>
                            <a href='<%=System.getProperty("ovam.dms.webdrive.base") %> ${asvDO.antwoord_pdf_dms_folder}/${asvDO.antwoord_pdf_dms_filename}' target="_blank">
                                <img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>
                            </a>
                        </td></tr>
                    </c:if>
                    <tr>
                        <td>Fase</td>
                        <td>
                            <c:choose>
                                <c:when test="${asvDO.lockedForDosierhouder}">
                                    <form:select path="schuldvordering_fase_id" disabled="true">
                                        <form:option value="" label=""/>
                                        <form:option value="12" label="ABBO"/>
                                        <form:option value="13" label="ABSP"/>
                                        <form:option value="14" label="ABSW-ANZ"/>
                                        <form:option value="15" label="VEIM-VZM"/>
                                    </form:select>
                                </c:when>
                                <c:otherwise>
                                        <form:select path="schuldvordering_fase_id">
                                            <form:option value="" label=""/>
                                            <form:option value="12" label="ABBO"/>
                                            <form:option value="13" label="ABSP"/>
                                            <form:option value="14" label="ABSW-ANZ"/>
                                            <form:option value="15" label="VEIM-VZM"/>
                                        </form:select>
                                        <form:errors
                                                path="schuldvordering_fase_id"
                                                style="color: red;"/>
                                </c:otherwise>
                            </c:choose>

                        </td>
                    </tr>
                </table>
            </td>
            <td style="width: 50%; text-align: left; vertical-align: top">
                <b>Bijlagen: </b>
                <br/>
                <c:forEach items="${bijlagen}"
                           var="bijlage">
                    <a href='<%=System.getProperty("ovam.dms.url")%>/share/page/document-details?nodeRef=workspace://SpacesStore/${bijlage.alfrescoNodeId}' target="_blank">
                        <img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Bijlage bekijken" title="Bijlage bekijken"/>${bijlage.name}
                    </a>
                    <br/>
                </c:forEach>
            </td>
        </tr>
    </table>

    <table style="width: 100%">
        <thead class="table-sub-header">
            <tr>
                <th></th>
                <th>Postnr.</th>
                <th>Taak : Details</th>
                <th>Type</th>
                <th>Eenheidsprijs / Eenheid</th>
                <th>Aantal / Bedrag</th>
                <th>Gecorrigeerd Aantal / Bedrag</th>
                <th>Totaal</th>
                <th>Totaal incl BTW</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${asvDO.asvRegels}"
                       var="regel" varStatus="status">
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
                            <c:if test="${not empty asvDO.voorstel_deelopdracht_id}">
                                <c:if test="${regel.regelVoorzienInVoorstel}">
                                    <img src="/pad/resources/images/checkmark.png" width="16" alt="Deze regel is voorzien in het voorstel" title="Deze regel is voorzien in het voorstel">
                                </c:if>
                                <c:if test="${not regel.voorzien}">
                                    <img src="/pad/resources/images/errorwarning_tab.gif" width="16" alt="Deze regel is niet voorzien in het voorstel" title="Deze regel is niet voorzien in het voorstel">
                                </c:if>
                            </c:if>
                        </td>
                        <td>
                            ${regel.postnr}
                        </td>
                        <td>
                            ${regel.taak}: ${regel.details}
                        </td>
                        <td>
                            ${regel.type}
                        </td>
                        <td style="text-align: right">
                            <c:choose>
                                <c:when test="${regel.type eq 'VH'}">
                                    <c:choose>
                                        <c:when test="${regel.meerwerkenRegel}">
                                            <fmt:formatNumber  value="${regel.meerwerken_eenheidsprijs}" maxFractionDigits="2" currencySymbol="&euro;" type="currency"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber
                                                    value="${regel.eenheidsprijs}"
                                                    maxFractionDigits="2" currencySymbol="&euro;"
                                                    type="currency"/> / ${regel.eenheid}
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${regel.type eq 'SPM'}">
                                    <c:if test="${not empty regel.offerte_regel_id}">
                                        (<fmt:formatNumber  value="${regel.offerte_regeltotaal}" maxFractionDigits="2" currencySymbol="&euro;" type="currency"/>)
                                     </c:if>
                                </c:when>
                            </c:choose>
                        </td>
                        <td style="text-align: right">
                            <c:choose>
                                <c:when test="${regel.type eq 'VH'}">
                                    <c:choose>
                                        <c:when test="${regel.gecorrigeerdafname ==  null}">
                                            <fmt:formatNumber value="${regel.afname}"
                                                              maxFractionDigits="2" minFractionDigits="2"/>
                                        </c:when>
                                        <c:otherwise>
                                            <S><fmt:formatNumber value="${regel.afname}"
                                                                 maxFractionDigits="2" minFractionDigits="2"/></S>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${regel.type eq 'TP' or regel.type eq 'SPM' }">
                                    <c:choose>
                                        <c:when test="${regel.gecorrigeerdafnamebedrag == null}">
                                            <fmt:formatNumber value="${regel.afnamebedrag}"
                                                              maxFractionDigits="2" minFractionDigits="2"
                                                              currencySymbol="&euro;"
                                                              type="currency"/>
                                        </c:when>
                                        <c:otherwise>
                                            <S><fmt:formatNumber value="${regel.afnamebedrag}"
                                                                 maxFractionDigits="2" minFractionDigits="2"
                                                                 currencySymbol="&euro;"
                                                                 type="currency"/></S>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                        </td>
                        <td style="text-align: left; width: 120px">
                            <c:choose>
                                <c:when test="${regel.type eq 'VH'}">
                                    <c:choose>
                                        <c:when test="${asvDO.lockedForDosierhouder}">
                                            <fmt:formatNumber value="${regel.gecorrigeerdafname}" maxFractionDigits="2" minFractionDigits="2"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" style="text-align: right; width: 100px"
                                                   name="asvRegels[${status.index}].gecorrigeerdafname"
                                                   value='<fmt:formatNumber value="${regel.gecorrigeerdafname}" maxFractionDigits="2" minFractionDigits="2"/>'/>
                                            <form:errors
                                                    path="asvRegels[${status.index}].gecorrigeerdafname"
                                                    style="color: red;"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${regel.type eq 'TP' or regel.type eq 'SPM' }">
                                    <c:choose>
                                        <c:when test="${asvDO.lockedForDosierhouder}">
                                            <fmt:formatNumber value="${regel.gecorrigeerdafnamebedrag}" maxFractionDigits="2" minFractionDigits="2" currencySymbol="&euro;" type="currency"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" style="text-align: right; width: 100px"
                                                   name="asvRegels[${status.index}].gecorrigeerdafnamebedrag"
                                                   value='<fmt:formatNumber value="${regel.gecorrigeerdafnamebedrag}" maxFractionDigits="2" minFractionDigits="2"/>' />&euro;
                                            <form:errors
                                                    path="asvRegels[${status.index}].gecorrigeerdafnamebedrag"
                                                    style="color: red;"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                        </td>
                        <td style="text-align: right">
                            <c:if  test="${not empty regel.type}">
                                <fmt:formatNumber
                                    value="${regel.roundedRegelTotaal}"
                                    maxFractionDigits="2" minFractionDigits="2" type="currency"
                                    currencySymbol="&euro;"/>
                            </c:if>
                        </td>
                        <td style="text-align: right">
                            <c:if  test="${not empty regel.type}">
                                <fmt:formatNumber
                                    value="${regel.roundedRegelTotaalInclBtw}"
                                    maxFractionDigits="2" minFractionDigits="2" type="currency"
                                    currencySymbol="&euro;"/>
                            </c:if>
                        </td>
                        <td>
                                <a id="displayText${status.index}" href="javascript:toggle(${status.index});">Weergeven</a>
                        </td>
                    </tr>
                    <tr class="${styleclass}">
                        <td colspan="12" class="${styleclass}">
                            <div id="toggleText${status.index}" style="display: none">
                                BTW: <c:choose>
                                    <c:when test="${regel.gecorrigeerd_btw_tarief ==null}">
                                        ${regel.btw_tarief}%
                                    </c:when>
                                    <c:when test="${regel.gecorrigeerd_btw_tarief !=null}">
                                        <S>${regel.btw_tarief}%</S>
                                    </c:when>
                                </c:choose>
                                <c:if test="${!asvDO.lockedForDosierhouder}">
                                    <form:select path="asvRegels[${status.index}].gecorrigeerd_btw_tarief">
                                           <form:option value=""/>
                                        <form:options items="${btwTarieven}"/>
                                    </form:select>
                                </c:if>
                                <br/>
                                Opmerking:
                                    <c:choose>
                                        <c:when test="${asvDO.lockedForDosierhouder}">
                                            ${offerteRegel.opmerking}
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" name="asvRegels[${status.index}].opmerking" value="${regel.opmerking}"  style="text-align: left;"/>
                                            <form:errors
                                                    path="asvRegels[${status.index}].opmerking"
                                                    style="color: red;"/>
                                        </c:otherwise>
                                    </c:choose>
                            </div>
                        </td>
                    </tr>
            </c:forEach>
            <c:choose>
                <c:when test="${asvDO.vordering_correct_bedrag != null}">
                    <tr class="table-basic-row">
                        <td colspan="7" ></td>
                        <td style="text-align: right">
                            <s><fmt:formatNumber
                                value="${asvDO.excl_btw_bedrag_zonderCorrectie}"
                                maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                            </s>
                        </td>
                        <td style="text-align: right">
                            <s><fmt:formatNumber
                                value="${asvDO.vordering_bedrag}"
                                maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                            </s>
                        </td>
                        <td></td>
                    </tr>
                    <tr class="table-basic-row">
                        <td colspan="7" ></td>
                        <td style="text-align: right">
                            <fmt:formatNumber
                                value="${asvDO.excl_btw_bedrag}"
                                maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                        </td>
                        <td style="text-align: right">
                            <fmt:formatNumber
                                value="${asvDO.vordering_correct_bedrag}"
                                maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                        </td>
                        <td></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr class="table-basic-row">
                        <td colspan="7" ></td>
                        <td style="text-align: right">
                            <fmt:formatNumber
                                value="${asvDO.excl_btw_bedrag}"
                                maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                        </td>
                        <td style="text-align: right">
                            <fmt:formatNumber
                                value="${asvDO.vordering_bedrag}"
                                maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                        </td>
                        <td></td>
                    </tr>
                </c:otherwise>
            </c:choose>
            <tr class="table-basic-row">
                <td style="text-align: right" colspan="7">Bedrag prijsherziening incl. BTW:</td>
                <td style="text-align: right">
                    <fmt:formatNumber
                            maxFractionDigits="2" minFractionDigits="2" type="currency"
                            value="${asvDO.herziening_bedrag}"
                            currencySymbol="&euro;"/>
                </td>
                <td style="">
                    <c:choose>
                        <c:when test="${asvDO.lockedForDosierhouder}">
                            <fmt:formatNumber
                                    maxFractionDigits="2" minFractionDigits="2" type="currency"
                                    value="${asvDO.herziening_correct_bedrag}"
                                    currencySymbol="&euro;"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" style="text-align: right; width: 100px"
                                   name="herziening_correct_bedrag"
                                   value='<fmt:formatNumber value="${asvDO.herziening_correct_bedrag}" maxFractionDigits="2" minFractionDigits="2"/>'/>
                            <form:errors
                                    path="herziening_correct_bedrag"
                                    style="color: red;"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>

                </td>
            </tr>
            <tr class="table-basic-row">
                <td style="text-align: right" colspan="7"> TOTAAL incl. BTW & prijsherziening:</td>
                <td></td>
                <td style="text-align: right">
                    <fmt:formatNumber
                            maxFractionDigits="2" minFractionDigits="2" type="currency"
                            value="${asvDO.totaalInclPrijsherziening}"
                            currencySymbol="&euro;"/>
                </td>
            </tr>
            <tr class="table-basic-row">
                <td style="text-align: right" colspan="7"> Bedrag boete excl. BTW:</td>
                <td style="text-align: right">
                    <c:choose>
                        <c:when test="${asvDO.lockedForDosierhouder}">
                        <fmt:formatNumber
                                maxFractionDigits="2" minFractionDigits="2" type="currency"
                                value="${asvDO.boete_bedrag}"
                                currencySymbol="&euro;"/>
                    </c:when>
                        <c:otherwise>
                            <input type="text" style="text-align: right; width: 100px"
                                   name="boete_bedrag"
                                   value='<fmt:formatNumber value="${asvDO.boete_bedrag}" maxFractionDigits="2" minFractionDigits="2"/>'/>
                            <form:errors
                                    path="boete_bedrag"
                                    style="color: red;"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr class="table-basic-row">
                <td style="text-align: right" colspan="7"> Bedrag incl. prijsherziening en boete:</td>
                <td></td>
                <td style="text-align: right">
                    <fmt:formatNumber
                            maxFractionDigits="2" minFractionDigits="2" type="currency"
                            value="${asvDO.totaalInclPrijsherzieningEnBoete}"
                            currencySymbol="&euro;"/>
                </td>
            </tr>
        </tbody>
    </table>
    <br/>
    Opmerking:
    <br/>
    <c:choose>
        <c:when test="${asvDO.lockedForDosierhouder}">
            ${asvDO.commentaar}
        </c:when>
        <c:otherwise>
            <form:textarea path="commentaar" rows="5" cols="85" maxlength="1000" htmlEscape="true" />
        </c:otherwise>
    </c:choose>
    <br/>
    <c:if test="${!asvDO.lockedForDosierhouder}">
        <input type="submit" value="Bewaar" class="inputbtn" name="action"/>
        <input type="submit" value="Accepteren" class="inputbtn" name="action"/>
        <input type="submit" value="Afkeuren" class="inputbtn" name="action"/>
        <a href="/pad/s/bestek/${bestekId}/schuldvordering/draftSchuldvordering-${asvDO.vordering_id}.pdf" target="_blank">Draft Antwoord brief</a>
    </c:if>
</form:form>
<br/>
<hr/>
<table style="width: 100%">
    <tr>
        <th>Datum</th>
        <th>Gebruiker</th>
        <th>Status</th>
        <th>Motivatie</th>
    </tr>
    <c:forEach items="${status_historiek}"
               var="statusHist" varStatus="status">
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
                                value="${statusHist.datum}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${statusHist.webloket_gebruiker_email != null}">
                        ${statusHist.webloket_gebruiker_email}
                    </c:when>
                    <c:otherwise>
                        ${statusHist.dossierhouder_id}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${statusHist.status_pad}</td>
            <td width="60%">${statusHist.motivatie}</td>
        </tr>
    </c:forEach>
</table>
<hr/>


<script type="text/javascript">
    function toggle(nr) {
        var ele = document.getElementById("toggleText"+nr);
        var text = document.getElementById("displayText"+nr);
        if(ele.style.display == "block") {
            ele.style.display = "none";
            text.innerHTML = "Weergeven";
        }
        else {
            ele.style.display = "block";
            text.innerHTML = "Verbergen";
        }
    }
</script>

