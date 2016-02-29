/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, console: false, _:false */

define([
    "ov/Model",
    "ov/formatters",
    "underscore"
], function (Model) {
    'use strict';

    var PlanningLijnModel, _boekjaar;

    _boekjaar = new Date().getFullYear();


    PlanningLijnModel = Model.extend({
        meta: Model.buildMeta([
            {
                name: "jaar",
                label: "Jaar",
                type: "int",
                hidden: true
            }, {
                name: "doss_hdr_id",
                label: "Dossierhouder",
                type: "string",
                hidden: true,
                width: 100
            }, {
                name: "dossier_id",
                type: "int",
                required: true,
                hidden: true
            }, {
                name: "dossier_nr",
                label: "Dossier Nr",
                width: 60,
                slickFormatter: function (row, cell, value, columnDef, item) {
                    return '<a href="s/dossier/' + item.get('dossier_id')  + '/basis" target="_blank" >' + value + '</a>';
                }
            }, {
                name: "dossier_gemeente_b",
                label: "Gemeente",
                width: 70
            }, {
                name: "dossier_b",
                label: "Titel",
                width: 180
            }, {
                name: "dossier_type",
                label: "A/B",
                width: 40
            }, {
                name: "dossier_is_raamcontract_jn",
                type: "string",
                hidden: true
            }, {
                name: "fase_code",
                label: "Fase",
                required: true,
                width: 50
            }, {
                name: "fase_looptijd",
                type: "int",
                hidden: true
            }, {
                name: "fase_code_heeft_details_jn",
                exportCsv: false,
                hidden: true
            }, {
                name: "fase_detail_code",
                label: "Fase detail",
                width: 70,
                slickFormatter: function (row, cell, value, columnDef, item) {
                    if (value === null && item.get("fase_code_heeft_details_jn") === 'J' && item.get("c_isInBoekjaar")) {
                        return '<span style="color: red;" title="verplicht veld" " >verplicht veld</span>';
                    }
                    if (value) {
                        return '<span title="' + value + '">' + value + '</span>';
                    }
                    return value;
                }
            }, {
                name: "actie_code",
                hidden: true
            }, {
                name: "contract_id",
                type: "int",
                hidden: true
            }, {
                name: "contract_is_raamcontract_jn",
                type: "string",
                hidden: true
            }, {
                name: "contract_type",
                hidden: true
            }, {
                name: "contract_nr",
                label: "Contract Nr",
                hidden: true,
                width: 70
            }, {
                name: "contract_b",
                hidden: true
            }, {
                name: "c_contract_b",
                label: "Contract  titel",
                exportCsv: false,
                width: 180
            }, {
                name: "bestek_id",
                type: "int",
                hidden: true
            }, {
                name: "bestek_nr",
                label: "Bestek",
                hidden: true,
                width: 70
            }, {
                name: "bestek_omschrijving",
                hidden: true
            }, {
                name: "c_bestek_omschrijving",
                label: "Bestek omschrijving",
                exportCsv: false,
                width: 180,
                slickFormatter: function (row, cell, value, columnDef, item) {
                    var color = "black",
                        href = "s/bestek/";
                    if (value !== null) {

                        if (item.get("benut_bestek_id")) {
                            color = "blue";
                            href = href + item.get("benut_bestek_id");
                        } else if (item.get("bestek_id")){
                            href = href + item.get("bestek_id");
                        } else {
                            // geen bestek gekend.
                            return value;
                        }

                        if (item.get("contract_id") !== null) {
                            href = href + "/deelopdrachten";
                        }

                        return '<a href="' + href + '" target="_blank" style="color: ' + color + ';" >' + value + '</a>';
                    }
                    return "";
                }
            }, {
                name: "igb_d",
                type: "date",
                label: "Gepland datum",
                required: true,
                width: 80
            }, {
                name: "ig_bedrag",
                label: "Gepland bedrag",
                required: true,
                type: "int",
                width: 80
            }, {
                name: "ib_bedrag",
                label: "Benut bedrag",
                type: "double",
                width: 80,
                slickCssClass: "blauwetekst"
            }, {
                name: "ibb_d",
                type: "date",
                label: "Benut datum",
                width: 80,
                slickCssClass: "blauwetekst"
            }, {
                name: "commentaar",
                label: "Commentaar",
                width: 200
            }, {
                name: "planning_dossier_versie",
                exportCsv: false,
                hidden: true
            }, {
                name: "lijn_id",
                exportCsv: false,
                hidden: true
            }, {
                name: "deleted_jn",
                exportCsv: false,
                hidden: true
            }, {
                name: "status_crud",
                exportCsv: false,
                hidden: true
            }
        ]),

        get: function (attr) {
            if (attr && attr.startsWith("c_")) {
                return this["_get_" + attr]();
            }
            return Model.prototype.get.call(this, attr);
        },

        _get_c_bestek_omschrijving: function () {
            if (this.get("benut_bestek_id")) {
                return "(" + this.get("benut_bestek_nr") + ") " + (this.get("benut_bestek_omschrijving") || "");
            }
            if (this.get("bestek_id") === null) {
                if (this.get("contract_id") !== null) {
                    if (this.get("contract_is_raamcontract_jn") === "J") {
                        return "Nieuw raamcontract bestek";
                    }
                    return "Nieuw gegroepeerd bestek";
                }
                return (this.get("bestek_omschrijving") || "");
            }
            return "(" + this.get("bestek_nr") + ") " + (this.get("bestek_omschrijving") || "");
        },

        _get_c_contract_b: function () {
            var dossier_is_raamcontract_jn, actie_code;
            dossier_is_raamcontract_jn = this.get("dossier_is_raamcontract_jn");
            actie_code = this.get("actie_code");
            if (this.get("fase_code") !== null) {
                // dit is een geediteerde lijn.
                if (this.get("contract_id") === null) {
                    if (actie_code === "H_B") {
                        if (dossier_is_raamcontract_jn === "J") {
                            return "Bestaand bestek";
                        }
                        if (dossier_is_raamcontract_jn === "N") {
                            return "Bestaand bestek";
                        }
                        return "Bestaand enkelvoudig bestek";
                    }
                    if (this.get("bestek_id") === null) {
                        if (actie_code === null || actie_code === "") {
                            return "";
                        }
                        if (actie_code === 'N_B') {
                            if (dossier_is_raamcontract_jn === "J") {
                                return "Nieuw raamcontract bestek";
                            }
                            if (dossier_is_raamcontract_jn === "N") {
                                return "Nieuw gegroepeerd bestek";
                            }
                            return "Nieuw enkelvoudig bestek";
                        }
                        if (actie_code === 'RC') {
                            return "Raamcontract";
                        }
                        if (actie_code === 'GGO') {
                            return "Gegroepeerde opdracht";
                        }
                        return "** ERROR **";
                    }
                }
            }
            return this.get("contract_b");
        },

        _get_c_isInBoekjaar: function () {
            var geplandJaar;
            geplandJaar = this.get("igb_d").getFullYear();
            return (geplandJaar <= _boekjaar);
        },

        _get_c_isReedsGekoppeld: function () {
            return !!(this.get("deelopdracht_id") || this.get("aanvraagvastlegging_id"));
        },

        // aanmaken van een nieuw item,
        // waarbij een aantal gegevens van dit item overgenomen worden.
        createNewLine: function () {
            var newAttrs;
            newAttrs = _.pick(this.attributes, 'dossier_id', 'dossier_nr', 'dossier_gemeente_b', 'dossier_b', 'dossier_type', 'dossier_is_raamcontract_jn', 'doss_hdr_id');
            newAttrs.deleted_jn = "N";
            newAttrs.status_crud=  "C";
            return new PlanningLijnModel(newAttrs);
        },

        calcStatus: function () {
            if (this.isDirty()) {
                if (this.get("status_crud") === 'R') {
                    this.set("status_crud", 'U');
                    //status_crud , mag het vershil niet maken bij dirtycheck;
                    this._orgAttributes.status_crud = 'U';
                }
            } else {
                if (this.get("status_crud") === 'U') {
                    this.set("status_crud", 'R');
                    this._orgAttributes.status_crud = 'R';
                }
            }
        }

    });



    return PlanningLijnModel;
});