define(
    ['knockout', 'jquery', 'ko.mapping', 'sammy', 'localisatieGrid',
        'jqxgrid', 'jqxgrid.storage', 'jqxgrid.selection',
        'jqxgrid.pager', 'jqxgrid.columnsresize', 'jqxgrid.aggregates',
        'jqxcalendar', 'jqx.globalize.nl-BE'],
    function (ko, $, komap, sammy, localisatieGrid) {
        var OverzichtPagina = {};

        OverzichtPagina.render = function () {
            console.log([$, ko, komap, sammy]);
            var self = this;

            var bedragrenderer = function (row, columnfield, value,
                                           defaulthtml, columnproperties) {
                var html = '<span style="margin: 4px; float: '
                    + columnproperties.cellsalign
                    + '; ">'
                    + $.jqx.dataFormat.formatnumber(value,
                        'd2', localisatieGrid.localisatie)
                    + '</span>';
                return html;

            };
            function getColums() {

                var colums = [
                    {
                        text: "Postnr",
                        datafield: "postnr",
                        width: 65,
                        frozen: true,
                        pinned: true,
                        cellsalign: 'right'

                    }, {
                        text: "Taak",
                        datafield: "taak",
                        width: 250,
                        frozen: true,
                        pinned: true
                    }];
                if (window.deelopdrachtId!=null && window.deelopdrachtId.value != 0) {
                    colums.push({
                        text: "Voorstel totaal",
                        datafield: "voorstelDeelopdrachtTotaal",
                        width: 65,
                        frozen: true,
                        pinned: true,
                        cellsalign: 'right',
                        cellsrenderer: bedragrenderer
                    });
                }else{
                    colums.push({
                        text: "Offerte totaal",
                        datafield: "regelTotaalOfferte",
                        width: 65,
                        frozen: true,
                        pinned: true,
                        cellsalign: 'right',
                        cellsrenderer: bedragrenderer
                    });
                }
                colums.push({
                    text: "Verbruik",
                    datafield: "totaalViewRegel",
                    width: 65,
                    frozen: true,
                    pinned: true,
                    cellsalign: 'right',
                    cellsrenderer: bedragrenderer
                });

                var i = 0;
                while (i < window.regels[1].schuldvorderingRegelDtoList.length) {
                    colums.push({
                        text: window.regels[1].schuldvorderingRegelDtoList[i].schuldvorderingNr,
                        datafield: 'bedrag' + i,
                        cellsalign: 'right',
                        width: 65,
                        cellsrenderer: bedragrenderer
                    });
                    i++;
                }

                return colums;
            }

            self.initGrids = function () {

                $('#overzichtGrid').jqxGrid('destroy');
                $('#outeroverzichtGrid').append(
                    ' <div id="overzichtGrid" ></div>');

                $("#overzichtGrid").bind(
                    'bindingcomplete',
                    function () {
                        // $("#overzichtGrid").jqxGrid('autoresizecolumns');
                        $("#overzichtGrid").jqxGrid(
                            'localizestrings',
                            localisatieGrid.localisatie);

                    });


                function getDatafields() {
                    var dataFields = [
                        {name: 'postnr', type: 'string'},
                        {name: 'taak', type: 'string'},
                        {name: 'regelTotaalOfferte', type: 'string'},
                        {name: 'voorstelDeelopdrachtTotaal', type: 'string'},
                        {name: 'totaalViewRegel', type: 'string'}
                    ];
                    var i = 0;
                    while (i < window.regels[1].schuldvorderingRegelDtoList.length) {
                        dataFields.push({
                            name: 'bedrag' + i,
                            map: 'schuldvorderingRegelDtoList>' + i + '>regelTotaal',
                            type: 'string'
                        });
                        i++;
                    }
                    dataFields.push();
                    return dataFields;

                }

                var sourceOverzicht = {
                    localdata: window.regels,
                    datatype: 'array',
                    datafields: getDatafields()

                };


                var dataAdapterOverzicht = new $.jqx.dataAdapter(
                    sourceOverzicht);

                var grid = $("#overzichtGrid")
                    .jqxGrid(
                    {
                        autoheight: true,
                        width: "1000",
                        theme: 'ui-sunny',
                        columns: getColums(),
                        source: dataAdapterOverzicht,
                        editable: false,
                        pageable: false,
                        showaggregates: true,
                        showstatusbar: true,
                        statusbarheight: 30,
                        selectionmode: 'none',
                        enablebrowserselection: true,
                        columnsresize: false,
                        sortable: false
                    });
            }
            self.initGrids();
            $('#overzichtsWrapper').removeClass('hidden');
        }

        /*        function onReady() {
         alert('test ik was hier')
         $('#VekOverzichtsWrapper').removeClass('hidden');
         }*/

        return OverzichtPagina;
    });