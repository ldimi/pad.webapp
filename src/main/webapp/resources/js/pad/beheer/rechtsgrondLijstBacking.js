/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "dropdown/dossierTypes",
    "common/dropdown/ja_nee_dd",
    "ov/GridComp",
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (dossierTypes, ja_nee_dd, GridComp, Model, events, ajax, fhf) {
    'use strict';
    var _comp, _detailComp, RechtsgrondCodeModel, _rechtsgrondCollection;

    RechtsgrondCodeModel =  function (attributes) {
        Model.call(this, attributes);
    };
    RechtsgrondCodeModel.prototype = Object.create(Model.prototype);
    _.extend(RechtsgrondCodeModel.prototype, {
        constructor: RechtsgrondCodeModel,
        meta: Model.buildMeta([{
                name: "dossier_type",
                label: "Dossiertype",
                required : true
            }, {
                name: "rechtsgrond_code",
                label: "Code",
                required : true
            }, {
                name: "rechtsgrond_b",
                label: "Omschrijving",
                width: 250,
                required: true
            }, {
                name: "screening_jn",
                label: "voor screening?",
                default: 'N',
                required: true
        }])
    });


    function postData(url, data) {
        ajax.postJson({
            url: url,
            content: data
        }).then(function (response) {
            if (response && response.success) {
                events.trigger("rechtsgronden:dataReceived", response.result);
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
                    model: RechtsgrondCodeModel,
                    editBtn: window._G_.model.isAdminArt46,
                    newBtn: window._G_.model.isAdminArt46,
                    deleteBtn: window._G_.model.isAdminArt46,
                    onNewClicked: function () {
                        events.trigger("detailcomp:open", new RechtsgrondCodeModel());
                    },
                    onEditClicked: function (item) {
                        events.trigger("detailcomp:open", item.clone());
                    },
                   onDeleteClicked: function (item) {
                       item.set("status_crud", "D");
                       postData('/pad/s/beheer/dossierRechtsgrond/save', item);
                    }
                });
                events.on("rechtsgronden:dataReceived", function (data) {
                    grid.setData(data);
                    _rechtsgrondCollection = grid.getData();
                });
            }
        }
    });
    _comp.view = function (ctrl) {
        return m("div", {style: {marginLeft: "50px", align:"left"}}, [
            m("h3", "Beheer Rechtsgronden"),
            m(".myGrid", {
                config: _.bind(ctrl.configGrid, ctrl),
                style: {width: "600px", height: "90%"}
            }),

            _detailComp.view(ctrl.dialogCtrl)
        ]);
    };



    _detailComp = {};

    _detailComp.controller = function () {

        events.on("detailcomp:open", _.bind(this.open, this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        events.on("rechtsgronden:dataReceived", _.bind(this.close, this));

        this.dossier_types = _.filter(dossierTypes, function (type) { return (type.value !== 'X'); });
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
                data = _rechtsgrondCollection;
                for (i = 0; i < data.length; i = i + 1) {
                    if (item.get('rechtsgrond_code') === data[i].get('rechtsgrond_code') &&
                        item.get('dossier_type') === data[i].get('dossier_type')) {
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
            postData('/pad/s/beheer/dossierRechtsgrond/save', item);
        },
        configDialog: function (el, isInitialized) {
            if (!isInitialized) {
                this.$detailDialog = $(el).dialog({
                    width: 450,
                    autoOpen: false,
                    modal: true
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
                title: "Editeer",
                config: _.bind(ctrl.configDialog, ctrl),
                style: {height: "300px"}
        },
            ctrl.item ?
                [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td", "Dossier type:"),
                                m("td", [
                                    ff.select("dossier_type", {
                                        class: "input",
                                        readOnly: (ctrl.item.get("status_crud") !== 'C')
                                    }, ctrl.dossier_types)
                                ])
                            ]),
                            m("tr", [
                                m("td[width='100px']", "Rechtsgrond code:"),
                                m("td[width='100px']",
                                    ff.input("rechtsgrond_code", {
                                        maxlength: 5,
                                        readOnly: (ctrl.item.get("status_crud") !== 'C')
                                    })
                                )
                            ]),
                            m("tr", [
                                m("td", "Omschrijving:"),
                                m("td", ff.input("rechtsgrond_b", {maxlength: 40}))
                            ]),
                            m("tr", [
                                m("td", "screening ?:"),
                                m("td", ff.select("screening_jn", ja_nee_dd))
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
        events.trigger("rechtsgronden:dataReceived", _G_.model.rechtsgronden);
    });

});