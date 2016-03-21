<%@ page language="java" %>
<%-- <%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="planning_js_div" class="planning" style="width: 1080px; height: 200px;">
</div>

<tiles:insertTemplate template="/WEB-INF/pages/layouts/jsView.jsp" />

<c:if test="${dossierart46form.dossier_type != 'X'}">

    <div class="planning" style="width: 1080px; margin-top: 30px;">
        <strong>Voorstel planning door dossierhouders</strong>

        <table>
            <tr>
                <td>
                    <logic:notEmpty name="ramingen" scope="request">
                        <display:table id="raming" name="ramingen" class="lijst width980">
                            <display:column title="Type">
                                <html:select name="raming" property="fase_id" styleClass="input" disabled="true">
                                    <html:options collection="fases" labelProperty="fase_b" property="fase_id"/>
                                </html:select>
                            </display:column>
                            <display:column title="Jaartal van gunning" class="center">
                                <bean:write name="raming" property="maand"/>/<bean:write name="raming" property="jaartal"/>
                            </display:column>
                            <display:column property="raming" title="Raming VK te voorzien" decorator="be.ovam.art46.decorator.CurrencyDecorator" class="number"/>
                            <display:column title="Prioriteit">
                                <html:select name="raming" property="prioriteit_id" styleClass="input" disabled="true" >
                                    <html:optionsCollection name="DDH" property="prioriteitRaming" label="prioriteit_b" value="prioriteit_id" />
                                </html:select>
                            </display:column>
                            <display:column property="budget_vastgelegd" title="Budget vastgelegd" decorator="be.ovam.art46.decorator.CurrencyDecorator" class="number"/>
                            <display:column title="Afgesloten" class="center">
                                <html:checkbox name="raming" property="afgesloten_s" value="1" styleClass="checkbox" disabled="true"/>
                            </display:column>
                            <display:column property="commentaar" title="Commentaar" />
                            <display:column style="text-align: center;">
                                <logic:present role="adminArt46">
                                    <img src='<html:rewrite page="/"/>/resources/images/edit.gif' alt="Raming wijzigen" title="Raming wijzigen" onclick="popupWindow('<html:rewrite action="raming"/>?crudAction=read&popup=yes&dossier_type=${dossierart46form.dossier_type}&raming_id=<bean:write name="raming" property="raming_id"/>&dossier_id=${dossierart46form.id}&forward=forwardboa','Raming')"/>
                                </logic:present>
                            </display:column>
                            <display:column style="text-align: center;">
                                <logic:present role="adminArt46">
                                    <a href='raming.do?crudAction=delete&raming_id=<bean:write name="raming" property="raming_id"/>&dossier_type=${dossierart46form.dossier_type}&dossier_id=${dossierart46form.id}&useForward=true&forward=forwardboa'>
                                            <html:img border="0" page="/resources/images/delete.gif" title="Raming verwijderen"/>
                                    </a>
                                </logic:present>
                            </display:column>
                            <display:setProperty name="basic.msg.empty_list_row" value=""/>
                        </display:table>
                    </logic:notEmpty>
                </td>
            </tr>
        </table>
    </div>

    <c:if test="${dossierart46form.dossier_type == 'B'}">
        <div class="planning" style="width: 1080px; margin-top: 30px;">
            <strong>Laatst goedgekeurde planning</strong>
            <table class="lijst width980">
                <tr>
                    <th>Fase</th>
                    <th>Jaartal van gunning</th>
                    <th>Raming VK te voorzien</th>
                    <th>Prioriteit</th>
                    <th>Budget vastgelegd</th>
                    <th>Datum goedkeuring</th>
                    <th>Commentaar</th>
                </tr>
                <logic:notEmpty name="ramingenHistoriek" scope="request">
                    <logic:iterate id="raming_hist" name="ramingenHistoriek" scope="request">
                        <tr>
                            <td>
                                <html:select name="raming_hist" property="fase_id" styleClass="input" disabled="true">
                                    <html:optionsCollection name="DDH" property="faseRaming" label="fase_b" value="fase_id" />
                                </html:select>
                            </td>
                            <td align="center">
                                <bean:write name="raming_hist" property="maand_voorstel"/>/<bean:write name="raming_hist" property="jaartal_voorstel"/>
                            </td>
                            <td align="right">
                                <bean:write name="raming_hist" property="raming" formatKey="number.format" filter="false"/>
                            </td>
                            <td align="center">
                                <html:select name="raming_hist" property="prioriteit_id" styleClass="input" disabled="true" >
                                    <html:optionsCollection name="DDH" property="prioriteitRaming" label="prioriteit_b" value="prioriteit_id" />
                                </html:select>
                            </td>
                            <td align="right">
                                <bean:write name="raming_hist" property="budget_vastgelegd" formatKey="number.format" filter="false"/>
                            </td>
                            <td align="center">
                                <bean:write name="raming_hist" property="goedkeur_d" formatKey="date.format"/>
                            </td>
                            <td>
                                <bean:write name="raming_hist" property="commentaar"/>
                            </td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
            </table>
        </div>
    </c:if>

    <logic:notEmpty name="dossierart46form" property="dossier_nr" scope="session">
        <div class="planning"  style="width: 1080px; margin-top: 30px;">
            <strong>Acties</strong>
            <tiles:insertDefinition name="actielijst2">
                <tiles:putAttribute name="dossier_type" value="B"/>
                <tiles:putAttribute name="forward" value="forwardivs"/>
                <tiles:putAttribute name="parent_id" value="${dossierart46form.id}"/>
                <tiles:putAttribute name="roles" value="adminIVS"/>
                <tiles:putAttribute name="extraUrl" value=""/>
            </tiles:insertDefinition>
        </div>
    </logic:notEmpty>
</c:if>


