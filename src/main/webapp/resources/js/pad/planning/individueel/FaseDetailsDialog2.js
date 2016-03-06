/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */

define([
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/dialogBuilder",
    "mithril",
    "underscore"
], function (Model, GridComp, events ,ajax, dialogBuilder, m, _) {
    'use strict';

    var FaseDetailsDialog , FaseDetailModel, configGrid;
    
    FaseDetailModel = Model.extend({
        meta: Model.buildMeta([
            { name: "dossier_nr",
                slickFormatter: function (row, cell, value, columnDef, item) {
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
        preOpen: function (contract_id, fase_code, readOnly) {
            this.readOnly = !!readOnly;
            
            this.title = "Fase : " + fase_code;
            
            ajax.postJSON({
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
        },
        configGrid: function (el, isInitialized) {
            var grid;
            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
                    model: FaseDetailModel,
                    editBtn: this.readOnly ? false: "Selecteer",
                    onEditClicked: function (item) {
                        events.trigger("faseDetailsDialog:selected", item);
                    }
                });
            }
            events.on("faseDetailsDialog:dataReceived", function(data) {
                grid.setData(data);
            });
        }
    
    });


    FaseDetailsDialog.view = function (ctrl) {
        return m("#faseDetailsDialog", [
            m("div",
                { style: { position: "absolute", top: "2px", left: "2px", right: "2px", bottom: "28px" },
                  class: "slick-grid-div",
                  config: ctrl.configGrid.bind(ctrl)
                }
            )
        ]);
    };
    
    return dialogBuilder(FaseDetailsDialog);
});