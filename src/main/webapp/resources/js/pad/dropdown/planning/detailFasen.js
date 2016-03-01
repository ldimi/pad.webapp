/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define(["common/dropdown/dropdownBuilder"], function (dropdownBuilder) {
    'use strict';

    var detailFasen, filterFasenFn;

    // Er wordt verondersteld dat    _G_.model.faseDetailDD      de lijst van planning detailFasen bevat
    // Deze lijst wordt dan gesplitst volgens fase_code

    filterFasenFn = function (fase_code) {
        return dropdownBuilder(_.filter(_G_.model.faseDetailDD, function (detailFase) {
                // voor dossire_type 'X', worden alle fasen in de dropdown opgenomen.
                return (detailFase.fase_code === fase_code);
        }), true);
    };

    detailFasen = {};

    detailFasen.dd = function (fase_code) {
        var dd;

        if (detailFasen[fase_code] === undefined) {
            detailFasen[fase_code] = filterFasenFn(fase_code);
        }
        return detailFasen[fase_code];
    };

    return detailFasen;
});