/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "dossier/DossierModel",
    "common/dropdown/dossier/rechtsgronden",
    "common/dropdown/dossier/fasen",
    "dropdown/dossierhouders",
    "dropdown/programmaTypes",
    "dropdown/fusiegemeenten",
    "common/dropdown/maanden_dd",
    "common/dropdown/dossier/doelgroepen_dd",
    "common/dropdown/dossier/verontreinig_activiteiten_dd",
    "common/dropdown/dossier/instrumenten_dd",
    "common/dropdown/dossier/screening/risicos",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "selectize"
], function (DossierModel, rechtsgronden, fasen, dossierhouders, programmaTypes, fusiegemeenten,
             maanden_dd, doelgroepen_dd, verontreinig_activiteiten_dd, instrumenten_dd,
             risicos, ajax, fhf) {
    'use strict';

    var comp;

    comp = {
        controller: function () {
            this.dossier = new DossierModel({
                dossier: _G_.model.dossier,
                instrument_lijst: _G_.model.instrument_lijst,
                activiteit_lijst: _G_.model.activiteit_lijst
            });
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
                            if (attrName === "commentaar_bodem") {
                                return false;
                            }
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
                        m(".col-md-4", ff.input("dossier_nr", {readOnly : true})),
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
                                m(".col-md-12", ff.textarea("aanpak_onderzocht_l", {rows: 3}))
                            ]) : null,
                        m(".col-md-6.nopadding", [
                            m(".col-md-12", m("label","Commentaar")),
                            m(".col-md-12",ff.textarea("commentaar", {rows: 3}))
                        ])
                    ])
                ]),
                (dossier_type !== 'X') ?
                    [
                        (ctrl.dossier.get("id") !== null) ?
                            m("fieldset", [
                                m("legend", m.trust("&nbsp;")),
                                m(".row.form-group", [
                                    m(".col-md-2", m("label","Verontreinigende activiteit")),
                                    m(".col-md-4", ff.selectize("activiteit_type_id_lijst", {multiple: true}, verontreinig_activiteiten_dd)),
                                    m(".col-md-2", m("label", "Instrument")),
                                    m(".col-md-4", ff.selectize("instrument_type_id_lijst", {multiple: true}, instrumenten_dd))
                                ])
                            ]) : null,
                        m("fieldset", [
                            m("legend", m.trust("&nbsp;")),
                            m(".row.form-group", [
                                m(".col-md-2", m("label","Actueel risico")),
                                m(".col-md-4",  ff.select("actueel_risico_id", risicos.actueel_dd)),
                                m(".col-md-2", m("label","Beleidsmatige prioriteit")),
                                m(".col-md-4", ff.select("beleidsmatig_risico_id", risicos.beleidsmatig_dd))
                            ]),
                            m(".row.form-group", [
                                m(".col-md-2", m("label","Integratie")),
                                m(".col-md-4", ff.select("integratie_risico_id", risicos.integratie_dd)),
                                m(".col-md-2", m("label","Potentieel risico")),
                                m(".col-md-4", ff.select("potentieel_risico_id", risicos.potentieel_dd))
                            ]),
                            m(".row.form-group", [
                                m(".col-md-2", m("label","Timing integratie: jaar")),
                                m(".col-md-1", ff.input("timing_jaar")),
                                m(".col-md-2", m("label","maand")),
                                m(".col-md-1", ff.select("timing_maand", maanden_dd)),
                                m(".col-md-2", m("label","Prioriteits index")),
                                m(".col-md-1", ff.input("prioriteits_index", {readOnly: true})),
                                m(".col-md-3", ctrl.dossier.get("prioriteits_formule"))
                            ])
                        ])
                    ]: null,
                ((_G_.model.isAdminIVS || _G_.model.isAdminArt46) ) ?
                    m(".row.col-md-12.floatRightContainer",[
                        m("button.btn.btn-primary.btn-sm", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Wijzigingen opslaan")
                    ]) : null
            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);
});
