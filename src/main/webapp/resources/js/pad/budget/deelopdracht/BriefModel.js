/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, console: false, _:false */

define([
    "ov/Model"
], function (Model) {
    'use strict';

    var BriefModel = Model.extend({
        meta: Model.buildMeta([{
                name: "commentaar",
                label: "Bestand omschrijving",
                required: true,
                width: 410
            }, {
                name: "brief_nr",
                label: "Nummer",
                width: 100,
                type: "string"
            }, {
                name: "brief_id",
                type: "int",
                hidden: true
        }])
    });

    return BriefModel;
});