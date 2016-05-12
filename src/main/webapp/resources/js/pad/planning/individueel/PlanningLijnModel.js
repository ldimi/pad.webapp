/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, console: false, _:false, _G_ */

define([
    "dropdown/planning/fasen",
    "ov/Model",
    "ov/events",
    "ov/formatters",
    "underscore"
], function (fasen, Model, events, formatters) {
    'use strict';

    var PlanningLijnModel, _boekjaar, intFormatter;

    intFormatter = formatters("int");
    _boekjaar = new Date().getFullYear();


    PlanningLijnModel = Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", label: "Jaar", type: "int", hidden: true },
            { name: "doss_hdr_id", label: "Dossierhouder", type: "string", hidden: true, width: 100 },
            { name: "dossier_id", type: "int", required: true, hidden: true },
            { name: "dossier_nr", label: "Dossier Nr", width: 60,
                gridFormatter: function (value) {
                    return '<a href="s/dossier/' + this.get('dossier_id')  + '/basis" target="_blank" >' + value + '</a>';
                }
            },
            { name: "dossier_gemeente_b", label: "Gemeente", width: 70 },
            { name: "dossier_b", label: "Titel", width: 180 },
            { name: "dossier_type", label: "A/B", width: 40 },
            { name: "dossier_is_raamcontract_jn", type: "string", hidden: true },
            { name: "fase_code", label: "Fase", required: true, width: 50 },
            { name: "fase_budget_code", label: "FBC", width: 50 },
            { name: "budget_code", label: "BC", required: true, width: 50 },
            { name: "fase_prijs", type: "int", hidden: true },
            { name: "fase_looptijd", type: "int", hidden: true },
            { name: "fase_code_heeft_details_jn", exportCsv: false, hidden: true },
            { name: "fase_detail_code", label: "Fase detail", width: 70,
                gridFormatter: function (value) {
                    if (value === null && this.get("fase_code_heeft_details_jn") === 'J' && this.get("c_isInBoekjaar")) {
                        return '<span style="color: red;" title="verplicht veld" " >verplicht veld</span>';
                    }
                    if (value) {
                        return '<span title="' + value + '">' + value + '</span>';
                    }
                    return value;
                }
            },
            { name: "actie_code", hidden: true },
            { name: "contract_id", type: "int", hidden: true },
            { name: "contract_is_raamcontract_jn", type: "string", hidden: true },
            { name: "contract_type", hidden: true },
            { name: "contract_nr", label: "Contract Nr", hidden: true, width: 70 },
            { name: "contract_b", hidden: true },
            { name: "c_contract_b", label: "Contract  titel", exportCsv: false, width: 180 },
            { name: "bestek_id", type: "int", hidden: true },
            { name: "bestek_nr", label: "Bestek", hidden: true, width: 70 },
            { name: "bestek_omschrijving", hidden: true },
            { name: "c_bestek_omschrijving", label: "Bestek omschrijving", exportCsv: false, width: 180,
                gridFormatter: function (value) {
                    var color = "black",
                        href = "s/bestek/";
                    if (value !== null) {

                        if (this.get("benut_bestek_id")) {
                            color = "blue";
                            href = href + this.get("benut_bestek_id");
                        } else if (this.get("bestek_id")){
                            href = href + this.get("bestek_id");
                        } else {
                            // geen bestek gekend.
                            return value;
                        }

                        if (this.get("contract_id") !== null) {
                            href = href + "/deelopdrachten";
                        }

                        return '<a href="' + href + '" target="_blank" style="color: ' + color + ';" >' + value + '</a>';
                    }
                    return "";
                }
            },
            { name: "benut_bestek_id", type: "int", hidden: true },
            { name: "benut_bestek_nr", type: "string", hidden: true },
            { name: "benut_bestek_omschrijving", type: "string", hidden: true },
            { name: "igb_d", type: "date", label: "Gepland datum", required: true, width: 80,
                gridFormatter: function (value) {
                    if (value !== null) {
                        return this.str("igb_d");
                    }
                    if (this.get("fase_looptijd") !== null) {
                        return '<span style="color: green;" >' + this.get("fase_looptijd") + ' maanden</span>';
                    }
                    return "";
                }
            },
            { name: "ig_bedrag", label: "Gepland bedrag", required: true, type: "int", width: 80,
                gridFormatter: function (value) {
                    if (value !== null) {
                        return intFormatter(this.get("ig_bedrag"), ".");
                    }
                    if (this.get("fase_prijs") !== null) {
                        return '<span style="color: green;" >' + intFormatter(this.get("fase_prijs") , ".") + '</span>';
                    }
                    return "";
                }
            },
            { name: "ib_bedrag", label: "Benut bedrag", type: "double", width: 80, slickCssClass: "blauwetekst" },
            { name: "ibb_d", type: "date", label: "Benut datum", width: 80, slickCssClass: "blauwetekst" },
            { name: "commentaar", label: "Commentaar", width: 200 },
            { name: "planning_dossier_versie", exportCsv: false, hidden: true },
            { name: "lijn_id", exportCsv: false, hidden: true },
            { name: "deleted_jn", exportCsv: false, hidden: true },
            { name: "status_crud", exportCsv: false, hidden: true }
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

        enforceInvariants: function () {

            this._validate_fase_detail_code_required_in_boekjaar();
            this._validate_bestek_id_required_for_HERHALING_BESTEK();
            this._validate_actie_code_required_in_boekjaar();
            this._validate_contract_id_required_for_RC_and_GGO_in_boekjaar();

            this._invariant_contract_id();


            if (this.hasChanged("actie_code")) {
                this.attributes.contract_id = null;
                this.attributes.bestek_id = null;
                events.trigger("planningLijnModel.actie_code:changed");
            } else if (this.hasChanged("contract_id")) {
                this.attributes.bestek_id = null;
                events.trigger("planningLijnModel.contract_id:changed");
            }

        },

        _invariant_contract_id: function () {
            var contract_id, contract;
            if (this.hasChanged("contract_id")) {
                contract_id =  this.get("contract_id");
                if (contract_id !== null) {
                    contract = _(_G_.contractenDD).findWhere({value: contract_id });
                    this.attributes.contract_type = contract.contract_type;
                } else {
                    this.attributes.contract_type =  null;
                }
            }
        },

        _validate_fase_detail_code_required_in_boekjaar: function () {
            if (this.isInBoekjaar() &&
                this.get("fase_code") !== null &&
                fasen.heeft_details_jn(this.get("fase_code")) === 'J' &&
                this.get("fase_detail_code") === null )  {

                this.validationError.fase_detail_code = "Verplicht veld in boekjaar.";
            }
        },

        _validate_bestek_id_required_for_HERHALING_BESTEK: function () {
            if (this.get("actie_code") === "H_B" && this.get("bestek_id") === null) {
                this.validationError.bestek_id = "Verplicht veld indien 'Bestaand bestek'.";
            }
        },

        _validate_actie_code_required_in_boekjaar: function () {
            if (this.isInBoekjaar() && this.get("actie_code") === null) {
                this.validationError.actie_code = "Verplicht veld in boekjaar.";
            }
        },

        _validate_contract_id_required_for_RC_and_GGO_in_boekjaar: function() {
            if (this.isInBoekjaar() &&
                _.contains(["RC", "GGO"], this.get("actie_code")) &&
                this.get("contract_id") === null)                       {

                this.validationError.contract_id = "Verplicht veld in boekjaar.";
            }
        },

        isInBoekjaar: function () {
            var boekjaar, geplandJaar;
            if (this.get("igb_d") !== null && this.isValid("igb_d")) {
                geplandJaar = this.get("igb_d").getFullYear();
                boekjaar = new Date().getFullYear();
                return (geplandJaar <= boekjaar);
            }
            return false;
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