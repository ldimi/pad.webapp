/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, window, _G_, m */

define([
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/dialogBuilder"
], function (Model, events, ajax, fhf, dialogBuilder) {
    "use strict";

    var AdresContactModel, adresContactDialog;
    
    AdresContactModel = Model.extend({
        meta: Model.buildMeta([
            { name: "adres_id", type: "int" },
            { name: "contact_id", type: "int" },
            { name: "naam" },
            { name: "voornaam" },
            { name: "functie" },
            { name: "tel" },
            { name: "fax" },
            { name: "gsm" },
            { name: "email" },
            { name: "commentaar" },
            { name: "stop_s" },
            { name: "referentie_postcodes" }
        ]),

        enforceInvariants: function () {
        }
    });
    

    adresContactDialog = {
        controller: function() {
            this.title = "Contact persoon";
            this.width = 400;
            //this.height = 300;
    
            events.on("adresContactDialog:open", this.open.bind(this));

            this.preOpen = function (adresContact_data) {
                this.adresContact = new AdresContactModel(adresContact_data);
                this.showErrors = m.prop(false);
            };

            this.bewaar = function () {
                this.showErrors(true);

                if (this.adresContact.get("bestek_id") === null || this.adresContact.isDirty()) {
                    if (!this.adresContact.isValid()) {
                        $.notify("Er zijn validatie fouten.");
                        return;
                    }

                    ajax.postJson({
                        url: "/pad/s/adres/contact/save",
                        content: this.adresContact
                    }).then(function (response) {
                        if (response && response.success) {
                            window.location = "http://" + window.location.host + "/pad/s/adres/" + response.result;
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
            ff = fhf.get().setModel(ctrl.adresContact).setShowErrors(ctrl.showErrors());
            
            return m("div", {style: {align:"left"}}, [
                m("table.formLayout", [
                    m("tr", [
                        m("td", "Naam:"),
                        m("td", ff.input("naam", {maxlength: 120}))
                    ]),
                    m("tr", [
                        m("td", "Voornaam:"),
                        m("td", ff.input("voornaam", {maxlength: 120}))
                    ]),
                    m("tr", [
                        m("td", "Functie:"),
                        m("td", ff.input("functie", {maxlength: 120}))
                    ]),
                    m("tr", [
                        m("td", "Tel.:"),
                        m("td", ff.input("tel", {maxlength: 12}))
                    ]),
                    m("tr", [
                        m("td", "Fax:"),
                        m("td", ff.input("fax", {maxlength: 12}))
                    ]),
                    m("tr", [
                        m("td", "Gsm:"),
                        m("td", ff.input("gsm", {maxlength: 20}))
                    ]),
                    m("tr", [
                        m("td", "Email:"),
                        m("td", ff.input("email", {maxlength: 50}))
                    ]),
                    m("tr", [
                        m("td[colspan=2]", ff.checkbox("stop_s", "Niet actief"))
                    ]),
                    m("tr", [
                        m("td", "Commentaar:"),
                        m("td", ff.textarea("commentaar", {rows: 3, cols:90, maxlength: 1500 }))
                    ]),
                    m("tr", [
                        m("td", "Referentie postcodes:"),
                        m("td", ff.textarea("referentie_postcodes", {rows: 3, cols:90, maxlength: 1500 }))
                    ])
                ]),
                m("div.floatRightContainer", {style: { paddingTop: "10px"}}, [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)},
                        (ctrl.adresContact.get("contact_id") === null) ? "Nieuw contact toevoegen" : "Wijzigingen opslaan" )
                ])
            ]);
        }
    };

    return dialogBuilder(adresContactDialog);
});