/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, m: false, alert: false, _: false, console: false, _G_ */

define([
    "ov/mithril/ajax",
    "ov/Model",
    "ov/mithril/formhelperFactory"
], function (ajax, Model, fhf) {
    'use strict';
    var $detailDialog, _Model = null,
        postData,
        comp, _controller, programmaOptions;

    _Model = Model.extend({
        meta: Model.buildMeta([
            {
                name: "jaar",
                label: "Jaar",
                required: true
            }, {
                name: "programma_code",
                label: "Programma code",
                required: true
            }, {
                name: "budget",
                label: "Budget",
                type: "int",
                required: true
            }, {
                name: "status_crud"
            }
        ])
    });

    programmaOptions = _.map(_G_.programmaList, function (programma) {
        return { value: programma.code, label: programma.programma_type_b };
    });
    programmaOptions.unshift({value: "", label: "" });

    postData = function (url, item, afterSaveCallback) {
        ajax.postJson({
            url: url,
            content: item
        }).then(function (response) {
            if (response && response.success) {
                afterSaveCallback();
                $detailDialog.dialog("close");
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
        bewaar: function () {
            this.showErrors(true);
            if (!this.itemModel.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }
            if (this._validate(this.itemModel)) {
                this._save(this.itemModel);
            }
        },
        open: function (itemData, dataArr, afterSaveCallback) {
            this.dataArr = dataArr;
            this.itemModel = new _Model(itemData);
            this.afterSaveCallback = afterSaveCallback;
            this.showErrors(false);
            m.redraw();
            $detailDialog = $("#budgetPerProgrammaDetailDialog").dialog({
                autoOpen: false,
                modal: true,
                width: 450
            });
            $detailDialog.dialog('open');
        },
        verwijder: function (itemData, afterSaveCallback) {
            postData('/pad/s/programma/jaarbudget/delete', itemData, afterSaveCallback);
        },
        _save: function (item) {
            var action;
            if (item.get("status_crud") === 'R') {
                action = "update";
            } else if (item.get("status_crud") === 'C') {
                action = "insert";
            } else {
                alert("item heeft een ongeldige status : " + item.get("status_crud"));
                return;
            }
            postData('/pad/s/programma/jaarbudget/' + action, item, this.afterSaveCallback);
        },
        _validate: function (item) {
            var found;
            if (item.get("status_crud") === 'C') {
                //programma_code moet uniek zijn.
                found = _.find(this.dataArr, function (lijn) {
                    return (lijn.budget && item.get("programma_code") === lijn.programma_code);
                });
                if (found) {
                    alert("programma_code is niet uniek.");
                    return false;
                }
            }
            return true;
        }
    });



    comp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.itemModel).setShowErrors(ctrl.showErrors());

        return [
            m("#budgetPerProgrammaDetailDialog.hidden",
                ctrl.itemModel ?
                    [
                        m("form[autocomplete='off'][novalidate='']", [
                            m("table", [
                                m("tbody", [
                                    m("tr", [
                                        m("td[width='100px']", "Jaar:"),
                                        m("td[width='100px']", [
                                            ff.input("jaar", {
                                                type: 'text',
                                                disabled: true
                                            })
                                        ])
                                    ]),
                                    m("tr", [
                                        m("td", "Programma code:"),
                                        m("td", [
                                            ff.select("programma_code", {
                                                disabled: ctrl.itemModel.get("status_crud") !== 'C',
                                                class: "input"
                                            }, programmaOptions)
                                        ])
                                    ]),
                                    m("tr", [
                                        m("td", "Budget:"),
                                        m("td", [
                                            ff.input("budget", {
                                                type: 'text'
                                            })
                                        ])
                                    ])
                                ])
                            ])
                        ]),
                        m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar")
                    ] :
                    ""
        )];
    };


    // initialisatie van controller, en render view
    m.mount($("#budgetPerProgrammaDetailDiv").get(0), comp);

    return {
        open: _.bind(_controller.open, _controller),
        verwijder: _.bind(_controller.verwijder, _controller)
    };
});