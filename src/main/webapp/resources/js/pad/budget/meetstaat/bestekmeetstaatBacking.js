/*jslint nomen: false, debug: true, browser: true, nomen: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */


define([
    "budget/meetstaat/MeetstaatMediator"
], function (MeetstaatMediator) {
    "use strict";

    function onReady() {
        new MeetstaatMediator(window.bestekId, '/pad/s/meetstaat/upload?bestekId=' + window.bestekId);
    }

    return {
        onReady: onReady
    };
});