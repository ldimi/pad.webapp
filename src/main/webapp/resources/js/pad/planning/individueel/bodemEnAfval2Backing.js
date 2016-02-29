/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, _G_ */

define([
    "planning/individueel/PlanningLijnDialog2",
    "planning/individueel/PlanningLijnModel",
    "dropdown/dossierhouders",
    "dropdown/jaren",
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "mithril",
    "underscore"
], function (PlanningLijnDialog, PlanningLijnModel, dossierhouders_dd, jaren_dd, Model, GridComp, events, ajax, fhf, m, _) {
    'use strict';

    var comp, paramComp, ParamModel, _grid, _initGrid, _getPlanning, _renderPlanningGegevens = null,
        _planning, _planningLijnDialog;


    ParamModel =  Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", type: "int" },
            { name: "dossier_id", type: "int" },
            { name: "doss_hdr_id" },
            { name: "benut_jn" }
        ]),

        enforceInvariants: function () {
            if (this.hasChanged("doss_hdr_id")) {
                window.open('/pad/s/planning/individueel/bodemEnAfval2?doss_hdr_id=' + this.get("doss_hdr_id"),'_top');
            }

        }
    });

    paramComp = {
        controller: function () {
            this.params = new ParamModel({
                doss_hdr_id: _G_.model.doss_hdr_id
            });

            // customisatie van dropdown lijsten voor params
            jaren_dd[0].label = "Basis planning";
            _G_.model.dossiersDD.unshift({value: "", label: ""});

            this.benut_jn_dd = [
                {value: "", label: "incl. gerealiseerd"},
                {value: "J", label: "excl. gerealiseerd"}
            ];

            this.showErrors = m.prop(false);

            this.ophalen = function () {
                alert("Todo");
            };
        },
        view: function (ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

            return m("div",
                m("table", [
                    m("tr", [
                        m("td", "Dossier houder:"),
                        m("td", ff.select("doss_hdr_id", dossierhouders_dd)),
                        m("td", "Jaar:"),
                        m("td", ff.select("jaar", jaren_dd)),
                        m("td", ff.select("benut_jn", ctrl.benut_jn_dd)),
                        m("td", "Dossier nr:"),
                        m("td", ff.select("dossier_id", _G_.model.dossiersDD)),
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
                paramComp.view(ctrl.paramCtrl)
            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

    return;
});