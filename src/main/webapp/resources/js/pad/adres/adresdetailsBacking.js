/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/mithril/ajax"
], function (ajax) {
    'use strict';

    $(function() {
        var $adresform, $adres_id, $land, $gemeente, $postcode;

        $adresform = $("form[name=adresform]");

        $adres_id = $adresform.find("[name=adres_id]");
        $land = $adresform.find("[name=land]");
        $gemeente = $adresform.find("[name=gemeente]");
        $postcode = $adresform.find("[name=postcode]");

        // voor een nieuw adres landcode default op 'BE' zetten.
        if ($adres_id.val() === '') {
            $land.val('BE');
        }


        // autocomplete lijst voor fusiegemeenten
        ajax.getJSON({
            url: '/pad/s//adres/gemeentenLijst'
        }).then(function (gemeentenLijst) {

            _.each(gemeentenLijst, function(item) {
                item.label = item.postcode + ' ' + item.fusiegemeente + ' ' + item.deelgemeente;
            });

            $postcode.autocomplete({
                source: gemeentenLijst,
                minLength: 2,
                focus: function(event, ui) {
                    // prevent autocomplete from updating the textbox
                    event.preventDefault();
                    // manually update the textbox
                    $(this).val(ui.item.postcode);
                },
                select: function(event, ui) {
                    // prevent autocomplete from updating the textbox
                    event.preventDefault();
                    // manually update the textbox and hidden field
                    $(this).val(ui.item.postcode);
                    $gemeente.val(ui.item.fusiegemeente);
                }
            });

            $gemeente.autocomplete({
                source: gemeentenLijst,
                minLength: 2,
                focus: function(event, ui) {
                    // prevent autocomplete from updating the textbox
                    event.preventDefault();
                    // manually update the textbox
                    $(this).val(ui.item.fusiegemeente);
                },
                select: function(event, ui) {
                    // prevent autocomplete from updating the textbox
                    event.preventDefault();
                    // manually update the textbox and hidden field
                    $(this).val(ui.item.fusiegemeente);
                    $postcode.val(ui.item.postcode);
                }
            });

        });
    });
});