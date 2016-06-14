/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, console: false, _:false */

define([
    "ov/Model"
], function (Model) {
    'use strict';

    var DeelopdrachtHistModel = Model.extend({
        meta: Model.buildMeta([{
                name: "wijzig_ts",
                label: "Gewijzigd op",
                type: "date",
                width: 80
            }, {
                name: "dossier_b_l",
                label: "Dossier",
                width: 150
            }, {
                name: "bedrag",
                label: "Geraamd bedrag",
                type: "double",
                width: 100
            }, {
                name: "voorstel_d",
                label: "Datum opdracht",
                type: "date",
                width: 100
            }, {
                name: "afsluit_d",
                label: "Afsluiting",
                type: "date",
                width: 90
            }, {
                name: "goedkeuring_d",
                label: "Goedkeuring",
                type: "date",
                width: 90
            }, {
                name: "goedkeuring_bedrag",
                label: "Goedgekeurd bedrag",
                type: "double",
                width: 110
        }])

    });
    return DeelopdrachtHistModel;
});