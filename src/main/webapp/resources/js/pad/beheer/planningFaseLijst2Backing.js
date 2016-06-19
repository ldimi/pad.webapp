/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, alert: false, _, m, _G_ */

define([
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/mithril/gridConfigBuilder",
    "ov/mithril/dialogBuilder"
], function (Model, events, ajax, fhf, gridConfigBuilder, dialogBuilder) {
    'use strict';
    var _comp, _handleResponse, FaseModel, FaseDetailModel, faseDialog;

    FaseModel = Model.extend({
        meta: Model.buildMeta([
            { name: "dossier_type", type: "string", label: "Dossier type", required: true },
            { name: "fase_code", type: "string", label: "Fase code", required: true},
            { name: "fase_code_b", type: "string", label: "Omschrijving" },
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
            events.trigger("fasenGrid:setData", response.result);
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
                        setDataEvent: "fasenGrid:setData"
                    }),
                    style: {width: "500px", height: "90%"}
                }),
    
                faseDialog.view(ctrl.faseDialogCtrl)
            ]);
        }
    };

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

            this.preOpen = function (fase) {
                this.fase = fase;
                this.showErrors(false);
                
                ajax.postJson({
                    url: '/pad/s/planningFaseDetails',
                    content: this.fase
                }).then(function (response) {
                    events.trigger("faseDetailGrid:setData",response.result);
                });
            };

            this.bewaar = function () {
                var status_crud;
                status_crud = this.fase.get("status_crud");

                this.showErrors(true);
                if (!this.fase.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }
                //if (status_crud === "C" &&
                //    findFaseInCollection(this.fase.get("doss_hdr_id")) ) {
                //    $.notifyError("Deze fase is al toegevoegd.");
                //    return;
                //}
                //
                //if (status_crud === 'R') {
                //    $.notify("Er zijn geen aanpassingen te bewaren.");
                //    return;
                //}
                //
                //if (status_crud === 'U' || status_crud === 'C') {
                //    saveFase(this.fase);
                //} else {
                //    alert("item heeft een ongeldige status : " + status_crud);
                //    return;
                //}
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
                            events.trigger("faseDetailDialog:open", new FaseDetailModel());
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
                })

            ];
        }
    });


    m.mount($("#jsviewContentDiv").get(0), _comp);
    
    $( document ).ready(function () {
        ajax.getJSON({
            url: '/pad/s/planningFasen'
        }).then(_handleResponse);
    });


});