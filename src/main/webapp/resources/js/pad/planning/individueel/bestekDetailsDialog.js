/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console */

define(["ov/ajax", "ov/Meta", "ov/GridComp"], function (ajax, Meta, GridComp) {
    'use strict';

    var $dialog, _grid, _initGrid, _filterDetails, _bestekDetailMeta, $selectBtn, _selectCb;

    _bestekDetailMeta = new Meta([{
        name: "bestek_id",
        hidden: true
    }, {
        name: "bestek_nr",
        hidden: true
    }, {
        name: "dossier_id", type: "int", hidden: true
    }, {
        name: "dossier_nr",
        width: 70,
        slickFormatter: function (row, cell, value, columnDef, item) {
            return '<a href="s/dossier/' + item.dossier_id  + '/basis" target="_blank" >' + value + '</a>';
        }
    }, {
        name: "dossier_b",
        label: "Dossier Titel",
        width: 300
    }, {
        name: "doss_hdr_id",
        label: "Doss. houder"
    }, {
        name: "igb_d",
        label: "Gepland datum",
        type: "date"
    }, {
        name: "ig_bedrag",
        label: "Gepland bedrag",
        type: "double"
    }, {
        name: "bedrag",
        label: "Bedrag deelopdracht",
        type: "double"
    }]);

    _initGrid = function () {
        if (!_grid) {
            _grid = new GridComp({
                el: "#bestekDetails_grid_div",
                meta: _bestekDetailMeta
            });
        }
    };

    _filterDetails = function (details) {
        return _.filter(details, function (detail) {
            return (detail.ig_bedrag !== null);
        });

    };

    function show(bestek_id, bestek_nr, selectCb) {
        _selectCb = selectCb;

        $dialog.dialog({title: "Bestek_nr : " + bestek_nr});

        if (selectCb) {
            $selectBtn.removeClass("hidden");
        } else {
            $selectBtn.addClass("hidden");
        }

        ajax.postJSON({
            url: "/pad/s/planning/getDetailsVoorBestek",
            content: bestek_id
        }).success(function (response) {
            if (response) {
                $dialog.dialog("open");
                _initGrid();

                if (selectCb) {
                    response = _filterDetails(response);
                }
                _grid.setData(response);
            }
        });
    }

    function init() {
        $dialog = $('#bestekDetailsDialog').dialog({
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