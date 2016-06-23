/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "dropdown/dossierTypes",
    "dropdown/dossierhouders",
    "dropdown/programmaTypes",
    "common/dropdown/dossier/fasen",
    "common/dropdown/dossier/rechtsgronden",
    "ov/Model",
    "ov/mithril/formhelperFactory"
], function (dossierTypes, dossierhouders, programmaTypes, fasen, rechtsgronden, Model, fhf) {
    'use strict';

    var _comp, ZoekParamsModel, ja_nee_dd;

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

    ZoekParamsModel = Model.extend({
        meta: Model.buildMeta([
            { name: "dossier_type" },
            { name: "bestek_nr" },
            { name: "dossier_id" },
            { name: "doss_hdr_id" },
            { name: "dossier_b" },
            { name: "wbs_nr" },
            { name: "programma_code" },
            { name: "fase_id", type: "int" },
            { name: "raamcontract_jn" },
            { name: "incl_afgesloten_s" }
        ]),
        enforceInvariants: function () {
            var dossier_type;

            dossier_type = this.get("dossier_type");

            if (dossier_type === null) {
                this.attributes.rechtsgrond_code = null;
                this.attributes.dossier_fase_id = null;
            } else {

                if (this.attributes.rechtsgrond_code !== null) {
                    if (!rechtsgronden.isValid(this.attributes.rechtsgrond_code, dossier_type)) {
                        this.attributes.rechtsgrond_code = null;
                    }
                }

                if (this.attributes.dossier_fase_id !== null) {
                    if (!fasen.isValid(this.attributes.dossier_fase_id, dossier_type)) {
                        this.attributes.dossier_fase_id = null;
                    }
                }
            }

            if (dossier_type !== 'B') {
                this.attributes.ig_s = null;
                this.attributes.historiek_lijst_id = null;
                this.attributes.ob_s = null;
            }

            if (dossier_type !== 'X') {
                this.attributes.raamcontract_jn = null;
            } else {
                // voor andere dossiers wordt hier niet opgezocht !!
                this.attributes.dossier_fase_id = null;
            }


        }
    });



    _comp = {};
    _comp.controller = function () {
        this.params = new ZoekParamsModel();
        this.showErrors = m.prop(false);
    };

    _comp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

        return m("div", {style: {margin: "30px"}}, [
                    (_G_.model.errorMsg) ?
                        m("div", {style: {color : "red"}} , _G_.model.errorMsg) : null,
                    m("form", {action: "/pad/s/dossier/zoek/result"}, [
                        m("table.formlayout", [
                            m("tbody", [
                                m("tr", [
                                    m("td", "Bestek nummer"),
                                    m("td", [
                                        ff.input("bestek_nr", {class: "input"})
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
                                    m("td", ff.select("dossier_fase_id", {class: "input"}, fasen[ctrl.params.get("dossier_type")] || [] ))
                                ]),
                                m("tr" , { style: {height: "20px"}}),
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
                                    m("td", "Dossierhouder IVS"),
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
                                    m("td[colspan=2]", [
                                        ff.checkbox("incl_afgesloten_s", "Inclusief afgesloten bestekken")
                                    ])
                                ]),
                                m("tr", [
                                    m("td[colspan=4][align=right]", [
                                        m("input[type=submit][value=Zoeken].inputbtn")
                                    ])
                                ]),

                                m("tr")
                            ])
                        ])
                    ])
                ]);


    };

    m.mount($("#jsviewContentDiv").get(0), _comp);
});
