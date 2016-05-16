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
    var _comp, _detailComp, ScreeningStofgroepCodeModel, _stofgroepCodeCollection;


    ScreeningStofgroepCodeModel = Model.extend({
        meta: Model.buildMeta([{
                    name: "stofgroep_code",
                    label: "Stofgroep",
                    width: 200,
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
                event.trigger("stofgroepCodes:dataReceived", response.result);
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
                    model: ScreeningStofgroepCodeModel,
                    newBtn: window._G_.model.isAdminArt46,
                    editBtn: false,
                    deleteBtn: window._G_.model.isAdminArt46,
                    onNewClicked: function () {
                        event.trigger("detailcomp:open", new ScreeningStofgroepCodeModel());
                    },
                    onDeleteClicked: function (item) {
                       item.set("status_crud", "D");
                       postData('/pad/s/beheer/screeningStofgroepCode/save', item);
                    }
                });
                event.on("stofgroepCodes:dataReceived", function (data) {
                    grid.setData(data);
                    _stofgroepCodeCollection = grid.getData();
                });
            }
        }
    });
    _comp.view = function (ctrl) {
        return m("div", {style: {marginLeft: "50px", align:"left"}}, [
            m("h3", "Beheer StofgroepCodes"),
            m(".myGrid", {
                config: _.bind(ctrl.configGrid, ctrl),
                style: {width: "250px", height: "90%"}
            }),

            _detailComp.view(ctrl.dialogCtrl)
        ]);
    };



    _detailComp = {};

    _detailComp.controller = function () {

        event.on("detailcomp:open", _.bind(this.open, this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        event.on("stofgroepCodes:dataReceived", _.bind(this.close, this));

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
                data = _stofgroepCodeCollection;
                for (i = 0; i < data.length; i = i + 1) {
                    if ( item.get('stofgroep_code') === data[i].get('stofgroep_code') ) {
                        alert("stofgroep is niet uniek.");
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
            postData('/pad/s/beheer/screeningStofgroepCode/save', item);
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
                style: {width: "300px", height: "300px"}
        },
            ctrl.item ?
                [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td[width='100px']", "StofgroepCode :"),
                                m("td[width='250px']",
                                    ff.input("stofgroep_code", {
                                        maxlength: 25,
                                        readOnly: (ctrl.item.get("status_crud") !== 'C')
                                    })
                                )
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
        event.trigger("stofgroepCodes:dataReceived", _G_.model.stofgroepCodes);
    });

});