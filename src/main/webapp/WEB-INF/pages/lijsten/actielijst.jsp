<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<table cellpadding='0' cellspacing='0' class="nopadding" >
    <tr>
        <td align="center">
            <html:form action="actiezoekresult" method="get" styleId="paramForm" >
                <table cellpadding='0' cellspacing='0' class="nopadding" style="border: 1px solid darkgray; background-color: #fdfdfd;">
                    <tr valign='middle'>
                        <td valign='middle'>
                            <html:select property="dossier_type" styleClass="input">
                                <html:option value="">Alle Dossiers</html:option>
                                <html:option value="A">Afval Dossiers</html:option>
                                <html:option value="X">Andere Dossiers</html:option>
                                <html:option value="B">Bodem Dossiers</html:option>
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="dossier_fase_id" styleClass="input">
                                <html:option value="-1">Alle Fases</html:option>
                                <html:optionsCollection name="DDH" property="dossierFasen_dd" label="fase_b_l" value="value" />
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="programma_code" multiple="true" size="3"  styleClass="input" >
                                <html:option value="-1">Alle programmas</html:option>
                                <html:optionsCollection name="DDH" property="programmaTypes" label="code" value="code" />
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="jaar_actie_d" styleClass="input">
                                <html:option value="-1">Gepland</html:option>
                                <html:optionsCollection name="vastlegging_jaar" />
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="jaar_realisatie_d" styleClass="input">
                                <html:option value="-1">Gerealiseerd</html:option>
                                <html:optionsCollection name="vastlegging_jaar" />
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="doss_hdr_id" styleClass="input">
                                <html:option value="">Alle dossierhouders</html:option>
                                <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="actie_type_id" styleClass="input">
                                <html:option value="">Alle acties</html:option>
                                <html:optionsCollection name="DDH" property="actiesTypesArt46" label="omschrijving" value="actie_type_id" />
                            </html:select>
                        </td>
                        <td>
                            <html:select property="lijst_id" styleClass="input">
                                <html:option value="-1">Alle lijsten</html:option>
                                <html:option value="0">&nbsp;</html:option>
                                <html:optionsCollection name="DDH" property="lijsten" label="lijst_b" value="lijst_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="1">
                            <html:checkbox property="actie_sub_type" styleClass="checkbox" value="1"/>Excl. Subacties
                        </td>
                        <td colspan="2">
                            <html:checkbox property="ingebrekestelling_s" styleClass="checkbox" value="1"/>Ingebrekestelling
                        </td>
                        <td colspan="1">
                            <html:checkbox property="ander_s" styleClass="checkbox" value="1"/>Andere&nbsp;
                        </td>
                        <td>
                            <html:select property="dossier_id" styleClass="input">
                                <html:option value="">Alle dossiers</html:option>
                                <logic:notEmpty name="dossiers" scope="request">
                                    <html:optionsCollection name="dossiers" label="dossier_b" value="dossier_id" />
                                </logic:notEmpty>
                            </html:select>
                        </td>
                        <td valign='middle'>
                            <html:select property="actie_sub_type_id" styleClass="input">
                                <html:option value="">Alle sub acties</html:option>
                                <html:optionsCollection name="DDH" property="actiesSubTypesArt46" label="omschrijving" value="actie_sub_type_id" />
                            </html:select>
                        </td>
                        <td valign='middle' align="right">
                            <html:submit styleClass="inputbtn">
                                <bean:message key="lijst.opname.button.ophalen" />
                            </html:submit>
                        </td >
                    </tr>
                </table>
            </html:form>
        </td>
    </tr>
    <tr>
        <td align="center">
            <logic:equal name="zoekaction" scope="session" value="/actielijstview.do">
                <logic:notEmpty name="zoeklijst" scope="session">
                    <display:table id="actie" name="sessionScope.zoeklijst" decorator="be.ovam.art46.decorator.ActieLijstDecorator" requestURI="/actielijstview.do" class="lijst width1000" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' export="true">
                        <display:column group="1" sortProperty="dossier_l" title="Titel" sortable="true" media="html">
                            <a href="/pad/s/dossier/<bean:write name="actie" property="dossier_id"/>/basis"/><bean:write name="actie" property="dossier_l"/></a>
                        </display:column>
                        <display:column property="dossier_l" media="pdf csv xml excel"/>
                        <display:column title="DH" property="doss_hdr_id" sortable="true" style="text-align: center;"/>
                        <display:column title="Fase" property="dossier_fase_b" sortable="true"  media="html" style="text-align: center;width: 50px;" />
                        <display:column property="fase_b" media="pdf csv xml excel"/>
                        <display:column title="Programma" property="programma_code" sortable="true"  media="html pdf csv xml excel" style="text-align: center;width: 50px;" />
                        <display:column title="Actie" property="actie_type_b" media="html pdf csv xml excel" style="text-align: center;width: 160px;"/>
                        <display:column title="Sub actie" property="actie_sub_type_b" media="html pdf csv xml excel"/>
                        <display:column title="Gepland van" property="actie_d"  sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                        <display:column title="Gepland tot" property="stop_d"  sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                        <display:column title="Gerealiseerd" property="realisatie_d"  sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                        <display:column property="rate" title="Tijd" style="text-align:right;" decorator="be.ovam.art46.decorator.BigDecimalDecorator" />
                        <display:column title="Commentaar" property="commentaar" maxLength="40" />
                        <display:setProperty name="export.decorated" value="true"/>
                    </display:table>
                </logic:notEmpty>
            </logic:equal>
        </td>
    </tr>
</table>


<tiles:insert definition="laadJS" />

<script type="text/javascript">
    laadBacking('lijsten/actielijstBacking');
</script>
