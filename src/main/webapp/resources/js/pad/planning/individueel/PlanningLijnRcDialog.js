/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console */

define([
    "planning/individueel/PlanningLijnDialog",
    "planning/individueel/bestekDetailsDialog",
    "planning/individueel/faseDetailsDialog",
    "ov/form"
], function (PlanningLijnDialog, bestekDetailsDialog, faseDetailsDialog) {
    'use strict';

    var PlanningLijnRcDialog;

    PlanningLijnRcDialog = function (lijnMeta) {
        PlanningLijnDialog.call(this, lijnMeta);
    };
    PlanningLijnRcDialog.prototype = new PlanningLijnDialog();
    PlanningLijnRcDialog.prototype.constructor = PlanningLijnRcDialog;
    $.extend(PlanningLijnRcDialog.prototype, {

        // extend
        init: function () {
            PlanningLijnDialog.prototype.init.call(this);

            $('#voegDeelopdrToeBtn').click($.proxy(this._onClick_voegDeelopdrToeBtn, this));
        },

        // override
        _setOptionListContractId: function () {
            // noop;
        },

        // override
        _setOptionListActieCode: function () {
            var optionList = [];
            if (this._lijn.get("dossier_is_raamcontract_jn") === 'J') {
                optionList.push({
                    value: "N_B",
                    label: "Nieuw raamcontract bestek"
                });
            } else {
                optionList.push({
                    value: "N_B",
                    label: "Nieuw gegroepeerd bestek"
                });
            }
            optionList.push({
                value: "H_B",
                label: "Bestaand bestek"
            });
            this.fm.$actie_code.select('setOptionList', optionList);
        },

        // override
        _setContractData: function () {
            // noop;
        },

        // extend
        _setInvariants: function () {
            var actie_code;
            PlanningLijnDialog.prototype._setInvariants.call(this);

            $(".contract_id").addClass("hidden");

            actie_code = this.fm.$actie_code.val();
            if (actie_code === "" || actie_code === "N_B") {
                $(".bestek_id").addClass("hidden");
            } else if (actie_code === "H_B") {
                $(".bestek_id").removeClass("hidden");
            }

            if (this._lijn.get("dossier_is_raamcontract_jn") === 'J') {
                $("#voegDeelopdrToeBtn").addClass("hidden");
            } else {
                if (actie_code === "N_B" && this.fm.$fase_code.ov_value() !== null) {
                    $("#voegDeelopdrToeBtn").removeClass("hidden");
                } else if (actie_code === "H_B" && this.fm.$bestek_id.ov_value() !== null) {
                    $("#voegDeelopdrToeBtn").removeClass("hidden");
                } else {
                    $("#voegDeelopdrToeBtn").addClass("hidden");
                }
            }

        },


        // override
        _fetchBestekkenDD: function () {
            var actie_code = this.fm.$actie_code.val();
            if (actie_code === "N_B") {
                this.fm.$bestek_id.select('setOptionList', [], null);
            } else if (actie_code === "H_B") {
                this._fetchBestekkenDDByDossierId(this.fm.$dossier_id.val());
            }
        },

        _onClick_voegDeelopdrToeBtn: function () {
            var actie_code, fase_code, bestek_id, self;
            self = this;

            if (this._lijn.get("dossier_is_raamcontract_jn") !== 'J') {
                actie_code = this.fm.$actie_code.val();
                fase_code = this.fm.$fase_code.ov_value();
                bestek_id = this.fm.$bestek_id.ov_value();

                if (actie_code === "N_B" && fase_code !== null) {
                    faseDetailsDialog.show(this.fm.$dossier_id.ov_value(), fase_code,  function (item) {
                        var omschrijving, commentaar_org;
                        commentaar_org = self.fm.$commentaar.val();
                        omschrijving = "( " + item.dossier_nr + " " + item.dossier_b +
                                        " : " + item.ig_bedrag + " )";
                        if (commentaar_org !== null && commentaar_org !== "") {
                            omschrijving = "\n" + omschrijving;
                        }
                        self.fm.$commentaar.val(commentaar_org + omschrijving);
                    });
                } else if (actie_code === "H_B" && bestek_id !== null) {
                    bestekDetailsDialog.show(bestek_id, this.fm.$bestek_nr.ov_value(), function (item) {
                        var omschrijving, commentaar_org;
                        commentaar_org = self.fm.$commentaar.val();
                        omschrijving = "( " + item.dossier_nr + " " + item.dossier_b + ", " +
                                       item.bestek_nr + " : " + item.ig_bedrag + " )";
                        if (commentaar_org !== null && commentaar_org !== "") {
                            omschrijving = "\n" + omschrijving;
                        }
                        self.fm.$commentaar.val(commentaar_org + omschrijving);
                    });
                }
            }
        }

    });



    return PlanningLijnRcDialog;
});