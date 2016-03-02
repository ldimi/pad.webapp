/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, console, _G_ */

define([
    "planning/individueel/PlanningLijnDialog2",
    "planning/individueel/PlanningLijnModel2",
    "dropdown/dossierhouders",
    "dropdown/jaren",
    "ov/Model",
    "ov/GridComp",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "mithril",
    "underscore"
], function (PlanningLijnDialog, PlanningLijnModel, dossierhouders_dd, jaren_dd, Model, GridComp, events, ajax, fhf, m, _) {
    'use strict';

    var comp, paramComp, ParamModel, _planning;


    ParamModel =  Model.extend({
        meta: Model.buildMeta([
            { name: "jaar", type: "int" },
            { name: "dossier_id", type: "int" },
            { name: "dossier_type" },
            { name: "doss_hdr_id" },
            { name: "benut_jn" }
        ]),

        enforceInvariants: function () {
            if (this.hasChanged("doss_hdr_id")) {
                window.open('/pad/s/planning/individueel/bodemEnAfval2?doss_hdr_id=' + this.get("doss_hdr_id"),'_top');
            }

        }
    });

    paramComp = {
        controller: function () {
            this.params = new ParamModel({
                doss_hdr_id: _G_.model.doss_hdr_id,
                dossier_type: "NIET_X"
            });

            // customisatie van dropdown lijsten voor params
            jaren_dd[0].label = "Basis planning";
            _G_.model.dossiersDD.unshift({value: "", label: ""});

            this.benut_jn_dd = [
                {value: "", label: "incl. gerealiseerd"},
                {value: "N", label: "excl. gerealiseerd"}
            ];

            this.showErrors = m.prop(false);

            // methods
            ///////////////////////////////////////////////////////////////

            this.ophalen = function () {
                $('#planningGrid').addClass('invisible');
                ajax.postJSON({
                    url: "/pad/s/planning/getPlanning",
                    content: this.params
                }).then(function (response) {
                    if (response) {
                        _planning = response;
                        _planning.faseDD.unshift({
                            value: "",
                            label: ""
                        });
                        _planning.faseDetailDD.unshift({
                            value: "",
                            label: ""
                        });
                        _planning.contractenDD.unshift({
                            value: "",
                            label: ""
                        });

                        events.trigger("planning.lijnen:refresh", _planning.lijnen);
                        $('#planningGrid').removeClass('invisible');
                    }
                });
            };
        },
        view: function (ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

            return m("div",
                m("table", [
                    m("tr", [
                        m("td", "Dossier houder:"),
                        m("td", ff.select("doss_hdr_id", dossierhouders_dd)),
                        m("td", "Jaar:"),
                        m("td", ff.select("jaar", jaren_dd)),
                        m("td", ff.select("benut_jn", ctrl.benut_jn_dd)),
                        m("td", "Dossier nr:"),
                        m("td", ff.select("dossier_id", _G_.model.dossiersDD)),
                        m("td", m("button", {class: "inputBtn", onclick: _.bind(ctrl.ophalen, ctrl)}, "Ophalen"))
                    ])
                ])
            );
        }
    };

    comp = {
        controller: function () {
            this.paramCtrl = new paramComp.controller();
            this.dialogCtrl = new PlanningLijnDialog.controller();
        },
        view: function (ctrl) {
            return m("div", [
                paramComp.view(ctrl.paramCtrl),
                m("#planningGrid", {
                    config: comp.configGrid,
                    class: "invisible",
                    style: {
                        position: "absolute",
                        top: "35px",
                        left: "5px",
                        right: "5px",
                        bottom: "5px"
                    }
                }),
                PlanningLijnDialog.view(ctrl.dialogCtrl)
            ]);
        },
        configGrid: function (el, isInitialized) {
            var grid;
            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
                    model: PlanningLijnModel,
                    editBtn: true,
                    newBtn: true,
                    deleteBtn: true,
                    onEditClicked: function (item) {
                        // _planningLijnDialog.show(item, _planning);
                        var cloned = item.clone();
                        cloned.set("status_crud", "U");
                        events.trigger("planningLijnDialog:open", cloned, _planning);
                    },
                    onNewClicked: function (item) {
                        // var newItem;
                        // if (item) {
                        //     newItem = item.createNewLine();
                        //     _planning.selectedLijnIndex = _planning.lijnen.indexOf(item);
                        //     _planningLijnDialog.show(newItem, _planning);
                        // } else {
                        //     alert("er is geen rij geselecteerd.");
                        // }
                    },
                    onDeleteClicked: function (item) {
                        // if (item.get('status_crud') === 'C') {
                        //     $.notifyError("Ongeplande lijnen worden niet verwijderd.");
                        //     return;
                        // }
                        // if (item.get("c_isReedsGekoppeld")) {
                        //     $.notifyError("Deze planningslijn kan niet verwijderd worden. (reeds gekoppeld/benut)");
                        //     return;
                        // }
                        // item.set({
                        //     deleted_jn: "J",
                        //     status_crud: "U"
                        // });
                        // ajax.postJSON({
                        //     url: "/pad/s/planning/bewaar",
                        //     content: {
                        //         lijnen: [item.clone()]
                        //     }
                        // }).success(function () {
                        //     $.notify({
                        //         text: "De lijn is verwijderd."
                        //     });
                        // });
                        //
                        // // lokale versie wordt al aangepast (we gaan ervan uit dat bewaren toch lukt.)
                        // _planning.lijnen = _.reject(_planning.lijnen, function (lijn) {
                        //     return (lijn.cid === item.cid);
                        // });
                        //
                        // events.trigger("planning.lijnen:refresh", _planning.lijnen);
                    },
                    exportCsv: true,
                    exportCsvFileName: "planning.csv"
                });
                events.on("planning.lijnen:refresh", function(planningLijnen) {
                    grid.setData(planningLijnen);
                });
            }

        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

    return;
});