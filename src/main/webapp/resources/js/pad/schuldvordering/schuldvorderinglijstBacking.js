/*jslint debug: true, browser: true */
/*global define: false, $: false, alert: false, console:false, Slick, _ , _G_*/

define([
    'schuldvordering/editSchuldVorderingDialog'
], function (editSchuldVorderingDialog) {
    'use strict';


    $(function () {
        if (_G_ && _G_.vordering_id) {
            editSchuldVorderingDialog.show(_G_.vordering_id);
        }
    });

});