define(
        [ 'knockout', 'jquery', 'ko.mapping', 'sammy', 'localisatieGrid',
                'jqxgrid', 'jqxgrid.storage', 'jqxgrid.selection',
                'jqxgrid.pager', 'jqxgrid.columnsresize', 'jqxgrid.aggregates',
                'jqxcalendar', 'jqx.globalize.nl-BE' ],
        function(ko, $, komap, sammy, localisatieGrid) {

            // console.log([$, ko, komap,sammy]);

            'use strict';

            // commons maar zonder app te gebruiken waardoor slickgrid en zo
            // meekomen ...

            // einde commons

            var VekoverzichtPagina = {};

            VekoverzichtPagina.render = function() {
                console.log([ $, ko, komap, sammy ]);

                var that = this;

                function ViewModel() {

                    var self = this;

                    var d = new Date();

                    self.jaar = ko.observable(d.getFullYear());
                    self.dossierhouder = ko.observable();
                    self.budgettairArtikel = ko.observable();
                    self.startValidatie = ko.observable();
                    self.eindValidatie = ko.observable();
                    self.programma = ko.observable();

                    self.resultaat = ko.observable();
                    self.spreidingHolder = ko.observable();

                    self.responseMessage = ko.observable();

                    self.dossierhouders = ko
                            .observableArray(window.dossierhouders);
                    var alleDossierhouders = {
                        doss_hdr_b : "Alle dossierhouders",
                        doss_hdr_id : ""
                    };
                    self.dossierhouders.unshift(alleDossierhouders);

                    self.budgettairArtikels = ko
                            .observableArray(window.budgettairArtikels);

                    // var alleBudgettairArtikels = {
                    // "value" : null,
                    // "label" : "Alle artikels",
                    // "artikel_b" : "",
                    // "artikel_id" : null
                    // };
                    // self.budgettairArtikels.unshift(alleBudgettairArtikels);

                    self.programmas = ko.observableArray(window.programmaTypes);
                    var alleProgrammas = {
                        code : null,
                        programma_type_b : "Alle programma's"
                    };
                    self.programmas.unshift(alleProgrammas);

                    self.formatDate = function(date) {

                        var antwoord = null;

                        if (date != null) {
                            antwoord = $.datepicker
                                    .formatDate('dd-mm-yy', date);
                        }

                        return antwoord;

                    };

                    self.getRequestData = function() {

                        var requestdata = {
                            jaar : self.jaar(),
                            doss_hdr_id : self.dossierhouder(),
                            budgettairArtikel : self.budgettairArtikel(),
                            programma : self.programma(),
                            startValidatie : self.formatDate(self
                                    .startValidatie()),
                            eindValidatie : self.formatDate(self
                                    .eindValidatie())
                        };

                        return requestdata;

                    }

                    self.exportUrl = function() {

                        var requestdata = self.getRequestData();

                        return "/pad/s/VEKoverzichtslijst/getExport/?"
                                + $.param(requestdata);

                    }

                    self.exportUrlIvs = function() {

                        var requestdata = self.getRequestData();
                        requestdata.ivs=true;

                        return "/pad/s/VEKoverzichtslijst/getExport/?"
                            + $.param(requestdata);

                    }

                    self.esrcodeUrl = function() {

                        var requestdata = self.getRequestData();
                        requestdata.ivs=true;

                        return "http://toepassing.ovam.be/budget/readEsrcode/open.do#openEsrcode/"+requestdata.jaar+"/"+requestdata.budgettairArtikel ;


                    }

                    self.laad = function(state) {

                        var requestdata = self.getRequestData();

                        var url = "/pad/s/VEKoverzichtslijst/getAll/";

                        $
                                .ajax(
                                        url,
                                        {
                                            data : requestdata,
                                            type : "get",
                                            contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                                            dataType : "json",
                                            success : function(data) {

                                                self.resultaat(data);
                                                self.initGrids();

                                                if (state != null) {
                                                    $("#overzichtGrid")
                                                            .jqxGrid(
                                                                    'loadstate',
                                                                    state);
                                                }

                                            },
                                            error : function(xhr, text, error) {

                                                alert(text);

                                            }

                                        });

                    };

                    self.sortSpreiding = function() {

                        self.spreidingHolder().spreiding.sort(function(left,
                                right) {
                            var jaarleft = left.jaar();
                            var jaarright = right.jaar();

                            return jaarleft == jaarright ? 0
                                    : (jaarleft < jaarright ? -1 : 1)
                        })

                    }

                    self.spreidingTotaal = function() {

                        var totaal = 0;

                        if (self.spreidingHolder() != null) {

                            for ( var i = 0; i < self.spreidingHolder()
                                    .spreiding().length; i++) {

                                var spreiding = self.spreidingHolder()
                                        .spreiding()[i];
                                totaal = totaal
                                        + parseFloat(spreiding.bedrag());

                            }
                        }

                        var result = totaal.toFixed(2);

                        return result;

                    };

                    self.isNumber = function(n) {
                        return !isNaN(parseFloat(n)) && isFinite(n);
                    }



                    self.verschilTotaal = function() {

                        var totaal = 0;
                        var antwoord = null

                        if (self.spreidingHolder() != null) {

                            for ( var i = 0; i < self.spreidingHolder()
                                    .spreiding().length; i++) {

                                var spreiding = self.spreidingHolder()
                                        .spreiding()[i];
                                totaal = totaal
                                        + parseFloat(spreiding.bedrag());

                            }

                            if (self.isNumber(totaal)) {

                                antwoord = (self.spreidingHolder()
                                        .initieelBedrag() - totaal).toFixed(2);

                            }
                        }

                        return antwoord;

                    };

                    self.verschilTotaalAbsoluut = function(){

                        return  Math.abs(self.verschilTotaal());
                    }

                    self.gefactureerdTotaal = function() {

                        var totaal = 0;

                        if (self.spreidingHolder() != null) {

                            for ( var i = 0; i < self.spreidingHolder()
                                    .spreiding().length; i++) {

                                var spreiding = self.spreidingHolder()
                                        .spreiding()[i];
                                totaal = totaal
                                        + parseFloat(spreiding.gefactureerd());

                            }
                        }

                        return totaal.toFixed(2);

                    };

                    self.laadDetail = function(twaalfnr) {

                        var url = "/pad/s/VEKoverzichtslijst/getSpreiding/";

                        self.responseMessage(null);
                        self.spreidingHolder(null);

                        $
                                .ajax(
                                        url,
                                        {
                                            data : {
                                                twaalfnr : twaalfnr
                                            },
                                            type : "get",
                                            contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                                            dataType : "json",
                                            success : function(data) {

                                                // for (var j = 0; j <
                                                // data.spreiding.length; j++) {
                                                //
                                                // var spreiding =
                                                // data.spreiding[j];
                                                //
                                                //
                                                // spreiding.vorigBedrag =
                                                // spreiding.bedrag;
                                                //
                                                //
                                                // }

                                                var project = komap
                                                        .fromJS(data);

                                                // self
                                                // .addComputedFunction(project)

                                                // self.spreidingArray
                                                // (obsArraySpreiding);
                                                self.spreidingHolder(project);
                                                self.sortSpreiding();
                                                $("#editSpreidingDiv").dialog(
                                                        "open");

                                            },
                                            error : function(xhr, text, error) {

                                                alert(text);

                                            }

                                        });

                    };

                    self.addSpreidingItem = function() {

                        var d = new Date();

                        self.sortSpreiding();

                        var item = {};

                        item.id = null;

                        item.jaar = d.getFullYear();

                        if (self.spreidingHolder().spreiding().length > 0) {

                            var laatste = self.spreidingHolder().spreiding().length - 1;

                            var laatsteSpreiding = self.spreidingHolder()
                                    .spreiding()[laatste];
                            item.jaar = laatsteSpreiding.jaar() + 1;

                        }

                        item.bedrag = 0;
                        item.vorigBedrag = 0;
                        item.gefactureerd = 0;

                        var koItem = komap.fromJS(item);

                        self.spreidingHolder().spreiding.push(koItem);
                        self.sortSpreiding();

                    };

                    self.removeSpreidingItem = function(item) {
                        self.spreidingHolder().spreiding.remove(item);
                        self.sortSpreiding();

                    };

                    self.save = function() {

                        self.responseMessage(null);

                         var validatorke =
                          $('#wijzigSpreidingForm').validate();

                          var valied = validatorke.form();


                        if (valied) {

                            var data = ko.toJS(self.spreidingHolder);

                            if (self.verschilTotaal() != null
                                    && self.verschilTotaal() > 0) {

                                data.voorgesteldAfTeBoekenBedrag = self
                                        .verschilTotaal();
                            }

                            else {
                                data.voorgesteldAfTeBoekenBedrag = 0;
                            }

                            var url = "/pad/s/VEKoverzichtslijst/updateSpreiding/";
                            /*
                             * if (afsluiten) {
                             *
                             * url =
                             * "/budget/budgetreservering/saveEnAfsluiten.do"; }
                             */

                            $
                                    .ajax(
                                            url,
                                            {
                                                data : ko.toJSON(data),
                                                type : "post",
                                                contentType : "application/json",
                                                success : function(data) {

                                                    if (data.success) {

                                                        var state = $(
                                                                "#overzichtGrid")
                                                                .jqxGrid(
                                                                        'savestate');

                                                        self.laad(state);

                                                        $("#editSpreidingDiv")
                                                                .dialog("close");

                                                    } else {

                                                        self
                                                                .responseMessage(data.errorMsg);
                                                        var project = komap
                                                                .fromJS(data.result);

                                                        self
                                                                .spreidingHolder(project);
                                                        self.sortSpreiding();

                                                    }

                                                },
                                                error : function(xhr, text,
                                                        error) {

                                                    alert(text + error);

                                                }
                                            });

                        } else {

                            // $.notify({
                            // text: "er zijn fouten gevonden, gelieve deze
                            // eerst te verbeteren" });

                            self
                                    .responseMessage("er zijn fouten gevonden, gelieve deze eerst te verbeteren");

                        }

                    };

                    self.initGrids = function() {

                        $('#overzichtGrid').jqxGrid('destroy');
                        $('#outeroverzichtGrid').append(
                                ' <div id="overzichtGrid" ></div>');

                        $("#overzichtGrid").bind(
                                'bindingcomplete',
                                function() {
                                    // $("#overzichtGrid").jqxGrid('autoresizecolumns');
                                    $("#overzichtGrid").jqxGrid(
                                            'localizestrings',
                                            localisatieGrid.localisatie);

                                });



                        var dataFields =  [
                            {name: 'project_id', type: 'string'},
                            {name: 'vastlegging_id', type: 'string'},
                            {name: 'bestek_nr', type: 'string' }
                            ,{name: 'dossier_id', type: 'string' }
                            ,{name: 'dossier_b', type: 'string' }

                            ,{name: 'gemeente_b', type: 'string' }
                            ,{name: 'datum', type: 'date', format: 'dd-MM-yyyy' }
                            ,{name: 'afsluit_d',type: 'date', format: 'dd-MM-yyyy' }

                            ,{name: 'spreiding_VALIDATIE_TS',type: 'date', format: 'dd-MM-yyyy'}
                            ,{name: 'vekVoorzien', type: 'double' }
                            ,{name: 'gefactureerd', type: 'double' }



                        ];


                        var sourceOverzicht = {
                            // knockout ... bij gebruik () ... krijg je de niet
                            // observable (dus niet de observable functie),
                            // indien geen () wel de observable functie,
                            // hier hebben we juist voor spreiding de functie
                            // nodig zodat er databinding kan gebeuren
                            // bij gebruik (waarde) ja je een set doen van de
                            // observable (... gebeurt bijna steeds achter de
                            // schermen)
                            localdata : self.resultaat().vastleggingOrdonanceringLijst,
                            datatype : 'array',
                            datafields:dataFields

                        };

                        var bedragrenderer = function(row, columnfield, value,
                                defaulthtml, columnproperties) {

                            var html = '<span style="margin: 4px; float: '
                                    + columnproperties.cellsalign
                                    + '; ">'
                                    + $.jqx.dataFormat.formatnumber(value,
                                            'd2', localisatieGrid.localisatie)
                                    + '</span>';
                            return html;
                        };

                        var somRenderer = function(aggregates, column, element,
                                summaryData) {
                            var renderstring = "<div class='jqx-widget-content jqx-widget-content-"
                                    + "ui-sunny"
                                    + "' style='float: right; width: 100%; height: 100%;'>";
                            $
                                    .each(
                                            aggregates,
                                            function(key, value) {
                                                var name = key === 'sum' ? 'Som'
                                                        : 'Avg';

                                                renderstring += '<div style="position: relative; margin: 6px; text-align: right; overflow: hidden;">'
                                                        + name
                                                        + ': '
                                                        + $.jqx.dataFormat
                                                                .formatnumber(
                                                                        value,
                                                                        'd2',
                                                                        localisatieGrid.localisatie)
                                                        + '</div>';
                                            });
                            renderstring += "</div>";
                            return renderstring;
                        };

                        var somFunctie = function(aggregatedValue, currentValue) {
                            return aggregatedValue + currentValue;
                        };

                        var dataAdapterOverzicht = new $.jqx.dataAdapter(
                                sourceOverzicht);

                        $("#overzichtGrid")
                                .jqxGrid(
                                        {
                                            autoheight : true,
                                            width : "1360",
                                            theme : 'ui-sunny',
                                            source : dataAdapterOverzicht,
                                            editable : false,
                                            pageable : true,
                                            pagesizeoptions : [ '10', '20',
                                                    '50', '100' ],
                                            pagesize : 20,
                                            showaggregates : true,
                                            showstatusbar : true,
                                            statusbarheight : 30,
                                            selectionmode : 'none',
                                            enablebrowserselection : true,
                                            columnsresize : true,
                                            sortable : true,

                                            columns : [

                                                    {
                                                        text : "twaalf nr",
                                                        datafield : "project_id",
                                                        width : 100
                                                    },
                                                    {
                                                        text : "vastlegging_nr",
                                                        datafield : "vastlegging_id",
                                                        width : 100
                                                    },
                                                    {
                                                        text : "bestek nr:",
                                                        datafield : "bestek_nr",
                                                        width : 100
                                                    },

                                                    {
                                                        text : "dossier nr",
                                                        datafield : "dossier_id",
                                                        width : 100
                                                    },
                                                    {
                                                        text : "dossier",
                                                        datafield : "dossier_b",
                                                        width : 200
                                                    },
                                                    {
                                                        text : "gemeente",
                                                        datafield : "gemeente_b",
                                                        width : 100
                                                    },
                                                    {
                                                        text : "datum",
                                                        datafield : "datum",
                                                        cellsformat : 'dd-MM-yyyy',
                                                        width : 84
                                                    },
                                                    {
                                                        text : "afsluit datum",
                                                        datafield : "afsluit_d",
                                                        cellsformat : 'dd-MM-yyyy',
                                                        width : 100
                                                    },
                                                    {
                                                        text : "validatie",
                                                        datafield : "spreiding_VALIDATIE_TS",
                                                        cellsformat : 'dd-MM-yyyy',
                                                        width : 100
                                                    },

                                                    {
                                                        text : "VEK voorzien",
                                                        datafield : "vekVoorzien",
                                                        width : 130,
                                                        cellsalign : 'right',
                                                        cellsrenderer : bedragrenderer, // cellsformat:
                                                        // 'c2'
                                                        // ,

                                                        aggregates : [ {
                                                            'sum' : somFunctie

                                                        }

                                                        ],

                                                        aggregatesrenderer : somRenderer
                                                    },

                                                    {
                                                        text : "gefactureerd",
                                                        datafield : "gefactureerd",
                                                        width : 130,
                                                        cellsalign : 'right',
                                                        cellsrenderer : bedragrenderer // cellsformat:
                                                        // 'c2'
                                                        // ,

                                                     /*	aggregates : [ {
                                                            'sum' : somFunctie
                                                        }

                                                        ],

                                                        aggregatesrenderer : somRenderer  */
                                                    },
                                                    //
                                                    //
                                                    //

                                                    {
                                                        text : 'Doorsturen',
                                                        datafield : 'Aanpassen',
                                                        width : 112,
                                                        columntype : 'button',
                                                        cellsrenderer : function() {
                                                            return "Aanpassen";
                                                        },
                                                        buttonclick : function(
                                                                row) {

                                                            var dataRecord = $(
                                                                    "#overzichtGrid")
                                                                    .jqxGrid(
                                                                            'getrowdata',
                                                                            row);

                                                            self
                                                                    .laadDetail(dataRecord.project_id);

                                                        }
                                                    }

                                            ]
                                        });

                    }

                }

                var viewmodel = new ViewModel();

                $("#editSpreidingDiv").dialog(
                        {
                            autoOpen : false,
                            height : 700,
                            width : 900,
                            modal : true,
                            buttons : [ {
                                id : "dialogSave",
                                text : "bevestig",
                                click : function(evt) {

                                    viewmodel.save();

                                }
                            }, {
                                id : "dialogCancel",
                                text : "annuleer",
                                click : function() {
                                    $(this).dialog("close");
                                }
                            } ],

                            close : function(event, ui) {

                                $('#dialogSave').removeClass(
                                        "ui-state-disabled").attr('disabled',
                                        false);

                            }

                        });

                // ko.applyBindings(viewmodel,
                // document.getElementById('VekOverzichtsWrapper'));

                ko.applyBindings(viewmodel);

                // ENKEL NODIG voor debug doeleinden

                window.viewmodel = viewmodel;
                window.ko = ko;

                $('#VekOverzichtsWrapper').removeClass('hidden');

            }

            return VekoverzichtPagina;

        });
