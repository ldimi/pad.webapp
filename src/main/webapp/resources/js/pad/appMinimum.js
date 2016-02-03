/*jslint nomen: true, debug: true, browser: true */
/*global define: false, require: false, $: true, console: false */

console.log("laad appMinimum");

define("knockout", function () {
    'use strict';
    if (ko) {

        console.log("laden knockout");


        // Formatting Functions
        var formatWithComma = function (x, precision, seperator) {
            var options = {
                precision: precision || 2,
                seperator: seperator || ','
            }
        /*  met default afrondingen ...
            var formatted = parseFloat(x,10).toFixed( options.precision );
            var regex = new RegExp(
                '^(\\d+)[^\\d](\\d{' + options.precision + '})$');
            formatted = formatted.replace(
                regex, '$1' + options.seperator + '$2');
            return formatted;*/


            var formatted = parseFloat(x,10).toString();
            var regex = new RegExp(
                '^(\\d+)[^\\d](\\d+)$');
            formatted = formatted.replace(
                regex, '$1' + options.seperator + '$2');
            return formatted;

        }

        var reverseFormat = function (x, precision, seperator) {
            var options = {

                seperator: seperator || ','
            }
            var regex = new RegExp(
                '^(\\d+)[^\\d](\\d+)$');
            var formatted = x.replace(regex, '$1.$2');
            return parseFloat(formatted);
        }
        // END: Formatting Functions

        /*

         Explanation of the RegExp used above:

         ^(\\d+)[^\\d](\\d{' + options.precision + '})$$

         ^        = pattern must match from the start of the string

         (\\d+)   = 1 or more digits
         - the brackets capture the matched string
         to be used in the replace function as $1

         [^\\d]   = any character but a digit
         - this includes letters, whitespace and
         punctutation (any character but 0-9)

         (\\d{X}) = X number of digits (above we use options.precision)
         - the brackets capture the matched string
         to be used in the replace function as $2

         $        = pattern must match to the end of the string
         - here we've used ^...$ so the pattern must
         match the entire string
        */

        // Custom Binding - place this in a seperate .js file and reference it in your html
        ko.bindingHandlers.commaDecimalFormatter = {
            init: function (element, valueAccessor) {

                var observable = valueAccessor();

                var interceptor = ko.computed({
                    read: function () {
                        return formatWithComma(observable());
                    },
                    write: function (newValue) {
                        observable(reverseFormat(newValue));
                    }
                });

                if (element.tagName == 'INPUT')
                    ko.applyBindingsToNode(element, {
                        value: interceptor
                    });
                else
                    ko.applyBindingsToNode(element, {
                        text: interceptor
                    });
            }
        }

    } else {
        throw "knockout is niet geladen.";
    }
    return ko;
});



define([
    "ov",
    "ov/initJQueryValidate",
    "jquery-ui",
    "jquery.validate",
    "jquery.notify"
], function (ov) {
    'use strict';
    var $notifyContainer;

    console.log("define app, ov.version: " + ov.version);

    function laadBacking(backing) {
        console.log("laadBacking");
        require([backing], function (bck) {
            if (bck.start) {
                bck.start();
            }
            $(function () {
                bck.onReady();
            });
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
        }
        return $notifyContainer.notify("create", template, vars, opts);
    };

    return {
        laadBacking: laadBacking
    };
});