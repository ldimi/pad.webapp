<%@ page language="java" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<logic:notEmpty name="dossierart46form" property="dossier_nr" scope="session">
    <br/>
    <div class="planning">
        <strong>Open bestekken </strong>
        <table>
            <tr>
                <td>
                    <display:table id="bestek" defaultsort="1" defaultorder="descending" name="requestScope.dossierdetailsopenbesteklijst" requestURI="/dossierdetails.do" class="lijst width980">
                        <display:column  title="Besteknr" sortProperty="bestek_nr">
                            <logic:equal name="bestek" property="deelopdracht" value="0">
                                <a href='/pad/s/bestek/<bean:write name="bestek" property="bestek_id"/>/basisgegevens/' >
                                    <bean:write name="bestek" property="bestek_nr"/>
                                </a>
                            </logic:equal>
                            <logic:equal name="bestek" property="deelopdracht" value="1">
                                <bean:write name="bestek" property="bestek_nr"/>
                            </logic:equal>
                            </display:column>
                        <display:column property="omschrijving" title="Omschrijving" />
                        <display:column title="Opdrachthouder" property="naam" />
                        <display:column title="Bestekhouder" property="bestek_hdr_id" />
                        <display:column title="Fase">
                            <html:select name="bestek" property="fase_id" styleClass="input" disabled="true">
                                <html:optionsCollection name="DDH" property="bestekBodemFase" />
                            </html:select>
                        </display:column>
                        <display:column title="Type">
                            <html:select name="bestek" property="type_id" styleClass="input" disabled="true">
                                <html:optionsCollection name="DDH" property="bestekBodemType" />
                            </html:select>
                        </display:column>
                        <display:column title="SAP WBS" style="width:100px">
                            <bean:write name="bestek" property="wbs_nr"/>
                        </display:column>
                        <display:column>
                            <logic:equal name="bestek" property="deelopdracht" value="0">
                                <logic:present role="adminArt46">
                                    <a href='s/<bean:write name="dossierart46form" property="dossier_type" scope="session" />/bestek/<bean:write name="bestek" property="bestek_id"/>/delete'>
                                        <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen bestek"/>
                                    </a>
                                </logic:present>
                            </logic:equal>
                            <logic:equal name="bestek" property="deelopdracht" value="1">
                                <a href='/pad/s/bestek/<bean:write name="bestek" property="bestek_id"/>/deelopdrachten/' >
                                    <html:img border="0" page="/resources/images/copy.gif" title="Details deelopdracht"/>
                                </a>
                            </logic:equal>
                        </display:column>
                        <display:setProperty name="basic.msg.empty_list_row" value=""/>
                    </display:table>
                </td>
                <td align="left" valign="top">
                    <logic:present role="adminArt46,adminIVS,adminBOA">
                        <a href="/pad/s/dossier/${dossierart46form.id}/bestek/aanmaken">
                            <html:img border="0" page="/resources/images/add.gif" title="Toevoegen Bestek"/>
                        </a>
                    </logic:present>
                </td>
            </tr>
        </table>
    </div>
    <br/>
    <div class="planning">
        <strong>Afgesloten bestekken</strong>
        <table>
            <tr>
                <td>
                    <display:table id="bestek" defaultsort="1" defaultorder="descending" name="requestScope.dossierdetailsafgeslotenbesteklijst" requestURI="/dossierdetails.do" class="lijst width980">
                        <display:column  title="Besteknr" sortProperty="bestek_nr">
                            <logic:equal name="bestek" property="deelopdracht" value="0">
                                <a href='/pad/s/bestek/<bean:write name="bestek" property="bestek_id"/>/basisgegevens/' >
                                    <bean:write name="bestek" property="bestek_nr"/>
                                </a>
                            </logic:equal>
                            <logic:equal name="bestek" property="deelopdracht" value="1">
                                <bean:write name="bestek" property="bestek_nr"/>
                            </logic:equal>
                            </display:column>
                        <display:column property="omschrijving" title="Omschrijving" />
                        <display:column title="Opdrachthouder" property="naam" />
                        <display:column title="Bestekhouder" property="bestek_hdr_id" />
                        <display:column title="Fase">
                            <html:select name="bestek" property="fase_id" styleClass="input" disabled="true">
                                <html:optionsCollection name="DDH" property="bestekBodemFase" />
                            </html:select>
                        </display:column>
                        <display:column title="Type">
                            <html:select name="bestek" property="type_id" styleClass="input" disabled="true">
                                <html:optionsCollection name="DDH" property="bestekBodemType" />
                            </html:select>
                        </display:column>
                        <display:column title="SAP WBS" style="width:100px">
                            <bean:write name="bestek" property="wbs_nr"/>
                        </display:column>
                        <display:column>
                            <logic:equal name="bestek" property="deelopdracht" value="0">
                                <logic:present role="adminArt46">
                                    <a href='s/<bean:write name="dossierart46form" property="dossier_type" scope="session" />/bestek/<bean:write name="bestek" property="bestek_id"/>/delete'>
                                        <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen bestek"/>
                                    </a>
                                </logic:present>
                            </logic:equal>
                            <logic:equal name="bestek" property="deelopdracht" value="1">
                                <a href='/pad/s/bestek/<bean:write name="bestek" property="bestek_id"/>/deelopdrachten/' >
                                    <html:img border="0" page="/resources/images/copy.gif" title="Details deelopdracht"/>
                                </a>
                            </logic:equal>
                        </display:column>
                        <display:setProperty name="basic.msg.empty_list_row" value=""/>
                    </display:table>
                </td>
            </tr>
        </table>
    </div>
</logic:notEmpty>
<logic:empty name="dossierart46form" property="dossier_nr" scope="session">
    Enkel aan een IVS dossier kan er een bestek gekoppeld worden.
</logic:empty>