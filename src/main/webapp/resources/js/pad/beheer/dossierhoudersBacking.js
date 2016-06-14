/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, window, _G_, m */

define([
    "ov/mithril/ajax",
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/mithril/formhelperFactory",
    "ov/mithril/dialogBuilder",
    "common/dropdown/ja_nee_dd",
    "dropdown/programmaTypes",
    "dropdown/dossierhouders"
], function (ajax, Model, GridComp, events, fhf, dialogBuilder, ja_nee_dd, programmaTypes, dossierhouders) {
    "use strict";

    var saveDossierhouder, DossierhouderModel, dossierhoudersCollection, findDossierhouderInCollection,
        configGrid, comp, detailComp;

    DossierhouderModel = Model.extend({
        meta: Model.buildMeta([
            {
                name: "doss_hdr_id",
                label: "Dossierhouder",
                required: true,
                width: 125
            }, {
                name: "actief_jn",
                label: "Actief",
                required: true,
                default: "J",
                width: 75
            }, {
                name: "programma_type_code",
                label: "Programma",
                width: 120
            }, {
                name: "vervanger",
                label: "Vervanger",
                width: 120
            }
        ])
    });

    findDossierhouderInCollection = function (doss_hdr_id) {
        return _.find(dossierhoudersCollection, function (dh) {
            return doss_hdr_id === dh.get("doss_hdr_id");
        });
    };

    saveDossierhouder = function (dossierhouder) {
        ajax.postJson({
            url: "/pad/s/beheer/dossierhouder/save",
            content: dossierhouder
        }).then(function (response) {
            if (response && response.success) {
                events.trigger("dossierhouders:dataReceived", response.result);
            } else {
                alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
            }
        });
    };

    configGrid = function (el, isInitialized) {
        var grid;
        if (!isInitialized) {
            grid = new GridComp({
                el: el,
                model: DossierhouderModel,
                newBtn: _G_.model.isAdminArt46,
                editBtn: _G_.model.isAdminArt46,
                deleteBtn: _G_.model.isAdminArt46,
                statusMsg: true,
                exportCsv: true,
                onNewClicked: function () {
                    events.trigger("detailcomp:open", new DossierhouderModel());
                },
                onEditClicked: function (item) {
                    var dossierhouder = item.clone();
                    events.trigger("detailcomp:open", dossierhouder);
                },
                onDeleteClicked: function (item) {
                    var dossierhouder = item.clone();
                    dossierhouder.set("status_crud", 'D');
                    saveDossierhouder(dossierhouder);
                }
            });
            events.on("dossierhouders:dataReceived", function (data) {
                grid.setData(data);
                dossierhoudersCollection = grid.getData();
            });
        }
    };

    detailComp = dialogBuilder({
        controller: function () {
            events.on("detailcomp:open", this.open.bind(this));
            // na save wordt er gewacht op data, vooraleer de dialog te sluiten.
            events.on("dossierhouders:dataReceived", _.bind(this.close, this));

            this.title = "Editeer";
            //this.width = 750;
            //this.height = 500;

            this.showErrors = m.prop(false);

            this.personeelsleden = _.chain(_G_.model.personeelsleden)
                .map(function (p) {
                    return {value: p.uid, label: p.uid + ":" + p.cn };
                })
                .unshift({ value: "", label: "" })
                .value();

            this.preOpen = function (dossierhouder) {
                this.dossierhouder = dossierhouder;
                this.showErrors(false);
            };

            this.bewaar = function () {
                var status_crud;
                status_crud = this.dossierhouder.get("status_crud");

                this.showErrors(true);
                if (!this.dossierhouder.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }
                if (status_crud === "C" &&
                    findDossierhouderInCollection(this.dossierhouder.get("doss_hdr_id")) ) {
                    $.notifyError("Deze dossierhouder is al toegevoegd.");
                    return;
                }

                if (status_crud === 'R') {
                    $.notify("Er zijn geen aanpassingen te bewaren.");
                    return;
                }

                if (status_crud === 'U' || status_crud === 'C') {
                    saveDossierhouder(this.dossierhouder);
                } else {
                    alert("item heeft een ongeldige status : " + status_crud);
                    return;
                }
            };
        },
        view: function(ctrl) {
            var ff;
            if (!ctrl.dossierhouder) {
                return null;
            }

            ff = fhf.get().setModel(ctrl.dossierhouder).setShowErrors(ctrl.showErrors());

            return [
                m("table.formlayout", [
                    m("tr", [
                        m("td", "Dossierhouder:"),
                        m("td", ff.select("doss_hdr_id", {
                            readOnly: (ctrl.dossierhouder.get("status_crud") !== 'C')
                        },ctrl.personeelsleden))
                    ]),
                    m("tr", [
                        m("td", "Actief:"),
                        m("td", ff.select("actief_jn", ja_nee_dd))
                    ]),
                    m("tr", [
                        m("td", "Programma:"),
                        m("td", ff.select("programma_type_code", programmaTypes))
                    ]),
                    m("tr", [
                        m("td", "Vervanger:"),
                        m("td", ff.select("vervanger", dossierhouders))
                    ])
                ]),
                m("div", [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "Annuleer")
                ])
            ];
        }
    });


    comp = {
        controller: function() {
            this.detailCtrl = new detailComp.controller();
        },
        view: function (ctrl) {
            return m("div", {style: {marginLeft: "50px", align:"left"}}, [
                m("h3", "Beheer Dossierhouders"),
                m(".myGrid", {
                    config: configGrid,
                    style: {width: "480px", height: "90%"}
                }),

                detailComp.view(ctrl.detailCtrl)
            ]);
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

    $(document).ready(function(){
        events.trigger("dossierhouders:dataReceived", _G_.model.beheerDossierhouders);
    });

});