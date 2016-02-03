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

        var currentItem, open, close, taakAfsluiten,
            $dialog, configDialog;

        currentItem = m.prop(null);

        open = function (item) {
            currentItem(item);
            m.redraw();
            $dialog.dialog('open');
        };

        close = function () {
            $dialog.dialog('close');
        };

        taakAfsluiten = function () {
            ajax.postJson({
                url: "/pad/s/brief/opmerking/" + currentItem().get("taak_key") + "/behandeld",
                content: null
            }).then(function (response) {
                if (response && response.success) {
                    options.taakAfgeslotenCb(currentItem());
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
                    //height: 300
                });
            }
        };

        return {
            open: open,
            close: close,
            taakAfsluiten: taakAfsluiten,
            currentItem: currentItem,
            configDialog: configDialog
        };
    };

    comp.view = function(ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.currentItem());

        return m(".hidden",
            {
                title: "Taak : opmerking brief",
                config: ctrl.configDialog
            },
            ctrl.currentItem() ?
                [
                    m("form[autocomplete='off'][id='detailForm'][novalidate='']", [
                        m("table", [
                            m("tbody", [
                                m("tr", [
                                    m("td", "Brief nr:"),
                                    m("td", [
                                        ff.input("omschrijving", {
                                            class: 'input',
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
