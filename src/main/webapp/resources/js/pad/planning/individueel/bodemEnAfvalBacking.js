/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, _G_ */

define([
    "ov/ajax",
    "ov/Meta",
    "planning/individueel/PlanningLijnDialog",
    "planning/individueel/PlanningLijnModel",
    "ov/GridComp",
    "ov/events",
    "underscore",
    "ov/select"
], function (ajax, Meta, PlanningLijnDialog, PlanningLijnModel, GridComp, events) {
    'use strict';

    var _grid, _initGrid, _getPlanning, _renderPlanningGegevens = null,
        _paramMeta, _paramFM, _planning, _planningLijnDialog;


    _paramMeta = new Meta([
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


    _initGrid = function () {
        _grid = new GridComp({
            el: '#planningIGB_div',
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
                    _planning.selectedLijnIndex = _planning.lijnen.indexOf(item);
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
        window.GRID = _grid;
    };

    _renderPlanningGegevens = function (planning) {
        _planning = planning;

        _planning.faseDD.unshift({
            value: "",
            label: ""
        });
        _planning.faseDetailDD.unshift({
            value: "",
            label: ""
        });
        _planning.contractenDD.unshift({
            value: "",
            label: ""
        });

        _grid.setData(_planning.lijnen);

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

    function onReady() {
        _initGrid();

        _paramFM = $('#paramForm').ov_formManager({
            meta: _paramMeta
        });

        $("#ophalenBtn").click(function () {
            var params = _paramFM.values();
            params.dossier_type = "NIET_X";
            _getPlanning(params);
        }).removeClass("invisible");

        _planningLijnDialog = new PlanningLijnDialog(PlanningLijnModel.prototype.meta);
        _planningLijnDialog.init();
    }

    return {
        onReady: onReady
    };
});