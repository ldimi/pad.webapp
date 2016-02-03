/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, m: false, console: false, _G_ */

define([
    "ov/Model"
], function (Model) {
    'use strict';

    return Model.extend({
        meta: Model.buildMeta([
            {
                name: 'taak_type',
                hidden: true
            }, {
                name: 'taak_type_nr',
                hidden: true
            }, {
                name: 'taak',
                width: 160,
                slickFormatter: function (row, cell, value, columnDef, item) {
                    if (item.get('kleur')) {
                        return '<span style="color: ' + item.get('kleur') + '">' + value + '</span>';
                    }
                    return value;
                }
            }, {
                name: 'taak_key',   // een string die toestaat de taak op te lossen (facultatief)
                hidden: true
            }, {
                name: 'uitvoerder',
                hidden: true
            }, {
                name: 'dossier_nr',
                label: 'dossier',
                width: 60,
                slickFormatter: function (row, cell, value, columnDef, item) {
                    var title;
                    if (value === null) {
                        return value;
                    }
                    title = item.get("dossier_b") || value;
                    return '<span title="' + title + '">' + value + '</span>';
                }
            }, {
                name: 'omschrijving',   // er wordt een link gerenderd naar een pagina ivm de taak (eventueel plaats waar taak opgelost wordt).
                width: 180,
                slickFormatter: function (row, cell, value, columnDef, item) {
                    return '<a href="/pad/' + item.get('link') + '" target="_blank" >' + value + '</a>';
                }
            }, {
                name: 'dms_link',
                width: 20,
                label: ' ',
                slickFormatter: function (row, cell, value, columnDef, item) {
                    if (!item.get('dms_file')) {
                        return '';
                    }
                    return '<a href="' + _G_.dms_webdrive_base + item.get('dms_file') + '" target="_blank" >' +
                           '<img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>' +
                           '</a>';
                }
            }, {
                name: 'extra_info',
                label: ' ',
                width: 450
            }, {
                name: 'uiterste_eind_d',
                width: 70,
                label: 'einddatum',
                type: 'date'
            }, {
                name: 'termijn',
                width: 50,
                slickFormatter: function (row, cell, value, columnDef, item) {
                    if (value && value < 8) {
                        return '<span style="color: red;">' + value + '</span>';
                    }
                    return value;
                }
            }, {
                name: 'afwerk_jn',
                width: 20,
                label: ' ',
                slickFormatter: function (row, cell, value, columnDef, item) {
                    if (item.get('afwerk_jn') !== 'J') {
                        return '';
                    }
                    return '<a href="javascript:void(0)" onclick="taakAfwerken(\'' + item.cid + '\')" >' +
                           '<img src="resources/images/document-edit-offline-16.png" width="16" height="16" border="0" alt="Taakafwerken" title="Taakafwerken"/>' +
                           '</a>';
                }
            }
        ])
    });

});
