/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, Slick: false, alert: false, jasmine, describe, beforeEach, it, expect */

define([
    "jasmine",
    "jasmine-html",
    "ov/test/ModelSpec",
    "ov/test/parsersFormattersSpec",
    "ov/test/FormSpec",
    "ov/test/selectSpec"
], function () {
    'use strict';

    // fix om href linken goed te zetten
    // (nodig omdat 'base href' gezet werd in head.)
    var org_createDom = jasmine.HtmlReporterHelpers.createDom;
    jasmine.HtmlReporterHelpers.createDom = function (type, attrs, childrenVarArgs) {
        if (attrs && attrs.href) {
            attrs.href = "/pad/s/testrunner" + attrs.href;
        }
        return org_createDom.apply(this, arguments);
    };
    jasmine.HtmlReporterHelpers.addHelpers(jasmine.HtmlReporter);
    jasmine.HtmlReporterHelpers.addHelpers(jasmine.HtmlReporter.ReporterView);
    jasmine.HtmlReporterHelpers.addHelpers(jasmine.HtmlReporter.SpecView);
    jasmine.HtmlReporterHelpers.addHelpers(jasmine.HtmlReporter.SuiteView);



    function onReady() {
        var jasmineEnv, htmlReporter;


        jasmineEnv = jasmine.getEnv();
        jasmineEnv.updateInterval = 1000;

        htmlReporter = new jasmine.HtmlReporter();
        jasmineEnv.addReporter(htmlReporter);

        jasmineEnv.specFilter = function (spec) {
            return htmlReporter.specFilter(spec);
        };

        jasmineEnv.execute();
    }

    return {
        onReady: onReady
    };
});