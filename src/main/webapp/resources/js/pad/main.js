/*jslint nomen: true, debug: true, browser: true */
/*global console: false, requirejs */

console.log("laad main.js");



requirejs.config({
    paths: {
        'mithril': '//cdnjs.cloudflare.com/ajax/libs/mithril/0.2.3/mithril',
        // 'jquery': '//ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery',
        // 'jquery-ui': '//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui',
        'jquery': '//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min',
        'jquery-ui': '//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min',
        'jquery.validate': '//cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.11.1/jquery.validate.min',
        'selectize': '//cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.1/js/standalone/selectize',

        'jquery.event.drag': '//services.ovam.be/jsrepo/slickgrid/2.2.1/lib/jquery.event.drag-2.2',
        'jquery.notify': '../jquery/jquery.notify',
        'slick.core' : '//services.ovam.be/jsrepo/slickgrid/2.2.1/slick.core',
        'slick.grid': '//services.ovam.be/jsrepo/slickgrid/2.2.1/slick.grid',

        'underscore': '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore', //-min',

        'jasmine': '../jasmine/jasmine-1.3.1/jasmine',
        'jasmine-html': '../jasmine/jasmine-1.3.1/jasmine-html',
        'RGraph.common.core': '../RGraph/libraries/RGraph.common.core',
        'RGraph.line': '../RGraph/libraries/RGraph.line',
        'RGraph.scatter': '../RGraph/libraries/RGraph.scatter',
        'RGraph.common.dynamic': '../RGraph/libraries/RGraph.common.dynamic',
        'browserplus-min': '../browserplus/browserplus-min',
        'plupload.full': '../plupload/js/plupload.full',
        'jquery.ui.plupload': '../plupload/js//jquery.ui.plupload/jquery.ui.plupload',
        'jquery.plupload.queue': '../plupload/js/jquery.plupload.queue/jquery.plupload.queue',
        'plupload-translation': '../plupload/js/i18n/nl',

        'jqxcore': '../jqwidgets/jqxcore',
        'jqxdata': '../jqwidgets/jqxdata',
        'jqxbuttons': '../jqwidgets/jqxbuttons',
        'jqxscrollbar': '../jqwidgets/jqxscrollbar',
        'jqxpanel': '../jqwidgets/jqxpanel',
        'jqxtree': '../jqwidgets/jqxtree',
        'jqxsplitter': '../jqwidgets/jqxsplitter',
        'jqxgrid': '../jqwidgets/jqxgrid',
        'jqxgrid.edit': '../jqwidgets/jqxgrid.edit',
        'jqxgrid.pager': '../jqwidgets/jqxgrid.pager',
        'jqxgrid.selection': '../jqwidgets/jqxgrid.selection',
        'jqxgrid.aggregates': '../jqwidgets/jqxgrid.aggregates',
        'jqxgrid.sort': '../jqwidgets/jqxgrid.sort',
        'jqxgrid.storage': '../jqwidgets/jqxgrid.storage',
        'jqxgrid.columnsresize': '../jqwidgets/jqxgrid.columnsresize',
        'jqxdatetimeinput': '../jqwidgets/jqxdatetimeinput',
        'jqxcalendar': '../jqwidgets/jqxcalendar',
        'jqxlistbox': '../jqwidgets/jqxlistbox',
        'jqxmenu': '../jqwidgets/jqxmenu',
        'jqxdropdownlist': '../jqwidgets/jqxdropdownlist',
        'jqxgrid.filter': '../jqwidgets/jqxgrid.filter',
        'jqx.globalize':'../jqwidgets/globalization/globalize',
        'jqx.globalize.nl-BE':'../jqwidgets/globalization/globalize.culture.nl-BE',
        'localisatieGrid': '../jqwidgets/globalization/localisatieGrid',

        'jqxknockout': '../jqwidgets/jqxknockout',
        'sammy': '../knockout/sammy',
        'ko.validation': '../knockout/knockout.validation',
        'ko.mapping': '../knockout/knockout.mapping-latest'
    },
    shim: {
        'jquery-ui': ['jquery'],
        'jquery.validate': ['jquery'],
        'jquery.notify' : ['jquery-ui'],

        'jquery.event.drag' : ['jquery'],
        'slick.core' : ['jquery.event.drag', 'jquery-ui'],
        'slick.grid': ['slick.core'],

        'underscore': {
            exports: '_'
        },

        'jasmine-html': ['jasmine'],
        'RGraph.line': ['RGraph.common.core'],
        'RGraph.common.dynamic': ['RGraph.common.core'],
        'RGraph.scatter': ['RGraph.common.core'],
        'jquery.ui.plupload': ['plupload.full'],
        'jquery.plupload.queue': ['plupload.full'],
        'plupload-translation': ['plupload.full'],

        'jqxcore': ['jquery-ui'],
        'jqxall': ['jquery-ui'],
        'jqxknockout': ['jqxcore', 'knockout'],
        'jqxdata': ['jqxcore'],
        'jqxscrollbar': ['jqxcore'],
        'jqxmenu': ['jqxcore'],
        'jqxdatetimeinput': ['jqxcore','jqxknockout'],
        'jqxcalendar': ['jqxdatetimeinput'],
        'jqxlistbox': ['jqxcore'],
        'jqxdropdownlist': ['jqxlistbox'],
        'jqxpanel': ['jqxcore'],
        'jqxsplitter': ['jqxcore'],
        'jqxbuttons': ['jqxcore'],
        'jqxtree': ['jqxcore', 'jqxscrollbar', 'jqxdata', 'jqxbuttons', 'jqxpanel'],
        'jqxgrid': ['jqxcore', 'jqxdata', 'jqxbuttons', 'jqxscrollbar', 'jqxdropdownlist', 'jqxmenu' ],
        'jqxgrid.edit': ['jqxgrid'],
        'jqxgrid.pager': ['jqxgrid'],
        'jqxgrid.storage': ['jqxgrid'],
        'jqxgrid.selection': ['jqxgrid'],
        'jqxgrid.aggregates': ['jqxgrid', 'jqxgrid.sort'],
        'jqxgrid.sort': ['jqxgrid', 'jqxmenu'],
        'jqxgrid.columnsresize': ['jqxgrid'],
        'jqxgrid.filter': ['jqxgrid'],
        'jqx.globalize.nl-BE' : ['jqx.globalize']

    },
    packages: [{
        name: "ov",
        location: "//services.ovam.be/jsrepo/ov/6.15"
        //location: "../ov"
    }, {
        name: "common",
        location: "../common"
    }],
    waitSeconds: 20   // default is 7
});



