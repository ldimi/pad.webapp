/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, window, _G_, m */

define([
    "adres/adresContactDialog",
    "ov/Model",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "selectize"
], function (adresContactDialog, Model, ajax, fhf) {
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
            var postcode, fusiegemeente, gemeente;
            if (this.hasChanged("postcode")) {
                postcode = this.get("postcode");
                gemeente = _(_G_.model.gemeentenLijst)
                    .find( function (item) {
                        return item.postcode === postcode;
                    });
                this.attributes.gemeente = gemeente.fusiegemeente;
            }
            if (this.hasChanged("gemeente")) {
                fusiegemeente = this.get("gemeente");
                gemeente = _(_G_.model.gemeentenLijst)
                    .find( function (item) {
                        return item.fusiegemeente === fusiegemeente;
                    });
                this.attributes.postcode = gemeente.postcode;
            }
        }
    });                               
    
    _.each(_G_.model.gemeentenLijst, function(item) {
        item.label = item.postcode + ' ' + item.fusiegemeente + ' ' + item.deelgemeente;
    });
    

    comp = {
        controller: function() {
            this.adres = new AdresModel(_G_.model.adresDO);
            this.showErrors = m.prop(false);
                        
            this.contactDialogCtrl = new adresContactDialog.controller();
            
            this.postcode_dd = _(_G_.model.gemeentenLijst)
                .map(function (item) {
                    return {
                        value: item.postcode,
                        label: item.postcode + ' ' + item.fusiegemeente + ' ' + item.deelgemeente
                    };
                });

            this.gemeente_dd = _(_G_.model.gemeentenLijst)
                .map(function (item) {
                    return {
                        value: item.fusiegemeente,
                        label: item.postcode + ' ' + item.fusiegemeente + ' ' + item.deelgemeente
                    };
                });

            this.openContact = function (contact) {
                this.contactDialogCtrl.open(contact);
                return false;
            };

            this.bewaar = function () {
                this.showErrors(true);

                if (this.adres.get("adres_id") === null || this.adres.isDirty()) {
                    if (!this.adres.isValid()) {
                        $.notify("Er zijn validatie fouten.");
                        return;
                    }

                    ajax.postJson({
                        url: "/pad/s/adres/save",
                        content: this.adres
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
            ff = fhf.get().setModel(ctrl.adres).setShowErrors(ctrl.showErrors());
            return m("div", {style: {margin: "10px", align:"left", width: "1000px"}}, [
                m("table.formLayout", {width: "800px"}, [
                    m("tr", [
                         m("td", "Naam / Bedrijf:"),
                         m("td", ff.input("naam", {maxlength: 120})),
                         m("td", "Tel.:"),
                         m("td", ff.input("tel", {maxlength: 12}))
                    ]),
                    m("tr", [
                        m("td", "Voornaam / Afdeling:"),
                        m("td", ff.input("voornaam", {maxlength: 120})),
                        m("td", "Fax:"),
                        m("td", ff.input("fax", {maxlength: 12}))
                    ]),
                    m("tr", [
                        m("td", "Straat & nr.:"),
                        m("td", ff.input("straat", {maxlength: 40})),
                        m("td", "Gsm:"),
                        m("td", ff.input("gsm", {maxlength: 20}))
                    ]),
                    m("tr", [
                        m("td", "Postcode:"),
                        m("td", m("input", {
                                    type: "text",
                                    maxlength: 8,
                                    value: ctrl.adres.get("postcode"),
                                    config : function (el, initialised, context) {
                                        if (!initialised) {
                                            $(el).autocomplete({
                                                source: ctrl.postcode_dd,
                                                minLength: 2,
                                                autoFocus: true ,
                                                select: function(event, ui) {
                                                    // prevent autocomplete from updating the textbox
                                                    event.preventDefault();
                                                    console.log("select");
                                                    ctrl.adres.set("postcode", ui.item.value);
                                                    m.redraw();
                                                }
                                            });
                                        }
                                    }
                        })),
                        m("td", "Email:"),
                        m("td", ff.input("email", {maxlength: 50}))
                    ]),
                    m("tr", [
                        m("td", "Gemeente:"),
                        m("td", m("input", {
                                    type: "text",
                                    maxlength: 8,
                                    value: ctrl.adres.get("gemeente"),
                                    config : function (el, initialised, context) {
                                        if (!initialised) {
                                            $(el).autocomplete({
                                                source: ctrl.gemeente_dd,
                                                minLength: 2,
                                                autoFocus: true ,
                                                select: function(event, ui) {
                                                    // prevent autocomplete from updating the textbox
                                                    event.preventDefault();
                                                    console.log("select");
                                                    ctrl.adres.set("gemeente", ui.item.value);
                                                    m.redraw();
                                                }
                                            });
                                        }
                                    }
                        })),
                        m("td", "Website:"),
                        m("td", ff.input("website", {maxlength: 100}))
                    ]),
                    m("tr", [
                        m("td", "Land:"),
                        m("td", ff.select("land", _G_.model.landen)),
                        m("td", "Type:"),
                        m("td", ff.select("type_id", _G_.model.adrestypes))
                    ]),
                    m("tr", [
                        m("td[colspan=2]", ff.checkbox("maatsch_zetel", "Maatschappelijke zetel")),
                        m("td[colspan=2]", ff.checkbox("stop_s", "Niet actief"))
                    ]),
                    m("tr", [
                        m("td", "Referentie postcodes:"),
                        m("td[colspan=3]", ff.textarea("referentie_postcodes", {rows: 3, cols:90, maxlength: 1500 }))
                    ])
                ]),
                m("div.floatRightContainer", {style: {width: "800px", paddingTop: "10px"}}, [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)},
                        (ctrl.adres.get("adres_id") === null) ? "Nieuw adres toevoegen" : "Wijzigingen opslaan" )
                ]),
                
                m("h4", "Contacten"),
                
                m("table.lijst1",[
                    m("thead", [
                        m("tr", [
                            m("th", "Naam"),
                            m("th", "Telefoon"),
                            m("th", "Fax"),
                            m("th", "GSM"),
                            m("th", "Email"),
                            m("th", "Functie"),
                            m("th", "Commentaar"),
                            m("th", "Actief?"),
                            m("th", "")
                        ])
                    ]),
                    m("tbody",
                        _.map(_G_.model.adresContactenlijst, function (contact) {
                            return m("tr", [
                                m("td",
                                    m("a", { href: "#", onclick: ctrl.openContact.bind(ctrl, contact) }, contact.naam_voornaam)
                                ),
                                m("td", contact.tel),
                                m("td", contact.fax),
                                m("td", contact.gsm),
                                m("td", contact.email),
                                m("td", contact.functie),
                                m("td", contact.commentaar),
                                m("td", contact.actief_jn),
                                m("td", "")
                            ]);
                        })
                    )
                ]),
                
                adresContactDialog.view(ctrl.contactDialogCtrl)
            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

});