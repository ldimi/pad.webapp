/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ , console */

define([
    "ov/Model2",
    "ov/mithril/formhelperFactory",
    "ov/mithril/ajax"
], function (Model, fhf,ajax) {
    'use strict';

    var comp, OpmerkingModel;

    OpmerkingModel = Model.extend({
        meta: Model.buildMeta([{
            name: 'opmerking'
        }])
    });

    comp = {};

    comp.controller = function (options) {

        var taak, opmerkingModel, open, close, taakAfsluiten,
            $dialog, configDialog;

        taak = m.prop(null);
        opmerkingModel = m.prop(null);

        open = function (item) {
            taak(item);
            opmerkingModel(new OpmerkingModel());
            m.redraw();
            $dialog.dialog('open');
        };

        close = function () {
            $dialog.dialog('close');
        };

        taakAfsluiten = function () {
            ajax.postJson({
                url: "/pad/s/brief/afdHfd/checked",
                content: {
                    brief_id: taak().get("taak_key"),
                    opmerking_afd_hfd: opmerkingModel().get("opmerking")
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
                    //height: 300
                });
            }
        };

        return {
            open: open,
            close: close,
            taakAfsluiten: taakAfsluiten,
            taak: taak,
            opmerkingModel: opmerkingModel,
            configDialog: configDialog
        };
    };

    comp.view = function(ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.opmerkingModel());

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
                                    m("td", "Opmerking:"),
                                    m("td", [
                                        ff.textarea("opmerking", {
                                            rows: 3,
                                            maxlength: 500,
                                            style: {width: "250px"},
                                            readOnly: false
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
