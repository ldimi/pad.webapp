/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([], function () {
    'use strict';

    var provincies;

    // Er wordt verondersteld dat    _G_.model.provincies      de lijst van provincies bevat

    provincies =  _.chain(_G_.model.provincies)
        .map(function (prov) {
            return {
                value: prov.id,
                label: prov.omschrijving
            };
        })
        .unshift({
            value: "",
            label: ""
        })
        .value();

    return provincies;
});