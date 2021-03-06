/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/GridComp",
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (GridComp, Model, events, ajax, fhf) {
    'use strict';
    var _comp, _detailComp, VerontreinigActiviteitModel;


    VerontreinigActiviteitModel = Model.extend({
        meta: Model.buildMeta([{
                    name: "activiteit_type_id",
                    label: "Code",
                    hidden: true,
                    type : "int"
                }, {
                    name: "activiteit_type_b",
                    label: "Omschrijving",
                    width: 300,
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
                events.trigger("verontreinigActiviteiten:dataReceived", response.result);
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
                    model: VerontreinigActiviteitModel,
                    editBtn: window._G_.model.isAdminArt46,
                    newBtn: window._G_.model.isAdminArt46,
                    deleteBtn: window._G_.model.isAdminArt46,
                    onNewClicked: function () {
                        events.trigger("detailcomp:open", new VerontreinigActiviteitModel());
                    },
                    onEditClicked: function (item) {
                        events.trigger("detailcomp:open", item.clone());
                    },
                   onDeleteClicked: function (item) {
                       item.set("status_crud", "D");
                       postData('/pad/s/beheer/verontreinigActiviteitType/save', item);
                    }
                });
                events.on("verontreinigActiviteiten:dataReceived", function (data) {
                    grid.setData(data);
                });
            }
        }
    });
    _comp.view = function (ctrl) {
        return m("div", {style: {marginLeft: "50px", align:"left"}}, [
            m("h3", "Beheer verontreinig activiteiten"),
            m(".myGrid", {
                config: _.bind(ctrl.configGrid, ctrl),
                style: {width: "400px", height: "90%"}
            }),

            _detailComp.view(ctrl.dialogCtrl)
        ]);
    };



    _detailComp = {};

    _detailComp.controller = function () {

        events.on("detailcomp:open", _.bind(this.open, this));

        // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
        events.on("verontreinigActiviteiten:dataReceived", _.bind(this.close, this));

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
            postData('/pad/s/beheer/verontreinigActiviteitType/save', item);
        },
        configDialog: function (el, isInitialized) {
            if (!isInitialized) {
                this.$detailDialog = $(el).dialog({
                    autoOpen: false,
                    modal: true,
                    width: 350
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
                config: _.bind(ctrl.configDialog, ctrl)
        },
            ctrl.item ?
                [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td[width='100px']", "Omschrijving:"),
                                m("td[width='200px']", ff.input("activiteit_type_b", {maxlength: 50}))
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
        events.trigger("verontreinigActiviteiten:dataReceived", _G_.model.verontreinigActiviteiten);
    });

});