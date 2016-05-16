/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/GridComp",
    "ov/Model2",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (GridComp, Model, event, ajax, fhf) {
    'use strict';
    var _comp, _detailComp, ScreeningMediumModel, _mediumCollection;


    ScreeningMediumModel = Model.extend({
        meta: Model.buildMeta([{
                    name: "medium_code",
                    label: "Code",
                    required : true
                }, {
                    name: "medium_b",
                    label: "Omschrijving",
                    required : true
                }]
        )
    });

    function postData(url, data) {
        ajax.postJson({
            url: url,
            content: data
        }).then(function (response) {
            if (response && response.success) {
                event.trigger("mediums:dataReceived", response.result);
            } else {
                alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
            }
        });
    }

    _comp = {};
    _comp.controller = function () {
        this.dialogCtrl = new _detailComp.controller();
    };
    _.extend(_comp.controller.prototype, {
        configGrid: function (el, isInitialized) {
            var grid;

            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
                    model: ScreeningMediumModel,
                    editBtn: window._G_.model.isAdminArt46,
                    newBtn: window._G_.model.isAdminArt46,
                    deleteBtn: window._G_.model.isAdminArt46,
                    onNewClicked: function () {
                        event.trigger("detailcomp:open", new ScreeningMediumModel());
                    },
                    onEditClicked: function (item) {
                        event.trigger("detailcomp:open", item.clone());
                    },
                   onDeleteClicked: function (item) {
                       item.set("status_crud", "D");
                       postData('/pad/s/beheer/screeningMedium/save', item);
                    }
                });
                event.on("mediums:dataReceived", function (data) {
                    grid.setData(data);
                    _mediumCollection = grid.getData();
                });
            }
        }
    });
    _comp.view = function (ctrl) {
        return m("div", {style: {margin: "50px", align:"left"}}, [
            m("h3", "Beheer Mediums"),
            m(".myGrid", {
                config: _.bind(ctrl.configGrid, ctrl),
                style: {width: "400px", height: "400px"}
            }),

            _detailComp.view(ctrl.dialogCtrl)
        ]);
    };



    _detailComp = {};

    _detailComp.controller = function () {

        event.on("detailcomp:open", _.bind(this.open, this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        event.on("mediums:dataReceived", _.bind(this.close, this));

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
                data = _mediumCollection;
                for (i = 0; i < data.length; i = i + 1) {
                    if ( item.get('medium_code') === data[i].get('medium_code') ) {
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
            postData('/pad/s/beheer/screeningMedium/save', item);
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

    _detailComp.view = function(ctrl) {
        var ff;

        if (!_G_.model.isAdminArt46) {
            return "";
        }

        ff = fhf.get().setModel(ctrl.item).setShowErrors(ctrl.showErrors());

        return m("#detailDialog.hidden", {
                config: _.bind(ctrl.configDialog, ctrl),
                style: {width: "400px", height: "300px"}
        },
            ctrl.item ?
                [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td[width='100px']", "Medium code:"),
                                m("td[width='100px']",
                                    ff.input("medium_code", {
                                        maxlength: 5,
                                        readOnly: (ctrl.item.get("status_crud") !== 'C')
                                    })
                                )
                            ]),
                            m("tr", [
                                m("td", "Omschrijving:"),
                                m("td", ff.input("medium_b", {maxlength: 25}))
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
        event.trigger("mediums:dataReceived", _G_.model.mediums);
    });

});