/*jslint debug: true, browser: true */
/*global define: false, $: false, alert: false, console:false, _:  false , _G_: false */

define([
    "schuldvordering/maakInSapDialog", "ov/Meta", "ov/GridComp", "ov/ajax", "underscore", "ov/select"
], function (maakInSapDialog, Meta, GridComp, ajax) {
    'use strict';
    var svDetailFm, _detailMeta, $svDetailDialog, sv_Project_0_fm, sv_Project_1_fm = null,
        $svbewaarBtn, $svAfkeurenBtn, $svAanmakenSapBtn, $svGoedkeurenBtn, $deelopdrachtSaldoMsg, $motivatieMsg, _facturenGrid, _facturenMeta = null,
        _data, _schuldvorderingOrg, _buildHrefOnClose, _fillData, _fillAntwoordPdfLink;

    function _openDialog() {
        $svDetailDialog.dialog('open');
    }

    function _onCloseDialog() {
        window.location = _buildHrefOnClose();
    }

    function _setGoedkeuringBedrag() {
        var vordering_bedrag, vordering_correct_bedrag, herziening_bedrag, herziening_correct_bedrag, boete_bedrag, goedkeuring_bedrag;

        vordering_bedrag = svDetailFm.$vordering_bedrag.ov_value();
        vordering_correct_bedrag = svDetailFm.$vordering_correct_bedrag.ov_value();

        herziening_bedrag = svDetailFm.$herziening_bedrag.ov_value();
        herziening_correct_bedrag = svDetailFm.$herziening_correct_bedrag.ov_value();

        boete_bedrag = svDetailFm.$boete_bedrag.ov_value();

        if (vordering_bedrag !== null || vordering_correct_bedrag !== null ||
            herziening_bedrag !== null || herziening_correct_bedrag !== null || boete_bedrag !== null) {

            vordering_bedrag = (vordering_correct_bedrag != null) ?
                                   vordering_correct_bedrag :
                                   (vordering_bedrag != null) ? vordering_bedrag : 0 ;
            herziening_bedrag = (herziening_correct_bedrag != null) ?
                                   herziening_correct_bedrag :
                                   (herziening_bedrag != null) ? herziening_bedrag : 0 ;
            boete_bedrag = boete_bedrag || 0;

            goedkeuring_bedrag = vordering_bedrag + herziening_bedrag - boete_bedrag;
            svDetailFm.$goedkeuring_bedrag.ov_value(goedkeuring_bedrag);
        } else {
            svDetailFm.$goedkeuring_bedrag.ov_value(null);
        }
    }

    function _enforceInvariants() {
        var selectedDeelopdracht, goedkeuring_bedrag, editeerbaar_b, bedrag_editeerbaar_b;

        console.log("_enforceInvariants");

        $deelopdrachtSaldoMsg.addClass("hidden");
        if (svDetailFm.$deelopdracht_id.ov_value() !== null) {
            selectedDeelopdracht = svDetailFm.$deelopdracht_id.select('getSelectedOption');

            goedkeuring_bedrag = svDetailFm.$goedkeuring_bedrag.ov_value();
            if (goedkeuring_bedrag && (selectedDeelopdracht.saldo + 0.001 < goedkeuring_bedrag)) {
                $deelopdrachtSaldoMsg.removeClass("hidden");
            }
        }

        if (_data.schuldvordering.afgekeurd_jn === 'J') {
            $('#nietAfgekeurdMsg').hide();
            $('#afgekeurdMsg').show();
        } else {
            $('#nietAfgekeurdMsg').show();
            $('#afgekeurdMsg').hide();
        }

        if (_data.schuldvordering.status === 'IN OPMAAK') {
            editeerbaar_b = false;
            bedrag_editeerbaar_b = false;
            $svbewaarBtn.hide();
            $svGoedkeurenBtn.hide();
            $svAanmakenSapBtn.hide();
            $svAfkeurenBtn.hide();
            alert("DIT IS EEN ERROR. Deze schuldvordering is nog niet ingediend, en mag hier dus niet zichtbaar zijn.");
        } else if (_data.schuldvordering.status === 'INGEDIEND') {
            if (!_data.projecten[0]) {
                // niet vastgelegd
                if (svDetailFm.$brief_nr.ov_value() !== null) {
                    // niet digitaal
                    editeerbaar_b = true;
                    bedrag_editeerbaar_b = true;
                    $svbewaarBtn.show();
                    $svGoedkeurenBtn.hide();
                    $svAanmakenSapBtn.show();
                    $svAfkeurenBtn.show();
                } else {
                    // digitaal
                    editeerbaar_b = false;
                    bedrag_editeerbaar_b = false;
                    $svbewaarBtn.hide();
                    $svGoedkeurenBtn.hide();
                    $svAanmakenSapBtn.show();
                    $svAfkeurenBtn.show();
                }
            } else {
                // al vastgelegd
                if (svDetailFm.$brief_nr.ov_value() !== null) {
                    // niet digitaal
                    editeerbaar_b = true;
                    bedrag_editeerbaar_b = false;
                    $svbewaarBtn.show();
                    $svGoedkeurenBtn.show();
                    $svAanmakenSapBtn.hide();
                    $svAfkeurenBtn.hide();
                } else {
                    // digitaal
                    editeerbaar_b = false;
                    bedrag_editeerbaar_b = false;
                    $svbewaarBtn.hide();
                    $svGoedkeurenBtn.show();
                    $svAanmakenSapBtn.hide();
                    $svAfkeurenBtn.hide();
                }
            }
        } else {
            // BEOORDEELD, ONDERTEKEND, VERZONDEN
            $svbewaarBtn.hide();
            $svGoedkeurenBtn.hide();
            $svAanmakenSapBtn.hide();
            $svAfkeurenBtn.hide();
        }

        svDetailFm.$form.find('input.editeerbaar, textarea.editeerbaar').attr({readonly: !editeerbaar_b});
        svDetailFm.$form.find('select.editeerbaar').attr({disabled: !editeerbaar_b});
        svDetailFm.$van_d.attr({disabled: !editeerbaar_b});
        svDetailFm.$tot_d.attr({disabled: !editeerbaar_b});

        svDetailFm.$form.find('.editeerbaar.bedrag').attr({readonly: !bedrag_editeerbaar_b});

        if (bedrag_editeerbaar_b) {
            _setGoedkeuringBedrag();
        }

        if (_data.schuldvordering.motivatie !== null) {
            $motivatieMsg.html("Negatief beoordeeld wegens : " + _data.schuldvordering.motivatie);
            $motivatieMsg.show();
        } else {
            $motivatieMsg.html("");
            $motivatieMsg.hide();
        }


        // indien bekeken vanuit brief (secretariaat !) minder functionaliteit
        //   TODO :  beter testen op rol 'secretariaat' als die er is.
        if (window.location.href.indexOf("brief") > 0 ) {
            $svAanmakenSapBtn.hide();
            $svAfkeurenBtn.hide();
        }
    }

    function showNieuw(brief_id, vordering_d) {
        // vordering_d wordt als string doorgegeven !
        ajax.getJson({
            url: "/pad/s/sv/nieuw",
            content: {
                brief_id: brief_id,
                vordering_d: vordering_d
            }
        }).success(function (data) {
            _fillData(data);
        });

    }

    function show(vordering_id) {

        // TEST GEGEVENS:
        //vordering_id = 33; // niet met sap gekoppeld, met deelopdrachten
        //vordering_id = 6066; // al met sap gekoppeld
        //vordering_id = 48; // met facturen
        //vordering_id = 5561; // niet met sap gekoppeld, zonder deelopdrachten, testdossier
        //vordering_id = 6683; // niet met sap gekoppeld, met meerdere mogelijke deelopdrachten

        ajax.getJson({
            url: "/pad/s/sv/get",
            content: {
                vordering_id: vordering_id
            }
        }).success(_fillData);
    }

    _fillAntwoordPdfLink = function(vordering_id, bestek_id, antw_dms_folder, antw_dms_filename) {
        var link;
        if (antw_dms_folder) {
            link =  '<a href="' + _G_.dms_webdrive_base + antw_dms_folder + '/' + antw_dms_filename + '" target="_blank">' +
                    '<img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Antwoord bekijken" title="Antwoord bekijken"/>' +
                    '</a>';
        } else {
            link = '<a href="/pad/s/bestek/' + bestek_id + '/schuldvordering/draftSchuldvordering-' + vordering_id + '.pdf" target="_blank" >draft</a>';
        }
        $("#antw_pdf_brief_div").html(link);
    };

    _fillData = function (data) {
        _data = data;
        _schuldvorderingOrg = _.clone(data.schuldvordering);
        // bugfix commentaar:
        //   linebreaks via digitaal traject komen anders door
        if (_schuldvorderingOrg.commentaar) {
            _schuldvorderingOrg.commentaar = $('<div />').html(_schuldvorderingOrg.commentaar).text();
        }

        svDetailFm.$schuldvordering_fase_id.select({
            optionList: _.chain(data.briefcategorieLijst).unshift({
                value: "",
                label: ""
            }).value()
        });


        if (data.schuldvordering.dossier_type === "X") {
            $("._deelopdracht").show();

            $("._deelopdracht").addClass("required");

            if (data.deelopdrachten && data.deelopdrachten.length > 0) {
                data.deelopdrachten.unshift({
                    label: "",
                    value: ""
                }); //lege optie
                svDetailFm.$deelopdracht_id.select({
                    optionList: data.deelopdrachten
                });
            }
        } else {
            $("._deelopdracht").hide();
        }

        svDetailFm.populate(data.schuldvordering);
        _fillAntwoordPdfLink(data.schuldvordering.vordering_id,
            data.schuldvordering.bestek_id,
            data.schuldvordering.antw_dms_folder,
            data.schuldvordering.antw_dms_filename
        );

        sv_Project_0_fm.populate(data.projecten[0]);
        sv_Project_1_fm.populate(data.projecten[1]);

        if (data.projecten[0]) {
            sv_Project_0_fm.$form.show();
        } else {
            sv_Project_0_fm.$form.hide();
        }
        if (data.projecten[1]) {
            sv_Project_1_fm.$form.show();
        } else {
            sv_Project_1_fm.$form.hide();
        }

        if (data.facturen && data.facturen.length > 0) {
            $('.facturen').show();
        } else {
            $('.facturen').hide();
        }

        _openDialog();
        if (data.facturen && data.facturen.length > 0) {
            _facturenGrid.setData(data.facturen);
        }

        _enforceInvariants();
    };

    function _doSave() {
        ajax.postJson({
            url: "/pad/s/sv/bewaar",
            content: _data
        }).success(function (data) {
            _fillData(data);
            $.notify({
                text: "De wijzigingen zijn bewaard."
            });
        });
    }

    function _extractSchulvorderingData() {
        svDetailFm.extractTo(_data.schuldvordering);
    }

    function _onSave() {
        if (svDetailFm.validate()) {
            _extractSchulvorderingData();
            if (_data.schuldvordering.vordering_id !== null && _.isEqual(_data.schuldvordering, _schuldvorderingOrg) ) {
                $.notify({
                    text: 'Er zijn geen wijzigingen.'
                });
            }


            else {
                _doSave();
            }
        } else {
            $.notify({
                text: 'Er zijn validatie fouten.'
            });
        }
    }

    function _onAanmakenSap() {
        var msg = "";
        if (svDetailFm.validate()) {

            _extractSchulvorderingData();
            if (!$deelopdrachtSaldoMsg.is(':hidden')) {
                alert("Saldo deelopdracht is ontoereikend.\nGelieve dit eerst in de deelopdracht aan te passen.");
                return false;
            }

            if (_data.schuldvordering.schuldvordering_fase_id === null) {
                msg = msg + "'Fase' moet ingevuld worden.\n";
            }
            if (_data.schuldvordering.van_d === null) {
                msg = msg + "'Van datum' moet ingevuld worden.\n";
            }
            if (_data.schuldvordering.tot_d === null) {
                msg = msg + "'Tot datum' moet ingevuld worden.\n";
            }

            if (_data.schuldvordering.vordering_bedrag === null) {
                msg = msg + "'Inkomend bedrag moet ingevuld worden.\n";
            }

            if (msg !== "") {
                alert(msg);
                return false;
            }


            if (_.isEqual(_data.schuldvordering, _schuldvorderingOrg)) {
                maakInSapDialog.show(_data);
            } else {
                alert('De schuldvordering is gewijzigd.\n Deze wijzigingen moeten eerst bewaard worden.');
            }
        } else {
            $.notify({
                text: 'Er zijn validatie fouten.'
            });
        }
    }

    function _afkeuren() {
        var msg = "";
        if (svDetailFm.validate()) {
            _extractSchulvorderingData();

            if (_data.schuldvordering.schuldvordering_fase_id === null) {
                msg = msg + "'Fase' moet ingevuld worden.\n";
            }

            if (msg !== "") {
                alert(msg);
                return false;
            }

            if (_.isEqual(_data.schuldvordering, _schuldvorderingOrg)) {
                ajax.postJson({
                    url: "/pad/s/sv/afkeuren",
                    content: _data
                }).success(function (data) {
                    _fillData(data);
                });
            } else {
                alert('De schuldvordering is gewijzigd.\n Deze wijzigingen moeten eerst bewaard worden.');
            }
        } else {
            $.notify({
                text: 'Er zijn validatie fouten.'
            });
        }
    }

    function _goedkeuren() {
        if (svDetailFm.validate()) {
            _extractSchulvorderingData();

            if (_.isEqual(_data.schuldvordering, _schuldvorderingOrg)) {
                ajax.postJson({
                    url: "/pad/s/sv/goedkeuren",
                    content: _data
                }).success(function (data) {
                    _fillData(data);
                });
            } else {
                alert('De schuldvordering is gewijzigd.\n Deze wijzigingen moeten eerst bewaard worden.');
            }
        } else {
            $.notify({
                text: 'Er zijn validatie fouten.'
            });
        }
    }

    function _initFacturenGrid() {
        if (!_facturenGrid) {
            _facturenGrid = new GridComp({
                el: "#facturenDiv",
                meta: _facturenMeta,
                statusMsg: false
            });
        }
    }

    _detailMeta = new Meta([
        {
            name: 'brief_nr',
            type: 'string',
            required: false
        }, {
            name: 'bestek_nr',
            type: 'string',
            required: true
        }, {
            name: 'deelopdracht_id',
            type: 'int'
        }, {
            name: 'vordering_d',
            type: 'date',
            required: true
        }, {
            name: 'vordering_nr',
            type: 'string'
        }, {
            name: 'schuldvordering_fase_id',
            type: 'int'
        }, {
            name: 'goedkeuring_d',
            type: 'date'
        }, {
            name: 'uiterste_verific_d',
            type: 'date'
        }, {
            name: 'uiterste_d',
            type: 'date'
        }, {
            name: 'van_d',
            type: 'date'
        }, {
            name: 'tot_d',
            type: 'date'
        }, {
            name: 'vordering_bedrag',
            type: 'double',
            min: 1,
            max: 35000000
        }, {
            name: 'vordering_correct_bedrag',
            type: 'double',
            min: 1,
            max: 35000000
        }, {
            name: 'herziening_bedrag',
            type: 'double'
        }, {
            name: 'herziening_correct_bedrag',
            type: 'double'
        }, {
            name: 'boete_bedrag',
            type: 'double',
            min: 1,
            max: 35000000
        }, {
            name: 'goedkeuring_bedrag',
            type: 'double',
            min: 1,
            max: 35000000
        }, {
            name: 'commentaar',
            type: 'string',
            size: 3000
        }
    ]);

    _facturenMeta = new Meta([
        {
            name: "factuur_id",
            label: "Factuur nr",
            width: 100
        }, {
            name: "initieel_acht_nr",
            label: "Vastlegging",
            width: 100
        }, {
            name: "volgnummer",
            label: "Volg nr",
            width: 50
        }, {
            name: "saldo",
            label: "Saldo",
            type: "double",
            width: 100
        }, {
            name: "bedrag",
            label: "Bedrag",
            type: "double",
            width: 100
        }, {
            name: "factuur_d",
            label: "Factuurdatum",
            type: "date",
            width: 100
        }, {
            name: "betaal_d",
            label: "Betaaldatum",
            type: "date",
            width: 100
        }
    ]);

    function init() {
        $svDetailDialog = $('#svDetailDialog').dialog({
            autoOpen: false,
            modal: true,
            width: 750,
            open: function(event) {
                $(event.target).parent().css('position', 'fixed');
                $(event.target).parent().css('top', '50px');
                $(event.target).parent().css('left', '100px');
            },
            close: _onCloseDialog
        });

        svDetailFm = $('#svDetailForm').ov_formManager({
            meta: _detailMeta
        });
        svDetailFm.$form.find('.editeerbaar').change(_enforceInvariants);

        svDetailFm.$van_d.datepicker();
        svDetailFm.$tot_d.datepicker();

        sv_Project_0_fm = $('#svProjectForm_0').ov_formManager();
        sv_Project_1_fm = $('#svProjectForm_1').ov_formManager();

        $svbewaarBtn = $('#svbewaarBtn').on('click', _onSave);
        $svAanmakenSapBtn = $('#svAanmakenSapBtn').on('click', _onAanmakenSap);
        $svGoedkeurenBtn = $('#svGoedkeurenBtn').on('click', _goedkeuren);
        $svAfkeurenBtn = $('#svAfkeurenBtn').on('click', _afkeuren);
        $deelopdrachtSaldoMsg = $('#deelopdrachtSaldoMsg');
        $motivatieMsg = $('#motivatieMsg');

        _initFacturenGrid();
    }

    _buildHrefOnClose = function () {
        var index_brief, index_schuldvorderingen;

        index_brief = window.location.href.indexOf("brief");
        if (index_brief > 0) {
            return window.location.href.substr(0, index_brief) + "briefdetails.do";
        }
        // verwijder vordering_id van url.
        index_schuldvorderingen = window.location.href.indexOf("schuldvorderingen");
        return window.location.href.substr(0, index_schuldvorderingen + 17);
    };


    // initialize onReady;
    $(init);


    //globale variabele zetten (tijdelijke hack compatibel met dojo opzet)
    // beter onclick op button.
    window.editSchuldVorderingDialog = {
        show: show,
        showNieuw: showNieuw
    };

    return window.editSchuldVorderingDialog;
});