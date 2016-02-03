/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, window, _G_, m */

define([
    "ov/mithril/ajax",
    "ov/Model",
    "ov/mithril/formhelperFactory",
    "ov/mithril/dialogBuilder"
], function (ajax, Model, fhf, dialogBuilder) {
    "use strict";

    var BestekModel, comp, aanmakenSAPDialog;

    BestekModel = Model.extend({
        meta: Model.buildMeta([
            {
                name: "bestek_id",
                type: "int"
            }, {
                name: "bestek_nr"
            }, {
                name: "dossier_id",
                type: "int",
                required: true
            }, {
                name: "dossier_nr",
                required: true
            }, {
                name: "dossier_type",
                required: true
            }, {
                name: "type_id",
                type: "int",
                required: true
            }, {
                name: "procedure_id",
                type: "int",
                required: true
            }, {
                name: "fase_id",
                type: "int",
                required: true
            }, {
                name: "dienst_id",
                type: "int",
                required: true
            }, {
                name: "omschrijving"
            }, {
                name: "commentaar"
            }, {
                name: "btw_tarief",
                type: "int",
                default: 21,
                required: true
            }, {
                name: "start_d",
                type: "date"
            }, {
                name: "stop_d",
                type: "date"
            }, {
                name: "afsluit_d",
                type: "date"
            }, {
                name: "verlenging_s"
            }, {
                name: "wbs_nr"
            }, {
                name: "screening_jn"
            }, {
                name: "screening_organisatie_id",
                type: "int"
            }, {
                name: "raamcontract_jn"
            }
        ]),

        enforceInvariants: function () {

            if (this.get("screening_jn") === "N" ) {
                this.attributes.screening_organisatie_id = null;
            }

        }
    });

    aanmakenSAPDialog = dialogBuilder({
        controller: function () {
            this.title = "Aanmaken Bestek in SAP";
            this.width = 300;

            this.preOpen = function (bestek) {
                this.bestek = bestek;
            };

            this.aanmaken = function () {
                var self = this;
                ajax.getJson({
                    url: "/pad/s/sap/getOrCreateBestek",
                    content: { id: self.bestek.get("bestek_id") }
                }).then(function (response) {
                    if (response && response.success) {
                        alert(" Wbs nummer voor bestek werd aangemaakt: " + response.result);
                        window.location = "http://" + window.location.host + "/pad/s/bestek/" + self.bestek.get("bestek_id") + "/basisgegevens";
                    } else {
                        alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                    }
                });
            };

        },
        view: function (ctrl) {
            var ff;
            if (!ctrl.bestek) {
                return;
            }
            ff = fhf.get().setModel(ctrl.bestek);
            return m("table.formLayout", [
                m("tr", [
                     m("td", "Bestek id:"),
                     m("td", ff.input("bestek_id", {readOnly: true}))
                ]),
                m("tr", [
                     m("td"),
                     m("td", m("button", {onclick: _.bind(ctrl.aanmaken, ctrl)}, "Aanmaken"))
                ])
            ]);
        }
    });


    comp = {
        controller: function() {
            this.btw_dd = [{value: 19, label: "19"}, {value: 21, label: "21"}];

            this.organisaties_dd = _.map(_G_.model.organisatiesVoorScreening_dd, _.identity);
            this.organisaties_dd.unshift({value: "", label: ""});

            this.bestek = new BestekModel(_G_.model.bestekDO);
            this.showErrors = m.prop(false);


            this.bewaar = function () {
                this.showErrors(true);

                if (this.bestek.get("bestek_id") === null || this.bestek.isDirty()) {
                    if (!this.bestek.isValid()) {
                        $.notify("Er zijn validatie fouten.");
                        return;
                    }

                    ajax.postJson({
                        url: "/pad/s/bestek/save",
                        content: this.bestek
                    }).then(function (response) {
                        if (response && response.success) {
                            window.location = "http://" + window.location.host + "/pad/s/bestek/" + response.result + "/basisgegevens";
                        } else {
                            alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                        }
                    });

                } else {
                    $.notify("Er zijn geen aanpassingen te bewaren.");
                }
            };

            this.aanmakenSAP = function () {
                this.aanmakenSAPCtrl.open(this.bestek);
            };

            this.aanmakenSAPCtrl = new aanmakenSAPDialog.controller();
        },
        view: function (ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.bestek).setShowErrors(ctrl.showErrors());
            return m("div", {style: {margin: "10px", align:"left", width: "1000px"}}, [
                m("table.formLayout", {width: "800px"}, [
                    m("tr", [
                         m("td", "Besteknummer:"),
                         m("td", ff.input("bestek_nr", {readOnly: true})),
                         m("td", {width: "30px", rowspan: 9})
                    ]),
                    m("tr", [
                        m("td", "Type:"),
                        m("td", ff.select("type_id", _G_.model.bestekBodemType_dd)),
                        m("td", "BTW-tarief (%):"),
                        m("td", ff.select("btw_tarief", ctrl.btw_dd))
                    ]),
                    m("tr", [
                        m("td", "Procedure:"),
                        m("td", ff.select("procedure_id", _G_.model.bestekBodemProcedure_dd)),
                        m("td", "Datum start:"),
                        m("td", ff.dateInput("start_d"))
                    ]),
                    m("tr", [
                        m("td", "Fase:"),
                        m("td", ff.select("fase_id", _G_.model.bestekBodemFase_dd)),
                        m("td", "Datum stop:"),
                        m("td", ff.dateInput("stop_d"))
                    ]),
                    m("tr", [
                        m("td", {colspan: 2}, ff.checkbox("raamcontract_jn", {disabled: true}, "Raamcontract", "J", "N")),
                        m("td", "Datum afsluiting:"),
                        m("td", ff.dateInput("afsluit_d"))
                    ]),
                    m("tr", [
                        m("td", {colspan: 2}, ff.checkbox("verlenging_s", null, "Verlenging", "1", "0"))
                    ]),
                    m("tr", [
                        m("td", ff.checkbox("screening_jn", {disabled: !_G_.model.isAdminArt46 }, "Screening", "J", "N")),
                        (ctrl.bestek.get("screening_jn") === "J") ?
                            m("td", {colspan: 3}, ff.select("screening_organisatie_id", {readOnly: !_G_.model.isAdminArt46 }, ctrl.organisaties_dd)) : null
                    ]),
                    m("tr", [
                         m("td", "SAP wbs nummer:"),
                         m("td", ff.input("wbs_nr", {readOnly: true}))
                    ]),
                    m("tr", [
                         m("td", {colspan: 2}, [
                             "Omschrijving:",
                             m("br"),
                             ff.textarea("omschrijving", {rows: 10, cols:55 })
                         ]),
                         m("td", {colspan: 2}, [
                             "Opmerkingen:",
                             m("br"),
                             ff.textarea("commentaar", {rows: 10, cols:55 })
                         ])
                    ])
                ]),
                (_G_.model.isEditAllowed) ?
                    m("div.floatRightContainer", {style: {width: "800px", paddingTop: "10px"}}, [
                        (ctrl.bestek.get("bestek_id") !== null && ctrl.bestek.get("wbs_nr") === null) ?
                            m("button", {onclick: _.bind(ctrl.aanmakenSAP, ctrl)}, "Aanmaken in SAP") : null ,
                        m("button", {onclick: _.bind(ctrl.bewaar, ctrl)},
                            (ctrl.bestek.get("bestek_id") === null) ? "Nieuw bestek toevoegen" : "Wijzigingen opslaan" )
                    ]) : null,

                aanmakenSAPDialog.view(ctrl.aanmakenSAPCtrl)
            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

});