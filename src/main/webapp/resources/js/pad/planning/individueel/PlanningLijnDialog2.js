/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */

define([
    "dropdown/planning/fasen",
    "dropdown/planning/detailFasen",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/dialogBuilder",
    "mithril",
    "underscore"
], function (fasen, detailFasen, events ,ajax, fhf, dialogBuilder, m, _) {
    'use strict';

    var PlanningLijnDialog, dateConfig;

    dateConfig = function (el, isInitialized) {
        if (!isInitialized) {
            $(el).datepicker({
                changeMonth: true,
                changeYear: true
            });
        }
    };


    PlanningLijnDialog = {};

    PlanningLijnDialog.controller = function () {
        events.on("planningLijnDialog:open", this.open.bind(this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        //events.on("dossierhouders:dataReceived", _.bind(this.close, this));

        this.title = "Editeer Planningslijn";
        //this.width = 750;
        //this.height = 500;
        
        this.showErrors = m.prop(false);

    };
    _.extend(PlanningLijnDialog.controller.prototype, {
        preOpen: function (lijn, planningData) {
            this._lijn = lijn;
            this._planningData = planningData;
            this.showErrors(false);
        },
        bewaar: _.noop,
        voegDeelopdrToe: _.noop
    });


    PlanningLijnDialog.view = function (ctrl) {
            var ff, dossier_type;
            if (!ctrl._lijn) { return null; }
            ff = fhf.get().setModel(ctrl._lijn).setShowErrors(ctrl.showErrors());

            dossier_type = ctrl._lijn.get("dossier_type");
            
            return m("div", [
                m("table.formlayout", [
                    m("tr", [
                        m("td", { width: "80px" },"Dossier:"),
                        m("td", { width: "420px", colspan: "3" },
                            m( "div", { style: {width: "100%", backgroundColor: "#EEE"}} , [
                                 ff.input("dossier_nr", {readOnly:  true, style: {border: "none", width: "48%"}}),
                                 ff.input("dossier_gemeente_b", {readOnly:  true, style: {border: "none", width: "48%"}}),
                                 ff.input("dossier_b", {readOnly:  true, style: {border: "none", width: "100%"}})
                            ])
                        )
                    ]),
                    m("tr", [
                        m("td", "Fase:"),
                        m("td", {colspan: "3" }, ff.select("fase_code", fasen[dossier_type]))
                    ]),
                    m("tr", [
                        m("td", "Fase detail:"),
                        m("td", {colspan: "3" }, ff.select("fase_detail_code", detailFasen.dd(ctrl._lijn.get("fase_code"))))
                    ]),
                    m("tr", [
                        m("td", { width: "80px" }, "Gepland Datum:"),
                        m("td", { width: "170px" }, ff.dateInput("igb_d", {config: dateConfig})),
                        m("td", { width: "80px" }, "Benut Datum:"),
                        m("td", { width: "170px" }, ff.dateInput("ibb_d", { readOnly: true, style: { color: "blue"}}))
                    ]),
                    m("tr", [
                        m("td", "actie:"),
                        m("td", {colspan: "3" }, ff.input("actie_code"))
                    ]),
                    m("tr", [
                        m("td", "Contract:"),
                        m("td", {colspan: "3" }, ff.input("contract_id"))
                    ]),
                    m("tr", [
                        m("td", "Bestek:"),
                        m("td", {colspan: "3" }, ff.input("bestek_id"))
                    ]),
                    m("tr", [
                        m("td", "Benut bestek:"),
                        m("td", {colspan: "3" }, ff.input("c_bestek_omschrijving", { readOnly: true, style: { color: "blue"}}))
                    ]),
                    m("tr", [
                        m("td", "Gepland Bedrag:"),
                        m("td", ff.input("ig_bedrag", {maxlength: "40"})),
                        m("td", "Benut Bedrag:"),
                        m("td", ff.input("ib_bedrag", { readOnly: true, style: { color: "blue"}}))
                    ]),
                    m("tr", [
                        m("td", "Commentaar:")
                    ]),
                    m("tr", [
                        m("td", {colspan: "4" }, ff.textarea("commentaar", { rows: 5, maxlength: 1000, style: { width: "500px"}}))
                    ])
                ]),
                m("div", [
                    m("button", {onclick: _.bind(ctrl.voegDeelopdrToe, ctrl)}, "Selecteer planningsitem"),
                    m("br"),
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "Annuleer")
                ])
            ]);
    };

    return dialogBuilder(PlanningLijnDialog);
});