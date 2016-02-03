/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([], function () {
    'use strict';

    var programmaTypes;

    // Er wordt verondersteld dat    _G_.model.programmaTypes      de lijst van programmaTypes bevat

    programmaTypes =  _.chain(_G_.model.programmaTypes)
        .map(function (pt) {
            return { value: pt.code, label: pt.programma_type_b };
        })
        .unshift({ value: "", label: "" })
        .value();

    return programmaTypes;
});