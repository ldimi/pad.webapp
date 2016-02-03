<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<style>
.dossierheader {
    background-color: #FFFF99;
}

.bestekheader {
    background-color: #FFFFDD;
}

</style>


<table>
    <tr>
        <td align="left">
            <html:form action="dossierbestekprojectzoekresult" method="get">
                <table cellpadding='0' cellspacing='0' width="1100px" >

                    <html:hidden property="resetCheckbox" value="1"/>
                    <tr valign='middle'>
                        <td valign='l'>
                            <html:select property="dossier_type" styleClass="input">
                                <html:option value="">Alle Dossiers</html:option>
                                <html:option value="A">Afval Dossiers</html:option>
                                <html:option value="X">Andere Dossiers</html:option>
                                <html:option value="B">Bodem Dossiers</html:option>
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="doss_hdr_id" styleClass="input">
                                <html:option value="">Alle dossierhouders</html:option>
                                <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="bestek_jaar" styleClass="input">
                                <html:option value="-1">Bestek</html:option>
                                <html:optionsCollection name="bestek_jaar" />
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="fase_id" styleClass="input">
                                <html:option value="-1">Alle fases</html:option>
                                <html:optionsCollection name="DDH" property="bestekBodemFase" />
                            </html:select>
                        </td>
                        <td>
                            <html:select property="programma_code" styleClass="input">
                                <html:option value="">Programma</html:option>
                                <html:optionsCollection name="DDH" property="programmaTypes" label="code" value="code" />
                            </html:select>
                        </td>
                        <td>Vastlegging van</td><td><html:text property="vastlegging_van_datum" size="10" maxlength="10" styleClass="input" onclick="scwShow(this,this);"/></td>
                        <td>Vastlegging tot</td><td><html:text property="vastlegging_tot_datum" size="10" maxlength="10" styleClass="input" onclick="scwShow(this,this);"/></td>
                        <td valign='middle'>
                            <html:select property="artikel_id" styleClass="input">
                                <html:option value="">Alle budgetaire artikels</html:option>
                                <html:optionsCollection name="DDH" property="budgetairartikel" label="artikel_b" value="artikel_b" />
                            </html:select>
                        </td>
                        <td valign='middle' align="right">
                            <html:checkbox property="inclusiefAfgesloten" styleClass="checkbox" value="1"  title="Inclusief afgesloten dossiers"/>
                        </td >
                        <td valign='middle' align="right">
                            <html:submit styleClass="inputbtn">
                                <bean:message key="lijst.opname.button.ophalen" />
                            </html:submit>
                        </td >
                    </tr>
                </table>
                <div style="width: 600px; margin-left: 500px; margin-top: 20px;">
                    <div style="float: left;">totaal bedrag: </div>
                    <div style="margin-left: 10px;float: left;">
                        <html:text property="totaal_bedrag" readonly="true" style="text-align:right" />
                    </div>
                </div>
            </html:form>
        </td>
    </tr>
    <tr>
        <td align="center">
            <html:form action="dossierbestekprojectcolumn">
                <html:hidden property="reset" value="1"/>
                <display:table class="lijst"  style="width:1800px;" id="project" name="sessionScope.zoeklijst" requestURI="/dossierbestekprojectview.do"  pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' export="true">
                    <display:caption>
                        <tr>
                            <logic:equal name="dossierbestekprojectform" property="c1" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c1" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c2" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c2" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c3" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c3" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c4" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c4" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c5" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c5" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c6" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c6" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c7" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c7" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c8" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c8" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c9" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c9" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c_programma_code" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c_programma_code" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c10" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c10" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c11" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c11" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c12" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c12" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c13" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c13" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c14" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c14" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c15" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c15" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                            <logic:equal name="dossierbestekprojectform" property="c16" scope="session" value="1">
                                <td align="center">
                                    <html:checkbox property="c15" styleClass="checkbox" value="1"/>
                                </td>
                            </logic:equal>
                        </tr>
                    </display:caption>
                    <display:setProperty name="export.decorated" value="true"/>
                    <logic:equal name="dossierbestekprojectform" property="c1" scope="session" value="1">
                        <display:column sortProperty="dossier_l" title="Dossier" sortable="true" headerClass="dossierheader" media="html">
                            <html:link action="dossierdetailsArt46.do?selectedtab=Basis" paramId="id" paramName="project" paramProperty="id"><bean:write name="project" property="dossier_l"/></html:link>
                        </display:column>
                        <display:column property="dossier_l" title="Dossier" headerClass="dossierheader" media="csv excel pdf xml"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c2" scope="session" value="1">
                        <display:column title="Type" property="dossier_type" class="center" headerClass="dossierheader"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c3" scope="session" value="1">
                        <display:column title="DH"  property="doss_hdr_id" class="center" headerClass="dossierheader" sortable="true"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c4" scope="session" value="1">
                        <display:column title="Facturen" property="totaal_ander_factuur" class="number" headerClass="dossierheader" sortable="true" decorator="be.ovam.art46.decorator.CurrencyDecorator" media="html"/>
                        <display:column title="Facturen" property="totaal_ander_factuur" class="number" headerClass="dossierheader" sortable="true" decorator="be.ovam.art46.decorator.CurrencyDecoratorExport" media="csv excel pdf xml"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c5" scope="session" value="1">
                        <display:column title="Bestek" property="bestek_nr" class="center" headerClass="bestekheader" sortable="true"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c6" scope="session" value="1">
                        <display:column property="bestek_type" title="Type" headerClass="dossierheader" media="html csv excel pdf xml" sortable="true"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c7" scope="session" value="1">
                        <display:column title="Omschrijving" property="omschrijving" class="center" headerClass="bestekheader" maxLength="20"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c8" scope="session" value="1">
                        <display:column title="Procedure" headerClass="bestekheader" media="html">
                            <html:select name="project" property="procedure_id" styleClass="input" disabled="true">
                                    <html:optionsCollection name="DDH" property="bestekBodemProcedure" />
                                </html:select>
                        </display:column>
                        <display:column property="bestek_procedure" title="Procedure" headerClass="dossierheader" media="csv excel pdf xml"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c9" scope="session" value="1">
                        <display:column property="bestek_fase" title="Fase" headerClass="dossierheader" media="html csv excel pdf xml" sortable="true"/>
                    </logic:equal>

                    <logic:equal name="dossierbestekprojectform" property="c_programma_code" scope="session" value="1">
                        <display:column title="Programma" property="programma_code" class="center" headerClass="dossierheader" sortable="true"  media="html csv excel pdf xml"/>
                    </logic:equal>

                    <logic:equal name="dossierbestekprojectform" property="c10" scope="session" value="1">
                        <display:column title="Opdrachthouder" property="opdrachthouder" class="center" headerClass="bestekheader" sortable="true"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c11" scope="session" value="1">
                        <display:column title="Fiche nr." property="project_id" class="center" media="html" sortable="true"/>
                        <display:column title="Fiche nr." property="project_id" class="center" decorator="be.ovam.art46.decorator.NumberToStringDecoratorExport" media="csv excel pdf xml"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c12" scope="session" value="1">
                        <display:column title="Vastlegging" property="vastlegging_id"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c13" scope="session" value="1">
                        <display:column title="Artikel" property="budgetair_artikel" class="center" />
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c14" scope="session" value="1">
                        <display:column title="Bedrag" property="bedrag" class="number" sortable="true" decorator="be.ovam.art46.decorator.CurrencyDecorator" media="html" />
                        <display:column title="Bedrag" property="bedrag" class="number" sortable="true" decorator="be.ovam.art46.decorator.CurrencyDecoratorExport" media="csv excel pdf xml"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c15" scope="session" value="1">
                        <display:column title="Datum" property="datum" class="center" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator"/>
                    </logic:equal>
                    <logic:equal name="dossierbestekprojectform" property="c16" scope="session" value="1">
                        <display:column title="Facturen" property="totaal_factuur" class="number" sortable="true" decorator="be.ovam.art46.decorator.CurrencyDecorator" media="html"/>
                        <display:column title="Facturen" property="totaal_factuur" class="number" sortable="true" decorator="be.ovam.art46.decorator.CurrencyDecoratorExport" media="csv excel pdf xml"/>
                    </logic:equal>
                    <display:footer>
                        <tr>
                            <td colspan="6">
                                <html:submit styleClass="inputbtn">Weergeven</html:submit>&nbsp;
                                <html:submit styleClass="inputbtn" onclick="setAction(this.form,'dossierbestekprojectcolumnall.do')">Alles Weergeven</html:submit>
                            </td>
                        </tr>
                    </display:footer>
                </display:table>
            </html:form>
        </td>
    </tr>
</table>
