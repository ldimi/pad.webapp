/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, alert: false, _, m, _G_ */

define([
    "dropdown/planning/budgetCodes",
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/gridConfigBuilder",
    "ov/mithril/dialogBuilder"
], function (budgetCodes, Model, events, ajax, fhf, gridConfigBuilder, dialogBuilder) {
    'use strict';
    var _comp, _handleResponse, FaseModel, faseDialog,
        FaseDetailModel, faseDetailDialog;

    FaseModel = Model.extend({
        meta: Model.buildMeta([
            { name: "dossier_type", type: "string", label: "Dossier type", required: true },
            { name: "fase_code", type: "string", label: "Fase code", required: true},
            { name: "fase_code_b", type: "string", label: "Omschrijving", width: 150 },
            { name: "budget_code", type: "string", label: "Budget", required: true }
        ])
    });

    FaseDetailModel = Model.extend({
        meta: Model.buildMeta([
            { name: "fase_code", type: "string", hidden: true, required: true },
            { name: "fase_detail_code", type: "string", label: "Fasedetail code", required: true },
            { name: "fase_detail_code_b", label: "Omschrijving", type: "string" }
        ])
    });

    _handleResponse = function (response) {
        if (response && response.success) {
            events.trigger("fasen:dataReceived", response.result);
        } else {
            alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
        }
    };




    _comp = {
        controller: function () {
            this.faseDialogCtrl = new faseDialog.controller();
        },
        view:function (ctrl) {
            return m("div", {style: {marginLeft: "50px", align:"left"}}, [
                m("h3", "Beheer Planning fasen"),
                m("#fasenGrid", {
                    config: gridConfigBuilder({
                        model: FaseModel,
                        newBtn: true,
                        editBtn: true,
                        deleteBtn:true,
                        statusMsg: true,
                        exportCsv: true,
                        onNewClicked: function (item, faseArray) {
                            events.trigger("faseDialog:open", new FaseModel(), faseArray);
                        },
                        onEditClicked: function (item, faseArray) {
                            events.trigger("faseDialog:open", item, faseArray);
                        },
                        onDeleteClicked: function (item) {
                            // var fase = item.clone();
                            // fase.set("status_crud", 'D');
                            // saveFase(fase);
                            ajax.postJson({
                                url: '/pad/s/planningFase/delete',
                                content: item
                            }).then(_handleResponse);
                        },
                        setDataEvent: "fasen:dataReceived"
                    }),
                    style: {width: "500px", height: "90%"}
                }),

                faseDialog.view(ctrl.faseDialogCtrl)
            ]);
        }
    };

    /* -------------------------------------------------------------------------------------------------------- */

    faseDialog = dialogBuilder({
        controller: function () {
            events.on("faseDialog:open", this.open.bind(this));
            // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
            events.on("fasen:dataReceived", _.bind(this.close, this));

            this.title = "Editeer Fase";
            //this.width = 750;
            //this.height = 500;

            this.showErrors = m.prop(false);

            this.dossierTypes = [
                { value: "", label: "" },
                { value: "A", label: "Afval" },
                { value: "B", label: "Bodem" }
            ];

            this.faseDetailDialogCtrl = new faseDetailDialog.controller();




            this.preOpen = function (fase, faseArray) {
                this.fase = fase;
                this.faseArray = faseArray;
                this.showErrors(false);

                ajax.postJson({
                    url: '/pad/s/planningFaseDetails',
                    content: this.fase
                }).then(function (response) {
                    events.trigger("faseDetailGrid:setData",response.result);
                });
            };

            this.bewaar = function () {
                var status_crud, action;
                status_crud = this.fase.get("status_crud");

                this.showErrors(true);
                if (!this.fase.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }

                if (!this.validateNoDuplicate(this.fase)) {
                    $.notifyError("code is niet uniek.");
                    return;
                }

                if (status_crud === 'R') {
                    $.notify("Er zijn geen aanpassingen te bewaren.");
                    return;
                }

                if (status_crud === 'C') {
                    action = "insert";
                } else {
                    action = "update";
                }

                ajax.postJson({
                    url: '/pad/s/planningFase/' + action,
                    content: this.fase
                }).then(function (response) {
                    if (response && response.success) {
                        events.trigger("fasen:dataReceived", response.result);
                    } else {
                        alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
                    }
                });
            };

            this.validateNoDuplicate = function (fase) {
                var duplicate;
                if (fase.get("status_crud") === 'C') {
                    //code moet uniek zijn.
                    duplicate = _.find(this.faseArray, function (item) {
                        return (item.get("dossier_type") === fase.get("dossier_type") &&
                                item.get("fase_code") === fase.get("fase_code"));
                    });
                    if (duplicate) {
                        return false;
                    }
                }
                return true;
            };

        },
        view: function(ctrl) {
            var ff;
            if (!ctrl.fase) {
                return null;
            }

            ff = fhf.get().setModel(ctrl.fase).setShowErrors(ctrl.showErrors());

            return [
                m("table.formlayout", [
                    m("tr", [
                        m("td", "Dossier type:"),
                        m("td", ff.select("dossier_type", {
                            readOnly: (ctrl.fase.get("status_crud") !== 'C')
                        }, ctrl.dossierTypes))
                    ]),
                    m("tr", [
                        m("td", "Fase code:"),
                        m("td", ff.input("fase_code", {
                            readOnly: (ctrl.fase.get("status_crud") !== 'C'),
                            maxlength: 5
                        }))
                    ]),
                    m("tr", [
                        m("td", "Omschrijving:"),
                        m("td", ff.input("fase_code_b", { maxlength: 40 }))
                    ]),
                    m("tr", [
                        m("td", "Budget:"),
                        m("td", ff.select("budget_code", budgetCodes))
                    ])
                ]),
                m("div", [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "Annuleer")
                ]),
                m("br"),
                m("#faseDetailGrid", {
                    config: gridConfigBuilder({
                        model: FaseDetailModel,
                        newBtn: true,
                        editBtn: true,
                        deleteBtn:true,
                        statusMsg: true,
                        onNewClicked: function (item) {
                            var newItem;
                            newItem = new FaseDetailModel({
                                fase_code: ctrl.fase.get("fase_code"),
                                status_crud: 'C'
                            });
                            events.trigger("faseDetailDialog:open", newItem);
                        },
                        onEditClicked: function (item) {
                            events.trigger("faseDetailDialog:open", item);
                        },
                        onDeleteClicked: function (item) {
                            // var fase = item.clone();
                            // fase.set("status_crud", 'D');
                            // saveFase(fase);
                            ajax.postJson({
                                url: '/pad/s/planningDetailFase/delete',
                                content: item
                            }).then(function() {
                                alert("todo");
                            });
                        },
                        setDataEvent: "faseDetailGrid:setData"
                    }),
                    style: {width: "300px", height: "150px" }
                }),

                faseDetailDialog.view(ctrl.faseDetailDialogCtrl)
            ];
        }
    });

    /* -------------------------------------------------------------------------------------------------------- */


    faseDetailDialog = dialogBuilder({
        controller: function () {
            events.on("faseDetailDialog:open", this.open.bind(this));
            // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
            //events.on("fasen:dataReceived", _.bind(this.close, this));

            this.title = "Editeer Fase";
            //this.width = 750;
            //this.height = 500;

            this.showErrors = m.prop(false);

            this.preOpen = function (faseDetail, faseDetailArray) {
                this.faseDetailArray = faseDetailArray;
                this.faseDetail = faseDetail;
                this.showErrors(false);

            };

            this.bewaar = function () {
                var status_crud;
                status_crud = this.faseDetail.get("status_crud");

                this.showErrors(true);
                if (!this.faseDetail.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }

                alert("TODO");
            };
        },
        view: function(ctrl) {
            var ff;
            if (!ctrl.faseDetail) {
                return null;
            }

            ff = fhf.get().setModel(ctrl.faseDetail).setShowErrors(ctrl.showErrors());

            return [
                m("table.formlayout", [
                    m("tr", [
                        m("td", "Fase code:"),
                        m("td", ff.input("fase_code", { readOnly: true }))
                    ]),
                    m("tr", [
                        m("td", "Fasedetail code:"),
                        m("td", ff.input("fase_detail_code", { maxlength: 20 }))
                    ]),
                    m("tr", [
                        m("td", "Omschrijving:"),
                        m("td", ff.input("fase_detail_code_b", { maxlength: 40 }))
                    ])
                ]),
                m("div", [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "Annuleer")
                ])
            ];
        }
    });

    /* -------------------------------------------------------------------------------------------------------- */


    m.mount($("#jsviewContentDiv").get(0), _comp);

    $( document ).ready(function () {
        ajax.getJSON({
            url: '/pad/s/planningFasen'
        }).then(_handleResponse);
    });


});