/*jslint nomen: true, debug: true, browser: true */
/*global define: false, require: false, $: true, console: false, requirejs, _ */

console.log("laad app");


define("app", [
    "ov",
    "ov/initJQueryValidate",
    "jquery-ui",
    "jquery.event.drag",
    "jquery.notify",
    "slick.core",
    "slick.grid",
    "underscore",
    "mithril"
], function (ov) {
    'use strict';
    var $notifyContainer;

    console.log("define app, ov.version: " + ov.version);

    function laadBacking(backing) {
        console.log("laadBacking");
        require([backing], function (bck) {
            if (bck) {
                if (bck.start) {
                    bck.start();
                }
                if (bck.onReady) {
                    $(function () {
                        bck.onReady();
                    });
                }
            }
        });

    }

    $('.ajax-loading').ajaxStart(function () {
        $(this).removeClass('invisible');
    }).ajaxStop(function () {
        $(this).addClass('invisible');
    }).ajaxError(function () {
        $(this).addClass('invisible');
    });

    // default setting datepicker
    $.datepicker.setDefaults({
        dateFormat: 'dd-mm-yy'
    });


    // functies definieren voor oudere javascript (chromium 25)
    if (typeof String.prototype.startsWith !== 'function') {
        String.prototype.startsWith = function (str) {
            return this.slice(0, str.length) === str;
        };
    }
    if (typeof String.prototype.endsWith !== 'function') {
        String.prototype.endsWith = function (str) {
            return this.slice(-str.length) === str;
        };
    }



    $notifyContainer = $("#notify-container");
    $notifyContainer.notify();
    $.notify = function (template, vars, opts) {
        if (typeof template !== "string") {
            opts = vars;
            vars = template;
            template = "default";
        } else if (template !== "default" && template !== "error") {
            // default: msg als eerste argument
            vars = vars || {};
            vars.text = template;
            template =  "default";
        }
        // template === "default" || template === "error"  :: no action
        return $notifyContainer.notify("create", template, vars, opts);
    };
    $.notifyError = function (vars, opts) {
        var template = "error",
            text;
        if (typeof vars === "string") {
            text = vars;
            vars = {text: text};
        }
        opts = opts || {};
        opts.expires = false;
        return $notifyContainer.notify("create", template, vars, opts);
    };

    return {
        laadBacking: laadBacking
    };
});


