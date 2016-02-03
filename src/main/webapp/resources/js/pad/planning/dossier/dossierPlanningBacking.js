/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, console: false, RGraph, _G_ */

define([
    "ov/ajax",
    "planning/individueel/PlanningLijnModel",
    "ov/GridComp"
], function (ajax, PlanningLijnModel, GridComp) {
    'use strict';

    var onReady, $paramForm, $jaar, $dossier_id, _getPlanning, _initGrid, _grid, _renderPlanningGegevens;

    _getPlanning = function () {
        var params = {};
        params.jaar = $jaar.val();
        params.dossier_id = $dossier_id.val();

        $('#planningGridDiv').addClass('invisible');
        ajax.postJSON({
            url: "/pad/s/planning/getPlanning",
            content: params
        }).success(function (response) {
            if (response) {
                console.log(response);
                _renderPlanningGegevens(response);
            }
        });
    };

    _renderPlanningGegevens = function (planning) {
        _grid.setData(planning.lijnen);
        $('#planningGridDiv').removeClass('invisible');
    };

    _initGrid = function () {
        PlanningLijnModel.prototype.meta.getColDef("dossier_nr").set("hidden", true);
        PlanningLijnModel.prototype.meta.getColDef("dossier_gemeente_b").set("hidden", true);
        PlanningLijnModel.prototype.meta.getColDef("dossier_b").set("hidden", true);
        PlanningLijnModel.prototype.meta.getColDef("dossier_type").set("hidden", true);

        _grid = new GridComp({
            el: '#planningGridDiv',
            model: PlanningLijnModel
        });
    };

    onReady = function () {
        console.log("dossierPlanningBacking ready");

        $paramForm = $('#paramForm');
        $dossier_id = $paramForm.find('[name=dossier_id]');
        $jaar = $paramForm.find('[name=jaar]');
        $jaar.removeClass('invisible');

        _initGrid();

        $jaar.on('change', function () {
            _getPlanning();
        }).removeClass("invisible");
        _getPlanning();
    };

    return {
        onReady: onReady
    };
});