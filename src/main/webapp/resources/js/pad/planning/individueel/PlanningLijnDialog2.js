/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */

define([
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/dialogBuilder",
    "mithril",
    "underscore"
], function (events,ajax, fhf, dialogBuilder, m, _) {
    'use strict';

    var PlanningLijnDialog;

    PlanningLijnDialog = {};

    PlanningLijnDialog.controller = function () {
        events.on("planningLijnDialog:open", this.open.bind(this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        //events.on("dossierhouders:dataReceived", _.bind(this.close, this));

        this.title = "Editeer Planningslijn";
        //this.width = 750;
        //this.height = 500;

        this.showErrors = m.prop(false);

        this.preOpen = function (lijn, planningData) {
            this._lijn = lijn;
            this._planningData = planningData;
            this.showErrors(false);
        };
    };


    PlanningLijnDialog.view = function (ctrl) {
            var ff;
            if (!ctrl._lijn) { return null; }
            ff = fhf.get().setModel(ctrl._lijn).setShowErrors(ctrl.showErrors());

            return m("table.formlayout", [
                m("tr", [
                    m("td", { width: "80px" },"Dossier:"),
                    m("td", { width: "420px", colspan: "3" },
                        m( "div", { style: {width: "100%", backgroundColor: "#EEE"}} , [
                             ff.input("dossier_nr", {readOnly:  true, style: {border: "none", width: "48%"}}),
                             ff.input("dossier_gemeente_b", {readOnly:  true, style: {border: "none", width: "48%"}}),
                             ff.input("dossier_b", {readOnly:  true, style: {border: "none", width: "100%"}})
                        ])
                    )
                ])
            ]);
    };

    return dialogBuilder(PlanningLijnDialog);
});