/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */

define([
    "planning/individueel/PlanningLijnModel",
    "dropdown/planning/fasen",
    "dropdown/planning/detailFasen",
    "dropdown/planning/budgetCodes",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/dialogBuilder",
    "mithril",
    "underscore"
], function (PlanningLijnModel, fasen, detailFasen, budgetCodes, events ,ajax, fhf, dialogBuilder, m, _) {
    'use strict';

    var PlanningLijnDialog, dateConfig, actie_dd;

    actie_dd = (function() {
        var dd = [
            { value: "N_B",
              label: (_G_.model.RAAM_OF_GROEP)
                        ? (_G_.model.RAAM_OF_GROEP === "RAAM")
                            ? "Nieuw raamcontract bestek"
                            : "Nieuw gegroepeerd bestek"
                        : "Nieuw enkelvoudig bestek"
            },
            { value: "H_B",
              label: (_G_.model.RAAM_OF_GROEP) ? "Bestaand bestek" : "Bestaand enkelvoudig bestek"
            }
        ];
        if (!_G_.model.RAAM_OF_GROEP) {
            dd.unshift({ value: "", label: "" });
            dd.push({ value: "GGO", label: "Gegroepeerde opdracht" });
            dd.push({ value: "RC", label: "Raamcontract" });
        }
        return dd;
    }());


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

        events.on("planningLijnModel.actie_code:changed", function() {
            this.fetchBestekkenDD();
            this.filterContractenDD();
        }.bind(this));

        events.on("planningLijnModel.contract_id:changed", this.fetchBestekkenDD.bind(this));

        this.title = "Editeer Planningslijn";
        this.width = 550;

        this.showErrors = m.prop(false);
    };
    _.extend(PlanningLijnDialog.controller.prototype, {
        preOpen: function (lijn, planningData) {
            this._lijn = lijn;
            _G_._CURRENT_LIJN = lijn;
            this._planningData = planningData;

            this.fetchBestekkenDD();
            this.filterContractenDD();

            this.showErrors(false);
        },
        bewaar: function() {
            this.showErrors(true);

            if (!this._lijn.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }
            if (!this._lijn.isDirty()) {
                $.notify("De lijn is niet gewijzigd.");
                return;
            }
            this._bewaar();
        },

        _bewaar: function () {

            ajax.postJson({
                url: "/pad/s/planning/bewaar",
                content: this._lijn.clone()
            }).then(function (response) {
                var savedLijn;
                savedLijn = new PlanningLijnModel(response);

                if (this._lijn.get("status_crud") === 'C') {
                    // eventueel lijn verwijderen
                    this._planningData.lijnen = _.reject(this._planningData.lijnen, function (lijn) {
                        return (lijn.get("dossier_id") === savedLijn.get("dossier_id") &&
                                lijn.get("ig_bedrag") === null &&
                                lijn.get("status_crud") === "C" &&
                                (lijn.get("fase_code") === null ||
                                 lijn.get("fase_code") === savedLijn.get("fase_code"))
                               );
                    }, this);
                    // deze nieuwe lijn toevoegen
                    this._planningData.lijnen.splice(this._planningData.selectedLijnIndex, 0, savedLijn);
                } else {
                    this._planningData.lijnen.splice(this._planningData.selectedLijnIndex, 1, savedLijn);
                }

                $.notify("De lijn is bewaard.");
                events.trigger("planning.lijnen:refresh", this._planningData.lijnen);
                this.close();
            }.bind(this));

        },

        voegDeelopdrToe: function() {
            var actie_code, fase_code, bestek_id;

            if (_G_.model.RAAM_OF_GROEP === "GROEP" && this._lijn.get("dossier_is_raamcontract_jn") === 'N') {
                actie_code = this._lijn.get("actie_code");
                fase_code = this._lijn.get("fase_code");
                bestek_id = this._lijn.get("bestek_id");

                if (actie_code === "N_B" && fase_code !== null) {
                    events.trigger("faseDetailsDialog:open",this._lijn.get("dossier_id"), this._lijn.get("fase_code"), function(item) {
                        var omschrijving, commentaar_org;
                        commentaar_org = this._lijn.str("commentaar");
                        omschrijving = "( " + item.get("dossier_nr") + " " + item.get("dossier_b") +
                                        " : " + item.get("ig_bedrag") + " )";
                        if (commentaar_org !== "") {
                            omschrijving = "\n" + omschrijving;
                        }
                        this._lijn.set("commentaar", commentaar_org + omschrijving);
                    }.bind(this));
                } else if (actie_code === "H_B" && bestek_id !== null) {
                    events.trigger("bestekDetailsDialog:open",this._lijn.get("bestek_id"), this._lijn.get("bestek_nr"), function(item) {
                        var omschrijving, commentaar_org;
                        commentaar_org = this._lijn.str("commentaar");
                        omschrijving = "( " + item.get("dossier_nr") + " " + item.get("dossier_b") + ", " + item.get("bestek_nr") +
                                        " : " + item.get("ig_bedrag") + " )";
                        if (commentaar_org !== "") {
                            omschrijving = "\n" + omschrijving;
                        }
                        this._lijn.set("commentaar", commentaar_org + omschrijving);
                    }.bind(this));
                }
            }
        },

        fetchBestekkenDD: function () {
            var actie_code, contract_id;
            actie_code = this._lijn.get("actie_code");
            contract_id = this._lijn.get("contract_id");

            if (actie_code === "N_B") {
                this.bestekken_dd = [];
            } else if (actie_code === "H_B") {
                this.fetchBestekkenDDByDossierId(this._lijn.get("dossier_id"));
            } else if (contract_id === "") {
                this.bestekken_dd = [];
            } else {
                this.fetchBestekkenDDByDossierId(contract_id);
            }
        },

        fetchBestekkenDDByDossierId: function (dossier_id) {
            var self = this;
            if (dossier_id !== null) {
                ajax.postJson({
                    url: "/pad/s/planning/getBestekkenByDossier",
                    content: dossier_id
                }).then(function (bestekken_dd) {
                    var actie_code;
                    actie_code = self._lijn.get("actie_code");
                    if (actie_code === "GGO") {
                        bestekken_dd.unshift({
                            value: "",
                            label: "via nieuw gegroepeerd bestek"
                        });
                    } else if (actie_code === "RC") {
                        bestekken_dd.unshift({
                            value: "",
                            label: "via nieuw raamcontract bestek"
                        });
                    } else {
                        bestekken_dd.unshift({
                            value: "",
                            label: ""
                        });
                    }
                    self.bestekken_dd = bestekken_dd;
                });
            }
            this.bestekken_dd = [];
        },

        filterContractenDD: function () {
            var actie_code = this._lijn.get("actie_code"),
                raamcontracten_jn;
            if (actie_code === "N_B" || actie_code === "H_B") {
                raamcontracten_jn = "GEEN";
            } else if (actie_code === "RC") {
                raamcontracten_jn = "J";
            } else {
                raamcontracten_jn = "N";
            }
            this.contractenDD = _.filter(_G_.contractenDD,  function (option) {
                return (option.value === "" || option.raamcontract_jn === raamcontracten_jn);
            });


        }

    });


    PlanningLijnDialog.view = function (ctrl) {
        var ff, pl_lijn, dossier_type;

        pl_lijn = ctrl._lijn;
        if (!pl_lijn) { return null; }

        ff = fhf.get().setModel(pl_lijn).setShowErrors(ctrl.showErrors())
            .setDefaultAttrs({
                readOnly: function (veldNaam) {
                    if (_.contains(["dossier_nr", "dossier_gemeente_b", "dossier_b",
                                    "ibb_d", "c_bestek_omschrijving", "ib_bedrag"], veldNaam) ) {
                        return true;
                    }
                    if (_.contains(["commentaar", "igb_d"], veldNaam) ) {
                        return false;
                    }
                    if (pl_lijn.get("ib_bedrag") || pl_lijn.get("c_isReedsGekoppeld")) {
                        return true;
                    }
                    if (veldNaam === "de_budget_code") {
                        return !_G_.model.isAdminArt46;
                    }
                    return false;
                }
            });

        dossier_type = pl_lijn.get("dossier_type");

        return m("div", [
            m("table.formlayout", [
                m("tr", [
                    m("td", { width: "80px" },"Dossier:"),
                    m("td", { width: "420px", colspan: "3" },
                        m( "div", { style: {width: "100%", backgroundColor: "#EEE"}} , [
                             ff.input("dossier_nr", { style: {border: "none", width: "48%"}}),
                             ff.input("dossier_gemeente_b", { style: {border: "none", width: "48%"}}),
                             ff.input("dossier_b", { style: {border: "none", width: "100%"}})
                        ])
                    )
                ]),
                m("tr", [
                    m("td", "Fase:"),
                    m("td", { width: "170px" }, ff.select("fase_code", fasen[dossier_type])),
                    m("td", { width: "80px" }, "Budgetcode"),
                    m("td", { width: "170px" }, ff.select("de_budget_code", budgetCodes))
                ]),
                (pl_lijn.get("budget_code") !== null) ?
                    m("tr",
                        m("td", {colspan: 4, style: {color: "blue"}}, "(de budgetcode verschilt van de default budgetcode voor deze fase)")
                    ) : null,
                ( fasen.heeft_details_jn(pl_lijn.get("fase_code")) === 'J') ?
                    m("tr", [
                        m("td", "Fase detail:"),
                        m("td", {colspan: "3" }, ff.select("fase_detail_code", detailFasen.dd(pl_lijn.get("fase_code"))))
                    ]) : null,
                m("tr", [
                    m("td", { width: "80px" }, "Gepland Datum:"),
                    m("td", { width: "170px" }, ff.dateInput("igb_d", {config: dateConfig})),
                    m("td", { width: "80px" }, "Benut Datum:"),
                    m("td", { width: "170px" }, ff.dateInput("ibb_d", { style: { color: "blue"}}))
                ]),
                m("tr", [
                    m("td", "actie:"),
                    m("td", {colspan: "3" }, ff.select("actie_code", actie_dd))
                ]),

                ( _.contains(["GGO", "RC"],pl_lijn.get("actie_code")) ) ?
                    m("tr", [
                        m("td", "Contract:"),
                        m("td", {colspan: "3" }, ff.select("contract_id", ctrl.contractenDD))
                    ]) : null,

                ( pl_lijn.get("actie_code") === "H_B" ||
                    ( _.contains(["GGO", "RC"],pl_lijn.get("actie_code")) && pl_lijn.get("contract_id") !== null)) ?
                    m("tr", [
                        m("td", "Bestek:"),
                        m("td", {colspan: "3" }, ff.select("bestek_id", ctrl.bestekken_dd))
                    ]) : null,
                ( pl_lijn.get("benut_bestek_id") !== null ) ?
                    m("tr", [
                        m("td", "Benut bestek:"),
                        m("td", {colspan: "3" }, ff.input("c_bestek_omschrijving", { style: { color: "blue"}}))
                    ]) : null,
                m("tr", [
                    m("td", "Gepland Bedrag:"),
                    m("td", ff.input("ig_bedrag", {maxlength: "40"})),
                    m("td", "Benut Bedrag:"),
                    m("td", ff.input("ib_bedrag", { style: { color: "blue"}}))
                ]),
                m("tr", [
                    m("td", "Commentaar:")
                ]),
                m("tr", [
                    m("td", {colspan: "4" }, ff.textarea("commentaar", { rows: 5, maxlength: 1000, style: { width: "500px"}}))
                ])
            ]),
            m("div", [
                (pl_lijn.get("dossier_is_raamcontract_jn") === 'N'  &&
                        ( (pl_lijn.get("actie_code") === "N_B" && pl_lijn.get("fase_code") !== null) ||
                          (pl_lijn.get("actie_code") === "H_B" && pl_lijn.get("bestek_id") !== null)   ))
                    ? m("button", {onclick: _.bind(ctrl.voegDeelopdrToe, ctrl)}, "Selecteer planningsitem")
                    : null,
                m("br"),
                m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                m("button", {onclick: _.bind(ctrl.close, ctrl)}, "Annuleer")
            ])
        ]);
    };

    return dialogBuilder(PlanningLijnDialog);
});