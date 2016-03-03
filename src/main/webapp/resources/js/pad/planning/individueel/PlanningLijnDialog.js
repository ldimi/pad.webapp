/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */

define([
    "ov/ajax",
    "ov/events",
    "ov/form",
    "underscore"
], function (ajax, events) {
    'use strict';

    var PlanningLijnDialog;


    PlanningLijnDialog = function (lijnMeta) {
        this._lijnMeta = lijnMeta;
    };
    $.extend(PlanningLijnDialog.prototype, {

        init: function () {
            this.$dialog = $('#planningLijnDialog').dialog({
                autoOpen: false,
                modal: true,
                width: 550
            });


            this.fm = $('#planningLijnForm').ov_formManager({
                meta: this._lijnMeta
            });
            this._customiseValidation();


            this.fm.$fase_code.change($.proxy(this._onChange_fase_code, this));

            this.fm.$actie_code.change($.proxy(this._onChange_actie_code, this));
            this.fm.$contract_id.change($.proxy(this._onChange_contract_id, this));
            this.fm.$bestek_id.change($.proxy(this._onChange_bestek_id, this));

            $('#annuleerBtn').click($.proxy(this._onClick_annuleerBtn, this));
            $('#bewaarBtn').click($.proxy(this._onClick_bewaarBtn, this));

            this.fm.$igb_d.datepicker({
                changeMonth: true,
                changeYear: true,
                onSelect: function () {
                    $(this).valid();
                }
            });
        },

        show: function (lijn, planningData) {
            this._lijn = lijn.clone();
            this._orgLijn = lijn;
            this._planningData = planningData;

            this.fm.$fase_code.select('setOptionList', planningData.faseDD);
            this.fm.$fase_detail_code.select('setOptionList', planningData.faseDetailDD);
            this._setOptionListContractId();
            this._setOptionListActieCode();

            this.fm.populate(this._lijn);

            this._filterFase();
            this._filterFaseDetail();
            this._filterContractDD();

            this._fetchBestekkenDD();

            this._setInvariants();
            this.$dialog.dialog("open");
            if (this._lijn.get("ib_bedrag") || this._lijn.get("c_isReedsGekoppeld")) {
                this.fm.$igb_d.datepicker("hide");
                this.fm.$form.find("[name=commentaar]").focus();
            }
        },

        _customiseValidation: function () {
            var self, isInBoekjaar;
            self = this;

            isInBoekjaar = function () {
                var boekjaar, geplandJaar;
                if (self.fm.$igb_d.val() !== "" && self.fm.$form.validate().check(self.fm.$igb_d)) {
                    geplandJaar = self.fm.$igb_d.ov_value().getFullYear();
                    boekjaar = new Date().getFullYear();
                    return (geplandJaar <= boekjaar);
                }
                return false;
            };

            // custom validatie 'fase_detail_code'
            $.validator.addMethod("fase_detail_code_required_in_boekjaar", $.proxy(function (value) {
                return (value !== "" || this.fm.$fase_code_heeft_details_jn.val() !== 'J' || !isInBoekjaar());
            }, this), $.format("Verplicht veld in boekjaar."));

            this.fm.$fase_detail_code.rules("add", {
                fase_detail_code_required_in_boekjaar: true
            });

            // custom validatie 'bestek_id'
            $.validator.addMethod("bestek_id_required_for_HERHALING_BESTEK", $.proxy(function (value) {
                return (value !== "" || this.fm.$actie_code.val() !== "H_B");
            }, this), $.format("Verplicht veld indien 'Bestaand bestek'."));

            this.fm.$bestek_id.rules("add", {
                bestek_id_required_for_HERHALING_BESTEK: true
            });

            // actie_code
            $.validator.addMethod("actie_code_required_in_boekjaar", $.proxy(function (value) {
                return (value !== "" || !isInBoekjaar());
            }, this), $.format("Verplicht veld in boekjaar."));

            this.fm.$actie_code.rules("add", {
                actie_code_required_in_boekjaar: true
            });

            // contract_id
            $.validator.addMethod("contract_id_required_for_RC_and_GGO_in_boekjaar", $.proxy(function (value) {
                return (value !== "" || (this.fm.$actie_code.val() !== "RC" && this.fm.$actie_code.val() !== "GGO")  || !isInBoekjaar());
            }, this), $.format("Verplicht veld in boekjaar."));
            this.fm.$contract_id.rules("add", {
                contract_id_required_for_RC_and_GGO_in_boekjaar: true
            });

        },

        _setInvariants: function () {
            var actie_code, benut_bestek_id;
            this._setFaseCodeData();
            this._setContractData();
            this._setBestekData();

            if (this._lijn.get("ib_bedrag") || this._lijn.get("c_isReedsGekoppeld")) {
                // reeds benut of reeds gekoppeld met deelopdracht/aanvraagvastlegging
                this.fm.$form.find("[name]")
                    .not("[name=commentaar]")
                    .not("[name=igb_d]")
                    .attr("disabled", "disabled");
            } else {
                this.fm.$form.find("[name]").removeAttr("disabled");
                this.fm.$ibb_d.attr("disabled", "disabled");
                this.fm.$ib_bedrag.attr("disabled", "disabled");
                this.$dialog.find("button").removeClass("invisible");
            }

            if (this.fm.$fase_code_heeft_details_jn.val() === 'J') {
                $(".fase_code_detail").removeClass("invisible");
            } else {
                $(".fase_code_detail").addClass("invisible");
            }

            actie_code = this.fm.$actie_code.val();
            if (actie_code === "" || actie_code === "N_B") {
                $(".contract_id").addClass("invisible");
                $(".bestek_id").addClass("invisible");
            } else if (actie_code === "H_B") {
                $(".contract_id").addClass("invisible");
                $(".bestek_id").removeClass("invisible");
            } else if (actie_code === "GGO" || actie_code === "RC") {
                $(".contract_id").removeClass("invisible");
                if (this.fm.$contract_id.val() === "") {
                    $(".bestek_id").addClass("invisible");
                } else {
                    $(".bestek_id").removeClass("invisible");
                }
            }

            benut_bestek_id = this.fm.$benut_bestek_id.val();
            if (benut_bestek_id === "") {
                $(".benut_bestek").hide();
            } else {
                $(".benut_bestek").show();
            }

            $("#voegDeelopdrToeBtn").addClass("hidden");
        },

        _setContractData: function () {
            var selectedOption = this.fm.$contract_id.select('getSelectedOption') || {};
            this.fm.$contract_nr.val(selectedOption.contract_nr);
            this.fm.$contract_b.val(selectedOption.contract_b);
            this.fm.$contract_type.val(selectedOption.contract_type);
            this.fm.$contract_is_raamcontract_jn.val(selectedOption.raamcontract_jn);
        },

        _setFaseCodeData: function () {
            var selectedOption = this.fm.$fase_code.select('getSelectedOption') || {};
            this.fm.$fase_code_heeft_details_jn.val(selectedOption.heeft_details_jn);
        },

        _setBestekData: function () {
            var selectedOption = this.fm.$bestek_id.select('getSelectedOption') || {};
            this.fm.$bestek_nr.val(selectedOption.bestek_nr);
            this.fm.$bestek_omschrijving.val(selectedOption.omschrijving);
        },

        _onChange_fase_code: function () {
            this.fm.$fase_detail_code.val("");
            this._filterFaseDetail();
            this._setInvariants();
        },

        _onChange_actie_code: function () {
            this.fm.$contract_id.val("");
            this.fm.$bestek_id.val("");
            this.fm.$bestek_omschrijving.val("");
            this._filterContractDD();
            this._fetchBestekkenDD();
            this._setInvariants();
        },

        _onChange_contract_id: function () {
            this.fm.$bestek_id.val("");
            this._fetchBestekkenDD();
            this._setInvariants();
        },

        _onChange_bestek_id: function () {
            this.fm.$bestek_omschrijving.val("");
            this._setInvariants();
        },

        _onClick_bewaarBtn: function () {
            if (this.fm.validate()) {
                this.fm.extractTo(this._lijn);
                this._lijn.calcStatus();
                if (this._lijn.isDirty()) {
                    this._bewaar();
                } else {
                    $.notify({
                        text: "De lijn is niet gewijzigd."
                    });
                }
            }
        },

        _onClick_annuleerBtn: function () {
            this.$dialog.dialog("close");
        },

        _setOptionListContractId: function () {
            this.fm.$contract_id.select('setOptionList', this._planningData.contractenDD);
        },

        _setOptionListActieCode: function () {
            this.fm.$actie_code.select('setOptionList', [
                {
                    value: "",
                    label: ""
                }, {
                    value: "N_B",
                    label: "Nieuw enkelvoudig bestek"
                }, {
                    value: "H_B",
                    label: "Bestaand enkelvoudig bestek"
                }, {
                    value: "GGO",
                    label: "Gegroepeerde opdracht"
                }, {
                    value: "RC",
                    label: "Raamcontract"
                }
            ]);
        },

        _filterFase: function () {
            var dossier_type = this.fm.$dossier_type.val();

            this.fm.$fase_code.select('filter', function (option) {
                return (option.value === "" || dossier_type === "X" || option.dossier_type === dossier_type);
            });
        },

        _filterFaseDetail: function () {
            var fase_code = this.fm.$fase_code.val();

            this.fm.$fase_detail_code.select('filter', function (option) {
                return (option.value === "" || option.fase_code === fase_code);
            });
        },

        _filterContractDD: function () {
            var actie_code = this.fm.$actie_code.val(),
                raamcontracten_jn;
            if (actie_code === "N_B" || actie_code === "H_B") {
                raamcontracten_jn = "GEEN";
            } else if (actie_code === "RC") {
                raamcontracten_jn = "J";
            } else {
                raamcontracten_jn = "N";
            }
            this.fm.$contract_id.select('filter', function (option) {
                return (option.value === "" || option.raamcontract_jn === raamcontracten_jn);
            });


        },

        _fetchBestekkenDD: function () {
            var actie_code = this.fm.$actie_code.val(),
                contract_id = this.fm.$contract_id.val();
            if (actie_code === "N_B") {
                this.fm.$bestek_id.select('setOptionList', [], null);
            } else if (actie_code === "H_B") {
                this._fetchBestekkenDDByDossierId(this.fm.$dossier_id.val());
            } else if (contract_id === "") {
                this.fm.$bestek_id.select('setOptionList', [], null);
            } else {
                this._fetchBestekkenDDByDossierId(contract_id);
            }
        },

        _fetchBestekkenDDByDossierId: function (dossier_id) {
            if (dossier_id !== "") {
                $(".bestek_id").addClass("invisible");
                ajax.postJSON({
                    url: "/pad/s/planning/getBestekkenByDossier",
                    content: dossier_id
                }).success($.proxy(this._fillBestekkenDD, this));
            }
        },

        _fillBestekkenDD: function (optionList) {
            var actie_code = this.fm.$actie_code.val();
            if (actie_code === "GGO") {
                optionList.unshift({
                    value: "",
                    label: "via nieuw gegroepeerd bestek"
                });
            } else if (actie_code === "RC") {
                optionList.unshift({
                    value: "",
                    label: "via nieuw raamcontract bestek"
                });
            } else {
                optionList.unshift({
                    value: "",
                    label: ""
                });
            }
            this.fm.$bestek_id.select('setOptionList', optionList);
            this._setInvariants();
        },

        _bewaar: function () {
            var self = this;
            ajax.postJSON({
                url: "/pad/s/planning/bewaar",
                content: self._lijn.clone()
            }).success(function (response) {
                self._orgLijn.set(response);
                $.notify({
                    text: "De lijn is bewaard."
                });
            });

            // lokale versie wordt al aangepast (we gaan ervan uit dat bewaren toch lukt.)
            this._orgLijn.attributes = _.clone(this._lijn.attributes);
            this._orgLijn.set("planning_dossier_versie", this._orgLijn.get("planning_dossier_versie") + 1);
            if (this._orgLijn.get('status_crud') === 'C') {
                // eventueel lijnen verwijderen
                this._planningData.lijnen = _.reject(this._planningData.lijnen, function (lijn) {
                    return (lijn.get("dossier_id") === this._orgLijn.get("dossier_id") &&
                            lijn.get("ig_bedrag") === null &&
                            lijn.get("status_crud") === "C");
                }, this);
                // deze nieuwe lijn toevoegen
                this._planningData.lijnen.splice(this._planningData.selectedLijnIndex, 0, this._orgLijn);
            }
            this._orgLijn.set('status_crud', 'R');

            events.trigger("planning.lijnen:refresh");

            this.$dialog.dialog("close");
        }


    });


    return PlanningLijnDialog;
});