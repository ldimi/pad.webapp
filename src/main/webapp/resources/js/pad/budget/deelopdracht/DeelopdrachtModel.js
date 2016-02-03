/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, console: false, _:false */

define([
    "ov/Model"
], function (Model) {
    'use strict';

    var DeelopdrachtModel;

    DeelopdrachtModel = Model.extend({
        meta: Model.buildMeta([{
                name: "deelopdracht_id",
                label: "deelopdracht id"
            }, {
                name: "bestek_id",
                label: "Bestek"
            }, {
                name: "offerte_id",
                label: "Offerte"
            }, {
                name: "bestek_nr"
            }, {
                name: "dossier_id",
                type: "int",
                required: true
            }, {
                name: "bedrag",
                label: "Geraamd bedrag (incl. BTW)",
                type: "double",
                required: true
            }, {
                name: "goedkeuring_bedrag",
                type: "double"
            }, {
                name: "schuldvordering_bedrag",
                type: "double"
            }, {
                name: "goedkeuring_d",
                type: "date"
            }, {
                name: "voorstel_d",
                type: "date"
            }, {
                name: "afsluit_d",
                type: "date"
            }, {
                name: "planning_lijn_id",
                type: "int",
                label: "Plannings Item"
            }, {
                name: "creatie_d",
                type: "date"
            }, {
                name: "raamcontract_jn"
            }, {
                name: "ander_doss_hdr_id"
            }, {
                name: "current_doss_hdr_id"
            }, {
                name: "status_crud"
        }]),

        enforceInvariants: function () {
            if (this.hasChanged("dossier_id")) {
                this.attributes.planning_lijn_id = null;
            }


            //custom validatie voor plannings item : verplicht vanaf 2014
            if( this.get("planning_lijn_id") === null &&
                !window._G_isAdminArt46  &&
                (this.get("creatie_d") === null || this.get("creatie_d").getFullYear() > 2013) ) {

                this.validationError.planning_lijn_id = 'verplicht veld';
            }

            console.log(this.attributes);
        },

        checkOpnieuwGoedkeuren: function() {
            var goedkeuring_bedrag, bedrag;

            if (this.get("raamcontract_jn") === "J" && this.get("afsluit_d") === null) {
                goedkeuring_bedrag = this.get("goedkeuring_bedrag");
                if (goedkeuring_bedrag !== null && this.get("goedkeuring_d") !== null) {
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