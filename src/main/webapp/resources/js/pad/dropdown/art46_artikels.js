/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([], function () {
    'use strict';

    var art46_artikels;

    // Er wordt verondersteld dat    _G_.model.art46_artikels      de lijst van art46_artikels bevat

    art46_artikels =  _.chain(_G_.model.art46_artikels)
        .map(function (art) {
            return {
                value: art.artikel_id,
                label: art.artikel_b
            };
        })
        .unshift({
            value: "-1",
            label: "Alle Artikels"
        })
        .unshift({
            value: "",
            label: ""
        })
        .value();

    return art46_artikels;
});