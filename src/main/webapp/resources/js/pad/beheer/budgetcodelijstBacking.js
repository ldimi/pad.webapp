/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, m: false, alert: false, _G_: false */

define([
    "ov/GridComp",
    "ov/Model",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (GridComp, Model, ajax, fhf) {
    'use strict';
    var _grid, BudgetCodeModel, _detailComp;

    BudgetCodeModel = Model.extend({
        meta: Model.buildMeta([{
                name: "budget_code",
                label: "Code",
                required : true
            }, {
                name: "budget_code_b",
                label: "Omschrijving",
                width: 250,
                required: true
            },  {
                name: "artikel_b",
                label: "Artikel code",
                width: 125,
                required: true
            }, {
                name: "status_crud",
                hidden: true
            }])
    });

    function postData(url, data) {
        ajax.postJson({
            url: url,
            content: data
        }).then(function (response) {
            if (response && response.success) {
                _grid.setData(response.result);
                _detailComp.ctrl.close();
            } else {
                alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
            }
        });
    }


    function initGrid() {
        _grid = new GridComp({
            el: "#myGrid",
            model: BudgetCodeModel,
            editBtn: window._G_.isAdminArt46,
            newBtn: window._G_.isAdminArt46,
            deleteBtn: window._G_.isAdminArt46,
            onEditClicked: function (item) {
                item.set('status_crud', 'R');
                _detailComp.ctrl.open(item.clone());
            },
            onNewClicked: function () {
                _detailComp.ctrl.open(new BudgetCodeModel({
                    budget_code: null,
                    budget_code_b: null,
                    artikel_b: null,
                    status_crud: 'C'
                }));
            },
            onDeleteClicked: function (item) {
                postData('/pad/s/beheer/budgetcode/delete', item);
            }
        });
    }



    _detailComp = {};

    _detailComp.controller = function () {
        _detailComp.ctrl = this;
        this.artikels = _(_G_.artikels)
            .chain()
            .map(function(artikel) {
                    return {value: artikel.artikel_b, label: artikel.artikel_b };
            })
            .unshift({value: "", label: ""})
            .value();

        this.showErrors = m.prop(false);
    };
    _.extend(_detailComp.controller.prototype, {
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
                data = _grid.getData();
                for (i = 0; i < data.length; i = i + 1) {
                    if (item.get('budget_code') === data[i].get('budget_code')) {
                        alert("code is niet uniek.");
                        return false;
                    }
                }
            }
            return true;
        },
        _save: function (item) {
            var action, status_crud;
            status_crud = item.get("status_crud");
            if (status_crud === 'R') {
                action = "update";
            } else if (status_crud === 'C') {
                action = "insert";
            } else {
                alert("item heeft een ongeldige status : " + status_crud);
                return;
            }
            postData('/pad/s/beheer/budgetcode/' + action, item);
        },
        onReady: function () {
            this.$detailDialog = $('#detailDialog').dialog({
                autoOpen: false,
                modal: true
                //width: 450
            });
        }

    });

    _detailComp.view = function(ctrl) {
        var ff;

        if (!_G_.isAdminArt46) {
            return "";
        }

        ff = fhf.get().setModel(ctrl.item).setShowErrors(ctrl.showErrors());

        return m("#detailDialog.hidden", {style: {width: "400px", height: "300px"}},
            ctrl.item ?
                [
                    m("form[autocomplete='off'][id='detailForm'][novalidate='']", [
                        m("table", [
                            m("tbody", [
                                m("tr", [
                                    m("td[width='100px']", "Code:"),
                                    m("td[width='100px']", [
                                        ff.input("budget_code", {
                                            maxlength: 15,
                                            type: 'text',
                                            readOnly: (ctrl.item.get("status_crud") !== 'C')
                                        })
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Omschrijving:"),
                                    m("td", [
                                        ff.input("budget_code_b", {maxlength: 40, type: 'text'})
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Artikel code:"),
                                    m("td", [
                                        ff.select("artikel_b", {
                                            class: "input"
                                        }, ctrl.artikels)
                                    ])
                                ])
                            ])
                        ]),
                        m("input[name='status_crud'][type='hidden'][value='']")
                    ]),
                    m("button.#bewaarBtn", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button.#annuleerBtn", {onclick: _.bind(ctrl.close, ctrl)}, "annuleer")
                ] :
                ""
        );
    };

    m.mount($("#detailDiv").get(0), _detailComp);

    function onReady() {
        initGrid();

        _detailComp.ctrl.onReady();

        ajax.getJSON({
            url: '/pad/s/beheer/budgetcodes'
        }).then(function (data) {
            _grid.setData(data);
        });

    }

    return {
        onReady: onReady
    };
});