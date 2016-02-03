<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib prefix="custom" uri="/tags/custom"%>

<style>

.afgesloten {
    background-color: #EEE;
}

</style>


<c:choose>
    <c:when test="${dossier_type eq 'X'}">
        <table cellpadding="0" cellspacing="0">

            <logic:notEmpty name="besteksaldo">
                <tr>
                    <td align="center" class="nopadding">
                        <table cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    Bedrag vastgelegd
                                </td>
                                <td class="number">
                                    <bean:write name="besteksaldo" property="vastlegging" formatKey="number.format" filter="false"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Bedrag geraamd
                                </td>
                                <td class="number">
                                    <bean:write name="besteksaldo" property="geraamd" formatKey="number.format" filter="false"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Saldo geraamd
                                </td>
                                <td class="number">
                                    <bean:write name="besteksaldo" property="geraamd_saldo" formatKey="number.format" filter="false"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Openstaand gepland
                                </td>
                                <td class="number">
                                    <bean:write name="besteksaldo" property="openstaand_gepland" formatKey="number.format" filter="false"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td align="center" class="nopadding">
                        <table>
                            <tr>
                                <td>
                                    Bedrag vastgelegd
                                </td>
                                <td class="number">
                                    <bean:write name="besteksaldo" property="vastlegging" formatKey="number.format" filter="false"/>
                                </td>
                                <td class="number">
                                    ( initieel : <bean:write name="besteksaldo" property="initiele_vastlegging" formatKey="number.format" filter="false"/> )
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    Bedrag facturen
                                </td>
                                <td class="number">
                                    <bean:write name="besteksaldo" property="factuur" formatKey="number.format" filter="false"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Saldo
                                </td>
                                <td class="number">
                                    <bean:write name="besteksaldo" property="saldo" formatKey="number.format" filter="false"/>
                                </td>
                            </tr>
                        </table>
                    </td>

                </tr>
            </logic:notEmpty>

            <tr>
                <td class="nopadding" colspan="2">
                    <table class="planning">
                        <tr>
                            <td width="70px" >Offerte :</td>
                            <td width="500px" >
                                <select name="offerte_id"
                                        style="width: 100%;"
                                        onchange="window.open('/pad/s/bestek/' + ${bestekId} + '/offerte/' + this.options[this.selectedIndex].value + '/deelopdrachten','_top')"
                                    >
                                    <option value="-1"></option>
                                    <custom:options items="${bestekOffertesDD}" selectedValue="${offerte_id}" />
                                </select>
                            </td>
                            <td align="right">
                                <logic:present role="adminArt46,adminIVS">
                                    <img src='<html:rewrite page="/"/>/resources/images/add.gif'
                                         alt="Toevoegen deelopdracht"
                                         onclick="window.newDeelopdracht(${bestekId}, '${besteksaldo.bestek_nr}','${besteksaldo.raamcontract_jn}');" />
                                </logic:present>
                            </td>
                        </tr>
                        <logic:notEmpty name="bestekdeelopdrachtlijst" >
                            <tr>
                                <td colspan="3" >
                                    <display:table
                                            class="planning"
                                            id="deelopdracht"
                                            name="requestScope.bestekdeelopdrachtlijst"
                                            requestURI="/pad/s/bestek/${bestekId}/deelopdrachten/"
                                            decorator="be.ovam.art46.decorator.BestekDeelopdrachtLijstDecorator"
                                            >
                                        <display:column title="Dossiernaam" sortable="true" sortProperty="dossier_b_l">
                                            <html:link action="dossierdetailsArt46.do?selectedtab=Basis" paramId="id" paramName="deelopdracht" paramProperty="dossier_id">
                                                <bean:write name="deelopdracht" property="dossier_b_l"/>
                                            </html:link>
                                        </display:column>
                                        <display:column property="offerte_inzender_totaal" title="Offerte" sortable="true" class="center" />
                                        <display:column property="deelopdracht_nr" title="Nummer" sortable="true" class="center" />
                                        <display:column property="doss_hdr_id" title="Dossierhouder" sortable="true" class="center" />
                                        <display:column property="dossier_type" title="Type" sortable="true" class="center" />
                                        <display:column property="bedrag" title="Geraamd bedrag" sortable="true" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator"/>
                                        <display:column property="totaal_schuldvorderingen" title="Goedgekeurd bedrag SV" sortable="true" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator"/>
                                        <display:column property="totaal_facturen" title="SAP facturen" sortable="true" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator"/>
                                        <display:column property="goedkeuring_d" decorator="be.ovam.art46.decorator.DateDecorator" title="Goedkeuring" sortable="true" class="center"/>
                                        <display:column class="center" title="Voorstel" sortable="true">
                                            <c:if test="${not empty deelopdracht.voorstel_deelopdracht_id}">
                                                <a href="/pad/s/bestek/${bestekId}/voorstel/${deelopdracht.voorstel_deelopdracht_id}">
                                                    ${deelopdracht.voorstel_deelopdracht_nr}
                                                </a>
                                            </c:if>
                                        </display:column>
                                        <display:column property="afsluit_d" decorator="be.ovam.art46.decorator.DateDecorator" title="Afsluiting" sortable="true" class="center bold"/>
                                        <display:column class="center">
                                            <img src='<html:rewrite page="/"/>/resources/images/edit.gif'
                                                 alt="Aanpassen deelopdracht"
                                                 onclick="window.openDeelopdracht(${deelopdracht.deelopdracht_id}, ${bestekId}, '${besteksaldo.bestek_nr}', '${besteksaldo.raamcontract_jn}');" />
                                        </display:column>
                                        <display:column class="center">
                                            <img src='<html:rewrite page="/"/>/resources/images/copy.gif'
                                                 alt="Lijst schuldvorderingen"
                                                 onclick="popupWindow('<html:rewrite action="deelopdrachtlijstschuldvordering"/>?popup=yes&deelopdracht_id=<bean:write name="deelopdracht" property="deelopdracht_id" />', 'Schuldvorderingen')"/>
                                        </display:column>
                                        <display:column class="center">
                                            <logic:present role="adminArt46">
                                                <a href="/pad/s/bestek/${bestekId}/deelopdrachten/${deelopdracht.deelopdracht_id}/verwijder/">
                                                    <img src='<html:rewrite page="/"/>/resources/images/delete.gif' alt="Verwijderen deelopdracht" border="0"/>
                                                </a>
                                            </logic:present>
                                        </display:column>
                                        <display:setProperty name="basic.msg.empty_list_row" value=""/>

                                        <display:footer>
                                            <tr style="font-weight:bold;">
                                                <td colspan="5" align="right">
                                                        Totaal
                                                </td>
                                                <td align="right">
                                                    ${som_geraamd_bedrag}
                                                </td>
                                                <td align="right">
                                                    ${som_totaal_schuldvorderingen}
                                                </td>
                                                <td align="right">
                                                    ${som_totaal_facturen}
                                                </td>
                                                <td colspan="7"></td>
                                            </tr>
                                        </display:footer>


                                    </display:table>
                                </td>
                            </tr>
                        </logic:notEmpty>
                    </table>
                </td>
            </tr>

            <tr height="30px" ></tr>

            <tr>
                <td class="nopadding" colspan="2">
                    <table class="planning">
                        <tr>
                            <td>Openstaande planning</td>
                        </tr>
                        <logic:notEmpty name="openstaandePlanningslijnen" >
                            <tr>
                                <td>
                                    <display:table
                                            class="planning"
                                            id="planningItem"
                                            name="openstaandePlanningslijnen"
                                            requestURI="/pad/s/bestek/${bestekId}/deelopdrachten/"
                                            >
                                        <display:column property="dossier_nr" title="Dossier nr." sortable="true" class="center" />
                                        <display:column property="dossier_b_l" title="Dossier naam" sortable="true" />
                                        <display:column property="doss_hdr_id" title="Dossierhouder" sortable="true" class="center" />
                                        <display:column property="dossier_type" title="Type" sortable="true" class="center" />
                                        <display:column property="ig_bedrag" title="Geraamd bedrag" sortable="true" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator" />
                                        <display:column property="igb_d" title="Gepland datum" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" class="center"/>
                                        <display:column class="center">
                                            <c:if test="${doss_hdr_id == planningItem.doss_hdr_id}">
                                                <img src='<html:rewrite page="/"/>/resources/images/edit.gif'
                                                     alt="Toevoegen deelopdracht"
                                                     onclick="window.newDeelopdracht(${bestekId}, '${besteksaldo.bestek_nr}', '${besteksaldo.raamcontract_jn}', ${planningItem.dossier_id}, ${planningItem.lijn_id}, ${planningItem.ig_bedrag}, '${planningItem.igb_ds}'  );" />
                                            </c:if>
                                        </display:column>
                                        <display:footer>
                                            <tr style="font-weight:bold;">
                                                <td colspan="4" align="right">
                                                        Totaal
                                                </td>
                                                <td align="right">
                                                    ${som_openstaand_gepland}
                                                </td>
                                                <td colspan="2"></td>
                                            </tr>
                                        </display:footer>
                                    </display:table>
                                </td>
                            </tr>
                        </logic:notEmpty>
                    </table>
                </td>
            </tr>

        </table>
    <br/>
    </c:when>
    <c:otherwise>
        Enkel aan bestekken van dossiers van het type "Andere" kunnen er deelopdrachten worden toegevoegd.
    </c:otherwise>
</c:choose>

<div id="deelopdrachtDetailDiv"></div>

<tiles:insert page="../util/uploader.jsp"/>

<tiles:insert definition="laadJS" />

<logic:present role="adminArt46">
    <script type="text/javascript">
        _G_isAdminArt46 = true;
    </script>
</logic:present>


<script type="text/javascript">

    _G_.deelopdracht_id = ${empty deelopdracht_id ? "null" : deelopdracht_id} ;
    _G_.bestek_id = ${bestekId} ;

    laadBacking('budget/deelopdracht/deelopdrachtBacking');
</script>









