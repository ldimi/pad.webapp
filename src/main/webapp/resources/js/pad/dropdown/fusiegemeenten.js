/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([], function () {
    'use strict';

    var fusiegemeenten;

    // Er wordt verondersteld dat    _G_.model.fusiegemeenten      de lijst van fusiegemeenten bevat

    fusiegemeenten =  _.chain(_G_.model.fusiegemeenten)
        .map(function (gem) {
            return {
                value: gem.nis_id,
                label: gem.gemeente_b
            };
        })
        .unshift({
            value: "",
            label: ""
        })
        .value();

    return fusiegemeenten;
});