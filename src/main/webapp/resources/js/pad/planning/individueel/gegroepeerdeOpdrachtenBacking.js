/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, console, _G_ */

define([
    "planning/individueel/PlanningLijnDialog",
    "planning/individueel/PlanningLijnModel",
    "planning/individueel/BestekDetailsDialog",
    "planning/individueel/FaseDetailsDialog",
    "dropdown/dossierhouders",
    "dropdown/jaren",
    "ov/Model",
    "ov/events",
    "ov/formatters",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/gridConfigBuilder",
    "mithril",
    "underscore"
], function (PlanningLijnDialog, PlanningLijnModel, BestekDetailsDialog, FaseDetailsDialog, dossierhouders_dd, jaren_dd, Model, events, formatters, ajax, fhf, gridConfigBuilder, m, _) {
    'use strict';

    var comp, paramComp, ParamModel, OverzichtLijnModel, _planning;

    PlanningLijnModel.prototype.meta.getColDef("dossier_gemeente_b").set("hidden", true);

    ParamModel =  Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", type: "int" },
            { name: "dossier_id", type: "int" },
            { name: "dossier_type" },
            { name: "doss_hdr_id" },
            { name: "benut_jn" }
        ]),

        enforceInvariants: function () {
            if (this.hasChanged("doss_hdr_id")) {
                if (_G_.model.RAAM_OF_GROEP === "GROEP") {
                    window.open('/pad/s/planning/individueel/gegroepeerdeOpdrachten?doss_hdr_id=' + this.get("doss_hdr_id"),'_top');
                } else {
                    window.open('/pad/s/planning/individueel/raamcontracten?doss_hdr_id=' + this.get("doss_hdr_id"),'_top');
                }
            }

        }
    });

    OverzichtLijnModel =  Model.extend({
        meta: Model.buildMeta([
            { name: "bestek_id",  hidden: true},
            { name: "bestek_nr", hidden: true},
            { name: "fase_code", hidden: true},
            { name: "group_id", label: "Bestek", width: 120,
                gridFormatter: function (value) {
                    if (this.get("bestek_id") === null) {
                        return value;
                    }
                    return '<a href="s/bestek/' + this.get("bestek_id") + '" target="_blank" >' + value + '</a>';
                }
            },
            { name: "omschrijving", label: "Omschrijving", width: 500},
            { name: "saldo_geraamd", label: "Geraamd saldo", type: "double" },
            { name: "gepland", label: "Gepland", type: "double"},
            { name: "voorspeld_saldo", label: "Voorspeld saldo", type: "double"}
        ])
    });


    paramComp = {
        controller: function () {


            this.params = new ParamModel({
                doss_hdr_id: _G_.model.doss_hdr_id,
                dossier_type: "X",
                dossier_id: _G_.model.A_dossiers_DD[0] ? _G_.model.A_dossiers_DD[0].value : null
            });

            // customisatie van dropdown lijsten voor params
            jaren_dd[0].label = "Basis planning";
            _G_.model.dossiersDD.unshift({value: "", label: ""});

            this.benut_jn_dd = [
                {value: "", label: "incl. gerealiseerd"},
                {value: "N", label: "excl. gerealiseerd"}
            ];

            this.showErrors = m.prop(false);

            // methods
            ///////////////////////////////////////////////////////////////

            this.ophalen = function () {
                $('#overzicht_div').addClass('invisible');

                ajax.postJSON({
                    url: "/pad/s/planning/getPlanning",
                    content: this.params
                }).then(function (response) {
                    if (response) {
                        _planning = response;

                        _planning.contractenDD.unshift({
                            value: "",
                            label: ""
                        });
                        _G_.contractenDD = _planning.contractenDD;  // !! opgepast : globale variabele.

                        events.trigger("planning.lijnen:refresh", _planning.lijnen);
                        $('#overzicht_div').removeClass('invisible');
                    }
                });

                ajax.postJSON({
                    url: "/pad/s/planning/getOverzichtRaamcontract",
                    content: this.params.get("dossier_id")
                }).then(function (response) {
                    if (response) {
                        events.trigger("overzicht.lijnen:refresh", response);
                        $('#overzicht_div').removeClass('invisible');
                    }
                }.bind(this));
            };
        },
        view: function (ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

            return m("div",
                m("table", [
                    m("tr", [
                        m("td", "Dossier houder:"),
                        m("td", ff.select("doss_hdr_id", {style: {width: "200px"}}, dossierhouders_dd)),
                        m("td", ff.select("jaar", jaren_dd)),
                        m("td", ff.select("benut_jn", ctrl.benut_jn_dd)),
                        (_G_.model.A_dossiers_DD.length > 0 ) ? [
                                m("td", "A-dossier:"),
                                m("td", ff.select("dossier_id", {style: {width: "350px"}}, _G_.model.A_dossiers_DD)),
                                m("td", m("button", {class: "inputBtn", onclick: _.bind(ctrl.ophalen, ctrl)}, "Ophalen"))
                            ] : null
                    ])
                ]),
                (_G_.model.A_dossiers_DD.length == 0 ) ?
                    m("div" , "geen dossier te plannen") : null
            );
        }
    };

    comp = {
        controller: function () {
            this.paramCtrl = new paramComp.controller();
            this.dialogCtrl = new PlanningLijnDialog.controller();

            this.bestekDetailsCtrl = new BestekDetailsDialog.controller();
            this.faseDetailsCtrl = new FaseDetailsDialog.controller();

            events.on("overzicht.lijnen:refresh", function(overzichtLijnen) {
                this.tot_voorspeld_saldo =  _.reduce(overzichtLijnen, function (memo, item) {
                    return memo + (item.voorspeld_saldo || 0);
                }, 0);
            }.bind(this));
        },
        view: function (ctrl) {
            return m("div", [
                paramComp.view(ctrl.paramCtrl),
                m("#overzicht_div", {
                        class:  "invisible",
                        style: {position: "absolute", top: "35px", left: "5px", right: "5px", bottom: "5px" }
                    } ,[
                    m("#overzichtGrid", {
                        config: gridConfigBuilder({
                            model: OverzichtLijnModel,
                            onEditClicked: function (item) {
                                if (item.get("bestek_id")) {
                                    events.trigger("bestekDetailsDialog:open",item.get("bestek_id"), item.get("bestek_nr"));
                                } else {
                                    events.trigger("faseDetailsDialog:open",item.get("contract_id"), item.get("fase_code"));
                                }
                            },
                            setDataEvent: "overzicht.lijnen:refresh"
                        }),
                        style: {
                            position: "absolute",
                            top: "0px",
                            left: "0px",
                            right: "0px",
                            bottom: "260px"
                        }
                    }),
                    m("div", { style: {position: "absolute", heigth: "30px", left: "0px", right: "0px", bottom: "240px" }}, [
                        "Totaal voorspeld saldo : ",
                        m("span", { style: {fontWeight: "bold"}}, formatters("double")(ctrl.tot_voorspeld_saldo, ".") )
                    ]),
                    m("#planningGrid", {
                        config: gridConfigBuilder({
                            model: PlanningLijnModel,
                            newBtn: true,
                            editBtn: true,
                            deleteBtn: true,
                            onNewClicked: function (item) {
                                var newItem;
                                if (item) {
                                    newItem = item.createNewLine();
                                    _planning.selectedLijnIndex = _planning.lijnen.indexOf(item);
                                    events.trigger("planningLijnDialog:open", newItem, _planning);
                                } else {
                                    alert("er is geen rij geselecteerd.");
                                }
                            },
                            onEditClicked: function (item) {
                                // _planningLijnDialog.show(item, _planning);
                                var cloned = item.clone();
                                _planning.selectedLijnIndex = _planning.lijnen.indexOf(item);
                                events.trigger("planningLijnDialog:open", cloned, _planning);
                            },
                            onDeleteClicked: function (item) {
                                if (item.get('status_crud') === 'C') {
                                    $.notifyError("Ongeplande lijnen worden niet verwijderd.");
                                    return;
                                }
                                if (item.get("c_isReedsGekoppeld")) {
                                    $.notifyError("Deze planningslijn kan niet verwijderd worden. (reeds gekoppeld/benut)");
                                    return;
                                }
                                item.set({
                                    deleted_jn: "J",
                                    status_crud: "U"
                                });
                                ajax.postJSON({
                                    url: "/pad/s/planning/bewaar",
                                    content: item.clone()
                                }).then(function () {
                                    $.notify("De lijn is verwijderd.");
                                });

                                // lokale versie wordt al aangepast (we gaan ervan uit dat bewaren toch lukt.)
                                _planning.lijnen = _.reject(_planning.lijnen, function (lijn) {
                                    return (lijn.cid === item.cid);
                                });

                                events.trigger("planning.lijnen:refresh", _planning.lijnen);
                            },
                            exportCsv: true,
                            exportCsvFileName: "planning.csv",
                            setDataEvent: "planning.lijnen:refresh"
                        }),
                        style: {
                            position: "absolute",
                            height: "230px",
                            left: "0px",
                            right: "0px",
                            bottom: "0px"
                        }
                    })
                ]),
                BestekDetailsDialog.view(ctrl.bestekDetailsCtrl),
                FaseDetailsDialog.view(ctrl.faseDetailsCtrl),
                PlanningLijnDialog.view(ctrl.dialogCtrl)
            ]);
        }


    };

    m.mount($("#jsviewContentDiv").get(0), comp);

    return;
});