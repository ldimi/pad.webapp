/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ , console */

define([
    "ov/mithril/dialogBuilder",
    "ov/mithril/formhelperFactory",
    "ov/mithril/ajax"
], function (dialogBuilder, fhf, ajax) {
    'use strict';

    var comp = {};

    comp.controller = function (options) {
        this.title = "Taak : nieuw Pad dossier";
        this.width = 380;

        this.currentItem = m.prop(null);

        this.taakAfsluiten = function () {
            var self = this;
            alert("TODO");
            // ajax.postJson({
            //     url: "/pad/s/dossier/nieuwPadDossier/" + self.taak.get("taak_key") + "/behandeld",
            //     content: null
            // }).then(function (response) {
            //     if (response && response.success) {
            //         options.taakAfgeslotenCb(self.taak);
            //         self.close();
            //     } else {
            //         alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
            //     }
            // });
        };

        this.preOpen = function (taak) {
            this.taak = taak;
        };

    };

    comp.view = function(ctrl) {
        var ff;
        ff = fhf.get().setModel(ctrl.taak);

        return [
            m("table.formlayout", [
                m("tbody", [
                    m("tr", [
                        m("td", "Dossier nr:"),
                        m("td", ff.input("dossier_nr", { class: 'input', readOnly: true }))
                    ])
                ])
            ]),
            m("button.#taakAfsluitenBtn", {onclick: ctrl.taakAfsluiten.bind(ctrl)}, "Ok en taak afsluiten"),
            m.trust("&nbsp;"),
            m("button.#annuleerBtn", {onclick: ctrl.close.bind(ctrl)}, "annuleer")
        ];
    };

    return dialogBuilder(comp);

});
