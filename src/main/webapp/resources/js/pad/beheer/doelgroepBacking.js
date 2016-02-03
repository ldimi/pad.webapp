/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, m: false, alert: false, _: false, _G_: false */

define([
    "ov/GridComp",
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (GridComp, Model, event, ajax, fhf) {
    'use strict';
    var DoelgroepModel, _comp, _detailComp;

    DoelgroepModel = Model.extend({
        meta: Model.buildMeta([
            {
                name: "doelgroep_type_id",
                hidden: true,
            }, {
                name: "doelgroep_type_b",
                label: "Omschrijving",
                required: true,
                size: 50,
                width: 250
            }
        ])
    });

    function postData(url, data) {
        ajax.postJson({
            url: url,
            content: data
        }).then(function (response) {
            if (response && response.success) {
                event.trigger("doelgroepen:dataReceived", response.result);
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
                    model: DoelgroepModel,
                    newBtn: _G_.model.isAdminArt46,
                    editBtn: _G_.model.isAdminArt46,
                    deleteBtn: _G_.model.isAdminArt46,
                    onNewClicked: function () {
                        event.trigger("detailcomp:open", new DoelgroepModel({
                            status_crud: 'C'
                        }));
                    },
                    onEditClicked: function (item) {
                        item.set("status_crud", 'U');
                        event.trigger("detailcomp:open", item.clone());
                    },
                    onDeleteClicked: function (item) {
                        postData('/pad/s/doelgroep/delete', item);
                    }
                });
                event.on("doelgroepen:dataReceived", function (data) {
                    grid.setData(data);
                });
            }
        }
    });
    _comp.view = function (ctrl) {

        return m("div", {style: {margin: "50px", align:"left"}}, [
            m("h3", "Beheer Doelgroepen"),
            m(".myGrid", {
                config: _.bind(ctrl.configGrid, ctrl),
                style: {width: "400px", height: "300px"}
            }),

            _detailComp.view(ctrl.dialogCtrl)
        ]);
    };


    _detailComp = {};

    _detailComp.controller = function () {

        event.on("detailcomp:open", _.bind(this.open, this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        event.on("doelgroepen:dataReceived", _.bind(this.close, this));

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
            this._save(this.item);
        },
        _save: function (item) {
            var action, status_crud;
            status_crud = item.get("status_crud");
            if (status_crud === 'U') {
                action = "update";
            } else if (status_crud === 'C') {
                action = "insert";
            } else {
                alert("item heeft een ongeldige status : " + status_crud);
                return;
            }
            postData('/pad/s/doelgroep/' + action, item);
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

        return m(".hidden", {
                config: _.bind(ctrl.configDialog, ctrl),
                style: {width: "400px", height: "300px"}
            },
            ctrl.item ?
                [
                    m("form[autocomplete='off'][id='detailForm'][novalidate='']", [
                        m("table", [
                            m("tbody", [
                                m("tr", [
                                    m("td", "Omschrijving:"),
                                    m("td", [
                                        ff.input("doelgroep_type_b", {maxlength: 50, type: 'text'})
                                    ])
                                ])
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
        event.trigger("doelgroepen:dataReceived", _G_.model.doelgroepen);
    });

});