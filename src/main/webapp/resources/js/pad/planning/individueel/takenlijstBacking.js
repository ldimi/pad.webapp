/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, _G_ */

define([
    "ov/ajax",
    "ov/Meta",
    "planning/individueel/PlanningLijnModel",
    "ov/GridComp",
    "underscore",
    "ov/select"
], function (ajax, Meta, PlanningLijnModel, GridComp, _) {
    'use strict';

    var _grid, _initGrid, _getTaken, _renderTaken = null,
        _paramMeta, _paramFM;

    _paramMeta = new Meta([{
        name: "jaar",
        type: "int"
    }]);


    _initGrid = function () {
        var meta = PlanningLijnModel.prototype.meta.clone();
        meta.getColDef("ib_bedrag").set("hidden", true);
        meta.getColDef("ibb_d").set("hidden", true);


        _grid = new GridComp({
            el: '#takenlijst_div',
            meta: meta,
            model: PlanningLijnModel
        });
    };

    _renderTaken = function (taken) {
        _grid.setData(_.sortBy(taken, "igb_d"));

        $('#takenlijst_div').removeClass('invisible');
    };

    _getTaken = function (params) {
        $('#takenlijst_div').addClass('invisible');
        ajax.postJSON({
            url: "/pad/s/planning/getTaken",
            content: params
        }).success(function (taken) {
            _renderTaken(taken);
        });

    };

    function onReady() {
        _initGrid();

        _paramFM = $('#paramForm').ov_formManager({meta: _paramMeta});
        _paramFM.$jaar.val(new Date().getFullYear());

        $("#ophalenBtn").click(function () {
            var params = _paramFM.values();

            // "ALLE" staat voor alle dossiertypes !
            params.dossier_type = "ALLE";

            _getTaken(params);
        }).removeClass("invisible");

    }

    return {
        onReady: onReady
    };
});