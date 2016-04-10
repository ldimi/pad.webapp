/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console: false */

define([
    "dropdown/jaren",
    "ov/Model",
    "ov/GridComp",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "mithril",
    "underscore"
], function (jaren_dd, Model, GridComp, ajax, fhf, m, _) {
    'use strict';
    var ParamsModel, JaarbudgetModel, paramsComp,
        _jaar, postData, _jaarbudgetGrid, _jaarbudgetDetailFm, $jaarbudgetDetailDialog;

    ParamsModel = Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", type: "int", required: true, default: new Date().getFullYear() }
        ])
    });
    
    JaarbudgetModel = Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", hidden: true },
            { name: "budget_code", label: "Budget code", required: true },
            { name: "budget", label: "Budget bedrag", type: "int", required: true },
            { name: "effectief_budget", label: "Effectief budget", type: "int", required: false },
            { name: "artikel_b", label: "Artikelcode", type: "String", required: false },
            { name: "status_crud", hidden: true }
        ])
    });

    paramsComp = {
        controller: function () {
            this.params = new ParamsModel();
            this.showErrors = m.prop(false);

            this.ophalen = function () {
                var jaar = this.params.get("jaar");
                if (jaar) {
                    ajax.getJSON({
                        url: '/pad/s/beheer/getjaarbudgetten',
                        content: {
                            "jaar": jaar
                        }
                    }).then(function (resp) {
                        _jaarbudgetGrid.setData(resp);
                        $('#jaarBudgetGridDiv').removeClass('invisible');
                    });
                }
            };
        },
        view: function (ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());
            return [
                m("table", [
                    m("tr", [
                        m("td", "Jaar:"),
                        m("td", ff.select("jaar", jaren_dd)),
                        m("td", m("button", {onclick: _.bind(ctrl.ophalen, ctrl)}, "Ophalen"))
                    ])
                ])
            ];
        }
    };


    
    function openJaarbudgetDialog(item) {
        _jaarbudgetDetailFm.populate(item);
        if (item.status_crud === 'C') {
            _jaarbudgetDetailFm.$budget_code.removeAttr("readonly");
            _jaarbudgetDetailFm.$budget_code.removeAttr("disabled");
        } else {
            _jaarbudgetDetailFm.$budget_code.attr("readonly", "readonly");
            _jaarbudgetDetailFm.$budget_code.attr("disabled", "disabled");
        }
        $jaarbudgetDetailDialog.dialog("open");
    }

    function initJaarBudgetGrid() {
        _jaarbudgetGrid = new GridComp({
            el: "#jaarBudgetGridDiv",
            meta: _jaarbudgetMeta,
            newBtn: window._G_isAdminArt46,
            editBtn: window._G_isAdminArt46,
            deleteBtn: window._G_isAdminArt46,
            onEditClicked: function (item) {
                openJaarbudgetDialog(item);
            },
            onNewClicked: function () {
                openJaarbudgetDialog({
                    status_crud: 'C',
                    jaar: _jaar
                });
            },
            onDeleteClicked: function (item) {
                postData('/pad/s/beheer/jaarbudget/delete', item);
            }
        });
    }


    function onReady() {
        $('#paramForm [name=jaar]').val(new Date().getFullYear());

        $('#ophalenBtn').click(function () {

            $('#jaarBudgetGridDiv').addClass('invisible');
            _jaar = $('#paramForm [name=jaar]').val();


        }).removeClass("invisible");


        _jaarbudgetDetailFm = $('#detailForm').ov_formManager({
            meta: _jaarbudgetMeta
        });

        $jaarbudgetDetailDialog = $('#detailDialog').dialog({
            autoOpen: false,
            modal: true
        });

        initJaarBudgetGrid();

        $('#bewaarBtn').click(function () {
            var item, action;

            if (_jaarbudgetDetailFm.validate()) {
                item = _jaarbudgetDetailFm.values();
                if (item.status_crud === 'C') {
                    action = "insert";
                } else {
                    action = "update";
                }
                postData('/pad/s/beheer/jaarbudget/' + action, item);
            }
        });

        $('#annuleerBtn').click(function () {
            $jaarbudgetDetailDialog.dialog("close");
        });

        postData = function (url, data) {
            ajax.postJson({
                url: url,
                content: data
            }).success(function (response) {
                if (response && response.success) {
                    _jaarbudgetGrid.setData(response.result);
                    $jaarbudgetDetailDialog.dialog("close");
                } else {
                    alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                }
            });
        };

    }

    return {
        onReady: onReady
    };
});