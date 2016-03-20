<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%-- <%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<tilesx:useAttribute id="extraUrl" name="extraUrl" ignore="true" classname="java.lang.String"/>

<table>
    <tr>
        <td>
            <logic:notEmpty name="acties" scope="request">
                <display:table id="actie" name="requestScope.acties" defaultsort="1" class="lijst width980" decorator="be.ovam.art46.decorator.ActieLijstDecorator">
                    <display:column title="Type">
                        <html:select name="actie" property="actie_type_id" styleClass="input" disabled="true">
                            <html:optionsCollection name="DDH" property="alleActiesTypes" label="actie_type_b" value="actie_type_id" />
                        </html:select>
                    </display:column>
                    <display:column title="Sub Type">
                        <html:select name="actie" property="actie_sub_type_id" styleClass="input" disabled="true">
                            <html:option value=""></html:option>
                            <html:optionsCollection name="DDH" property="actiesubtypelijst" label="actie_sub_type_b" value="actie_sub_type_id" />
                        </html:select>
                    </display:column>
                    <display:column property="actie_d" title="Datum gepland van" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                    <display:column property="stop_d" title="Datum gepland tot" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                    <display:column property="realisatie_d" title="Datum gerealiseerd" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                    <display:column property="commentaar" title="Commentaar" />
                    <display:column property="rate" title="Tijd" style="text-align:right;" decorator="be.ovam.art46.decorator.BigDecimalDecorator" />
                    <display:column style="text-align: center;">
                        <logic:present role="adminIVS">
                            <img src='<html:rewrite page="/"/>/resources/images/edit.gif' alt="Actie wijzigen" title="Actie wijzigen" onclick="popupWindow('<html:rewrite action="actie"/>?crudAction=read&popup=yes&forward=<tiles:getAsString name="forward"/>&dossier_type=<tiles:getAsString name="dossier_type"/>&actie_id=<bean:write name="actie" property="actie_id"/>','Actie')"/>
                        </logic:present>
                    </display:column>
                    <display:column style="text-align: center;">
                        <logic:present role="adminIVS">
                            <a href='actie.do?crudAction=delete&actie_id=<bean:write name="actie" property="actie_id"/>&dossier_type=<tiles:getAsString name="dossier_type"/>&forward=<tiles:getAsString name="forward"/>&useForward=true&dossier_id=<tiles:getAsString name="parent_id" />&dossier_id_jd=<tiles:getAsString name="parent_id" />'>
                                    <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen"/>
                            </a>
                        </logic:present>
                    </display:column>
                    <display:setProperty name="basic.msg.empty_list_row" value=""/>
                </display:table>
            </logic:notEmpty>
        </td>
        <td valign="top">
            <logic:present role="adminIVS">
                <img src='<html:rewrite page="/"/>/resources/images/add.gif' alt="Actie toevoegen" title="Actie toevoegen" onclick="popupWindow('<html:rewrite action="actie"/>?crudAction=view&popup=yes&forward=<tiles:getAsString name="forward"/>&dossier_type=<tiles:getAsString name="dossier_type"/>&actie_id=0&dossier_id_jd=<tiles:getAsString name="parent_id" />&dossier_id=<tiles:getAsString name="parent_id" />&bestek_id=<tiles:getAsString name="parent_id" /><%=extraUrl %>','Actie')"/>
            </logic:present>
        </td>
    </tr>
</table>