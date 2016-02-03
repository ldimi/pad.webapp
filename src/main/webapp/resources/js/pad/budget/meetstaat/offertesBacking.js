/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, console: false, RGraph, _G_ */


define([], function () {
    "use strict";

    window.onChangeOrganisatie_id = function (select, bestek_id, offerte_id) {
        window.location = "http://" + window.location.host +
                          "/pad/s/bestek/" + bestek_id  +
                          "/meetstaat/offerte/" + offerte_id +
                          "/koppelOrganisatie?organisatie_id=" + select.value;
    };

    return {
    };
});