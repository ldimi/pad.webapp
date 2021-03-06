/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */

define([
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/dialogBuilder",
    "ov/mithril/gridConfigBuilder",
    "mithril",
    "underscore"
], function (Model, GridComp, events ,ajax, dialogBuilder, gridConfigBuilder, m, _) {
    'use strict';

    var BestekDetailsDialog , BestekDetailModel;

    BestekDetailModel = Model.extend({
        meta: Model.buildMeta([
            { name: "bestek_id", hidden: true },
            { name: "bestek_nr", hidden: true },
            { name: "dossier_id", type: "int", hidden: true },
            { name: "dossier_nr", width: 70,
                gridFormatter: function (value) {
                    return '<a href="s/dossier/' + this.get("dossier_id")  + '/basis" target="_blank" >' + value + '</a>';
                }
            },
            { name: "dossier_b", label: "Dossier Titel", width: 300 },
            { name: "doss_hdr_id", label: "Doss. houder" },
            { name: "igb_d", label: "Gepland datum", type: "date" },
            { name: "ig_bedrag", label: "Gepland bedrag", type: "double" },
            { name: "bedrag", label: "Bedrag deelopdracht", type: "double" }
        ])
    });

    BestekDetailsDialog = {};

    BestekDetailsDialog.controller = function () {

        this.title = "Bestek";
        this.width = 800;
        this.height = 400;

        this.showErrors = m.prop(false);

        events.on("bestekDetailsDialog:open", this.open.bind(this));
    };
    _.extend(BestekDetailsDialog.controller.prototype, {
        preOpen: function (bestek_id, bestek_nr, selectCb) {
            this.selectCb = selectCb;

            this.title = "Bestek : " + bestek_nr;

            ajax.postJson({
                url: "/pad/s/planning/getDetailsVoorBestek",
                content: bestek_id
            }).then(function (response) {
                if (response) {
                    if (selectCb) {
                        response = _.filter(response, function (detail) {
                                        return (detail.ig_bedrag !== null);
                                    });
                    }
                    events.trigger("bestekDetailsDialog:dataReceived", response);
                }
            });

            this.showErrors(false);
        }
    });


    BestekDetailsDialog.view = function (ctrl) {
        return m("#bestekDetailsDialog", [
            m("div",
                { style: { position: "absolute", top: "2px", left: "2px", right: "2px", bottom: "28px" },
                  config: gridConfigBuilder({
                                model: BestekDetailModel,
                                editBtn: ctrl.selectCb ? "Selecteer" : false,
                                onEditClicked: function (item) {
                                    if (ctrl.selectCb) { ctrl.selectCb(item);}
                                    ctrl.close();
                                },
                                setDataEvent: "bestekDetailsDialog:dataReceived"
                            })
                }
            )
        ]);
    };

    return dialogBuilder(BestekDetailsDialog);
});