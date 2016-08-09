<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<bean:parameter name="popup" id="popup" value="no"/>
<table bgcolor="#eeeeee">
    <tr>
        <td align="right">
            <logic:present role="adminArt46,adminIVS,adminBOA">
                <html:link action="adrescontact?crudAction=view" paramName="adresform" paramProperty="adres_id" paramId="adres_id" scope="request">
                    <html:img border="0" page="/resources/images/add.gif" title="Toevoegen contact"/>
                </html:link>
            </logic:present>
        </td>
    </tr>
    <logic:present name="adresContactenlijst" scope="request">
        <logic:notEmpty name="adresContactenlijst" scope="request">
            <tr>
                <td>
                    <display:table id="adrescontact" name="requestScope.adresContactenlijst" requestURI="/adres.do?crudAction=read" export="true" defaultsort="1" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                        <display:column titleKey="adrescontact.lijst.naam" sortable="true">
                            <a href='<html:rewrite action="adrescontact"/>?crudAction=read&adres_id=<bean:write name="adrescontact" property="adres_id"/>&contact_id=<bean:write name="adrescontact" property="contact_id"/>'>
                                <bean:write name="adrescontact" property="naam_voornaam"/>
                            </a>
                        </display:column>
                        <display:column property="tel" titleKey="adrescontact.lijst.tel" sortable="true"/>
                        <display:column property="fax" titleKey="adrescontact.lijst.fax" sortable="true"/>
                        <display:column property="gsm" titleKey="adrescontact.lijst.gsm" sortable="true"/>
                        <display:column property="email" titleKey="adrescontact.lijst.email" sortable="true"/>
                        <display:column property="functie" titleKey="adrescontact.lijst.functie" sortable="true"/>
                        <display:column property="commentaar" titleKey="adrescontact.lijst.commentaar" sortable="true"/>
                        <display:column property="actief_jn" title="actief ?" sortable="true"/>
                        <display:column>
                            <a href='<html:rewrite action="adrescontact"/>?crudAction=delete&adres_id=<bean:write name="adrescontact" property="adres_id"/>&contact_id=<bean:write name="adrescontact" property="contact_id"/>'>
                                    <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen contact"/>
                            </a>
                        </display:column>
                    </display:table>
                </td>
            </tr>
        </logic:notEmpty>
    </logic:present>
</table>
