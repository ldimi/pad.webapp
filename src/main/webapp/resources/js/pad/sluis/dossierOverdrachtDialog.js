/*jslint nomen: true, debug: true, browser: true */
/*global define: false, console: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "dropdown/dossierTypes",
    "common/dropdown/dossier/rechtsgronden",
    "common/dropdown/dossier/fasen",

    "dropdown/dossierhouders",
    "dropdown/programmaTypes",
    "dropdown/fusiegemeenten",

    "common/dropdown/ja_nee_dd",
    "common/dropdown/maanden_dd",
    "common/dropdown/dossier/doelgroepen_dd",
    "common/dropdown/dossier/screenBestekken_dd",
    "common/dropdown/dossier/overdrachtstatussen_dd",
    "common/dropdown/dossier/verontreinig_activiteiten_dd",
    "common/dropdown/dossier/instrumenten_dd",
    "common/dropdown/dossier/screening/risicos",

    "ov/mithril/formhelperFactory",
    "ov/mithril/dialogBuilder",
    "ov/mithril/ajax",
    "ov/events",
    "selectize"
], function (dossierTypes, rechtsgronden, fasen,
             dossierhouders, programmaTypes, fusiegemeenten,
             ja_nee_dd, maanden_dd, doelgroepen_dd, screenBestekken_dd, overdrachtstatussen_dd,
             verontreinig_activiteiten_dd, instrumenten_dd, risicos,
             fhf, dialogBuilder, ajax, event) {
    'use strict';
    var _comp;

    _comp = {};

    _comp.controller = function () {

        event.on("dossierOverdrachtDialog:open", this.open.bind(this));

        // na save (en ontvangen van de nieuwe gegevens) wordt de dialog automatisch gesloten.
        event.on("dossierOverdracht:dataReceived", this.close.bind(this));

        this.width = 800;

        this.dossier_types = _.filter(dossierTypes, function (type) { return type.value !== 'X'; });

        this.showErrors = m.prop(false);
    };
    _.extend(_comp.controller.prototype, {
        preOpen: function (item) {

            this.item = item;
            window._item = item;

            this.title = "Overdracht detail, id:  " + this.item.get("overdracht_id") + ", dossierNr: " + this.item.get("dossier_nr") ;

            this.showErrors(false);
        },

        bewaar: function () {
            this.showErrors(true);
            if (this.item.isDirty()) {
                if (!this.item.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }
                this._save(this.item);
            } else {
                $.notify("Er zijn geen aanpassingen te bewaren.");
            }
        },
        _save: function (item) {
            var status_crud;
            status_crud = item.get("status_crud");
            if (status_crud === 'R') {
                $.notify("Er zijn geen aanpassingen te bewaren.");
            }
            if (status_crud !== 'C' && status_crud !== 'U') {
                alert("item heeft een ongeldige status : " + status_crud);
                return;
            }
            event.trigger("dossierOverdracht:save", item);

        },
        maakZip: function () {
            ajax.postJson({
                url: '/pad/s/sluis/overdracht/maakZip',
                content: this.item.get("dossier_id")
            }).then(function (response) {
                if (response && response.success) {
                    $.notify("Zip bestand zal in batch aangemaakt worden.");
                } else {
                    alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                }
            });
        }

    });

    _comp.view = function (ctrl) {
        var vdom, ff, dossier_type;

        if (!ctrl.item) {
            return null;
        }

        dossier_type = ctrl.item.get("dossier_type");

        ff = fhf.get().setModel(ctrl.item).setShowErrors(ctrl.showErrors())
            .setDefaultAttrs({
                readOnly: function (veldNaam) {
                    if (!_G_.model.isAdminSluis) {
                        return true;
                    }
                    if ("status" === veldNaam ) {
                        return (ctrl.item.get("status_crud") === 'C');
                    }
                    if ("screening_jn" === veldNaam) {
                        return (ctrl.item.get("aanmaak_pad_jn") === "N");
                    }
                    if (_.contains(["status_start_d", "prioriteits_index"], veldNaam) ) {
                        return true;
                    }
                    if (_.contains(["dossier_type", "dossier_id_boa"], veldNaam) ) {
                        return (ctrl.item.get("status_crud") !== 'C');
                    }
                    return false;
                }
            });

        vdom = [
            m(".formlayout.container-fluid",
                m(".row.form-group", [
                    m(".col-xs-6", [
                        m(".col-xs-3", "Dossier Type:"),
                        m(".col-xs-9", ff.select("dossier_type", ctrl.dossier_types))
                    ]),
                    m(".col-xs-6", [
                        m(".col-xs-3", "Aanmaak Padnr:"),
                        m(".col-xs-3",  ff.select("aanmaak_pad_jn", ja_nee_dd)),
                        m(".col-xs-3", "Screening gewenst:"),
                        m(".col-xs-3",  ff.select("screening_jn", ja_nee_dd))
                    ])
                ]),
                (dossier_type === 'B')
                    ? [
                        m(".row.form-group", [
                            m(".col-xs-6", [
                                m(".col-xs-3", "Bodem Nr:"),
                                m(".col-xs-9",
                                    ff.input("dossier_id_boa", {
                                        onkeyup: function () {
                                            _.bind(ctrl.item.setSmeg_data, ctrl.item)("editing");
                                            _.bind(ctrl.item.set, ctrl.item)({"dossier_id_boa": this.value });
                                        },
                                        onblur: _.partial(_comp.view.onblur_dossier_id_boa, ctrl.item),
                                        disabled: (ctrl.item.get("status_crud") !== 'C')
                                    })
                                )
                            ])
                        ]),
                        m(".row.form-group", [
                            m(".col-xs-2"),
                            m(".col-xs-10", m("span", {style: {color: "red"}}, ctrl.item.error("smeg_data") ))
                        ]),
                        (ctrl.item.get_ivs_dossier_jn() === 'J')
                            ? m(".row.form-group",
                                m(".col-xs-12",
                                    m("span", { style: {color: "blue"}}, "Dit is reeds een PAD dossier: " + ctrl.item.get("dossier_nr"))))
                            : null,
                        (ctrl.item.get("smeg_id"))
                            ? [
                                m(".row.form-group",
                                    m(".col-xs-12",
                                        m("a", { href: _G_.model.mistral2Url + "?detailId=" + ctrl.item.get("smeg_id"), target: "_blank", tabindex: -1}, "Dossier Bodem: " + ctrl.item.get("smeg_naam")))
                                ),
                                m(".row.form-group", [
                                    m(".col-xs-2", {style: {width: "13%"}}, "Titel:"),
                                    m(".col-xs-10", {style: {width: "87%"}}, ff.input("dossier_b" ) )
                                ]),
                                m(".row.form-group", [
                                    m(".col-xs-6", [
                                        m(".col-xs-3", "Gemeente:"),
                                        m(".col-xs-9", ff.select("nis_id", fusiegemeenten))
                                    ]),
                                    m(".col-xs-6", [
                                        m(".col-xs-3", "Dossierhouder IVS:"),
                                        m(".col-xs-9", ff.select("doss_hdr_id", dossierhouders))
                                    ])
                                ]),
                                m(".row.form-group", [
                                    m(".col-xs-6", [
                                        m(".col-xs-3", "Rechtsgrond:"),
                                        m(".col-xs-9", ff.select("rechtsgrond_code", rechtsgronden[dossier_type] ))
                                    ]),
                                    m(".col-xs-6", [
                                        m(".col-xs-3", "Fase:"),
                                        m(".col-xs-9", ff.select("dossier_fase_id", fasen[dossier_type] ))
                                    ])
                                ]),
                                m(".row.form-group", [
                                    m(".col-xs-6", [
                                        m(".col-xs-3", "Programma:"),
                                        m(".col-xs-9", ff.select("programma_code", programmaTypes))
                                    ]),
                                    m(".col-xs-6", [
                                        m(".col-xs-3", "Doelgroep:"),
                                        m(".col-xs-9", ff.select("doelgroep_type_id", doelgroepen_dd))
                                    ])
                                ])
                            ] : null

                    ]
                    : [
                        m(".row.form-group", [
                            m(".col-xs-6", [
                                m(".col-xs-3", "Afval titel:"),
                                m(".col-xs-9", ff.input("dossier_b"))
                            ])
                        ]),
                        m(".row.form-group", [
                            m(".col-xs-6", [
                                m(".col-xs-3", "Gemeente:"),
                                m(".col-xs-9", ff.select("nis_id", fusiegemeenten))
                            ]),
                            m(".col-xs-6", [
                                m(".col-xs-3", "Dossierhouder IVS:"),
                                m(".col-xs-9", ff.select("doss_hdr_id", dossierhouders))
                            ])
                        ]),
                        m(".row.form-group", [
                            m(".col-xs-6", [
                                m(".col-xs-3", "Rechtsgrond:"),
                                m(".col-xs-9", ff.select("rechtsgrond_code", rechtsgronden[dossier_type] ))
                            ]),
                            m(".col-xs-6", [
                                m(".col-xs-3", "Fase:"),
                                m(".col-xs-9", ff.select("dossier_fase_id", fasen[dossier_type] ))
                            ])
                        ]),
                        m(".row.form-group", [
                            m(".col-xs-6", [
                                m(".col-xs-3", "Programma:"),
                                m(".col-xs-9", ff.select("programma_code", programmaTypes))
                            ]),
                            m(".col-xs-6", [
                                m(".col-xs-3", "Doelgroep:"),
                                m(".col-xs-9", ff.select("doelgroep_type_id", doelgroepen_dd))
                            ])
                        ])
                    ],


                m(".row",  { style: {borderTop: "1px solid lightgray", paddingTop: "10px"}}),
                m(".row.form-group", [
                    m(".col-xs-6", [
                        m(".col-xs-3", "Actueel risico:"),
                        m(".col-xs-9", ff.select("actueel_risico_id", risicos.actueel_dd))
                    ]),
                    m(".col-xs-6", [
                        m(".col-xs-3", "Beleidsmatige prioriteit:"),
                        m(".col-xs-9", ff.select("beleidsmatig_risico_id", risicos.beleidsmatig_dd))
                    ])
                ]),
                m(".row.form-group", [
                    m(".col-xs-6", [
                        m(".col-xs-3", "Integratie:"),
                        m(".col-xs-9", ff.select("integratie_risico_id", risicos.integratie_dd))
                    ]),
                    m(".col-xs-6", [
                        m(".col-xs-3", "Potentieel risico:"),
                        m(".col-xs-9", ff.select("potentieel_risico_id", risicos.potentieel_dd))
                    ])
                ]),
                m(".row.form-group", [
                    m(".col-xs-6", [
                            m(".col-xs-3", " Timing integratie:jaar"),
                            m(".col-xs-3", ff.input("timing_jaar")),
                            m(".col-xs-3", "maand"),
                            m(".col-xs-3", ff.select("timing_maand", maanden_dd))
                    ]),
                    m(".col-xs-6", [
                            m(".col-xs-3", "Prioriteits index:"),
                            m(".col-xs-3", ff.input("prioriteits_index"))
                    ])
                ]),
                (ctrl.item.get("status_crud") !== 'C') ?
                    m(".row.form-group", [
                        m(".col-xs-6", [
                            m(".col-xs-3", "Verontreinigende activiteit:"),
                            m(".col-xs-9", ff.selectize("activiteit_type_id_lijst", {multiple: true}, verontreinig_activiteiten_dd))
                        ]),
                        m(".col-xs-6", [
                            m(".col-xs-3", "Instrument:"),
                            m(".col-xs-9", ff.selectize("instrument_type_id_lijst", {multiple: true}, instrumenten_dd))
                        ])
                    ]) : null,

                (dossier_type === 'B')
                    ? [
                        m(".row",  { style: {borderTop: "1px solid lightgray", paddingTop: "10px"}}),
                        m(".row.form-group", [
                            m(".col-xs-6", [
                                m(".col-xs-3", "Raming BBO: prijs"),
                                m(".col-xs-3", ff.input("bbo_prijs")),
                                m(".col-xs-3", "Looptijd (maanden)"),
                                m(".col-xs-3", ff.input("bbo_looptijd"))
                            ])
                        ]),
                        m(".row.form-group", [
                            m(".col-xs-6", [
                                m(".col-xs-3", "met BSP?"),
                                m(".col-xs-3", ff.select("bsp_jn", ja_nee_dd))
                            ])
                        ]),
                        (ctrl.item.get("bsp_jn") === 'J')
                            ? m(".row.form-group", [
                                m(".col-xs-6", [
                                    m(".col-xs-3", "Raming BSP: prijs"),
                                    m(".col-xs-3", ff.input("bsp_prijs")),
                                    m(".col-xs-3", "Looptijd (maanden)"),
                                    m(".col-xs-3", ff.input("bsp_looptijd"))
                                ]),
                                m(".col-xs-6", [
                                    m(".col-xs-3", "Raming BSW: prijs"),
                                    m(".col-xs-3", ff.input("bsw_prijs")),
                                    m(".col-xs-3", "Looptijd (maanden)"),
                                    m(".col-xs-3", ff.input("bsw_looptijd"))
                                ])
                            ]) : null
                    ] : null,



                m(".row.form-group", {style: {borderTop: "1px solid lightgray", paddingTop: "10px"}}),
                m(".row.form-group", [
                    m(".col-xs-2", {style: {width: "13%"}}, "Commentaar:"),
                    m(".col-xs-10", {style: {width: "87%"}},
                        ff.textarea("commentaar", {
                            rows: 2,
                            maxlength: 500,
                            style: {width: "100%"}
                        })
                    )
                ]),
                m(".row.form-group", [
                    m(".col-xs-2", {style: {width: "13%"}}, "Screen bestek:"),
                    m(".col-xs-10", {style: {width: "87%"}}, ff.select("screen_bestek_id", screenBestekken_dd))
                ]),
                m(".row.form-group", [
                    m(".col-xs-6", [
                        m(".col-xs-3", "Status startdatum:"),
                        m(".col-xs-9", ff.dateInput("status_start_d"))
                    ]),
                    m(".col-xs-6", [
                        m(".col-xs-3", "Status:"),
                        m(".col-xs-9", ff.select("status", overdrachtstatussen_dd))
                    ])
                ])
            ),
            m(".floatRightContainer",
                (_G_.model.isAdminSluis) ? [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "annuleer"),
                    (ctrl.item.get("dossier_id_boa") !== null && ctrl.item.isValid("dossier_id_boa"))
                        ? m("button", {onclick: _.bind(ctrl.maakZip, ctrl)}, "Maak Zip")
                        : null,
                    (ctrl.item.get("zip_d") !== null)
                        ? m("a", {href: _G_.model.dmsUrl + "/share/page/repository#filter=path|" +
                                        "/Toepassingen/Mistral2/Dossier_screening/".replace(/\//g, "%2F") +
                                        ctrl.item.str("dossier_id_boa") , target: "_blank"},
                                 "naar zip folder")
                        : null
                ] : null
            )
        ];

        return vdom;
    };
    _comp.view.onblur_dossier_id_boa = function (item) {
        if (item.isValid("dossier_id_boa")) {
            _comp.laadSmegData(item);
        }
    };
    _comp.laadSmegData = function (item) {
        var dossier_id_boa = item.get("dossier_id_boa");
        if (dossier_id_boa !== null) {
            ajax.getJson({
                url: '/pad/s/sluis/smegdata/' + dossier_id_boa
            }).then(function (response) {
                if (response && response.success) {
                    item.setSmeg_data(response.result);
                } else {
                    alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
                }
            });
        }
    };


    return dialogBuilder(_comp);
});