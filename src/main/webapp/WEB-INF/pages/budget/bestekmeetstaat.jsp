<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="position: absolute;
           top: 5px;
           left: 5px;
           right: 0px;
           bottom: 0px;">

    <c:if test="${!empty meetstaatPdfBrief || !empty meetstaatExcelBrief}">
        <c:if test="${!empty meetstaatPdfBrief}">
            <a href='<bean:write name="meetstaatPdfBrief" property="editUrl"/>' target="_blank">
                <bean:write name="meetstaatPdfBrief" property="dms_filename"/><html:img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>
            </a>
        </c:if>
        <c:if test="${!empty meetstaatExcelBrief}">
            <a href='<bean:write name="meetstaatExcelBrief" property="editUrl"/>' target="_blank">
                <bean:write name="meetstaatExcelBrief" property="dms_filename"/><html:img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>
            </a>
        </c:if>
    </c:if>

    <c:if test="${empty meetstaatPdfBrief && empty meetstaatExcelBrief}">
        <div style="height: 30px;">
            <div style="float: left; margin-right: 50px;">
                <b>Nieuwe meetstaat: </b>
            </div>
            <div style="float: left; margin-right: 70px;">
                <input type="button" id="uploadCSVBtn"  title="Nieuwe meetstaat Aanmaken op basis van een CSV bestand" value="Aanmaken op basis van een CSV bestand"/>
            </div>
            <div style="float: left;">
                <tiles:insert page="../util/uploader.jsp"/>
            </div>
            <div  style="float: left;">
                <form id="templateMeetstaat" autocomplete="off">
                    Aanmaken op basis van template:
                    <select id="template" name="template" class="input">
                        <option value=""></option>
                        <c:forEach var="template" items="${templates}" >
                            <option value="${template.id}" > ${template.naam}</option>
                        </c:forEach>
                    </select>
                    <input type="button" id="laadTemplateBtn"  title="Laad template" value="Laad template"/>
                </form>
            </div>
            <div style="clear: both;">
            </div>
            <div  style="float: left; height: 30px;">
                <form id="anderBestekMeetstaat" autocomplete="off">
                    Aanmaken op basis van een bestek Nr.:
                    <input id="anderbestek" naam="anderbestek" class="input">
                    <input type="button" id="laadAnderBestekBtn"  title="Laad meetstaat" value="Laad meetstaat"/>
                </form>
            </div>
        </div>
    </c:if>

    <div id="meetstatenGrid"
        style="position: absolute;
               top: 60px;
               left: 0px;
               right: 0px;
               bottom: 25px;">
    </div>

    <div style="position: absolute;
                left: 0px;
                bottom: 5px;
                right: 0px;
                height: 20px;" >

        <c:if test="${empty meetstaatPdfBrief}">
            <div style="float: left;">
                <input type="button" id="saveMeetstaatBtn"  title="Meetstaat Opslaan" value="Meetstaat Opslaan"/>
            </div>
        </c:if>
        <c:if test="${!empty meetstaatPdfBrief && empty offertes}">
            <div style="float: left;">
                <input type="button" value="Meetstaat ontgrendelen" onclick="window.location='/pad/s/bestek/${bestekId}/meetstaat/ontgrendel';" />
            </div>
        </c:if>
        <div id="waarschuwingNietOpgeslagenMeetstaat" hidden="hidden"
            style="color: red;
                float: left;
                margin-top: 3px;
                margin-left: 10px;">
            <b>Opgelet: Deze meetstaat is nog niet opgeslagen!!</b>
        </div>

    </div>


    <div id="bestekmeetstaatRegelDialog" class="hidden" title="Meetstaat regel">
        <form id="bestekmeetstaatRegelForm" autocomplete="off">
            <table>
                <tr>
                    <td>Postnr</td>
                    <td>
                        <input type="text" name="postnr"/>
                        <input type="hidden" name="oldPostnr"/>
                        <input type="hidden" name="bestekId"/>
                        <input type="hidden" name="id"/>
                        <input type="hidden" name="crudStatus"/>
                        <input type="hidden" name="templateId"/>
                    </td>
                </tr>
                <tr>
                    <td>Taak</td>
                    <td><input type="text" name="taak"/> </td>
                </tr>
                <tr>
                    <td>Details</td>
                    <td><input type="text" name="details"/> </td>
                </tr>
                <tr id="typeRow">
                    <td >Type</td>
                    <td>
                        <select name="type" class="input">
                            <option value=""></option>
                            <c:forEach var="type" items="${types}" >
                                <option value="${type}" > ${type}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr id="eenheidRow">
                    <td>Eenheid</td>
                    <td>
                        <select name="eenheid" class="input">
                            <option value=""></option>
                            <c:forEach var="item" items="${eenheden}" >
                                <option value="${item.lable}" > ${item.lable}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr id="aantalRow">
                    <td>Aantal</td>
                    <td><input id="Aantal" type="text" name="aantal"/> </td>
                </tr>
                <tr id="eenheidsprijsRow">
                    <td>Eenheidsprijs</td>
                    <td><input type="text" name="eenheidsprijs"/> </td>
                </tr>
                <tr id="regelTotaalRow">
                    <td>Bedrag</td>
                    <td><input type="text" name="regelTotaal"/> </td>
                </tr>
            </table>
        </form>
        <input type="button" id="bewaarBtn" title="Bewaar" value="Bewaar"/>
        <input type="button" id="bewaarVolgendeBtn" title="Aanpassen en open volgende" value="Aanpassen en open volgende"/>
        <input type="button" id="annuleerBtn" title="Annuleer" value="Annuleer"/>
    </div>
</div>

<tiles:insert definition="laadJS" />
<script type="text/javascript">
    isLocked = ${meetstaatLocked}
    bestekId = ${bestekId};
</script>
<script type="text/javascript">
    laadBacking('budget/meetstaat/bestekmeetstaatBacking');
</script>