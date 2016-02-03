/*jslint debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ , console */

define([
    "ov/parsers",
    'brief/briefDocumentenComp',
    'schuldvordering/editSchuldVorderingDialog'
], function (parsers, briefDocumentenComp, editSchuldVorderingDialog) {
    'use strict';


    $(function () {

        var $briefform = $("[name=briefform]"),
            brief_id = $("[name=brief_id]", $briefform).val(),
            parent_brief_id = $("[name=parent_brief_id]", $briefform).val(),
            brief_id_int;

        if (brief_id && !parent_brief_id) {
            brief_id_int = parsers("int")(brief_id);

            briefDocumentenComp.brief_id(brief_id_int);
            m.mount($("#documenten_div").get(0), briefDocumentenComp);

        }

        $('#schuldvorderingBtn').click(function() {
            var bestek_id = $("[name=bestek_id]", $briefform).val(),
                bestek_id_org = $("[name=bestek_id_org]", $briefform).val(),
                vordering_d = $("[name=in_d]", $briefform).val(),
                vordering_id = $("[name=vordering_id]", $briefform).val();

            if (vordering_id === "") {

                // check bestek_id, en vordering_d moeten ingevuld zijn.
                if (bestek_id_org !== bestek_id) {
                    alert("Bestek is gewijzigd, Deze wijziging moet eerst bewaard worden.");
                } else if (bestek_id_org === "") {
                    alert("Er moet een bestek ingevuld zijn om een schulvordering aan te maken.");
                } else if (vordering_d === "") {
                    alert("Datum OVAM (binnenkomend) moet ingevuld zijn om een schuldvordering aan te maken.");
                } else {
                    editSchuldVorderingDialog.showNieuw(brief_id, vordering_d);
                }
            } else {
                editSchuldVorderingDialog.show(vordering_id);
            }

        });

        $('#verwijderBtn').click(function() {
            var form, ok;

            ok =  window.confirm("Wilt u deze brief verwijderen?");
            if (ok) {
                form = $("[name=briefform]");
                window.setAction(form.get(0),'briefdetailsdelete.do');
                form.submit();
            }
        });



    });

});
