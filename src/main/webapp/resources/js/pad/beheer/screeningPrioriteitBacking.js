/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "common/dropdown/ja_nee_dd",
    "ov/GridComp",
    "ov/Model2",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (ja_nee_dd, GridComp, Model, event, ajax, fhf) {
    'use strict';
    var _comp, _criteriumComp, ScreeningPrioriteitCriteriumModel, _prioriteitCriteriumCollection,
        _waardeComp, ScreeningPrioriteitWaardeModel;


    ScreeningPrioriteitCriteriumModel = Model.extend({
        meta: Model.buildMeta([{
                    name: "criterium_code",
                    label: "Criterium code",
                    required: true
                }, {
                    name: "criterium_b",
                    label: "Omschrijving",
                    width: 140,
                    required: true
                }, {
                    name: "gewicht",
                    label: "Gewicht",
                    type: "int",
                    width: 70,
                    required: true
                }]
        ),

        enforceInvariants: function () {
            var gewicht;

            //validatie gewicht
            gewicht = this.get("gewicht");
            if ( gewicht !== null && this.isValid("gewicht") &&
                 (gewicht < 0 || gewicht > 100 )                    ) {

                this.validationError.gewicht = "alleen waarden tussen 0  en 100";
            }

        }

    });


    ScreeningPrioriteitWaardeModel = Model.extend({
        meta: Model.buildMeta([{
                    name: "waarde_id",
                    hidden: true
                }, {
                    name: "criterium_code",
                    label: "Criterium code",
                    required: true
                }, {
                    name: "bbo_beschikbaar_jn",
                    label: "BBO beschikbaar ?",
                    required: false  //alleen verplicht maken bij criterium A en P
                }, {
                    name: "waarde_b",
                    label: "Waarde",
                    width: 300,
                    required: true
                }, {
                    name: "score",
                    label: "Score",
                    width: 60,
                    type: "int",
                    required: true
                }]
        ),

        enforceInvariants: function () {
            var criterium_code, score;

            //validatie bbo_beschikbaar_jn
            criterium_code = this.get("criterium_code");
            if ( (criterium_code === "A" || criterium_code === "P") &&
                 this.get("bbo_beschikbaar_jn") === null                   ) {

                this.validationError.bbo_beschikbaar_jn = "verplicht veld.";
            }

            //validatie score
            score = this.get("score");
            if ( score !== null && this.isValid("score") &&
                 (score < 0 || score > 10 )                    ) {

                this.validationError.score = "alleen waarden tussen 0  en 10";
            }

        }

    });


    function postData(url, data) {
        ajax.postJson({
            url: url,
            content: data
        }).then(function (response) {
            if (response && response.success) {
                event.trigger("criteria:dataReceived", response.result.priotiteitCriteria);
                event.trigger("waarden:dataReceived", response.result.priotiteitWaarden);
            } else {
                alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
            }
        });
    }

    _comp = {};
    _comp.controller = function () {
        this.criteriumDialogCtrl = new _criteriumComp.controller();
        this.waardeDialogCtrl = new _waardeComp.controller();
    };
    _.extend(_comp.controller.prototype, {
        configCriteriumGrid: function (el, isInitialized) {
            var grid;

            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
                    model: ScreeningPrioriteitCriteriumModel,
                    editBtn: window._G_.model.isAdminArt46,
                    newBtn: false,
                    deleteBtn: window._G_.model.isAdminArt46,
                    onNewClicked: function () {
                        event.trigger("criteriumcomp:open", new ScreeningPrioriteitCriteriumModel());
                    },
                    onEditClicked: function (item) {
                        event.trigger("criteriumcomp:open", item.clone());
                    },
                   onDeleteClicked: function (item) {
                       item.set("status_crud", "D");
                       postData('/pad/s/beheer/screeningPrioriteitCriterium/save', item);
                    }
                });
                event.on("criteria:dataReceived", function (data) {
                    var gewichtSom;

                    grid.setData(data);
                    _prioriteitCriteriumCollection = grid.getData();

                    gewichtSom = _prioriteitCriteriumCollection.reduce(function(memo, criterium) {
                        return memo + criterium.get("gewicht");
                    }, 0 );

                    if (gewichtSom === 100) {
                        $("#gewichtSomError").addClass("hidden");
                    } else {
                        $("#gewichtSomError").removeClass("hidden");
                    }
                });
            }
        },
        configWaardeGrid: function (el, isInitialized) {
            var grid;

            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
                    model: ScreeningPrioriteitWaardeModel,
                    editBtn: window._G_.model.isAdminArt46,
                    newBtn: window._G_.model.isAdminArt46,
                    deleteBtn: window._G_.model.isAdminArt46,
                    onNewClicked: function () {
                        event.trigger("waardecomp:open", new ScreeningPrioriteitWaardeModel());
                    },
                    onEditClicked: function (item) {
                        event.trigger("waardecomp:open", item.clone());
                    },
                   onDeleteClicked: function (item) {
                       item.set("status_crud", "D");
                       postData('/pad/s/beheer/screeningPrioriteitWaarde/save', item);
                    }
                });
                event.on("waarden:dataReceived", function (data) {
                    grid.setData(data);
                });
            }
        }
    });


    _comp.view = function (ctrl) {
        return m("div", {style: {marginLeft: "50px", align:"left"}}, [
            m("h3", "Beheer Prioriteits criteria"),
            m(".myGrid", {
                config: _.bind(ctrl.configCriteriumGrid, ctrl),
                style: {width: "350px", height: "180px"}
            }),
            m("#gewichtSomError" , {style:  {color: "red"} }, "Som van gewichten is niet gelijk aan 100"),
            m("h3", "Beheer Prioriteits waarden"),
            m(".myGrid", {
                config: _.bind(ctrl.configWaardeGrid, ctrl),
                style: {width: "600px", height: "500px"}
            }),

            _criteriumComp.view(ctrl.criteriumDialogCtrl),
            _waardeComp.view(ctrl.waardeDialogCtrl)
        ]);
    };





    //  Criterium Details
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    _criteriumComp = {};

    _criteriumComp.controller = function () {

        event.on("criteriumcomp:open", _.bind(this.open, this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        event.on("criteria:dataReceived", _.bind(this.close, this));

        this.showErrors = m.prop(false);
    };
    _.extend(_criteriumComp.controller.prototype, {
        open: function (item) {
            this.item = item;
            this.showErrors(false);
            m.redraw();
            this.$detailDialog.dialog('open');
        },
        close: function () {
            this.$detailDialog.dialog('close');
        },
        bewaar: function () {
            this.showErrors(true);
            if (!this.item.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }
            if (this._validate(this.item)) {
                this._save(this.item);
            }
        },
        _validate: function (item) {
            var i, data;
            if (item.get("status_crud") === 'C') {
                //code moet uniek zijn.
                data = _prioriteitCriteriumCollection;
                for (i = 0; i < data.length; i = i + 1) {
                    if ( item.get('criterium_code') === data[i].get('criterium_code') ) {
                        alert("code is niet uniek.");
                        return false;
                    }
                }
            }
            return true;
        },
        _save: function (item) {
            var status_crud;
            status_crud = item.get("status_crud");
            if (status_crud === 'R') {
                $.notify("Er zijn geen aanpassingen te bewaren.");
                return;
            }
            if (status_crud !== 'U' && status_crud !== 'C') {
                alert("item heeft een ongeldige status : " + status_crud);
                return;
            }
            postData('/pad/s/beheer/screeningPrioriteitCriterium/save', item);
        },
        configDialog: function (el, isInitialized) {
            if (!isInitialized) {
                this.$detailDialog = $(el).dialog({
                    autoOpen: false,
                    modal: true,
                    width: 450
                });
            }
        }
    });

    _criteriumComp.view = function(ctrl) {
        var ff;

        if (!_G_.model.isAdminArt46) {
            return "";
        }

        ff = fhf.get().setModel(ctrl.item).setShowErrors(ctrl.showErrors());

        return m("#criteriumDialog.hidden", {
                config: _.bind(ctrl.configDialog, ctrl),
                style: {width: "400px", height: "300px"}
        },
            ctrl.item ?
                [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td[width='100px']", "Criterium code:"),
                                m("td[width='100px']",
                                    ff.input("criterium_code", {
                                        maxlength: 5,
                                        readOnly: (ctrl.item.get("status_crud") !== 'C')
                                    })
                                )
                            ]),
                            m("tr", [
                                m("td", "Omschrijving:"),
                                m("td", ff.input("criterium_b", {maxlength: 25}))
                            ]),
                            m("tr", [
                                m("td", "Gewicht:"),
                                m("td", ff.input("gewicht"))
                            ])
                        ])
                    ]),
                    m("div", [
                        m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                        m("button", {onclick: _.bind(ctrl.close, ctrl)}, "annuleer")
                    ])
                ] :
                ""
        );
    };





    //  Waarde Details
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    _waardeComp = {};

    _waardeComp.controller = function () {

        event.on("waardecomp:open", _.bind(this.open, this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        event.on("criteria:dataReceived", _.bind(this.close, this));

        this.showErrors = m.prop(false);
    };
    _.extend(_waardeComp.controller.prototype, {
        open: function (item) {
            this.item = item;
            this.showErrors(false);
            m.redraw();
            this.$detailDialog.dialog('open');
        },
        close: function () {
            this.$detailDialog.dialog('close');
        },
        bewaar: function () {
            this.showErrors(true);
            if (!this.item.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }
            this._save(this.item);
        },
        _save: function (item) {
            var status_crud;
            status_crud = item.get("status_crud");
            if (status_crud === 'R') {
                $.notify("Er zijn geen aanpassingen te bewaren.");
                return;
            }
            if (status_crud !== 'U' && status_crud !== 'C') {
                alert("item heeft een ongeldige status : " + status_crud);
                return;
            }
            postData('/pad/s/beheer/screeningPrioriteitWaarde/save', item);
        },
        configDialog: function (el, isInitialized) {
            if (!isInitialized) {
                this.$detailDialog = $(el).dialog({
                    autoOpen: false,
                    modal: true,
                    width: 450
                });
            }
        }
    });

    _waardeComp.view = function(ctrl) {
        var ff;

        if (!_G_.model.isAdminArt46) {
            return "";
        }

        ff = fhf.get().setModel(ctrl.item).setShowErrors(ctrl.showErrors());

        return m("#waardeDialog.hidden", {
                config: _.bind(ctrl.configDialog, ctrl),
                style: {width: "400px", height: "300px"}
        },
            ctrl.item ?
                [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td[width='100px']", "Criterium code:"),
                                m("td[width='200px']",
                                    ff.input("criterium_code", {
                                        maxlength: 5,
                                        readOnly: (ctrl.item.get("status_crud") !== 'C')
                                    })
                                )
                            ]),
                            (ctrl.item.get("criterium_code") === "A" || ctrl.item.get("criterium_code") === "P") ?
                                m("tr", [
                                    m("td", "BBO beschikbaar ?:"),
                                    m("td", ff.select("bbo_beschikbaar_jn", ja_nee_dd))
                                ]) : null,
                            m("tr", [
                                m("td", "Omschrijving:"),
                                m("td", ff.input("waarde_b", {maxlength: 50}))
                            ]),
                            m("tr", [
                                m("td", "Score:"),
                                m("td", ff.input("score"))
                            ])
                        ])
                    ]),
                    m("div", [
                        m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                        m("button", {onclick: _.bind(ctrl.close, ctrl)}, "annuleer")
                    ])
                ] :
                ""
        );
    };

    m.mount($("#jsviewContentDiv").get(0), _comp);

    $(document).ready(function(){
        event.trigger("criteria:dataReceived", _G_.model.lijsten.priotiteitCriteria);
        event.trigger("waarden:dataReceived", _G_.model.lijsten.priotiteitWaarden);
    });

});