/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, m: false, _G_programmaList */

define([
    "ov/mithril/ajax",
    "ov/Model",
    "ov/mithril/formhelperFactory"
], function (ajax, Model, fhf) {
    'use strict';
    var $dialog, OmschrijvingModel = null,
        postData,
        comp, _controller;

    OmschrijvingModel = Model.extend({
        meta: Model.buildMeta([{
            name: "omschrijving",
            required: true
        }])
    });

    postData = function (url, item) {
        ajax.postJson({
            url: url,
            content: item
        }).then(function (response) {
            if (response && response.success) {
                $dialog.dialog("close");
                window.location = window.location;
            } else {
                alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
            }
        });
    };

    comp = {};
    comp.controller = function () {
        _controller = this;
        this.showErrors = m.prop(false);
    };
    _.extend(comp.controller.prototype, {
        open: function () {
            this.itemModel = new OmschrijvingModel({
                omschrijving: null
            });
            this.showErrors(false);
            m.redraw();
            $dialog = $("#markeerPlanningDialog").dialog({
                modal: true,
                width: 550
            });
        },
        bewaar: function () {
            this.showErrors(true);
            if (!this.itemModel.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }
            postData('/pad/s/planning/jaar/markeer', this.itemModel);
        }
    });


    comp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.itemModel).setShowErrors(ctrl.showErrors());

        return [m("#markeerPlanningDialog.hidden[title='Markeer planning']",
            ctrl.itemModel ?
                [
                    m("form[autocomplete='off'][novalidate='']", [
                        m("table", [
                            m("tbody", [
                                m("tr", [
                                    m("td[width='100px']", "Omschrijving:"),
                                    m("td[width='400px']", [
                                        ff.input("omschrijving", {
                                            type: 'text',
                                            maxlength: '60'
                                        })
                                    ])
                                ])
                            ])
                        ])
                    ]),
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Markeer")
                ] :
                ""
        )];
    };

    // initialisatie van controller, en render view
    m.mount($("#markeerPlanningDiv").get(0), comp);

    return {
        open: _.bind(_controller.open, _controller)
    };
});