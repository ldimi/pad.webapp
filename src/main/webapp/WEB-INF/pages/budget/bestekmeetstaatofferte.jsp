<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="javascript">
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
<c:if test="${not (offerteForm.offerte.status eq 'Toegewezen' )}">
    <input type="button" id="uploadBtn"  title="Offerte inladen op basis van een CSV bestand" value="Offerte inladen op basis van een CSV bestand"/>
</c:if>
<tiles:insert page="../util/uploader.jsp"/>

<c:if test="${not empty offerteForm}">
    <c:if test="${not empty bindingResult}">
        <b style="color: red">Deze Meetstaat kan niet opgeslagen worden: Sommige waarden zijn niet correct ingevoerd</b>
    </c:if>
    <form:form  method="post" accept-charset="UTF-8" action="/pad/s/bestek/${bestekDO.bestek_id}/meetstaat/offertes/" modelAttribute="offerteForm">
        <table>
            <tr>
                <td>Inschrijver: ${offerteForm.offerte.inzender}</td>
                <td>
                    <input type="hidden" name="offerte_id" value="${offerteForm.offerte.id}" />
                    <input type="hidden" name="offerte.id" value="${offerteForm.offerte.id}" />
                    <input type="hidden" name="offerte.bestekId" value="${bestekDO.bestek_id}" />
                    <input type="hidden" name="offerte.offerte.brief.brief_id" value="${offerteForm.offerte.brief.brief_id}" />
                    <input type="hidden" name="offerte.inzender" value="${offerteForm.offerte.inzender}" />
                </td>
                <td>BTW-tarief (%)</td>
                <td><form:select path="offerte.btwTarief" items="${btwTarieven}" cssStyle="width: 45px;"/></td>
            </tr>
        </table>

        <table class="lijst1" >
            <tr>
                <th>Postnr.</th>
                <th>Taak</th>
                <th>Details</th>
                <th>Type</th>
                <th>Eenheid</th>
                <c:if test="${empty offerteForm.offerte.status}">
                    <th>Aantal</th>
                </c:if>
                <th>Offerte Aantal</th>
                <th>Offerte prijs</th>
                <th>Offerte Detail totaal</th>
                <th>Offerte Sub totaal</th>
                <th>Offerte Post totaal</th>
                <th>Details</th>
            </tr>
            <c:forEach items="${offerteForm.offerteRegels}" var="offerteRegel" varStatus="status">
                <c:choose>
                    <c:when test="${status.index mod 2 == 0 }">
                        <c:set var="styleclass" value="tableRowEven"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="styleclass" value="tableRowOdd"/>
                    </c:otherwise>
                </c:choose>
                <%--
                --%>
                <tr class="${styleclass}">
                    <td>${offerteRegel.cleanPostnr}</td>
                    <td style="width: 300px">
                        <c:choose>
                            <c:when test="${offerteRegel.extraRegel and not (offerteRegel.totaal or offerteRegel.parent)}">
                                <input type="text" name="offerteRegels[${status.index}].extraRegelTaak" style="text-align: left " value="${offerteRegel.extraRegelTaak}"/>
                                <form:errors path="offerteRegels[${status.index}].extraRegelTaak"  style="color: red;"/>
                            </c:when>
                            <c:otherwise>
                                ${offerteRegel.taak}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="width: 300px">
                        <c:choose>
                            <c:when test="${offerteRegel.extraRegel and not (offerteRegel.totaal or offerteRegel.parent)}">
                                <input type="text" name="offerteRegels[${status.index}].extraRegelDetails" style="text-align: left " value="${offerteRegel.extraRegelDetails}"/>
                                <form:errors path="offerteRegels[${status.index}].extraRegelDetails"  style="color: red;"/>
                            </c:when>
                            <c:otherwise>
                                ${offerteRegel.details}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        ${offerteRegel.type}
                    </td>
                    <td>
                        <c:if test="${offerteRegel.type=='VH'}">
                            <c:choose>
                                <c:when test="${empty offerteForm.offerte.status and offerteRegel.extraRegel}">
                                    <form:select path="offerteRegels[${status.index}].extraRegelEenheid" items="${eenheden}" itemValue="lable" itemLabel="lable" cssStyle="width: 75px;"/>
                                </c:when>
                                <c:otherwise>
                                    ${offerteRegel.eenheid}
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </td>
                    <c:if test="${empty offerteForm.offerte.status}">
                        <td style="text-align: right ">
                            <c:if test="${not offerteRegel.extraRegel and not(offerteRegel.meetstaatRegel==null)}">
                                <fmt:formatNumber value="${offerteRegel.meetstaatRegel.aantal}" maxFractionDigits="2" minFractionDigits="2"/>
                            </c:if>
                        </td>
                    </c:if>
                    <td style="text-align: right ">
                        <c:if test="${offerteRegel.type=='VH'}">
                            <c:choose>
                                <c:when test="${empty offerteForm.offerte.status}">
                                    <c:choose>
                                        <c:when test="${offerteRegel.extraRegel or offerteRegel.aantal == offerteRegel.meetstaatRegel.aantal}">
                                            <input type="text" name="offerteRegels[${status.index}].aantal" style="text-align: right " value="<fmt:formatNumber value="${offerteRegel.aantal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input style="color: red; text-align: right;" type="text" name="offerteRegels[${status.index}].aantal" value="<fmt:formatNumber value="${offerteRegel.aantal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <form:errors path="offerteRegels[${status.index}].aantal" style="color: red;"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber value="${offerteRegel.aantal}" maxFractionDigits="2" minFractionDigits="2"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </td>
                    <td style="text-align: right ">
                        <c:if test="${offerteRegel.type=='VH'}">
                            <c:choose>
                                <c:when test="${empty offerteForm.offerte.status}">
                                    <input type="text" name="offerteRegels[${status.index}].eenheidsprijs" style="text-align: right " value="<fmt:formatNumber value="${offerteRegel.eenheidsprijs}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                                    <form:errors path="offerteRegels[${status.index}].eenheidsprijs"  style="color: red;"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber value="${offerteRegel.eenheidsprijs}" maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </td>
                    <td style="text-align: right " >
                        <c:choose>
                            <c:when test="${empty offerteForm.offerte.status and ((offerteRegel.type=='TP'or offerteRegel.type=='SPM')) and offerteRegel.level>2}">
                                <input type="text" name="offerteRegels[${status.index}].regelTotaal" style="text-align: right " value="<fmt:formatNumber value="${offerteRegel.regelTotaal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                                <form:errors path="offerteRegels[${status.index}].regelTotaal"  style="color: red;"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:formatNumber value="${offerteRegel.detailTotaal}" maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align: right " >
                        <c:choose>
                            <c:when test="${empty offerteForm.offerte.status and (offerteRegel.type=='TP'or offerteRegel.type=='SPM') and offerteRegel.level==2}">
                                <input type="text" name="offerteRegels[${status.index}].regelTotaal" style="text-align: right " value="<fmt:formatNumber value="${offerteRegel.regelTotaal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                                <form:errors path="offerteRegels[${status.index}].regelTotaal"  style="color: red;"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:formatNumber value="${offerteRegel.subTotaal}" maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align: right ">
                        <c:choose>
                            <c:when test="${empty offerteForm.offerte.status and (offerteRegel.type=='TP'or offerteRegel.type=='SPM') and offerteRegel.level==1}">
                                <input type="text" name="offerteRegels[${status.index}].regelTotaal" style="text-align: right " value="<fmt:formatNumber value="${offerteRegel.regelTotaal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                                <form:errors path="offerteRegels[${status.index}].regelTotaal"  style="color: red;"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:formatNumber value="${offerteRegel.postTotaal}" maxFractionDigits="2" minFractionDigits="2" type="currency" currencySymbol="&euro;"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${not empty offerteRegel.cleanPostnr}">
                            <a id="displayText${status.index}" href="javascript:toggle(${status.index});">Weergeven</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${empty offerteRegel.extraRegel}">extra
                            <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen"/>
                        </c:if>
                    </td>
                </tr>
                <tr class="${styleclass}">
                    <td colspan="13" class="${styleclass}">
                        <div id="toggleText${status.index}" style="display: none">
                            <c:if test="${offerteRegel.type=='TP'or offerteRegel.type=='SPM' or offerteRegel.type=='VH'}">
                            BTW-tarief: ${offerteRegel.toeTePassenBtwTarief}%

                                <form:select path="offerteRegels[${status.index}].btwTarief" cssStyle="width: 45px;">
                                    <form:option value=""/>
                                    <form:options items="${btwTarieven}"/>
                                </form:select>
                                Totaal: incl. BTW <fmt:formatNumber value="${offerteRegel.regelTotaalInclBTW}" type="currency" maxFractionDigits="2" minFractionDigits="2" currencySymbol="&euro;"/>
                            </c:if>
                            <br/>
                            Opmerking: <input type="text" name="offerteRegels[${status.index}].opmerking" style="text-align: left" value="${offerteRegel.opmerking}"/>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>
                    Excl BTW:
                </td>
                <td>
                    <fmt:formatNumber value="${offerteForm.offerte.totaal}" type="currency" maxFractionDigits="2" minFractionDigits="2" currencySymbol="&euro;"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>
                   Incl BTW:
                </td>
                <td>
                    <fmt:formatNumber value="${offerteForm.offerte.totaalInclBtw}" maxFractionDigits="2" minFractionDigits="2" type="currency"  currencySymbol="&euro;"/>
                </td>
                <td></td>
            </tr>

            <c:if test="${empty offerteForm.offerte.status and not(offerteForm.offerte.orgineel==null)}">
                <tr>
                    <td colspan="5"><b>Extra regel toevoegen</b></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="text" name="nieuweOfferteRegel.extraRegelTaak" style="text-align: left"/>
                        <form:errors path="nieuweOfferteRegel.extraRegelTaak"  style="color: red;"/>
                    </td>
                    <td><input type="text" name="nieuweOfferteRegel.extraRegelDetails" style="text-align: left"/>
                        <form:errors path="nieuweOfferteRegel.extraRegelDetails"  style="color: red;"/>
                    </td>
                    <td>
                        <form:select path="nieuweOfferteRegel.extraRegelType"  cssStyle="width: 55px;" id="nieuweOfferteRegelExtraRegelType">
                            <form:options items="${types}"/>
                        </form:select>
                    </td>
                    <td>
                        <form:select path="nieuweOfferteRegel.extraRegelEenheid" cssStyle="width: 75px;" id="nieuweOfferteRegelextraRegelEenheid">
                            <form:option value=""/>
                            <form:options items="${eenheden}" itemLabel="lable" itemValue="lable"/>
                        </form:select>
                    </td>
                    <c:if test="${empty offerteForm.offerte.status}">
                        <td></td>
                    </c:if>
                    <td>
                        <input type="text" name="nieuweOfferteRegel.aantal" style="text-align: right" id="nieuweOfferteRegelofferteAantal"/>
                        <form:errors path="nieuweOfferteRegel.aantal"  style="color: red;"/>
                    </td>
                    <td>
                        <input type="text" name="nieuweOfferteRegel.eenheidsprijs" style="text-align: right" id="nieuweOfferteRegelofferteEenheidsprijs"/>
                        <form:errors path="nieuweOfferteRegel.eenheidsprijs"  style="color: red;"/>
                    </td>
                    <td>
                        <input type="text" name="nieuweOfferteRegel.regelTotaal" style="text-align: right" id="nieuweOfferteRegelRegelTotaal">
                        <form:errors path="nieuweOfferteRegel.regelTotaal"  style="color: red;"/>
                    </td>
                    <td></td>
                    <td></td>
                    <td><input type="submit" value="Toevoegen" class="inputbtn" name="action"/></td>
                </tr>
            </c:if>

        </table>
        <c:if test="${not (offerteForm.offerte.status eq 'Toegewezen' )}">
            <input type="submit" value="Bewaar en herbereken" class="inputbtn" name="action"/>
        </c:if>
        <c:if test="${offerteForm.offerte.status eq 'Toegewezen' }">
            <input type="submit" value="Btw aanpassen" class="inputbtn" name="action"/>
        </c:if>
    </form:form>

    <br/>
    <c:if test="${not empty offerteForm.opmerkingen}">
        <b>Opmerkingen:</b><br/>
        <c:forEach items="${offerteForm.opmerkingen}" var="opmerking">
            ${opmerking}<br/>
        </c:forEach>
    </c:if>
    <br/>
    <br/>
    Exporteren naar draft:
    <a href="/pad/s/bestek/meetstaat/offertes/${offerteForm.offerte.id}/export/draftOfferte-${offerteForm.offerte.inzender}.pdf" target="_blank"> PDF </a> /
    <a href="/pad/s/bestek/meetstaat/offertes/${offerteForm.offerte.id}/export/draftOfferte-${offerteForm.offerte.inzender}.xls" target="_blank"> Excel </a>
    <br />
    <br/>
<br/><br/>


</c:if>
<c:if test="${not (offerteForm.offerte.status eq 'Afgesloten')}">
    <c:if test="${not (offerteForm.offerte.status eq 'Toegewezen' )}">
        <input type="button"  id="buttonToewijzen" value="Toewijzen" title="Toewijzen" class="inputbtn"/>
    </c:if>
    <input type="button"  id="buttonAfsluiten" value="Afsluiten" title="Afsluiten" class="inputbtn"/>
</c:if>
<tiles:insert definition="laadJS" />
<script type="text/javascript">
    bestek_id = ${bestekDO.bestek_id};
    offerte_id = ${offerteForm.offerte.id};
</script>
<script type="text/javascript">
    laadBacking('lijsten/bestekmeetstaatofferteBacking');
</script>