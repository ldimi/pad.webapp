<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>

    <c:if test="${dossierart46form.dossier_type == 'B'}">
        <tr>
            <td>
                <table class="planning">
                    <tr>
                        <th>Dossierhouders</th>
                    </tr>
                    <logic:iterate id="dossierhouder" name="dossierhouders" scope="request" indexId="index">
                        <tr>
                            <td align='left'>
                                <bean:write name="dossierhouder" property="ambtenaar_b"/> (<bean:write name="dossierhouder" property="type"/>)
                            </td>
                        </tr>
                    </logic:iterate>
                </table>
            </td>
        </tr>
        <tr>
            <td height="30"></td>
        </tr>
    </c:if>
    <tr>
        <td>
            <div class="planning">
                <strong>Contactpersonen</strong>
                <table>
                    <tr>
                        <logic:notEmpty name="dossiercontactlijst" scope="request">
                            <td>
                                <display:table id="adrescontact" name="requestScope.dossiercontactlijst" requestURI="/dossierdetails.do?selectedtab=Contact" class="lijst width980">
                                    <display:column property="naam_l" title="Naam" href="adres.do?crudAction=read" paramId="adres_id" paramProperty="adres_id" sortable="true"/>
                                    <display:column title="Type">
                                        <html:select name="adrescontact" property="type_id" styleClass="input" disabled="true">
                                            <html:optionsCollection name="DDH" property="adrestypes" label="adres_type_b" value="adres_type_id" />
                                        </html:select>
                                    </display:column>
                                    <display:column  title="Contact" sortable="true" class="center">
                                        <logic:notEqual name="adrescontact" property="contact_id" value="0">
                                            <html:link action="adrescontact?crudAction=read" paramId="contact_id" paramName="adrescontact" paramProperty="contact_id">
                                                <bean:write name="adrescontact" property="naam_contact_l"/>
                                            </html:link>
                                        </logic:notEqual>
                                    </display:column>
                                    <display:column property="functie" title="Functie" class="center"/>
                                    <display:column property="tel" title="Tel" class="center"/>
                                    <display:column property="gsm" title="Gsm" class="center"/>
                                    <display:column property="email" title="Email" class="center"/>
                                    <logic:present role="adminArt46,adminIVS,adminBOA,adminRegister">
                                        <display:column class="center">
                                            <a href="/pad/s/dossier/<bean:write name="dossier" property="id"/>/contact/delete?adres_id=<bean:write name="adrescontact" property="adres_id"/>&contact_id=<bean:write name="adrescontact" property="contact_id"/>" />
                                                <html:img border="0" page="/resources/images/delete.gif" alt="Delete contact"/>
                                            </a>
                                        </display:column>>
                                    </logic:present>
                                </display:table>
                            </td>
                        </logic:notEmpty>
                        <td align="right" valign="top">
                            <logic:present role="adminArt46,adminIVS,adminBOA,adminRegister">
                                <img src='<html:rewrite page="/"/>/resources/images/add.gif' onclick="popupWindowPar('startsearch.do?popup=yes&searchFlag=dossiercontact&searchForward=success_zoek_contact&searchId=<bean:write name="dossierart46form" property="id"/>&forwardURL=', 'Adressen','/dossierdetails.do?selectedtab=Contact');"/>
                            </logic:present>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <td height="30"></td>
    </tr>
    <tr>
        <td>
            <div class="planning">
                <strong>Geadresseerde</strong>
                <table>
                    <tr>
                        <logic:notEmpty name="alle_adressen" scope="request">
                            <td>
                                <display:table id="adres" name="requestScope.alle_adressen" requestURI="/dossierdetails.do?selectedtab=Contact" class="lijst width980">
                                    <display:column property="naam_l" title="Naam" href="adres.do?crudAction=read" paramId="adres_id" paramProperty="adres_id" sortable="true"/>
                                    <display:column title="Type">
                                        <html:select name="adres" property="type_id" styleClass="input" disabled="true">
                                            <html:optionsCollection name="DDH" property="adrestypes" label="adres_type_b" value="adres_type_id" />
                                        </html:select>
                                    </display:column>
                                    <display:column  title="Contact" sortable="true" class="center">
                                        <logic:notEqual name="adres" property="contact_id" value="0">
                                            <html:link action="adrescontact?crudAction=read" paramId="contact_id" paramName="adres" paramProperty="contact_id">
                                                <bean:write name="adres" property="naam_contact_l"/>
                                            </html:link>
                                        </logic:notEqual>
                                    </display:column>
                                    <display:column property="straat" title="Straat" class="center"/>
                                    <display:column property="postcode" title="Postcode" class="center"/>
                                    <display:column property="gemeente" title="Gemeente" class="center"/>
                                </display:table>
                            </td>
                        </logic:notEmpty>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
