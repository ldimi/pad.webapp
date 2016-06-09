/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "dossier/LoginLijstDialog",
    "ov/Model2",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (LoginLijstDialog, Model, events, ajax, fhf) {
    'use strict';

    var _comp, OfferteModel;

    OfferteModel = Model.extend({
        meta: Model.buildMeta([{
                name: "id",
                type: "int"
            }, {
                name: "inzender"
            }, {
                name: "totaal",
                type: "double"
            }, {
                name: "totaal_incl_btw",
                type: "double"
            }, {
                name: "status"
            }, {
                name: "organisatie_id",
                type: "int"
        }])
    });



    _comp = {};
    _comp.controller = function () {

        // lijst van OfferteModel opbouwen.
        this.modelLijst = _.map(_G_.model.offertes, function (offerte) {
            return new OfferteModel(offerte);
        });

        //this.organisaties_dd = null;

        this.loginLijstDialogCtrl = new LoginLijstDialog.controller();
    };
    _.extend(_comp.controller.prototype, {
        openLoginlijst: function(item) {
            events.trigger("LoginLijstDialog:open", item.get("organisatie_id"));
        }
    });

    _comp.view = function (ctrl) {
        //var ff;
        //
        //ff = fhf.get().setModel(ctrl.currentItem).setShowErrors(ctrl.showErrors());

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
                    return m("tr", [
                        m("td", m("a", {href: "/pad/s/bestek/" + off.get("bestek_id") + "/meetstaat/offertes/" + off.get("id") + "/"}, off.get("inzender"))),
                        m("td", off.str("totaal")),
                        m("td", off.str("totaal_incl_btw")),
                        m("td", off.str("status")),
                        m("td", off.str("organistie_id"))
                        //m("td", m("button", {onclick: _.bind(ctrl.openLoginlijst, ctrl, off)}, "--> Logins")),
                        //m("td", m("button", {onclick: _.bind(ctrl.verwijder, ctrl, off)}, "Verwijder"))
                    ]);
                }))
                //m("tr", [
                //    m("td", ff.select("organisatietype", ctrl.organisatietypes_dd)),
                //    m("td", ctrl.organisaties_dd ? ff.select("organisatie_id", ctrl.organisaties_dd) : null),
                //    m("td"),
                //    m("td", m("button", {onclick: _.bind(ctrl.toevoegen, ctrl)}, "Toevoegen"))
                //])
            ]),
            LoginLijstDialog.view(ctrl.loginLijstDialogCtrl)
        ]);

        //return m("div", "hello");
    };

    m.mount($("#jsviewContentDiv").get(0), _comp);
});
