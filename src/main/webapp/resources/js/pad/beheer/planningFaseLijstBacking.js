/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, alert: false */

define([
    "ov/ajax",
    "ov/Model2",
    "ov/GridComp",
    "ov/events",
    "beheer/planningFaseDialog"
], function (ajax, Model, GridComp, events, planningFaseDialog) {
    'use strict';
    var _initGrid, _handleResponse, _haalFasen, FaseModel;

    FaseModel = Model.extend({
        meta: Model.buildMeta([{
                name: "dossier_type",
                type: "string",
                label: "Dossier type",
                required: true
            }, {
                name: "fase_code",
                type: "string",
                label: "Fase code",
                required: true
            }, {
                name: "fase_code_b",
                type: "string",
                label: "Omschrijving"
            }, {
                name: "budget_code",
                type: "string",
                label: "Budget",
                required: true
            }])
    });

    _initGrid = function () {
        var grid = new GridComp({
            el: "#fasenGrid",
            model: FaseModel,
            newBtn: window._G_isAdminArt46,
            editBtn: window._G_isAdminArt46,
            deleteBtn:window._G_isAdminArt46,
            onNewClicked: function (item, faseArray) {
                planningFaseDialog.show(new FaseModel(), faseArray);
            },
            onEditClicked: function (item, faseArray) {
                if (window._G_isAdminArt46) {
                    planningFaseDialog.show(item, faseArray);
                }
            },
            onDeleteClicked: function (item) {
                if (item) {
                    ajax.postJson({
                        url: '/pad/s/planningFase/delete',
                        content: item
                    }).success(_handleResponse);
                }
            }
        });
        events.on("fasenGrid:setData", function (data) {
            grid.setData(data);
        });
    };

    _handleResponse = function (response) {
        if (response && response.success) {
            events.trigger("fasenGrid:setData", response.result);
        } else {
            alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
        }
    };

    function onReady() {
        planningFaseDialog.init(FaseModel.prototype.meta);
        _initGrid();
        _haalFasen.success(_handleResponse);
    }

    _haalFasen = ajax.getJSON({
        url: '/pad/s/planningFasen'
    });

    return {
        onReady: onReady
    };
});