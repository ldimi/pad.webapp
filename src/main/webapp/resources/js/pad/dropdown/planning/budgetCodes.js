/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define(["common/dropdown/dropdownBuilder"], function (dropdownBuilder) {
    'use strict';

    // Er wordt verondersteld dat    _G_.model.budgetCodeDD      de lijst van budgetCodes bevat
    return dropdownBuilder(_G_.model.budgetCodeDD, true);

});