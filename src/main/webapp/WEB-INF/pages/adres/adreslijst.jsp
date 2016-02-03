<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table>
    <tr>
        <th>
            Adres zoeklijst
        </th>
    </tr>
    <tr>
        <td>
            <display:table id="adres" class="lijst" name="sessionScope.zoeklijst" requestURI="/adreslijstview.do" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                <logic:equal parameter="popup" value="yes">
                    <display:column property="naam" titleKey="adres.lijst.naam" sortable="true" />
                </logic:equal>
                <logic:notEqual parameter="popup" value="yes">
                    <display:column property="naam" titleKey="adres.lijst.naam" href="adres.do?crudAction=read" paramId="adres_id" paramProperty="adres_id"  sortable="true" />
                </logic:notEqual>
                <display:column property="voornaam" titleKey="adres.lijst.voornaam" sortable="true"/>
                <display:column property="straat" titleKey="adres.lijst.straat" sortable="true"/>
                <display:column property="postcode" titleKey="adres.lijst.postcode" class="center" sortable="true"/>
                <display:column property="gemeente" titleKey="adrescontact.lijst.gemeente" class="center" sortable="true"/>
                <display:column property="contact_naam" titleKey="adres.lijst.contact_naam" sortable="true"/>
                <display:column property="contact_functie" titleKey="adres.lijst.contact_functie" sortable="true"/>
                <display:column titleKey="adres.lijst.type" sortable="true">
                    <html:select name="adres" property="type_id" styleClass="input" disabled="true">
                        <html:optionsCollection name="DDH" property="adrestypes" label="adres_type_b" value="adres_type_id" />
                    </html:select>
                </display:column>
                <logic:notEmpty name="searchform" scope="session">
                    <logic:equal name="searchform" property="searchFlag" scope="session" value="briefadres">
                        <display:column>
                            <a href='javascript:pad_load("<html:rewrite action="briefloadadres"/>?adres_id=<bean:write name="adres" property="adres_id"/>&contact_id=<bean:write name="adres" property="contact_id"/>",top.opener)'>
                                <html:img border="0" page="/resources/images/add.gif" title="Toevoegen adres aan brief"/>
                            </a>
                        </display:column>
                    </logic:equal>
                </logic:notEmpty>
            </display:table>
        </td>
    </tr>
    <tr>
        <td>
            <html:link action="adreszoek?popup=yes">
                Opnieuw zoeken
            </html:link>
        </td>
    </tr>
    <tr>
        <td>
            <html:link action="adres?crudAction=view&popup=yes">
                Nieuw adres invoeren
            </html:link>
        </td>
    </tr>
</table>