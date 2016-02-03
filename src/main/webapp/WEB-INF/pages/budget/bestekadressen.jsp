<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table>
    <tr>
        <logic:notEmpty name="bestekadressen" scope="request">
            <td>
                <display:table id="adrescontact" name="requestScope.bestekadressen" requestURI="/pad/s/bestek/${bestekId}/opdrachthouders/" class="lijst width980">
                    <display:column property="naam_l" title="Naam" href="adres.do?crudAction=read" paramId="adres_id" paramProperty="adres_id" sortable="true"/>
                    <display:column property="naam_contact_l" title="Contact persoon" class="center" />
                    <display:column property="functie" title="Functie" class="center"/>
                    <display:column property="tel" title="Telefoon" class="center"/>
                    <display:column property="gsm" title="GSM" class="center"/>
                    <display:column property="email" title="Email" class="center"/>
                    <logic:present role="adminArt46,adminIVS,adminBOA,adminRegister">
                        <display:column class="center">
                            <logic:present role="adminArt46,adminIVS">
                                <a href='/pad/s/bestek/<bean:write name="adrescontact" property="bestek_id"/>/opdrachthouders/delete?adres_id=<bean:write name="adrescontact" property="adres_id"/>&contact_id=<bean:write name="adrescontact" property="contact_id"/>'>
                                    <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen"/>
                                </a>
                            </logic:present>
                        </display:column>>
                    </logic:present>
                </display:table>
            </td>
        </logic:notEmpty>
        <td align="right" valign="top">
            <logic:present role="adminArt46,adminIVS">
                <img src='<html:rewrite page="/"/>/resources/images/add.gif' title="Opdrachthouder toevoegen" onclick="popupWindow('startsearch.do?popup=yes&forwardURL=/s/bestek/${bestekId}/opdrachthouders/&searchFlag=bestekadres&searchForward=success_zoek_contact&searchId=${bestekId}', 'Adressen');"/>
            </logic:present>
        </td>
    </tr>
</table>