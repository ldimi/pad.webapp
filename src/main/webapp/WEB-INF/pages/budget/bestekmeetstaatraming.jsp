<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${not empty bindingResult}">
    <b style="color: red">Deze Meetstaat kan niet opgeslagen worden: Sommige waarden zijn niet correct ingevoerd</b>
</c:if>
<c:if test="${not empty defintiefMakenValidatieErrors}">
    <p style="color: #ff0000">
        U kan deze meetstaat nog niet definitief maken omdat:
        <c:forEach items="${defintiefMakenValidatieErrors}" var="error">
            <br/> ${error}
        </c:forEach>
    </p>
</c:if>

<form:form method="post" accept-charset="UTF-8" action="/pad/s/bestek/${bestekId}/meetstaat/raming/" modelAttribute="ramingForm">
    <table>
        <input type="hidden" value="${bestekId}" name="bestekId"/>
        <tr>
        <th>Postnr.</th>
        <th>Taak</th>
        <th>Details</th>
        <th>Type</th>
        <th>Eenheid</th>
        <th>Aantal</th>
        <th>Eenheidsprijs</th>
        <th>Detail totaal</th>
        <th>Sub totaal</th>
        <th>Post totaal</th>
        </tr>
        <c:forEach items="${ramingForm.meetstaatRegels}" var="meetstaatRegel" varStatus="status">
            <c:choose>
                <c:when test="${status.index mod 2 == 0 }">
                    <c:set var="styleclass" value="tableRowEven"/>
                </c:when>
                <c:otherwise>
                    <c:set var="styleclass" value="tableRowOdd"/>
                </c:otherwise>
            </c:choose>
            <tr class="${styleclass}">
                <td><c:out value="${meetstaatRegel.cleanPostnr}"/>
                    <input type="hidden" name="meetstaatRegels[${status.index}].id" value="<c:out value="${meetstaatRegel.id}"/>"/>
                    <input type="hidden" name="meetstaatRegels[${status.index}].cleanPostnr" value="<c:out value="${meetstaatRegel.cleanPostnr}"/>"/>
                    <input type="hidden" name="meetstaatRegels[${status.index}].postnr" value="${meetstaatRegel.postnr}"/>
                    <input type="hidden" name="meetstaatRegels[${status.index}].bestek.bestek_id" value="${meetstaatRegel.bestek.bestek_id}"/>
                    <input type="hidden" name="meetstaatRegels[${status.index}].taak" value="${meetstaatRegel.taak}"/>
                    <input type="hidden" name="meetstaatRegels[${status.index}].details" value="${meetstaatRegel.details}"/>
                    <input type="hidden" name="meetstaatRegels[${status.index}].type" value="${meetstaatRegel.type}"/>
                    <input type="hidden" name="meetstaatRegels[${status.index}].eenheid" value="${meetstaatRegel.eenheid}"/>
                </td>
                <td style="width: 300px"><c:out value="${meetstaatRegel.taak}"/></td>
                <td style="width: 300px">${meetstaatRegel.details} </td>
                <td>${meetstaatRegel.type}</td>
                <td>${meetstaatRegel.eenheid}</td>
                <td style="text-align: right ">
                   <c:if test="${meetstaatRegel.type=='VH'}">
                    <c:choose>
                        <c:when test="${meetstaatLockt eq 'false'}">
                            <input type="text" name="meetstaatRegels[${status.index}].aantal" value="<fmt:formatNumber value="${meetstaatRegel.aantal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                            <form:errors path="meetstaatRegels[${status.index}].aantal" style="color: red;"/>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="meetstaatRegels[${status.index}].aantal" value="<fmt:formatNumber value="${meetstaatRegel.aantal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                            <fmt:formatNumber value="${meetstaatRegel.aantal}" maxFractionDigits="2" minFractionDigits="2"/>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                </td>
                <td style="text-align: right ">
                    <c:if test="${meetstaatRegel.type=='VH'}">
                        <input type="text" name="meetstaatRegels[${status.index}].eenheidsprijs" style="text-align: right " value="<fmt:formatNumber value="${meetstaatRegel.eenheidsprijs}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                        <form:errors path="meetstaatRegels[${status.index}].eenheidsprijs" style="color: red;"/>
                    </c:if>
                </td>
                <td style="text-align: right ">
                    <c:choose>
                        <c:when test="${(meetstaatRegel.type=='TP' or meetstaatRegel.type=='SPM') and meetstaatRegel.level>2}">
                            <input type="text" name="meetstaatRegels[${status.index}].regelTotaal" style="text-align: right " value="<fmt:formatNumber value="${meetstaatRegel.regelTotaal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                            <form:errors path="meetstaatRegels[${status.index}].regelTotaal" style="color: red;"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:formatNumber value="${meetstaatRegel.detailTotaal}" maxFractionDigits="2" minFractionDigits="2"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: right ">
                    <c:choose>
                        <c:when test="${(meetstaatRegel.type=='TP' or meetstaatRegel.type=='SPM') and meetstaatRegel.level==2}">
                            <input type="text" name="meetstaatRegels[${status.index}].regelTotaal" style="text-align: right " value="<fmt:formatNumber value="${meetstaatRegel.regelTotaal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                            <form:errors path="meetstaatRegels[${status.index}].regelTotaal" style="color: red;"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:formatNumber value="${meetstaatRegel.subTotaal}" maxFractionDigits="2" minFractionDigits="2"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: right ">
                    <c:choose>
                        <c:when test="${(meetstaatRegel.type=='TP' or meetstaatRegel.type=='SPM') and meetstaatRegel.level==1}">
                            <input type="text" name="meetstaatRegels[${status.index}].regelTotaal" style="text-align: right " value="<fmt:formatNumber value="${meetstaatRegel.regelTotaal}" maxFractionDigits="2" minFractionDigits="2"/>"/>
                            <form:errors path="meetstaatRegels[${status.index}].regelTotaal" style="color: red;"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:formatNumber value="${meetstaatRegel.postTotaal}" maxFractionDigits="2" minFractionDigits="2"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Bewaar en herbereken" class="inputbtn"/>
</form:form>

Exporteren naar draft:
<a href="/pad/s/bestek/meetstaat/export/draftMeetstaat-${bestekId}.pdf" target="_blank"> PDF </a> / <a href="/pad/s/bestek/meetstaat/export/draftMeetstaat-${bestekId}.xls" target="_blank"> Excel </a>
<br/><br/>
<c:if test="${meetstaatLockt eq 'false'}">
    <input type="button" value="Meetstaat definitief maken" onclick="window.location='/pad/s/bestek/${bestekId}/meetstaat/definitiefmaken';" />
</c:if>
<br/><br/>