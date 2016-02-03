/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ , console */

define([
    "ov/mithril/formhelperFactory",
    "ov/mithril/ajax"
], function (fhf, ajax) {
    'use strict';

    var comp;

    comp = {};

    comp.controller = function (options) {

        var taak, open, close, taakAfsluiten,
            $dialog, configDialog;

        taak = m.prop(null);

        open = function (item) {
            taak(item);
            m.redraw();
            $dialog.dialog('open');
        };

        close = function () {
            $dialog.dialog('close');
        };

        taakAfsluiten = function () {
            ajax.postJson({
                url: "/pad/s/brief/auteur/checked",
                content: {
                    brief_id: taak().get("taak_key")
                }
            }).then(function (response) {
                if (response && response.success) {
                    options.taakAfgeslotenCb(taak());
                    close();
                } else {
                    alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                }
            });
        };

        configDialog = function(element, isInitialized) {
            if (!isInitialized) {
                $dialog = $(element).dialog({
                    autoOpen: false,
                    modal: true,
                    width: 380
                });
            }
        };

        return {
            open: open,
            close: close,
            taakAfsluiten: taakAfsluiten,
            taak: taak,
            configDialog: configDialog
        };
    };

    comp.view = function(ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.taak());

        return m(".hidden",
            {
                title: "Taak : check inkomende brief",
                config: ctrl.configDialog
            },
            ctrl.taak() ?
                [
                    m("form[autocomplete='off'][id='detailForm'][novalidate='']", [
                        m("table", [
                            m("tbody", [
                                m("tr", [
                                    m("td", "Opmerking Afd.Hfd:"),
                                    m("td", [
                                        ff.textarea("extra_info_2", {
                                            rows: 3,
                                            maxlength: 500,
                                            style: {width: "250px"},
                                            readOnly: true
                                        })
                                    ])
                                ])
                            ])
                        ])
                    ]),
                    m("button.#taakAfsluitenBtn", {onclick: ctrl.taakAfsluiten}, "Taak afsluiten"),
                    m("button.#annuleerBtn", {onclick: ctrl.close}, "annuleer")
                ] :
                ""
        );
    };

    return comp;

});
