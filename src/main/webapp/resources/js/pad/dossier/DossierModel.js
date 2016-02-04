/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/Model"
], function (Model) {
    'use strict';

    var DossierModel;

    DossierModel = Model.extend({
        meta: Model.buildMeta([
            { name: "id", type: "int" },
            { name: "dossier_id" },
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
            { name: "commentaar_bodem" }
        ]),

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
                     this.get("aanpak_onderzocht_s") !== null ||
                     this.get("aanpak_onderzocht_l") !== null    ) {
                    this.validationError.dossier_b = 'verplicht veld';
                }
            }

        }

    });

    return DossierModel;
});
