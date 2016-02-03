/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([], function () {
    'use strict';

    var dossierhouders;

    // Er wordt verondersteld dat    _G_.model.dossierhouders      de lijst van dossierhouders bevat

    dossierhouders =  _.chain(_G_.model.dossierhouders)
        .map(function (dh) {
            return {
                value: dh.doss_hdr_id,
                label: dh.doss_hdr_b
            };
        })
        .unshift({
            value: "",
            label: ""
        })
        .value();

    return dossierhouders;
});