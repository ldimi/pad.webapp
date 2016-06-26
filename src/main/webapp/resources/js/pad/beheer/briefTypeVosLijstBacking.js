/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/GridComp",
    "ov/Model",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (GridComp, Model, ajax, fhf) {
    'use strict';
    var _grid, _detailComp, BriefTypeVosModel;

    BriefTypeVosModel = Model.extend({
        meta: Model.buildMeta([{
                name: "type_id",
                label: "Id",
                type: "int",
                required : true
            }, {
                name: "type_b",
                label: "Omschrijving",
                width: 250,
                required : true
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
            el: "#briefTypeVosGrid",
            model: BriefTypeVosModel,
            editBtn: window._G_.isAdminArt46,
            newBtn: window._G_.isAdminArt46,
            deleteBtn: window._G_.isAdminArt46,
            onEditClicked: function (item) {
                _detailComp.ctrl.open(item.clone());
            },
            onNewClicked: function () {
                _detailComp.ctrl.open(new BriefTypeVosModel());
            },
            onDeleteClicked: function (item) {
                postData('/pad/s/beheer/briefTypeVos/delete', item);
            }
        });
    }


    _detailComp = {};

    _detailComp.controller = function () {
        _detailComp.ctrl = this;
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
                    if (item.get('type_id') === data[i].get('type_id')) {
                        alert("Id is niet uniek.");
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
                $.notify("Er zijn geen aanpassingen te bewaren.");
                return;
            }
            if (status_crud === 'U') {
                action = "update";
            } else if (status_crud === 'C') {
                action = "insert";
            } else {
                alert("item heeft een ongeldige status : " + status_crud);
                return;
            }
            postData('/pad/s/beheer/briefTypeVos/' + action, item);
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
                                    m("td[width='100px']", "Id:"),
                                    m("td[width='100px']", [
                                        ff.input("type_id", {
                                            type: 'text',
                                            readOnly: (ctrl.item.get("status_crud") !== 'C')
                                        })
                                    ])
                                ]),
                                m("tr", [
                                    m("td", "Omschrijving:"),
                                    m("td", [
                                        ff.input("type_b", {maxlength: 100, type: 'text'})
                                    ])
                                ])
                            ])
                        ])
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

        ajax.getJson({
            url: '/pad/s/beheer/briefTypesVos'
        }).then(function (data) {
            _grid.setData(data.result);
        });

    }

    return {
        onReady: onReady
    };
});