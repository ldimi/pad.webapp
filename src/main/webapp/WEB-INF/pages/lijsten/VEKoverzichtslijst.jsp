<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" uri="/tags/custom" %>

<h3>Opvolging vereffeningskrediet:</h3>


<div id="VekOverzichtsWrapper" class="hidden">


<form id=zoekForm>
    <table>

        <tr>
            <td>Jaar</td>
            <td><input type="input" data-bind="value:jaar"></td>

        </tr>

        <tr>
            <td>budgettaire artikels</td>
            <td><select
                    data-bind="options: $root.budgettairArtikels,
						       optionsText: function(item) {
						       return item.label
						        },
						       optionsValue: function(item) {
						       return item.artikel_b
						       },
						       value: budgettairArtikel">

            </select>
            </td>
        </tr>

        <tr>
            <td>dossierhouder</td>
            <td><select
                    data-bind="options: $root.dossierhouders,
                              optionsText: function(item) {
      						  return item.doss_hdr_b
      						  },
						      optionsValue: function(item) {
						      return item.doss_hdr_id
						       },
						      value: dossierhouder">
            </select></td>
        </tr>

        <tr>
            <td>programma</td>
            <td><select
                    data-bind="options: $root.programmas,
                              optionsText: function(item) {
      						  return item.programma_type_b
      						  },
						      optionsValue: function(item) {
						      return item.code
						       },
						      value: programma">
            </select></td>
        </tr>
    </table>


    <table>

        <tr>
            <td> Laatst gevalideerd tussen


            </td>
            <td>
                <div id="startvalidatie"
                     data-bind="jqxDateTimeInput: {value:$root.startValidatie,width: '250px', height: '25' }">
                </div>
            </td>
            <td>
                <div id="eindvalidatie"
                     data-bind="jqxDateTimeInput: {value:$root.eindValidatie,width: '250px', height: '25' }">
                </div>
            </td>


        </tr>

        <tr>

            <td colspan="1">
                <button id="ophalenBtn" class="inputbtn"
                        data-bind="click: function(data, event) { laad (null, data, event) }">ophalen
                </button>
            </td>

            <td>
                <a data-bind="attr: { href: $root.exportUrl()  }" target="_blank">
                    <img src="/pad/resources/images/calc.jpg" width=30 border="0" title="detail bekijken" alt="detail bekijken">
                    Exporteer naar rekenblad</a>      &nbsp;    &nbsp;
              </td><td>
                <a data-bind="attr: { href: $root.exportUrlIvs()  }" target="_blank">
                    <img src="/pad/resources/images/calc.jpg" width=30 border="0" title="detail bekijken" alt="detail bekijken">
                    Exporteer naar rekenblad (IVS)</a>

            <a data-bind="attr: { href: $root.esrcodeUrl()  }" target="_blank">
                <img src="/pad/resources/images/budget_image_small.png" width=30 border="0" title="detail bekijken" alt="detail bekijken">
                budgetbeheer</a>
            </td>


        </tr>



    </table>

</form>


<div id="samenvattingDiv" data-bind="if: resultaat() !=null">

    <table>
        <tr>
            <td>Totaal beschikbaar VEK (begroting)</td>
            <td>
                <span data-bind="text:resultaat().vekBegrotingTotaal"></span></td>

        </tr>

        <tr>
            <td>Totaal VEK voorzien</td>
            <td>
                <span data-bind="text:resultaat().vekVoorzienTotaal"></span></td>


        </tr>



    </table>

</div>


<div id="outeroverzichtGrid">
    <div id="overzichtGrid"></div>
</div>


<div id="editSpreidingDiv" data-bind="if: spreidingHolder() !=null">

    Spreiding <br/>

    <h3>
        <span data-bind="text: $root.responseMessage" style="color: red"></span>
    </h3>

    <table>


        <tr>
            <td>projectNr:</td>
            <td><span data-bind="text: spreidingHolder().projectId"></span></td>
        </tr>


        <tr>
            <td>Naam</td>
            <td><span data-bind="text: spreidingHolder().projectB"></span></td>
        </tr>

        <tr>
            <td>bestek</td>
            <td><span data-bind="text: spreidingHolder().bestekNr"></span></td>
        </tr>

        <tr>
            <td>dossier</td>
            <td><span data-bind="text: spreidingHolder().dossierNr"></span></td>
        </tr>


        <tr>
            <td>Schuldeiser</td>
            <td><span data-bind="text: spreidingHolder().schuldeiser"></span></td>
        </tr>


        <tr>
            <td>initieel</td>
            <td><span data-bind="commaDecimalFormatter: spreidingHolder().initieelBedrag"
                      class="double required"></span></td>
        </tr>


        <tr>
            <td>Totaal verwacht vereffeningskrediet</td>

            <td><span data-bind="commaDecimalFormatter: $root.spreidingTotaal"></span></td>
        </tr>


        <tr>
            <td>waarvan reeds gefactureerd</td>

            <td><span
                    data-bind="commaDecimalFormatter: $root.gefactureerdTotaal"></span></td>
        </tr>


    </table>

    <form id="wijzigSpreidingForm">
        <table class="displayTable" style="width: 300px;">


            <thead>
            <tr>
                <th>Jaar</th>
                <th>nieuwe waarde</th>
                <th>huidige waarde</th>
                <th>reeds gefactureerd</th>
            </tr>
            </thead>

            <tbody data-bind="foreach: spreidingHolder().spreiding">

            <td><input type="text" style="width: 50px;"
                       data-bind="value: jaar, attr:{id:'jaar' + $index(),name:'jaar' +
        $index()}"
                       class="required number" maxlength="4" size="10"/></td>
            </td>
            <td><input type="text" style="width: 100px;"
                       data-bind="commaDecimalFormatter :bedrag, attr:{id:'bedrag' +
        $index(),name:'bedrag' + $index()}"
                       class="double required"/></td>


            <td><input type="text" style="width: 100px;"
                       data-bind="commaDecimalFormatter :vorigBedrag, attr:{id:'vorigBedrag' +
        $index(),name:'value:vorigBedrag,' + $index()},enable: false"/>


            </td>


            <td><input type="text" style="width: 100px;"
                       data-bind="value:gefactureerd, attr:{id:'gefactureerd' +
        $index(),name:'value:gefactureerd,' + $index()},enable: false"/>


            </td>

            <td><a data-bind="click: $root.removeSpreidingItem"> <img
                    src="/pad/resources/images/delete.gif" border="0" title="Verwijderen">
            </a>

            </td>


            </tbody>


        </table>
    </form>


    Voeg lijn toe:
    <button id="toevoegenSpreidingItem" class="inputbtn"
            data-bind="click:addSpreidingItem">toevoegen
    </button>
    </td>


    <div id="afboekVoorstelDiv" data-bind="if: $root.verschilTotaal() !==null && $root.verschilTotaal() > 0">


        <p><input type="checkbox" data-bind="checked: spreidingHolder().vastleggingMagAfgeboektWorden"/></p> Gelieve
        door te geven aan de boekhouding dat het volgend bedrag nooit gebruikt zal worden
        <span data-bind="commaDecimalFormatter: $root.verschilTotaal"></span>


    </div>


    <div id="MeerVoorstelDiv" data-bind="if: $root.verschilTotaal() !==null && $root.verschilTotaal() < 0">


        <p><input type="checkbox" data-bind="checked: spreidingHolder().vastleggingMagAfgeboektWorden"/></p> Totaal van
        de spreiding gaat boven
        <span data-bind="commaDecimalFormatter: $root.verschilTotaalAbsoluut "></span> het vastleggingsbedrag.


    </div>

</div>

<!--
    <form action="/tasks/saveform" method="post">
    <textarea name="tasks" data-bind="value: ko.toJSON($root)" cols="100"
    rows="5 "></textarea>
    <br />
    <button type="submit">Save</button>
    </form> -->


</div>

<tiles:insert definition="laadJSzonderSlickgrid"/>


<script type="text/javascript">
    require(
            [ 'appMinimum' ],
            function (app) {

                require(
                        [ 'jquery', 'lijsten/VekOverzichtBacking' ],
                        function ($, overzicht) {

                            $(document)
                                    .ready(
                                    function () {

                                        window.dossierhouders = <custom:outJson object="dossierhouders" />
                                                window.budgettairArtikels = <custom:outJson object="budgettairArtikels" />
                                                        window.programmaTypes = <custom:outJson object="programmaTypes" />


                                                                overzicht.render();
                                    });

                        });

            });
</script>