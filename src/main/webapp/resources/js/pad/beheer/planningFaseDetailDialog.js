/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true */

define(["ov/ajax", "ov/events"], function (ajax, events) {
    'use strict';

    var _faseDetail, _faseDetailArray = null,
        _fm, $dialog;

    function validateDetailFase(faseDetail) {
        var duplicate;
        if (faseDetail.get("status_crud") === 'C') {
            //code moet uniek zijn.
            duplicate = _.find(_faseDetailArray, function(item) {
                return (item.get("fase_code") === faseDetail.get("fase_code") &&
                        item.get("fase_detail_code") === faseDetail.get("fase_detail_code"));
            });
            if (duplicate) {
                alert("code is niet uniek.");
                return false;
            }
        }
        return true;
    }

    function show(faseDetail, faseDetailArray) {
        _faseDetailArray = faseDetailArray;
        _faseDetail = faseDetail;

        _fm.populate(_faseDetail);
        if (_faseDetail.get("status_crud") === 'C') {
            _fm.$fase_detail_code.removeAttr("readonly");
        } else {
            _fm.$fase_detail_code.attr("readonly", "readonly");
        }

        $dialog.dialog("open");
    }

    function init(faseDetailMeta) {
        _fm = $('#planningFaseDetailForm').ov_formManager({
            meta: faseDetailMeta
        });

        $dialog = $('#planningFaseDetailDialog').dialog({
            autoOpen: false,
            modal: true,
            width: 270
        });


        $('#annuleerFaseDetailBtn').click(function () {
            $dialog.dialog("close");
        });

        $('#bewaarFaseDetailBtn').click(function () {
            var action;
            if (_fm.validate()) {
                _fm.extractTo(_faseDetail);
                if (validateDetailFase(_faseDetail)) {
                    if (_faseDetail.get("status_crud") === 'C') {
                        action = "insert";
                    } else {
                        action = "update";
                    }

                    ajax.postJson({
                        url: '/pad/s/planningFaseDetail/' + action,
                        content: _faseDetail
                    }).success(function (response) {
                        if (response && response.success) {
                            events.trigger("faseDetailGrid:setData", response.result);
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