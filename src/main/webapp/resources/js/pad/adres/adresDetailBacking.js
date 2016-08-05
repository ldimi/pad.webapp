/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, window, _G_, m */

define([
    "ov/mithril/ajax",
    "ov/Model",
    "ov/mithril/formhelperFactory"
], function (ajax, Model, fhf) {
    "use strict";

    var AdresModel, comp;

    AdresModel = Model.extend({
        meta: Model.buildMeta([
            { name: "adres_id", type: "int" },
            { name: "naam" },
            { name: "voornaam" },
            { name: "straat" },
            { name: "postcode" },
            { name: "gemeente" },
            { name: "land" },
            { name: "tel" },
            { name: "fax" },
            { name: "email" },
            { name: "website" },
            { name: "stop_s" },
            { name: "maatsch_zetel" },
            { name: "type_id", type: "int" },
            { name: "gsm" },
            { name: "referentie_postcodes" }
            
        ]),

        enforceInvariants: function () {
        }
    });

    comp = {
        controller: function() {
            this.adres = new AdresModel(_G_.model.adresDO);
            this.showErrors = m.prop(false);


            this.bewaar = function () {
                this.showErrors(true);

                if (this.adres.get("bestek_id") === null || this.adres.isDirty()) {
                    if (!this.adres.isValid()) {
                        $.notify("Er zijn validatie fouten.");
                        return;
                    }

                    ajax.postJson({
                        url: "/pad/s/adres/save",
                        content: this.adres
                    }).then(function (response) {
                        if (response && response.success) {
                            window.location = "http://" + window.location.host + "/pad/s/adres/" + response.result + "/basisgegevens";
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
            var ff;
            ff = fhf.get().setModel(ctrl.adres).setShowErrors(ctrl.showErrors());
            return m("div", {style: {margin: "10px", align:"left", width: "1000px"}}, [
                m("table.formLayout", {width: "800px"}, [
                    m("tr", [
                         m("td", "Naam / Bedrijf:"),
                         m("td", ff.input("naam")),
                         m("td", "Tel.:"),
                         m("td", ff.input("tel"))
                    ]),
                    m("tr", [
                        m("td", "Voornaam / Afdeling:"),
                        m("td", ff.input("voornaam")),
                         m("td", "Fax:"),
                         m("td", ff.input("fax"))
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
                             ff.textarea("omschrijving", {rows: 10, cols:55, maxlength: 750 })
                         ]),
                         m("td", {colspan: 2}, [
                             "Opmerkingen:",
                             m("br"),
                             ff.textarea("commentaar", {rows: 10, cols:55, maxlength: 750 })
                         ])
                    ])
                ]),
                (_G_.model.isEditAllowed) ?
                    m("div.floatRightContainer", {style: {width: "800px", paddingTop: "10px"}}, [
                        (ctrl.adres.get("bestek_id") !== null && ctrl.adres.get("wbs_nr") === null) ?
                            m("button", {onclick: _.bind(ctrl.aanmakenSAP, ctrl)}, "Aanmaken in SAP") : null ,
                        m("button", {onclick: _.bind(ctrl.bewaar, ctrl)},
                            (ctrl.adres.get("bestek_id") === null) ? "Nieuw bestek toevoegen" : "Wijzigingen opslaan" )
                    ]) : null

            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

});