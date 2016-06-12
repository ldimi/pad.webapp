/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "budget/meetstaat/LoginLijstDialog",
    "ov/Model2",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (LoginLijstDialog, Model, events, ajax, fhf) {
    'use strict';

    var _comp, OfferteModel;

    OfferteModel = Model.extend({
        meta: Model.buildMeta([
            { name: "id", type: "int" },
            { name: "inzender" },
            { name: "totaal", type: "double" },
            { name: "totaal_incl_btw", type: "double" },
            { name: "status" },
            { name: "organisatie_id", type: "int"}
        ]),
        enforceInvariants: function () {
            if (this.hasChanged("organisatie_id")) {
                window.location = "http://" + window.location.host +
                                  "/pad/s/bestek/" + this.get("bestek_id")  +
                                  "/meetstaat/offerte/" + this.get("id") +
                                  "/koppelOrganisatie?organisatie_id=" + this.str("organisatie_id");
            }
        }
    });


    _comp = {};
    _comp.controller = function () {

        // lijst van OfferteModel opbouwen.
        this.modelLijst = _.map(_G_.model.offertes, function (offerte) {
            return new OfferteModel(offerte);
        });

        this.organisaties_dd = _.chain(_G_.model.organisaties_dd)
            .map(function(org) {
                org.value = org.organisatie_id;
                return org;
            })
            .unshift({value: "", label: "" })
            .value();

        this.loginLijstDialogCtrl = new LoginLijstDialog.controller();
    };
    _.extend(_comp.controller.prototype, {
        openLoginlijst: function(offerte) {
            events.trigger("LoginLijstDialog:open", offerte.get("organisatie_id"));
        }
    });

    _comp.view = function (ctrl) {
        var bestekDO = _G_.model.bestekDO;
        
        return m("div", {style: {margin: "10px", "background-color": "white"}}, [
            m("table",[
                m("thead", [
                    m("th", "Inzender"),
                    m("th", "Totaal Excl BTW"),
                    m("th", "Totaal Incl BTW"),
                    m("th", "Status"),
                    m("th", "Organisatie")
                ]),
                m("tbody", _.map(ctrl.modelLijst, function(off) {
                    var ff = fhf.get().setModel(off);
                    return m("tr", [
                        m("td", m("a", {href: "/pad/s/bestek/" + off.get("bestek_id") + "/meetstaat/offertes/" + off.get("id") + "/"}, off.get("inzender"))),
                        m("td", off.str("totaal")),
                        m("td", off.str("totaal_incl_btw")),
                        m("td", off.str("status")),
                        m("td", ff.select("organisatie_id", ctrl.organisaties_dd)),
                        off.get("organisatie_id")
                            ? m("td", m("button", {onclick: _.bind(ctrl.openLoginlijst, ctrl, off), class: "inputbtn"}, "Logins"))
                            : null
                    ]);
                }))
            ]),
            m("br"),
            m("form", { method: "get", action: "/pad/s/bestek/meetstaat/offertes/export/draftOffertes-" + bestekDO.bestek_id + ".xls", target: "_blank"},
                m("button", { type: "submit", class: "inputbtn"}, "Exporteren voor rekenkundige controle: Excel")),
            m.trust("Rapport financi&euml;le controle:"),
            m("br"),
            m("br"),
            bestekDO.controle_dms_id
                ? m("a", {href: bestekDO.controle_dms_folder + "/" + bestekDO.controle_dms_filename, target: "_blank"}, [
                    bestekDO.controle_dms_filename,
                    m("img", {src: "resources/images/AlfrescoLogo32.png", width: 16, height: 16, border: 0, alt: "Brief bekijken", title: "Brief bekijken"})
                  ])
                : null,
            m("br"),
            m("br"),
            m("form", { action: "/pad/s/bestek/meetstaat/offertes/uploaden", enctype: "multipart/form-data", method: "post"}, [
                m("input", {type: "hidden", value: bestekDO.bestek_id, name: "bestek_id"}),
                m("input", {type: "file", name: "file", onchange: function(){ this.form.submit();} })
            ]),
            LoginLijstDialog.view(ctrl.loginLijstDialogCtrl)
        ]);

    };

    m.mount($("#jsviewContentDiv").get(0), _comp);
});
