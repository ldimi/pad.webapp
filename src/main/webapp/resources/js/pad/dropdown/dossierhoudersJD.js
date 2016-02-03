/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([], function () {
    'use strict';

    var dossierhoudersJD;

    // Er wordt verondersteld dat    _G_.model.dossierhoudersJD      de lijst van dossierhoudersJD bevat

    dossierhoudersJD =  _.chain(_G_.model.dossierhoudersJD)
        .map(function (dh) {
            return {
                value: dh.ambtenaar_id,
                label: dh.ambtenaar_b
            };
        })
        .unshift({
            value: "",
            label: ""
        })
        .value();

    return dossierhoudersJD;
});