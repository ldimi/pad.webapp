/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define(["common/dropdown/dropdownBuilder"], function (dropdownBuilder) {
    'use strict';

    var fasen, filterFasenFn;

    // Er wordt verondersteld dat    _G_.model.faseDD      de lijst van planningFasen bevat
    // Deze lijst wordt dan gesplitst volgens dossier_type

    filterFasenFn = function (dossier_type) {
        return dropdownBuilder(_.filter(_G_.model.faseDD, function (fase) {
                // voor dossire_type 'X', worden alle fasen in de dropdown opgenomen.
                return (dossier_type === 'X' || fase.dossier_type === dossier_type);
        }), true);
    };

    fasen = {};
    fasen.B = filterFasenFn('B');
    fasen.A = filterFasenFn('A');
    fasen.X = filterFasenFn('X');

    fasen.isValid = function (fase_id, dossier_type) {
        var gevonden;
        gevonden = _.find(fasen[dossier_type] , function (fase) {
                       return (fase.value === fase_id);
                   });
        return !!gevonden;
    };

    return fasen;
});