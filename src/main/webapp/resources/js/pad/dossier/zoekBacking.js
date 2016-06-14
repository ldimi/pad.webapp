/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "dropdown/dossierTypes",
    "dropdown/dossierhouders",
    "dropdown/dossierhoudersBB",
    "dropdown/dossierhoudersJD",
    "dropdown/programmaTypes",
    "common/dropdown/dossier/doelgroepen_dd",
    "dropdown/provincies",
    "dropdown/fusiegemeenten",
    "dropdown/art46_artikels",
    "dropdown/art46_lijsten",
    "common/dropdown/dossier/fasen",
    "common/dropdown/dossier/rechtsgronden",
    "ov/Model",
    "ov/mithril/formhelperFactory"
], function (dossierTypes, dossierhouders, dossierhoudersBB, dossierhoudersJD, programmaTypes, doelgroepen_dd, provincies,
             fusiegemeenten, art46_artikels, art46_lijsten, fasen, rechtsgronden, Model, fhf) {
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
            { name: "dossier_id_boa" },
            { name: "dossier_id_jd" },
            { name: "doss_hdr_id" },
            { name: "doss_hdr_id_boa" },
            { name: "doss_hdr_id_jd" },
            { name: "dossier_b" },
            { name: "sap_project_nr" },
            { name: "ivs_s" },
            { name: "nis_id" },
            { name: "provincie" },
            { name: "adres" },
            { name: "project_id" },
            { name: "art46_lijst_id" },
            { name: "historiek_lijst_id" },
            { name: "programma_code" },
            { name: "rechtsgrond_code" },
            { name: "dossier_fase_id", type: "int" },
            { name: "doelgroep_type_id" },
            { name: "raamcontract_J_N" },
            { name: "incl_afgesloten_s" },
            { name: "ig_s" },
            { name: "ob_s" },
            { name: "aanpak_s" },
            { name: "aanpak_onderzocht_s" }
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
                this.attributes.dossier_id_boa = null;
                this.attributes.doss_hdr_id_boa = null;

                this.attributes.ig_s = null;
                this.attributes.art46_lijst_id = null;
                this.attributes.historiek_lijst_id = null;
                this.attributes.ob_s = null;
            }

            if (dossier_type !== 'X') {
                this.attributes.raamcontract_J_N = null;
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
                                    m("td", "Dossier type"),
                                    m("td", [
                                        ff.select("dossier_type", {class: "input" }, dossierTypes)
                                    ]),
                                    (ctrl.params.get("dossier_type") === 'X') ? [
                                            m("td", "Raamcontract ?"),
                                            m("td", [
                                                ff.select("raamcontract_J_N", {class: "input" }, ja_nee_dd)
                                            ])
                                        ] : null
                                ]),
                                m("tr", [
                                    m("td", "Bestek nummer"),
                                    m("td", [
                                        ff.input("bestek_nr", {class: "input"})
                                    ])
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
                                m("tr", {style: {visibility: (ctrl.params.get("dossier_type") === 'B') ? "visible" : "hidden"}}, [
                                    m("td", "Dossier nummer BB"),
                                    m("td", [
                                        ff.input("dossier_id_boa", {class: "input"})
                                    ]),
                                    m("td", "Dossierhouder BB"),
                                    m("td", [
                                        ff.select("doss_hdr_id_boa", {class: "input"}, dossierhoudersBB)
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Dossier nummer JD"),
                                    m("td", [
                                        ff.input("dossier_id_jd", {class: "input"})
                                    ]),
                                    m("td", "Dossierhouder JD"),
                                    m("td", [
                                        ff.select("doss_hdr_id_jd", {class: "input"}, dossierhoudersJD)
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Dossiernaam IVS"),
                                    m("td", [
                                        ff.input("dossier_b", {class: "input"})
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "SAP Projectnr"),
                                    m("td", [
                                        ff.input("sap_project_nr", {class: "input"})
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Programma"),
                                    m("td", [
                                        ff.select("programma_code", {class: "input"}, programmaTypes)
                                    ]),
                                    m("td", "Doelgroep"),
                                    m("td", [
                                        ff.select("doelgroep_type_id", {class: "input"}, doelgroepen_dd)
                                    ])
                                ]),
                                m("tr", {style: {visibility: (ctrl.params.get("dossier_type") !== null) ? "visible" : "hidden"}}, [
                                    m("td", "Rechtsgrond"),
                                    m("td", [
                                        ff.select("rechtsgrond_code", {class: "input"}, rechtsgronden[ctrl.params.get("dossier_type")] || [] )
                                    ]),
                                    (ctrl.params.get("dossier_type") !== 'X') ? [
                                            m("td", "Fase"),
                                            m("td", [
                                                ff.select("dossier_fase_id", {class: "input"}, fasen[ctrl.params.get("dossier_type")] || [] )
                                            ])
                                        ] : null
                                ]),
                                m("tr", [
                                    m("td", "Adres"),
                                    m("td", [
                                        ff.input("adres", {class: "input"})
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Provincie"),
                                    m("td", [
                                        ff.select("provincie", {class: "input"}, provincies)
                                    ]),
                                    m("td", "Fusiegemeente"),
                                    m("td", [
                                        ff.select("nis_id", {class: "input"}, fusiegemeenten)
                                    ])
                                ]),
                                m("tr", {style: {visibility: (ctrl.params.get("dossier_type") === 'B') ? "visible" : "hidden"}}, [
                                    m("td[colspan=2]", [
                                        ff.checkbox("ig_s", "Enkel Ingebreke stelling")
                                    ]),
                                    m("td", "Artikel 46"),
                                    m("td", [
                                        ff.select("art46_lijst_id", {class: "input", style: { width: " 135px", float: "left"}}, art46_artikels),
                                        ff.select("historiek_lijst_id", {class: "input", style: { width: " 135px", float: "left"}}, art46_lijsten)
                                    ])
                                 ]),
                                m("tr", {style: {visibility: (ctrl.params.get("dossier_type") === 'B') ? "visible" : "hidden"}}, [
                                    m("td[colspan=2]", [
                                        ff.checkbox("ob_s", "Enkel Onschuldige eigenaar")
                                    ])
                                ]),
                                m("tr", [
                                    m("td[colspan=2]", [
                                        ff.checkbox("incl_afgesloten_s", "Inclusief afgesloten dossiers")
                                    ]),
                                    m("td[colspan=2]", [
                                        ff.checkbox("aanpak_onderzocht_s", "Geintegreerde oplossing")
                                    ])
                                ]),
                                m("tr", [
                                    m("td[colspan=2]", [
                                        ff.checkbox("ivs_s", "IVS dossiers")
                                    ]),
                                    m("td[colspan=2]", [
                                        ff.checkbox("aanpak_s", "Dossier opgestart")
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
