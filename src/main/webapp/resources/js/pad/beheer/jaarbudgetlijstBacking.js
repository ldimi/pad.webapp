/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, alert: false, console: false, _G_ */

define([
    "dropdown/jaren",
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/dialogBuilder",
    "mithril",
    "underscore"
], function (jaren_dd, Model, GridComp, events, ajax, fhf, dialogBuilder, m, _) {
    'use strict';
    var ParamsModel, JaarbudgetModel, getJaarbudgetten, saveJaarbudget, paramsComp, configJaarbudgetGrid, jaarbudgetDialog,
        comp, _jaar;

    ParamsModel = Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", type: "int", required: true, default: new Date().getFullYear() }
        ]),
        enforceInvariants: function () {
            if (this.hasChanged("jaar")) {
                _jaar = null; // wordt pas bij ophalen opnieuw ingevuld.
            }
        }
    });

    JaarbudgetModel = Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", hidden: true },
            { name: "budget_code", label: "Budget code", required: true },
            { name: "budget", label: "Budget bedrag", type: "int", required: true },
            { name: "effectief_budget", label: "Effectief budget", type: "int", required: false },
            { name: "artikel_b", label: "Artikelcode", type: "String", required: false },
            { name: "status_crud", hidden: true, default: "C" }
        ])
    });

    getJaarbudgetten = function (jaar) {
        ajax.getJSON({
            url: '/pad/s/beheer/getjaarbudgetten',
            content: {
                "jaar": jaar
            }
        }).then(function (resp) {
            events.trigger("jaarbudget:dataReceived", resp);
        });
    };

    saveJaarbudget = function (jaarbudget) {
        ajax.postJson({
            url: "/pad/s/beheer/jaarbudget/save",
            content: jaarbudget
        }).then(function (list) {
            events.trigger("jaarbudget:dataReceived", list);
        });
    };

    paramsComp = {
        controller: function () {
            this.params = new ParamsModel();
            this.showErrors = m.prop(false);

            this.ophalen = function () {
                _jaar = this.params.get("jaar");
                if (_jaar) {
                    getJaarbudgetten(_jaar);
                }
            };
        },
        view: function (ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());
            return [
                m("table", [
                    m("tr", [
                        m("td", "Jaar:"),
                        m("td", ff.select("jaar", jaren_dd)),
                        m("td", m("button", {onclick: _.bind(ctrl.ophalen, ctrl)}, "Ophalen"))
                    ])
                ])
            ];
        }
    };


    configJaarbudgetGrid = function (el, isInitialized) {
        var grid;
        if (!isInitialized) {
            grid = new GridComp({
                el: el,
                model: JaarbudgetModel,
                newBtn: _G_.model.isAdminArt46,
                editBtn: _G_.model.isAdminArt46,
                deleteBtn: _G_.model.isAdminArt46,
                onNewClicked: function () {
                    events.trigger("jaarbudgetDialog:open", new JaarbudgetModel().set("jaar", _jaar));
                },
                onEditClicked: function (item) {
                    events.trigger("jaarbudgetDialog:open", item.clone().set("status_crud", "U"));
                },
                onDeleteClicked: function (item) {
                    saveJaarbudget(item.clone().set("status_crud", "D"));
                }
            });
            events.on("jaarbudget:dataReceived", function (data) {
                grid.setData(data);
            });
        }
    };

    jaarbudgetDialog = dialogBuilder({
        controller: function () {
            events.on("jaarbudgetDialog:open", _.bind(this.open, this));
            events.on("jaarbudget:dataReceived", _.bind(this.close, this));

            this.title = "Editeer";
            //this.width = 300;

            this.showErrors = m.prop(false);

            this.preOpen = function (jaarbudget) {
                this.jaarbudget = jaarbudget;
                this.showErrors(false);
            };

            this.bewaar = function () {
                this.showErrors(true);
                if (!this.jaarbudget.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }
                saveJaarbudget(this.jaarbudget);
            };
        },
        view: function(ctrl) {
            var ff, status_crud;

            ff = fhf.get().setModel(ctrl.jaarbudget).setShowErrors(ctrl.showErrors());
            status_crud = ctrl.jaarbudget.get("status_crud");
            return [
                m("table.formlayout", [
                    m("tr", [
                        m("td[width=100px]", "Budget code:"),
                        m("td[width=100px]",
                            ff.select("budget_code",
                                {readOnly:  status_crud !== 'C' },
                                _G_.model.budgetCodeDD)
                        )
                    ]),
                    m("tr", [
                        m("td", "Budget bedrag:"),
                        m("td", ff.input("budget"))
                    ]),
                    m("tr", [
                        m("td", "Effectief budget:"),
                        m("td", ff.input("effectief_budget"))
                    ])
                ]),
                m("div", [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "annuleer")
                ])
            ];
        }
    });

    comp = {
        controller: function() {
            this.paramsCtrl = new paramsComp.controller();
            this.jaarbudgetCtrl = new jaarbudgetDialog.controller();
        },
        view: function (ctrl) {
            return m("div", {style: {marginLeft: "50px", align:"left"}}, [
                m("h3", "Beheer jaarbudget planning"),

                paramsComp.view(ctrl.paramsCtrl),

                m("div", {
                    config: configJaarbudgetGrid,
                    style: {width: "500px", height: "200px", marginTop: "20px"},
                    class: _jaar ? "" : "invisible"
                }),

                jaarbudgetDialog.view(ctrl.jaarbudgetCtrl)
            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

});