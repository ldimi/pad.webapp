<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="height: 30px; padding-top: 5px;">
    <form id="paramForm" >
        <table>
            <tr>
                <td>Dossier houder:</td>
                <td>
                    <select name="doss_hdr_id" onchange="window.open('/pad/s/planning/individueel/bodemEnAfval?doss_hdr_id='+this.options[this.selectedIndex].value,'_top')" class="input">
                        <option value=""></option>
                        <c:forEach var="item" items="${dossierhouders}" >
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
                <td>Dossier nr:</td>
                <td>
                    <select name="dossier_id" class="input">
                        <option value=""></option>
                        <c:forEach var="item" items="${dossiersDD}" >
                            <option value="${item.value}">${item.label}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="button" id="ophalenBtn" value="Ophalen" class="inputbtn invisible"/></td>
        </table>
    </form>
</div>

<div id="planningIGB_div" class="invisible"
    style="position: absolute;
           top: 35px;
           left: 5px;
           right: 5px;
           bottom: 5px;">
</div>

<tiles:insert page="/WEB-INF/pages/planning/individueel/planningLijnDialog.jsp" flush="true" />

<tiles:insert definition="laadJS" />

<script type="text/javascript">
    laadBacking('planning/individueel/bodemEnAfvalBacking');
</script>
