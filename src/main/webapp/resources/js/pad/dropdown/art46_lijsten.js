/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([], function () {
    'use strict';

    var art46_lijsten;

    // Er wordt verondersteld dat    _G_.model.art46_lijsten      de lijst van art46_lijsten bevat

    art46_lijsten =  _.chain(_G_.model.art46_lijsten)
        .map(function (lijst) {
            return {
                value: lijst.lijst_id,
                label: lijst.lijst_b
            };
        })
        .unshift({
            value: "",
            label: ""
        })
        .value();

    return art46_lijsten;
});