/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */

define([
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/dialogBuilder",
    "ov/mithril/gridConfigBuilder",
    "mithril",
    "underscore"
], function (Model, events ,ajax, dialogBuilder, gridConfigBuilder, m, _) {
    'use strict';

    var FaseDetailsDialog , FaseDetailModel;

    FaseDetailModel = Model.extend({
        meta: Model.buildMeta([
            { name: "dossier_nr",
                gridFormatter: function (value) {
                    return '<a href="dossierdetailsArt46.do?dossier_nr=' + value + '" target="_blank" >' + value + '</a>';
                }
            },
            { name: "dossier_b", label: "Dossier Titel", width: 300 },
            { name: "doss_hdr_id", label: "Dossierhouder" },
            { name: "igb_d", label: "Gepland datum", type: "date" },
            { name: "ig_bedrag", label: "Gepland bedrag", type: "double" }
        ])
    });

    FaseDetailsDialog = {};

    FaseDetailsDialog.controller = function () {

        this.title = "Fasen";
        this.width = 800;
        this.height = 400;

        this.showErrors = m.prop(false);

        events.on("faseDetailsDialog:open", this.open.bind(this));
    };
    _.extend(FaseDetailsDialog.controller.prototype, {
        preOpen: function (contract_id, fase_code, selectCb) {
            this.selectCb = selectCb;

            this.title = "Fase : " + fase_code;

            ajax.postJson({
                url: "/pad/s/planning/getDetailsVoorFase",
                content: {
                    contract_id: contract_id,
                    fase_code: fase_code
                }
            }).then(function (response) {
                if (response) {
                    events.trigger("faseDetailsDialog:dataReceived", response);
                }
            });

            this.showErrors(false);
        }

    });


    FaseDetailsDialog.view = function (ctrl) {
        return m("div",
                { style: { position: "absolute", top: "2px", left: "2px", right: "2px", bottom: "28px" },
                  config: gridConfigBuilder({
                                model: FaseDetailModel,
                                editBtn: ctrl.selectCb ? "Selecteer" :false,
                                onEditClicked: function (item) {
                                    if (ctrl.selectCb) { ctrl.selectCb(item);}
                                    ctrl.close();
                                },
                                setDataEvent: "faseDetailsDialog:dataReceived"
                            })
                }
            );
        //]);
    };

    return dialogBuilder(FaseDetailsDialog);
});