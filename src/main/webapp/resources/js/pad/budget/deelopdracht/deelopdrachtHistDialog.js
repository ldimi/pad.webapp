/*jslint nomen: true, debug: true, browser: true */
/*global define: false, console: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "budget/deelopdracht/DeelopdrachtHistModel",
    "ov/GridComp",
    "ov/mithril/ajax",
    "ov/events"
], function (DeelopdrachtHistModel, GridComp, ajax, event) {
    'use strict';
    var _comp;

    _comp = {};

    _comp.controller = function () {

        event.on("deelopdrachtHistDialog:open", this.open.bind(this));

    };
    _.extend(_comp.controller.prototype, {
        open: function (deelopdracht_id) {
            var self = this;
            ajax.getJson({
                url: '/pad/s/deelopdracht/historiek?id=' + deelopdracht_id
            }).then(function (response) {
                if (response && response.success) {
                    window.setTimeout(function () {
                        //m.redraw();
                        self.$detailDialog.dialog('open');
                        event.trigger("historiek:dataReceived",response.result);
                    });
                } else {
                    alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                }
            });

        },
        close: function () {
            this.$detailDialog.dialog('close');
        },
        _configDialog: function (el, isInitialized) {
            if (!isInitialized) {
                this.$detailDialog = $(el).dialog({
                    title: "Deelopdracht historiek",
                    autoOpen: false,
                    modal: true,
                    width: 840,
                    height: 500
                });
            }
        },
        _configGrid: function (el, isInitialized) {
            var _grid;

            if (!isInitialized) {
                _grid = new GridComp({
                    el: el,
                    model: DeelopdrachtHistModel
                });
                event.on("historiek:dataReceived", function(data) {
                    _grid.setData(data);
                });
            }
        }
    });

    _comp.view = function(ctrl) {

        return [
            m("#historiekDialog.hidden", {config: _.bind(ctrl._configDialog, ctrl) }, [
                m("div", {
                    config: _.bind(ctrl._configGrid, ctrl),
                    style: {
                        width: "790px",
                        height: "400px"
                    }
                })
            ]
        )];
    };

    return _comp;
});