/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, console: false, RGraph, _G_ */

define([
    "planning/individueel/PlanningLijnModel",
    "dropdown/jaren",
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/gridConfigBuilder",
    "mithril",
    "underscore"
], function (PlanningLijnModel, jaren_dd, Model, events, ajax, fhf, gridConfigBuilder, m, _) {
    'use strict';

    var ParamModel, comp, planningOphalen;

    PlanningLijnModel.prototype.meta.getColDef("dossier_nr").set("hidden", true);
    PlanningLijnModel.prototype.meta.getColDef("dossier_gemeente_b").set("hidden", true);
    PlanningLijnModel.prototype.meta.getColDef("dossier_b").set("hidden", true);
    PlanningLijnModel.prototype.meta.getColDef("dossier_type").set("hidden", true);


    planningOphalen = function (params) {
        $('#planningGridDiv').addClass('invisible');
        ajax.postJSON({
            url: "/pad/s/planning/getPlanning",
            content: params
        }).then(function (planning) {
            $('#planningGridDiv').removeClass('invisible');
            events.trigger("planningGrid:refresh", planning.lijnen);
        });
    };

    ParamModel =  Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", type: "int" },
            { name: "dossier_id", type: "int" }
        ]),
        enforceInvariants: function () {
            if (this.hasChanged("jaar")) {
                events.trigger("jaar:changed");
            }

        }
    });

    comp = {
        controller: function () {
            this.params = new ParamModel({
                jaar: null,
                dossier_id: _G_.model.dossier.id
            });
            events.on("jaar:changed", function () {
                planningOphalen(this.params);
            }.bind(this));
            _.defer(function () { planningOphalen(this.params);}.bind(this));
        },
        view: function(ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.params);

            return [
                m("div", {style: {margin: "5px"}}, [
                    "jaar : ",
                    ff.select("jaar", jaren_dd)
                ]),
                m("#planningGridDiv", {
                    style: {height: "160px"},
                    config: gridConfigBuilder({
                                  model: PlanningLijnModel,
                                  setDataEvent: "planningGrid:refresh"
                              })
                })
            ];
        }
    };

    m.mount($("#planning_js_div").get(0), comp);
});
