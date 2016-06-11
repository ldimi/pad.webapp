<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
    <tr>
        <td>
            <logic:notEmpty name="acties" scope="request">
                <display:table id="actie" name="requestScope.acties" defaultsort="1" class="lijst width980" decorator="be.ovam.art46.decorator.ActieLijstDecorator">
                    <display:column title="Type">
                        <html:select name="actie" property="actie_type_id" styleClass="input" disabled="true">
                            <html:optionsCollection name="types"  label="actie_type_b" value="actie_type_id" />
                        </html:select>
                    </display:column>
                    <display:column title="Sub Type">
                        <html:select name="actie" property="actie_sub_type_id" styleClass="input" disabled="true">
                            <html:option value=""></html:option>
                            <html:optionsCollection name="subtypes"  label="actie_sub_type_b" value="actie_sub_type_id" />
                        </html:select>
                    </display:column>
                    <display:column property="actie_d" title="Datum gepland van" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                    <display:column property="stop_d" title="Datum gepland tot" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                    <display:column property="realisatie_d" title="Datum gerealiseerd" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                    <display:column property="commentaar" title="Commentaar" />
                    <display:column property="rate" title="Tijd" style="text-align:right;" decorator="be.ovam.art46.decorator.BigDecimalDecorator" />
                    <display:column style="text-align: center;">
                        <logic:present role="adminArt46,adminIVS">
                            <img src='<html:rewrite page="/"/>/resources/images/edit.gif' alt="Actie wijzigen" title="Actie wijzigen" onclick="popupWindow('/pad/s/bestek/${bestekDO.bestek_id}/acties/<bean:write name="actie" property="actie_id"/>/wijzig/','Actie')"/>
                        </logic:present>
                    </display:column>
                    <display:column style="text-align: center;">
                        <logic:present role="adminArt46,adminIVS">
                            <a href='/pad/s/bestek/${bestekDO.bestek_id}/acties/<bean:write name="actie" property="actie_id"/>/verwijder/'>
                                <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen"/>
                            </a>
                        </logic:present>
                    </display:column>
                    <display:setProperty name="basic.msg.empty_list_row" value=""/>
                </display:table>
            </logic:notEmpty>
        </td>
        <td valign="top">
            <logic:present role="adminArt46,adminIVS">
                <img src='<html:rewrite page="/"/>/resources/images/add.gif' alt="Actie toevoegen" title="Actie toevoegen" onclick="popupWindow('/pad/s/bestek/${bestekDO.bestek_id}/acties/aanmaken/','Actie')"/>
            </logic:present>
        </td>
    </tr>
</table>