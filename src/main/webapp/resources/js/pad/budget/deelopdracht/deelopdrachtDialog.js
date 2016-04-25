/*jslint nomen: true, debug: true, browser: true */
/*global define: false, console: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "budget/deelopdracht/DeelopdrachtModel",
    "budget/deelopdracht/BriefModel",
    "budget/deelopdracht/deelopdrachtHistDialog",
    "budget/deelopdracht/briefDialog",
    "util/UploaderDialog",
    "ov/mithril/formhelperFactory",
    "ov/mithril/ajax",
    "ov/GridComp",
    "ov/events"
], function (DeelopdrachtModel, BriefModel, deelopdrachtHistDialog, briefDialog, UploaderDialog, fhf,ajax, GridComp, event) {
    'use strict';
    var _comp, goedkeuring_afkeuring_dd;

    goedkeuring_afkeuring_dd = [
        { value: "", label: "" },
        { value: "gk", label: "goedkeuring" },
        { value: "afk", label: "afkeuring" }
    ];

    _comp = {};

    _comp.controller = function () {

        event.on("deelopdrachtDialog:open", this.open.bind(this));
        event.on("brieven:fetch",this.fetchBrieven.bind(this));

        this.deelopdracht =  new DeelopdrachtModel();

        this.dossiers = [];
        this.offerten = [];

        this.planningItems = [];

        this.deelopdrachtHistDialogCtrl = new deelopdrachtHistDialog.controller();
        this.briefDialogCtrl = new briefDialog.controller();


        this.showErrors = m.prop(false);
    };
    _.extend(_comp.controller.prototype, {
        open: function (deelopdracht, dossiers, offerten, planningItems) {
            this.deelopdracht = new DeelopdrachtModel(deelopdracht);

            this.dossiers = dossiers;
            this.offerten = offerten;
            this.planningItems = planningItems;

            this.showErrors(false);
            m.redraw();
            this.$detailDialog.dialog('open');

            //event.trigger("brieven:fetch");
            this.fetchBrieven();
        },
        close: function () {
            this.$detailDialog.dialog('close');
        },
        filterredPlanningItems: function () {
            return _.filter(this.planningItems, function (planningItem) {
                return (planningItem.value === "" || planningItem.dossier_id === this.deelopdracht.get("dossier_id") );
            }, this);
        },
        fetchBrieven: function () {
            var deelopdracht_id;
            deelopdracht_id =this.deelopdracht.get("deelopdracht_id");
            if (deelopdracht_id) {
                ajax.getJSON({
                    url: '/pad/s/budget/deelopdracht/getBrieven?deelopdrachtId=' + deelopdracht_id
                }).then(function (response) {
                    if (response && response.success) {
                        event.trigger("brieven:dataReceived",response.result);
                    } else {
                        alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                    }
                });
            }
        },

        bewaar: function () {
            var action, self = this;

            this.showErrors(true);
            if (!this.deelopdracht.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }

            if (this.deelopdracht.get("afsluit_d")) {
                this.deelopdracht.set("bedrag", this.deelopdracht.get("schuldvordering_bedrag"));
            }


            if (this.deelopdracht.get("raamcontract_jn") === 'N') {
                if (this.deelopdracht.get("goedkeuring_d")) {
                    this.deelopdracht.set("goedkeuring_bedrag", this.deelopdracht.get("bedrag"));
                } else {
                    this.deelopdracht.set("goedkeuring_bedrag", null);
                }
            } else {
                // raamcontract
                if (this.deelopdracht.get("ander_doss_hdr_id") !== this.deelopdracht.get("current_doss_hdr_id") &&
                    this.deelopdracht.get("bestek_hdr_id") !== this.deelopdracht.get("current_doss_hdr_id")         ) {
                    if (this.deelopdracht.checkOpnieuwGoedkeuren()) {
                        this.deelopdracht.set("goedkeuring_d", null);
                        this.deelopdracht.set("afkeuring_d", null);
                        this.deelopdracht.set("afkeuring_opm", null);
                    }
                } else {
                    // raamcontract beheerder is aan het editeren.
                    if (this.deelopdracht.get("goedkeuring_d")) {
                        this.deelopdracht.set("goedkeuring_bedrag", this.deelopdracht.get("bedrag"));
                    }
                }
            }




            if (this.deelopdracht.get("status_crud") === 'C') {
                action = "insert";
            } else {
                action = "update";
            }

            ajax.postJson({
                url: '/pad/s/budget/deelopdracht/' + action,
                content: this.deelopdracht
            }).then(function (response) {
                if (response && response.success) {
                    self.close();
                    window.location = window.location;
                } else {
                    alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                }
            });

        },
        openHistoriek: function () {
            event.trigger("deelopdrachtHistDialog:open", this.deelopdracht.get("deelopdracht_id"));
        },
        openUploadDialog: function () {
            var uploadDialog;
            uploadDialog = new UploaderDialog("#uploadDialog");
            uploadDialog.show('/pad/s/budget/deelopdracht/upload?deelopdrachtId=' + this.deelopdracht.get("deelopdracht_id"), function () {
                event.trigger("brieven:fetch");
            });
        },
        _configDialog: function (el, isInitialized) {
            if (!isInitialized) {
                this.$detailDialog = $(el).dialog({
                    title: "Deelopdracht detail",
                    autoOpen: false,
                    modal: true,
                    width: 560
                });
            }
        },
        _configBrievenGrid: function (el, isInitialized) {
            var grid, self = this;

            if (!isInitialized) {
                grid = new GridComp({
                    el: el,
                    model: BriefModel,
                    newBtn: "Bestanden toevoegen",
                    editBtn: true,
                    onNewClicked: function () {
                        self.openUploadDialog();
                    },
                    onEditClicked: function (item) {
                        event.trigger("briefDialog:open",item);
                    }
                });
                event.on("brieven:dataReceived", function(data) {
                    grid.setData(data);
                });
            }
        }
    });

    _comp.view = function(ctrl) {
        var ff, deelopdracht;
        deelopdracht = ctrl.deelopdracht;
        ff = fhf.get().setModel(deelopdracht).setShowErrors(ctrl.showErrors());

        return [
            m("#detailDialog.hidden", {config: _.bind(ctrl._configDialog, ctrl) }, [
                m("form[autocomplete='off'][novalidate='']", [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td[width='100px']", "Bestek"),
                                m("td[width='160px']", ff.input("bestek_nr", {readOnly: true } )),
                                m("td[width='100px']", "Contract"),
                                m("td[width='160px']", ff.input("ander_dossier_nr", {readOnly: true } ))

                            ]),
                            m("tr", [
                                m("td", "Dossier"),
                                m("td[colspan=3]", ff.select("dossier_id", {}, ctrl.dossiers))
                            ]),
                            m("tr", [
                                m("td", "Offerte"),
                                m("td[colspan=3]", ff.select("offerte_id", {}, ctrl.offerten))
                            ]),
                            m("tr", [
                                m("td", "Plannings Item"),
                                m("td[colspan=3]", ff.select("planning_lijn_id", {}, ctrl.filterredPlanningItems()))
                            ]),
                            m("tr", [
                                m("td", "Geraamd bedrag (incl. BTW)"),
                                m("td", [
                                    ff.input("bedrag", {maxlength: 10 }),
                                    deelopdracht.boven_50_procent_grens() ?
                                        m("label", {style: {color: "blue"}}, "meer dan 50% verhoogd: moet opnieuw goedgekeurd worden") : ""
                                ]),
                                m("td", "Goedgekeurd bedrag SV"),
                                m("td", ff.input("schuldvordering_bedrag", {readOnly: true }))
                            ]),
                            m("tr", [
                                m("td", "Datum opdracht"),
                                m("td", ff.dateInput("voorstel_d")),
                                m("td", "Datum afsluiting"),
                                m("td", ff.dateInput("afsluit_d"))
                            ]),
                            ( deelopdracht.get("raamcontract_jn") === "J" )
                                ? // beheer goed/afkeuren voor raamcontracten
                                (deelopdracht.get("ander_doss_hdr_id") !== deelopdracht.get("current_doss_hdr_id") &&
                                 deelopdracht.get("bestek_hdr_id") !== deelopdracht.get("current_doss_hdr_id")  )
                                    ?  // readonly mededeling van goedkeurings status
                                    (deelopdracht.get("goedkeuring_afkeuring") === "gk")
                                        ? [
                                            m("tr",
                                                m("td[colspan=4]",
                                                    m("span", {style: {color: "blue"}},
                                                        "Goedgekeurd op " + deelopdracht.str("goedkeuring_d") + " voor " + deelopdracht.str("goedkeuring_bedrag") + " EUR."
                                            )))
                                        ]
                                        : (deelopdracht.get("goedkeuring_afkeuring") === "afk")
                                            ? [
                                                m("tr",
                                                    m("td[colspan=4]",
                                                        m("span", {style: {color: "red"}},
                                                            "Afgekeurd op " + deelopdracht.str("afkeuring_d")
                                                ))),
                                                m("tr",
                                                    m("td[colspan=4]",
                                                        m("span", {style: {color: "red"}},
                                                            deelopdracht.str("afkeuring_opm")
                                                ))),
                                                m("tr",
                                                    m("td", {colspan: 2}, ff.checkbox("opnieuw_goedkeuren_jn", "Opnieuw laten goedkeuren", "J", "N"))
                                                )
                                            ]
                                            : (deelopdracht.get("goedkeuring_bedrag") !== null )
                                                ?
                                                m("tr",
                                                    m("td[colspan=4]",
                                                        m("span", {style: {color: "blue"}},
                                                            "vorig goedgekeurd bedrag : " + deelopdracht.str("goedkeuring_bedrag") + " EUR."
                                                )))
                                                : null
                                    :
                                    [ // editeerbare velden om goed / af te keuren (editeerbaar voor ander_doss_hdr_id, en bestek_hdr_id)
                                        (deelopdracht.get("goedkeuring_bedrag") !== null) ?
                                            m("tr", [
                                                m("td", "Laatst goedgekeurd bedrag"),
                                                m("td", ff.input("goedkeuring_bedrag", {readOnly: true }))
                                            ]) : null,
                                        m("tr", [
                                            m("td", "Goed/Af-keuren"),
                                            m("td", ff.select("goedkeuring_afkeuring", goedkeuring_afkeuring_dd))
                                        ]),
                                        m("tr",
                                            (deelopdracht.get("goedkeuring_afkeuring") === "gk") ?
                                                [
                                                    m("td", "Datum goedkeuring"),
                                                    m("td", ff.dateInput("goedkeuring_d"))
                                                ] :
                                                (deelopdracht.get("goedkeuring_afkeuring") === "afk") ?
                                                    [
                                                        m("td", "Datum afkeuring"),
                                                        m("td", ff.dateInput("afkeuring_d"))
                                                    ] : null
                                        ),
                                        (deelopdracht.get("goedkeuring_afkeuring") === "afk") ?
                                            m("tr", [
                                                m("td", "Reden afkeuring"),
                                                m("td[colspan=3]", ff.textarea("afkeuring_opm"), {maxlength: 250})
                                            ]) : null
                                    ]
                                : null  // indien geen raamcontract, is er steeds een automatische goedkeuring
                        ])
                    ])
                ]),
                m("div", {style: { marginBottom: "10px" }}, [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "Annuleer")
                ]),

                deelopdracht.get("deelopdracht_id") === null ?
                    m("div", "Om bestanden toe te voegen, moet de deelopdracht eerst opgeslagen worden.") :
                    m("div", {
                        config: _.bind(ctrl._configBrievenGrid, ctrl),
                        style: { width: "530px", height: "150px" }
                    }),
                deelopdracht.get("deelopdracht_id") !== null ?
                    m("div", {style: {marginTop: "10px"}}, [
                            m("button", {onclick: _.bind(ctrl.openHistoriek, ctrl)}, "Toon geschiedenis")
                    ]) :
                    "",

                deelopdrachtHistDialog.view(ctrl.deelopdrachtHistDialogCtrl),
                briefDialog.view(ctrl.briefDialogCtrl)
            ]
        )];
    };

    return _comp;
});