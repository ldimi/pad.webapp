/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, console: false, RGraph, _G_ */

define([
], function () {
    'use strict';

    var onReady;


    onReady = function () {
        console.log("detailoverzichtBacking ready");
        $("input[name$='_d']").datepicker({
            changeMonth: true,
            changeYear: true
        });
    };

    return {
        onReady: onReady
    };
});