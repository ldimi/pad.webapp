/*jslint nomen: true, debug: true, browser: true */
/*global define: false, console: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/Model",
    "dropdown/dossierTypes",
    "ov/mithril/formhelperFactory",
    "ov/events"
], function (Model, dossierTypes, fhf,event) {
    'use strict';
    var comp, ParamsModel;

    ParamsModel = Model.extend({
        meta: Model.buildMeta([{
            name: "dossier_type"
        }, {
            name: "selected_status_list"
        }])
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
                            m("td", "Dossier Type:"),
                            m("td", ff.select("dossier_type", ctrl.dossier_types)),
                            m("td", "Status:"),
                            m("td", ff.select_multiple("selected_status_list", ctrl.statusSelectOptions)),
                            m("td", m("button", {onclick: _.bind(ctrl.ophalen, ctrl) }, "Ophalen"))
                        ])
                    )
                )
            )
        );
    };


    return comp;
});