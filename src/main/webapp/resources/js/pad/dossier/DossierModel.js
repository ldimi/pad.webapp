/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/Model",
    "common/dossier/InstrumentenManager",
    "common/dropdown/dossier/screening/risicos"
], function (Model, InstrumentenManager, risicos) {
    'use strict';

    var DossierModel;

    DossierModel = Model.extend({
        constructor: function (dossierData) {
            Model.call(this, dossierData.dossier);

            this.initInstrumentenCollection(dossierData.instrumenten);
        },
        meta: Model.buildMeta([
            { name: "id", type: "int" },
            { name: "dossier_nr" },
            { name: "dossier_id_boa", type: "int" },
            { name: "smeg_naam" },
            { name: "smeg_id", type: "int" },
            { name: "dossier_hdr_id" },
            { name: "dossier_b" },
            { name: "dossier_type" },

            { name: "adres" },
            { name: "nis_id" },
            { name: "deelgemeente" },
            { name: "postcode" },
            { name: "land" },

            { name: "afsluit_d", type: "date" },
            { name: "commentaar" },
            { name: "dossier_fase_id", type: "int" },
            { name: "aanpak_onderzocht_s" },
            { name: "aanpak_onderzocht_l" },
            { name: "financiele_info" },
            { name: "onderzoek_id" },
            { name: "conform_bbo_d", type: "date" },
            { name: "conform_bsp_d", type: "date" },
            { name: "eindverklaring_d", type: "date" },
            { name: "sap_project_nr"},
            { name: "wbs_ivs_nr" },
            { name: "programma_code" },
            { name: "rechtsgrond_code" },
            { name: "doelgroep_type_id", type: "int" },

            { name: "smeg_naam" },
            { name: "commentaar_bodem" },

            { name: "timing_jaar", type: "int" , min: 2000, max: 2050 },
            { name: "timing_maand", type: "int" , min: 1, max: 12},

            { name: "bbo_prijs", type: "int" },
            { name: "bbo_looptijd", type: "int" },
            { name: "bsp_jn" },
            { name: "bsp_prijs", type: "int" },
            { name: "bsp_looptijd", type: "int" },
            { name: "bsw_prijs", type: "int" },
            { name: "bsw_looptijd", type: "int" },

            { name: "actueel_risico_id", type: "int" },
            { name: "beleidsmatig_risico_id", type: "int" },
            { name: "integratie_risico_id", type: "int" },
            { name: "potentieel_risico_id", type: "int" },
            { name: "prioriteits_index", type: "double" },
            { name: "prioriteits_formule" },

            { name: "overdracht_id", type: "int" }
        ]),

        initInstrumentenCollection: function(instrument_lijst) {
            instrument_lijst = instrument_lijst || [];
            this.instrumentenManager = new InstrumentenManager(instrument_lijst, this.get("dossier_id"), this.get("dossier_type"));
            this.attributes.instrument_type_id_lijst = this.instrumentenManager.getInstrument_type_id_lijst();
        },


        enforceInvariants: function () {

            if (this.get("id") === null && this.get("dossier_b") === null ) {
                this.validationError.dossier_b = 'verplicht veld';
            }

            if (this.get("aanpak_onderzocht_s") === "1" && this.get("aanpak_onderzocht_l") === null ) {
                this.validationError.aanpak_onderzocht_l = 'verplicht veld';
            }

            if (this.get("dossier_b") === null) {
                if ( this.get("afsluit_d") !== null ||
                     this.get("conform_bsp_d") !== null ||
                     this.get("eindverklaring_d") !== null ||
                     this.get("commentaar") !== null ||
                     this.get("aanpak_onderzocht_l") !== null    ) {
                    this.validationError.dossier_b = 'verplicht veld';
                }
            }

            this._invariant_timing();
            this._invariant_bsp_jn();
            this._invariant_prioriteits_index();

            this._invariant_instrumenten();

        },

        _invariant_timing: function () {
            if (this.get("timing_jaar") === null && this.get("timing_maand") !== null) {
                this.validationError.timing_jaar = "verplicht veld indien maand ingevuld.";
            }
        },

        _invariant_bsp_jn: function () {
            // raming BSP en BSW mogen aleen waarden bevatten indien bsp_jn =  'J'
            if (this.get("bsp_jn") !== 'J') {
                this.attributes.bsp_prijs = null;
                this.attributes.bsp_looptijd = null;
                this.attributes.bsw_prijs = null;
                this.attributes.bsw_looptijd = null;
            }
        },

        _invariant_prioriteits_index: function () {
            var prioriteit;
            prioriteit = risicos.calcPrioriteit(
                this.get("actueel_risico_id"),
                this.get("beleidsmatig_risico_id"),
                this.get("integratie_risico_id"),
                this.get("potentieel_risico_id")
            );

            this.attributes.prioriteits_index = prioriteit.index;
            this.attributes.prioriteits_formule = prioriteit.formule;
        },

        _invariant_instrumenten: function () {
            if (!this.instrumentenManager) {
                // initialisatie van instrumenten is nog niet gebeurd.
                return;
            }
            this.instrumentenManager.setInstrument_type_id_lijst(this.get("instrument_type_id_lijst"));
        }

    });

    return DossierModel;
});
