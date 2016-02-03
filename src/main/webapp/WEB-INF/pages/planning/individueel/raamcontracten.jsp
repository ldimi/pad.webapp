<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div style="height: 30px; padding-top: 5px;">

    <form id="paramForm">
        <table>
            <tr>
                <td>Dossier houder:</td>
                <td>
                    <select name="doss_hdr_id"
                        onchange="window.open('/pad/s/planning/individueel/raamcontracten?doss_hdr_id='+this.options[this.selectedIndex].value,'_top')"
                        class="input">
                            <option value=""></option>
                            <c:forEach var="item" items="${dossierhouders}">
                                <c:if test="${item.doss_hdr_id eq hdr_id}">
                                    <option value="${item.doss_hdr_id}" selected="selected">${item.doss_hdr_b}</option>
                                </c:if>
                                <c:if test="${item.doss_hdr_id ne hdr_id}">
                                    <option value="${item.doss_hdr_id}">${item.doss_hdr_b}</option>
                                </c:if>
                            </c:forEach>
                    </select>
                </td>
                <td>Jaar:</td>
                <td>
                    <select name="jaar" class="input">
                        <option value="">Basis planning</option>
                        <c:forEach var="jaar" items="${DDH.jaren}" >
                            <option value="${jaar}">${jaar}</option>
                        </c:forEach>
                   </select>
                </td>
                <td>
                    <select name="benut_jn" class="input">
                        <option value="">incl. gerealiseerd</option>
                        <option value="N">excl. gerealiseerd</option>
                    </select>
                </td>
                <c:if test="${fn:length(raamContractenDD) > 0}">
                    <td>A-dossier:</td>
                    <td><select name="dossier_id" class="input">
                            <c:forEach var="item" items="${raamContractenDD}">
                                <option value="${item.value}">${item.label}</option>
                            </c:forEach>
                    </select></td>
                    <td><input type="button" id="ophalenBtn" value="Ophalen"
                        class="inputbtn invisible" /></td>
                </c:if>
        </table>
    </form>

    <c:if test="${raamContractenDD.size() == 0}">
        <div style="margin: 5px;">Geen raamcontracten te plannen.</div>
    </c:if>

</div>

<div id="overzicht_div" class="invisible"
    style="position: absolute; top: 35px; left: 5px; right: 5px; bottom: 5px;">
    <div id="overzicht_grid_div"
        style="position: absolute; top: 0px; left: 0px; right: 0px; bottom: 260px;"></div>
    <div
        style="position: absolute; height: 30px; left: 0px; right: 0px; bottom: 230px;">
        Totaal voorspeld saldo : <span id="tot_voorspeld_saldo"
            style="font-weight: bold;"></span>
    </div>

    <div id="planningIGB_div" class="invisible"
        style="position: absolute; height: 230px; left: 0px; right: 0px; bottom: 0px;">
    </div>
</div>

<div id="bestekDetailsDialog" class="hidden">
    <div id="bestekDetails_grid_div" style="position: absolute; top: 2px; left: 2px; right: 2px; bottom: 2px;" ></div>
</div>
<div id="faseDetailsDialog" class="hidden">
    <div id="faseDetails_grid_div" style="position: absolute; top: 2px; left: 2px; right: 2px; bottom: 2px;" ></div>
</div>

<tiles:insert
    page="/WEB-INF/pages/planning/individueel/planningLijnDialog.jsp"
    flush="true" />
<tiles:insert definition="laadJS" />

<script type="text/javascript">
    laadBacking('planning/individueel/gegroepeerdeOpdrachtenBacking');
</script>
