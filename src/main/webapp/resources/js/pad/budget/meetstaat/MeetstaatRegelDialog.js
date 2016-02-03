/*jslint nomen: false, debug: true, browser: true, nomen: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console */


define(["ov/form"], function () {
    "use strict";

    function initMeetstaatRegelDialog(meetstaatMeta, bewaarCb) {
        var open, save, dialog, changeType, bestekmeetstaatRegelFm , _bewaarCb;

        _bewaarCb = bewaarCb;

        dialog = $('#bestekmeetstaatRegelDialog').dialog({
            autoOpen : false,
            modal : true,
            width : 390,
            open: function (event) {
                $(event.target).parent().css('position', 'fixed');
                $(event.target).parent().css('top', '50px');
                $(event.target).parent().css('left', '100px');
            }
        });

        bestekmeetstaatRegelFm = $('#bestekmeetstaatRegelForm').ov_formManager({
            meta: meetstaatMeta
        });

        changeType = function(parent) {
            if (parent) {
                $("#typeRow").addClass("hidden");
                $("#eenheidRow").addClass("hidden");
                $("#aantalRow").addClass("hidden");
                $("#eenheidsprijsRow").addClass("hidden");
                $("#regelTotaalRow").addClass("hidden");
            } else {
                $("#typeRow").removeClass("hidden");
                if (bestekmeetstaatRegelFm.$type.val() === "VH") {
                    $("#eenheidRow").removeClass("hidden");
                    $("#aantalRow").removeClass("hidden");
                    $("#eenheidsprijsRow").removeClass("hidden");

                    bestekmeetstaatRegelFm.$regelTotaal.val(null);
                    $("#regelTotaalRow").addClass("hidden");
                } else {
                    bestekmeetstaatRegelFm.$eenheid.val(null);
                    $("#eenheidRow").addClass("hidden");

                    bestekmeetstaatRegelFm.$aantal.val(null);
                    $("#aantalRow").addClass("hidden");

                    bestekmeetstaatRegelFm.$eenheidsprijs.val(null);
                    $("#eenheidsprijsRow").addClass("hidden");

                    if (bestekmeetstaatRegelFm.$type.val() === "") {
                        bestekmeetstaatRegelFm.$regelTotaal.val(null);
                        $("#regelTotaalRow").addClass("hidden");
                    } else {
                        $("#regelTotaalRow").removeClass("hidden");
                    }
                }
            }
        };

        open = function (item) {
            bestekmeetstaatRegelFm.reset();
            bestekmeetstaatRegelFm.populate(item);
            changeType(item.childs > 0);
            if (item.crudStatus === 'C') {
                $('#bewaarVolgendeBtn').hide();
                $("#bewaarBtn").val("Toevoegen");
            } else {
                $('#bewaarVolgendeBtn').show();
                $("#bewaarBtn").val("Aanpassen");
            }
            dialog.dialog("open");
            if (item.crudStatus !== 'C') {
                if (bestekmeetstaatRegelFm.$aantal.is(":hidden")) {
                    bestekmeetstaatRegelFm.$taak.focus();
                } else {
                    bestekmeetstaatRegelFm.$aantal.focus();
                }
            }
         };

        bestekmeetstaatRegelFm.$type.change(function () {
            changeType();
        });

        save = function(naarVolgende) {
            var item, nummer;

            if (bestekmeetstaatRegelFm.validate()) {
                item = bestekmeetstaatRegelFm.values();
                nummer = item.postnr;
                nummer = nummer.replace(/\./g, '');
                nummer = nummer.replace(/\,/g, '');
                if ($.isNumeric(nummer)) {
                    _bewaarCb(item, naarVolgende);
                } else {
                    alert("Geen geldig PostNr");
                }
            }
        };

        $('#bewaarBtn').click(function () {
            save(false);
        });

        $('#bewaarVolgendeBtn').click(function () {
            save(true);
        });

        $('#annuleerBtn').click(function () {
            dialog.dialog("close");
        });

        return {
            open: open
        };
    }

    return {
        init: initMeetstaatRegelDialog
    };
});

