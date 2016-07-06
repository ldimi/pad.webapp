/*jslint nomen: true, debug: true, browser: true */
/*global define: false, console: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/Model",
    "dropdown/dossierTypes",
    "ov/mithril/formhelperFactory",
    "ov/events"
], function (Model, dossierTypes, fhf,event) {
    'use strict';
    var comp, ParamsModel, ja_nee_dd;

    ja_nee_dd = [
        { value:  "J", label: "Ja" },
        { value:  "N", label: "Nee" }
    ];

    ParamsModel = Model.extend({
        meta: Model.buildMeta([
           { name: "aanmaak_pad_jn", default: "J" },
           { name: "screening_jn", default: "J" },
           { name: "dossier_type" },
           { name: "selected_status_list" }
        ]),

        enforceInvariants: function () {
            if ( this.get("aanmaak_pad_jn") === "N" ) {
                this.attributes.screening_jn = "N";
                this.attributes.dossier_type = "B";
            } else if (this.hasChanged("aanmaak_pad_jn") && this.get("aanmaak_pad_jn") === "J") {
                this.attributes.screening_jn = "J";
                this.attributes.dossier_type = null;
            }
            
            if ((this.hasChanged("aanmaak_pad_jn") && this.get("screening_jn") === "N") ||
                (this.hasChanged("screening_jn") && this.get("screening_jn") === "N")      ) {
                this.attributes.selected_status_list = [];
            }
        }

    });

    comp = {};

    comp.controller = function () {
        this.params = new ParamsModel();

        // selecteer eerste status
        this.params.set("selected_status_list", _G_.model.page_status_list.slice(0, 1));

        event.on("dossierOverdracht:dataReceived", function (data) {
            this.params = new ParamsModel(data.params);
        }.bind(this));

        this.dossier_types = _.filter(dossierTypes, function (type) { return (type.value !== 'X'); });

        // selecteerbare statussen voor deze pagina
        this.statusSelectOptions = _.chain(_G_.model.overdrachtstatussen_dd)
            .map(_.clone)
            .filter(function (item) {
                return (_G_.model.page_status_list.indexOf(item.value) !== -1);
            })
            .value();
            
        this.statusSelectOptions_zonder_screening = _.chain(this.statusSelectOptions)
            .filter(function (item) {
                return !_.contains(["screening", "na_screen", "na_scr_afg"], item.value);
            })
            .value();


        this.showErrors = m.prop(false);
    };
    _.extend(comp.controller.prototype, {
        ophalen: function (ev) {
            var selected_status_list;
            ev.preventDefault();

            selected_status_list = this.params.get("selected_status_list");
            if (selected_status_list === null || selected_status_list.length === 0) {
                alert("Er moet minstens 1 status geselecteerd worden.");
                return;
            }

            event.trigger("dossierOverdracht:fetchOverdrachten", this.params);
        }
    });

    comp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

        return m("div",
            m("form[autocomplete='off'][novalidate='']",
                m("table",
                    m("tbody",
                        m("tr", [
                            m("td", "voor PAD:"),
                            m("td", ff.select("aanmaak_pad_jn", ja_nee_dd)),
                            ( ctrl.params.get("aanmaak_pad_jn") === "J" )
                                ? [
                                    m("td", "Screening ?:"),
                                    m("td", ff.select("screening_jn", ja_nee_dd))
                                  ]
                                : null,
                            m("td", "Dossier Type:"),
                            m("td", ff.select("dossier_type", {readOnly: ( ctrl.params.get("aanmaak_pad_jn") === "N" ) }, ctrl.dossier_types)),
                            m("td", "Status:"),
                            ( ctrl.params.get("screening_jn") === "J" )
                                ? m("td", ff.select_multiple("selected_status_list", ctrl.statusSelectOptions))
                                : m("td", ff.select_multiple("selected_status_list", ctrl.statusSelectOptions_zonder_screening)),
                            m("td", m("button", {onclick: _.bind(ctrl.ophalen, ctrl) }, "Ophalen"))
                        ])
                    )
                )
            )
        );
    };




    return comp;
});