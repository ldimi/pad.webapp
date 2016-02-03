/*jslint debug: true, browser: true */
/*global define: false, $: false, alert: false, console:false, Slick */

define(["ov/Meta", "ov/ajax", "ov/select", "ov/form", "underscore"], function (Meta, ajax) {
    'use strict';

    var _data, $dialog, _initialised = false,
        _formMeta, _fm;

    _formMeta = new Meta([
        {
            name: 'vordering_id',
            type: 'int'
        }, {
            name: 'deelopdracht_id',
            type: 'int'
        }, {
            name: 'deelopdracht'
        }, {
            name: 'brief_nr'
        }, {
            name: 'bestek_nr'
        }, {
            name: 'goedkeuring_bedrag',
            type: 'double'
        }, {
            name: 'vastlegging_bedrag',
            required: true,
            type: 'double'
        }, {
            name: 'vastlegging_bedrag_2',
            required: true,
            type: 'double'
        }, {
            name: 'project_id',
            required: true
        }, {
            name: 'project_id_2',
            required: true
        }
    ]);

    function _enforceInvariants() {
        var vastlegging, vastlegging_bedrag, vastlegging_bedrag_2;

        $("._tr_paar_minuten").addClass("invisible");
        if (_data.schuldvordering.dossier_type === 'X') {
            _fm.$form.find("._deelopdracht").removeClass("invisible");
        } else {
            _fm.$form.find("._deelopdracht").addClass("invisible");
        }


        if (_fm.$project_id.ov_value() === null) {
            // eerste vastlegging nog niet geselecteerd.
            _fm.$project_id_2.ov_value(null);
            _fm.$vastlegging_bedrag.ov_value(null);
            _fm.$vastlegging_bedrag_2.ov_value(null);
            _fm.$form.find("._vastlegging_bedrag").addClass("hidden");
            _fm.$form.find("._vastlegging_2").addClass("hidden");
        } else {
            // eerste vastlegging is gekozen.
            _fm.$form.find("._vastlegging_bedrag").removeClass("hidden");

            vastlegging = _fm.$project_id.select('getSelectedOption');
            vastlegging_bedrag = _fm.$vastlegging_bedrag.ov_value() || 0;
            vastlegging_bedrag_2 = _fm.$vastlegging_bedrag_2.ov_value() || 0;

            if (vastlegging_bedrag_2 === 0) {
                _fm.$project_id_2.ov_value(null);
                _fm.$vastlegging_bedrag_2.ov_value(null);
                _fm.$form.find("._vastlegging_2").addClass("hidden");

                if (vastlegging.OPEN_BEDRAG + 0.005 > vastlegging_bedrag) {
                    // standaard geval : alles lukt binnen eerste vastlegging
                    _fm.$vastlegging_bedrag.attr("readonly", true);
                } else {
                    _fm.$vastlegging_bedrag.attr("readonly", false);
                }
            } else {
                _fm.$vastlegging_bedrag.attr("readonly", false);
                _fm.$form.find("._vastlegging_2").removeClass("hidden");
            }
        }
    }

    function _onChange_project_id() {
        var data, vastlegging, resterend_bedrag, minimaal_resterend_bedrag,
            vastleggingen, vastleggingen_2, i;
        console.log("_onChange_project_id");

        // reset alle waarden
        _fm.$vastlegging_bedrag.val(null);
        _fm.$vastlegging_bedrag_2.val(null);
        _fm.$project_id_2.val(null);
        _fm.$project_id_2.select("setOptionList", []);

        data = _fm.values();
        vastlegging = _fm.$project_id.select('getSelectedOption');
        if (vastlegging.OPEN_BEDRAG + 0.005 <= data.goedkeuring_bedrag) {
            _fm.$vastlegging_bedrag.ov_value(vastlegging.OPEN_BEDRAG);

            // resterend bedrag invullen.
            resterend_bedrag = (data.goedkeuring_bedrag - vastlegging.OPEN_BEDRAG).toFixed(2);
            minimaal_resterend_bedrag = (data.goedkeuring_bedrag - vastlegging.MOGELIJK_BEDRAG).toFixed(2);
            _fm.$vastlegging_bedrag_2.ov_value(resterend_bedrag);

            // tweede vastlegging dropdown vullen:
            vastleggingen = _fm.$project_id.select("getOptionList");
            vastleggingen_2 = [];
            for (i = 0; i < vastleggingen.length; i = i + 1) {
                if (vastleggingen[i].value !== vastlegging.value && vastleggingen[i].MOGELIJK_BEDRAG >= minimaal_resterend_bedrag) {
                    vastleggingen_2.push(vastleggingen[i]);
                }
            }
            vastleggingen_2.unshift({
                label: "&nbsp;",
                value: ""
            }); //lege optie
            _fm.$project_id_2.select("setOptionList", vastleggingen_2);

        } else {
            _fm.$vastlegging_bedrag.ov_value(data.goedkeuring_bedrag);
        }
        _enforceInvariants();
    }

    function _onChange_vastlegging_bedrag() {
        var bedrag = _fm.$vastlegging_bedrag.ov_value();

        console.log("verandering vastlegging_bedrag : " + _fm.$vastlegging_bedrag.val());
        _fm.$vastlegging_bedrag.ov_value(bedrag);
        _fm.$vastlegging_bedrag_2.ov_value(_fm.$goedkeuring_bedrag.ov_value() - _fm.$vastlegging_bedrag.ov_value());
        _enforceInvariants();
    }

    function _onChange_vastlegging_bedrag_2() {
        var bedrag_2 = _fm.$vastlegging_bedrag_2.ov_value();

        console.log("verandering vastlegging_bedrag_2 : " + _fm.$vastlegging_bedrag_2.val());
        _fm.$vastlegging_bedrag_2.ov_value(bedrag_2);
        _fm.$vastlegging_bedrag.ov_value(_fm.$goedkeuring_bedrag.ov_value() - _fm.$vastlegging_bedrag_2.ov_value());
        _enforceInvariants();
    }

    function _doSave() {
        var content;

        content = {
            vorderingId : _data.vordering_id,
            twaalfNr : _data.project_id,
            bedrag : _data.vastlegging_bedrag,
            twaalfNr_2: _data.project_id_2,
            bedrag_2 : _data.vastlegging_bedrag_2
        };

        $("._tr_paar_minuten").removeClass("invisible");
        ajax.postJson({
            url: "/pad/s/sap/createSchuldVorderingen",
            content: content
        }).success(function (response) {
            if (response.success) {
                alert("De Schuldvordering werd aangemaakt. Wbs nummer is: " + response.result);

                // alles sluiten en pagina refreshen.
                window.location = window.location; //pagina herladen.
            } else {
                alert("mislukt : " + response.errorMsg);
                $("._tr_paar_minuten").addClass("invisible");
            }
        });
        console.log(content);
    }

    function _onSave() {
        var vastlegging, vastlegging_2;

        console.log("on save");
        if (_fm.validate()) {
            _fm.extractTo(_data);

            // bijkomende validatie
            if (_data.vastlegging_bedrag) {
                if (_data.vastlegging_bedrag < 0.001) {
                    alert("Het bedrag op de eerste vastlegging mag niet negatief zijn.");
                    return;
                }

                vastlegging = _fm.$project_id.select('getSelectedOption');
                if (_data.vastlegging_bedrag > vastlegging.MOGELIJK_BEDRAG + 0.001) {
                    alert("Er is niet genoeg geld vrij op de eerste vastlegging.");
                    return;
                }
            } else {
                alert("Het bedrag op de eerste vastlegging mag niet nul zijn.");
                return;
            }


            // bijkomende validatie
            if (_data.vastlegging_bedrag_2) {
                if (_data.vastlegging_bedrag_2 < 0.001) {
                    alert("Het bedrag op de tweede vastlegging mag niet negatief zijn.");
                    return;
                }
                vastlegging_2 = _fm.$project_id_2.select('getSelectedOption');
                if (_data.vastlegging_bedrag_2 > vastlegging_2.MOGELIJK_BEDRAG + 0.001) {
                    alert("Er is niet genoeg geld vrij op de tweede vastlegging.");
                    return;
                }

                if ((_data.vastlegging_bedrag + _data.vastlegging_bedrag_2 > _data.goedkeuring_bedrag + 0.001) ||
                    (_data.vastlegging_bedrag + _data.vastlegging_bedrag_2 < _data.goedkeuring_bedrag - 0.001)    ) {
                    alert("De som van de bedragen op de vastleggingen is niet gelijk aan het totaal bedrag goedkeuring.");
                    return;
                }
            }
            _doSave();
        } else {
            alert('Er zijn validatie fouten.');
        }
    }

    function _init() {
        if (!_initialised) {
            console.log("maakInSapDialog.init");

            $dialog = $('#sapMaakDialog').dialog({
                autoOpen: false,
                modal: true,
                width: 580,
                open: function(event) {
                    $(event.target).parent().css('position', 'fixed');
                    $(event.target).parent().css('top', '75px');
                    $(event.target).parent().css('left', '200px');
                }
            });
            _fm = $('#sapMaakForm').ov_formManager({
                meta: _formMeta
            });

            _fm.$project_id.change(_onChange_project_id);
            _fm.$vastlegging_bedrag.change(_onChange_vastlegging_bedrag);
            _fm.$vastlegging_bedrag_2.change(_onChange_vastlegging_bedrag_2);

            $('#sapBewaarBtn').click(_onSave);
            _initialised = true;
        }
    }

    function round(value, decimals) {
        var power;
        decimals = decimals || 2;
        power = Math.pow(10, 2);
        return Math.round(value * power) / power;
    }

    function _fillVastleggingenSelect(vastleggingen) {
        var i, option;

        if (vastleggingen && vastleggingen[0] && vastleggingen[0].value !== "") {
            // er nog geen lege optie toegevoegd
            // initiele aanpassingen vastleggingen lijst.
            for (i = 0; i < vastleggingen.length; i = i + 1) {
                option = vastleggingen[i];
                option.INITIEEL_BEDRAG = round(option.INITIEEL_BEDRAG);
                option.OPEN_BEDRAG = round(option.OPEN_BEDRAG);
                option.MOGELIJK_BEDRAG = round(option.MOGELIJK_BEDRAG);
                option.label = option.INITIEEL_ACHT_NR + "  ; initieel : " + option.INITIEEL_BEDRAG + "  ; saldo : " + option.OPEN_BEDRAG + "  ; max : " + option.MOGELIJK_BEDRAG;
                option.selected = false;
            }
            vastleggingen.unshift({
                label: "",
                value: ""
            }); //lege optie
        }

        _fm.$project_id.select({
            optionList: vastleggingen
        });
    }

    function _fillView() {


        _fm.populate(_data.schuldvordering);

        _fillVastleggingenSelect(_data.vastleggingen);
    }

    return {
        show: function (data) {
            console.log("maakInSapDialog.show");
            _data = data;

            if (_data.projecten && _data.projecten.length > 0) {
                alert("Deze schuldvordering is al gekoppeld aan een wbs : " + _data.schuldvordering.wbs_nr);
                return;
            }

            if (_data.schuldvordering.dossier_type === 'X') {
                if (_data.schuldvordering.deelopdracht_id === null || _data.schuldvordering.deelopdracht_id === 0) {
                    alert("Deze schuldvordering is nog niet gekoppeld aan een deelopdracht.");
                    return;
                }
            }

            _init();
            _fillView();
            _enforceInvariants();
            $dialog.dialog("open");
        }
    };
});