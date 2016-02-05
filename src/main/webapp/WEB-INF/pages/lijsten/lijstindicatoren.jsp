<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table>
    <tr>
        <td align="center">
            <table cellpadding='0' cellspacing='0'>
                <html:form action="lijstindicatoren">
                <tr valign='middle'>
                    <td>Lijst:</td>
                    <td valign='middle'>
                        <html:select property="lijst_id" styleClass="input">
                            <html:option value="">&nbsp;</html:option>
                            <html:optionsCollection name="DDH" property="lijsten" label="lijst_b" value="lijst_id" />
                        </html:select>
                    </td>
                    <td>Programma:</td>
                    <td valign='middle'>
                        <html:select property="programma_code" styleClass="input" >
                            <html:option value=""></html:option>
                            <html:optionsCollection name="DDH" property="programmaTypes" label="code" value="code" />
                        </html:select>
                    </td>
                    <td valign='middle'>
                        <html:submit styleClass="inputbtn">
                            <bean:message key="lijst.opname.button.ophalen" />
                        </html:submit>
                    </td >
                </tr>
                </html:form>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <logic:present name="aanpak_s_total" scope="session">
                <logic:equal name="zoekaction" scope="session" value="/lijstindicatorenview.do">
                    <display:table class="lijst width1000"
                            defaultsort="3" id="dossier"
                            name="sessionScope.zoeklijst"
                            requestURI="/lijstindicatorenview.do"
                            export="true"
                            pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                        <display:setProperty name="export.decorated" value="true"/>

                        <display:column title="Dossiernaam BB" property="dossier_b_boa" sortable="true" media="html csv excel pdf xml"  />

                        <display:column title="Dossiernr. BB" sortable="true" sortProperty="dossier_id_boa" class="center" media="html">
                            <a href="/pad/s/dossier/<bean:write name="dossier" property="id"/>/basis"/><bean:write name="dossier" property="dossier_id_boa"/></a>
                        </display:column>
                        <display:column title="Dossiernr. BB" property="dossier_id_boa" media="csv excel pdf xml" />

                        <display:column title="Dossiernaam IVS" property="dossier_l" class="center" sortable="true" media="html csv excel pdf xml"/>

                        <display:column title="Dossiernr. IVS" property="dossier_nr" class="center" sortable="true" media="html csv excel pdf xml"/>

                        <display:column title="Programma" property="programma_code" class="center" sortable="true" media="html csv excel pdf xml" />

                        <display:column title="Dossier opgestart" sortProperty="aanpak_s" class="center" sortable="true" media="html" >
                            <html:checkbox name="dossier" property="aanpak_s" value="1" disabled="true" styleClass="checkbox"/>
                        </display:column>
                        <display:column title="Dossier opgestart" property="aanpak_s" media="csv excel pdf xml" />

                        <display:column title="Integratie onderzocht" sortProperty="aanpak_onderzocht_s" class="center" sortable="true" media="html" >
                            <html:checkbox name="dossier" property="aanpak_onderzocht_s" value="1" disabled="true" styleClass="checkbox" />
                        </display:column>
                        <display:column title="Integratie onderzocht" property="aanpak_onderzocht_s" media="csv excel pdf xml" />


                        <display:setProperty name="basic.msg.empty_list_row" value="" />
                        <display:footer>
                            <tr style="font-weight:bold;">
                                <td colspan="5" align="right">
                                        Totaal aantal
                                </td>
                                <td align="center">
                                    <bean:write name="aanpak_s_total"/>
                                </td>
                                <td align="center">
                                    <bean:write name="aanpak_onderzocht_s_total"/>
                                </td>
                            </tr>
                            <tr style="font-weight:bold;">
                                <td colspan="5" align="right">
                                    <strong>

                                    </strong>
                                </td>
                                <td align="center">
                                    <bean:write name="aanpak_s_perc"/> %
                                </td>
                                <td align="center">
                                    <bean:write name="aanpak_onderzocht_s_perc"/> %
                                </td>
                            </tr>
                        </display:footer>
                    </display:table>
                </logic:equal>
            </logic:present>
        </td>
    </tr>
</table>