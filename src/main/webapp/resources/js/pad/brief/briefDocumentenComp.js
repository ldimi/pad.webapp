/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ , console */

define([
    "ov/GridComp",
    "brief/BriefDocumentModel",
    "ov/mithril/ajax"
], function (GridComp, BriefDocumentModel, ajax) {
    'use strict';

    var comp, _initGrid;

    _initGrid = function (el, isInitialized) {
        var _grid;
        if (!isInitialized) {
            _grid = new GridComp({
                el: el,
                model: BriefDocumentModel
            });

            $(function () {
                ajax.getJSON({
                    url: '/pad/s/brief/' + comp.brief_id() + '/documenten'
                }).then(function (data) {
                    _grid.setData(data.result);
                });
            });
        }
    };

    comp = {};

    comp.brief_id = m.prop(null);

    comp.controller = function () {};
    comp.view = function() {
        return [
            m("div", {
                config: _initGrid,
                style: {width: "800px", height: "120px"}
            })
        ];
    };

    return comp;

});
