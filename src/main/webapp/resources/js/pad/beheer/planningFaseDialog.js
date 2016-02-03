/*jslint debug: true, browser: true, nomen: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true */

define([
    "ov/ajax",
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "beheer/planningFaseDetailDialog"
], function (ajax, Model, GridComp, events, planningFaseDetailDialog) {
    'use strict';

    var _fm, _fase, _faseArray, _grid,
        _initGrid, _validateFase, _handleDetailResponse, $dialog, FaseDetailModel;

    FaseDetailModel = Model.extend({
        meta: Model.buildMeta([
            {
                name: "fase_code",
                type: "string",
                hidden: true,
                required: true
            }, {
                name: "fase_detail_code",
                type: "string",
                label: "Fasedetail code",
                required: true
            }, {
                name: "fase_detail_code_b",
                label: "Omschrijving",
                type: "string"
            }, {
                name: "status_crud",
                type: "string",
                hidden: true
            }
        ])
    });

    _validateFase = function (fase) {
        var duplicate;
        if (fase.get("status_crud") === 'C') {
            //code moet uniek zijn.
            duplicate = _.find(_faseArray, function (item) {
                return (item.get("dossier_type") === fase.get("dossier_type") &&
                        item.get("fase_code") === fase.get("fase_code"));
            });
            if (duplicate) {
                alert("code is niet uniek.");
                return false;
            }
        }
        return true;
    };

    _handleDetailResponse = function (response) {
        if (response && response.success) {
            events.trigger("faseDetailGrid:setData",response.result);
        } else {
            alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
        }
    };

    _initGrid = function () {
        if (!_grid) {
            _grid = new GridComp({
                el: "#faseDetailDiv",
                model: FaseDetailModel,
                newBtn: window._G_isAdminArt46,
                editBtn: window._G_isAdminArt46,
                deleteBtn: window._G_isAdminArt46,
                onNewClicked: function (item, faseDetailArray) {
                    var newItem;
                    newItem = new FaseDetailModel({
                        fase_code: _fase.get("fase_code"),
                        status_crud: 'C'
                    });

                    planningFaseDetailDialog.show(newItem, faseDetailArray);
                },
                onEditClicked: function (item, faseDetailArray) {
                    if (window._G_isAdminArt46) {
                        item.set("status_crud", "R");
                        planningFaseDetailDialog.show(item, faseDetailArray);
                    }
                },
                onDeleteClicked: function (item) {
                    if (item) {
                        ajax.postJson({
                            url: '/pad/s/planningFaseDetail/delete',
                            content: item
                        }).success(_handleDetailResponse);
                    }
                }
            });
        }
        events.on("faseDetailGrid:setData", function (data) {
            _grid.setData(data);
        });
    };

    function show(fase, faseArray) {
        _faseArray = faseArray;
        _fase = fase;

        _fm.populate(_fase);

        if (_fase.get("status_crud") === 'C') {
            _fm.$dossier_type.removeAttr("disabled");
            _fm.$fase_code.removeAttr("readonly");
            $("#faseDetailDiv").hide();
            $dialog.dialog("open");
        } else {
            _fm.$dossier_type.attr("disabled", "disabled");
            _fm.$fase_code.attr("readonly", "readonly");
            $("#faseDetailDiv").show();
            // grid moet zichtbaar zijn om data te renderen,
            // daarom eerst dialog openen.
            $dialog.dialog("open");
            _initGrid();
        }



        ajax.postJson({
            url: '/pad/s/planningFaseDetails',
            content: _fase
        }).success(_handleDetailResponse);

    }

    function init(faseMeta) {

        _fm = $('#planningFaseForm').ov_formManager({
            meta: faseMeta
        });

        $dialog = $('#planningFaseDialog').dialog({
            autoOpen: false,
            modal: true,
            width: 320
        });

        planningFaseDetailDialog.init(FaseDetailModel.prototype.meta);

        $('#annuleerFaseBtn').click(function () {
            $dialog.dialog("close");
        });

        $('#bewaarFaseBtn').click(function () {
            var action;
            if (_fm.validate()) {
                _fm.extractTo(_fase);
                if (_validateFase(_fase)) {
                    if (_fase.get("status_crud") === 'C') {
                        action = "insert";
                    } else {
                        action = "update";
                    }

                    ajax.postJson({
                        url: '/pad/s/planningFase/' + action,
                        content: _fase
                    }).success(function (response) {
                        if (response && response.success) {
                            events.trigger("fasenGrid:setData", response.result);
                            $dialog.dialog("close");
                        } else {
                            alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
                        }
                    });
                }
            }
        });
    }

    return {
        init: init,
        show: show
    };
});