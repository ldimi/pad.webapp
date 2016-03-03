/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, _G_ */

define([
    "ov/ajax",
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "planning/individueel/bestekDetailsDialog",
    "planning/individueel/faseDetailsDialog",
    "ov/formatters",
    "planning/individueel/PlanningLijnRcDialog",
    "planning/individueel/PlanningLijnModel",
    "underscore",
    "ov/select"
], function (ajax, Model, GridComp, events, bestekDetailsDialog, faseDetailsDialog, formatters, PlanningLijnDialog, PlanningLijnModel) {
    'use strict';

    var _overzichtGrid, _grid = null,
        _renderPlanningGegevens, _getPlanning, _planning = null,
        _initOverzichtGrid, _initPlanningLijnenGrid, _initParamsForm, _getOverzicht = null,
        _paramMeta, _paramFm, _overzichtlijnMeta, _planningLijnDialog;

    _paramMeta = Model.buildMeta([
        {
            name: "jaar",
            type: "int"
        }, {
            name: "dossier_id",
            type: "int"
        }, {
            name: "doss_hdr_id"
        }, {
            name: "benut_jn"
        }
    ]);

    _overzichtlijnMeta = Model.buildMeta([{
            name: "bestek_id",
            hidden: true
        }, {
            name: "bestek_nr",
            hidden: true
        }, {
            name: "fase_code",
            hidden: true
        }, {
            name: "group_id",
            label: "Bestek",
            width: 120,
            slickFormatter: function (row, cell, value, columnDef, dataContext) {
                if (dataContext.bestek_id === null) {
                    return value;
                }
                return '<a href="s/bestek/' + dataContext.bestek_id + '" target="_blank" >' + value + '</a>';
            }
        }, {
            name: "omschrijving",
            label: "Omschrijving",
            width: 500
        }, {
            name: "saldo_geraamd",
            label: "Geraamd saldo",
            type: "double"
        }, {
            name: "gepland",
            label: "Gepland",
            type: "double"
        }, {
            name: "voorspeld_saldo",
            label: "Voorspeld saldo",
            type: "double"
    }]);

    _initParamsForm = function () {
        _paramFm = $('#paramForm').ov_formManager({
            meta: _paramMeta
        });
    };

    _initOverzichtGrid = function () {
        _overzichtGrid = new GridComp({
            el: "#overzicht_grid_div",
            meta: _overzichtlijnMeta,
            onEditClicked: function (item) {
                if (item.bestek_id) {
                    bestekDetailsDialog.show(item.bestek_id, item.bestek_nr);
                } else {
                    faseDetailsDialog.show(item.contract_id, item.fase_code);
                }
            }
        });
    };

    _initPlanningLijnenGrid = function () {
        var meta = PlanningLijnModel.prototype.meta.clone();
        meta.getColDef("contract_nr").set("hidden", true);
        meta.getColDef("contract_b").set("hidden", true);
        meta.getColDef("dossier_gemeente_b").set("hidden", true);

        _grid = new GridComp({
            el: "#planningIGB_div",
            meta: meta,
            model: PlanningLijnModel,
            editBtn: true,
            newBtn: true,
            deleteBtn: true,
            onEditClicked: function (item) {
                _planningLijnDialog.show(item, _planning);
            },
            onNewClicked: function (item) {
                var newItem;
                if (item) {
                    newItem = item.createNewLine();
                    _planning.lijnen.selectedIndex = _planning.lijnen.indexOf(item);
                    _planningLijnDialog.show(newItem, _planning);
                } else {
                    alert("er is geen rij geselecteerd.");
                }
            },
            onDeleteClicked: function (item) {
                if (item.get('status_crud') === 'C') {
                    $.notifyError("Ongeplande lijnen worden niet verwijderd.");
                    return;
                }
                if (item.get("c_isReedsGekoppeld")) {
                    $.notifyError("Deze planningslijn kan niet verwijderd worden. (reeds gekoppeld/benut)");
                    return;
                }
                item.set({
                    deleted_jn: "J",
                    status_crud: "U"
                });
                ajax.postJSON({
                    url: "/pad/s/planning/bewaar",
                    content: item.clone()
                }).success(function () {
                    $.notify({
                        text: "De lijn is verwijderd."
                    });
                });

                // lokale versie wordt al aangepast (we gaan ervan uit dat bewaren toch lukt.)
                _planning.lijnen = _.reject(_planning.lijnen, function (lijn) {
                    return (lijn.cid === item.cid);
                });

                events.trigger("planning.lijnen:refresh");
            },
            exportCsv: true,
            exportCsvFileName: "planning.csv"
        });
        events.on("planning.lijnen:refresh", function() {
            _grid.setData(_planning.lijnen);
        });
    };


    _renderPlanningGegevens = function (planning) {
        _planning = planning;

        _planning.faseDetailDD.unshift({
            value: "",
            label: ""
        });
        _planning.contractenDD.unshift({
            value: "",
            label: "nieuwBestek"
        });

        _grid.setData(_planning.lijnen);
        _grid._slickGrid.setActiveCell(0, 0);

        $('#planningIGB_div').removeClass('invisible');
    };

    _getPlanning = function (params) {
        $('#planningIGB_div').addClass('invisible');
        ajax.postJSON({
            url: "/pad/s/planning/getPlanning",
            content: params
        }).success(function (response) {
            if (response) {
                _renderPlanningGegevens(response);
            }
        });

    };

    _getOverzicht = function (params) {
        $('#overzicht_div').addClass('invisible');
        ajax.postJSON({
            url: "/pad/s/planning/getOverzichtRaamcontract",
            content: params.dossier_id
        }).success(function (response) {
            var tot_voorspeld_saldo;
            if (response) {
                _overzichtGrid.setData(response);
                tot_voorspeld_saldo =  _.reduce(response, function (memo, item) {
                    var voorspeld_saldo = item.voorspeld_saldo || 0;
                    return memo + voorspeld_saldo;
                }, 0);
                $('#tot_voorspeld_saldo').text(formatters("double")(tot_voorspeld_saldo));
                $('#overzicht_div').removeClass('invisible');
            }
        });
    };

    function onReady() {
        _initOverzichtGrid();
        _initPlanningLijnenGrid();

        _initParamsForm();

        bestekDetailsDialog.init();
        faseDetailsDialog.init();

        $("#ophalenBtn").click(function () {
            var params = _paramFm.values();
            params.dossier_type = "X";
            _getOverzicht(params);
            _getPlanning(params);
        }).removeClass("invisible");

        _planningLijnDialog = new PlanningLijnDialog(PlanningLijnModel.prototype.meta);
        _planningLijnDialog.init();
    }

    return {
        onReady: onReady
    };
});