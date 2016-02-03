/*jslint nomen: false, debug: true, browser: true, nomen: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console */


define([
    "budget/meetstaat/MeetstaatMediator"
], function (MeetstaatMediator) {
    "use strict";

    function onReady() {
        var mediator;

        _.extend(MeetstaatMediator.prototype, {
            createNewItem: function () {
                return {
                    templateId: this._bestekOrMeetstaatId,
                    crudStatus: 'C'
                };
            },

            getMeetstaatUrl: function () {
                return '/pad/s/beheer/meetstaat/template/' + this._bestekOrMeetstaatId + '/regels/';
            },

            getHerberekenUrl: function () {
                return "/pad/s/beheer/meetstaat/template/herbereken";
            },

            getVerplaatsUrl: function (huidig, nieuw) {
                return "/pad/s/beheer/meetstaat/template/verplaats/" + huidig + "/" + nieuw + "/";
            },

            getAddUrl: function() {
                return "/pad/s/beheer/meetstaat/template/add";
            },

            getSaveUrl: function() {
                return "/pad/s/beheer/meetstaat/template/save/" + $('#naam').val() +"/";
            }

        });
        var url = '/pad/s/beheer/meetstaat/template/upload/'+window.templateId;
        if (typeof window.templateId == 'undefined'){
            url = '/pad/s/beheer/meetstaat/template/upload/'+0;
        }
        mediator = new MeetstaatMediator(window.templateId,url);
    }

    return {
        onReady: onReady
    };
});