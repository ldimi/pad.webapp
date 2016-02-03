/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, confirm: false, _G_:true, console */
define([],
function () {
    "use strict";

    var checked = false;

    function onReady() {
        window.isGeprint = function (id, ev) {
            if (confirm('Brief antwoord schuldvordering ' + id + ' is correct afgedrukt?') === true) {
                window.location = "http://" + window.location.host + "/pad/s/afgedrukt/" + id;
            }
        };
        window.checkedAll = function () {
            var i;
            if (checked === false) {
                checked = true;
            } else {
                checked = false;
            }
            for (i = 0; i < document.getElementById('schuldvorderingenform').elements.length; i = i + 1) {
                document.getElementById('schuldvorderingenform').elements[i].checked = checked;
            }
        };
    }

    return {
        onReady: onReady
    };
});