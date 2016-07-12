/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "dropdown/dossierTypes",
    "dropdown/dossierhouders",
    "dropdown/programmaTypes",
    "ov/Model",
    "ov/mithril/formhelperFactory"
], function (dossierTypes, dossierhouders, programmaTypes, Model, fhf) {
    'use strict';

    var _comp, ZoekParamsModel, ja_nee_dd, bestekBodemFase_dd;

    ja_nee_dd = [{
        value: "",
        label: ""
    }, {
        value: "J",
        label: "Ja"
    }, {
        value: "N",
        label: "Nee"
    }];

    bestekBodemFase_dd = _G_.model.bestekBodemFase_dd;
    bestekBodemFase_dd.unshift( {value: "", label: "" });

    ZoekParamsModel = Model.extend({
        meta: Model.buildMeta([
            { name: "bestek_nr" },
            { name: "bestek_hdr_id" },
            { name: "omschrijving" },
            { name: "wbs_nr" },
            { name: "fase_id", type: "int" },
            { name: "dossier_nr" },
            { name: "dossier_type" },
            { name: "doss_hdr_id" },
            { name: "dossier_b" },
            { name: "programma_code" },
            { name: "raamcontract_jn" },
            { name: "incl_afgesloten_bestekken" },
            { name: "incl_afgesloten_dossiers" }
        ]),
        enforceInvariants: function () {
            var dossier_type;

            dossier_type = this.get("dossier_type");

            if (dossier_type !== 'X') {
                this.attributes.raamcontract_jn = null;
            }


        }
    });



    _comp = {};
    _comp.controller = function () {
        this.params = new ZoekParamsModel(_G_.model.params);
        this.showErrors = m.prop(false);
    };

    _comp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

        return m("div", {style: {margin: "30px"}}, [
                    (_G_.model.errorMsg) ?
                        m("div", {style: {color : "red"}} , _G_.model.errorMsg) : null,
                    m("form", {action: "/pad/s/bestek/zoeken"}, [
                        m("table.formlayout", [
                            m("tbody", [
                                m("tr", [
                                    m("td", "Bestek nummer"),
                                    m("td", [
                                        ff.input("bestek_nr", {class: "input"})
                                    ]),
                                    m("td", "Bestekhouder"),
                                    m("td", [
                                        ff.select("bestek_hdr_id", {class: "input"}, dossierhouders)
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Omschrijving"),
                                    m("td", [
                                        ff.input("omschrijving", {class: "input"})
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "SAP WBS"),
                                    m("td", [
                                        ff.input("wbs_nr", {class: "input"})
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Fase"),
                                    m("td", ff.select("fase_id", {class: "input"}, _G_.model.bestekBodemFase_dd))
                                ]),
                                m("tr", [
                                    m("td[colspan=2]", [
                                        ff.checkbox("incl_afgesloten_bestekken", "Inclusief afgesloten bestekken")
                                    ])
                                ]),
                                m("tr", { style: { height: "20px" }}),
                                m("tr", [
                                    m("td", "Dossier type"),
                                    m("td", [
                                        ff.select("dossier_type", {class: "input" }, dossierTypes)
                                    ]),
                                    (ctrl.params.get("dossier_type") === 'X') ? [
                                            m("td", "Raamcontract ?"),
                                            m("td", [
                                                ff.select("raamcontract_jn", {class: "input" }, ja_nee_dd)
                                            ])
                                        ] : null
                                ]),
                                m("tr", [
                                    m("td", "Dossier nummer IVS"),
                                    m("td", [
                                        ff.input("dossier_nr", {class: "input"})
                                    ]),
                                    m("td", "Dossierhouder"),
                                    m("td", [
                                        ff.select("doss_hdr_id", {class: "input"}, dossierhouders)
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Dossiernaam IVS"),
                                    m("td", [
                                        ff.input("dossier_b", {class: "input"})
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Programma"),
                                    m("td", [
                                        ff.select("programma_code", {class: "input"}, programmaTypes)
                                    ])
                                ]),
                                m("tr", [
                                    m("td[colspan=4][align=right]", [
                                        m("input[type=submit][value=Zoeken].inputbtn")
                                    ])
                                ])
                            ])
                        ])
                    ])
                ]);


    };

    m.mount($("#jsviewContentDiv").get(0), _comp);
});
