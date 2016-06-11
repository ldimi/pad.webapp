<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<table width="100%">
    <tr>
        <th>
            Overzicht vastleggingen
        </th>
    </tr>
</table>

<div id="vastleggingsAanvragenGrid" title="Vastleggings aanvragen" style="width:1020px; height:300px">

</div>

<div id="detailVastleggingDialog" class="hidden" title="Aanvraag vastlegging">
    <form id="detailVastleggingForm" autocomplete="off">
        <table>
            <tr>
                <td style="vertical-align: top">
                    <table>
                        <tr>
                            <td>Besteknummer</td>
                            <td align='right'>
                                <input type="text" name="bestek_nr"  disabled="disabled"/>
                                <input type="hidden" name="id" />
                                <input type="hidden" name="bestekid" />
                                <input type="hidden" name="aanvraagid" />
                                <input type="hidden" name="geweigerd" />
                            </td>
                        </tr>
                        <tr>
                            <td>Type</td>
                            <td align='right'>
                                <input type="text" name="type_b" disabled="disabled"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Procedure</td>
                            <td align='right'>
                                <input type="text" name="procedure_b" disabled="disabled"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Fase</td>
                            <td align='right'>
                                <input type="text" name="fase_b" disabled="disabled"/>
                            </td>
                        </tr>

                        <tr>
                            <td align='left' colspan="2">
                                <input type="checkbox" value="1" name="raamcontract_s" disabled="disabled"/>  &nbsp;Raamcontract
                            </td>
                        </tr>
                        <tr>
                            <td align='left' colspan="2">
                                <input type="checkbox" value="1" name="verlenging_s" disabled="disabled"/>  &nbsp;Verlenging
                            </td>
                        </tr>
                        <tr>
                            <td>SAP wbs nummer </td>
                            <td align='left' colspan="3">
                                <input type="text" name="wbsBestek" disabled="disabled"/>
                            </td>
                        </tr>
                        <tr>
                            <td align='left' colspan="2">
                                Omschrijving: <br/>
                                <textarea name="omschrijving" maxlength="100"  rows="2" cols="45" disabled="disabled">
                                </textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>Plannings Item</td>
                            <td align='right'>
                                <select name="planningsitem" class="input"><option value=""></option></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Budgetair Artikel</td>
                            <td align='right'>
                                <select name="budgetairartikel" class="input">
                                    <option value=""></option>
                                    <c:forEach var="item" items="${budgetairartikels}" >
                                        <option value="${item.value}" > ${item.label}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Kostenplaats</td>
                            <td align='right'>
                                <select name="kostenplaats" class="input">
                                    <option value=""></option>
                                    <c:forEach var="item" items="${kostenplaatsen}" >
                                        <option value="${item.value}" > ${item.label}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                </table>
                </td>
                <td style="vertical-align: top">
                    <table>
                        <tr>
                            <td colspan="2"><b>Datum goedkeuring</b></td>
                        </tr>
                        <tr>
                            <td>Inspectie van Financi&euml;n</td>
                            <td align='right'>
                                <input type="text" name="inspectievanfinancien"  />
                            </td>
                        </tr>
                        <tr>
                            <td>Voogdijminister</td>
                            <td align='right'>
                                <input type="text" name="voogdijminister"  />
                            </td>
                        </tr>
                        <tr>
                            <td>Minister van begroting</td>
                            <td align='right'>
                                <input type="text" name="ministervanbegroting"  />
                            </td>
                        </tr>
                        <tr>
                            <td>Vlaamse regering</td>
                            <td align='right'>
                                <input type="text" name="vlaamseregering"  />
                            </td>
                        </tr>
                        <tr>
                            <td>Vast te leggen bedrag Incl. BTW</td>
                            <td align='right'>
                                <input type="text" name="vast_bedrag"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top">Spreiding betalingen:Incl. BTW </td>
                            <td ><div id="avastleggingsBetalingsGrid" title="Spreiding betalingen" style="width:200px; height:200px"><br/>
                            </div></td>
                        </tr>
                    </table>
                </td>
                <td style="vertical-align: top">
                    <table>
                        <tr>
                            <td><b>Opdrachthouder</b></td>
                            <td align='right'>
                                <select name="opdrachthouder_id" class="input"></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Straat</td>
                            <td align='right'>
                                <input type="text" name="straat" readonly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Gemeente</td>
                            <td align='right'>
                                <input type="text" name="gemeente" readonly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Gunningsverslag</td>
                            <td align='right'>
                                <select name="gunningsverslag" class="input" style="width: 180px;"><option value=""></option></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Gunningsbeslissing</td>
                            <td align='right'>
                                <select name="gunningsbeslissing" class="input" style="width: 180px;"><option value=""></option></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Overeenkomst</td>
                            <td align='right'>
                                <select name="overeenkomst" class="input" style="width: 180px;"><option value=""></option></select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                Optioneel:<br/>
                                <div id="optioneleBestandenGrid" title="Optionele bestanden" style="width:305px; height:180px"></div>
                            </td>
                        </tr>
                    </table>
                </td>
                </tr>
            <tr>
                <td>
                    <button id="bewaarBtn">Bewaar</button>
                    <button id="verzendBtn">Verzend</button>
                    <button id="annuleerBtn">Annuleer</button>
                </td>
                <td colspan="2">
                    <div id="bedragTeKlein" hidden="hidden" style="color: red"><b>Totaal spreiding betalingen is kleiner dan het vast te leggen bedrag</b></div>
                    <div id="bedragTeGroot" hidden="hidden" style="color: red"><b>Totaal spreiding betalingen is groter dan het vast te leggen bedrag</b></div>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="vastleggingsBetalingsDialog" class="hidden" title="Aanvraag vastlegging betaling">
    <form id="vastleggingsBetalingsForm" autocomplete="off">
        <table>
            <tr>
                <td>Jaar.</td>
                <td>
                    <select name="jaar" class="input">
                    <option value=""></option>
                        <c:forEach begin="2014" end="2114" varStatus="jaar">
                            <option value="${jaar.index}">${jaar.index}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Bedrag</td>
                <td><input type="text" name="bedrag"  />
                    <input type="hidden" name="aanvraagid"/>
                </td>
            </tr>
        </table>
    </form>
    <button id="bewaarBetalingBtn">Bewaar</button>
    <button id="annuleerBetalingBtn">Annuleer</button>
</div>


<div id="optioneleBestandenDialog" class="hidden" title="Optionele bestanden">
    <form id="optioneleBestandenForm" autocomplete="off">
        <table>
            <tr>
                <td>Bestand</td>
                <td>
                    <select name="bestand" class="input">
                        <option value=""></option>
                    </select>
                    <input type="hidden" name="aanvraagid"/>
                </td>
            </tr>
        </table>
    </form>
    <button id="bewaarBestandBtn">Bewaar</button>
    <button id="annuleerBestandBtn">Annuleer</button>
</div>

<tiles:insertDefinition name="laadJS" />

<script type="text/javascript">
    bestek_id = ${bestekDO.bestek_id};
</script>

<script type="text/javascript">
    laadBacking('lijsten/bestekvastleggingenlijstBacking');
</script>