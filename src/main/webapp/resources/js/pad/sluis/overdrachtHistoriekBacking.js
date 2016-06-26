/*jslint nomen: true, debug: true, browser: true */
/*global define: false, console: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/Model",
    "ov/mithril/formhelperFactory",
    "ov/GridComp",
    "ov/mithril/ajax",
    "ov/events"
], function (Model, fhf, GridComp, ajax, event) {
    'use strict';
    var ParamsModel, _paramsComp, _comp, _initGrid, _processReceivedData, OverdrachtHistoriekModel;


    OverdrachtHistoriekModel = Model.extend({
        meta: Model.buildMeta([
            { name: "overdracht_id", label: "Idd", type: "int", width: 20 },
            { name: "versie_nr", type: "int", required: true, default: 0, hidden: true },
            { name: "dossier_id", type: "int", hidden: true },
            { name: "dossier_type", label: " ", width: 20 },
            { name: "gemeente_b", label: "Gemeente", width: 80 },
            { name: "dossier_omschrijving", label: "Titel", width: 150},
            { name: "dossier_id_boa", label: "Bodem Nr", type: "int", width: 60 },
            { name: "dossier_nr", label: "IVS Nr", width: 60,
                gridFormatter: function (value) {
                    return (value && !value.startsWith("_") ) ? value : null;
                }
            },
            { name: "dossier_fase_id", type: "int", required: true, hidden: true },
            { name: "fase_b", label: "Fase", width: 150 },
            { name: "rechtsgrond_b", label: "Rechtsgrond" },
            { name: "doelgroep_b", label: "Doelgroep", width: 150 },
            { name: "timing_jaar", type: "int", hidden: true },
            { name: "timing_maand", type: "int", hidden: true },
            { name: "timing_b", label: "Timing integratie", width: 100,
                default: function () {
                    return this.str("timing_jaar") +
                           (this.get("timing_maand") ? " / " + this.str("timing_maand") : "");
                }
            },

            { name: "prioriteits_index", type: "double", label: "Prioriteit" },

            { name: "commentaar", label: "Commentaar",width: 200 },
            { name: "screen_bestek_omschrijving", label: "Screen bestek", width: 250 },

            { name: "status_start_d", label: "Status startdatum", type: "date" },
            { name: "status_b", label: "Status" },
            { name: "deleted_jn", label: "Verwijderd" },
            { name: "doss_hdr_id",label: "Dossierhouder"}
        ])
    });



    _processReceivedData = function (data) {
        var overdrachtenArray;

        overdrachtenArray = _.map(data, function (overdrachtAttributes) {
            var overdracht;
            overdracht = new OverdrachtHistoriekModel(overdrachtAttributes);

            return overdracht;
        });

        event.trigger("dossierOverdrachtHistoriek:dataReceived", overdrachtenArray);
    };

    // initialisatie van grid
    //////////////////////////////////////////////////

    _initGrid = function (el, isInitialized) {
        var _grid;

        if (!isInitialized) {
            _grid = new GridComp({
                el: el,
                model: OverdrachtHistoriekModel,
                editBtn: false,
                newBtn: false,
                deleteBtn: false
            });
            event.on("dossierOverdrachtHistoriek:dataReceived", function (data) {
                _grid.setData(data);
            });
        }
    };

    // params component
    ////////////////////////////////////////////

    ParamsModel = Model.extend({
        meta: Model.buildMeta([{
            name: "overdracht_id"
        }, {
            name: "dossier_id_boa"
        }, {
            name: "dossier_b"
        }])
    });

    _paramsComp = {};

    _paramsComp.controller = function () {
        this.params = new ParamsModel();
        this.showErrors = m.prop(false);
    };
    _.extend(_paramsComp.controller.prototype, {
        ophalen: function (ev) {
            ev.preventDefault();
            //alert(JSON.stringify(this.params));
            ajax.postJson({
                url: '/pad/s/sluis/overdracht/historiek/lijst',
                content: this.params
            }).then(function (response) {
                _processReceivedData(response.result);
            });
        }
    });
    _paramsComp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

        return [
            m("div", [
                m("form[autocomplete='off'][novalidate='']", [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td", "Overdracht Id:"),
                                m("td", [ff.input("overdracht_id")]),
                                m("td", "Bodem nr:"),
                                m("td", [ff.input("dossier_id_boa")]),
                                m("td", "Dossier titel:"),
                                m("td", [ff.input("dossier_b")]),
                                m("td", [
                                    m("button", {
                                        onclick: _.bind(ctrl.ophalen, ctrl)
                                    }, "Ophalen")
                                ])
                            ])
                        ])
                    ])
                ])
            ])
        ];
    };

    // basisComponent voor deze pagina
    ///////////////////////////////////////////////////////

    _comp = {};

    _comp.controller = function () {
        this.paramsCtrl = new _paramsComp.controller();
    };
    _comp.view = function (ctrl) {
        return [
            _paramsComp.view(ctrl.paramsCtrl),
            m("#overdrachtenGrid", {
                config: _initGrid,
                style: {
                    position: "absolute",
                    top: "40px",
                    left: "5px",
                    right: "5px",
                    bottom: "5px"
                }
            })
        ];
    };

    m.mount($("#jsviewContentDiv").get(0), _comp);

    return null;
});