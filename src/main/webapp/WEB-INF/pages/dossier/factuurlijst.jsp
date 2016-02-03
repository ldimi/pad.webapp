    <%@ page language="java" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<div class="planning">
        <strong>Facturen Open bestekken</strong>
        <table>
            <tr>
                <td>
                    <html:form action="dossierdetailsfacturensave">
                        <display:table id="factuur" name="requestScope.lijstsapfacturen" requestURI="/dossierdetails.do?selectedtab=Facturen" export="false" class="lijst width980">
                            <display:column property="project_id" title="Fiche Nr." />
                            <display:column property="factuur_id" titleKey="factuur.id" />
                            <display:column property="bedrag" titleKey="factuur.bedrag" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator"/>
                            <display:column property="factuur_d" titleKey="factuur.datum" class="center" decorator="be.ovam.art46.decorator.DateDecorator"/>
                            <display:column property="betaal_d" titleKey="factuur.betaaldatum" class="center" decorator="be.ovam.art46.decorator.DateDecorator" sortable="true"/>
                            <display:column property="bestek_nr" title="Besteknr." class="center" sortable="true"/>
                            <display:column property="omschrijving" title="Omschrijving" class="center" />
                            <display:column property="opdrachthouder" title="Opdrachthouder" class="center" sortable="true"/>
                            <logic:equal name="dossierart46form" property="dossier_type" scope="session" value="X">
                                <display:column>
                                    <bean:define id="project_id" name="factuur" property="project_id"/>
                                    <bean:define id="factuur_id" name="factuur" property="factuur_id"/>
                                    <html:select name="factuur" property="project_factuur_dossier_id" styleClass="input">
                                        <option value="<%= project_id + "_" + factuur_id + "_0" %>">-</option>
                                        <logic:iterate id="dossier" name="lijstsapfacturendossiers" scope="request">
                                            <bean:define id="dossier_id" name="dossier" property="id"/>
                                            <html:option value="<%= project_id + \"_\" + factuur_id + \"_\" + dossier_id%>" >
                                                <bean:write name="dossier" property="dossier_l"/>
                                            </html:option>
                                        </logic:iterate>
                                    </html:select>
                                </display:column>
                                <display:footer>
                                    <tr>
                                        <td colspan="10" align="right">
                                            <logic:present role="adminArt46">
                                                <html:submit styleClass="inputbtn">Opslaan</html:submit>
                                            </logic:present>
                                        <td>
                                    </tr>
                                </display:footer>
                                <display:setProperty name="basic.msg.empty_list_row" value=""/>
                            </logic:equal>
                        </display:table>
                    </html:form>
                </td>
            </tr>
        </table>
    </div>
    <br/>
    <div class="planning">
        <strong>Facturen Gesloten bestekken</strong>
        <table>
            <tr>
                <td>
                    <html:form action="dossierdetailsfacturensave">
                        <display:table id="factuur" name="requestScope.lijstsapfacturengesloten" requestURI="/dossierdetails.do?selectedtab=Facturen" export="false"  class="lijst width980">
                            <display:column property="project_id" titleKey="sap.project_id" />
                            <display:column property="factuur_id" titleKey="factuur.id" />
                            <display:column property="bedrag" titleKey="factuur.bedrag" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator"/>
                            <display:column property="saldo" titleKey="factuur.saldo" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator"/>
                            <display:column property="factuur_d" titleKey="factuur.datum" class="center" decorator="be.ovam.art46.decorator.DateDecorator"/>
                            <display:column property="betaal_d" titleKey="factuur.betaaldatum" class="center" decorator="be.ovam.art46.decorator.DateDecorator" sortable="true"/>
                            <display:column property="bestek_nr" title="Besteknr." class="center" sortable="true"/>
                            <display:column property="omschrijving" title="Omschrijving" class="center" />
                            <display:column property="opdrachthouder" title="Opdrachthouder" class="center" sortable="true"/>
                            <logic:equal name="dossierart46form" property="dossier_type" scope="session" value="X">
                                <display:column>
                                    <bean:define id="project_id" name="factuur" property="project_id"/>
                                    <bean:define id="factuur_id" name="factuur" property="factuur_id"/>
                                    <html:select name="factuur" property="project_factuur_dossier_id" styleClass="input">
                                        <option value="<%= project_id + "_" + factuur_id + "_0" %>">-</option>
                                        <logic:iterate id="dossier" name="lijstsapfacturendossiers" scope="request">
                                            <bean:define id="dossier_id" name="dossier" property="id"/>
                                            <html:option value="<%= project_id + \"_\" + factuur_id + \"_\" + dossier_id%>" >
                                                <bean:write name="dossier" property="dossier_l"/>
                                            </html:option>
                                        </logic:iterate>
                                    </html:select>
                                </display:column>
                                <display:footer>
                                    <tr>
                                        <td colspan="10" align="right">
                                            <logic:present role="adminArt46">
                                                <html:submit styleClass="inputbtn">Opslaan</html:submit>
                                            </logic:present>
                                        <td>
                                    </tr>
                                </display:footer>
                                <display:setProperty name="basic.msg.empty_list_row" value=""/>
                            </logic:equal>
                        </display:table>
                    </html:form>
                </td>
            </tr>
        </table>
    </div>