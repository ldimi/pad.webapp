/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, _G_ */

define([
    "planning/individueel/PlanningLijnModel",
    "dropdown/dossierhouders",
    "dropdown/jaren",
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "underscore",
    "mithril"
], function (PlanningLijnModel, dossierhouders_dd, jaren_dd, Model, GridComp, events, ajax, fhf, _, m) {
    'use strict';

    var ParamModel, paramComp, comp;

    PlanningLijnModel.prototype.meta.getColDef("ib_bedrag").set("hidden", true);
    PlanningLijnModel.prototype.meta.getColDef("ibb_d").set("hidden", true);


    ParamModel =  Model.extend({
        meta: Model.buildMeta([
            { name: "doss_hdr_id" },
            { name: "dossier_type" },
            { name: "jaar", type: "int"}
        ])
    });

    paramComp = {
        controller: function () {
            this.params = new ParamModel({
                doss_hdr_id: _G_.model.doss_hdr_id,
                dossier_type: "ALLE",
                jaar: new Date().getFullYear()
            });

            this.showErrors = m.prop(false);

            // methods
            ///////////////////////////////////////////////////////////////

            this.ophalen = function () {
                $('#takenlijst').addClass('invisible');
                ajax.postJSON({
                    url: "/pad/s/planning/getTaken",
                    content: this.params
                }).then(function (taken) {
                    events.trigger("taken:refresh", _.sortBy(taken, "igb_d"));
                    $('#takenlijst').removeClass('invisible');
                });
            }.bind(this);
        },
        view: function (ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

            return m("div",
                m("table", [
                    m("tr", [
                        m("td", "Dossier houder:"),
                        m("td", ff.select("doss_hdr_id", {style: {width: "200px"}}, dossierhouders_dd)),
                        m("td", "Jaar:"),
                        m("td", ff.select("jaar", jaren_dd)),
                        m("td", m("button", {class: "inputBtn", onclick: _.bind(ctrl.ophalen, ctrl)}, "Ophalen"))
                    ])
                ])
            );
        }
    };

    comp = {
        controller: function () {
            this.paramCtrl = new paramComp.controller();
        },
        view: function (ctrl) {
            return m("div", [
                paramComp.view(ctrl.paramCtrl),
                m("#takenlijst", {
                    config: comp.configGrid,
                    class: "invisible",
                    style: {
                        position: "absolute",
                        top: "35px",
                        left: "5px",
                        right: "5px",
                        bottom: "5px"
                    }
                })
            ]);
        },
        configGrid: function (el, isInitialized) {
            var grid;
            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
                    model: PlanningLijnModel,
                    exportCsv: true,
                    exportCsvFileName: "taken_planning.csv"
                });
                events.on("taken:refresh", function(planningLijnen) {
                    grid.setData(planningLijnen);
                });
            }
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

    return;
});