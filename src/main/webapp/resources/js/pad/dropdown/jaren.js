/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([], function () {
    'use strict';

    // Er wordt verondersteld dat    _G_.model.jaren      de lijst van jaren bevat
    var jaren =  _.chain(_G_.model.jaren)
        .map(function (jaar) {
            return {
                value: jaar,
                label: jaar
            };
        })
        .unshift({
            value: "",
            label: ""
        })
        .value();

    return jaren;
});