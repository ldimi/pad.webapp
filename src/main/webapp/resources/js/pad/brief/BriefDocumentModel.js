/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ , console */

define([
    "ov/Model"
], function (Model) {
    'use strict';
    var BriefDocumentModel;

    BriefDocumentModel = Model.extend({
        meta: Model.buildMeta([{
                name: "type",
                width: 50
            }, {
                name: "brief_id",
                hidden: true
            }, {
                name: "brief_nr",
                label: "Briefnummer",
                hidden: true,
                width: 180
            }, {
                name: "dms_folder",
                width: 180
            }, {
                name: "dms_filename",
                width: 180
            }, {
                name: "dms_id",
                label: " ",
                width: 30,
                slickFormatter: function (row, cell, value, columnDef, item) {
                    return '<a href="' + _G_.dms_webdrive_base + item.get('dms_folder') + '/' + item.get('dms_filename') + '" target="_blank" >' +
                           '<img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>' +
                           '</a>';
                }
            }])
    });
    return BriefDocumentModel;
});
