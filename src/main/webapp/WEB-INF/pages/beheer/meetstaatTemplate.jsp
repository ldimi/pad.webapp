<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 Template naam : <input type="text" name="naam" id="naam" value="${naam}"/><br/>

        <div>
            <b>Nieuwe template meetstaat: </b><br/>
            <input type="button" id="uploadCSVBtn"  title="Nieuwe template meetstaat Aanmaken op basis van een CSV bestand" value="Aanmaken op basis van een CSV bestand"/>
            <tiles:insert page="../util/uploader.jsp"/>
            <br/>
        </div>


    <div id="waarschuwingNietOpgeslagenMeetstaat" hidden="hidden" style="color: red"><b>Opgelet: Deze meetstaat is nog niet opgeslagen!!</b></div>
    <div id="meetstatenGrid" style="width:1020px; height:700px"></div>
    <input type="button" id="saveMeetstaatBtn"  title="Meetstaat Template Opslaan" value="Meetstaat Template Opslaan"/>



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
                                <option value="${item.code}" > ${item.lable}</option>
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
    <tiles:insert definition="laadJS" />
    <script type="text/javascript">
        templateId = ${templateId};
    </script>
    <script type="text/javascript">
        laadBacking('beheer/meetstaatTemplateBacking');
    </script>

