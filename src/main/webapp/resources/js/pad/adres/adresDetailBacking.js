/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, window, _G_, m */

define([
    "ov/mithril/ajax",
    "ov/Model",
    "ov/mithril/formhelperFactory"
], function (ajax, Model, fhf) {
    "use strict";

    var AdresModel, comp;
    
    _G_.model.adrestypes.unshift({value: "", label: ""});

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
                        m("td", "Straat & nr.:"),
                        m("td", ff.input("straat")),
                        m("td", "Gsm:"),
                        m("td", ff.input("gsm"))
                    ]),
                    m("tr", [
                        m("td", "Postcode:"),
                        m("td", ff.input("postcode")),
                        m("td", "Email:"),
                        m("td", ff.input("email"))
                    ]),
                    m("tr", [
                        m("td", "Gemeente:"),
                        m("td", ff.input("gemeente")),
                        m("td", "Website:"),
                        m("td", ff.input("website"))
                    ]),
                    m("tr", [
                        m("td", "Land:"),
                        m("td", ff.select("land", _G_.model.landen)),
                        m("td", "Type:"),
                        m("td", ff.select("type_id", _G_.model.adrestypes))
                    ]),
                    m("tr", [
                        m("td", "Voornaam / Afdeling:"),
                        m("td", ff.input("voornaam")),
                        m("td", "Fax:"),
                        m("td", ff.input("fax"))
                    ])
                ]),
                m("div.floatRightContainer", {style: {width: "800px", paddingTop: "10px"}}, [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)},
                        (ctrl.adres.get("adres_id") === null) ? "Nieuw adres toevoegen" : "Wijzigingen opslaan" )
                ])

            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

});