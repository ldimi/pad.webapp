/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console */

define([
    "ov/ajax", "ov/Meta", "ov/GridComp"
], function (ajax, Meta, GridComp) {
    'use strict';

    var $dialog, _grid, _initGrid, _faseDetailMeta, $selectBtn, _selectCb;

    _faseDetailMeta = new Meta([
        {
            name: "dossier_nr",
            slickFormatter: function (row, cell, value, columnDef, item) {
                return '<a href="dossierdetailsArt46.do?dossier_nr=' + value + '" target="_blank" >' + value + '</a>';
            }
        }, {
            name: "dossier_b",
            label: "Dossier Titel",
            width: 300
        }, {
            name: "doss_hdr_id",
            label: "Dossierhouder"
        }, {
            name: "igb_d",
            label: "Gepland datum",
            type: "date"
        }, {
            name: "ig_bedrag",
            label: "Gepland bedrag",
            type: "double"
        }
    ]);

    _initGrid = function () {
        if (!_grid) {
            _grid = new GridComp({
                el: "#faseDetails_grid_div",
                meta: _faseDetailMeta
            });
        }
    };

    function show(contract_id, fase_code, selectCb) {
        _selectCb = selectCb;

        $dialog.dialog({
            title: "Fase : " + fase_code
        });

        if (selectCb) {
            $selectBtn.removeClass("hidden");
        } else {
            $selectBtn.addClass("hidden");
        }

        ajax.postJSON({
            url: "/pad/s/planning/getDetailsVoorFase",
            content: {
                contract_id: contract_id,
                fase_code: fase_code
            }
        }).success(function (response) {
            if (response) {
                $dialog.dialog("open");
                _initGrid();
                _grid.setData(response);
            }
        });
    }

    function init() {
        $dialog = $('#faseDetailsDialog').dialog({
            autoOpen: false,
            modal: true,
            width: 800,
            height: 400
        });

        $selectBtn = $dialog.find("[name=selectBtn]");

        $selectBtn.click(function () {
            var item;
            item = _grid.getSelectedItem();
            $dialog.dialog("close");
            _selectCb(item);
        });
    }

    return {
        init: init,
        show: show
    };
});