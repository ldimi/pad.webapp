/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console: false, _G_ */

define([
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "dropdown/jaren",
    "dropdown/programmaTypes",
    "ov/mithril/dialogBuilder",
    "underscore",
    "mithril"
], function (Model, GridComp, events, ajax, fhf, jaren_dd, programmaTypes_dd, dialogBuilder, _, m) {
    'use strict';
    var _jaar, fetchMijlpaalList, fetchMijlpaalProgrammaList, saveMijlpaal, saveMijlpaalProgramma, comp,
        ParamsModel, paramsComp,
        MijlpaalModel, configMijlpaalGrid, mijlpaalList, mijlpaalDialog,
        MijlpaalProgrammaModel, configMijlpaalProgrammaGrid, mijlpaalProgrammaList, mijlpaalProgrammaDialog;

    ParamsModel = Model.extend({
        meta: Model.buildMeta([{
                name: "jaar",
                type: "int",
                required: true,
                default: new Date().getFullYear()
        }])
    });



    MijlpaalModel = Model.extend({
        meta: Model.buildMeta([{
                name: "jaar",
                type: "int",
                hidden: true
            }, {
                name: "mijlpaal_d",
                label: "Mijlpaal",
                type: "date",
                required: true
            }, {
                name: "percentage",
                label: "Percentage",
                type: "int",
                min: 1,
                max: 100,
                required: true
            }, {
                name: "status_crud",
                default: "C",
                hidden: true
            }
        ]),
        enforceInvariants: function () {
            var mijlpaal_d, jaar;
            mijlpaal_d = this.get("mijlpaal_d");
            jaar = this.get("jaar");

            if (!this.validationError.mijlpaal_d && mijlpaal_d) {
                if (mijlpaal_d.getFullYear() !== jaar) {
                    this.validationError.mijlpaal_d = "De gekozen datum behoort niet tot het jaar  " + jaar;
                }
            }
        }
    });

    MijlpaalProgrammaModel = Model.extend({
        meta: Model.buildMeta([{
                name: "jaar",
                type: "int",
                hidden: true
            }, {
                name: "mijlpaal_d",
                label: "Mijlpaal",
                type: "date",
                required: true
            }, {
                name: "programma_code",
                label: "Programma",
                type: "string",
                required: true
            }, {
                name: "percentage",
                label: "Percentage",
                type: "int",
                min: 1,
                max: 100,
                required: true
            }, {
                name: "status_crud",
                default: "C",
                hidden: true
        }]),
        enforceInvariants: function () {
            var mijlpaal_d, jaar, found;

            mijlpaal_d = this.get("mijlpaal_d");
            jaar = this.get("jaar");

            if (!this.validationError.mijlpaal_d && mijlpaal_d) {
                if (mijlpaal_d.getFullYear() !== jaar) {
                    this.validationError.mijlpaal_d = "De gekozen datum behoort niet tot het jaar  " + jaar;
                }
            }

            if (!this.validationError.mijlpaal_d && mijlpaal_d) {
                found = _.find(mijlpaalList, function (mijlpaal) {
                    return ((mijlpaal.get("mijlpaal_d") - this.get("mijlpaal_d")) === 0);
                }, this);
                if (!found) {
                    this.validationError.mijlpaal_d = "De datum is geen bestaande mijlpaal datum";
                    return false;
                }
            }
        }
    });


    fetchMijlpaalList = function (jaar) {
        ajax.getJson({
            url: "/pad/s/beheer/getJaarMijlpalen",
            content: {jaar: jaar}
        }).then(function (list) {
            events.trigger("mijlpaal:dataReceived", list);
        });
    };

    saveMijlpaal = function (mijlpaal) {
        ajax.postJson({
            url: "/pad/s/beheer/jaarmijlpaal/save",
            content: mijlpaal
        }).then(function (list) {
            events.trigger("mijlpaal:dataReceived", list);
        });
    };

    fetchMijlpaalProgrammaList = function (jaar) {
        ajax.getJson({
            url: "/pad/s/beheer/getJaarMijlpalenProgramma",
            content: {jaar: jaar}
        }).then(function (list) {
            events.trigger("mijlpaalProgramma:dataReceived", list);
        });
    };

    saveMijlpaalProgramma = function (mijlpaalProgramma) {
        ajax.postJson({
            url: "/pad/s/beheer/jaarmijlpaalProgramma/save",
            content: mijlpaalProgramma
        }).then(function (list) {
            events.trigger("mijlpaalProgramma:dataReceived", list);
        });
    };

    configMijlpaalGrid = function (el, isInitialized) {
        var grid;
        if (!isInitialized) {
            grid = new GridComp({
                el: el,
                model: MijlpaalModel,
                newBtn: _G_.model.isAdminArt46,
                editBtn: _G_.model.isAdminArt46,
                deleteBtn: _G_.model.isAdminArt46,
                onNewClicked: function () {
                    events.trigger("mijlpaalDialog:open", new MijlpaalModel().set("jaar", _jaar));
                },
                onEditClicked: function (item) {
                    events.trigger("mijlpaalDialog:open", item.clone().set("status_crud", "U"));
                },
                onDeleteClicked: function (item) {
                    saveMijlpaal(item.clone().set("status_crud", "D"));
                }
            });
            events.on("mijlpaal:dataReceived", function (data) {
                grid.setData(data);
                mijlpaalList = grid.getData();
            });
        }
    };

    configMijlpaalProgrammaGrid = function (el, isInitialized) {
        var grid;
        if (!isInitialized) {
            grid = new GridComp({
                el: el,
                model: MijlpaalProgrammaModel,
                newBtn: _G_.model.isAdminArt46,
                editBtn: _G_.model.isAdminArt46,
                deleteBtn: _G_.model.isAdminArt46,
                onNewClicked: function () {
                    events.trigger("mijlpaalProgrammaDialog:open", new MijlpaalProgrammaModel().set("jaar", _jaar));
                },
                onEditClicked: function (item) {
                    events.trigger("mijlpaalProgrammaDialog:open", item.clone().set("status_crud", "U"));
                },
                onDeleteClicked: function (item) {
                    saveMijlpaalProgramma(item.clone().set("status_crud", "D"));
                }
            });
            events.on("mijlpaalProgramma:dataReceived", function (data) {
                grid.setData(data);
                mijlpaalProgrammaList = grid.getData();
            });
        }
    };

    paramsComp = {
        controller: function () {
            this.params = new ParamsModel();
            this.showErrors = m.prop(false);

            this.ophalen = function () {
                _jaar = this.params.get("jaar");
                if (_jaar) {
                    fetchMijlpaalList(_jaar);
                    fetchMijlpaalProgrammaList(_jaar);
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

    mijlpaalDialog = dialogBuilder({
        controller: function () {
            events.on("mijlpaalDialog:open", _.bind(this.open, this));
            events.on("mijlpaal:dataReceived", _.bind(this.close, this));

            this.title = "Editeer";
            //this.width = 300;
            //this.height = 200;

            this.showErrors = m.prop(false);

            this.preOpen = function (mijlpaal) {
                this.mijlpaal = mijlpaal;
                this.showErrors(false);
            };

            this.bewaar = function () {
                this.showErrors(true);
                if (!this.mijlpaal.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }
                saveMijlpaal(this.mijlpaal);
            };
        },
        view: function(ctrl) {
            var ff;
            if (!ctrl.mijlpaal) {
                return null;
            }

            ff = fhf.get().setModel(ctrl.mijlpaal).setShowErrors(ctrl.showErrors());

            return [
                // truukje: veld buiten scherm krijgt focus bij open dialog , om openklappen van calendar te vermijden.
                ff.input("jaar", {style: { position: "absolute", top: "-100px"}}),
                m("table", [
                    m("tr", [
                        m("td", "Datum:"),
                        m("td", ff.dateInput("mijlpaal_d", {
                            readOnly: (ctrl.mijlpaal.get("status_crud") !== 'C'),
                            disabled: (ctrl.mijlpaal.get("status_crud") !== 'C')
                        }))
                    ]),
                    m("tr", [
                        m("td", "Percentage:"),
                        m("td", ff.input("percentage"))
                    ])
                ]),
                m("div", [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "annuleer")
                ])
            ];
        }
    });

    mijlpaalProgrammaDialog = dialogBuilder({
        controller: function () {
            events.on("mijlpaalProgrammaDialog:open", _.bind(this.open, this));
            events.on("mijlpaalProgramma:dataReceived", _.bind(this.close, this));

            this.title = "Editeer";

            this.showErrors = m.prop(false);

            this.preOpen = function (mijlpaalProgramma) {
                this.mijlpaalProgramma = mijlpaalProgramma;
                this.showErrors(false);
            };

            this.bewaar = function () {
                this.showErrors(true);
                if (!this.mijlpaalProgramma.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }
                saveMijlpaalProgramma(this.mijlpaalProgramma);
            };
        },
        view: function (ctrl) {
            var ff;
            if (!ctrl.mijlpaalProgramma) {
                return null;
            }

            ff = fhf.get().setModel(ctrl.mijlpaalProgramma).setShowErrors(ctrl.showErrors());

            return [
                // truukje: veld buiten scherm krijgt focus bij open dialog , om openklappen van calendar te vermijden.
                ff.input("jaar", {style: { position: "absolute", top: "-100px"}}),
                m("table", [
                    m("tr", [
                        m("td", "Datum:"),
                        m("td", ff.dateInput("mijlpaal_d", {
                            readOnly: (ctrl.mijlpaalProgramma.get("status_crud") !== 'C'),
                            disabled: (ctrl.mijlpaalProgramma.get("status_crud") !== 'C')
                        }))
                    ]),
                    m("tr", [
                        m("td", "Programma:"),
                        m("td", ff.select("programma_code", {
                                readOnly: (ctrl.mijlpaalProgramma.get("status_crud") !== 'C')
                            },
                            programmaTypes_dd
                        ))
                    ]),
                    m("tr", [
                        m("td", "Percentage:"),
                        m("td", ff.input("percentage"))
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
            this.mijlpaalCtrl = new mijlpaalDialog.controller();
            this.mijlpaalProgrammaCtrl = new mijlpaalProgrammaDialog.controller();
        },
        view: function (ctrl) {
            return m("div", {style: {marginLeft: "50px", align:"left"}}, [
                m("h3", "Beheer Mijlpalen"),

                paramsComp.view(ctrl.paramsCtrl),

                m("div", {
                    config: configMijlpaalGrid,
                    style: {width: "400px", height: "200px", marginTop: "20px"},
                    class: mijlpaalList ? "" : "invisible"
                }),
                m("div", {
                    config: configMijlpaalProgrammaGrid,
                    style: {width: "400px", height: "200px", marginTop: "20px"},
                    class: mijlpaalProgrammaList ? "" : "invisible"
                }),

                mijlpaalDialog.view(ctrl.mijlpaalCtrl),
                mijlpaalProgrammaDialog.view(ctrl.mijlpaalProgrammaCtrl)
            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

});