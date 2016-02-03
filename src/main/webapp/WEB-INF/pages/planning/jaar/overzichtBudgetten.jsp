<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" uri="/tags/custom"%>



<div style="border: 1px solid;
            position: absolute;
            top: 40px;
            left: 0px;
            background-color: #fdfdfd;
            padding: 5px;
            ">

    <div style="height: 30px;">
        <form id="paramForm" >
            <table>
                <tr>
                    <td>Jaar:</td>
                    <td>
                        <select name="jaar" class="input">
                            <c:forEach var="jaar" items="${DDH.jaren}" >
                                <option value="${jaar}">${jaar}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="button" id="ophalenBtn" value="Ophalen" class="inputbtn invisible"/></td>
            </table>
        </form>
    </div>

    <div id="jb_content_div" class="invisible">
        <div style="margin: 5px 5px 2px 0px; font-weight: bold;">Overzicht per artikel:</div>
        <div id="budget_per_budgetcode_div"
            style="width: 1000px;">
        </div>

        <div style="margin: 5px 5px 2px 0px; font-weight: bold;">Overzicht per programma:</div>
        <div id="budget_per_programmacode_div"
            style="width: 1000px;">
        </div>

        <logic:present role="adminArt46">
            <button id="markeerBtn">Markeer huidige planning</button>
        </logic:present>

        <div style="height: 30px; padding-top: 5px;">
            <form id="paramGrafiekForm" >
                <input type="text" name="jaar" class="hidden" />
                <table>
                    <tr>
                        <td>Programma:</td>
                        <td>
                            <select name="programma_code" class="input"></select>
                        </td>
                        <td>Goedgekeurde planning:</td>
                        <td>
                            <select name="markering_id" class="input">
                                <option value=""></option>
                                <custom:options items="${DDH.planningMarkeringenDD}" />
                            </select>
                        </td>
                        <td><input type="button" id="genereerGrafiekBtn" value="Grafiek" class="inputbtn"/></td>
                </table>
            </form>
        </div>

        <div id="canvas_div" class="invisible" style="height: 370px;">
        </div>

    </div>

</div>

<div id="markeerPlanningDiv" >
</div>

<div id="budgetPerProgrammaDetailDiv">
</div>


<tiles:insertDefinition name="laadJS" />

<script type="text/javascript">
    _G_ = _G_ || {};

    <logic:present role="adminArt46">
        _G_.isAdminArt46 = true;
    </logic:present>


    _G_.programmaList = <custom:outJson object="programmaList" />

    laadBacking('planning/jaar/overzicht/overzichtBudgettenBacking');
</script>
