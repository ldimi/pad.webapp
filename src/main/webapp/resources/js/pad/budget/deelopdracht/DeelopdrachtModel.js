/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, console: false, _:false */

define([
    "ov/Model"
], function (Model) {
    'use strict';

    var DeelopdrachtModel;

    DeelopdrachtModel = Model.extend({
        meta: Model.buildMeta([
            { name: "deelopdracht_id" },
            { name: "bestek_id" },
            { name: "offerte_id" },
            { name: "bestek_nr" },
            { name: "dossier_id", type: "int", required: true },
            { name: "bedrag", type: "double", required: true },
            { name: "goedkeuring_bedrag", type: "double" },
            { name: "schuldvordering_bedrag", type: "double" },
            // goedkeuring of afkeuring : twee waarden 'gk' of 'afk' (of null)
            { name: "goedkeuring_afkeuring"},
            { name: "goedkeuring_d", type: "date" },
            { name: "afkeuring_d", type: "date" },
            { name: "voorstel_d", type: "date" },
            { name: "afsluit_d", type: "date" },
            { name: "planning_lijn_id", type: "int", label: "Plannings Item" },
            { name: "creatie_d", type: "date" },
            { name: "raamcontract_jn" },
            { name: "ander_doss_hdr_id" },
            { name: "current_doss_hdr_id" },
            { name: "status_crud" }
        ]),

        enforceInvariants: function () {
            if (this.hasChanged("dossier_id")) {
                this.attributes.planning_lijn_id = null;
            }
            
            this.invariant_goedk_afk(); 
            
            
            //custom validatie voor plannings item : verplicht vanaf 2014
            if( this.get("planning_lijn_id") === null &&
                !window._G_isAdminArt46  &&
                (this.get("creatie_d") === null || this.get("creatie_d").getFullYear() > 2013) ) {

                this.validationError.planning_lijn_id = 'verplicht veld';
            }

            console.log(this.attributes);
        },
        
        invariant_goedk_afk: function () {
            var goedkeuring_afkeuring;
            goedkeuring_afkeuring = this.get("goedkeuring_afkeuring");
            
            if (this.hasChanged("goedkeuring_afkeuring")) {
                if (goedkeuring_afkeuring === "afk") {
                    this.attributes.goedkeuring_d = null;
                    this.attributes.afkeuring_d = new Date();
                } else if (goedkeuring_afkeuring === "gk") {
                    this.attributes.goedkeuring_d = new Date();
                    this.attributes.afkeuring_d = null;
                    this.attributes.afkeuring_opm = null;
                } else {
                    this.attributes.goedkeuring_d = null;
                    this.attributes.afkeuring_d = null;
                    this.attributes.afkeuring_opm = null;
                }
            } else {
                if (this.get("goedkeuring_d") !== null ) {
                    this.attributes.goedkeuring_afkeuring = 'gk';
                    this.attributes.afkeuring_d = null;
                } else if (this.get("afkeuring_d") !== null ) {
                    this.attributes.goedkeuring_afkeuring = 'afk';
                } else {
                    this.attributes.goedkeuring_afkeuring = null;
                }
            }
        },

        checkOpnieuwGoedkeuren: function() {
            var goedkeuring_bedrag, bedrag;

            if (this.get("raamcontract_jn") === "J" && this.get("afsluit_d") === null) {
                goedkeuring_bedrag = this.get("goedkeuring_bedrag");
                if (goedkeuring_bedrag !== null) {
                    bedrag = this.get("bedrag");
                    if (goedkeuring_bedrag + (goedkeuring_bedrag / 2) <= bedrag) {
                        return true;
                    }
                }
            }
            return false;
        }

    });

    return DeelopdrachtModel;
});