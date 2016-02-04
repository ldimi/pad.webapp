/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/Model",
    "common/dropdown/dossier/rechtsgronden",
    "common/dropdown/dossier/fasen",
    "dropdown/dossierhouders",
    "dropdown/programmaTypes",
    "dropdown/fusiegemeenten",
    "common/dropdown/dossier/doelgroepen_dd",
    "common/dropdown/dossier/screening/risicos",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (Model, rechtsgronden, fasen, dossierhouders, programmaTypes, fusiegemeenten, doelgroepen_dd,
             risicos, ajax, fhf) {
    'use strict';

    var DossierModel, comp;

    DossierModel = Model.extend({
        meta: Model.buildMeta([
            { name: "id", type: "int" },
            { name: "dossier_id" },
            { name: "dossier_id_boa", type: "int" },
            { name: "smeg_naam" },
            { name: "smeg_id", type: "int" },
            { name: "dossier_hdr_id" },
            { name: "dossier_b" },
            { name: "dossier_type" },

            { name: "adres" },
            { name: "nis_id" },
            { name: "deelgemeente" },
            { name: "postcode" },
            { name: "land" },

            { name: "afsluit_d", type: "date" },
            { name: "commentaar" },
            { name: "dossier_fase_id", type: "int" },
            { name: "aanpak_onderzocht_s" },
            { name: "aanpak_onderzocht_l" },
            { name: "financiele_info" },
            { name: "onderzoek_id" },
            { name: "conform_bbo_d", type: "date" },
            { name: "conform_bsp_d", type: "date" },
            { name: "eindverklaring_d", type: "date" },
            { name: "sap_project_nr"},
            { name: "wbs_ivs_nr" },
            { name: "programma_code" },
            { name: "rechtsgrond_code" },
            { name: "doelgroep_type_id", type: "int" },

            { name: "smeg_naam" },
            { name: "commentaar_bodem" }
        ]),

        enforceInvariants: function () {

            if (this.get("id") === null && this.get("dossier_b") === null ) {
                this.validationError.dossier_b = 'verplicht veld';
            }

            if (this.get("aanpak_onderzocht_s") === "1" && this.get("aanpak_onderzocht_l") === null ) {
                this.validationError.aanpak_onderzocht_l = 'verplicht veld';
            }

            if (this.get("dossier_b") === null) {
                if ( this.get("afsluit_d") !== null ||
                     this.get("conform_bsp_d") !== null ||
                     this.get("eindverklaring_d") !== null ||
                     this.get("commentaar") !== null ||
                     this.get("aanpak_onderzocht_s") !== null ||
                     this.get("aanpak_onderzocht_l") !== null    ) {
                    this.validationError.dossier_b = 'verplicht veld';
                }
            }

        }

    });


    comp = {
        controller: function () {
            this.dossier = new DossierModel(_G_.model.dossier);
            this.showErrors = m.prop(false);

            this.bewaar = function () {
                this.showErrors(true);

                if (this.dossier.get("id") === null || this.dossier.isDirty()) {
                    if (!this.dossier.isValid()) {
                        $.notify("Er zijn validatie fouten.");
                        return;
                    }

                    ajax.postJson({
                        url: "/pad/s/dossier/save",
                        content: this.dossier
                    }).then(function (response) {
                        if (response && response.success) {
                            window.location = "http://" + window.location.host + "/pad/s/dossier/" + response.result + "/basis";
                        } else {
                            alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                        }
                    });

                } else {
                    $.notify("Er zijn geen aanpassingen te bewaren.");
                }
            };

        },
        view: function (ctrl) {
            var ff, bodemPanel, dossier_type;

            dossier_type = ctrl.dossier.get("dossier_type");

            ff = fhf.get().setModel(ctrl.dossier).setShowErrors(ctrl.showErrors())
                    .setDefaultAttrs({
                        "readOnly": function (attrName) {
                            if (_G_.model.isAdminIVS || _G_.model.isAdminArt46) {
                                if (ctrl.dossier.get("dossier_type") === 'B' &&
                                    ctrl.dossier.get("dossier_nr").indexOf('_') === 0 &&
                                    !_G_.model.isAdminArt46                                   ) {
                                    return true;
                                }

                                if (ctrl.dossier.get("afsluit_d") !== null) {
                                    if (attrName !== "afsluit_d") {
                                        return true;
                                    }
                                    return false;
                                }
                                return false;
                            }
                            return true;
                        }
                    });

            bodemPanel = function (ctrl) {
                return m("fieldset", [
                    m(".row.form-group", [
                        m(".col-md-12",
                            m("a",
                                {href: _G_.model.mistralUrl + "?detailId=" + ctrl.dossier.get("smeg_id")},
                                "Dossier bodem: " + ctrl.dossier.get("smeg_naam") + " ( " + ctrl.dossier.get("dossier_id_boa") + " )"
                        ))
                    ]),
                    m(".row.form-group", [
                        m(".col-md-2", m("label","Commentaar Bodem")),
                        m(".col-md-10", ff.textarea("commentaar_bodem", {rows: 2}))
                    ])
                ]);
            };

            return m(".formlayout.container-fluid",[
                (dossier_type === 'B') ? bodemPanel(ctrl) : null,
                m("fieldset", [
                    (dossier_type === 'B') ? m("legend", "Dossierdetails IVS") : m(".row", {style: {height: "15px"}}),
                    m(".row.form-group", [
                        m(".col-md-2", m("label","Dossiernummer IVS")),
                        m(".col-md-4", ff.input("dossier_id", {readOnly : true})),
                        m(".col-md-2", m("label","SAP projectNr")),
                        m(".col-md-4", ff.input("sap_project_nr", {readOnly : true}))
                    ]),
                    m(".row.form-group", [
                        m(".col-md-2", m("label","Titel")),
                        m(".col-md-4", ff.input("dossier_b")),
                        m(".col-md-2", m("label","Dossierhouder")),
                        m(".col-md-4", ff.select("doss_hdr_id", dossierhouders))
                    ]),
                    m(".row.form-group", [
                        (dossier_type !== 'X') ? [
                            m(".col-md-2", m("label","Gemeente")),
                            m(".col-md-4", ff.select("nis_id", fusiegemeenten))
                        ] : null
                    ]),
                    (dossier_type === 'A') ? [
                        m(".row.form-group", [
                            m(".col-md-2", m("label","Adres")),
                            m(".col-md-4", ff.input("adres"))
                        ]),
                        m(".row.form-group", [
                            m(".col-md-2", m("label","Postcode")),
                            m(".col-md-4", ff.input("postcode"))
                        ]),
                        m(".row.form-group", [
                            m(".col-md-2", m("label","Deelgemeente")),
                            m(".col-md-4", ff.input("deelgemeente"))
                        ])
                    ] : null,
                    (dossier_type === 'B') ?
                        m(".row.form-group", [
                            m(".col-md-2", m("label","Adres (bodemonderzoek)")),
                            m(".col-md-4", ff.select("onderzoek_id", _G_.model.dossierAdressen))
                        ]) : null,
                    m(".row.form-group", [
                        (dossier_type !== 'X') ? [
                            m(".col-md-2", m("label","Rechtsgrond")),
                            m(".col-md-4", ff.select("rechtsgrond_code", rechtsgronden[dossier_type]))
                        ] : null,
                        m(".col-md-2", m("label","Fase")),
                        m(".col-md-4", ff.select("dossier_fase_id", fasen[dossier_type]))
                    ]),
                    m(".row.form-group", [
                        m(".col-md-2", m("label","Programma")),
                        m(".col-md-4", ff.select("programma_code", programmaTypes)),
                        (dossier_type !== 'X') ? [
                            m(".col-md-2", m("label","Doelgroep")),
                            m(".col-md-4", ff.select("doelgroep_type_id", doelgroepen_dd))
                        ] : null
                    ]),
                    (dossier_type === 'B') ? [
                        m(".row.form-group", [
                            m(".col-md-2", m("label","BBO conform")),
                            m(".col-md-4", ff.dateInput("conform_bbo_d")),
                            m(".col-md-2", m("label","BSP conform")),
                            m(".col-md-4", ff.dateInput("conform_bsp_d"))
                        ]),
                        m(".row.form-group", [
                            m(".col-md-2", m("label","Eindverklaring")),
                            m(".col-md-4", ff.dateInput("eindverklaring_d"))
                        ])
                    ] : null,
                    m(".row.form-group", [
                        m(".col-md-2", m("label","Afsluitingsdatum")),
                        m(".col-md-4", ff.dateInput("afsluit_d"))
                    ]),
                    m(".row.form-group", [
                        m(".col-md-12", ff.checkbox("aanpak_s", {readOnly: true}, "Dossier opgestart" , "1", "0"))
                    ]),
                    m(".row.form-group", [
                        (dossier_type === 'B') ?
                            m(".col-md-6.nopadding", [
                                m(".col-md-12", ff.checkbox("aanpak_onderzocht_s", "Integratie onderzocht" , "1", "0")),
                                m(".col-md-12", ff.textarea("aanpak_onderzocht_l", {rows: 4}))
                            ]) : null,
                        m(".col-md-6.nopadding", [
                            m(".col-md-12", m("label","Commentaar")),
                            m(".col-md-12",ff.textarea("commentaar", {rows: 4}))
                        ])
                    ])
                ]),
                ((_G_.model.isAdminIVS || _G_.model.isAdminArt46) ) ?
                    m(".row.col-md-12.floatRightContainer",[
                        m("button.btn.btn-primary.btn-sm", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Wijzigingen opslaan")
                    ]) : null
            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);
});
