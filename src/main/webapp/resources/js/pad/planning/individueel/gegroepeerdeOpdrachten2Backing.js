/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, console, _G_ */

define([
    "planning/individueel/PlanningLijnDialog2",
    "planning/individueel/PlanningLijnModel2",
    "planning/individueel/bestekDetailsDialog",
    "planning/individueel/FaseDetailsDialog2",
    "dropdown/dossierhouders",
    "dropdown/jaren",
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/formatters",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "mithril",
    "underscore"
], function (PlanningLijnDialog, PlanningLijnModel, bestekDetailsDialog, FaseDetailsDialog, dossierhouders_dd, jaren_dd, Model, GridComp, events, formatters, ajax, fhf, m, _) {
    'use strict';

    var comp, paramComp, ParamModel, OverzichtLijnModel, _planning;


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
                window.open('/pad/s/planning/individueel/gegroepeerdeOpdrachten2?doss_hdr_id=' + this.get("doss_hdr_id"),'_top');
            }

        }
    });

    OverzichtLijnModel =  Model.extend({
        meta: Model.buildMeta([
            { name: "bestek_id",  hidden: true},
            { name: "bestek_nr", hidden: true},
            { name: "fase_code", hidden: true},
            { name: "group_id", label: "Bestek", width: 120,
                slickFormatter: function (row, cell, value, columnDef, dataContext) {
                    if (dataContext.get("bestek_id") === null) {
                        return value;
                    }
                    return '<a href="s/bestek/' + dataContext.bestek_id + '" target="_blank" >' + value + '</a>';
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
                dossier_id: _G_.model.gegroepeerdeOpdrachtenDD[0] ? _G_.model.gegroepeerdeOpdrachtenDD[0].value : null
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
                        m("td", ff.select("doss_hdr_id", dossierhouders_dd)),
                        m("td", ff.select("jaar", jaren_dd)),
                        m("td", ff.select("benut_jn", ctrl.benut_jn_dd)),
                        m("td", "A-dossier:"),
                        m("td", ff.select("dossier_id", _G_.model.gegroepeerdeOpdrachtenDD)),
                        m("td", m("button", {class: "inputBtn", onclick: _.bind(ctrl.ophalen, ctrl)}, "Ophalen"))
                    ])
                ])
            );
        }
    };

    comp = {
        controller: function () {
            this.paramCtrl = new paramComp.controller();
            this.dialogCtrl = new PlanningLijnDialog.controller();

            bestekDetailsDialog.init();
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
                        config: comp.configOverzichtGrid,
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
                        config: comp.configGrid,
                        //class: "invisible",
                        style: {
                            position: "absolute",
                            height: "230px",
                            left: "0px",
                            right: "0px",
                            bottom: "0px"
                        }
                    })
                ]),
                FaseDetailsDialog.view(ctrl.faseDetailsCtrl),
                PlanningLijnDialog.view(ctrl.dialogCtrl)
            ]);
        },


        configOverzichtGrid: function (el, isInitialized) {
            var grid;
            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
                    model: OverzichtLijnModel,
                    onEditClicked: function (item) {
                        if (item.get("bestek_id")) {
                            bestekDetailsDialog.show(item.get("bestek_id"), item.get("bestek_nr"));
                        } else {
                            events.trigger("faseDetailsDialog:open",item.get("contract_id"), item.get("fase_code"), true);
                        }
                    }
                });
                events.on("overzicht.lijnen:refresh", function(overzichtLijnen) {
                    grid.setData(overzichtLijnen);
                });
            }

        },


        configGrid: function (el, isInitialized) {
            var grid;
            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
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
                    exportCsvFileName: "planning.csv"
                });
                events.on("planning.lijnen:refresh", function(planningLijnen) {
                    grid.setData(planningLijnen);
                });
            }

        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

    return;
});