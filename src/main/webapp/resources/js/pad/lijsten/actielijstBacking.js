/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, alert: false */

define(["ov/select"], function () {
    'use strict';
    var $paramForm, $dossier_type, $actie_type_id, $actie_sub_type_id = null,
        _getFilterForPrefix, _filterActies, _filterSubActies;

    _getFilterForPrefix = function (prefix) {
        return function (option) {
            var lbl_type;
            if (prefix === undefined || prefix === null || prefix === "") {
                return true;
            }
            if (option.value === "") {
                return true;
            }
            lbl_type = option.label.substr(0, prefix.length);
            return (lbl_type === prefix || lbl_type === 'K') ? true : false;
        };
    };

    _filterActies = function () {
        var type = $dossier_type.val();
        $actie_type_id.select('filter', _getFilterForPrefix(type));
    };

    _filterSubActies = function () {
        var selectedAction = $actie_type_id.find('option:selected'),
            prefix;
        if (selectedAction.length === 1) {
            prefix = selectedAction.text();
            if (prefix === "Alle acties") {
                prefix = $dossier_type.val();
            }
        }
        $actie_sub_type_id.select('filter', _getFilterForPrefix(prefix));
    };

    function onReady() {
        $paramForm = $('#paramForm');

        $dossier_type = $paramForm.find('[name=dossier_type]');
        $dossier_type.change(function () {
            _filterActies();
            $actie_type_id.val("");
            _filterSubActies();
            $actie_sub_type_id.val("");
        });

        $actie_type_id = $paramForm.find('[name=actie_type_id]');
        $actie_type_id.select();
        $actie_type_id.change(function () {
            _filterSubActies();
            $actie_sub_type_id.val("");
        });

        $actie_sub_type_id = $paramForm.find('[name=actie_sub_type_id]');
        $actie_sub_type_id.select();

        _filterActies();
        _filterSubActies();
    }

    return {
        onReady: onReady
    };
});